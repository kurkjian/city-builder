import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import mayflower.*;

import java.util.Arrays;
import java.util.List;

public class BuildingHandler extends Actor {

    boolean building;
    boolean hasMap;
    CellMap map;

    public BuildingHandler() {
        setImage("img/bh.png");

        building = false;
        hasMap = false;
    }

    public void act() {
        if(!hasMap)
        {
            getMap();
        }

        if(Mayflower.mouseDown(this))
        {
            building = true;
        }
        else
        {
            if(building)
            {
                building = false;
            }
        }

        if(building)
        {
            List<Actor> atMouse = Mayflower.mouseClicked();
            for(Actor a : atMouse)
            {
                if(a instanceof Grass)
                {
                   Road road = new Road(a.getX(),a.getY(),50,50);
                   getWorld().addObject(road, a.getX(), a.getY());
                   for(int i = 0; i < 12; i++)
                   {
                       Cell c;

                       if(i < 3)
                       {
                           c = map.getCell(a.getX()/50, a.getY()/50 - i - 1);
                       }
                       else if (i < 6)
                       {
                           c = map.getCell(a.getX()/50, a.getY()/50 + i + 1 - 3);
                       }
                       else if (i < 9)
                       {
                           c = map.getCell(a.getX()/50 - 1 - i + 6, a.getY()/50);
                       }
                       else
                       {
                           c = map.getCell(a.getX()/50 + 1 + i - 9, a.getY()/50);
                       }

                       if(c instanceof Grass)
                       {
                           Grass g = (Grass)c;
                           g.setAvailable();
                       }
                   }



                   map.setCell(a.getX()/50, a.getY()/50, road);

                   getWorld().removeObject(a);
                }
            }
        }

    }

    public void getMap() {
        if(getWorld() instanceof Level)
        {
            Level l = (Level) getWorld();
            map = l.getMap();
            hasMap = true;
        }
    }

    public CellMap updateMap() {
        return map;
    }
}