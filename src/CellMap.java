import java.util.ArrayList;
import java.util.List;

public class CellMap {
    private Cell[][] map;

    public CellMap(int r, int c){
        map = new Cell[r][c];
    }

    public CellMap(Cell[][] m)
    {
        map = m;
    }

    public CellMap(int i)
    {
        map = new Cell[26][16];

        for(int j = 0; j < map.length; j++)
        {
            for(int k = 0; k < map[0].length; k++)
            {
                map[j][k] = new Grass(j,k,50,50);
            }
        }
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

    public String toString()
    {
        String ret = "";
        for(int i = 0; i < rows(); i++)
        {
            for(int j = 0; j < cols(); j++)
            {
                ret += String.format("%2s ", getCell(i,j).toString());
            }
            ret += "\n";
        }

        return ret;
    }

    public CellMap copy()
    {
        CellMap ret = new CellMap(map.length, map[0].length);

        for(int i = 0; i < ret.rows(); i++)
        {
            for(int j = 0; j < ret.cols(); j++)
            {
                Cell test = map[i][j];
                if(test instanceof Grass)
                {
                    if(((Grass) test).isAvailable())
                    {
                        Grass g = new Grass(i,j,50,50);
                        g.setAvailable();
                        ret.setCell(i,j,g);
                    }
                    else
                    {
                        ret.setCell(i,j, new Grass(i,j,50,50));
                    }
                }
                else if( test instanceof Road)
                {
                    ret.setCell(i,j,new Road(i,j,50,50));
                }
                else if( test instanceof House)
                {
                    ret.setCell(i,j,new House(i,j));
                }
                else if( test instanceof Factory)
                {
                    ret.setCell(i,j,new Factory(i,j));
                }


//                ret.setCell(i,j,map[i][j]);
            }
        }

        return ret;
    }

}