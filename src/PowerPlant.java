import mayflower.*;

public class PowerPlant extends Building
{
    private int waterCost;
    private int electricProduction;
    public PowerPlant()
    {
        super(1,1,50);
        waterCost=1;
        electricProduction=50;
    }

    public void act()
    {

    }
    public int getPowerPlantWaterCost()
    {
        return waterCost;
    }
    public int getElectricProduction()
    {
        return electricProduction;
    }

}
