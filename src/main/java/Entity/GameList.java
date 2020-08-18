package Entity;

import java.util.ArrayList;

public class GameList extends ArrayList<CoreGame> {
    public CoreGame findByPlayer(ClientConnector c) {
        for (CoreGame g : this) {
            for (Player p: g.getPlayers()) {
                if(p.getClientConnector().equals(c)) return g;
            }
        }
        return null;
    }
}
