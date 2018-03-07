package cartagena.players;

import cartagena.enums.CARD;
import cartagena.enums.COLOR;
import cartagena.gameComponent.Pirate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class LocalPlayer {
    public static String username = "Undefined";
    public static COLOR userColor;
    public static List<CARD> userCards = new ArrayList<>();
    public static boolean isHost;
    public static boolean isReady = false;
    public static Pirate[] pirates = new Pirate[6];
    public static int queue = 0;

    public static void initialize(){
        for(int i = 0; i < pirates.length; i++)
            pirates[i] = new Pirate();
    }

    public String toString(){
        return getPlayerAsString();
    }

    public static String getPlayerAsString(){
        return username + "%" + userColor + "%" + isReady + "%" + queue + "%" +
                userCards.stream().map(CARD::getCardID)
                .collect(Collectors.joining(""));
    }

    public static void addCard(CARD c){
        userCards.add(c);
    }
    public static void useCard(CARD c){
        for(CARD card : userCards)
            if(card.compare(c)){
                userCards.remove(card);
                break;
            }
    }

    public static String getCards(){
        String returnString = "";
        for(int i = 0; i < userCards.size(); i++)
            returnString += userCards.get(i).cardID;

        return returnString.trim();
    }

    // import is a specific word
    public static void setCards(String cardString){
        userCards.clear();

        String cardIDs = cardString;
        for(int i=0; i < cardIDs.length(); i++){
            userCards.add(CARD.getById(Character.getNumericValue(cardIDs.charAt(i))));
        }

    }
}
