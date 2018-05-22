import mayflower.*;

public class Grass extends Cell {

    private boolean active;
    private boolean available;

    public Grass(int x, int y, int w, int h){
        super(x,y,w,h);
        setImage("resources/grass.png");
        setRotation((int)(Math.random()*4)*90);
        active = false;
        Mayflower.setMouseOffset(0,0);
        available = false;
    }

    public void act() {
        if(Mayflower.mouseHovered(this))
        {
            active = true;
            setImage("resources/grass-active.png");
            getWorld().showText("Grass", 15, 1325, 600, Color.BLACK);
            getWorld().showText(" ", 15, 1320, 620, Color.BLACK);
            getWorld().showText(" ", 15, 1305, 640, Color.BLACK);
        }
        else if(active)
        {
            active = false;
            if(available)
                setImage("resources/grass-open.png");
            else
                setImage("resources/grass.png");
        }
    }

    public void setAvailable() {
        available = true;
        setImage("resources/grass-open.png");
    }

    public void setUnavailable() {
        available = false;
        setImage("resources/grass.png");
    }

    public void refresh()
    {
        if(available)
            setImage("resources/grass-open.png");
        else
            setImage("resources/grass.png");
    }

    public boolean isAvailable(){
        return available;
    }

    public String toString()
    {
        if(available)
            return "-1";
        return "0";
    }
}