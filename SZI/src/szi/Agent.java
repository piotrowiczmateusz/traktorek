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

    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String UP = "up";
    public static final String DOWN = "down";

    private static String icon;

    int width = 40;
    int height = 40;
    //IMAGE
    int positionX;
    int positionY;

    public void run() {
        //nop
    }

    public Agent(int positionX, int positionY) {
        icon = System.getProperty("user.dir") + "\\src\\graphics\\tractor-" + RIGHT + ".png";
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void addWindow(Window window) {
        this.window = window;
    }

    public void moveAgent(String way) {
        icon = System.getProperty("user.dir") + "\\src\\graphics\\tractor-" + way + ".png";
        if (way.equals(UP) && positionY > 1) {
            if (checkNextStep(UP)) {
                positionY--;
            }

        } else if (way.equals(DOWN) && positionY < 13) {
            if (checkNextStep(DOWN)) {
                positionY++;
            }

        } else if (way.equals(LEFT) && positionX > 0) {
            if (checkNextStep(LEFT)) {
                positionX--;
            }

        } else if (way.equals(RIGHT) && positionX < 23) {
            if (checkNextStep(RIGHT)) {
                positionX++;
            }
        }
        repaintGraphic();
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

    public static void repaintGraphic() {
        Window.window.repaint();
    }

    public boolean checkNextStep(String direction) {
        if (direction.equals(UP)) {
            if (window.komorki[positionX][positionY - 1].isCrossable()) {
                return true;
            }

        } else if (direction.equals(DOWN)) {
            if (window.komorki[positionX][positionY + 1].isCrossable()) {
                return true;
            }

        } else if (direction.equals(LEFT)) {
            if (window.komorki[positionX - 1][positionY].isCrossable()) {
                return true;
            }

        } else if (direction.equals(RIGHT)) {
            if (window.komorki[positionX + 1][positionY].isCrossable()) {
                return true;
            }
        }
        return false;
    }

    public static String getIcon() {
        return icon;
    }
}
