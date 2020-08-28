package Entity;

import java.util.ArrayList;
import java.util.Objects;

public class BombList extends ArrayList<Bomb> {
    public Bomb findByBombId(String id) {
        return this.stream().filter(b -> Objects.equals(b.getBombId(), id)).findFirst().orElse(null);
    }

    public Bomb findByPlayerId(String id) {
        return this.stream().filter(b -> Objects.equals(b.getPlayerId(), id)).findFirst().orElse(null);
    }

}
