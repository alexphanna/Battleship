import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Neon {
    public class Button extends AbstractButton {
        private Color color;
        public Button(String text, MouseListener l) {
            setText(text);
            setSize(100, 25);
            setColor(Color.RED);
            addMouseListener(new ButtonSelected());
            addMouseListener(l);
        }
        public Color getColor() {
            return color;
        }
        public void setColor(Color color) {
            this.color = color;
            repaint();
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(getColor());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
            int width = g.getFontMetrics().stringWidth(getText());
            g.drawString(getText(), getWidth() / 2 - width / 2, getHeight() / 2 + 5);
        }
        public class ButtonSelected implements MouseListener {
            public void mousePressed(MouseEvent e) {  
                setColor(Color.WHITE);
            }  
            public void mouseReleased(MouseEvent e) { 
                setColor(Color.RED);
            }  
            public void mouseClicked(MouseEvent e) { }  
            public void mouseEntered(MouseEvent e) { }  
            public void mouseExited(MouseEvent e) { }  
        }
    }
}
