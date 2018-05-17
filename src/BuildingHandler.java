import mayflower.*;

import java.util.*;

public class BuildingHandler extends Actor {

    Cell selected;
    Queue<Cell> selectable;
    boolean building;
    boolean hasMap;


    CellMap map;
    CellMap prevMap;
    Stack<CellMap> mapHist;

    Save save;
    Level l;

    public BuildingHandler(Level lvl) {
        setImage("img/bh.png");

        building = false;
        hasMap = false;
        l = lvl;
        lvl.addObject(new BuildMenu(1300, 0), 1300, 0);
        mapHist = new Stack<>();

        selectable = new LinkedList<>();

        selectable.add(new Road(1325, 25, 50, 50));
        selectable.add(new House(1325, 100));
        selectable.add(new Factory(1325, 175));
        selectable.add(new WindTurbine(1325, 250));
        selectable.add(new Farm(1325, 325));
        selectable.add(new Grass(1325, 400, 50, 50));



        selectable.add(new Save(1325, 500, 50, 50));

        lvl.addObject(selectable.poll(), 1325, 25);
        lvl.addObject(selectable.poll(), 1325, 100);
        lvl.addObject(selectable.poll(), 1325, 175);
        lvl.addObject(selectable.poll(), 1325, 250);
        lvl.addObject(selectable.poll(), 1325, 325);
        lvl.addObject(selectable.poll(), 1325, 400);

        save = (Save) selectable.poll();

        lvl.addObject(save, 1325, 500);
    }

    public void act() {

        if(Mayflower.isKeyPressed(Keyboard.KEY_SPACE))
            System.out.println(mapHist);

        if(!hasMap)
        {
            getMap();
            prevMap = map.copy();
//            System.out.println(prevMap);
            mapHist.push(prevMap);
        }


        if(mapHist.size() > 50)
        {
            mapHist.removeElementAt(0);
        }
        if(mapHist.isEmpty())
        {
            mapHist.push(new CellMap(0));
        }

        if(Mayflower.isKeyPressed(Keyboard.KEY_Z) && mapHist.size() > 0)
        {
            renderMap();
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

        if(Mayflower.mouseDown(this) && Mayflower.getMouseInfo().getX() >= 1300)
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
                else if (a instanceof WindTurbine)
                {
                    Cell b = new WindTurbine(Mayflower.getMouseInfo().getX(), Mayflower.getMouseInfo().getY());
                    setSelected(b);
                }
                else if (a instanceof Farm)
                {
                    Cell b = new Farm(Mayflower.getMouseInfo().getX(), Mayflower.getMouseInfo().getY());
                    setSelected(b);
                }
            }
            SelectedInfo();
        }

        refreshSelected();

        if(building)
        {
            save.updateSave(false);
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
                else if (selectable != null && a != null && selected instanceof Grass && a.getX() <= 1300)
                {
                    getWorld().addObject(selected, a.getX(), a.getY());
                    map.setCell(Mayflower.getMouseInfo().getX()/50, Mayflower.getMouseInfo().getY()/50, selected);
                    getWorld().removeObject(a);
                }
            }
            compareMaps();
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
        else if (selected instanceof WindTurbine)
        {
            selected = new WindTurbine(Mayflower.getMouseInfo().getX(), Mayflower.getMouseInfo().getY());
        }
        else if (selected instanceof Farm)
        {
            selected = new Farm(Mayflower.getMouseInfo().getX(), Mayflower.getMouseInfo().getY());
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
        else if (selected instanceof WindTurbine)
        {
            getWorld().showText("Wind turbine", 15, 1325, 500, Color.BLACK);
            getWorld().showText("Cost: 5", 15, 1325, 525, Color.BLACK);
        }
        else if (selected instanceof Farm)
        {
            getWorld().showText("Farm", 15, 1325, 500, Color.BLACK);
            getWorld().showText("Cost: 5", 15, 1325, 525, Color.BLACK);
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

    public void renderMap()
    {
        CellMap curr = map;
        CellMap newMap = mapHist.pop();
//        CellMap newMap = new CellMap(0);

        for(int i = 0; i < map.rows(); i++)
        {
            for(int j = 0; j < map.cols(); j++)
            {
                Cell newCell = newMap.getCell(i,j);
                if(newCell instanceof Grass)
                    newCell.setRotation(curr.getCell(i,j).getRotation());
                getWorld().removeObject(curr.getCell(i,j));
                getWorld().addObject(newCell, i * 50, j * 50);
            }
        }

        map = newMap;
        prevMap = map.copy();
    }

    public void compareMaps()
    {
        if(mapHist.size() < 1)
            return;
        CellMap prev = mapHist.peek();
        for(int i = 0; i < map.rows(); i++)
        {
            for(int j = 0; j < map.cols(); j++)
            {
                Cell prevCell = prev.getCell(i,j);
                Cell newCell = map.getCell(i,j);

                if(!prevCell.getClass().equals(newCell.getClass()))
                {
                    mapHist.push(prevMap);
                    prevMap = map.copy();
                    return;
                }
                else
                {
                    if(prevCell instanceof Grass)
                    {
                        if(((Grass) prevCell).isAvailable() != ((Grass) newCell).isAvailable())
                        {
                            mapHist.push(prevMap);
                            prevMap = map.copy();
                            return;
                        }
                    }
                }
            }
        }
    }

}