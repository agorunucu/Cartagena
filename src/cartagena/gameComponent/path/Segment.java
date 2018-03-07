package cartagena.gameComponent.path;

public class Segment {
    public Cell[] cells;

    public Segment(){
        cells = new Cell[6];
    }

    public Segment(Cell... cell){
        cells = cell.clone();
    }

}
