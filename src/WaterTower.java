import mayflower.*;

public class WaterTower extends Building
{
    private int waterSupply;

    public WaterTower()
    {
        super(1,1,100);
        waterSupply =50;


    }

    public void act()
    {



    }
    public int getWaterSupply()
    {
        return waterSupply;
    }

}
