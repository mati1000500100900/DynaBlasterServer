package Server;

import Entity.ClientsList;
import Entity.GameList;
import Entity.LobbyList;

public class Global {
    public static ClientsList clients = new ClientsList();
    public static LobbyList lobbys = new LobbyList();
    public static GameList games = new GameList();
}
