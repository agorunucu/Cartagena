package cartagena.enums;

import javafx.scene.image.Image;

public enum CARD {
    Bottle(1,"/resources/bottle.png"),
    Keys(2,"/resources/keys.png"),
    Sword(3,"/resources/sword.png"),
    Skull(4,"/resources/skull.png"),
    Hat(5,"/resources/hat.png"),
    Pistol(6,"/resources/pistol.png");

    public int cardID;
    private Image image;
    CARD(int cardID, String imageLoc){
        this.cardID = cardID;
        this.image = new Image(getClass().getResourceAsStream(imageLoc));
    }
    public boolean compare(CARD c){
        return cardID == c.cardID;
    }

    public static CARD getById(int id) {
        for(CARD e : values()) {
            if(e.cardID == id) return e;
        }
        return null;
    }


    public Image getImage(){
        return image;
    }
    public String getCardID(){return cardID + "";}
}
