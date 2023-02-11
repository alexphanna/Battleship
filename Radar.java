import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Radar extends JPanel{
    private Timer timer;
    private int angle;
    private int x, y;
    public Radar() {
        setBounds(0, 0, 500, 500);
        setOpaque(false);
        timer = new Timer(5, new UpdateLocation(this));
        timer.start();
    }
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        g.drawLine(250, 250, 250 + x, 250 - y);
    }
    public class UpdateLocation implements ActionListener {
        private Radar radar;
        public UpdateLocation(Radar radar) {
            this.radar = radar;
        }
        public void actionPerformed(ActionEvent e) {
            angle--;
            x = (int)(Math.cos(Math.toRadians(angle)) * (Math.sqrt(Math.pow(500, 2) + Math.pow(500, 2)) / 2));
            y = (int)(Math.sin(Math.toRadians(angle)) * (Math.sqrt(Math.pow(500, 2) + Math.pow(500, 2)) / 2));
            repaint();
        }
    }
}
