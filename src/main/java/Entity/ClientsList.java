package Entity;

import io.vertx.core.net.NetSocket;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ClientsList extends ArrayList<ClientConnector> {
    public ClientConnector findById(String id) {
        for (ClientConnector c : this) {
            if (c.getId().equals(id)) return c;
        }
        return null;
    }

    public ClientConnector findBySocket(NetSocket s) {
        for (ClientConnector c : this) {
            if (c.getSocket() == s) return c;
        }
        return null;
    }

    public String getCilentsNicksAndPings() {
        return this.stream().map(c -> c.getNick() + "|" + c.getLastPing()).collect(Collectors.joining(":"));
    }
}
