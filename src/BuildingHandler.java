import mayflower.*;

import java.util.*;

public class BuildingHandler extends Actor {

    Cell selected;
    Queue<Cell> selectable;
    boolean building;
    boolean hasMap;
    CellMap map;

    public BuildingHandler(World lvl) {
        setImage("img/bh.png");

        building = false;
        hasMap = false;

        selectable = new LinkedList<>();
        selectable.add(new Road(1325, 100, 50, 50));
        selectable.add(new House(1325, 200));
        selectable.add(new Factory(1325, 300));
        selectable.add(new Grass(1325, 400, 50, 50));
        selectable.add(new Save(1325, 500, 50, 50));

        lvl.addObject(selectable.poll(), 1325, 100);
        lvl.addObject(selectable.poll(), 1325, 200);
        lvl.addObject(selectable.poll(), 1325, 300);
        lvl.addObject(selectable.poll(), 1325, 400);
        lvl.addObject(selectable.poll(), 1325, 500);
    }

    public void act() {
        if(!hasMap)
        {
            getMap();
        }

        if(Mayflower.mouseDown(this) && Mayflower.getMouseInfo().getX() < 1300)
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

        if(Mayflower.mouseDown(this) && Mayflower.getMouseInfo().getX() > 1300)
        {
            List<Actor> atMouse = Mayflower.mouseClicked();
            for(Actor a : atMouse)
            {
                if (a instanceof Road)
                {
                    Cell b = new Road(1, 1, 50, 50);
                    setSelected(b);
                }
                if (a instanceof House)
                {
                    Cell b = new House();
                    setSelected(b);
                }
                if (a instanceof Factory)
                {
                    Cell b = new Factory();
                    setSelected(b);
                }
                if (a instanceof Grass)
                {
                    Cell b = new Grass(1, 1, 50, 50);
                    setSelected(b);
                }
            }
        }

        refreshSelected();

        if(building)
        {
            List<Actor> atMouse = Mayflower.mouseClicked();
            for(Actor a : atMouse)
            {
                if(a instanceof Grass && a.getX() <= 1300)
                {

                   for(int i = 0; i < 12; i++)
                   {
                       Cell c;
                       getWorld().addObject(selected, a.getX(), a.getY());
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



                    map.setCell(Mayflower.getMouseInfo().getX()/50, Mayflower.getMouseInfo().getY()/50, selected);

                   getWorld().removeObject(a);
                }
            }
        }

    }

    public void setSelected(Cell c)
    {
        selected = c;
    }

    public void refreshSelected()
    {
        if (selected instanceof Road)
        {
            selected = new Road(1, 1, 50, 50);
        }
        if (selected instanceof House)
        {
            selected = new House(1, 1);
        }
        if (selected instanceof Factory)
        {
            selected = new Factory(1, 1);
        }
        if (selected instanceof Grass)
        {
            selected = new Grass(1, 1, 50, 50);
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