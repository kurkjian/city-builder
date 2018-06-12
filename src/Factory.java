import mayflower.*;

public class Factory extends Building
{
    boolean active;
    public Factory()
    {
        super(2,1, 10);
        setImage("resources/Factory 1.png");
        active = false;
    }

    public Factory(int x, int y)
    {
        super(x,y, 10);
        setImage("resources/Factory 1.png");
        active = false;
    }

    public void act()
    {
        if(Mayflower.mouseHovered(this))
        {
            active = true;
            setImage("resources/factory 1-active.png");
            getWorld().showText("Factory", 15, 1325, 600, Color.BLACK);
            getWorld().showText(" ", 15, 1320, 620, Color.BLACK);
            getWorld().showText(" ", 15, 1305, 640, Color.BLACK);
            getWorld().showText(" ", 15, 1325, 640, Color.BLACK);
        }
        else if(active)
        {
            active = false;
            setImage("resources/factory 1.png");
        }
    }

    public String toString()
    {
        return "3";
    }
}