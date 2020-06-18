package Entity;

public class Player{
    private ClientConnector clientConnector;
    private int positionX;
    private int positionY;
    private int vector;
    private boolean isAlive;
    private int frags;

    public Player( ClientConnector clientConnector, int positionX, int positionY)
    {

        this.clientConnector = clientConnector;
        this.positionX=positionX;
        this.positionY=positionY;

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
