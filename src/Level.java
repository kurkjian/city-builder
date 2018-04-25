import mayflower.Actor;
import mayflower.Mayflower;
import mayflower.MouseInfo;
import mayflower.World;

import java.util.List;


public class Level extends World {
    private CellMap map;
    private BuildingHandler bh;

    public Level() {
        map = new CellMap(54,54);
        for(int i = 0; i < map.rows(); i++)
        {
            for(int j = 0; j < map.cols(); j++)
            {
                Grass g = new Grass(i*50,j*50,50,50);
                addObject(g,i*50,j*50);
                map.setCell(i,j,g);
            }
        }

        bh = new BuildingHandler();
        addObject(bh,0,0);
    }

    public void act()
    {
        if(bh.updateMap() != null)
            map = bh.updateMap();

        /*MouseInfo mouse = Mayflower.getMouseInfo();
        if(mouse.getX() < 25)
        {
            List<Actor> actors = getObjects();
            for(Actor a : actors)
            {
                a.setLocation(a.getX() + 10, a.getY());
            }
        }*/

    }

    public CellMap getMap() {
        return map;
    }
}
