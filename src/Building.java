import mayflower.*;

public class Building extends Cell
{
    private int cost;

    public Building(int x, int y, int cost)
    {
        super(x, y, 50, 50);
        this.cost = cost;
    }

    public void act()
    {

    }

    public int getCost()
    {
        return cost;
    }

    public int getPowerCost() { return cost;}
}