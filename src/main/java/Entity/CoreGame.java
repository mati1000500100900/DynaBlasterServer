package Entity;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class CoreGame {
    private PlayerList players;
    private BombList bombs;
    private boolean hasGameStarted;
    private boolean isGameOver;
    private Observable explosion;
    private Observable movement;

    public CoreGame(Lobby lobby) {
        players = new PlayerList();
        for (ClientConnector participant : lobby.getParticipants()) {
            players.add(new Player(participant));
        }
        bombs = new BombList();
        hasGameStarted = false;
        isGameOver = false;
        this.explosion = Observable.interval(20, TimeUnit.MILLISECONDS);
        Disposable subscribe = this.explosion.subscribe(time -> kaBoom((Long) time));
        this.movement = Observable.interval(20, TimeUnit.MILLISECONDS);
        Disposable subscribe2 = this.movement.subscribe(time -> broadcastMovement((Long) time));
    }

    public void broadcastPlanted(Player player, int posX, int posY) {
        Bomb bomb = new Bomb(posX, posY, 3000, player.getClientConnector().getId());
        bombs.add(bomb);
        for (Player p : players) {
            p.getClientConnector()
                    .getSocket()
                    .write("B2:" + Integer.toString(bomb.getPositionX()) + "|" + Integer.toString(bomb.getPositionY()) + "|" + bomb.getPlayerId() + "|" + bomb.getBombId() + ";");
        }
    }

    public void kaBoom(Long time) {
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


    public void broadcastMovement(Long time) {
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

    public void killingInAName(String bombId, ClientConnector connector) {
        Bomb bomb = bombs.findById(bombId);
        if (bomb != null) {
            Player victim = players.findByConnector(connector);
            Player agressor = players.findByPlayerID(bomb.getPlayerId());
            victim.setIsAlive(false);
            broadcastDeath(victim);
            agressor.setWins(agressor.getWins() + 1);
        }
    }

    public void broadcastDeath(Player victim) {
        for (Player p : players) {
            p.getClientConnector()
                    .getSocket()
                    .write("B4:" + victim.getClientConnector().getId() + "|" + Boolean.toString(victim.getIsAlive()) + ";");
        }

    }


}
