import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Sylwia on 2016-03-12.
 */


public class Goście extends JFrame {
    public Goście() {
        super("Księga Gości");
        setSize(1250, 800);
        setLocation(50, 50);

        setVisible(true);
        setResizable(false);
    }


    public void paint(Graphics g)
    {
        super.paint(g);
        File plik=new File("goscie.png");
        BufferedImage image=null;
        try
        {
            image= ImageIO.read(plik);


        }
        catch (IOException e) {


            System.err.println("Blad odczytu obrazka");


            File plik1=new File("blad.png");
            try {
                image= ImageIO.read(plik1);
            } catch (IOException e1) {
                System.err.println("Blad odczytu obrazka");
            }


        }

        g.drawImage(image,0,0,this);

    }

}