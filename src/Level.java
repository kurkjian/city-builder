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
    int jobs;
    Timer timer;
    boolean gameOver;

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
        money = 2000;
        jobs = 0;

        gameOver = false;

        bh = new BuildingHandler(this);
        addObject(bh,0,0);
    }

    public void act()
    {
        if (gameOver == false) {
            if (bh.updateMap() != null)
                map = bh.updateMap();
            updateInfo();
            printInfo();
            if (timer.isDone()) {
                taxes();
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

    public void updateInfo()
    {
        int pop = 0;
        int fo = 0;
        int pow = 0;
        int jo = 0;
        for (int row = 0; row < map.rows(); row++) {
            for (int col = 0; col < map.cols(); col++) {
                if (map.getCell(row, col) instanceof Factory) {
                    jo = jo + 16;
                    pow = pow - 2;
                }
            }
        }
        for (int row = 0; row < map.rows(); row++)
        {
            for (int col = 0; col < map.cols(); col++)
            {
                if (map.getCell(row, col) instanceof WindTurbine)
                {
                    pow = pow + 10;
                }
            }
        }
        for (int row = 0; row < map.rows(); row++)
        {
            for (int col = 0; col < map.cols(); col++)
            {
                if (map.getCell(row, col) instanceof Farm)
                {
                    fo = fo + 32;
                }
            }
        }
        for (int row = 0; row < map.rows(); row++)
        {
            for (int col = 0; col < map.cols(); col++)
            {
                if (map.getCell(row, col) instanceof House)
                {
                    House h = (House) map.getCell(row, col);
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
                }
            }
        }
        for (int row = 0; row < map.rows(); row++)
        {
            for (int col = 0; col < map.cols(); col++)
            {
                if (map.getCell(row, col) instanceof Road)
                {
                    Road a = (Road) map.getCell(row, col);
                    for (int i = 0; i < 12; i++) {
                        Cell c;

                        if (i < 3) {
                            c = map.getCell(a.getX() / 50, a.getY() / 50 - i - 1);
                        } else if (i < 6) {
                            c = map.getCell(a.getX() / 50, a.getY() / 50 + i + 1 - 3);
                        } else if (i < 9) {
                            c = map.getCell(a.getX() / 50 - 1 - i + 6, a.getY() / 50);
                        } else {
                            c = map.getCell(a.getX() / 50 + 1 + i - 9, a.getY() / 50);
                        }

                        if (c instanceof Grass) {
                            Grass g = (Grass) c;
                            g.setAvailable();
                        }
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
