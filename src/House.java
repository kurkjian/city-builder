import mayflower.*;



public class House extends Building
{
    private int peopleInHouse;
    private Timer timer;
    boolean active;

    public House ()
    {
        super(1,1, 5);

        setImage("img/House.png");
        peopleInHouse=1;
        timer = new Timer(999999999);
        active = false;
    }

    public House(int x, int y)
    {
        super(x, y, 5);
        setImage("img/House.png");
        peopleInHouse=1;
        timer = new Timer(999999999);
        active = false;
    }
    public void act()
    {
        if(Mayflower.mouseHovered(this))
        {
            active = true;
            setImage("img/house-active.png");
            getWorld().showText("House", 15, 1325, 600, Color.BLACK);
            getWorld().showText("People: " + peopleInHouse, 15, 1325, 625, Color.BLACK);
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
        double money = 0.25;
        return money* peopleInHouse;
    }

    public int getPeople()
    {
        return peopleInHouse;
    }

    public String toString() {
        return "2";
    }

}