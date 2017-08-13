import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Sylwia on 2016-04-07.
 */
public class PostacGlowna extends Istota
{
    private String nazwa;
    private Image image;
    private int screenSize=800;
    public int punkt=0;
boolean kimjestem;





    public PostacGlowna(String nazwa_postaci, int x, int y,boolean xkimjestem)
    {
        super(x,y);
kimjestem=xkimjestem;

        this.nazwa=nazwa_postaci;
        this.image = new ImageIcon(this.nazwa).getImage();

        super.szerokosc=this.image.getWidth(null);
        super.wysokosc=this.image.getHeight(null);

    }

    public void moveOn(){
        if(y>700)
            this.y-=270;


    }
    public  void tick()
    {
        if(kimjestem==false){
        if(this.y<screenSize-image.getHeight(null))
            this.y += 5;
        }
        else if(kimjestem==true) {
            if(this.y<300-image.getHeight(null))
                this.y += 5;
        }

    }


    public void paint(Graphics g) {


        g.drawImage(this.image, x, y, null);
    }



}