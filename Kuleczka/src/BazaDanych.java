import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JOptionPane;


public class BazaDanych{
    private Connection con = null;
    private Statement stat = null;
    int licznik=0;
    BazaDanych(){
        connectDatabase();
    }


    public void connectDatabase(){
        try{
            //dane do logowania do bazy danych, ukryte


          //  con = DriverManager.getConnection( host, user, passwd );
            stat = con.createStatement();
        }
        catch ( SQLException err ) {
            System.out.println( err.getMessage( ) );
            System.out.println("BŁĄD BAZA DANYCH-ŁĄCZENIE");
            System.exit(0);
        }
    }

    public void zapis_wyniku(String login,int x)
    {

        try (PreparedStatement prepStmt = con.prepareStatement("insert into wyniki values (NULL, ?, ?);")) {
            prepStmt.setString(1, login);
            prepStmt.setInt(2, x);

            prepStmt.execute();
            prepStmt.close();
        } catch (SQLException e) {
            System.out.println("BŁĄD ZAPIS WYNIKU DO BAZY");
            System.exit(0);
        }

}



    public boolean loginUsers (String login, String passw){
        LinkedList<Gracz> users = selectUsers();
        boolean b = false;
        if(users.size() != 0)
        {
            for(int i =  0; i<users.size(); i++){
                if((users.get(i).getLogin().equals(login)) & (users.get(i).getHasło().equals(passw))) b = true;
            }
        }

        if(b == false)
            JOptionPane.showMessageDialog(null, "Błędne dane", "Błąd", JOptionPane.ERROR_MESSAGE);

        if(b == true) {
            System.out.println("Zalogowano");
            Binarnie zapisuje=new Binarnie();
            String odczyt = null;
            try {
                odczyt = zapisuje.odczyt("Statystyka.bin");
            } catch (IOException e) {
                System.out.println("BŁĄD LOGOWANIA-ZAPISU DO PLIKU BINARNEGO");
                System.exit(0);}

            zapisuje.ZapisStatystyki(login,odczyt);
        }
        return b;
    }



    public boolean registerUser(String imie, String nazwisko, String email, String login, String haslo) {
        LinkedList<Gracz> users = selectUsers();
        boolean b = false;

        if(users.size() != 0){
            for(int i=0; i<users.size(); i++){
                if(users.get(i).getLogin().equals(login)) {
                    b = false;
                    licznik=1;
                }
                else b = true;
            }
        }
        else b = true;
        if(b == true && licznik==0){

            Rejestracja Gracz=new Rejestracja(imie,nazwisko,login,haslo,email);
            Binarnie zapis=new Binarnie();
            String odczyt = null;
            try {
                odczyt = zapis.odczyt("Rejestracja.bin");
            } catch (IOException e) {
                System.out.println("BŁĄD REJESTRACJI-ODCZYTU PLIKU BINARNEGO");
                System.exit(0);}

            try {
                zapis.zapisNowegoGracza(Gracz,odczyt);
            } catch (IOException e) {
                System.out.println("BŁĄD REJESTRACJI-ZAPISU PLIKU BINARNEGO");
                System.exit(0);}
            try {

                PreparedStatement prepStmt = con.prepareStatement("insert into dane values (NULL, ?, ?, ?, ?, ?, NULL);");
                prepStmt.setString(1, imie);
                prepStmt.setString(2, nazwisko);
                prepStmt.setString(3, email);
                prepStmt.setString(4, login);
                prepStmt.setString(5, haslo);
                prepStmt.execute();
            }
            catch (SQLException e) {
                System.err.println("BŁĄD DODANIA UŻYTKOWNIKA DO BAZY");
                System.exit(0);
                e.printStackTrace();
                return false;
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Taki użytkownik już istnieje w bazie", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
        return b;
    }



    public LinkedList<Gracz> selectUsers() {
        LinkedList<Gracz> users = new LinkedList<Gracz>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM dane");
            String imie,nazwisko,email,login,haslo;
            while(result.next()) {
                login = result.getString("login");
                haslo = result.getString("haslo");
                email = result.getString("email");
                imie = result.getString("imie");
                nazwisko = result.getString("nazwisko");
                users.add(new Gracz(imie,nazwisko,login,haslo,email));
            }
        } catch (SQLException e) {
            System.out.println("BŁĄD PODCZAS WYBIERANIA UŻYTKOWNIKA Z BAZY");
            System.exit(0);
            return null;
        }
        return users;
    }


    public ArrayList<Opisy> PobierzOpisy() throws SQLException {
        ArrayList<Opisy> Opis = new ArrayList<Opisy>();


        ResultSet result = stat.executeQuery("SELECT imie,opis FROM kuleczka.postacie");
        String a,b;
        while (result.next()) {

            a=result.getString("imie");
            b=result.getString("opis");
            Opis.add(new Opisy(a,b));
        }

        return Opis;

    }

    public String  PobierzWyniki()  {
        ArrayList<Wyniki_baza> Wynik = new ArrayList<Wyniki_baza>();


        ResultSet result = null;
        try {
            result = stat.executeQuery("SELECT login_gracza,ilosc_pkt FROM `wyniki` ORDER BY `ilosc_pkt` DESC; ");
        } catch (SQLException e) {
            System.out.println("BŁĄD POBIERANIA WYNIKÓW GRACZA");
            System.exit(0);}
        int x=0;
        String a,b;
        try {
            while (result.next()) {
                x++;
                a=result.getString("login_gracza");
                b=result.getString("ilosc_pkt");
                Wynik.add(new Wyniki_baza(a,b));
                if(x==10)
                    break;
            }
        } catch (SQLException e) {
            System.out.println("BŁĄD ODCZYTYWANIA DANYCH GRACZY(WYNIKÓW)");
            System.exit(0);
        }
        String napisy="\n";
        for(int i=0;i<10;i++) {
            napisy = napisy + "   " + (i+1) + ") " + Wynik.get(i).imie + " - " + Wynik.get(i).wynik + " Punktów\n\n";
        }
        return napisy;

    }



}