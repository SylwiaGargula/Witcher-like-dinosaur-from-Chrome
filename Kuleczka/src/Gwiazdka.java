import javax.swing.*;
import java.awt.*;

/**
 * Created by Sylwia on 2016-05-21.
 */
public class Gwiazdka extends Istota{
    private Image image;

    public Gwiazdka(int x, int y) {
        super(x, y);

        this.image = new ImageIcon("gwiazdka.gif").getImage();

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