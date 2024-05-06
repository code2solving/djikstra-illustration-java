import java.awt.*;
import java.util.Locale;


public class Start {
    public static void main(String[] args) {
        Win win = new Win();
        Level level = Level.load(1);
        win.setLevel(level);
        win.setA(new Point(4, 7));
        win.setB(new Point(15, 0));
    }
}
