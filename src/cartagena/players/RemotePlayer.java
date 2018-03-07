package cartagena.players;

import cartagena.enums.CARD;
import cartagena.enums.COLOR;
import cartagena.gameComponent.Pirate;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RemotePlayer {
    public String username;
    public COLOR userColor;
    public boolean isReady = false;
    public List<CARD> userCards = new ArrayList<>();
    public Pirate[] pirates = new Pirate[6];
    public int queue = 0;

    public RemotePlayer(String username){
        this.username = username;
        for(int i = 0; i < pirates.length; i++)
            pirates[i] = new Pirate();
    }
    public RemotePlayer(String username, int queue){
        this.username = username;
        this.queue = queue;
        for(int i = 0; i < pirates.length; i++)
            pirates[i] = new Pirate();
    }

    public String toString(){
        return username + "%" + userColor + "%" + isReady + "%" + queue + "%" +
                userCards.stream().map(CARD::getCardID)
                        .collect(Collectors.joining(""));
    }

    public static RemotePlayer createFromString(String playerString){
        RemotePlayer rp = new RemotePlayer(playerString.split("%")[0]);
        rp.userColor = COLOR.getColorByName(playerString.split("%")[1]);
        rp.isReady = Boolean.parseBoolean(playerString.split("%")[2]);
        rp.queue = Integer.parseInt(playerString.split("%")[3]);

        if(playerString.split("%").length > 4){
            String carts = playerString.split("%")[4];
            for(int i = 0; i < carts.length(); i++)
                rp.userCards.add(CARD.getById(Character.getNumericValue(carts.charAt(i))));

        }

        return rp;
    }


    public void addCard(CARD c){
        userCards.add(c);
    }


    public String getCards(){
        String returnString = "";
        for(int i = 0; i < userCards.size(); i++)
            returnString += userCards.get(i).cardID;

        return returnString.trim();
    }

    // import is a specific word
    public void setCards(String cardString){
        userCards.clear();

        String cardIDs = cardString;
        for(int i=0; i < cardIDs.length(); i++){
            userCards.add(CARD.getById(Character.getNumericValue(cardIDs.charAt(i))));
        }

    }
}
