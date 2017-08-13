import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Radio extends JFrame implements ActionListener,ChangeListener {


    Clip clip;
    JButton bPlay,bStop;
    JButton bPauza,bNext,bPrevious;
    JTextArea tSpis;
    long clipTime;
    boolean isplayed=false;
    String[] sciezki;
    FloatControl gainControl;
    File plik;

    AudioInputStream audioInputStream;
    JSlider suwak;
    int numer=0,glosnosc=0;
    int min,max;

    public Radio() throws  InterruptedException, UnsupportedAudioFileException, IOException
    {
        try {
            setTitle("Radio");
            setSize(700,300);
            setVisible(true);
            setLocation(1000,100);
            setLayout(null);


            bPlay=new JButton("START");
            bPlay.setBounds(10,20,120,30);
            add(bPlay);
            bPlay.addActionListener(this);

            bPauza=new JButton("WSTRZYMAJ");
            bPauza.setBounds(130,20,120,30);
            add(bPauza);
            bPauza.addActionListener(this);

            bStop=new JButton("ZATRZYMAJ");
            bStop.setBounds(250,20,120,30);
            add(bStop);
            bStop.addActionListener(this);


            bNext=new JButton("NASTĘPNY");
            bNext.setBounds(370,20,120,30);
            add(bNext);
            bNext.addActionListener(this);

            bPrevious=new JButton("POPRZEDNI");
            bPrevious.setBounds(490,20,120,30);
            add(bPrevious);
            bPrevious.addActionListener(this);



            tSpis=new JTextArea("");
            JScrollPane scrollPane=new JScrollPane(tSpis);
            scrollPane.setBounds(300,100,200,200);
            add(scrollPane);



            File f=new File("C:/Users/Sylwia/Desktop/java_lato_2015-2016_sylwia_gargula/Kuleczka/muzyka");
            sciezki=f.list();

            for(int i=0;i<sciezki.length;i++)
            {
                if(i==numer)
                {
                    tSpis.append(i+1+". "+sciezki[i]+" TERAZ"+"\n");
                }
                else
                {
                    tSpis.append(i+1+". "+sciezki[i]+"\n");
                }
                sciezki[i]="muzyka/"+sciezki[i];
            }


            plik=new File(sciezki[numer]);
            audioInputStream = AudioSystem.getAudioInputStream(plik);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);




            gainControl =(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(glosnosc);
            min=(int) gainControl.getMinimum();
            max=(int) gainControl.getMaximum();



            suwak=new JSlider(min,max,0);
            suwak.setBounds(20,125,150,20);
            suwak.addChangeListener(this);

            add(suwak);

            clip.start();

        } catch (LineUnavailableException ex) {
            Logger.getLogger(Radio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object z=e.getSource();
        if(z==bPlay)
        {
            clip.start();
            isplayed=true;

        }
        else if(z==bStop)
        {
            clip.stop();
            isplayed=false;
        }

        else if(z==bPauza)
        {
            if(isplayed==true)
            {
                isplayed=false;
                clipTime=clip.getMicrosecondPosition();
                clip.stop();
            }
            else
            {
                isplayed=true;
                clip.setMicrosecondPosition(clipTime);
                clip.start();
            }
        }
        else if(z==bNext)
        {
            try {
                clip.stop();

                numer++;
                if(numer>sciezki.length-1)
                {
                    numer=0;
                }
                plik=new File(sciezki[numer]);
                try {
                    audioInputStream = AudioSystem.getAudioInputStream(plik);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Radio.class.getName()).log(Level.SEVERE, null, ex);
                }

                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                gainControl =(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(glosnosc);
                clip.start();
                tSpis.setText("");
                for(int i=0;i<sciezki.length;i++)
                {
                    if(i==numer)
                    {
                        tSpis.append(i+1+". "+sciezki[i]+"    TERAZ"+"\n");
                    }
                    else
                    {
                        tSpis.append(i+1+". "+sciezki[i]+"\n");
                    }
                }
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Radio.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Radio.class.getName()).log(Level.SEVERE, null, ex);
            }


        } else if(z==bPrevious)

        {
            try {
                clip.stop();
                numer--;
                if(numer<0)
                {
                    numer=sciezki.length-1;
                }
                plik=new File(sciezki[numer]);
                try {
                    audioInputStream = AudioSystem.getAudioInputStream(plik);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Radio.class.getName()).log(Level.SEVERE, null, ex);
                }
                clip = AudioSystem.getClip();

                min=(int)gainControl.getMinimum();
                max=(int)gainControl.getMaximum();


                clip.open(audioInputStream);
                clip.start();
                tSpis.setText("");
                for(int i=0;i<sciezki.length;i++)
                {

                    if(i==numer)
                    {
                        tSpis.append(i+1+". "+sciezki[i]+"    TERAZ"+"\n");
                    }
                    else
                    {
                        tSpis.append(i+1+". "+sciezki[i]+"\n");
                    }
                }
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Radio.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Radio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        glosnosc=suwak.getValue();
        clipTime=clip.getMicrosecondPosition();
        clip.stop();
        gainControl.setValue((float)glosnosc);
        clip.setMicrosecondPosition(clipTime);
        clip.start();

    }


}