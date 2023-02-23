import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class UserInterface {
    public class Button extends AbstractButton {
        private Color color;
        private boolean enabled;
        public Button(String text) {
            enabled = false;
            setText(text);
            setSize(100, 25);
            if (enabled) setColor(Color.RED);
            else setColor(Color.GRAY);
            addMouseListener(new ButtonSelected());
        }
        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
            if (enabled) setColor(Color.RED);
            else setColor(Color.GRAY);
        }
        public Color getColor() { return color; }
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
                if (isEnabled()) {
                    setColor(Color.WHITE);
                }
            }  
            public void mouseReleased(MouseEvent e) { 
                if (isEnabled()) {
                    setColor(Color.RED);
                }
            }  
            public void mouseClicked(MouseEvent e) { }  
            public void mouseEntered(MouseEvent e) { }  
            public void mouseExited(MouseEvent e) { }  
        }
    }
    public class Chat extends JPanel {
        private Output output;
        public Chat() {
            output = new Output();
            setSize(250, 300);
            setOpaque(false);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            add(output);
            add(new Input());

        }
        public Output getOutput() {
            return output;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.RED);
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
        }
        public class Output extends JPanel {
            public Output() {
                setMaximumSize(new Dimension(240, 275));
                setAlignmentX(CENTER_ALIGNMENT);
                setOpaque(false);
                setLayout(new GridLayout(15, 1));
                for (int i = 0; i < 15; i++) {
                    JLabel label = new JLabel();
                    label.setForeground(Color.WHITE);
                    add(label);
                }
            }
            public void print(String str) {
                if (getEmptyLabel() != null) getEmptyLabel().setText(str); 
                else { 
                    moveLabelsUp();
                    getEmptyLabel().setText(str); 
                }
            }
            public JLabel getEmptyLabel() {
                for (Component component : getComponents()) {
                    JLabel label = (JLabel)component;
                    if (label.getText().isEmpty()) return label;
                }
                return null;
            }
            public void moveLabelsUp() {
                for (int i = 1; i < getComponents().length; i++) {
                    JLabel label = (JLabel)getComponent(i);
                    ((JLabel)getComponent(i - 1)).setText(label.getText());
                }
                ((JLabel)getComponent(getComponents().length - 1)).setText("");;
            }
        }
        public class Input extends JTextField {
            public Input() {
                setMinimumSize(new Dimension(250, 25));
                setMaximumSize(new Dimension(250, 25));
                setBorder(null);
                setOpaque(false);
                setForeground(Color.WHITE);
                setLayout(null);   
                addActionListener(new InputEntered());
            }
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
    
                g.setColor(Color.RED);
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
            }
            public class InputEntered implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    ((Chat)getParent()).getOutput().print(getText());
                    setText("");
                }
            }
        }
    }
}
