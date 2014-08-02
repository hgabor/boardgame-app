package hu.level14.boardgameapp.remote;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListFragment;

public class Session {
    private final GameServer server;
    private final String nick;
    private final String key;
    
    Session(GameServer server, String nick, String key) {
        this.server = server;
        this.nick = nick;
        this.key = key;
    }

    public String getNick() {
        return nick;
    }
    public String getKey() {
        return key;
    }
    
    public List<String> queryGameTypes() {
        try {
            JSONArray list = (JSONArray)server.DoRequest("/gametypes", null);
            int length = list.length();
            List<String> ret = new ArrayList<String>();
            for (int i = 0; i < length; i++) {
                ret.add(list.getString(i));
            }
            return ret;
        } catch (JSONException e) {
            throw new RuntimeException(e); // Terminate
        }
    }
}
