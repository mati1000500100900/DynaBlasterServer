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

        Observable ping = Observable.interval(1000, TimeUnit.MILLISECONDS); //WATCHDOG
        ping.subscribe(time -> {
            for (ClientConnector c : Global.clients) {
                if(c.getLastAlive()+1000<System.currentTimeMillis())
                    c.getSocket().write("PING:" + System.currentTimeMillis() + ";");
            }
            for (ClientConnector c : Global.clients) {
                if(c.getLastAlive()+30000<System.currentTimeMillis()){
                    Lobby lo=Global.lobbys.findLobbyByParticipant(c);
                    if(lo!=null){
                        lo.removeParticipant(c);
                        if(lo.getParticipants().size()==0) Global.lobbys.remove(lo);
                    }
                    Global.clients.remove(c);
                    c.getSocket().close();
                    System.out.println(c.getNick()+" timed out");
                    break;
                }
            }
        });

        System.out.println("Server is now listening");
    }

    public void interpreter(String data, NetSocket socket) {
        String[] command = data.split(":");
        if (command[0].equals("CONN")) {
            System.out.println(command[1] + " connected");
            ClientConnector cl = new ClientConnector(socket, command[1]);
            socket.write("OK:CONN:" + cl.getId() + ";");
            socket.write("PING:" + System.currentTimeMillis() + ";");
            Global.clients.add(cl);
        } else if(command[0].equals("RCON")) {
            ClientConnector cl = Global.clients.findById(command[1]);
            if(cl!=null){
                socket.write("OK:RCON;");
                cl.setSocket(socket);
            }
            else socket.write("ER:RCON;");
        } else {
            ClientConnector cl = Global.clients.findBySocket(socket);
            if (cl != null) { /*--\/--Only for connected clients--\/---*/
                cl.seenAlive();
                if (command[0].equals("DCON")) {
                    System.out.println(cl.getNick() + " disconnected");
                    Global.clients.remove(cl);
                    Lobby lo=Global.lobbys.findLobbyByParticipant(cl);
                    if(lo!=null){
                        lo.removeParticipant(cl);
                        if(lo.getParticipants().size()==0) Global.lobbys.remove(lo);
                    }
                    socket.close();
                } else if (command[0].equals("PONG")) {
                    Long ping = System.currentTimeMillis() - Long.parseLong(command[1]);
                    cl.setLastPing(ping);
                    System.out.println(cl.getNick() + " ping: " + ping + "ms");
                } else if(command[0].equals("A1")){ //OPEN NEW LOBBY
                    if(command.length==2){
                        Lobby lo = new Lobby(command[1]);
                        lo.addParticipant(cl);
                        Global.lobbys.add(lo);
                        socket.write("OK:A1:"+lo.getId()+";");
                        System.out.println("room: "+command[1]+" created");
                    }
                    else socket.write("ER:A1;");
                }else if(command[0].equals("A2")){ // REQUEST LOBBY LIST
                    socket.write("OK:A2:"+Global.lobbys.getLobbyNamesAndIds()+";");
                }else if(command[0].equals("A3")){ // JOIN LOBBY
                    if(command.length==2){
                        Lobby lo = Global.lobbys.findById(command[1]);
                        if(lo!=null){
                            lo.addParticipant(cl);
                            socket.write("OK:A3;");
                        }
                        else socket.write("ER:A3;");
                    }
                    else socket.write("ER:A3;");
                }else if(command[0].equals("A4")){ // LEAVE LOBBY
                    Lobby lo = Global.lobbys.findLobbyByParticipant(cl);
                    if(lo!=null){
                        lo.removeParticipant(cl);
                        socket.write("OK:A4;");
                        if(lo.getParticipants().size()==0) Global.lobbys.remove(lo);
                    }
                    else socket.write("ER:A4;");
                }
            }
        }
    }
}

