import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;


public class Level {
    private final int[] data;
    public Level(int[] data) {
        this.data = data;
    }


    public int[] getData() {
        return data;
    }


    public int getWeight(Point point) {
        if (point.x >= 0 && point.x < 16 && point.y >= 0 && point.y < 16) return data[point.x + point.y * 16];
        else return -2;
    }


    public static Level load(int id) {
        Level level = null;
        try {
            int[] data = new int[256]; //16 * 16
            InputStream is = Level.class.getResourceAsStream("/levels/level" + String.format("%02d", id) + ".ini");
            int index = 0;
            while (is.available() > 0) {
                char ch = (char) is.read();
                if (ch >= '0' && ch <= '9') data[index++] = Integer.parseInt(ch+"");
                if (ch == '.') data[index++] = -1;
            }
            is.close();


            level = new Level(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return level;
    }
}
