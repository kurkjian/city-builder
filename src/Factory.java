import mayflower.*;


public class Factory extends Building
{
    public Factory()
    {
        super(2,1, 10);
        setImage("img/Factory 1.png");
    }

    public Factory(int x, int y)
    {
        super(x,y, 10);
        setImage("img/Factory 1.png");
    }

    public void act()
    {

    }
}