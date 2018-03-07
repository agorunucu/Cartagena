package cartagena.data;

import cartagena.gameComponent.Deck;
import cartagena.players.LocalPlayer;
import cartagena.protocols.Message;

public final class GameData {
    public static boolean isGameStarted = false;
    public static int currentQueue = 0;

    public static void updateGameStatus(Message message){


        for(int i = 0; i < PlayerData.remotePlayers.size() + 1; i++){

            // Localplayer user data
            if(message.getContent().get(i*13).equalsIgnoreCase(LocalPlayer.username)){
                for(int j = 0; j < 6; j++){
                    LocalPlayer.pirates[j].location =
                            Integer.parseInt(message.getContent().get(i*13+(j*2)+1));

                }
                LocalPlayer.setCards(message.getContent().get(i*13 + 12));
            }
            // Remote players data
            else{
                for(int j = 0; j < 6; j++)
                    PlayerData.getByName(message.getContent().get(i*13 + j*2))
                            .pirates[j].location =
                            Integer.parseInt(message.getContent().get(i*13+(j*2 + 1)));
                PlayerData.getByName(message.getContent().get(i*13)).setCards(message.getContent().get(i*13 + 12));
            }

        }

        Deck.importt(message.getContent().get(message.getContent().size()-2));
        GameData.currentQueue = Integer.parseInt(message.getContent().get(message.getContent().size()-1));

    }
}
