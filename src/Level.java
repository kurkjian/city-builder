import mayflower.World;

public class Level extends World {

    public Level() {
        for(int i = 0; i < 2650/50+50; i++)
        {
            for(int j = 0; j < 1440/50+50; j++)
            {
                Grass g = new Grass();
                addObject(g,i*50,j*50);
            }
        }
    }

    public void act()
    {

    }
}
