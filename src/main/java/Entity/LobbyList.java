package Entity;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class LobbyList extends ArrayList<Lobby> {
    public String getLobbyNames(){
        return this.stream().map(l->l.getName()).collect(Collectors.joining(":"));
    }
    public String getLobbyNamesAndIds(){
        return this.stream().map(l->l.getName()+":"+l.getId()).collect(Collectors.joining(":"));
    }
}
