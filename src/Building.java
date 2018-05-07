import mayflower.*;

public class Building extends Cell
{
    private int length;
    private int width;
    private int cost;

    public Building(int x, int y, int cost)
    {
        length = x;
        width = y;
        this.cost =cost;
    }

    public void act()
    {

    }

    public int getCost()
    {
        return cost;
    }
}