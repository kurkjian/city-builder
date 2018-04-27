import mayflower.*;

public class Title extends World {

    Button play;

    public Title() {
        setBackground("img/title.png");
        showText("City Builder v0.1", 48,500,150);
        play = new Button("img/play.png");
        addObject(play,500,450);
    }

    public void act() {
        if(Mayflower.mouseClicked(play))
        {
            World level = new Level();
            Mayflower.setWorld(level);
        }
    }
}
