import mayflower.*;



public class House extends Building
{
    private int peopleInHouse;
    private Timer timer;
    private int  waterCost;
    private int electricCost;

    public House ()
    {
        super(1,1, 5);
        waterCost=2;
        electricCost = 2;

        setImage("img/House.png");
        peopleInHouse=1;
        timer = new Timer(30000);
    }

    public House(int x, int y) {
        super(x,y,5);
        setImage("img/House.png");
        peopleInHouse = 1;
        timer = new Timer(30000);
    }
    public void act()
    {
        if(peopleInHouse<4)
        {
            if(timer.isDone())
            {
                peopleInHouse++;
                timer.reset();
            }
        }
        taxReturn();

    }
    public double taxReturn()
    {
        double money = 0.5;
        return money* peopleInHouse;
    }

    public String toString() {
        return "2";
    }

    public int getHouseWaterCost()
    {
        return waterCost;
    }
    public int getHouseElectricCost()
    {
        return electricCost;
    }

}