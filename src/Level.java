import mayflower.Color;
import mayflower.World;
import mayflower.*;

import java.awt.*;


public class Level extends World {

    private CellMap map;
    private BuildingHandler bh;
    int food;
    int population;
    int power;
    double money;
    int jobs;
    Timer timer;
    boolean gameOver;

    public Level() {
        map = new CellMap(26,16);
        for(int i = 0; i < map.rows(); i++)
        {
            for(int j = 0; j < map.cols(); j++)
            {
                Grass g = new Grass(i,j,50,50);
                addObject(g,i*50,j*50);
                map.setCell(i,j,g);
            }
        }

        timer = new Timer(999999999);
        food = 0;
        population = 0;
        power = 0;
        money = 500;
        jobs = 0;

        gameOver = false;

        bh = new BuildingHandler(this);
        addObject(bh,0,0);
    }

    public Level(CellMap m) {
        map = m;
        for(int i = 0; i < map.rows(); i++)
        {
            for(int j = 0; j < map.cols(); j++)
            {
                Cell c = map.getCell(i,j);
                addObject(c,i*50,j*50);
            }
        }

        for(int i = 0; i < map.rows(); i++)
        {
            for(int j = 0; j < map.cols(); j++)
            {
                Cell c = map.getCell(i,j);
                if(c instanceof Road)
                {
                    ((Road) c).updateImage();
                }
            }
        }

        timer = new Timer(999999999);
        food = 0;
        population = 0;
        power = 0;
        money = 500;
        jobs = 0;

        gameOver = false;

        bh = new BuildingHandler(this);
        addObject(bh,0,0);
    }

    public void act()
    {
        if (!gameOver) {
            if (bh.updateMap() != null)
                map = bh.updateMap();
            printInfo();
            if (timer.isDone()) {
                updateInfo();
                timer.reset();
            }
            isGameOver();
        }
        else
        {
            addObject(new GameOver(), 0, 0);
        }

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

    public void isGameOver()
    {
        if (money < -20000)
        {
            gameOver = true;
        }
    }

    public void renderAvailable(Cell cell)
    {
        int x = cell.getX() / 50;
        int y = cell.getY() / 50;

        if(cell instanceof Grass)
        {
            ((Grass) cell).setUnavailable();
        }

        for (int k = 0; k < 12; k++) {
            Cell c;

            if (k < 3) {
                c = map.getCell(x, y - k - 1);
            } else if (k < 6) {
                c = map.getCell(x, y + k + 1 - 3);
            } else if (k < 9) {
                c = map.getCell(x - 1 - k + 6, y);
            } else {
                c = map.getCell(x + 1 + k - 9, y);
            }

            if (c instanceof Grass) {
                if(cell instanceof Road)
                {
                    Grass g = (Grass) c;
                    g.setAvailable();
                }
                else
                {
                    Grass g = (Grass) c;
                    g.setUnavailable();
                }
            }
            if(cell instanceof Grass)
            {
                if(c instanceof Road)
                {
                    ((Grass) cell).setAvailable();
                }
            }

        }
    }

    public void updateInfo()
    {
        int pop = 0;
        int fo = 0;
        int pow = 0;
        int jo = 0;
        for (int row = 0; row < map.rows(); row++) {
            for (int col = 0; col < map.cols(); col++) {

                Cell c = map.getCell(row,col);

                if (c instanceof Factory) {

                    jo = jo + 16;
                    pow = pow - 2;
                    setMoney(getMoney() - 5);
                }
                else if (c instanceof WindTurbine)
                {
                    pow = pow + 10;
                }
                else if (c instanceof Farm)
                {
                    fo = fo + 32;
                }
                else if (c instanceof Grass) {
                    Grass g = (Grass) map.getCell(row, col);
                    if (g.isAvailable()) {
//                        g.setUnavailable();
                        map.setCell(row, col, g);
                    }
                }

                if (c instanceof House)
                {
                    House h = (House) c;
                    pop = pop + h.getPeople();
                    jo = jo - h.getPeople();
                    pow = pow - 1;
                    fo = fo - (1 * h.getPeople());
                    if (jo >= 0 && pow >= 0 && fo >= 0) {
                        h.setWorking(true);
                        map.setCell(row, col, h);
                    } else {
                        h.setWorking(false);
                        map.setCell(row, col, h);
                    }
                    if (h.isWorking()) {
                        setMoney(getMoney() + h.taxReturn());
                    }
                    else
                    {
                        setMoney(getMoney() - h.taxReturn());
                    }
                }
            }
        }

        population = pop;
        food = fo;
        power = pow;
        jobs = jo;
    }

    public void printInfo()
    {
        showText("Money: " + money, 12, 1305, 675, Color.BLACK);
        showText("Population: " + population, 12, 1305, 700, Color.BLACK);
        showText("Open Jobs: " + jobs, 12, 1305, 725, Color.BLACK);
        showText("Food: " + food, 12, 1305, 750, Color.BLACK);
        showText("Power: " + power, 12, 1305, 775, Color.BLACK);
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
                    if (h.isWorking()) {
                        setMoney(getMoney() + h.taxReturn());
                    }
                    else
                    {
                        setMoney(getMoney() - h.taxReturn());
                    }
                }
                if (map.getCell(r, c) instanceof Factory)
                {
                    setMoney(getMoney() - 5);
                }
            }
        }
    }

    public CellMap getMap() {
        return map;
    }
}