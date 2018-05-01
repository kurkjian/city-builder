import mayflower.*;

import java.util.List;
import java.util.Map;

public class Road extends Cell {
    private CellMap map;
    public Road(int x, int y, int w, int h) {
        super(x,y,w,h);
        setImage("img/road.png");
    }

    public void act()
    {

    }


    public List<Road> junctionPoints()
    {
        if(getWorld() instanceof Level)
        {
            Level l= (Level) getWorld();
            map=l.getMap();
        }
        Cell[][] cellMap=map.getMap();
        for(int r =0; r < cellMap.length; r++)
        {
            for(int c =0; c< cellMap[0].length; c++ )
            {

            }
        }
        return null;
    }

    public String toString()
    {
        return "1";
    }
}
