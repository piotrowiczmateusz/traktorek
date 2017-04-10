package szi;

import szi.data.*;
import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{
   
    public MapaKomorek map;
    public Komorka[][] komorki;   
    private int sizeX;
    private int sizeY;
    static Window window = new Window();

    public Window() {
        super("Agent -> Traktorek");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        komorki = Mapa.create();
        sizeX = komorki.length * 40;
        sizeY = komorki[0].length * 40;
        setSize(sizeX , sizeY);
        map = new MapaKomorek(komorki);        
    }

    public static void main(String[] args) {
       
    }

    @Override
    public void paint(Graphics g) {
        try {
            Komorka[][] komorki = map.getMap();
            for (int i = 0; i < komorki.length; i++) {
                for (int j = 0; j < komorki[0].length; j++) {
                    if (komorki[i][j].getName() == "Trawa") {
                        Image trawa = new ImageIcon(System.getProperty("user.dir") + "\\src\\graphics\\trawa.png").getImage();
                        g.drawImage(trawa, i * 40, j * 40, null);
                    } 
                    else if (komorki[i][j].getName() == "Droga") {
                        Image droga = new ImageIcon(System.getProperty("user.dir") + "\\src\\graphics\\droga.png").getImage();
                        g.drawImage(droga, i * 40, j * 40, null);
                    }
                    else if (komorki[i][j].getName() == "Drzewo") {
                        Image droga = new ImageIcon(System.getProperty("user.dir") + "\\src\\graphics\\drzewo.png").getImage();
                        g.drawImage(droga, i * 40, j * 40, null);
                    }
                    else if (komorki[i][j].getName() == "Buraki") {
                        Image droga = new ImageIcon(System.getProperty("user.dir") + "\\src\\graphics\\buraki.png").getImage();
                        g.drawImage(droga, i * 40, j * 40, null);
                    }
                    else if (komorki[i][j].getName() == "Kamien") {
                        Image droga = new ImageIcon(System.getProperty("user.dir") + "\\src\\graphics\\Kamien.png").getImage();
                        g.drawImage(droga, i * 40, j * 40, null);
                    }
                }
            }
            
            Image house = new ImageIcon(System.getProperty("user.dir") + "\\src\\graphics\\domek.png").getImage();
            g.drawImage(house, 0, 0, null);
            
        } 
        catch (Exception e) {
            System.out.println("Natrafiłem na problem: " + e.toString());
        }
    }
}
