import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Client {
    private static JFrame frame = new JFrame("Battleship");
    public static int[][] grid = Grid.getRandomShips();
    private static int[][] enemyGrid = Grid.getRandomShips(); 
    private static int[][] enemyTorpedoGrid = new int[10][10];
    public static BigPanel mainPanel = new BigPanel(enemyTorpedoGrid);
    public static UserInterface.Button confirmButton = new UserInterface().new Button("Confirm");
    private static UserInterface.Chat chat = new UserInterface().new Chat();
    public static void main(String[] args) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new MainMenu());
        frame.pack();
        frame.setVisible(true);
    }
    public static void start() {
        JPanel primaryPanel = new JPanel();
        primaryPanel.setLayout(new BoxLayout(primaryPanel, BoxLayout.X_AXIS));
        primaryPanel.setBounds(0, 0, 800, 500);
        frame.add(primaryPanel);

        JPanel bigPanel = new JPanel();
        bigPanel.setLayout(null);   
        bigPanel.setPreferredSize(new Dimension(500, 500));
        bigPanel.add(mainPanel);
        primaryPanel.add(bigPanel);

        JPanel smallPanel = new JPanel();
        smallPanel.setLayout(null);
        smallPanel.setPreferredSize(new Dimension(300, 500));
        smallPanel.setBackground(Color.BLACK);
        primaryPanel.add(smallPanel);

        UserInterface.Button switchViewButton = new UserInterface().new Button("Switch View");
        switchViewButton.addMouseListener(new Client().new SwitchViewClicked(switchViewButton));
        switchViewButton.setLocation(25, 400);
        smallPanel.add(switchViewButton);

        confirmButton.setEnabled(true);
        confirmButton.addMouseListener(new Client().new ConfirmClicked(confirmButton, switchViewButton));
        confirmButton.setLocation(175, 400);
        smallPanel.add(confirmButton);

        chat.setLocation(25, 25);
        smallPanel.add(chat);

        frame.pack();
        frame.setVisible(true);
    }
    public static void SwitchView() {
        Client.mainPanel.getShipLayer().setVisible(!Client.mainPanel.getShipLayer().isVisible());
        Client.mainPanel.getButtonLayer().setVisible(!Client.mainPanel.getButtonLayer().isVisible());
        Client.mainPanel.getShipLayer().getOverlay().repaint();
    }
    public class SwitchViewClicked implements MouseListener {
        private UserInterface.Button button;
        public SwitchViewClicked(UserInterface.Button button) {
            this.button = button;
        }
        public void mousePressed(MouseEvent e) {  
            if (button.isEnabled()) SwitchView();
        }  
        public void mouseReleased(MouseEvent e) { }  
        public void mouseClicked(MouseEvent e) { }  
        public void mouseEntered(MouseEvent e) { }  
        public void mouseExited(MouseEvent e) { }  
    }
    public class ConfirmClicked implements MouseListener {
        private UserInterface.Button button;
        private UserInterface.Button switchViewButton;
        public ConfirmClicked(UserInterface.Button button, UserInterface.Button switchViewButton) {
            this.button = button;
            this.switchViewButton = switchViewButton;
        }
        public void mousePressed(MouseEvent e) {  
            if (button.isEnabled()) {
                mainPanel.getShipLayer().setComponentZOrder(mainPanel.getShipLayer().getOverlay(), 0);
                if (mainPanel.getShipLayer().isVisible()) {
                    Client.mainPanel.getShipLayer().lock();
                    switchViewButton.setEnabled(true);
                    Grid.print(grid);
                    Client.mainPanel.getShipLayer().setVisible(false);
                    Client.mainPanel.getButtonLayer().setVisible(true);
                    button.setText("Fire");
                    button.setEnabled(false);
                }
                else {
                    int row = mainPanel.getButtonLayer().getSquare().getY() / 50;
                    int column = mainPanel.getButtonLayer().getSquare().getX() / 50;
                    if (enemyGrid[row][column] != 0) {
                        mainPanel.getButtonLayer().getSquare().setColor(Color.RED);
                        boolean hasX = true;
                        for (int r = 0; r < enemyGrid.length; r++)
                            for (int c = 0; c < enemyGrid[0].length; c++)
                                if (!(row == r && column == c) && enemyGrid[r][c] == enemyGrid[row][column]) hasX = false;
                        enemyGrid[row][column] *= -1;
                        if (hasX) {
                            String str = "You have sunk a ";
                            if (Math.abs(enemyGrid[row][column]) == 1) str += "submarine";
                            else if (Math.abs(enemyGrid[row][column]) == 2) str += "destroyer";
                            else if (Math.abs(enemyGrid[row][column]) == 3) str += "cruiser";
                            else if (Math.abs(enemyGrid[row][column]) == 4) str += "battleship";
                            else if (Math.abs(enemyGrid[row][column]) == 5) str += "carrier";
                            chat.getOutput().print(str);
                            for (int r = 0; r < enemyGrid.length; r++)
                                for (int c = 0; c < enemyGrid[0].length; c++)
                                    if (enemyGrid[r][c] == enemyGrid[row][column]) mainPanel.getButtonLayer().getSquareAt(r, c).setHasX(true);
                        }
                        else chat.getOutput().print("You have hit a ship at " + (char)(row + 65) + (column + 1));    
                    }
                    else mainPanel.getButtonLayer().getSquare().setColor(Color.WHITE);
                    mainPanel.getButtonLayer().repaint();
                    mainPanel.getButtonLayer().setSquare(null);
                    Grid.print(enemyGrid);
                    int randomRow, randomColumn;
                    do {
                        randomRow = (int)(Math.random() * 10);
                        randomColumn = (int)(Math.random() * 10);
                    } while (enemyTorpedoGrid[randomRow][randomColumn] == 1);
                    enemyTorpedoGrid[randomRow][randomColumn] = 1;
                    if (grid[randomRow][randomColumn] != 0) chat.getOutput().print("The Opponent has hit your ship at " + (char)(randomRow + 65) + (randomColumn + 1));
                }
            }
        }  
        public void mouseReleased(MouseEvent e) { }  
        public void mouseClicked(MouseEvent e) { }  
        public void mouseEntered(MouseEvent e) { }  
        public void mouseExited(MouseEvent e) { }  
    }
}