/**
 * Created by Sylwia on 2016-03-18.
 */
public class Gracz {


    String Imie, Nazwisko, Login, Hasło, Email;
    public Gracz(String aImie,String aNazwisko,String aLogin,String aHasło,String aEmail)
    {
        Imie=aImie;
        Nazwisko=aNazwisko;
        Login=aLogin;
        Hasło=aHasło;
        Email=aEmail;

    }

    public String getLogin() {
        return Login;
    }

    public String getHasło() {
        return Hasło;
    }



}
