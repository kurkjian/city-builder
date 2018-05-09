import mayflower.*;

public class House extends Building
{
    private int peopleInHouse;
    private Timer timer;
    boolean active;
    boolean working;

    public House ()
    {
        super(1,1, 5);

        setImage("img/House.png");
        peopleInHouse=1;
        timer = new Timer(999999999);
        active = false;
        working = false;
    }

    public House(int x, int y)
    {
        super(x, y, 5);
        setImage("img/House.png");
        peopleInHouse=1;
        timer = new Timer(999999999);
        active = false;
        working = false;
    }
    public void act()
    {
        if(Mayflower.mouseHovered(this))
        {
            active = true;
            setImage("img/house-active.png");
            getWorld().showText("House", 15, 1325, 600, Color.BLACK);
            getWorld().showText("People: " + peopleInHouse, 15, 1320, 620, Color.BLACK);
            if (working)
            {
                getWorld().showText("Working", 15, 1325, 640, Color.BLACK);
            }
            else
            {
                getWorld().showText("Not working", 15, 1305, 640, Color.BLACK);
            }
        }
        else if(active)
        {
            active = false;
            setImage("img/house.png");
        }
        if(peopleInHouse<8)
        {
            if(timer.isDone())
            {
                peopleInHouse++;
                timer.reset();
            }
        }

    }
    public double taxReturn()
    {
        double money = 0.5;
        return money* peopleInHouse;
    }

    public int getPeople()
    {
        return peopleInHouse;
    }

    public void setWorking(boolean b) {working = b;}

    public boolean isWorking()
    {
        return working;
    }

}