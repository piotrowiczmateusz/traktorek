/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szi;

/**
 *
 * @author s407332
 */

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import szi.NeuralNetwork.NeuralNetwork;
import szi.NeuralNetwork.runNetwork;

public class Agent extends TimerTask {

    private Window window;

    public static final String WEST = "west";

    public static final String EAST = "east";
    public static final String NORTH = "north";
    public static final String SOUTH = "south";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String FORWARD = "forward";
    public static final String BACKWARD = "backward";
    
    private static String icon;
    int width = 40;
    int height = 40;
    int positionX;
    int positionY;
    int rotation;
    runNetwork runnetwork = null;
    runNetwork runnetworkRoad = null;
    
    String[] fuel = new String[9];  
    String currentFuel;
    int counter = 0;   
    
    String[] water = new String[11];  
    String currentWater;
    int counterWater = 0;   
    
    @Override
    public void run() {
    }

    public void initFuelValues(){
        fuel[0] = "full";
        fuel[1] = "full";
        fuel[2] = "much";
        fuel[3] = "much";
        fuel[4] = "half";
        fuel[5] = "half";
        fuel[6] = "little";
        fuel[7] = "little";
        fuel[8] = "lack";
        
        currentFuel = fuel[counter];
    }
    
    public void initWaterValues(){
        water[0] = "full";
        water[1] = "full";
        water[2] = "much";
        water[3] = "much";
        water[4] = "much";
        water[5] = "much";
        water[6] = "half";
        water[7] = "half";       
        water[8] = "little";
        water[9] = "little";
        water[10] = "lack";
        
        currentWater = water[counterWater];
    }
    
    public void changeFuelLvl(){
        if(currentFuel != "lack") {
            counter++;
            currentFuel = fuel[counter];
        }  
    }
    
    public void changeWaterLvl(){
        if(currentWater != "lack") {
            counterWater++;
            currentWater = water[counterWater];
        }
    }
    
    public void refuel(){
        counter = 0;
        currentFuel = fuel[counter];
    }
    
    public void refill(){
        counterWater = 0;
        currentWater = water[counterWater];
    }
    
    public Agent(int positionX, int positionY) {
        icon = System.getProperty("user.dir") + "\\src\\graphics\\testSet\\tractor-" + EAST + ".png";
        this.positionX = positionX;
        this.positionY = positionY;
        initFuelValues();
        initWaterValues();         
        try {
            runnetworkRoad = new runNetwork(0.25, 0.6, 0.2, "Droga");
            runnetwork = new runNetwork(0.2, 0.6, 0.12, "Rosliny");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addWindow(Window window) {
        this.window = window;
    }
    
    private static int[] getPixelData(BufferedImage img, int x, int y) {
        int argb = img.getRGB(x, y);

        int rgb[] = new int[] {
            (argb >> 16) & 0xff, //red
            (argb >>  8) & 0xff, //green
            (argb      ) & 0xff  //blue
        };
        System.out.println("rgb: " + rgb[0] + " " + rgb[1] + " " + rgb[2]);
        return rgb;
    }

    public void moveAgent(String way) {
        if (way.equals(LEFT)) {
            rotation = (rotation - 1 + 4) % 4;
        }
        if (way.equals(RIGHT)) {
            rotation = (rotation + 1) % 4;
        }
        String absoluteDirection = LocalToAbsolute();
        icon = System.getProperty("user.dir") + "/src/graphics/testSet/tractor-" + absoluteDirection + ".png";
        if (way.equals(FORWARD) || way.equals(BACKWARD)) {
            int i = way.equals(FORWARD) ? 1 : -1;
            if (absoluteDirection.equals(Agent.NORTH) && positionY - i >= 0 && positionY - i < window.komorki[0].length) {
                if (checkNextStep(positionX, positionY - i)) {
                    positionY -= i;
                }

            } else if (absoluteDirection.equals(
                    Agent.SOUTH) && positionY + i >= 0 && positionY + i < window.komorki[0].length) {
                if (checkNextStep(positionX, positionY + i)) {
                    positionY += i;
                }

            } else if (absoluteDirection.equals(
                    Agent.WEST) && positionX - i >= 0 && positionX - i < window.komorki.length) {
                if (checkNextStep(positionX - i, positionY)) {
                    positionX -= i;
                }

            } else if (absoluteDirection.equals(
                    Agent.EAST) && positionX - i >= 0 && positionX - i < window.komorki.length) {
                if (checkNextStep(positionX + i, positionY)) {
                    positionX += i;
                }
            }
        }
        
//        if(window.komorki[positionX][positionY].isCurrentObject()){
        if(true){
            if(window.komorki[positionX][positionY].getName().equals("Droga"))
            {
                double[] x = runnetwork.neuralNetwork.getPixels("testSet\\" + window.komorki[positionX][positionY].getImageName());

                runnetworkRoad.neuralNetwork.setInputs(x);
                double decisionRoadValue = runnetworkRoad.neuralNetwork.getOutput()[0]; 
                double rs = 0.0;
                double rm = 0.5;
                double rb = 1.0;
                double rsResult = 1.0 - Math.abs(rs - decisionRoadValue);                
                double rmResult = 1.0 - Math.abs(rm - decisionRoadValue);
                double rbResult = 1.0 - Math.abs(rb - decisionRoadValue);

                String nameOfRoad="";
                if((rsResult > rmResult) && (rsResult > rbResult)) nameOfRoad = "drodze suchej";
                if((rmResult > rsResult) && (rmResult > rbResult)) nameOfRoad = "drodze mokrej";
                if((rbResult > rsResult) && (rbResult > rmResult)) nameOfRoad = "błocie";
                System.out.println("Traktor stoi na: " + nameOfRoad);
            }
            else
            {
                double[] x = runnetwork.neuralNetwork.getPixels("testSet\\" + window.komorki[positionX][positionY].getImageName());

                runnetwork.neuralNetwork.setInputs(x);
                double decisionValue = runnetwork.neuralNetwork.getOutput()[0]; 
                System.out.println("Stan nawozu na określonym polu: " + (1-(window.komorki[positionX][positionY].getNawoz())));
                System.out.println("Decyzja nawożenia: " + decisionValue);
                if(decisionValue > 0.5){
                    window.komorki[positionX][positionY].fertilize();
                }
            }
            window.komorki[positionX][positionY].setCurrentObject(false);

        }
        repaintGraphic();
    }
    
    public Position forwardTile() {
        int i = 1;
        int x = positionX;
        int y = positionY;
        String absoluteDirection = LocalToAbsolute();
        if (absoluteDirection.equals(Agent.NORTH) && positionY - i >= 0 && positionY - i < window.komorki[0].length) {
            x -= i;          
        } else if (absoluteDirection.equals(Agent.SOUTH) && positionY + i >= 0 && positionY + i < window.komorki[0].length) {
            y += i;            
        } else if (absoluteDirection.equals(Agent.WEST) && positionX - i >= 0 && positionX - i < window.komorki.length) {
            x -= i;
        } else if (absoluteDirection.equals(Agent.EAST) && positionX - i >= 0 && positionX - i < window.komorki.length) {
            y += i;
        }
        return new Position(x, y);
    }
    
    private String LocalToAbsolute() {
        return new String[]{NORTH, EAST, SOUTH, WEST}[rotation];
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getX() {
        return this.positionX;
    }

    public int getY() {
        return this.positionY;
    }
    
    public int getRotation() {
        return rotation;
    }
    
    public String getFuelLvl(){
        return currentFuel;
    }
    
    public String getWaterLvl(){
        return currentWater;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public static void repaintGraphic() {
        Window.window.repaint();
    }

    public boolean checkNextStep(int x, int y) {
        return window.komorki[x][y].isCrossable();
    }

    public static String getIcon() {
        return icon;
    }
    
    public boolean equals(Agent agent) {
        return (this.getX() == agent.getX() && this.getY() == agent.getY() && this.getRotation() == agent.getRotation());
    }
    
    public class Position {

        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
}
