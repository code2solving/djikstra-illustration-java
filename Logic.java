import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Logic {
    private static volatile boolean starting = false;
    private static volatile boolean logicStop = false;
    private static volatile boolean drawProcess = true;
    private static volatile long speed = 15;

    //start the search
    public static boolean isStarting() {
        return starting;
    }

    // stop the search
    public static void stop() {
        logicStop = true;
    }


    public static void setDrawProcess(boolean drawProcess) {
        Logic.drawProcess = drawProcess;
    }

   //set the speed of drawing
    public static void setSpeed(long speed) {
        Logic.speed = speed;
    }

    public static void process(final Win win, final Level level, final Point pointA, final Point pointB) {
        if (!starting) {
            new Thread(
                    new Runnable() {
                @Override
                public void run() {
                    logicStop = false;
                    starting = true;
                    win.setProcessStatus(true);
                    win.clear();


                    method1(win, level, pointA, pointB);

                    starting = false;
                    win.setProcessStatus(false);
                }
            }).start();
        }
    }

    public static void sleep() {
        sleep(speed);
    }
    public static void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Dijkstra algorithm
    public static void method1(Win win, Level level, Point pointA, Point pointB) {
        Point[] dir = {new Point(0, -1),new Point(1, 0), new Point(0, 1), new Point(-1, 0)};
        ArrayList<Point> findWay = new ArrayList<>();
        HashMap<Point, ElementValue> table = new HashMap<>();

        table.put(pointA, new ElementValue(
                        0, /*общий вес точки А*/
                        null, /*в точку "А" ниоткуда не пришли*/
                        false /*метка - вес возможно может быть меньше, хотя очевидно что уже не может быть меньше, это для того чтобы цикл не остановился сразу же*/
                )
        );

        boolean stop = false;
        while (!stop) {
            if (logicStop) return; //остановим цикл если была нажата кнопка "Остановить"
            int minWeight = Integer.MAX_VALUE;
            Point currentPoint = null;
            for (Map.Entry<Point, ElementValue> pair : table.entrySet()) {
                Point p = pair.getKey();
                ElementValue ev = pair.getValue();
                if (!ev.label && ev.weight < minWeight) {
                    minWeight = ev.weight;
                    currentPoint = p;
                }
            }

            if (currentPoint != null) {
                table.get(currentPoint).label = true;
                ArrayList<Point> way = new ArrayList<>();
                Point wPoint = currentPoint;
                boolean wStop = false;

                while (!wStop) {
                    way.add(0, wPoint);
                    ElementValue wEv = table.get(wPoint);
                    if (wEv != null && wEv.point != null) wPoint = wEv.point;
                    else wStop = true;
                }
                if (drawProcess) {
                   //рисование пути
                    sleep();
                    win.clear();
                    for (int i = 0; i < way.size(); i++) {
                        Point p = way.get(i);
                        Point fromP = null;
                        if (i - 1 > 0) fromP =  way.get(i - 1);
                        if (p.equals(pointA) || p.equals(pointB)) continue; //не рисуем на точках A и B
                        win.drawSelect(fromP, p);
                    }
                    sleep();
                }


                for (int i = 0; i < 4; i++) {
                    Color cellColor = Color.WHITE;
                    Point neighborPoint = new Point(currentPoint.x + dir[i].x, currentPoint.y + dir[i].y);

                    if (neighborPoint.x >= 0 && neighborPoint.x < 16 && neighborPoint.y >= 0 && neighborPoint.y < 16) {
                        if (!way.contains(neighborPoint)) {
                            if (neighborPoint.equals(pointB)) {
                                stop = true;
                                break;
                            }

                            int neighborWeight = level.getWeight(neighborPoint);
                            if (neighborPoint.equals(pointA)) neighborWeight = 0;
                            if (neighborWeight == -1) {
                                cellColor = Color.RED;
                                neighborWeight = Integer.MAX_VALUE;
                            } else {
                                for (int j = 0; j < way.size(); j++) {
                                    Point p = way.get(j);
                                    int w = level.getWeight(p);
                                    if (p.equals(pointA)) w = 0;
                                    neighborWeight += w;
                                }
                                neighborWeight += way.size() - 1;
                            }


                            if (table.containsKey(neighborPoint)) {
                                if (!table.get(neighborPoint).label) {
                                    if (neighborWeight < table.get(neighborPoint).weight) {
                                        table.get(neighborPoint).weight = neighborWeight;
                                        table.get(neighborPoint).point = currentPoint;
                                        table.get(neighborPoint).label = false;
                                        if (drawProcess) {
                                            win.drawSelect(currentPoint, neighborPoint, cellColor);
                                            sleep();
                                        }
                                    } else {
                                        if (drawProcess) {
                                            if (cellColor != Color.RED) cellColor = Color.BLACK;
                                            win.drawSelect(currentPoint, neighborPoint, cellColor);
                                            sleep();
                                        }
                                    }
                                } else {
                                    if (drawProcess) {
                                        if (cellColor != Color.RED) cellColor = Color.BLUE;
                                        win.drawSelect(currentPoint, neighborPoint, cellColor);
                                        sleep();
                                    }
                                }
                            } else {
                                table.put(neighborPoint, new ElementValue(neighborWeight, currentPoint, false));
                                if (drawProcess) {
                                    win.drawSelect(currentPoint, neighborPoint, cellColor);
                                    sleep();
                                }
                            }
                        }
                    } else if (drawProcess) {
                        win.drawSelect(currentPoint, neighborPoint, Color.RED);
                        sleep();
                    }
                }
                if (stop) {
                    findWay = way;
                    break;
                }
            } else stop = true;
        }

        if (findWay.size() > 0) {
            int findWayWeight = 0;
            for (Point p : findWay) findWayWeight += level.getWeight(p);
            findWayWeight += findWay.size() - 1;
            win.setLabel("Вес всего пути: " + findWayWeight);
            win.clear();
            for (int i = 0; i < findWay.size(); i++) {
                Point p = findWay.get(i);
                Point fromP = null;
                if (i - 1 > 0) fromP =  findWay.get(i - 1);
                if (p.equals(pointA)) continue; //не рисуем на точках A и B
                win.drawSelect(fromP, p);
            }
        } else {
            win.setLabel("Путь не найден");
        }
    }

    //Store the results of search
    public static class ElementValue {
        public int weight;
        public Point point;
        public boolean label;

        public ElementValue(int weight, Point point, boolean label) {
            this.weight = weight;
            this.point = point;
            this.label = label;
        }
    }
}
