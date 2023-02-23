import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class MainMenu extends JPanel {
    public MainMenu() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(500, 500));
        setLayout(null);

        UserInterface.Button singleplayer = new UserInterface().new Button("Singleplayer");
        singleplayer.setLocation(200, 200);
        singleplayer.setEnabled(true);
        singleplayer.addMouseListener(new SingleplayerClicked());
        add(singleplayer);

        UserInterface.Button multiplayer = new UserInterface().new Button("Multiplayer");
        multiplayer.setLocation(200, 275);
        add(multiplayer);
    }
    public class SingleplayerClicked implements MouseListener {
        public void mousePressed(MouseEvent e) { 
            getParent().remove(0);
            Client.start();
        }  
        public void mouseReleased(MouseEvent e) { }  
        public void mouseClicked(MouseEvent e) { }  
        public void mouseEntered(MouseEvent e) { }  
        public void mouseExited(MouseEvent e) { }  
    }
}