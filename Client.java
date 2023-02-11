import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Client {
    public static BigPanel grid = new BigPanel();
    public static void main(String[] args) {
        JFrame frame = new JFrame("Battleship");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel primaryPanel = new JPanel();
        primaryPanel.setLayout(new BoxLayout(primaryPanel, BoxLayout.X_AXIS));
        primaryPanel.setBounds(0, 0, 800, 500);
        frame.add(primaryPanel);

        JPanel bigPanel = new JPanel();
        bigPanel.setLayout(null);
        bigPanel.setPreferredSize(new Dimension(500, 500));
        bigPanel.add(grid);
        primaryPanel.add(bigPanel);

        JPanel smallPanel = new JPanel();
        smallPanel.setLayout(null);
        smallPanel.setPreferredSize(new Dimension(300, 500));
        smallPanel.setBackground(Color.BLACK);
        primaryPanel.add(smallPanel);

        Neon.Button confirmButton = new Neon().new Button("Confirm", new Client().new ConfirmClicked());
        confirmButton.setLocation(175, 400);
        smallPanel.add(confirmButton);

        Neon.Button switchViewButton = new Neon().new Button("Switch View", new Client().new SwitchViewClicked());
        switchViewButton.setLocation(25, 400);
        smallPanel.add(switchViewButton);

        grid.addShip(new Carrier(), 1, 1);
        grid.addShip(new Destroyer(), 3, 1);
        grid.addShip(new Battleship(), 5, 1);
        grid.addShip(new Submarine(), 7, 1);
        grid.addShip(new Cruiser(), 9, 1);

        frame.pack();
        frame.setVisible(true);
    }
    public class SwitchViewClicked implements MouseListener {
        public void mousePressed(MouseEvent e) {  
            Client.grid.getShipLayer().setVisible(!Client.grid.getShipLayer().isVisible());
            Client.grid.getButtonLayer().setVisible(!Client.grid.getButtonLayer().isVisible());
        }  
        public void mouseReleased(MouseEvent e) { }  
        public void mouseClicked(MouseEvent e) { }  
        public void mouseEntered(MouseEvent e) { }  
        public void mouseExited(MouseEvent e) { }  
    }
    public class ConfirmClicked implements MouseListener {
        public void mousePressed(MouseEvent e) {  
            Client.grid.getShipLayer().lock();
        }  
        public void mouseReleased(MouseEvent e) { }  
        public void mouseClicked(MouseEvent e) { }  
        public void mouseEntered(MouseEvent e) { }  
        public void mouseExited(MouseEvent e) { }  
    }
}