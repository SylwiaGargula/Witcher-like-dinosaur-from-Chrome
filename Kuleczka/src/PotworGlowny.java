import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Sylwia on 2016-04-07.
 */
public class PotworGlowny extends Istota
{
    private String nazwa;
    private Image image;


    public PotworGlowny(int q, int x, int y) {
        super(x, y);

        switch (q) {

            case 1:
                nazwa = "utopiec.png";
                break;
            case 2:
                nazwa = "bies.png";
                break;
            case 3:
                nazwa = "poludnica.png";
                break;
        }
        this.image = new ImageIcon(this.nazwa).getImage();

        super.szerokosc = this.image.getWidth(null);
        super.wysokosc = this.image.getHeight(null);

    }

    public  void tick()
    {
        if(this.x>0)
            this.x-=10;

    }


    public void paint(Graphics g) {

        if(x<=0)
            y=2000;
        g.drawImage(this.image, x, y, null);

    }
}