import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sylwia on 2016-03-18.
 */
public class Logowanie extends JDialog implements ActionListener {


    JLabel lLogin,lHasło;
    JTextField tLogin;
    JPasswordField pHasło;
    JButton bZaloguj, bAnuluj;
int opcja;

    public Logowanie(int xopcja)
    {
        opcja=xopcja;
        setSize(500,350);
        setLocation(500,200);
        setTitle("Logowanie");

        setLayout(null);



        setResizable(false);

        lLogin=new JLabel("Login",JLabel.RIGHT);
        lLogin.setBounds(10,50,100,20);
        add(lLogin);

        lHasło=new JLabel("Hasło",JLabel.RIGHT);
        lHasło.setBounds(10,90,100,20);
        add(lHasło);

        tLogin=new JTextField();
        tLogin.setBounds(250,50,100,20);
        add(tLogin);

        pHasło=new JPasswordField();
        pHasło.setBounds(250,90,100,20);
        add(pHasło);

        bZaloguj= new JButton("Zaloguj się");
        bZaloguj.setBounds(100,200,100,20);
        add(bZaloguj);
        bZaloguj.addActionListener(this);


        bAnuluj= new JButton("Anuluj");
        bAnuluj.setBounds(300,200,100,20);
        add(bAnuluj);
        bAnuluj.addActionListener(this);



    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object źródło=e.getSource();
        if(źródło==bZaloguj)
        {

            String Login, Hasło;
            Login=tLogin.getText();
            Hasło=pHasło.getText();

            BazaDanych baza=new BazaDanych();
            if(baza.loginUsers(Login,Hasło)==true)
            {
                dispose();
                Wybór okno3= new Wybór(Login, opcja);

                okno3.setVisible(true);
            }
            else
            {
                tLogin.setText("");
                pHasło.setText("");
            }

            dispose();

        }

    }
}
