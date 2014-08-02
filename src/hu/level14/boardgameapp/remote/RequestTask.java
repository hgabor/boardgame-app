package hu.level14.boardgameapp.remote;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

class RequestTask extends AsyncTask<Void, Void, Object> {
    private String address;
    private Object data;
    
    public RequestTask(String address, Object data) {
        this.address = address;
        this.data = data;
    }
    
    @Override
    protected Object doInBackground(Void... params) {
        try {
            HttpClient client = AndroidHttpClient.newInstance("BoardGameApp Testing (https://github.com/hgabor/boardgame-app)");
            HttpResponse response;
            if (data != null) {
                HttpPost post = new HttpPost(this.address);
                post.setHeader("Content-type", "application/json");
                post.setEntity(new StringEntity(data.toString()));
                response = client.execute(post);
            }
            else {
                HttpGet get = new HttpGet(this.address);
                get.setHeader("Content-type", "application/json");
                response = client.execute(get);
            }
            
            HttpEntity entity = response.getEntity();
            
            InputStream stream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }

            JSONTokener tokener = new JSONTokener(sb.toString());
            
            return tokener.nextValue();
        }
        catch(Exception e) {
            Log.e("io", "Error occured during a request.", e);
            throw new RuntimeException(e); // Terminate the app
        }
    }
}