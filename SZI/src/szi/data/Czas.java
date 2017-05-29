/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szi.data;

/**
 *
 * @author s407332
 */
import szi.Agent;
import szi.Window;

import java.util.List;
import java.util.TimerTask;

import static szi.data.AStar.runAStar2;

public class Czas extends TimerTask {

    private static int hour = 0;
    private static int day = 1;
    private static int month = 1;
    
    private boolean direction = true;

    private static List<String> tab;
    private int positionInTab = 0;
    private static Window timeWindow;

    private static String icon;

    public void run() {
        setIcon(0);
        while (true) {
            
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
           
            if (AStar.isRunning) {                          
                moveAgent();
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            if (!AStar.isRunning) {
                timeWindow.repaint();
            }
            runAStar2();
        }
    }

    public static void addWindow(Window window) {
        timeWindow = window;
    }

    private void moveAgent() {
        String direction;
        direction = tab.get(positionInTab);
        //System.out.println("Obecny kierunek: " + direction);
        if (direction.equals(Agent.RIGHT)) {
            timeWindow.agent.moveAgent(Agent.RIGHT);
            positionInTab++;

        } else if (direction.equals(Agent.BACKWARD)) {
            timeWindow.agent.moveAgent(Agent.BACKWARD);
            positionInTab++;

        } else if (direction.equals(Agent.LEFT)) {
            timeWindow.agent.moveAgent(Agent.LEFT);
            positionInTab++;

        } else if (direction.equals(Agent.FORWARD)) {
            timeWindow.agent.moveAgent(Agent.FORWARD);
            positionInTab++;

        }
        if (positionInTab == tab.size()) {
            AStar.isRunning = false;
            positionInTab = 0;   
        }
    }

    private void setIcon(int hour) {
        icon = System.getProperty("user.dir") + "\\src\\graphics\\Info\\time " + hour + ".png";
    }

    public static String getIcon() {
        return icon;
    }

    public static void setStepsList(List<String> list) {
        tab = list;
    }
}
