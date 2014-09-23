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
    private final String address;
    private final int socketPort;
    
    Session(GameServer server, String nick, String key,  String address, int socketPort) {
        this.server = server;
        this.nick = nick;
        this.key = key;
        this.address = address;
        this.socketPort = socketPort;
    }

    public String getNick() {
        return nick;
    }
    public String getKey() {
        return key;
    }
    public int getSocketPort() {
        return socketPort;
    }
    public String getAddress() {
        return address;
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
