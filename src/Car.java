import mayflower.*;

import java.util.List;

public class Car extends Actor
{

    public Car()
    {

    }


    public void act()
    {

    }


    public void carNavigation()
    {
        List<Factory> factoryList =  getWorld().getObjects(Factory.class);
        int factoryIndex = (int)(Math.random()*factoryList.size());
        Factory randomFactory = factoryList.get(factoryIndex);


      List<Road> roadList = getWorld().getObjects(Road.class);

    }
    public void moveTo(Object road, Object car, int xCord, int yCord)
    {

    }




}
