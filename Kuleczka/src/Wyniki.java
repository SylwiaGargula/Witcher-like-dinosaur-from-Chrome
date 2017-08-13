import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Sylwia on 2016-03-12.
 */


public class Wyniki extends JFrame {
    public Wyniki() {


        super("Wyniki");

        BazaDanych baza=new BazaDanych();

        setSize(450, 800);
        setLocation(50, 50);
        final JTextArea textArea = new JTextArea(10, 20);
        JScrollPane scroll = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        String napis=baza.PobierzWyniki();
        textArea.setText(napis);

        textArea.setFont(new Font("Helvetica",Font.ITALIC, 25));
        textArea.setEditable(false);
        add(scroll);

        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

       // try {
            //setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("wyniki.jpg")))));
        //} catch (IOException e) {
          // e.printStackTrace();
       // }
    }

    /*public void paint(Graphics g) {
        super.paint(g);
        File plik = new File("wyniki.jpg");
        BufferedImage image = null;
        try {
            image = ImageIO.read(plik);


        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
        }

        g.drawImage(image, 0, 0, this);

    }

*/
}
