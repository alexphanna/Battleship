import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public abstract class AbstractShip extends AbstractButton {
    private int length;
    private boolean rotation;
    private boolean selected;
    private Point oldLocation;
    private boolean oldRotation;
    public AbstractShip(int length) {
        this.length = length;
        this.rotation = true;
        this.selected = false;
        setSize(length * 50,  50);
        addMouseListener(new ShipSelected(this));
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        if (isSelected()) g.fillOval(3, 3, getWidth() - 7, getHeight() - 7);
        else g.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
        g.setColor(Color.BLUE);
        g.drawOval(3, 3, getWidth() - 7, getHeight() - 7);
    }

    public void setSelected(boolean selected) { this.selected = selected; }
    public boolean isSelected() { return selected; }

    public int getLength() {  return length; }
    public void setOldLocation(Point location) { oldLocation = location; }
    public Point getOldLocation() { return oldLocation; }

    public void Rotate() {
        if (rotation) setSize(50, getLength() * 50);
        else setSize(getLength() * 50, 50);
        rotation = !rotation;
    }
    public boolean getRotation() { return rotation; }
    public void setOldRotation(boolean rotation) { oldRotation = rotation; }
    public boolean getOldRotation() { return oldRotation; }

    public boolean exists(int[][] grid, int row, int column) {
        for (int i = 0; i < getLength(); i++) {
            if (getRotation() && grid[row][column + i] != 0) return true;
            else if (!getRotation() && grid[row + i][column] != 0) return true; 
        }
        return false; 
    }
    public void move(int[][] grid, int row, int column) {
        for (int i = 0; i < getLength(); i++) {
            if (this instanceof Ship.Submarine && getRotation()) grid[row][column + i] = 1;
            else if (this instanceof Ship.Submarine && !getRotation()) grid[row + i][column] = 1;
            else if (getRotation()) grid[row][column + i] = getLength();
            else grid[row + i][column] = getLength();
        }
    }
    public void remove(int[][] grid, int row, int column) {
        for (int i = 0; i < getLength(); i++) {
            if (getRotation()) grid[row][column + i] = 0;
            else grid[row + i][column] = 0;
        }
    }
    public class ShipSelected implements MouseListener {
        private AbstractShip ship;
        private Timer timer;
        public ShipSelected(AbstractShip ship) {
            this.ship = ship;
            timer = new Timer(1, new UpdateLocation());
        }
        public void mousePressed(MouseEvent e) {  
            if (!Client.mainPanel.getShipLayer().isLocked()) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    ship.setOldLocation(getLocation());
                    ship.setOldRotation(getRotation());
                    ship.remove(Client.grid, (getY() + 25) / 50, (getX() + 25) / 50);
                    ship.setSelected(true);
                    getParent().setComponentZOrder(ship, 0);
                    timer.start();
                }
                else if (ship.isSelected() && Client.mainPanel.getShipLayer().getMousePosition() != null) {
                    Rotate();
                }
            }
        }  
        public void mouseReleased(MouseEvent e) { 
            if (!Client.mainPanel.getShipLayer().isLocked() && SwingUtilities.isLeftMouseButton(e)) {
                ship.setSelected(false);
                timer.stop();
                if (ship.exists(Client.grid, (ship.getY() + 25) / 50, (ship.getX() + 25) / 50)) {
                    if (getOldRotation() != getRotation()) Rotate();
                    ship.setLocation(getOldLocation());
                    ship.move(Client.grid, (ship.getY() + 25) / 50, (ship.getX() + 25) / 50);
                }
                else {
                    ship.setLocation(new Point((int)((ship.getX() + 25) / 50) * 50, (int)((ship.getY() + 25) / 50) * 50));
                    ship.move(Client.grid, (ship.getY() + 25) / 50, (ship.getX() + 25) / 50);
                }
            }
        }  
        public class UpdateLocation implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (Client.mainPanel.getShipLayer().getMousePosition() != null) {
                    Point mouse = Client.mainPanel.getShipLayer().getMousePosition();
                    if ((mouse.getY() - ship.getHeight() / 2 < 0 || mouse.getY() + ship.getHeight() / 2 > 500)
                        && (mouse.getX() - ship.getWidth() / 2 < 0 || mouse.getX() + ship.getWidth() / 2 > 500))
                        ship.setLocation(ship.getX(), ship.getY());
                    else if (mouse.getY() - ship.getHeight() / 2 < 0) ship.setLocation((int)(mouse.getX() - ship.getWidth() / 2), 0);
                    else if (mouse.getY() + ship.getHeight() / 2 > 500) ship.setLocation((int)(mouse.getX() - ship.getWidth() / 2), 500 - ship.getHeight());
                    else if (mouse.getX() - ship.getWidth() / 2 < 0) ship.setLocation(0, (int)(mouse.getY() - ship.getHeight() / 2));
                    else if (mouse.getX() + ship.getWidth() / 2 > 500) ship.setLocation(500 - ship.getWidth(), (int)(mouse.getY() - ship.getHeight() / 2));
                    else ship.setLocation((int)(mouse.getX() - ship.getWidth() / 2), (int)(mouse.getY() - ship.getHeight() / 2));
                    ship.repaint();
                }
            }
        }
        public void mouseClicked(MouseEvent e) { }  
        public void mouseEntered(MouseEvent e) { }  
        public void mouseExited(MouseEvent e) { }  
    }
}