import java.util.List;

/**
 * Created by Sylwia on 2016-05-21.
 */
public class ZbieranieGwiazdek {

    List<Gwiazdka> gwiazdki;

    public ZbieranieGwiazdek(List<Gwiazdka> gwiazdki)
    {
        this.gwiazdki=gwiazdki;


    }

    public boolean sprawdzgwiazdki(Istota postac) {
        for (Gwiazdka gwiazdka : this.gwiazdki) {

            if ((gwiazdka.getX()+gwiazdka.getSzerokosc()) <= postac.getX() + postac.getSzerokosc() && (gwiazdka.getY()+gwiazdka.getWysokosc()) <= postac.getY() + postac.getWysokosc()) {
                {gwiazdka.y=2000;
                    return true;
                }
            }
        }
        return false;
    }

}
