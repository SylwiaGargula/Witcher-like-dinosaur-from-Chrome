import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Created by Sylwia on 2016-04-03.
 */
public class Kolizja extends JFrame implements ActionListener {

    JLabel jPonownie;
    JButton bTak, bNie;
    String login;
int opcja;

    public Kolizja(String x, int xopcja) {
        super("Kolizja");

opcja=xopcja;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 350);
        setLocation(500, 200);

        setLayout(null);
        login=x;

        setResizable(false);

        jPonownie=new JLabel("Nastąpiła kolizja, czy chcesz spróbować jeszcze raz?",JLabel.RIGHT);
        jPonownie.setBounds(2,70,320,30);
        add(jPonownie);

        bTak=new JButton("Tak");
        bTak.setBounds(10,150,60,50);
        add(bTak);
        bTak.addActionListener(this);

        bNie=new JButton("Nie");
        bNie.setBounds(200,150,60,50);
        add(bNie);
        bNie.addActionListener(this);


        try {
            Zapis();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void Zapis() throws FileNotFoundException {
        PrintWriter zapis = new PrintWriter("Przeszkody.txt");
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            int a = r.nextInt(3) + 1; //losowanie od 1 do 3
            zapis.println(a);
        }
        zapis.close();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object źródło =e.getSource();
        if(źródło==bTak){
            //uruchom
            Wybór nowa= new Wybór(login, opcja);

            nowa.setVisible(true);


            dispose();
        }
        else if(źródło==bNie)
        {
            Window menu=new Window();
            menu.setVisible(true);
            dispose();

        }
    }
}
