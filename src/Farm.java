import mayflower.*;

public class Farm extends Building {

    boolean active;

    public Farm(int x, int y)
    {
        super(x, y, 5);
        setImage("resources/farm.png");
        active = false;
    }

    public void act()
    {
        if (Mayflower.mouseHovered(this)) {
            active = true;
            setImage("resources/farm-active.png");
            getWorld().showText("Farm", 15, 1325, 600, Color.BLACK);
            getWorld().showText(" ", 15, 1320, 620, Color.BLACK);
            getWorld().showText(" ", 15, 1325, 640, Color.BLACK);
        } else if (active) {
            active = false;
            setImage("resources/farm.png");
        }
    }

    public String toString()
    {
        return "5";
    }
}