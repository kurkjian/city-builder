import mayflower.*;

public class Grass extends Actor {

    public Grass(){
        setImage("img/grass.png");
        setRotation((int)(Math.random()*4)*90);
    }

    public void act() {
        if(Mayflower.mouseClicked(this))
        {
            setImage("img/road.png");
        }
    }
}
