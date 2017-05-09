/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plants;
import szi.data.Komorka;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author s407332
 */
public class Buraki implements Komorka{
    
    private double nawoz;
    static Timer mTimer;
    private boolean currentObject;
    
    public Buraki() {
        this.mTimer = new Timer();
        this.nawoz = Math.random();
        this.currentObject = false;
        mTimer.scheduleAtFixedRate(mTask, 0, 6000);
    }
    
    @Override
    public void cross() {}
    
    private TimerTask mTask = new TimerTask() {
        @Override
        public void run() {
            if(Buraki.this.nawoz > 0.05) {
                Buraki.this.nawoz -= 0.05;
            }
        }
    };
    
    @Override
    public double getNawoz() {
        return nawoz;
    }
    
    @Override
    public double getNum() {
        return 1;
    }
    
    @Override
    public void setCurrentObject(boolean isCurrentObject) {
        this.currentObject = isCurrentObject;
    }
    
    @Override
    public boolean isCurrentObject() {
        return this.currentObject;
    }
    
    @Override
    public void fertilize(){
        this.nawoz = 1;
    }

    @Override
    public Color getColor() {
        return new Color(50,50,50);
    }

    @Override
    public double getCrossingCost() {
        return 4.5;
    }

    @Override
    public double getIncome() {
        return 0;
    }

    @Override
    public boolean isCrossable() {
        return true;
    }

    @Override
    public String getName() {
        return "Buraki";
    }
}
