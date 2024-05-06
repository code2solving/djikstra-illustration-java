import sun.rmi.runtime.Log;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Win extends JFrame {
    private JPanel panel;
    private JMenu menuLevel;
    private JCheckBoxMenuItem menuProcess;
    private JRadioButtonMenuItem menuRadio1;
    private JRadioButtonMenuItem menuRadio2;
    private JRadioButtonMenuItem menuRadio3;
    private JRadioButtonMenuItem menuRadio4;
    private JRadioButtonMenuItem menuRadio5;
    private JPanel wrapper;
    private DrawLayer layerWork;
    private DrawLayer layerLevel;
    private DrawLayer layerGrid;
    private JLabel label;

    private Level level;
    private Point pointA;
    private Point pointB;

    public Win() {
        setTitle("Path searching - Dijkstra algorithm");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setPreferredSize(new Dimension(354, 442));

        panel = new JPanel(new BorderLayout());
            wrapper = new JPanel(new BorderLayout());
            wrapper.setBackground(new Color(0, 98, 37));
            wrapper.setBorder(new CompoundBorder(new EmptyBorder(4,4,4,4), new BevelBorder(BevelBorder.LOWERED)));
                layerWork = new DrawLayer(this);
                layerWork.setLayout(new BorderLayout());
                layerWork.setOpaque(false);
                    layerLevel = new DrawLayer(this);
                    layerLevel.setLayout(new BorderLayout());
                    layerLevel.setOpaque(false);
                        layerGrid = new DrawLayer(this);
                        layerGrid.setLayout(new BorderLayout());
                        layerGrid.setBackground(new Color(62, 115, 54));
                        layerGrid.g.setColor(new Color(22,22,22));
                        for (int i = 0; i <= 16; i++) layerGrid.g.drawLine(8, i * 20 + 8, 328, i * 20 + 8);
                        for (int i = 0; i <= 16; i++) layerGrid.g.drawLine(i * 20 + 8, 8, i * 20 + 8, 328);
                        layerGrid.draw();
                    layerLevel.add(layerGrid);
                layerWork.add(layerLevel);
            wrapper.add(layerWork);
        panel.add(wrapper);
            JPanel south = new JPanel(new BorderLayout());
            south.setBackground(new Color(0, 98, 37));
            south.setBorder(new EmptyBorder(4,4,4,4));
                JPanel buttons = new JPanel(new BorderLayout());
                buttons.setBackground(new Color(0, 98, 37));
                    JButton buttonStart = new JButton("Start");
                    buttonStart.setBackground(new Color(63, 139, 63));
                    buttonStart.setBorder(new LineBorder(Color.DARK_GRAY, 1));
                    buttonStart.setBorderPainted(false);
                    buttonStart.setFocusPainted(false);
                    buttonStart.setPreferredSize(new Dimension(100, 30));
                    final Win win = this;
                    buttonStart.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Logic.process(win, level, pointA, pointB);
                        }
                    });
                buttons.add(buttonStart, BorderLayout.WEST);
                    JPanel buttonResetBorder = new JPanel(new BorderLayout());
                    buttonResetBorder.setBackground(new Color(0, 98, 37));
                    buttonResetBorder.setBorder(new EmptyBorder(0, 8, 0, 0));
                        JButton buttonReset = new JButton("Reset");
                        buttonReset.setBackground(new Color(63, 139, 63));
                        buttonReset.setBorder(new LineBorder(Color.DARK_GRAY, 1));
                        buttonReset.setBorderPainted(false);
                        buttonReset.setFocusPainted(false);
                        buttonReset.setPreferredSize(new Dimension(100, 30));
                        buttonReset.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (!Logic.isStarting()) {
                                    layerWork.clear();
                                    layerWork.draw();
                                    setProcessStatus(false);
                                } else Logic.stop();
                            }
                        });
                    buttonResetBorder.add(buttonReset);
                buttons.add(buttonResetBorder);
            south.add(buttons, BorderLayout.WEST);
                label = new JLabel("Wait");
                label.setForeground(new Color(20, 41, 20));
                label.setBorder(new EmptyBorder(10, 10, 10, 10));
                label.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
            south.add(label);
        panel.add(south, BorderLayout.SOUTH);
        setContentPane(panel);

        JMenuBar menu = new JMenuBar();
        menu.setBackground(new Color(0, 98, 37));
        menu.setBorderPainted(false);
            menuLevel = new JMenu("Level 1");
            menuLevel.setForeground(new Color(84, 191, 84));
            menuLevel.setBorder(new EmptyBorder(5, 4, 0, 0));
                JMenuItem menuLevel1 = new JMenuItem("Level 1");
                menuLevel1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!Logic.isStarting()) {
                            clear();
                            setLevel(Level.load(1));
                            menuLevel.setText("Level 1");
                        }
                    }
                });
                JMenuItem menuLevel2 = new JMenuItem("Level 2");
                menuLevel2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!Logic.isStarting()) {
                            clear();
                            setLevel(Level.load(2));
                            menuLevel.setText("Level 2");
                        }
                    }
                });
                JMenuItem menuLevel3 = new JMenuItem("Level 3");
                menuLevel3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!Logic.isStarting()) {
                            clear();
                            setLevel(Level.load(3));
                            menuLevel.setText("Level 3");
                        }
                    }
                });
                JMenuItem menuLevel4 = new JMenuItem("Level 4");
                menuLevel4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!Logic.isStarting()) {
                            clear();
                            setLevel(Level.load(4));
                            menuLevel.setText("Level 4");
                        }
                    }
                });
                JMenuItem menuLevel5 = new JMenuItem("Level 5");
                menuLevel5.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!Logic.isStarting()) {
                            clear();
                            setLevel(Level.load(5));
                            menuLevel.setText("Level 5");
                        }
                    }
                });
            menuLevel.add(menuLevel1);
            menuLevel.add(menuLevel2);
            menuLevel.add(menuLevel3);
            menuLevel.add(menuLevel4);
            menuLevel.add(menuLevel5);
        menu.add(menuLevel);
            JMenu menuSetting = new JMenu("Настройки");
            menuSetting.setForeground(new Color(84, 191, 84));
            menuSetting.setBorder(new EmptyBorder(5, 4, 0, 0));
                JMenu menuSpeed = new JMenu("Скорость");
                    ButtonGroup radioGroup = new ButtonGroup();
                        menuRadio1 = new JRadioButtonMenuItem("Очень быстро");
                        menuRadio1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                menuRadio1.setSelected(true);
                                Logic.setSpeed(1);
                            }
                        });
                        menuRadio2 = new JRadioButtonMenuItem("Быстро");
                        menuRadio2.setSelected(true);
                        menuRadio2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                menuRadio2.setSelected(true);
                                Logic.setSpeed(10);
                            }
                        });
                        menuRadio3 = new JRadioButtonMenuItem("Средне");
                        menuRadio3.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                menuRadio3.setSelected(true);
                                Logic.setSpeed(40);
                            }
                        });
                        menuRadio4 = new JRadioButtonMenuItem("Медленно");
                        menuRadio4.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                menuRadio4.setSelected(true);
                                Logic.setSpeed(100);
                            }
                        });
                        menuRadio5 = new JRadioButtonMenuItem("Очень медленно");
                        menuRadio5.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                menuRadio5.setSelected(true);
                                Logic.setSpeed(333);
                            }
                        });
                    radioGroup.add(menuRadio1);
                    radioGroup.add(menuRadio2);
                    radioGroup.add(menuRadio3);
                    radioGroup.add(menuRadio4);
                    radioGroup.add(menuRadio5);
                menuSpeed.add(menuRadio1);
                menuSpeed.add(menuRadio2);
                menuSpeed.add(menuRadio3);
                menuSpeed.add(menuRadio4);
                menuSpeed.add(menuRadio5);
            menuSetting.add(menuSpeed);
                menuProcess = new JCheckBoxMenuItem("Показывать процесс поиска");
                menuProcess.setSelected(true);
                menuProcess.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!Logic.isStarting()) {
                            Logic.setDrawProcess(menuProcess.isSelected());
                        }
                    }
                });
            menuSetting.add(menuProcess);
        menu.add(menuSetting);
        setJMenuBar(menu);

        pack();
        setLocationRelativeTo(null);
        validate();
        repaint();
        setVisible(true);
    }

    private static class DrawLayer extends JPanel {
        private Win win;
        private BufferedImage bi = null;
        public Graphics2D g;

        public DrawLayer(Win win) {
            this.win = win;
            bi = new BufferedImage(340, 340, BufferedImage.TYPE_INT_ARGB);
            g = (Graphics2D) bi.getGraphics();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            if (bi != null) {
                g.drawImage(bi, 0, 0, null);
            }
        }

        public void draw() {
            win.wrapper.repaint();
        }

        public void clear() {
            bi = new BufferedImage(340, 340, BufferedImage.TYPE_INT_ARGB);
            g = (Graphics2D) bi.getGraphics();
        }
    }


    private void drawLevel() {
        if (level != null) {
            for (int i = 0; i < 256; i++) {
                int x = i % 16;
                int y = i / 16;
                if (level.getData()[i] == -1) layerLevel.g.setColor(new Color(20, 41, 20));
                else if (level.getData()[i] == 1) layerLevel.g.setColor(new Color(56, 108, 53));
                else if (level.getData()[i] == 2) layerLevel.g.setColor(new Color(54, 105, 52));
                else if (level.getData()[i] == 3) layerLevel.g.setColor(new Color(51, 102, 51));
                else if (level.getData()[i] == 4) layerLevel.g.setColor(new Color(49, 98, 49));
                else if (level.getData()[i] == 5) layerLevel.g.setColor(new Color(46, 92, 46));
                else if (level.getData()[i] == 6) layerLevel.g.setColor(new Color(43, 86, 43));
                else if (level.getData()[i] == 7) layerLevel.g.setColor(new Color(41, 83, 41));
                else if (level.getData()[i] == 8) layerLevel.g.setColor(new Color(40, 80, 40));
                else if (level.getData()[i] == 9) layerLevel.g.setColor(new Color(37, 75, 37));
                if (level.getData()[i] != 0) layerLevel.g.fillRect(x * 20 + 9, y * 20 + 9, 19, 19);


                if (level.getData()[i] != -1 && level.getData()[i] != 0) {
                    layerLevel.g.setColor(new Color(20, 60, 20));
                    layerLevel.g.drawString(level.getData()[i]+"", x * 20 + 15, y * 20 + 23);
                }
            }
        }

        if (pointA != null) {
            layerLevel.g.setColor(new Color(38, 43, 150));
            layerLevel.g.fillRect(pointA.x * 20 + 9, pointA.y * 20 + 9, 19, 19);
            layerLevel.g.setColor(new Color(100, 104, 255));
            layerLevel.g.drawString("A", pointA.x * 20 + 15, pointA.y * 20 + 23);
        }

        if (pointB != null) {
            layerLevel.g.setColor(new Color(109, 33, 16));
            layerLevel.g.fillRect(pointB.x * 20 + 9, pointB.y * 20 + 9, 19, 19);
            layerLevel.g.setColor(new Color(255, 81, 46));
            layerLevel.g.drawString("B", pointB.x * 20 + 15, pointB.y * 20 + 23);
        }

        layerLevel.draw();
    }

    public void setLevel(Level level) {
        this.level = level;
        layerLevel.clear();
        drawLevel();
    }

    public void setA(Point point) {
        pointA = point;
        layerLevel.clear();
        drawLevel();
    }


    public void setB(Point point) {
        pointB = point;
        layerLevel.clear();
        drawLevel();
    }


    public void setProcessStatus(boolean b) {
        if (b) {
            label.setText("Processing...");
            label.setForeground(new Color(203, 201, 22));
        } else {
            label.setText("Wait");
            label.setForeground(new Color(20, 41, 20));
        }
    }


    public void setLabel(String text) {
        label.setText(text);
        label.setForeground(new Color(203, 201, 22));
    }


    public void drawSelect(Point from, Point point) {
        drawSelect(from, point, Color.YELLOW);
    }

    public void drawSelect(Point from, Point point, Color color) {
        layerWork.g.setColor(color);
        layerWork.g.drawRect(point.x * 20 + 9, point.y * 20 + 9, 18, 18);
        if (from != null && color == Color.YELLOW) {
            int xx = 10;
            int yy = 10;
            if (from.x < point.x) xx = 17;
            else if (from.x > point.x) xx = 3;
            if (from.y < point.y) yy = 17;
            else if (from.y > point.y) yy = 3;
            layerWork.g.drawLine(from.x * 20 + 8 + xx, from.y * 20 + 9 + yy, point.x * 20 + 8 + (20 - xx), point.y * 20 + 9 +(20 - yy));
        }
        layerWork.draw();
    }


    public void drawNumber(Point p, String s) {
        layerWork.g.setColor(Color.WHITE);
        layerWork.g.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
        int n = s.length() * 3 - 3;
        layerWork.g.drawString(s, p.x * 20 + 15 - n, p.y * 20 + 23);
        layerWork.draw();
    }


    public void clear() {
        layerWork.clear();
        layerWork.draw();
    }
}
