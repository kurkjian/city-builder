import mayflower.*;
import java.io.*;

public class Save extends Cell {

    CellMap map;

    public Save(int x, int y, int w, int h) {
        super(x,y,w,h);
        setImage("img/save.png");
    }

    public void act()
    {
        if(Mayflower.mouseClicked(this))
        {
            saveGame();
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
            for(int i = 0; i < grid[0].length; i++)
            {
                for(int j = grid.length - 1; j >= 0; j--)
                {
                    out.printf("%1s ", grid[j][i]);
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

}
