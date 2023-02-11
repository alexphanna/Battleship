import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public abstract class Ship extends AbstractButton {
    private int length;
    private Point oldLocation;
    private boolean oldRotation;
    private boolean isSelected;
    private boolean rotation;
    public Ship(int length) {
        this.length = length;
        this.rotation = true;
        setSize(length * 50,  50);
        addMouseListener(new ShipSelected(this));
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        if (getSelected()) g.fillOval(3, 3, getWidth() - 7, getHeight() - 7);
        else g.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
        g.setColor(Color.BLUE);
        g.drawOval(3, 3, getWidth() - 7, getHeight() - 7);
    }
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    public boolean getSelected() {
        return isSelected;
    }
    public boolean getRotation() {
        return rotation;
    }
    public void setRotation(boolean rotation) {
        this.rotation = rotation;
    }
    public int getLength() {
        return length;
    }
    public void setOldLocationAndRotation(Point location, boolean isRotated) {
        oldLocation = location;
        oldRotation = isRotated;
    }
    public Point getOldLocation() {
        return oldLocation;
    }
    public boolean getOldRotation() {
        return oldRotation;
    }
    public class ShipSelected implements MouseListener {
        private Ship ship;
        private Timer timer;
        public ShipSelected(Ship ship) {
            this.ship = ship;
            timer = new Timer(1, new UpdateLocation());
        }
        public void mousePressed(MouseEvent e) {  
            if (!Client.grid.getShipLayer().isLocked()) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    ship.setOldLocationAndRotation(getLocation(), getRotation());
                    if (getX() <= 500 - getLength() * 50) {
                        Client.grid.removeShip(ship, (getY() + 25) / 50, (getX() + 25) / 50);
                    }
                    ship.setSelected(true);
                    timer.start();
                }
                else {
                    ship.setSize(ship.getHeight(), ship.getWidth());
                    setRotation(!getRotation());
                }
            }
        }  
        public void mouseReleased(MouseEvent e) { 
            if (!Client.grid.getShipLayer().isLocked() && SwingUtilities.isLeftMouseButton(e)) {
                ship.setSelected(false);
                timer.stop();
                ship.setLocation(new Point((int)((ship.getX() + 25) / 50) * 50, (int)((ship.getY() + 25) / 50) * 50));
                Client.grid.moveShip(ship, (ship.getY() + 25) / 50, (ship.getX() + 25) / 50);
                Client.grid.print();
            }
        }  
        public class UpdateLocation implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (Client.grid.getShipLayer().getMousePosition() != null) {
                    Point mouse = Client.grid.getShipLayer().getMousePosition();
                    if ((mouse.getY() - ship.getHeight() / 2 < 0 || mouse.getY() + ship.getHeight() / 2 > 500)
                        && (mouse.getX() - ship.getWidth() / 2 < 0 || mouse.getX() + ship.getWidth() / 2 > 500))
                        ship.setLocation(ship.getX(), ship.getY());
                    else if (mouse.getY() - ship.getHeight() / 2 < 0) ship.setLocation((int)(mouse.getX() - ship.getWidth() / 2), 0);
                    else if (mouse.getY() + ship.getHeight() / 2 > 500) ship.setLocation((int)(mouse.getX() - ship.getWidth() / 2), 500 - ship.getHeight());
                    else if (mouse.getX() - ship.getWidth() / 2 < 0) ship.setLocation(0, (int)(mouse.getY() - ship.getHeight() / 2));
                    else if (mouse.getX() + ship.getWidth() / 2 > 500) ship.setLocation(500 - ship.getWidth(), (int)(mouse.getY() - ship.getHeight() / 2));
                    else ship.setLocation((int)(mouse.getX() - ship.getWidth() / 2), (int)(mouse.getY() - ship.getHeight() / 2));
                    Client.grid.getShipLayer().repaint();
                }
            }

        }
        public void mouseClicked(MouseEvent e) { }  
        public void mouseEntered(MouseEvent e) { }  
        public void mouseExited(MouseEvent e) { }  
    }
}