package Entity;

public class Player {
    private ClientConnector clientConnector;
    private int positionX;
    private int positionY;
    private int vector;
    private boolean isAlive;

    public Player(ClientConnector clientConnector) {

        this.clientConnector = clientConnector;
        this.isAlive=true;

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
        return positionY;
    }

    public void setPositionY(int position) {
        this.positionY = position;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean alive) {
        this.isAlive = alive;
    }

    public ClientConnector getClientConnector() {
        return clientConnector;
    }
}
