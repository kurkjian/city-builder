import java.util.ArrayList;
import java.util.List;

public class CellMap {
    private Cell[][] map;

    public CellMap(int r, int c){
        map = new Cell[r][c];
    }

    public void createMap(){
        for (int r = 0; r < map.length; r++){
            for (int c = 0; c < map[r].length; c++){
                map[r][c] = new Cell(r, c, 50, 50);
            }
        }
    }

    public Cell getCell(int x, int y){
        try {
            return map[x][y];
        }
        catch (Exception e) {
            return null;
        }
    }

    public void setCell(int x, int y, Cell c){
        map[x][y] = c;
    }

    public Cell[][] getMap() { return map; }

    public int rows() {
        return map.length;
    }

    public int cols() {
        return map[0].length;
    }
}