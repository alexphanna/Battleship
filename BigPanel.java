import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class BigPanel extends JLayeredPane {
    private int[][] grid;
    private ShipLayer shipLayer;
    private JPanel buttonLayer;
    public BigPanel() {
        grid = new int[10][10];
        setBounds(0, 0, 500, 500);

        Grid gridLayer = new Grid();
        gridLayer.setBounds(0, 0, 500, 500);
        gridLayer.setBackground(Color.BLACK);
        add(gridLayer, 2);

        add(new Radar(), 1);
        
        shipLayer = new ShipLayer();
        shipLayer.setBounds(0, 0, 500, 500);
        shipLayer.setLayout(null);
        shipLayer.setOpaque(false);
        add(shipLayer, 0);

        buttonLayer = new JPanel();
        buttonLayer.setBounds(0, 0, 500, 500);
        buttonLayer.setLayout(new GridLayout(10, 10));
        buttonLayer.setOpaque(false);
        buttonLayer.setVisible(false);
        for (int i = 0; i < 100; i++) {
            buttonLayer.add(new Square());
        }
        add(buttonLayer, 0);
    }
    public class Grid extends JPanel{
        public void paintComponent (Graphics g) {
            super.paintComponent(g);
    
            g.setColor(Color.GREEN);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            for (int i = 1; i < 10; i++) {
                g.drawLine(i * 50 - 1, 0, i * 50 - 1, getHeight() - 1);
                g.drawLine(0, i * 50 - 1, getWidth() - 1, i * 50 - 1);
            }
        }
    }
    public class ShipLayer extends JPanel {
        boolean locked;
        public ShipLayer() {
            locked = false;
        }
        public boolean isLocked() {
            return locked;
        }
        public void lock() {
            locked = true;
        }
    }
    public class Square extends AbstractButton {
        private Boolean isHit;
        public Square() {
            isHit = null;
            addMouseListener(new SquareClicked(this));
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;

            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(2));
            if (isHit != null) {
                g2.drawOval(10, 10, getWidth() - 21, getHeight() - 21);
            }
        }
        public void setIsHit(boolean _isHit) {
            this.isHit = _isHit;
        }
        public class SquareClicked implements MouseListener {
            private Square square;
            public SquareClicked(Square square) {
                this.square = square;
            }
            public void mousePressed(MouseEvent e) {  
                if (getButtonLayer().isVisible()) {
                    System.out.println("Click recieved!");
                    square.setIsHit(true);
                    square.repaint();
                }
            }  
            public void mouseReleased(MouseEvent e) { }  
            public void mouseClicked(MouseEvent e) { }  
            public void mouseEntered(MouseEvent e) { }  
            public void mouseExited(MouseEvent e) { }  
        }
    }
    public ShipLayer getShipLayer() {
        return shipLayer;
    }
    public JPanel getButtonLayer() {
        return buttonLayer;
    }
    public void addShip(Ship ship, int row, int column) {
        ship.setLocation(column * 50, row * 50);
        getShipLayer().add(ship);
        moveShip(ship, row, column);
    }
    public void moveShip(Ship ship, int row, int column) {
        for (int i = 0; i < ship.getLength(); i++) {
            if (ship.getRotation()) grid[row][column + i] = ship.getLength();
            else grid[row + i][column] = ship.getLength();
        }
    }
    public void removeShip(Ship ship, int row, int column) {
        for (int i = 0; i < ship.getLength(); i++) {
            if (ship.getOldRotation()) grid[row][column + i] = 0;
            else grid[row + i][column] = 0;
        }
    }
    public void print() {
        System.out.println();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}