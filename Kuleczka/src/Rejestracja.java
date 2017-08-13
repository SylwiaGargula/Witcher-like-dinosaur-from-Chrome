import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sylwia on 2016-03-18.
 */
public class Rejestracja extends JDialog implements ActionListener{

    JTextField  tImie, tnazwisko, temail, tlogin, thasło;
    JLabel  lImie, lnazwisko, lemail, llogin, lhasło;
    JButton bOk, bAnuluj, bMamJużKonto;
    //mam juz konto przeniesie do okienka logowania
int opcja;
    String imie,nazwisko,login,haslo,mail;

    public Rejestracja(String ximie, String xnazwisko, String xlogin, String xhaslo, String email)
    {
        imie=ximie;
        nazwisko=xnazwisko;
        login=xlogin;
        haslo=xhaslo;
        mail=email;

    }
    public Rejestracja(int xopcja)
    {
        opcja=xopcja;
        setSize(500,500);
        setLocation(500,200);
        setTitle("Rejestracja");

        setLayout(null);

        setResizable(false);


        lImie=new JLabel("Imię",JLabel.RIGHT);
        lImie.setBounds(10,50,100,20);
        add(lImie);

        lnazwisko=new JLabel("Nazwisko",JLabel.RIGHT);
        lnazwisko.setBounds(10,90,100,20);
        add(lnazwisko);


        lemail=new JLabel("E-mail",JLabel.RIGHT);
        lemail.setBounds(10,130,100,20);
        add(lemail);

        llogin=new JLabel("Login",JLabel.RIGHT);
        llogin.setBounds(10,170,100,20);
        add(llogin);

        lhasło=new JLabel("Hasło",JLabel.RIGHT);
        lhasło.setBounds(10,210,100,20);
        add(lhasło);


        tImie=new JTextField();
        tImie.setBounds(250,50,100,20);
        add(tImie);

        tnazwisko=new JTextField();
        tnazwisko.setBounds(250,90,100,20);
        add(tnazwisko);

        temail=new JTextField();
        temail.setBounds(250,130,100,20);
        add(temail);

        tlogin=new JTextField();
        tlogin.setBounds(250,170,100,20);
        add(tlogin);

        thasło=new JTextField();
        thasło.setBounds(250,210,100,20);
        add(thasło);


        bOk= new JButton("Zatwierdź");
        bOk.setBounds(100,300,100,20);
        add(bOk);
        bOk.addActionListener(this);


        bAnuluj= new JButton("Anuluj");
        bAnuluj.setBounds(300,300,100,20);
        add(bAnuluj);
        bAnuluj.addActionListener(this);


        bMamJużKonto= new JButton("Mam już konto");
        bMamJużKonto.setBounds(150,400,200,20);
        add(bMamJużKonto);
        bMamJużKonto.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object klikniecie =e.getSource();
        if(klikniecie==bMamJużKonto)
        {
            Logowanie logowanie= new Logowanie(opcja);
            logowanie.setVisible(true);
            dispose();
        }

        else if(klikniecie==bOk)
        {


            BazaDanych baza=new BazaDanych();
            baza.registerUser(tImie.getText(),tnazwisko.getText(),temail.getText(),tlogin.getText(),thasło.getText());

            Logowanie logowanie= new Logowanie(opcja);
            logowanie.setVisible(true);
            dispose();

        }
    }
    public String getLogin()
    {
        return login;
    }
    public String getPassw()
    {
        return haslo;
    }


    public String getEmail() {
        return mail;
    }

    public  String getNazwisko() {
        return nazwisko;
    }

    public String getImie() {
        return imie;
    }
}