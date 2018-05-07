import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import mayflower.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BuildingHandler extends Actor {

    Cell selected;
    Queue<Cell> selectable;
    boolean building;
    boolean hasMap;
    CellMap map;
    Level l;

    public BuildingHandler(Level lvl) {
        setImage("img/bh.png");

        building = false;
        hasMap = false;
        l = lvl;

        lvl.addObject(new BuildMenu(1300, 0), 1300, 0);

        selectable = new LinkedList<>();

        selectable.add(new Road(1325, 100, 50, 50));
        selectable.add(new House(1325, 200));
        selectable.add(new Factory(1325, 300));
        selectable.add(new Grass(1325, 400, 50, 50));

        lvl.addObject(selectable.poll(), 1325, 100);
        lvl.addObject(selectable.poll(), 1325, 200);
        lvl.addObject(selectable.poll(), 1325, 300);
        lvl.addObject(selectable.poll(), 1325, 400);
    }

    public void act() {
        if(!hasMap)
        {
            getMap();
        }

//        List<Actor> atMouse = Mayflower.mouseClicked();

        if(Mayflower.mouseDown(this) && Mayflower.getMouseInfo().getX() < 1300)
        {
            building = true;
//            System.out.println("DOWN");
        }
        else
        {
//            System.out.println("UP");
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
                    Cell b = new Road(Mayflower.getMouseInfo().getX(), Mayflower.getMouseInfo().getY(), 50, 50);
                    setSelected(b);
                }
                else if (a instanceof House)
                {
                    Cell b = new House();
                    setSelected(b);
                }
                else if (a instanceof Factory)
                {
                    Cell b = new Factory();
                    setSelected(b);
                }
                else if (a instanceof Grass)
                {
                    Cell b = new Grass(Mayflower.getMouseInfo().getX(), Mayflower.getMouseInfo().getY(), 50, 50);
                    setSelected(b);
                }
            }
            SelectedInfo();
        }

        refreshSelected();

        if(building)
        {
            List<Actor> atMouse = Mayflower.mouseClicked();
            for(Actor a : atMouse)
            {
                if (a instanceof Grass && selected instanceof Road && a.getX() <= 1300)
                {
                    getWorld().addObject(selected, a.getX(), a.getY());
                    map.setCell(Mayflower.getMouseInfo().getX()/50, Mayflower.getMouseInfo().getY()/50, selected);
                    getWorld().removeObject(a);
                    Road r = (Road) selected;
                    l.setMoney(l.getMoney() - r.getCost());
                }
                else if(a instanceof Grass && a.getX() <= 1300 && !(selected instanceof Grass))
                {
                    Grass g = (Grass) a;
                    if (g.isAvailable()){
                        getWorld().addObject(selected, a.getX(), a.getY());
                        map.setCell(Mayflower.getMouseInfo().getX()/50, Mayflower.getMouseInfo().getY()/50, selected);
                        getWorld().removeObject(a);
                        Building b = (Building) selected;
                        l.setMoney(l.getMoney() - b.getCost());
                    }
                }
                else if (selected instanceof Grass && a.getX() <= 1300)
                {
                    getWorld().addObject(selected, a.getX(), a.getY());
                    map.setCell(Mayflower.getMouseInfo().getX()/50, Mayflower.getMouseInfo().getY()/50, selected);
                    getWorld().removeObject(a);

                }
            }
            refreshAvailable();
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

    public void setSelected(Cell c)
    {
        selected = c;
    }

    public void refreshSelected()
    {
        if (selected instanceof Road)
        {
            selected = new Road(Mayflower.getMouseInfo().getX(), Mayflower.getMouseInfo().getY(), 50, 50);
        }
        else if (selected instanceof House)
        {
            selected = new House(Mayflower.getMouseInfo().getX(), Mayflower.getMouseInfo().getY());
        }
        else if (selected instanceof Factory)
        {
            selected = new Factory(Mayflower.getMouseInfo().getX(), Mayflower.getMouseInfo().getY());
        }
        else if (selected instanceof Grass)
        {
            selected = new Grass(Mayflower.getMouseInfo().getX(), Mayflower.getMouseInfo().getY(), 50, 50);
        }
    }

    public void refreshAvailable()
    {
        for (int row = 0; row < map.rows(); row++)
        {
            for (int col = 0; col < map.cols(); col++)
            {
                if (map.getCell(row, col) instanceof Road)
                {
                    Road a = (Road) map.getCell(row, col);
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
                }
            }
        }
    }

    public void SelectedInfo()
    {
        if (selected instanceof Road)
        {
            getWorld().showText("Road", 15, 1325, 500, Color.BLACK);
            getWorld().showText("Cost: 1", 15, 1325, 525, Color.BLACK);
        }
        else if (selected instanceof House)
        {
            getWorld().showText("House", 15, 1325, 500, Color.BLACK);
            getWorld().showText("Cost: 5", 15, 1325, 525, Color.BLACK);
        }
        else if (selected instanceof Factory)
        {
            getWorld().showText("Factory", 15, 1325, 500, Color.BLACK);
            getWorld().showText("Cost: 10", 15, 1325, 525, Color.BLACK);
        }
    }

    public CellMap updateMap() {
        return map;
    }
}