package szi;


import szi.data.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.IOException;
import static java.lang.Math.toIntExact;
import java.util.Arrays;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import plants.Buraki;


public class Window extends JFrame{
   
    public MapaKomorek map;
    public static Agent agent = new Agent(2, 4);
    static java.util.Timer timer = new java.util.Timer();
    public Komorka[][] komorki;   
    private int sizeX;
    private int sizeY;
    static Window window = new Window();
    static Czas time = new Czas();
    private AStar aStar;

    public Window() {
        super("Agent -> Traktorek");
        agent.addWindow(this);
        Czas.addWindow(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        komorki = Mapa.create();
        sizeX = komorki.length * 40;
        sizeY = komorki[0].length * 40;
        setSize(sizeX , sizeY);
        map = new MapaKomorek(komorki);
        aStar = new AStar(this, agent);
        addMouseListener(new MouseAdapter() { 
          public void mousePressed(MouseEvent me) { 
            int destinationX = toIntExact(me.getX()/40);
            int destinationY = toIntExact(me.getY()/40);
            
            window.komorki[destinationX][destinationY].setCurrentObject(true);
            AStar.runAStar(agent.getX(), agent.getY(), agent.rotation, destinationX , destinationY);
            Czas.setStepsList(AStar.stepsList);
            System.out.println("Cel: X: " + destinationX + ", Y: " + destinationY);
          } 
        });
    }

    public static void main(String[] args) {
       timer.scheduleAtFixedRate(agent, 10, 10);
       time.run();
    }

    @Override
    public void paint(Graphics g) {
        try {
            Komorka[][] komorki = map.getMap();
            int radius = 10;
            for (int i = 0; i < komorki.length; i++) {
                for (int j = 0; j < komorki[0].length; j++) {
                        Image pole = new ImageIcon(System.getProperty("user.dir") + "\\src\\graphics\\"+ komorki[i][j].getName().toLowerCase() + ".png").getImage();
                        
                        g.drawImage(pole, i * 40, j * 40, null);
                        String[] names = {"Droga","Drzewo", "Trawa", "Kamien", "Dom"}; 
                        if(!Arrays.asList(names).contains(komorki[i][j].getName())){
                            float nawoz = (float) komorki[i][j].getNawoz();
                            g.setColor(new Color((1-nawoz),nawoz,0.0f));
                            g.fillOval((i+1) * 40 - radius, (j+1) * 40 - radius, radius, radius);
                        }
                      
                }
            }
            
            Image house = new ImageIcon(System.getProperty("user.dir") + "\\src\\graphics\\domek.png").getImage();
            g.drawImage(house, 0, 0, null);
            
            Image tractor = new ImageIcon(Agent.getIcon()).getImage();
            g.drawImage(tractor, agent.getX() * 40, agent.getY() * 40, null);
            
        } 
        catch (Exception e) {
            System.out.println("Natrafiłem na problem: " + e.toString());
        }
    }
}
