
import java.io.*;
import java.util.Date;


/**
 * Created by Sylwia on 2016-04-10.
 */


public class Binarnie {

    public Binarnie() {}


    public void zapisNowegoGracza(Rejestracja nowy,String odczyt) throws IOException
    {
        DataOutputStream pomoc = null;

        try {
            pomoc = new DataOutputStream(new FileOutputStream("Rejestracja.bin"));
        } catch (FileNotFoundException e) {
            System.out.println("BŁĄD OTWIARCIA PLIKU BINARNEGO");
            System.exit(0);
        }

        try {
            Date dzis=new Date();
            String tekst="Imie: "+nowy.getImie()+" Nazwisko:" + nowy.getNazwisko()+" Login: "+nowy.getLogin()+" Hasło: "+nowy.getPassw()+" Email:" +nowy.getEmail()+" Data Rejestracji:"+dzis;

            odczyt=odczyt+tekst;
            pomoc.writeUTF(odczyt);

        } catch (IOException e) {
            System.out.println("BŁĄD ZAPISU DO PLIKU BINARNEGO");
            System.exit(0);
        }

        try {
            if (pomoc != null)

                pomoc.close();
        } catch (IOException e) {
            System.out.println("BŁĄD ZAMYKANIA PLIKU BINARNEGO");
            System.exit(0);
        }
    }
    public String odczyt(String nazwaPliku) throws IOException {

        InputStream pom = null;
        DataInputStream pom2 = null;
        String odczyt="";

        try{

            pom = new FileInputStream(nazwaPliku);
            pom2 = new DataInputStream(pom);
            int length = pom2.available();
            byte[] bufor = new byte[length];
            pom2.readFully(bufor);


            for (byte b:bufor)
            {
                char c = (char)b;
                odczyt = odczyt + c;

            }
        }catch(Exception e){

            System.out.println("BŁĄD ODCZYTU PLIKU BINARNEGO");
            System.exit(0);
        }finally{


            if(pom!=null)
                pom.close();
            if(pom2!=null)
                pom2.close();
        }
        odczyt = odczyt + "\n";
        return odczyt;
    }
    public void ZapisStatystyki(String login,String odczyt)
    {
        DataOutputStream strumień = null;

        try {
            strumień = new DataOutputStream(new FileOutputStream("Statystyka.bin"));
        } catch (FileNotFoundException e) {
            System.out.println("BŁĄD PRZY OTWIERANIU PLIKU BINARNEGO");
            System.exit(0);
        }

        try {
            Date dzis=new Date();
            String tekst=login+" Zalogowal sie "+dzis;

            odczyt=odczyt+tekst;
            strumień.writeUTF(odczyt);

        } catch (IOException e) {
            System.out.println("BŁĄD ZAPISU BINARNEGO");
            System.exit(0);
        }

        try {
            if (strumień != null)

                strumień.close();
        } catch (IOException e) {
            System.out.println("BŁĄD PODCZAS ZAMYKANIA ZAPISU BINARNEGO");
            System.exit(0);
        }
    }
}

