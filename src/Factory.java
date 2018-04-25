import mayflower.*;


public class Factory extends Building
{
    private int waterCost;
    private int electricCost;
    public Factory()
    {
        super(2,1, 10);
        // setImage("img/")
        waterCost =4;
        electricCost=10;
    }

    public void act()
    {

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