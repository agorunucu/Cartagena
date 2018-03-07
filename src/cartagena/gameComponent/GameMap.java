package cartagena.gameComponent;

import cartagena.enums.CARD;
import cartagena.gameComponent.path.Cell;
import cartagena.gameComponent.path.Segment;
import cartagena.generator.PathGenerator;

public final class GameMap {
    private static GameMap ourInstance = new GameMap();
    public static GameMap getInstance() {
        return ourInstance;
    }


    private static Segment[] segments;

    private GameMap() {
        segments = PathGenerator.generateSegments();
        setCellIDs();
    }

    public GameMap(Segment... segments) {
        this.segments = segments.clone();
        setCellIDs();

    }



    public static Cell getCellByID(int id){
        int counter = 1;

        for(int i = 0; i< 6; i++)
            for(int j = 0; j < 6; j++)
                if(counter++ == id)
                    return GameMap.segments[i].cells[j];

        return null;
    }

    public static void generate(){
        segments = PathGenerator.generateSegments();
        setCellIDs();
    }

    private static void setCellIDs(){
        // 0 start point
        // 1-36 playing area
        // 37 boat

        int counter = 1;
        for(int i = 0; i < segments.length; i++)
            for(int j = 0; j < segments[i].cells.length; j++)
                segments[i].cells[j].ID = counter++;
    }

    public static String asString(){
        String returnString = "";

        for(int i = 0; i < segments.length; i++)
            for(int j = 0; j < segments[i].cells.length; j++)
                returnString += segments[i].cells[j].card.cardID;

        return returnString;
    }

    public static void set(String gameMapString){
        segments = new Segment[6];

        int segmentCounter = -1;
        for(int i = 0; i < gameMapString.length(); i++){
            if(i % 6 == 0){
                segmentCounter++;
                segments[segmentCounter] = new Segment();
                segments[segmentCounter].cells[0] = new Cell(i,Character.getNumericValue(gameMapString.charAt(i)));
            }else{
                segments[segmentCounter].cells[i % 6] = new Cell(i,Character.getNumericValue(gameMapString.charAt(i)));
            }
        }
    }

    /**
     * @param ID 0 -> start, 1..37 road, 38 -> boat
     * @param count starts with 0, max 2 .. 0,1,2
     * @return int[]: 0 is width, 1 is height
     */
    public static int[] getLocation(int ID, int count){

        int[] returnString = new int[2];

        switch (ID){
            case 0: returnString[0] = 722; returnString[1] = 14 + 23 * (count / 6); break;
            case 1: returnString[0] = 520; returnString[1] = 20; break;
            case 2: returnString[0] = 417; returnString[1] = 20; break;
            case 3: returnString[0] = 320; returnString[1] = 20; break;
            case 4: returnString[0] = 227; returnString[1] = 20; break;
            case 5: returnString[0] = 133; returnString[1] = 20; break;
            case 6: returnString[0] = 38; returnString[1] = 20; break;
            case 7: returnString[0] = 38; returnString[1] = 110; break;
            case 8: returnString[0] = 133; returnString[1] = 110; break;
            case 9: returnString[0] = 227; returnString[1] = 110; break;
            case 10: returnString[0] = 320; returnString[1] = 110; break;
            case 11: returnString[0] = 417; returnString[1] = 110; break;
            case 12: returnString[0] = 520; returnString[1] = 110; break;
            case 13: returnString[0] = 520; returnString[1] = 200; break;
            case 14: returnString[0] = 417; returnString[1] = 200; break;
            case 15: returnString[0] = 320; returnString[1] = 200; break;
            case 16: returnString[0] = 227; returnString[1] = 200; break;
            case 17: returnString[0] = 133; returnString[1] = 200; break;
            case 18: returnString[0] = 38; returnString[1] = 200; break;
            case 19: returnString[0] = 38; returnString[1] = 290; break;
            case 20: returnString[0] = 133; returnString[1] = 290; break;
            case 21: returnString[0] = 227; returnString[1] = 290; break;
            case 22: returnString[0] = 320; returnString[1] = 290; break;
            case 23: returnString[0] = 417; returnString[1] = 290; break;
            case 24: returnString[0] = 520; returnString[1] = 290; break;
            case 25: returnString[0] = 520; returnString[1] = 380; break;
            case 26: returnString[0] = 417; returnString[1] = 380; break;
            case 27: returnString[0] = 320; returnString[1] = 380; break;
            case 28: returnString[0] = 227; returnString[1] = 380; break;
            case 29: returnString[0] = 133; returnString[1] = 380; break;
            case 30: returnString[0] = 38; returnString[1] = 380; break;
            case 31: returnString[0] = 38; returnString[1] = 470; break;
            case 32: returnString[0] = 133; returnString[1] = 470; break;
            case 33: returnString[0] = 227; returnString[1] = 470; break;
            case 34: returnString[0] = 320; returnString[1] = 470; break;
            case 35: returnString[0] = 417; returnString[1] = 470; break;
            case 36: returnString[0] = 520; returnString[1] = 470; break;
            case 37: returnString[0] = 619; returnString[1] = 565; break;
            default: returnString[0] = 619; returnString[1] = 565; break;
        }

        returnString[0] += 23 * (count % 6);

        return returnString;
    }

    public static int findNext(int currentLocation, CARD card){

        while(true){
            if(++currentLocation >= 37){
                return 37;
            }

            if(getCellByID(currentLocation).card.compare(card)){
                return currentLocation;
            }
        }

    }
}
