import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class BigPanel extends JLayeredPane {
    private ShipLayer shipLayer;
    private ButtonLayer buttonLayer;
    public BigPanel(int[][] grid) {
        setBounds(0, 0, 500, 500);

        Grid gridLayer = new Grid();
        gridLayer.setBounds(0, 0, 500, 500);
        gridLayer.setBackground(Color.BLACK);
        add(gridLayer, 2);
        
        shipLayer = new ShipLayer(grid);
        shipLayer.setBounds(0, 0, 500, 500);
        shipLayer.setLayout(null);
        shipLayer.setOpaque(false);
        add(shipLayer, 0);

        buttonLayer = new ButtonLayer();
        buttonLayer.setBounds(0, 0, 500, 500);
        buttonLayer.setLayout(new GridLayout(10, 10));
        buttonLayer.setOpaque(false);
        buttonLayer.setVisible(false);
        for (int i = 0; i < 100; i++) {
            Square square = new Square();
            buttonLayer.add(square);
        }
        add(buttonLayer, 0);

        addShips(Client.grid);
    }
    public class ShipLayer extends JPanel {
        private boolean locked;
        private Overlay overlay;
        public ShipLayer(int[][] temp) {
            locked = false;
            overlay = new Overlay(temp);
            add(overlay);
        }
        public boolean isLocked() {
            return locked;
        }
        public void lock() {
            locked = true;
        }
        public Overlay getOverlay() {
            return overlay;
        }
        public class Overlay extends JPanel {
            int[][] overlay;
            public Overlay(int[][] overlay) {
                this.overlay = overlay;
                setBounds(0, 0, 500, 500);
                setOpaque(false);
            }
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g;

                g2.setStroke(new BasicStroke(2));
                if (overlay != null) {
                    for (int row = 0; row < overlay.length; row++) {
                        for (int column = 0; column < overlay[0].length; column++) {
                            if (overlay[row][column] == 1) {
                                if (Client.grid[row][column] != 0) g2.setColor(Color.RED);
                                else g2.setColor(Color.WHITE);
                                g2.drawOval(column * 50 + 10, row * 50 + 10, 30, 30);
                            }
                        }
                    }

                }
            }
        }
    }
    public class ButtonLayer extends JPanel {
        Square square;
        public void setSquare(Square square) {
            this.square = square;
        }
        public Square getSquare() {
            return square;
        }
        public Square getSquareAt(int row, int column) {
            return (Square)getComponent(row * 10 + column);
        }
    }
    public class Square extends AbstractButton {
        private Color color;
        private boolean hasX;
        public Square() {
            color = null;
            hasX = false;
            addMouseListener(new SquareClicked(this));
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(2));
            if (color != null) {
                g2.setColor(color);
                if (hasX) { 
                    g2.drawLine(14, 14, getWidth() - 15, getHeight() - 15);
                    g2.drawLine(getWidth() - 15, 14, 14, getHeight() - 15);
                }
                g2.drawOval(10, 10, getWidth() - 20, getHeight() - 20);
            }
        }
        public class SquareClicked implements MouseListener {
            private Square square;
            public SquareClicked(Square square) {
                this.square = square;
            }
            public void mousePressed(MouseEvent e) {  
                if (getButtonLayer().isVisible() && square.color == null) {
                    if (getButtonLayer().getSquare() != null) {
                        getButtonLayer().getSquare().setColor(null);
                        getButtonLayer().getSquare().repaint();
                    }
                    getButtonLayer().setSquare(square);
                    square.setColor(Color.GREEN);
                    square.repaint();
                    Client.confirmButton.setEnabled(true);
                }
            }  
            public void mouseReleased(MouseEvent e) { }  
            public void mouseClicked(MouseEvent e) { }  
            public void mouseEntered(MouseEvent e) { }  
            public void mouseExited(MouseEvent e) { }  
        }
        public void setColor(Color color) { this.color = color; }
        public void setHasX(boolean hasX) { this.hasX = hasX; }
    }
    public ShipLayer getShipLayer() {
        return shipLayer;
    }
    public ButtonLayer getButtonLayer() {
        return buttonLayer;
    }
    
    public void addShip(Ship ship, int row, int column) {
        ship.setLocation(column * 50, row * 50);
        getShipLayer().add(ship);
    }
    public void addShips(int[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            int counter = 1;
            for (int column = 0; column < grid[0].length - 1; column++) {
                if (grid[row][column] != 0 && grid[row][column] == grid[row][column + 1]) counter++; 
                if ((grid[row][column] == 1 && counter == 3) || (grid[row][column] != 1 && counter == grid[row][column])) {
                    if (grid[row][column] == 1) addShip(new Submarine(), row, column - counter + 2);
                    else addShip(new Ship(grid[row][column]), row, column - counter + 2);
                    counter = 1;                        
                }
            }
        }
        for (int column = 0; column < grid[0].length; column++) {
            int counter = 1;
            for (int row = 0; row < grid.length - 1; row++) {
                if (grid[row][column] != 0 && grid[row][column] == grid[row + 1][column]) counter++; 
                if ((grid[row][column] == 1 && counter == 3) || (grid[row][column] != 1 && counter == grid[row][column])) {
                    Ship temp = new Ship(grid[row][column]);
                    if (grid[row][column] == 1) temp = new Ship(3);
                    temp.Rotate();
                    addShip(temp, row - counter + 2, column);
                    counter = 1;
                }
            }
        }
    }
}