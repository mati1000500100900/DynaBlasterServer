package Entity;

import java.util.ArrayList;

public class BombList extends ArrayList<Bomb> {
    public Bomb findById(String id) {
        for (Bomb b : this) {
            if (b.getBombId().equals(id)) return b;
        }
        return null;
    }

}
