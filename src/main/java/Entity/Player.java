package Entity;

public class Player {
    private ClientConnector clientConnector;
    private int positionX;
    private int positionY;
    private int vector; // 0 - dół, 1 - prawo, 2 - góra, 3 - lewo
    private boolean isAlive;
    private int frags;

    public Player(ClientConnector clientConnector) {

        this.clientConnector = clientConnector;

    }

    public void initPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getVector() {
        return vector;
    }

    public void setVector(int vector) {
        this.vector = vector;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int position) {
        this.positionX = position;
    }

    public int getPositionY() {
        return positionX;
    }

    public void setPositionY(int position) {
        this.positionX = position;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean alive) {
        this.isAlive = alive;
    }

    public int getWins() {
        return frags;
    }

    public void setWins(int frags) {
        this.frags = frags;
    }

    public ClientConnector getClientConnector() {
        return clientConnector;
    }
}
