package hu.level14.boardgameapp.remote;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Application;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.AndroidException;
import android.util.Log;

public class GameServer {
    private String baseAddress;
    
    private GameServer(String address) {
        this.baseAddress = address;
    }
    
    public static Session NewSession(String address, String nick) {
        GameServer s = new GameServer(address);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("nick", nick);
        
        JSONObject obj = s.DoRequest("/session", map);
        String key = obj.optString("Key");
        if (TextUtils.isEmpty(key)) {
            throw new RuntimeException("No key returned");
        }
        
        return new Session(s, nick, key);
    }
    
    JSONObject DoRequest(String query, Map<String, Object> data) {
        // TODO: error handling
        return DoRequest(query, new JSONObject(data));
    }
    
    private JSONObject DoRequest(String query, Object data) {
        RequestTask task = new RequestTask(baseAddress + query, data);
        task.execute();
        try {
            return task.get();
        } catch (Exception e) {
            throw new RuntimeException(e); // Terminate the app
        }
    }
    
    private static class RequestTask extends AsyncTask<Void, Void, JSONObject> {
        private String address;
        private Object data;
        
        public RequestTask(String address, Object data) {
            this.address = address;
            this.data = data;
        }
        
        @Override
        protected JSONObject doInBackground(Void... params) {
            try {
                HttpClient client = AndroidHttpClient.newInstance("BoardGameApp Testing (https://github.com/hgabor/boardgame-app)");
                HttpPost post = new HttpPost(this.address);
                post.setHeader("Content-type", "application/json");
                post.setEntity(new StringEntity(data.toString()));
                HttpResponse response = client.execute(post);
                
                HttpEntity entity = response.getEntity();
                
                InputStream stream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }

                return new JSONObject(sb.toString());
            }
            catch(Exception e) {
                Log.e("io", "Error occured during a request.", e);
                throw new RuntimeException(e); // Terminate the app
            }
        }

        
    }
    
}
