import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Sylwia on 2016-04-07.
 */
public class DetektorKolizji {
    List<Istota> potwory;

    public DetektorKolizji(List <Istota> potwory)
    {
        this.potwory=potwory;


    }

    public boolean sprawdz(Istota postac) {
        for (Istota istota : this.potwory) {
            if (istota.getX() <= postac.getX() + postac.getSzerokosc() & istota.getY() <= postac.getY() + postac.getWysokosc()) {
                postac.kolizja=true;
                return true;
            }
        }
return false;
    }

}
