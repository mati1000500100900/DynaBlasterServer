package Entity;

import java.util.UUID;

public class Lobby {
    private String name;
    private String id;
    private ClientsList participants;

    public Lobby(String name){
        this.name=name;
        this.participants = new ClientsList();
        this.id=genId();
    }

    public String getId() {
        return id;
    }

    private String genId(){
        return UUID.randomUUID().toString().substring(0,6);
    }

    public void broadcastUsers(){
        for(ClientConnector c : participants){
            c.getSocket().write("DD");
        }
    }

    public void addParticipant(ClientConnector c){
        this.participants.add(c);
    }

    public boolean removeParticipant(ClientConnector c){
        return this.participants.remove(c);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientsList getParticipants() {
        return participants;
    }

    public void setParticipants(ClientsList participants) {
        this.participants = participants;
    }
}
