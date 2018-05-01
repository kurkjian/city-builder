import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import mayflower.*;

import java.util.Arrays;
import java.util.List;

public class BuildingHandler extends Actor {

    boolean building;
    boolean hasMap;
    CellMap map;
    CellMap prevMap;

    public BuildingHandler() {
        setImage("img/bh.png");

        building = false;
        hasMap = false;
    }

    public void act() {
        if(!hasMap)
        {
            getMap();
            prevMap = map;
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

        if(Mayflower.isKeyPressed(Keyboard.KEY_Z))
        {
            System.out.println("UNDO");
            for(int i = 0; i < map.rows(); i++)
            {
                for(int j = 0; j < map.cols(); j++)
                {
                    if(!map.getCell(i,j).equals(prevMap.getCell(i,j)))
                    {
                        System.out.println("change");
                        getWorld().addObject(prevMap.getCell(i,j),i*50,j*50);
                        map.setCell(i,j,prevMap.getCell(i,j));
                        getWorld().removeObject(map.getCell(i,j));
                    }
                }
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

                    prevMap = map;
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