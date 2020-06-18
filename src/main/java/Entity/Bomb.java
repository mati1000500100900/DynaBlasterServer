package Entity;

public class Bomb {
    private int positionX;
    private int positionY;
    private int timer;
    private String playerId;
    private String bombId;

    public Bomb(int positionX, int positionY, int timer, String playerId, String bombId)
    {
        this.bombId = bombId;
        this.playerId = playerId;
        this.positionX = positionX;
        this.positionY = positionY;
        this.timer = timer;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getTimer() {
        return timer;
    }

    public String getBombId() {
        return bombId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
