package Entity;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Lobby {
    private String name;
    private String id;
    private ClientsList participants;
    private Observable shedule;

    public Lobby(String name) {
        this.participants = new ClientsList();
        this.name = name;
        this.id = genId();
        this.shedule = Observable.interval(1000, TimeUnit.MILLISECONDS);
        Disposable subscribe = this.shedule.subscribe(time -> broadcastUsers((Long) time));
    }

    public void broadcastUsers(Long time) {
        for (ClientConnector c : participants) {
            c.getSocket().write("C1:" + participants.getCilentsNicksAndPings() + ";");
        }
    }

    public String getId() {
        return id;
    }

    private String genId() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    public void broadcastUsers() {
        for (ClientConnector c : participants) {
            c.getSocket().write("DD");
        }
    }

    public void addParticipant(ClientConnector c) {
        this.participants.add(c);
    }

    public boolean removeParticipant(ClientConnector c) {
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
