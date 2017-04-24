package szi;

import szi.data.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.lang.Math.toIntExact;

public class Window extends JFrame{
   
    public MapaKomorek map;
    public static Agent agent = new Agent(0, 4);
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
            
           AStar.runningChange();
            AStar.runAStar(agent.getX(), agent.getY(), destinationX, destinationY);
            Czas.setStepsList(AStar.stepsList);
            System.out.println(destinationX + ", " + destinationY);
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
                        Image droga = new ImageIcon(System.getProperty("user.dir") + "\\src\\graphics\\sloneczniki.png").getImage();
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
            
            Image tractor = new ImageIcon(Agent.getIcon()).getImage();
            g.drawImage(tractor, agent.getX() * 40, agent.getY() * 40, null);
            
        } 
        catch (Exception e) {
            System.out.println("Natrafiłem na problem: " + e.toString());
        }
    }
}
