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
        setImage("resources/bh.png");

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

        lvl.addObject(save, 1337, 540);
    }

    public void act() {

        if(!hasMap)
        {
            getMap();
            prevMap = map.copy();
//            System.out.println(prevMap);
            mapHist.push(prevMap);
        }


        if(mapHist.size() > 5)
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
                    Cell b = new WindTurbine(Mayflower.getMouseInfo().getX()/50, Mayflower.getMouseInfo().getY()/50);
                    setSelected(b);
                }
                else if (a instanceof Farm)
                {
                    Cell b = new Farm(Mayflower.getMouseInfo().getX()/50, Mayflower.getMouseInfo().getY()/50);
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
            l = (Level) getWorld();
            for(Actor a : atMouse)
            {
                if(a instanceof BuildingHandler)
                    continue;
                int x = Mayflower.getMouseInfo().getX() / 50;
                int y = Mayflower.getMouseInfo().getY() / 50;
                if (a instanceof Grass && selected instanceof Road && a.getX() <= 1300)
                {
                    getWorld().addObject(selected, a.getX(), a.getY());
                    map.setCell(x, y, selected);
                    getWorld().removeObject(a);
                    Road r = (Road) selected;
                    l.setMoney(l.getMoney() - r.getCost());

                    r = (Road)(map.getCell(x, y));
                    l.renderAvailable(r);
                    r.updateImage();

                    List<Cell> roads = r.getNeighbors(l, r.getx(), r.gety());
                    for(int i = 0; i < roads.size(); i++)
                    {
                        if(roads.get(i) instanceof Road)
                        {
                            ((Road) roads.get(i)).updateImage();
                        }
                    }
                }
                else if(a instanceof Grass && a.getX() <= 1300 && !(selected instanceof Grass))
                {
                    Grass g = (Grass) a;
                    if (g.isAvailable()){
                        getWorld().addObject(selected, a.getX(), a.getY());
                        map.setCell(x, y, selected);
                        getWorld().removeObject(a);
                        Building b = (Building) selected;
                        l.setMoney(l.getMoney() - b.getCost());

                    }
                }
                else if (selected != null && a != null && selected instanceof Grass && a.getX() <= 1300)
                {
                    l.addObject(selected, a.getX(), a.getY());
                    selected.setx(((Cell) a).getx());
                    selected.sety(((Cell) a).gety());
                    map.setCell(x, y, selected);
                    boolean road = a instanceof Road;
                    l.removeObject(a);
                    if(road)
                        l.renderAvailable(selected);
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
            selected = new Road(Mayflower.getMouseInfo().getX() / 50, Mayflower.getMouseInfo().getY() / 50, 50, 50);
        }
        else if (selected instanceof House)
        {
            selected = new House(Mayflower.getMouseInfo().getX()/ 50, Mayflower.getMouseInfo().getY()/ 50);
        }
        else if (selected instanceof Factory)
        {
            selected = new Factory(Mayflower.getMouseInfo().getX()/ 50, Mayflower.getMouseInfo().getY()/ 50);
        }
        else if (selected instanceof Grass)
        {
            selected = new Grass(Mayflower.getMouseInfo().getX()/ 50, Mayflower.getMouseInfo().getY()/ 50, 50, 50);
        }
        else if (selected instanceof WindTurbine)
        {
            selected = new WindTurbine(Mayflower.getMouseInfo().getX()/ 50, Mayflower.getMouseInfo().getY()/ 50);
        }
        else if (selected instanceof Farm)
        {
            selected = new Farm(Mayflower.getMouseInfo().getX()/ 50, Mayflower.getMouseInfo().getY()/ 50);
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
                Cell currCell = map.getCell(i,j);

                if(newCell instanceof Grass && currCell instanceof Grass)
                    newCell.setRotation(curr.getCell(i,j).getRotation());
                if(currCell.getClass() == newCell.getClass())
                {
                    newCell.setImage(currCell.getImage());
                    newCell.setRotation(currCell.getRotation());
                    getWorld().removeObject(curr.getCell(i,j));
                    getWorld().addObject(newCell, i * 50, j * 50);
                }
                else if(!currCell.getClass().equals(newCell.getClass()))
                {
                    getWorld().removeObject(curr.getCell(i,j));
                    getWorld().addObject(newCell, i * 50, j * 50);
                }
                else
                {
                    if(currCell instanceof Grass)
                    {
                        if(((Grass) currCell).isAvailable() != ((Grass) newCell).isAvailable())
                        {
                            getWorld().removeObject(curr.getCell(i,j));
                            getWorld().addObject(newCell, i * 50, j * 50);
                        }
                    }
                }


            }
        }

        map = newMap;

        for(int i = 0; i < map.rows(); i++)
        {
            for(int j = 0; j < map.cols(); j++)
            {
                Cell c = map.getCell(i , j);
                if(c instanceof Grass)
                {
                    ((Grass) c).refresh();
                }
                if(c instanceof Road)
                {
                    l.renderAvailable(c);
                }
            }
        }
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