package Entity;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class CoreGame {
    private PlayerList players;
    private BombList bombs;
    private boolean hasGameStarted;
    private boolean isGameOver;
    private Observable timer;

    public CoreGame(Lobby lobby) {
        players = new PlayerList();
        int i=0;
        for (ClientConnector participant : lobby.getParticipants()) {
            players.add(new Player(participant));
            participant.getSocket().write("A5:"+Integer.toString(i++)+";");
        }
        bombs = new BombList();
        hasGameStarted = false;
        isGameOver = false;
        this.timer = Observable.interval(200, TimeUnit.MILLISECONDS);
        Disposable subscribe = this.timer.subscribe(time -> divider((Long) time));
    }

    public void divider(Long time){
        kaBoom();
        if(time%5==0) broadcastMovement();
        //System.out.println(time);
    }

    public void newBomb(Player player, int posX, int posY) {
        Bomb bomb = new Bomb(posX, posY, 3000, player.getClientConnector().getId());
        bombs.add(bomb);
        for (Player p : players) {
            p.getClientConnector()
                    .getSocket()
                    .write("B2:" + Integer.toString(bomb.getPositionX()) + "|" + Integer.toString(bomb.getPositionY()) + "|" + bomb.getPlayerId() + "|" + bomb.getBombId() + ";");
        }
    }

    public void kaBoom() {
        for (Bomb b : bombs) {
            if (b.getTimer() < System.currentTimeMillis()) {
                broadcastBoom(b);
                bombs.remove(b);
                break;
            }
        }
    }

    public void broadcastBoom(Bomb bomb) {
        for (Player p : players) {
            p.getClientConnector()
                    .getSocket()
                    .write("B3:" + Integer.toString(bomb.getPositionX()) + "|" + Integer.toString(bomb.getPositionY()) + "|" + bomb.getPlayerId() + "|" + bomb.getBombId() + ";");
        }
    }


    public void broadcastMovement() {
        for (Player p : players) {
            p.getClientConnector().getSocket().write("B8:" + players.getPlayersPosition() + ";");
        }

    }

    public void recieveMovement(ClientConnector connector, int posX, int posY, int vector) {
        Player player = players.findByConnector(connector);
        if (player != null) {
            player.setPositionX(posX);
            player.setPositionY(posY);
            player.setVector(vector);
        }
    }

    public void killingInAName(ClientConnector connector) {
            Player victim = players.findByConnector(connector);
            victim.setIsAlive(false);
            broadcastDeath(victim);
    }

    public void broadcastDeath(Player victim) {
        for (Player p : players) {
            p.getClientConnector()
                    .getSocket()
                    .write("B4:" + victim.getClientConnector().getId() + "|" + Integer.toString(victim.getIsAlive()? 1 : 0) + ";");
        }

    }

    public PlayerList getPlayers() {
        return players;
    }

    public BombList getBombs() {
        return bombs;
    }

    public void setHasGameStarted(boolean hasGameStarted) {
        this.hasGameStarted = hasGameStarted;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}
