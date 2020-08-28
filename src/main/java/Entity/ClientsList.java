package Entity;

import io.vertx.core.net.NetSocket;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClientsList extends ArrayList<ClientConnector> {
    public ClientConnector findById(String id) {
        return this.stream().filter(c -> Objects.equals(c.getId(), id)).findFirst().orElse(null);
    }

    public ClientConnector findBySocket(NetSocket s) {
        return this.stream().filter(c -> Objects.equals(c.getSocket(), s)).findFirst().orElse(null);
    }

    public String getCilentsNicksAndPings() {
        return this.stream().map(c -> c.getNick() + "|" + c.getLastPing()).collect(Collectors.joining(":"));
    }
}
