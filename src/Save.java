import mayflower.*;
import java.io.*;

public class Save extends Cell {

    CellMap map;
    boolean saved;

    public Save(int x, int y, int w, int h) {
        super(x,y,w,h);
        setImage("img/save-yes.png");
        saved = true;
    }

    public void act()
    {
        if(Mayflower.mouseClicked(this))
        {
            saveGame();
            updateSave(true);
        }
    }

    public void saveGame()
    {
        map = getWorld().getObjects(BuildingHandler.class).get(0).updateMap();
        try
        {
            FileWriter fw = new FileWriter("save.txt",false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw, true);

            Cell[][] grid = map.getMap();
            for(int i = 0; i < grid.length; i++)
            {
                for(int j = 0; j < grid[0].length; j++)
                {
                    out.printf("%2s ", grid[i][j]);
                }
                out.println();
            }


            out.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void updateSave(boolean b)
    {
        saved = b;
        if(saved)
            setImage("img/save-yes.png");
        else
            setImage("img/save-no.png");
    }

}
