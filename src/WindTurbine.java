import mayflower.*;

public class WindTurbine extends Building
{
    boolean active;

    public WindTurbine(int x, int y)
    {
        super(x, y, 5);
        setImage("resources/wind turbine.png");
        active = false;
    }

    public void act() {
        if (Mayflower.mouseHovered(this)) {
            active = true;
            setImage("resources/wind turbine-active.png");
            getWorld().showText("Wind turbine", 15, 1325, 600, Color.BLACK);
            getWorld().showText(" ", 15, 1320, 620, Color.BLACK);
            getWorld().showText(" ", 15, 1325, 640, Color.BLACK);
        } else if (active) {
            active = false;
            setImage("resources/wind turbine.png");
        }
    }

    public String toString()
    {
        return "4";
    }
}