import mayflower.*;
import java.awt.Image;
import java.awt.Graphics;

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
}