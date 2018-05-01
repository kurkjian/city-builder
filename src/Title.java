import mayflower.*;
import java.io.File;

public class Title extends World {

    Button play;
    File save;
    Button load;

    public Title() throws Exception {
        setBackground("img/title.png");
        showText("City Builder v0.1", 48,500,150);
        play = new Button("img/play.png");
        addObject(play,500,450);

        save = new File("save.txt");
        if(save.exists())
        {
            load = new Button("img/load.png");
            addObject(load, 500, 600);
        }
    }

    public void act() {
        if(Mayflower.mouseClicked(play))
        {
            World level = new Level();
            Mayflower.setWorld(level);
        }

        System.out.println(save.exists());
    }
}
