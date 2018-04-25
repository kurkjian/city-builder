import mayflower.*;

public class PowerPlant
{
    private int waterCost;
    private int electricProduction;
    public PowerPlant()
    {
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
