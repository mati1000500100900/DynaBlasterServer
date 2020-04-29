package Server;

import Entity.ClientConnector;
import Entity.Lobby;
import io.reactivex.Observable;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.reactivex.FlowableHelper;

import java.util.concurrent.TimeUnit;

public class Receiver extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        vertx.createNetServer().connectHandler(socket -> {
            RecordParser parser = RecordParser.newDelimited(";", socket);
            FlowableHelper.toFlowable(parser)
                    .map(buffer -> buffer.toString("UTF-8"))
                    .subscribe(data -> interpreter(data, socket), throwable -> {
                        throwable.printStackTrace();
                        socket.close();
                    });
        }).listen(2136);

        Observable ping = Observable.interval(1000, TimeUnit.MILLISECONDS);
        ping.subscribe(time -> {
            for (ClientConnector c : Global.clients) {
                c.getSocket().write("PING:" + System.currentTimeMillis() + ";");
            }
        });

        System.out.println("Server is now listening");
    }

    public void interpreter(String data, NetSocket socket) {
        String[] command = data.split(":");
        if (command[0].equals("CONNECT")) {
            System.out.println(command[1] + " connected");
            ClientConnector cl = new ClientConnector(socket, command[1]);
            socket.write("HI:" + cl.getId() + ";");
            Global.clients.add(cl);
        } else if(command[0].equals("RECONNECT")) {
            ClientConnector cl = Global.clients.findById(command[1]);
            if(cl!=null){
                socket.write("OK");
                cl.setSocket(socket);
            }
        } else {
            ClientConnector cl = Global.clients.findBySocket(socket);
            if (cl != null) { /*--\/--Only for connected clients--\/---*/
                if (command[0].equals("DISCONNECT")) {
                    System.out.println(cl.getNick() + " disconnected");
                    Global.clients.remove(cl);
                    socket.close();
                } else if (command[0].equals("PONG")) {
                    Long ping = System.currentTimeMillis() - Long.parseLong(command[1]);
                    System.out.println(cl.getNick() + " ping: " + ping + "ms");
                } else if(command[0].equals("A1")){ //OPEN NEW LOBBY
                    if(command.length==2){
                        Lobby lo = new Lobby(command[1]);
                        lo.addParticipant(cl);
                        Global.lobbys.add(lo);
                        socket.write("OK:A1:"+lo.getId()+";");
                        System.out.println("room: "+command[1]+" created");
                    }
                    else socket.write("ER:A3;");
                }else if(command[0].equals("A2")){ // REQUEST LOBBY LIST
                    socket.write("A2:"+Global.lobbys.getLobbyNamesAndIds()+";");
                }
            }
        }
    }
}

