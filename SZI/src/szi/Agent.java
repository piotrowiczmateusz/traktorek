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
import java.util.TimerTask;

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

    public void run() {}

    public Agent(int positionX, int positionY) {
        icon = System.getProperty("user.dir") + "\\src\\graphics\\tractor-" + EAST + ".png";
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void addWindow(Window window) {
        this.window = window;
    }

    public void moveAgent(String way) {
        if (way.equals(LEFT)) {
            rotation = (rotation - 1 + 4) % 4;
        }
        if (way.equals(RIGHT)) {
            rotation = (rotation + 1) % 4;
        }
        String absoluteDirection = LocalToAbsolute();
        icon = System.getProperty("user.dir") + "/src/graphics/tractor-" + absoluteDirection + ".png";
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
        System.out.println("Obecna pozycja: X: " + positionX + ", Y: " + positionY);
        repaintGraphic();

    }
    
    public Position forwardTile() {
        int i = 1;
        int x = positionX;
        int y = positionY;
        String absoluteDirection = LocalToAbsolute();
        if (absoluteDirection.equals(Agent.NORTH) && positionY - i >= 0 && positionY - i < window.komorki[0].length) {
            x -= i;


        } else if (absoluteDirection.equals(
                Agent.SOUTH) && positionY + i >= 0 && positionY + i < window.komorki[0].length) {
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
