import mayflower.*;
import java.awt.Image;
import java.awt.Graphics;
import java.util.*;

public class Cell extends Actor
{

    private int x;
    private int y;
    private int width;
    private int height;
    private Image image;

    public Cell()
    {

    }
    public Cell(int a, int b, int w, int h) {
        x = a;
        y = b;
        width = w;
        height = h;
    }

    public void act()
    {

    }

    public int getx(){
        return x;
    }

    public int gety(){
        return y;
    }

    public void draw(Graphics window){
        window.drawImage(image, x, y, width, height, null);
    }

    public List<Cell> getNeighbors(Level level, int x, int y)
    {
        Cell[][] map = level.getMap().getMap();
        List<Cell> ret = new ArrayList<>();

        if(x - 1 > -1)
        {
            ret.add(map[x-1][y]);
        }
        if(x + 1 < map.length)
        {
            ret.add(map[x+1][y]);
        }
        if(y - 1 > -1)
        {
            ret.add(map[x][y-1]);
        }
        if(y + 1 < map[0].length)
        {
            ret.add(map[x][y+1]);
        }

        return ret;
    }
}