
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.lang.InterruptedException;
import java.io.IOException;

public class Window extends JFrame implements ActionListener{

    JButton bRozpocznij,bWyniki,bWyjście, bKsięgaGości;
    JPanel panel,panel2;
    int opcja;
    Klient osoba;
    public Window()  {

        super("Kuleczka");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1250, 800);
        setLocation(50, 50);

        //    setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("menu.jpg")))));

        Radio radio = null;
        try {
            radio = new Radio();
        } catch (InterruptedException e1) {
            System.out.println("BŁĄD RADIO");
        } catch (UnsupportedAudioFileException e1) {
            System.out.println("BŁĄD RADIO");
        } catch (IOException e1) {
            System.out.println("BŁĄD RADIO");
        }

        setLayout(null);


        opcja = JOptionPane.showConfirmDialog(null, "Serwer(Y) KLIENT(N)", "ŁĄCZENIE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcja == JOptionPane.NO_OPTION) {

            String serverIP = JOptionPane.showInputDialog(this, "Podaj adres IP servera", "IP", JOptionPane.INFORMATION_MESSAGE);
            osoba = new Klient(serverIP);
        }
        panel2 = new JPanel();
        panel2.setSize(new Dimension(1400, 900));
        panel2.setLayout(null);
        add(panel2);

        panel = new JPanel();
        panel.setSize(new Dimension(1400, 900));
        add(panel);


        bRozpocznij = new JButton("Rozpocznij grę");
        bRozpocznij.setBounds(550, 150, 200, 50);
        panel2.add(bRozpocznij);
        bRozpocznij.addActionListener(this);


        bWyniki = new JButton("Wyniki");
        bWyniki.setBounds(550, 250, 200, 50);
        panel2.add(bWyniki);
        bWyniki.addActionListener(this);

        bKsięgaGości = new JButton("Gadu");
        bKsięgaGości.setBounds(550, 350, 200, 50);
        panel2.add(bKsięgaGości);
        bKsięgaGości.addActionListener(this);

        bWyjście = new JButton("Wyjście");
        bWyjście.setBounds(550, 450, 200, 50);
        panel2.add(bWyjście);
        bWyjście.addActionListener(this);


        setResizable(false);
        setVisible(true);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        File plik=new File("menu.jpg");
        BufferedImage image=null;
        try
        {
            image= ImageIO.read(plik);
        }
        catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
        }

        g.drawImage(image,0,0,this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object źródło =e.getSource();
        if(źródło==bWyjście){
            dispose();
        }

        else if(źródło==bRozpocznij){
            Rejestracja rejestracja= new Rejestracja(opcja);
            rejestracja.setVisible(true);
            dispose();

        }

        else if(źródło==bKsięgaGości){
            if(opcja==JOptionPane.YES_OPTION)
            {
                Serwer ser=new Serwer();
                Thread pierwszy=new Thread(ser);
                pierwszy.start();
            }
            else
            {
                Thread drugi=new Thread(osoba);
                drugi.start();
            }

        }

        else if(źródło==bWyniki){
            Wyniki okno2=new Wyniki();
            okno2.setVisible(true);
            dispose();
        }
    }

}

