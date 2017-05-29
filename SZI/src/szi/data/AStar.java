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

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class AStar {

    public static List<String> stepsList;
    public static Queue<String> stepsList2 = new LinkedList<>();
    public static String nextStep;
    public static boolean isRunning = false;
    private static int direction;
    private static int rotation;
    private static Window window;
    private static Agent agent;

    public AStar(Window window, Agent agent) {
        this.window = window;
        this.agent = agent;
    }

    public static void runAStar2() {
        if (!stepsList2.isEmpty()) {

            String step = stepsList2.poll();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(step);
            switch (step) {
                case "left":
                    agent.moveAgent(Agent.LEFT);
                    break;
                case "right":
                    agent.moveAgent(Agent.RIGHT);
                    break;
                case "forward":
                    agent.moveAgent(Agent.FORWARD);
                    break;
            }
        }
    }

    public static void runAStar(int startX, int startY, int rotation, int endX, int endY) {
        stepsList = getStepsList(startX, startY, rotation, endX, endY);
        /*System.out.println("Lista kroków:");
        for (String step : stepsList
                ) {
            System.out.println(step);
        }
        System.out.println("Gotowy.");
        */
        isRunning = true;
        
    }

    private static double heuristicCost(Position position, int goalX, int goalY) {
        double roadCost = 0;
        roadCost = abs(position.x - goalX) + abs(position.y - goalY);
        double rotationCost = 1;
        if (position.x != goalX && (position.rotation == 2 || position.rotation == 0)) {
            rotationCost = 0;
        }
        if (position.y != goalY && (position.rotation == 1 || position.rotation == 3)) {
            rotationCost = 0;
        }
        return roadCost + rotationCost;
    }

    private static Position newPosition(Position oldPosition, String direction) {
        Position newPosition = new Position(oldPosition.x, oldPosition.y, oldPosition.rotation, 1);
        newPosition.moveAgent(direction);
        if (direction == Agent.LEFT || direction == Agent.RIGHT){
            newPosition.gValue = 0.5 * window.komorki[newPosition.x][newPosition.y].getCrossingCost();
        }
        newPosition.moves = new ArrayList<>(oldPosition.moves);
        if (direction == Agent.BACKWARD){
            newPosition.gValue = 2 * window.komorki[newPosition.x][newPosition.y].getCrossingCost();
        }
        if (direction == Agent.FORWARD){
            newPosition.gValue = window.komorki[newPosition.x][newPosition.y].getCrossingCost();
        }
        newPosition.moves.add(direction);
        return newPosition;
    }

    private static List<String> getStepsList(int posX, int posY, int rotation, int goalX, int goalY) {
        Set<Position> closedSet = new HashSet<>();
        PriorityQueue<Position> openSet = new PriorityQueue<Position>(50, new Comparator<Position>() {

            @Override
            public int compare(Position o1, Position o2) {
                if ((o1.gValue + heuristicCost(o1, goalX, goalY)) < (o2.gValue + heuristicCost(o2, goalX, goalY))) {
                    return -1;
                }
                if ((o1.gValue + heuristicCost(o1, goalX, goalY)) > (o2.gValue + heuristicCost(o2, goalX, goalY))) {
                    return 1;
                }
                return 0;
            }
        });

        openSet.add(new Position(posX, posY, rotation, 0));
        while (!openSet.isEmpty()) {
           
            Position currPosition = openSet.poll();
        
            if (currPosition.x == goalX && currPosition.y == goalY) return currPosition.moves;
            closedSet.add(currPosition);
            for (Position p : new Position[]{newPosition(currPosition, Agent.FORWARD),
                    newPosition(currPosition, Agent.BACKWARD),
                    newPosition(currPosition, Agent.LEFT),
                    newPosition(currPosition, Agent.RIGHT)}) {
                if (closedSet.contains(p) || p.equals(currPosition)) {
                    continue;
                }
                p.gValue = currPosition.gValue + p.gValue;
                openSet.add(p);
            }
        }

        return null;
    }

    public static void initialize(int x, int y, int rotation) {
        stepsList2 = new LinkedList<>(getStepsList(x, y, rotation, 3, 3));
    }

    private static class Position {

        public int x;
        public int y;
        public int rotation;
        public double gValue;

        public List<String> moves;

        public Position(int x, int y, int rotation, double gValue) {
            this.x = x;
            this.y = y;
            this.rotation = rotation;
            this.gValue = gValue;
            this.moves = new ArrayList<>();
        }

        @Override
        public boolean equals(Object obj) {
            Position other = (Position) obj;
            return (x == other.x && y == other.y && rotation == other.rotation);
        }

        @Override
        public int hashCode() {
            return x + y + rotation;
        }

        public void moveAgent(String way) {
            if (way.equals(Agent.LEFT)) {
                rotation = (rotation - 1 + 4) % 4;
            }
            if (way.equals(Agent.RIGHT)) {
                rotation = (rotation + 1) % 4;
            }
            String absoluteDirection = LocalToAbsolute();
            if (way.equals(Agent.FORWARD) || way.equals(Agent.BACKWARD)) {
                int i = way.equals(Agent.FORWARD) ? 1 : -1;
                if (absoluteDirection.equals(Agent.NORTH) && y - i >= 0 && y - i < window.komorki[0].length) {
                    if (checkNextStep(x, y - i)) {
                        y -= i;
                    }

                } else if (absoluteDirection.equals(Agent.SOUTH) && y + i >= 0 && y + i < window.komorki[0].length) {
                    if (checkNextStep(x, y + i)) {
                        y += i;
                    }

                } else if (absoluteDirection.equals(Agent.WEST) && x - i >= 0 && x - i < window.komorki.length) {
                    if (checkNextStep(x - i, y)) {
                        x -= i;
                    }

                } else if (absoluteDirection.equals(Agent.EAST) && x + i >= 0 && x + i < window.komorki.length) {
                    if (checkNextStep(x + i, y)) {
                        x += i;
                    }
                }
            }
        }

        public boolean checkNextStep(int x, int y) {
            return window.komorki[x][y].isCrossable();
        }

        private String LocalToAbsolute() {
            return new String[]{Agent.NORTH, Agent.EAST, Agent.SOUTH, Agent.WEST}[rotation];
        }
    }
}