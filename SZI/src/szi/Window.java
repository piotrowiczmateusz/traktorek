package szi;
import decisiontree.XMLParser;
import static decisiontree.XMLParser.getDecision;
import szi.data.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.IOException;
import static java.lang.Math.sqrt;
import static java.lang.Math.toIntExact;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import org.xml.sax.SAXException;
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
    
    // Parametry rosliny do zebrania    
    public static String fuel = "";
    public static String distance = "";
    public static String surface = "";
    public static String plantMaturity = "";
    
    public void calculateDistance(int x, int y){
        int paliwoX = 4;
        int paliwoY = 1;
        double dist = Math.abs(((x-paliwoX)*(x-paliwoX))+((y-paliwoY)*(y-paliwoY)));
        dist = sqrt(dist); 
        if(dist <= 7.3){
            distance = "near";
        }
        else if((dist>7.3)&&(dist<9.3)){
            distance = "medium";
        }
        else{
            distance = "far";
        }
        System.out.println("Distance : " + dist + " parametr: " + distance);
    }
    
    public void checkPlantMaturity(double maturity){
        // poziom dojrzalosci
        if(maturity <= 0.35){
            plantMaturity = "poorly";
        }
        else if((maturity>0.35)&&(maturity<=0.50)){
            plantMaturity = "medium";
        }
        else if((maturity>0.50)&&(maturity<=0.75)){
            plantMaturity = "well";
        }
        else if(maturity > 0.75){
            plantMaturity = "developed";
        }
        System.out.println("Poziom dojrzalosci: " + maturity);
    }

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
                try {
                    int destinationX = toIntExact(me.getX()/40);
                    int destinationY = toIntExact(me.getY()/40);
                    window.komorki[destinationX][destinationY].setCurrentObject(true);
                    // jeśli nie ma paliwa to wróć do zbiornika
                    if(fuel.equals("lack")){
                        destinationX = 4;
                        destinationY = 1;
                    }
                    AStar.runAStar(agent.getX(), agent.getY(), agent.rotation, destinationX , destinationY);
                    Czas.setStepsList(AStar.stepsList);
                    System.out.println("Cel: X: " + destinationX + ", Y: " + destinationY);
                    // jeśli kliknięto na zbiornik, zatankuj traktor
                    if ((destinationX == 4)&&(destinationY == 1)){
                        agent.refuel();
                        fuel = agent.getFuelLvl();
                    }
                    else{
                        // Ustawienie parametrów do drzewa decyzyjnego
                        fuel = agent.getFuelLvl(); // fuel
                        calculateDistance(destinationX, destinationY); // distance
                        surface = window.komorki[destinationX][destinationY].getName(); //surface                    
                        checkPlantMaturity(window.komorki[destinationX][destinationY].getPlantMaturity()); // maturity
                        
                        setParameters(fuel, distance, surface, plantMaturity);
                        agent.changeFuelLvl();
                        fuel = agent.getFuelLvl();                   
                    }
                } catch (SAXException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }
            }           
        });
         
    }
    
    public static void setParameters(String fuel, String distance, String surface, String plantMaturity) throws SAXException {
        getDecision(fuel, distance, surface, plantMaturity);
    }

    public static void main(String[] args) throws SAXException {       
       timer.scheduleAtFixedRate(agent, 10, 10);
       time.run();
       
    }


    @Override
    public void paint(Graphics g) {
        try {
            Komorka[][] komorki = map.getMap();
            for (int i = 0; i < komorki.length; i++) {
                for (int j = 0; j < komorki[0].length; j++) {
                        //System.out.println(System.getProperty("user.dir") + "\\src\\graphics\\"+ komorki[i][j].getImageName()+ ".png");
                        Image pole = new ImageIcon(System.getProperty("user.dir") + "\\src\\graphics\\testSet\\"+ komorki[i][j].getImageName() + ".png").getImage();
                        
                        g.drawImage(pole, i * 40, j * 40, null);
                }
            }
            
            Image house = new ImageIcon(System.getProperty("user.dir") + "\\src\\graphics\\testSet\\domek.png").getImage();
            g.drawImage(house, 0, 0, null);
            
            Image tractor = new ImageIcon(Agent.getIcon()).getImage();
            g.drawImage(tractor, agent.getX() * 40, agent.getY() * 40, null);
            
        } 
        catch (Exception e) {
            System.out.println("Natrafiłem na problem: " + e.toString());
        }
    }
    
}
