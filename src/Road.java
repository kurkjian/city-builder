import mayflower.*;

public class Road extends Cell {
    boolean active;
    int cost;
    public Road(int x, int y, int w, int h) {
        super(x,y, w, h);
        setImage("img/road.png");
        active = false;
        cost = 1;
    }

    public void act() {
        if(Mayflower.mouseHovered(this))
        {
            active = true;
            setImage("img/road-active.png");
            getWorld().showText("Road", 15, 1325, 600, Color.BLACK);
            getWorld().showText(" ", 15, 1320, 620, Color.BLACK);
            getWorld().showText(" ", 15, 1305, 640, Color.BLACK);
        }
        else if(active)
        {
            active = false;
            setImage("img/road.png");
        }
    }

    public int getCost()
    {
        return cost;
    }
}
