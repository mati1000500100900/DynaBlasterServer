package Entity;

import io.vertx.core.net.NetSocket;

import java.util.UUID;

public class ClientConnector {
    private NetSocket socket;
    private String nick;
    private String id;
    private boolean active;

    public ClientConnector(NetSocket s, String nick){
        this.socket=s;
        this.nick=nick;
        this.id=genId();
        this.active=true;
    }

    private String genId(){
        return UUID.randomUUID().toString().substring(0,6);
    }

    public NetSocket getSocket() {
        return socket;
    }

    public void setSocket(NetSocket socket) {
        this.socket = socket;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
