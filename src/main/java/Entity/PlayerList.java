package Entity;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PlayerList extends ArrayList<Player> {
    public Player findByConnector(ClientConnector s) {
        for (Player p : this) {
            if (p.getClientConnector().equals(s)) return p;
        }
        return null;
    }

    public Player findByPlayerID(String id) {
        for (Player p : this) {
            if (p.getClientConnector().getId().equals(id)) return p;
        }
        return null;
    }

    public String getPlayersPosition() {
        return this.stream().map(p -> p.getClientConnector().getId() + "|" + p.getPositionX() + "|" + p.getPositionY() + "|" + p.getVector() + "|" + Integer.toString(p.getIsAlive() ? 1 : 0)).collect(Collectors.joining(":"));
    }

}
