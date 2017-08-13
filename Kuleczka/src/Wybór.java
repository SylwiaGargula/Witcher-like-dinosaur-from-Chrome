import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * Created by Sylwia on 2016-03-13.
 */

public class Wybór extends JFrame implements ActionListener{


    BazaDanych baza;
    ArrayList<Opisy> Opis;
    JButton bGeralt,bTriss,bYen;
    JLabel lGeralt,lTriss,LYen;
    String login;
int opcja;

    public Wybór(String xlogin, int xopcja)
    {
opcja=xopcja;
        login=xlogin;


        setSize(800,600);
        setLocation(500,200);
        setTitle("Wybór postaci");

        setLayout(null);

        setVisible(true);

        setResizable(false);

        baza=new BazaDanych();

        try {
            Opis=baza.PobierzOpisy();
        } catch (SQLException e) {
            System.out.println("Blad pobierania opisow z bazy danych");
        }
        try {
            setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("wybor.bmp")))));
        } catch (IOException e) {
            e.printStackTrace();
        }


        bGeralt= new JButton("Wybierz Geralta");
        bGeralt.setBounds(300,200,150,20);
        add(bGeralt);
        bGeralt.addActionListener(this);


        bTriss= new JButton("Wybierz Triss");
        bTriss.setBounds(300,300,150,20);
        add(bTriss);
        bTriss.addActionListener(this);

        bYen= new JButton("Wybierz Yennefer");
        bYen.setBounds(300,400,150,20);
        add(bYen);
        bYen.addActionListener(this);


        String tekst = null,tekst1=null,tekst2=null;
        for(int i=0;i<Opis.size();i++)
        {
            if(Opis.get(i).imie.equals("Geralt"))
            {
            tekst= Opis.get(i).opis;
            }
            else if(Opis.get(i).imie.equals("Triss"))
            {
                tekst1= Opis.get(i).opis;
            }
            else if(Opis.get(i).imie.equals("Yennefer"))
            {
                tekst2= Opis.get(i).opis;
            }
        }

        lGeralt = new JLabel(tekst);
        lGeralt.setForeground(Color.WHITE);

        lGeralt.setBounds(10, 248, 500, 20);
        add(lGeralt);


        lTriss = new JLabel(tekst1);
        lTriss.setForeground(Color.WHITE);
        lTriss.setBounds(10, 350, 500, 20);
        add(lTriss);


        LYen = new JLabel(tekst2);
        LYen.setForeground(Color.WHITE);

        LYen.setBounds(10, 475, 500, 20);
        add(LYen);

    }

    public void paint(Graphics g)
    {


        super.paint(g);

        BufferedImage image=null;

        String postac1="geralt.png" ;
        Image bgpos1= new ImageIcon(postac1).getImage();
        g.drawImage(bgpos1,70,200,this);


        String postac2="triss.png" ;
        Image bgpos2= new ImageIcon(postac2).getImage();
        g.drawImage(bgpos2,70,300,this);


        String postac3="yen.png" ;
        Image bgpos3= new ImageIcon(postac3).getImage();
        g.drawImage(bgpos3,70,410,this);


    }

    @Override
    public void actionPerformed(ActionEvent f) {
        Object reakcja =f.getSource();


        if(reakcja==bGeralt)
        {
            if(opcja==JOptionPane.YES_OPTION) {
                GraSerwer okno = new GraSerwer("geralt.png", login,opcja);
                //tu tworze nowe okno gry
                okno.setVisible(true);
                setVisible(false);
                Thread watekGry=new Thread(okno);
                watekGry.start();

            }
            else
            {
                GraKlient okno = new GraKlient("geralt.png", login,opcja);
                okno.setVisible(true);
                setVisible(false);
                Thread watekGry=new Thread(okno);
                watekGry.start();

            }

        }

        else if(reakcja==bTriss)
        {
            if(opcja==JOptionPane.YES_OPTION) {
                GraSerwer okno = new GraSerwer("triss.png", login,opcja);
                okno.setVisible(true);
                setVisible(false);
                Thread watekGry=new Thread(okno);
                watekGry.start();

            }
            else
            {
                GraKlient okno = new GraKlient("triss.png", login,opcja);
                okno.setVisible(true);
                setVisible(false);
                Thread watekGry=new Thread(okno);
                watekGry.start();

            }

        }

        else if(reakcja==bYen)
        {
            if(opcja==JOptionPane.YES_OPTION) {
                GraSerwer okno = new GraSerwer("yen.png", login,opcja);
                okno.setVisible(true);
                setVisible(false);
                Thread watekGry=new Thread(okno);
                watekGry.start();

            }
            else
            {
                GraKlient okno = new GraKlient("yen.png", login,opcja);
                okno.setVisible(true);
                setVisible(false);
                Thread watekGry=new Thread(okno);
                watekGry.start();

            }
        }



    }




}
