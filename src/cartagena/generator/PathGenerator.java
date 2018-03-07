package cartagena.generator;

import cartagena.enums.CARD;
import cartagena.gameComponent.path.Cell;
import cartagena.gameComponent.GameMap;
import cartagena.gameComponent.path.Segment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class PathGenerator {

    public static Cell[] generateCells(){
        List<CARD> cards = Arrays.asList(CARD.values());
        Collections.shuffle(cards);
        List<Cell> cells = new ArrayList<>();
        for(CARD c : cards){
            cells.add(new Cell(c));
        }
        return cells.toArray(new Cell[0]);

    }

    public static Segment[] generateSegments(){
        Segment[] segmentList = new Segment[6];

        for(int i = 0; i < segmentList.length; i++){
            segmentList[i] = new Segment(generateCells());
        }

        return segmentList;
    }

}
