package cartagena.enums;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Arrays;

public enum COLOR {
    Blue("Blue", Color.BLUE, "/resources/cblue.png"),
    Yellow("Yellow", Color.YELLOW, "/resources/cyellow.png"),
    Red("Red", Color.RED, "/resources/cred.png"),
    Green("Green", Color.GREEN, "/resources/cgreen.png"),
    Brown("Brown", Color.BROWN, "/resources/cbrown.png");

    private Color color;
    private String name;
    private Image image;
    COLOR(String name, Color color, String imageLoc){
        this.name = name;
        this.color = color;
        this.image = new Image(getClass().getResourceAsStream(imageLoc));
    }

    public Image getImage(){
        return image;
    }

    public static COLOR getColorByName(String color){
        for(COLOR c : values())
            if(c.name.equals(color))
                return c;
        return null;
    }
    @Override
    public String toString() {
        return name;
    }
}
