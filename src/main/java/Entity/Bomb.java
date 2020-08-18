package Entity;

import java.util.UUID;

public class Bomb {
    private int positionX;
    private int positionY;
    private long timer;
    private String playerId;
    private String bombId;

    public Bomb(int positionX, int positionY, int timer, String playerId) {
        this.bombId = genId();
        this.playerId = playerId;
        this.positionX = positionX;
        this.positionY = positionY;
        this.timer = timer + System.currentTimeMillis();
    }

    private String genId() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public long getTimer() {
        return timer;
    }

    public String getBombId() {
        return bombId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
