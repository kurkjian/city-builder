import mayflower.*;

import java.util.List;

public class Road extends Cell {
    boolean active;
    int cost;

    Road top;
    Road right;
    Road bottom;
    Road left;

    public Road(int x, int y, int w, int h) {
        super(x,y, w, h);
        setImage("img/road.png");
        active = false;
        cost = 1;

        top = null;
        right = null;
        bottom = null;
        left = null;
    }

    public void act() {
        if(Mayflower.mouseHovered(this))
        {
            active = true;
//            setImage("img/road-active.png");
            getWorld().showText("Road", 15, 1325, 600, Color.BLACK);
            getWorld().showText(" ", 15, 1320, 620, Color.BLACK);
            getWorld().showText(" ", 15, 1305, 640, Color.BLACK);
        }
        else if(active)
        {
            active = false;
//            setImage("img/road.png");
        }
    }

    public int getCost()
    {
        return cost;
    }

    public String toString()
    {
        return "1";
    }

    public void updateImage()
    {
        List<Cell> neighbors = getNeighbors((Level) getWorld(), this.getx(), this.gety());
        for(int i = 0; i < neighbors.size(); i++)
        {
            if(neighbors.get(i) instanceof Road)
            {

                Road r = (Road) neighbors.get(i);
                if(r.getx() == this.getx())
                {
                    if(r.gety() - 1 == this.gety())
                    {
                        bottom = r;
                    }
                    else
                    {
                        top = r;
                    }
                }
                else if(r.gety() == this.gety())
                {
                    if(r.getx() - 1 == this.getx())
                    {
                        right = r;
                    }
                    else
                    {
                        left = r;
                    }
                }
            }
        }
        int count = 0;
        if(top != null)
            count++;
        if(right != null)
            count++;
        if(bottom != null)
            count++;
        if(left != null)
            count++;

        if(count <= 1)
        {
            setImage("img/road-straight.png");

            if(count == 1)
            {
                if(right != null || left != null)
                {
                    setRotation(90);
                }
                else
                    setRotation(0);
            }
        }
        else if(count == 2)
        {
            setImage("img/road-corner.png");

            if(top != null && right != null)
            {
                setRotation(270);
            }
            else if(right != null && bottom != null)
            {
                setRotation(0);
            }
            else if(top != null && left != null)
            {
                setRotation(180);
            }
            else if(bottom != null && left != null)
            {
                setRotation(90);
            }

            if(top != null && bottom != null)
            {
                setImage("img/road-straight.png");
                setRotation(0);
            }
            else if(right != null && left != null)
            {
                setImage("img/road-straight.png");
                setRotation(90);
            }
        }
        else if(count == 3)
        {
            setImage("img/road-t.png");

            if(top != null && bottom != null && left != null)
            {
                setRotation(180);
            }
            else if(top != null && bottom != null && right != null)
            {
                setRotation(0);
            }
            else if(top != null && left != null && right != null)
            {
                setRotation(270);
            }
            else if(bottom != null && left != null && right != null)
            {
                setRotation(90);
            }
        }
        else
        {
            setImage("img/road-quad.png");
        }

    }
}