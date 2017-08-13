
import javax.swing.*;
import java.awt.EventQueue;
import java.io.*;
import java.util.Random;


public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                    new Window();

            }
        });

        try {
            Zapis();
        } catch (Exception e) {
            System.out.print("Błąd w main");
        }

    }


    public static void Zapis() throws FileNotFoundException {
        PrintWriter zapis = new PrintWriter("Przeszkody.txt");
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            int a = r.nextInt(3) + 1; //losowanie od 1 do 3
            zapis.println(a);
        }
        zapis.close();
    }

}

