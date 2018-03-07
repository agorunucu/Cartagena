package cartagena.gameComponent;

import cartagena.enums.CARD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Deck {

    private static List<CARD> cards = new ArrayList<>();

    public static void create(){
        cards.clear();
        for(int i = 0; i < 30; i++) {
            cards.add(CARD.Bottle);
            cards.add(CARD.Hat);
            cards.add(CARD.Keys);
            cards.add(CARD.Pistol);
            cards.add(CARD.Skull);
            cards.add(CARD.Sword);
        }
        shuffle();
    }

    public static CARD draw(){
        if(cards.size() == 0)
            create();

        CARD c = cards.get(0);
        cards.remove(0);
        return c;
    }

    public static int size(){
        return cards.size();
    }

    public static void shuffle(){
        Collections.shuffle(cards);
    }

    public static String export(){
        String returnString = "";
        for(int i = 0; i < cards.size(); i++)
            returnString += cards.get(i).cardID;

        return returnString.trim();
    }

    // import is a specific word
    public static void importt(String cardString){
        cards.clear();

        String cardIDs = cardString;
        for(int i=0; i < cardIDs.length(); i++){
            cards.add(CARD.getById(Character.getNumericValue(cardIDs.charAt(i))));
        }

    }


}
