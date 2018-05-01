import mayflower.*;


public class Factory extends Building
{
    private int waterCost;
    private int electricCost;

    public Factory()
    {
        super(2,1, 10);
        setImage("img/factory 1.png");
        waterCost =4;
        electricCost=10;
    }

    public Factory(int x, int y)
    {
        super(x,y,10);
        setImage("img/factory 1.png");
    }

    public void act()
    {

    }

    public String toString()
    {
        return "3";
    }

    public int getFactoryWaterCost()
    {
        return waterCost;
    }
    public int getFactoryElectricCost()
    {
        return electricCost;
    }
}