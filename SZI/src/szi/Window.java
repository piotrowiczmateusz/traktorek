package szi;

import szi.data.*;
import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{
   
    public CellMap map;
    public Cell[][] cells;   
    private int sizeX;
    private int sizeY;
    static Window window = new Window();

    public Window() {
        super("Jestę Traktorę");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        cells = Map.create();
        sizeX = cells.length * 40;
        sizeY = cells[0].length * 40;
        setSize(sizeX , sizeY);
        map = new CellMap(cells);        
    }

    public static void main(String[] args) {
       
    }

    @Override
    public void paint(Graphics g) {
        try {
            Cell[][] cells = map.getMap();
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[0].length; j++) {
                    if (cells[i][j].getName() == "ROAD") {
                        Image road = new ImageIcon(System.getProperty("user.dir") + "\\src\\graphics\\droga2.png").getImage();
                        g.drawImage(road, i * 40, j * 40, null);
                    } 
                }
            }
            
            Image house = new ImageIcon(System.getProperty("user.dir") + "\\src\\graphics\\domek2.png").getImage();
            g.drawImage(house, 0, 0, null);
            
        } 
        
        
        catch (Exception e) {
            System.out.println("Natrafiłem na problem: " + e.toString());
        }

    }

}
