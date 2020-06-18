package Entity;

public class Player{
    private ClientConnector clientConnector;
    private String nick;
    private int positionX;
    private int positionY;
    private int vector;
    private boolean isAlive;
    private int frags;
    private String id;
    private long lastAlive;
    private long lastPing;

    public Player(int positionX, int positionY, int vector, boolean isAlive, int frags)
    {
        this.id = this.clientConnector.getId();
        this.nick = this.clientConnector.getNick();
        this.lastAlive = this.clientConnector.getLastAlive();
        this.lastPing = this.clientConnector.getLastPing();
        this.positionX=positionX;
        this.positionY=positionY;
        this.vector=vector;
        this.isAlive=isAlive;
        this.frags=frags;

    }

    public void setVector(int vector) {
        this.vector = vector;
    }

    public int getVector() {
        return vector;
    }

    public void setPositionX(int position) {
        this.positionX = position;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionY(int position) {
        this.positionX = position;
    }

    public int getPositionY() {
        return positionX;
    }

    public void setIsAlive(boolean alive) {
        this.isAlive = alive;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public int getWins() {
        return frags;
    }

    public void setWins(int frags) {
        this.frags = frags;
    }
}
