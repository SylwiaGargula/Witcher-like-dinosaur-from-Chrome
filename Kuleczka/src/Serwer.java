/**
 * Created by Sylwia on 2016-04-19.
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Serwer extends JFrame implements ActionListener,Runnable{
    JTextField podaj;
    JTextArea pokaz;
    ServerSocket serwer;
    Socket polaczenie;
    ObjectOutputStream output;
    ObjectInputStream input;
    public Serwer()
    {
        setTitle("Gadu (S)");
        setLocation(0,250);
        setSize(600,600);
        setResizable(false);


        setLayout(null);

        podaj=new JTextField();
        podaj.setBounds(25,25,200,25);
        add(podaj);
        podaj.addActionListener(this);



        pokaz=new JTextArea();
        JScrollPane scroll=new JScrollPane(pokaz);
        scroll.setBounds(25,75,250,200);
        add(scroll);
        setVisible(true);

    }
    public void uruchom() throws ClassNotFoundException
    {
        try {
            serwer=new ServerSocket(11111);
            while(true)
            {
                czekanie();
                wejsciewyjscie();
                gadu();
            }
        } catch (IOException ex) {
            Logger.getLogger(Serwer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void czekanie()
    {
        try {
            pokaz.append("Czekam  \n");
            polaczenie=serwer.accept();
            pokaz.append("Połączono z IP "+ polaczenie.getInetAddress().getHostName());
        } catch (IOException ex) {


            Logger.getLogger(Serwer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            wyslij(podaj.getText());

            podaj.setText("");
        } catch (IOException ex) {


            Logger.getLogger(Serwer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void wejsciewyjscie() throws IOException
    {

        output= new ObjectOutputStream(polaczenie.getOutputStream());
        output.flush();
        input= new ObjectInputStream(polaczenie.getInputStream());

    }

    private void gadu() throws IOException, ClassNotFoundException {
        PrintWriter zapis = new PrintWriter("GaduS.txt");
        wyslij("Połączono się");
        String wiadomosc;
        do
        {
            wiadomosc=(String) input.readObject();
            pokaz.append("\n "+wiadomosc);
                zapis.println(wiadomosc);

        }while(!wiadomosc.equals("KONIEC"));

        pokaz.append("\n KONIEC");

        output.close();
        input.close();
        polaczenie.close();
        serwer.close();
    }

    private void wyslij(String text) throws IOException
    {

        output.writeObject("Serwer: "+text);
        output.flush();
        pokaz.append("\n Serwer: "+text);

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
        }

        g.drawImage(image,0,0,this);

    }

    @Override
    public void run() {
        try {
            uruchom();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Serwer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
