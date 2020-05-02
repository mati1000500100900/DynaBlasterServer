package Entity;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class LobbyList extends ArrayList<Lobby> {
    public String getLobbyNames(){
        return this.stream().map(l->l.getName()).collect(Collectors.joining(":"));
    }
    public String getLobbyNamesAndIds(){
        return this.stream().map(l->l.getName()+"/"+l.getId()).collect(Collectors.joining(":"));
    }
    public Lobby findById(String id){
        for(Lobby l : this){
            if(l.getId().equals(id)) return l;
        }
        return null;
    }
    public Lobby findLobbyByParticipant(ClientConnector c){
        for(Lobby l : this){
            if(l.getParticipants().indexOf(c)!=-1) return l;
        }
        return null;
    }
}
