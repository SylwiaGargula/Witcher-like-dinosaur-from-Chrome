import java.awt.*;

/**
 * Created by Sylwia on 2016-04-07.
 */
public abstract class Istota
{
    protected boolean kolizja=false;
    protected int x,y;
    protected int szerokosc, wysokosc;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSzerokosc() {
        return szerokosc;
    }

    public int getWysokosc() {
        return wysokosc;
    }

    public  Istota(int x, int y)
    {
        this.x=x;
        this.y=y;

    }
    public abstract void tick();

    public abstract void paint(Graphics g) ;
}
