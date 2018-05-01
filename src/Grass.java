import mayflower.*;

public class Grass extends Cell {

    private boolean active;
    private boolean available;

    public Grass(int x, int y, int w, int h){
        super(x,y,w,h);
        setImage("img/grass.png");
        setRotation((int)(Math.random()*4)*90);
        active = false;
        Mayflower.setMouseOffset(0,0);
        available = false;
    }

    public void act() {
        if(Mayflower.mouseHovered(this))
        {
            active = true;
            setImage("img/grass-active.png");
        }
        else if(active)
        {
            active = false;
            if(available)
                setImage("img/grass-open.png");
            else
                setImage("img/grass.png");
        }
    }

    public void setAvailable() {
        available = true;
        setImage("img/grass-open.png");
    }

    public String toString()
    {
        return "0";
    }
}
