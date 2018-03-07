package cartagena.gameComponent.path;

import cartagena.enums.CARD;

public class Cell {
    public int ID;
    public CARD card;

    public Cell(CARD card){this.card = card;}

    public Cell(int ID, int cardID){
        this.ID = ID;
        this.card = CARD.getById(cardID);
    }

    public String toString(){
        return "ID: " + ID + ", Card: " + card.toString();
    }
}
