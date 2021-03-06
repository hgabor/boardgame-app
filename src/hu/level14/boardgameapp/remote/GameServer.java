package hu.level14.boardgameapp.remote;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Application;
import android.text.TextUtils;
import android.util.AndroidException;

public class GameServer {
    private final String baseAddress;
    
    private GameServer(String address) {
        this.baseAddress = address;
    }
    
    public static Session NewSession(String address, String nick) {
        GameServer s = new GameServer(address);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("nick", nick);
        
        JSONObject obj = (JSONObject)s.DoRequest("/session", map);
        String key = obj.optString("Key");
        if (TextUtils.isEmpty(key)) {
            throw new RuntimeException("No key returned");
        }
        String portString = obj.optString("SocketPort");
        int port = Integer.parseInt(portString);
        
        Pattern p = Pattern.compile("http://([^:]+)(:.+)?");
        Matcher m = p.matcher(address);
        
        if (!m.matches()) {
            throw new RuntimeException("Invalid address (for now): " + address);
        }
        String rawAddress = m.group(1);
        
        return new Session(s, nick, key, rawAddress, port);
    }
    
    Object DoRequest(String query, Map<String, Object> data) {
        // TODO: error handling
        if (data == null) {
            return DoRequest(query, (Object)null);
        }
        else {
            return DoRequest(query, new JSONObject(data));
        }
    }
    
    private Object DoRequest(String query, Object data) {
        RequestTask task = new RequestTask(baseAddress + query, data);
        task.execute();
        try {
            return task.get();
        } catch (Exception e) {
            throw new RuntimeException(e); // Terminate the app
        }
    }
    
    RequestTask DoRequestAsync(String query, Map<String, Object> data) {
        RequestTask task;
        if (data == null) {
            task = new RequestTask(baseAddress + query, null);
        }
        else {
            task = new RequestTask(baseAddress + query, new JSONObject(data));
        }
        task.execute();
        return task;
    }
    
}
