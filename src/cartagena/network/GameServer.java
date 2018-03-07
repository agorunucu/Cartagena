package cartagena.network;

import cartagena.GUI.GameScreen;
import cartagena.data.GameData;
import cartagena.data.PlayerData;
import cartagena.gameComponent.Deck;
import cartagena.players.LocalPlayer;
import cartagena.players.RemotePlayer;
import cartagena.protocols.Message;

public final class GameServer {

    public static GameScreen gs;

    public static Message getGameStatus(){
        Message returnMessage = new Message()
                .setCode("710")
                .setUser(LocalPlayer.username);

        for(int i = 0; i < 6; i++)
            returnMessage.addContent(LocalPlayer.username, LocalPlayer.pirates[i].location + "");
        returnMessage.addContent(LocalPlayer.getCards());

        for(RemotePlayer rp : PlayerData.remotePlayers){
            for(int i = 0; i < 6; i++)
                returnMessage.addContent(rp.username, rp.pirates[i].location + "");
            returnMessage.addContent(rp.getCards());
        }
        returnMessage.addContent(Deck.export());
        returnMessage.addContent(GameData.currentQueue + "");
        return returnMessage;
    }

    public static void sendGameStatus(){
        sendToClients(getGameStatus());
    }

    public static void sendToClients(Message message){
        for(Server s : PlayerData.clients)
            s.sendToClient(message.toString());
    }

    public static void receivedData(Message message) {
        // If it's host
        gs.newMessageArrives(message);
        /*System.out.println("Request from client: " + message);

        switch (message.getCode()){
            case "710": GameData.updateGameStatus(message); break;
            case "810": GameData.updateGameStatus(message); sendToClients(message); break;
        }*/

    }


    public static void initialize(){
        Deck.create();

        for(int i = 0; i < 3;i++){
            LocalPlayer.addCard(Deck.draw());
            for(RemotePlayer rp : PlayerData.remotePlayers)
                rp.addCard(Deck.draw());
        }

    }
}
