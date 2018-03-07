package cartagena.data;

import cartagena.network.Server;
import cartagena.players.RemotePlayer;

import java.util.ArrayList;
import java.util.List;

public final class PlayerData {
    public static List<RemotePlayer> remotePlayers = new ArrayList<>();
    public static List<Server> clients= new ArrayList<>();

    public static RemotePlayer getByName(String username){
        for(RemotePlayer rp : remotePlayers)
            if(rp.username.equalsIgnoreCase(username))
                return rp;
        return null;
    }

}
