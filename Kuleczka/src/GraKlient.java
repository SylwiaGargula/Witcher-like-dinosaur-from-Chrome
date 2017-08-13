/**
 * Created by Sylwia on 2016-05-28.
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by Sylwia on 2016-03-05.
 */
public class GraKlient extends JFrame  implements KeyListener,Runnable {
    int x = 100, y = 600;
    java.util.List<String> lista=new ArrayList<String>(100);

    String IP=null;
    Socket gracz;
    ObjectOutputStream output;
    ObjectInputStream input;

    DetektorKolizji kolizja;
    ZbieranieGwiazdek CzyZebrano;

    List<Istota> potwory=new ArrayList<>();
    List<Gwiazdka> gwiazdki=new ArrayList<>();
    List<Istota> potworyPrzeciwnika=new ArrayList<>();
    List<Gwiazdka> gwiazdkiPrzeciwnika=new ArrayList<>();

    PostacGlowna postac;
    PostacGlowna przeciwnik;


    BazaDanych baza;

    String login;

    File plik; //postac;
    File plik1;

    BufferedImage image;
    int opcja;
    public static String nazwa_postaci;
    boolean looped=true;

    public GraKlient(String A,String xlogin, int xopcja) {

        super("Gra");

        login=xlogin;
        opcja=xopcja;
        baza=new BazaDanych();


        postac=new PostacGlowna(A, x,y,false);
        przeciwnik=new PostacGlowna(A,x,y-300,true);
        try {
            Odczyt();
        } catch (FileNotFoundException e) {
           System.exit(0);
        }

        int a=0;
        int przesunecie=200;
        for (String numer : lista) {
            int q=Integer.parseInt(numer);


            Random r = new Random();
            for (int i = 0; i < 100; i++)
                a = r.nextInt(500) + 250;

            gwiazdki.add(new Gwiazdka(1700+przesunecie, 500));
            potwory.add(new PotworGlowny(q,1100+(2*przesunecie) , 700));

            gwiazdkiPrzeciwnika.add(new Gwiazdka(1700+przesunecie,500-420));
            potworyPrzeciwnika.add(new PotworGlowny(q,1100+(2*przesunecie),700-420));


            kolizja = new DetektorKolizji(potwory);
            CzyZebrano=new ZbieranieGwiazdek(gwiazdki);

            przesunecie+=200+a;

        }

        nazwa_postaci = A;

        plik = new File(nazwa_postaci);


        image = null;
        try {
            image = ImageIO.read(plik);

        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            plik1 = new File("blad1.png");
            try {
                image = ImageIO.read(plik1);
            } catch (IOException e1) {
                System.out.println("Nie odczytano obrazka");
                System.exit(0);
            }

        }

        setSize(1250, 800);
        setLocation(50, 50);

        setVisible(true);
        setResizable(false);
        addKeyListener(this);


        this.createBufferStrategy(3);
        BufferStrategy bufferStrategy= getBufferStrategy();

        Thread thread=new Thread(()->
        {

            while(looped)
            {

                for(Istota istota : this.potwory)
                    istota.tick();

                for(Gwiazdka gwiazdka : this.gwiazdki)
                    gwiazdka.tick();

                this.postac.tick();

                for(Istota istota : this.potworyPrzeciwnika)
                    istota.tick();

                for(Gwiazdka gwiazdka : this.gwiazdkiPrzeciwnika)
                    gwiazdka.tick();

                this.przeciwnik.tick();


                if( kolizja.sprawdz(postac))
                {
                    baza.zapis_wyniku(login,postac.punkt);
                    setTitle("Kolizja");
                    wyslij("kolizja");
                    wyslij(Integer.toString(postac.punkt));

                    dispose();
                }
                else {

                    postac.punkt++;

                    if(CzyZebrano.sprawdzgwiazdki(postac))
                        postac.punkt+=5;

                }
                Graphics g= bufferStrategy.getDrawGraphics();
                String nazwa = "pierwsze.jpg";
                Image bgImage = new ImageIcon(nazwa).getImage();
                g.drawImage(bgImage, 0, 0, this);

                for(Istota istota : this.potwory)
                    istota.paint(g);

                for(Gwiazdka gwiazdka : this.gwiazdki)
                    gwiazdka.paint(g);

                this.postac.paint(g);

                for(Istota istota : this.potworyPrzeciwnika)
                    istota.paint(g);

                for(Gwiazdka gwiazdka : this.gwiazdkiPrzeciwnika)
                    gwiazdka.paint(g);

                this.przeciwnik.paint(g);


                g.dispose();
                bufferStrategy.show();

                try {
                    Thread.sleep(1000/30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.print("jestem-Watek");

        });

        thread.setName("watek animacji");
        thread.start();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                looped=false;

            }
        });
    }


    public void Odczyt() throws FileNotFoundException {
        FileReader pom = null;
        String linia =null;

        try {
            pom = new FileReader("Przeszkody.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Błąd");
            System.exit(1);
        }

        BufferedReader pom1 = new BufferedReader(pom);

        try {
            while ((linia = pom1.readLine()) != null) {

                lista.add(linia);

            }


        } catch (IOException e) {
            System.out.println("Błąd");
            System.exit(2);
        }


        try {
            pom.close();
        } catch (IOException e) {
            System.out.println("Błąd");
            System.exit(3);
        }
    }




    public void paint(Graphics g) {
        super.paint(g);


    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {

            postac.moveOn();
            wyslij("skok");
        }

    }
    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_W) {

        }

    }

    @Override
    public void run() {
        polaczZSerwerem();
        ustawStrumienie();
        odbierz();
    }

  public void polaczZSerwerem()
  {
      IP=JOptionPane.showInputDialog(this,"Podaj adres IP servera","IP",JOptionPane.INFORMATION_MESSAGE);
      try {
          gracz=new Socket(InetAddress.getByName(IP),1111);
      } catch (IOException e) {
          e.printStackTrace();
      }

  }

    public void ustawStrumienie()
    {
        try {
            output= new ObjectOutputStream( gracz.getOutputStream());
            output.flush();
            input= new ObjectInputStream (gracz.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void odbierz()
    {
        String pom=null;
        while(true) {
            try {
                pom = (String) input.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (pom.equals("skok"))
            {
                skok();
            }
            else if(pom.equals("kolizja"))
            {
                przeciwnik.kolizja=true;
                do {

                }while(postac.kolizja!=true);
                try {
                    przeciwnik.punkt=(int) input.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(postac.punkt>przeciwnik.punkt)
                {
                    JOptionPane.showMessageDialog(null,"Wygrywasz","WYGRANA",JOptionPane.INFORMATION_MESSAGE);
                }
                else if(postac.punkt==przeciwnik.punkt)
                { JOptionPane.showMessageDialog(null,"Remis","REMIS",JOptionPane.INFORMATION_MESSAGE);}
                else if(postac.punkt<przeciwnik.punkt)
                {
                    JOptionPane.showMessageDialog(null,"Wygrywa Przeciwnik","PRZEGRANA",JOptionPane.INFORMATION_MESSAGE);
                }

                Kolizja kolizja = new Kolizja(login,opcja);
                kolizja.setVisible(true);
                dispose();
            }
        }
    }

    public void skok()
    {
        przeciwnik.moveOn();
    }

    public void wyslij(String dane)
    {
        try {
            output.writeObject(dane);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}






