package szi;
import decisiontree.XMLParser;
import static decisiontree.XMLParser.getDecision;
import static decisiontree.XMLParser.getDecisionWater;
import szi.data.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    
    private String MODE = "zbieranie"; // zbieranie / nawadnianie
    
    // Parametry rosliny do zebrania   
    
    public static String fuel = "";
    public static String water = "";
    public static String distance = "";
    public static String distanceWater = "";
    public static String surface = "";
    public static String plantMaturity = "";
    public static String weather = "";
    public int destinationX;
    public int destinationY;
    
    public void calculateDistance(int x, int y){
        int paliwoX = 4;
        int paliwoY = 1;
        double dist = Math.abs(((x-paliwoX)*(x-paliwoX))+((y-paliwoY)*(y-paliwoY)));
        dist = sqrt(dist); 
        if(dist <= 7.3){ distance = "near"; }
        else if((dist>7.3)&&(dist<9.3)){ distance = "medium"; }
        else{ distance = "far"; }
        System.out.println("Odległość od zbiornika paliwa: " + distance + " ("+dist + ")");
    }
    
    public void calculateDistanceWater(int x, int y){
        int wodaX = 1;
        int wodaY = 5;
        double dist = Math.abs(((x-wodaX)*(x-wodaX))+((y-wodaY)*(y-wodaY)));
        dist = sqrt(dist); 
        if(dist <= 7.3){ distanceWater = "near"; }
        else if((dist>7.3)&&(dist<9.3)){ distanceWater = "medium"; }
        else{ distanceWater = "far"; }
        System.out.println("Odległość od zbiornika wodnego: " + distanceWater + " ("+dist + ")");
    }
    
    public void checkPlantMaturity(double maturity){
        if(maturity <= 0.35){ plantMaturity = "poorly"; }
        else if((maturity>0.35)&&(maturity<=0.50)){ plantMaturity = "medium"; }
        else if((maturity>0.50)&&(maturity<=0.75)){ plantMaturity = "well"; }
        else if(maturity > 0.75){ plantMaturity = "developed"; }
        System.out.println("Poziom dojrzalości: " + plantMaturity + " ("+maturity + ")");
    }
    
    public void checkWeather(){
        int randomNum = 0 + (int)(Math.random() * 10); 
        if(randomNum <= 3.3){ weather = "rainy"; }
        else if((randomNum>3.3)&&(randomNum<=6.6)){ weather = "cloudy"; }
        else if(randomNum > 6.6){ weather = "sunny"; }
        System.out.println("Pogoda: " + weather);
    }

    public void runTraktorek() throws SAXException {
               
        checkLvls();

        AStar.runAStar(agent.getX(), agent.getY(), agent.rotation, destinationX , destinationY);
        Czas.setStepsList(AStar.stepsList);
        System.out.println("Cel: X: " + destinationX + ", Y: " + destinationY);

        if(MODE == "zbieranie") { zbieranie(); } 
        else if (MODE == "nawadnianie") { nawadnianie(); }     
    }
    
    public Window() {
        super("Agent -> Traktorek");
       
        komorki = Mapa.create();
        sizeX = komorki.length * 40;
        sizeY = komorki[0].length * 40;
        
        createWindow(); // tworzenie elementów okna
                          
        Czas.addWindow(this);
        agent.addWindow(this);
        map = new MapaKomorek(komorki);
        aStar = new AStar(this, agent);
        
        addMouseListener(new MouseAdapter() { 
            public void mousePressed(MouseEvent me) { 
                try {            
                    System.out.println("\n========================================");
                    System.out.println("Tryb: " + MODE);
                    destinationX = toIntExact(me.getX()/40);
                    destinationY = toIntExact(me.getY()/40);
                    window.komorki[destinationX][destinationY].setCurrentObject(true);
                    
                    runTraktorek();  
                } catch (SAXException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }
            }           
        });
    }
    
    public void checkLvls() throws SAXException {
        if(fuel.equals("lack")){ destinationX = 4; destinationY = 1; }  
        if(MODE == "nawadnianie") {
            if(water.equals("lack")){ destinationX = 1; destinationY = 5; } 
        } 
        if ((destinationX == 4)&&(destinationY == 1)){
            agent.refuel();
            fuel = agent.getFuelLvl();
        }  
        if ((destinationX == 1)&&(destinationY == 5)) {
            agent.refill();
            water = agent.getWaterLvl();
        }
    }
    
    public void createWindow() {
        
        // Tworzenie okna
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	setPreferredSize(new Dimension(sizeX+120, sizeY));
        
        // Panel z planszą i z przyciskami
        
        JPanel gridPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel buttons = new JPanel();
         
        rightPanel.setBackground(UIManager.getColor("Panel.background"));
        rightPanel.setPreferredSize(new Dimension(120, sizeY));
        
        buttons.setOpaque(true);
	buttons.setBackground(Color.LIGHT_GRAY);
	buttons.setForeground(Color.black);
	buttons.setPreferredSize(new Dimension(90, 110));
	buttons.setBounds(new Rectangle(new Point(100, 10), getPreferredSize()));    
        
        // Buttony

        JButton button = new JButton("Zbieranie");
        JButton button2 = new JButton("Nawadnianie");
  
        button.setVisible(true);
        button2.setVisible(true);
      
        button.setAlignmentX(Component.TOP_ALIGNMENT);
        button2.setAlignmentX(Component.TOP_ALIGNMENT);
      
        button.setBorderPainted(false);
        button2.setBorderPainted(false);
      
        button.setFocusPainted(false);
        button2.setFocusPainted(false);
    
        button.setContentAreaFilled(false);
        button2.setContentAreaFilled(false);
      
        button.setPreferredSize(new Dimension(150, 40));
        button2.setPreferredSize(new Dimension(150, 40));
        
        // Dodawanie elementów do okna
        
        buttons.add(button);
        buttons.add(button2);
        
        rightPanel.add(buttons);

        add(gridPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

	pack();
	setLocationRelativeTo(null);
	setVisible(true);
	setResizable(false);
        
        // Listenery do buttonów
      
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { MODE = "zbieranie"; System.out.println("Zmieniono tryb na ZBIERANIE."); }
          });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { MODE = "nawadnianie"; System.out.println("Zmieniono tryb na NAWADNIANIE.");}
          });
    }
    
    public void zbieranie() throws SAXException {
        
        // Ustawienie parametrów do drzewa decyzyjnego
        
        fuel = agent.getFuelLvl(); // fuel
        System.out.println("Poziom paliwa: " + fuel);
        calculateDistance(destinationX, destinationY); // distance
        surface = window.komorki[destinationX][destinationY].getName(); //surface                    
        System.out.println("Nawierzchnia: " + surface);
        checkPlantMaturity(window.komorki[destinationX][destinationY].getPlantMaturity()); // maturity
        
        System.out.println("---------------PARSER---------------");
        getDecision(fuel, distance, surface, plantMaturity);
        agent.changeFuelLvl();
        fuel = agent.getFuelLvl(); 
        
    }
    
    public void nawadnianie() throws SAXException {

        if(water.equals("lack")){
            destinationX = 1;
            destinationY = 5;
        }
        if ((destinationX == 1)&&(destinationY == 5)){
            agent.refill();
            water = agent.getWaterLvl();
        }
        
        // Ustawienie parametrów do drzewa decyzyjnego
        
        fuel = agent.getFuelLvl(); // fuel
        System.out.println("Poziom paliwa: " + fuel);
        water = agent.getWaterLvl(); // water   
        System.out.println("Poziom wody: " + water);
        calculateDistance(destinationX, destinationY); // distance
        calculateDistanceWater(destinationX, destinationY); // water_distance
        surface = window.komorki[destinationX][destinationY].getName(); //surface     
        System.out.println("Nawierzchnia: " + surface);
        checkPlantMaturity(window.komorki[destinationX][destinationY].getPlantMaturity()); // maturity
        checkWeather();
        System.out.println("---------------PARSER---------------");
        getDecisionWater(fuel, water, distance, distanceWater, surface, plantMaturity, weather);
        agent.changeFuelLvl();
        fuel = agent.getFuelLvl();
        agent.changeWaterLvl();
        water = agent.getWaterLvl();
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
