package Server;

import io.reactivex.Observable;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Receiver extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        ArrayList<NetSocket> sockets = new ArrayList<>();

        NetServer server = vertx.createNetServer();
        server.connectHandler(socket -> {
            socket.handler(buffer -> {
                if(buffer.toString().equals("CONNECT"))
                    sockets.add(socket);
                else if(buffer.toString().equals("DISCONNECT")) sockets.remove(socket);
                if(sockets.indexOf(socket)!=-1){
                    System.out.println(buffer.toString());
                }
            });
        });
        server.listen(1234, "localhost", res -> {
            if (res.succeeded()) {
                System.out.println("Server is now listening!");
            } else {
                System.out.println("Failed to bind!");
            }
        });
        Observable<Long> clock = Observable.interval(1, TimeUnit.SECONDS);

        clock.subscribe(time -> {
            for(NetSocket s : sockets){
                s.write("dupa");
            }
        });

    }
}