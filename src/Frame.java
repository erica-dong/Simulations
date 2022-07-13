import javax.swing.*;

public class Frame extends JFrame {
    public Frame() {
        this.setTitle("Simulations");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        Panel p = new Panel();
        this.add(p);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        while (true) {
            p.repaint();
        }
    }

    public static void main(String[] args) throws Exception {
        new Frame();
    }
}
