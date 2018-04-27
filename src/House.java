import mayflower.*;



public class House extends Building
{
    private int peopleInHouse;
    private Timer timer;

    public House ()
    {
        super(1,1, 5);

        setImage("img/House.png");
        peopleInHouse=1;
        timer = new Timer(30000);
    }
    public void act()
    {
        if(peopleInHouse<4)
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

}