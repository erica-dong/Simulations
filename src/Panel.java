import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Panel extends JPanel implements ActionListener {
    static final int WIDTH = 1200;
    static final int HEIGHT = 600;

    Vector goal;
    public Panel()  {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(null);
        this.setBackground(new Color(146, 231, 255));
        this.setFocusable(true);
        this.addKeyListener(new PKeyAdapter());
        this.addMouseListener(new PMouseAdapter());
        this.addMouseMotionListener(new PMouseAdapter());

        goal = new Vector(0.3, 0.3);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {

        Vector[] v = {new Vector(0.5,0.5), new Vector(0.2, 0.3), new Vector(0.4, 0.5), new Vector(0.6, 0.4), new Vector(0.2, 0.1)};
        Chain c = new Chain(v);

        c.solve(goal);
        c.drawChain(g);
        g.setColor(Color.RED);
        g.fillOval((int) (goal.x*WIDTH) - 5, (int) (goal.y*HEIGHT) - 5, 10, 10);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public class PKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_1 -> System.out.println("1 pressed");
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
    public class PMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDragged(MouseEvent e) {
            Vector mousePos = new Vector(e.getX(), e.getY());
            goal = new Vector(mousePos.x/WIDTH, mousePos.y/HEIGHT);

            repaint();
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            Vector mousePos = new Vector(e.getX(), e.getY());
            goal = new Vector(mousePos.x/WIDTH, mousePos.y/HEIGHT);

            repaint();
        }

    }
    public class Chain {
        Vector[] points;
        double[] lengths;
        Vector start;

        public Chain(Vector[] points) {
            this.points = points;
            if (points.length > 0) {
                start = points[0];
            }
            else {
                start = new Vector(0, 0);
            }
            lengths = new double[points.length - 1];
            for (int i = 0; i < points.length - 1; i++) {
                lengths[i] = points[i].dist(points[i+1]);
            }
        }
        public void forward(Vector goal) {
            points[points.length-1] = goal;
            for (int i = points.length-1; i > 0; i--) {
                points[i-1] = points[i-1].sub(points[i]).normalize().mult(lengths[i-1]).add(points[i]);
            }
        }
        public void backward() {
            points[0] = start;
            for (int i = 0; i < points.length-1; i++) {
                points[i+1] = points[i+1].sub(points[i]).normalize().mult(lengths[i]).add(points[i]);
            }
        }
        public void solve(Vector goal) {
            double sum = 0;
            for (double l : lengths) {
                sum += l;
            }
            if (sum <= start.dist(goal)) {
                for (int i = 1; i < points.length; i++) {
                    points[i] = points[i-1].add(goal.sub(start).normalize().mult(lengths[i-1]));
                }
            }
            else {
                int counter = 0;
                while (points[points.length-1].dist(goal) > Math.pow(10, -10) && counter < 30) {
                    forward(goal);
                    backward();
                    counter++;
                }
            }
        }
        public void drawChain(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(5));
            g2.setColor(Color.GRAY);

            for (int i = 0; i < points.length; i++) {
                if (i != points.length-1) {
                    g.setColor(Color.GRAY);
                    g2.drawLine((int) (points[i].x*WIDTH), (int)(points[i].y*HEIGHT), (int)(points[i+1].x*WIDTH), (int)(points[i+1].y*HEIGHT));
                }
                g.setColor(Color.BLACK);
                g.fillOval((int) (points[i].x*WIDTH) - 5, (int) (points[i].y*HEIGHT) - 5, 10, 10);

            }
            g.fillOval((int) (start.x*WIDTH) - 5, (int) (start.y*HEIGHT) - 5, 10, 10);
        }
    }
    public class MassPoint {
        double x, y, mass; //x and y from 0 to 1
        Vector dir;

        public MassPoint(double x, double y, double mass, Vector dir) {
            this.x = x;
            this.y = y;
            this.mass = mass;
            this.dir = dir;
        }

        public void drawMP(Graphics g) {
            g.fillOval((int) (x*WIDTH) - 5, (int) (y*HEIGHT) - 5, 10, 10);
        }
    }
}
