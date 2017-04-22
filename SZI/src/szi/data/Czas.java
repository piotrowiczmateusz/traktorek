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

public class Czas extends TimerTask {

    private static int hour = 0;
    private static int day = 1;
    private static int month = 1;
    private static String dayOrNight;
    public static int counter = 0;

    private boolean direction = true;

    private int tabForeward[] = {2,2,2,2,1,4,4,4,4,1,2,2,2,2,1,4,4,4,1,2,2,2,1,4,4,4,1,1,2,2,2,1,4,4,4,1,2,2,2,1,4,4,4,4,1,2,2,2,2};
    private int tabBack[] =     {4,4,4,4,3,2,2,2,2,3,4,4,4,3,2,2,2,3,4,4,4,3,3,2,2,2,3,4,4,4,3,2,2,2,3,4,4,4,4,3,2,2,2,2,3,4,4,4,4};
    private static List<String> tab;
    private int positionInTab = 0;
    private static Window timeWindow;

    private static String icon;

    public void run() {
        setIcon(0);
        while (true) {
            nextHour();
            checkDate();
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            if (AStar.isRunning) {
                moveAgent();
            }
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            if (!AStar.isRunning) {
                timeWindow.repaint();
            }
            counter++;
        }
    }

    public static void addWindow(Window window) {
        timeWindow = window;
    }

    private void moveAgent() {
        String direction;
//        if (this.direction){
//            direction = tabForeward[positionInTab];
//        }
//        else {
//            direction = tabBack[positionInTab];
//        }
        direction = tab.get(positionInTab);
        if (direction.equals(Agent.RIGHT)) {
            timeWindow.agent.moveAgent(Agent.RIGHT);
            positionInTab++;

        } else if (direction.equals(Agent.DOWN)) {
            timeWindow.agent.moveAgent(Agent.DOWN);
            positionInTab++;

        } else if (direction.equals(Agent.LEFT)) {
            timeWindow.agent.moveAgent(Agent.LEFT);
            positionInTab++;

        } else if (direction.equals(Agent.UP)) {
            timeWindow.agent.moveAgent(Agent.UP);
            positionInTab++;

        }
        if (positionInTab == tab.size()) {
            positionInTab = 0;
            //this.direction = !this.direction;
            AStar.runningChange();
        }
    }

    private void nextHour() {
        hour++;
        if (hour > 5 && hour < 21) {
            dayOrNight = "dzień";
        }
        else {
            dayOrNight = "noc";
        }
    }

    private void nextDay() {
        hour = 0;
        day++;
        //Weather.change();
        Agent.repaintGraphic();
    }

    private void nextMonth() {
        hour = 0;
        day = 1;
        if (month < 12) {
            month++;
        } else {
            month = 1;
        }
    }

    private void checkDate() {
        if (hour == 24) {
            nextDay();
        }
        setIcon(hour);
        if (day == 30 && month == 2) {
            nextMonth();
        } else if (day == 31 && (month == 4 || month == 6 || month == 9 || month == 11)) {
            nextMonth();
        } else if (day == 32) {
            nextMonth();
        }

    }

    public static int getDay() {
        return day;
    }

    public static int getHour() {
        return hour;
    }

    public static String getMonth() {
        String name = "";
        switch (month) {
            case 1:
                name = "styczeń";
                break;
            case 2:
                name = "luty";
                break;
            case 3:
                name = "marzec";
                break;
            case 4:
                name = "kwiecień";
                break;
            case 5:
                name = "maj";
                break;
            case 6:
                name = "czerwiec";
                break;
            case 7:
                name = "lipiec";
                break;
            case 8:
                name = "sierpień";
                break;
            case 9:
                name = "wrzesień";
                break;
            case 10:
                name = "październik";
                break;
            case 11:
                name = "listopad";
                break;
            case 12:
                name = "grudzień";
                break;
        }
        return name;
    }

    private void setIcon(int hour) {
        icon = System.getProperty("user.dir") + "\\src\\graphics\\Info\\time " + hour + ".png";
    }

    public static String getIcon() {
        return icon;
    }

    public static String dayOrNight() {
        return dayOrNight;
    }

    public static void setStepsList(List<String> list) {
        tab = list;
    }

}
