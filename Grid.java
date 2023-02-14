import javax.swing.*;
import java.awt.*;
public class Grid extends JPanel{
    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        for (int i = 1; i <= 10; i++) {
            g.setColor(Color.GREEN);
            g.drawLine(i * 50 - 1, 0, i * 50 - 1, getHeight() - 1);
            g.drawLine(0, i * 50 - 1, getWidth() - 1, i * 50 - 1);

            g.setColor(new Color(0, 128, 0));
            g.drawString(i + "", (i - 1) * 50 + (50 - 7 - 10 * (i / 10 + 1)), 14);
            g.drawString((char)(i + 64) + "", 7, (i - 1) * 50 + 43);
        }
    }
    public static boolean shipExists(int[][] grid, Ship ship, int row, int column) {
        for (int i = 0; i < ship.getLength(); i++) {
            if (ship.getRotation() && grid[row][column + i] != 0) return true;
            else if (!ship.getRotation() && grid[row + i][column] != 0) return true; 
        }
        return false; 
    }
    public static int[][] getRandomShips() {
        int[][] grid = new int[10][10];
        for (int i = 1; i <= 5; i++) {
            Ship ship = new Submarine();
            if (i == 2) ship = new Destroyer();
            else if (i == 3) ship = new Cruiser();
            else if (i == 4) ship = new Battleship();
            else if (i == 5) ship = new Carrier();
            double number = Math.random();
            if (number < 0.5) ship.Rotate();
            int row, column;
            if (ship.getRotation()) {
                row = (int)(Math.random() * 10);
                column = (int)(Math.random() * (10 - ship.getLength()));
            }
            else {
                row = (int)(Math.random() * (10 - ship.getLength()));
                column = (int)(Math.random() * 10);
            }
            if (!shipExists(grid, ship, row, column)) moveShip(grid, ship, row, column);
            else {
                i--;
                continue;
            }
        }
        print(grid);
        return grid;
    }
    public static void moveShip(int[][] grid, Ship ship, int row, int column) {
        for (int i = 0; i < ship.getLength(); i++) {
            if (ship instanceof Submarine && ship.getRotation()) grid[row][column + i] = 1;
            else if (ship instanceof Submarine && !ship.getRotation()) grid[row + i][column] = 1;
            else if (ship.getRotation()) grid[row][column + i] = ship.getLength();
            else grid[row + i][column] = ship.getLength();
        }
    }
    public static void removeShip(int[][] grid, Ship ship, int row, int column) {
        for (int i = 0; i < ship.getLength(); i++) {
            if (ship.getRotation()) grid[row][column + i] = 0;
            else grid[row + i][column] = 0;
        }
    }
    public static void print(int[][] grid) {
        System.out.println();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] < 0) System.out.print(grid[i][j] + " ");
                else System.out.print(" " + grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
