import mayflower.Color;
import mayflower.World;
import mayflower.*;


public class Level extends World {

    private CellMap map;
    private BuildingHandler bh;
    int food;
    int population;
    int power;
    double money;
    Timer timer;

    public Level() {
        map = new CellMap(26,16);
        for(int i = 0; i < map.rows(); i++)
        {
            for(int j = 0; j < map.cols(); j++)
            {
                Grass g = new Grass(i*50,j*50,50,50);
                addObject(g,i*50,j*50);
                map.setCell(i,j,g);
            }
        }

        timer = new Timer(999999999);
        food = 0;
        population = 0;
        power = 0;
        money = 500;

        bh = new BuildingHandler(this);
        addObject(bh,0,0);
    }

    public Level(CellMap m) {
        map = m;
        for(int i = 0; i < map.rows(); i++)
        {
            for(int j = 0; j < map.cols(); j++)
            {
                Grass g = new Grass(i*50,j*50,50,50);
                addObject(g,i*50,j*50);
            }
        }

        timer = new Timer(999999999);
        food = 0;
        population = 0;
        power = 0;
        money = 500;

        bh = new BuildingHandler(this);
        addObject(bh,0,0);
    }

    public void act()
    {
        if(bh.updateMap() != null)
            map = bh.updateMap();
        updateInfo();
        printInfo();
        if (timer.isDone())
        {
            taxes();
            timer.reset();
        }

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

    public int getPopulation()
    {
        return population;
    }
    public int getFood()
    {
        return food;
    }
    public int getPower()
    {
        return power;
    }
    public double getMoney()
    {
        return money;
    }

    public void setPopulation(int n)
    {
        population = n;
    }
    public void setFood(int n)
    {
        food = n;
    }
    public void setPower(int n)
    {
        power = n;
    }
    public void setMoney(double n)
    {
        money = n;
    }

    public void updateInfo()
    {
        int pop = 0;
        int fo = 0;
        int pow = 0;
        for (int r = 0; r < map.rows(); r++)
        {
            for (int c = 0; c < map.cols(); c++)
            {
                if (map.getCell(r, c) instanceof House)
                {
                    House h = (House) map.getCell(r, c);
                    pop = pop + h.getPeople();
                }
            }
        }
        population = pop;
        food = fo;
        power = pow;
    }

    public void printInfo()
    {
        showText("Money: " + money, 12, 1305, 675, Color.BLACK);
        showText("Population: " + population, 12, 1305, 700, Color.BLACK);
        showText("Food: " + food, 12, 1305, 725, Color.BLACK);
        showText("Power: " + power, 12, 1305, 750, Color.BLACK);
    }

    public void taxes()
    {
        for (int r = 0; r < map.rows(); r++)
        {
            for (int c = 0; c < map.cols(); c++)
            {
                if (map.getCell(r, c) instanceof House)
                {
                    House h = (House) map.getCell(r, c);
                    setMoney(getMoney() + h.taxReturn());
                }
            }
        }
    }

    public CellMap getMap() {
        return map;
    }
}