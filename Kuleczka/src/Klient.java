import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Sylwia on 2016-04-19.
 */
public class Klient extends JFrame implements ActionListener, Runnable{
    JTextField podaj;
    JTextArea pokaz;

    public String serverIP;
    Socket polaczenie;
    ObjectOutputStream output;
    ObjectInputStream input;

    public void Kliencik()
    {
        setTitle("Gadu (K)");
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

    public Klient(String ip)
    {
        serverIP=ip;

    }

    public void uruchom() throws IOException, ClassNotFoundException
    {
        laczenie();
        wejsciewyjscie();
        gadu();
    }


    private void laczenie() throws IOException
    {
        pokaz.append("Czekam");
        polaczenie=new Socket(InetAddress.getByName(serverIP),11111);
        pokaz.append(" Udało się połączyć z "+polaczenie.getInetAddress().getHostName());
    }

    private void wejsciewyjscie() throws IOException
    {

        output= new ObjectOutputStream(polaczenie.getOutputStream());
        output.flush();
        input= new ObjectInputStream(polaczenie.getInputStream());

    }


    private void gadu() throws IOException, ClassNotFoundException
    {
        PrintWriter zapis = new PrintWriter("GaduK.txt");
        String wiadomosc=null;
        do
        {
            wiadomosc=(String) input.readObject();
            pokaz.append("\n "+wiadomosc); //laczenie

             zapis.println(wiadomosc);

        }while(!wiadomosc.equals("KONIEC"));

        output.close();
        input.close();
        polaczenie.close();
    }

    public void wyslij(String Text) throws IOException
    {
        output.writeObject("Klient: "+Text);
        output.flush();
        pokaz.append("\n Klient: " + Text);

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
    public void actionPerformed(ActionEvent e) {
        try {
            wyslij(podaj.getText());
        }


        catch (IOException ex) {
            Logger.getLogger(Klient.class.getName()).log(Level.SEVERE, null, ex);
        }
        podaj.setText("");

    }


    @Override
    public void run() {
        try {
            Kliencik();
            uruchom();
        } catch (IOException ex) {
            Logger.getLogger(Klient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Klient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}