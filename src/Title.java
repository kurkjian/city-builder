import mayflower.*;
import java.io.File;
import java.util.Scanner;

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

        if(Mayflower.mouseClicked(load))
        {
            CellMap map = new CellMap(26,16);
            try {
                Scanner s = new Scanner(new File("save.txt"));
                for(int i = 0; i < 26; i++)
                {
                    for(int j = 0; j < 16; j++)
                    {
                        int tile = s.nextInt();
                        map.setCell(i,j, getTile(tile, i , j));
                    }
                    s.nextLine();
                }
            }
            catch (Exception e)
            {
                World level = new Level();
                Mayflower.setWorld(level);
            }


        }
    }

    public Cell getTile(int id, int i, int j)
    {
        if(id == 0)
            return new Grass(i * 50, j * 50, 50,50);
        if(id == 1)

    }
}
