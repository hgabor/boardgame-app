package hu.level14.boardgameapp.remote;

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
}
