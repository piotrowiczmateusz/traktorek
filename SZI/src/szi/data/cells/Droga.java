/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szi.data.cells;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import szi.data.Komorka;

/**
 *
 * @author s407332
 */
public class Droga implements Komorka {

    static Timer mTimer;
    private String currentImage;
    private double woda;
    private boolean currentObject;
    
    public Droga() {
        this.mTimer = new Timer();
        this.currentObject = false;
        this.woda = Math.random();
        mTimer.scheduleAtFixedRate(mTask, 0, 10000);
    }
    
    private TimerTask mTask = new TimerTask() {
        @Override
        public void run() {
            if(Droga.this.woda <= 0.0) {
                Droga.this.woda = 1.0;
                Droga.this.currentImage = ("bloto_" + (double) Math.round((Droga.this.woda)*10)/10).replace(".","");
            }
            if((Droga.this.woda <= 0.55)&&(Droga.this.woda > 0)) {
                Droga.this.woda -= 0.05;
                if(Droga.this.woda<0) Droga.this.woda = 0.0;
                Droga.this.currentImage = ("droga-sucha_" + (double) Math.round((Droga.this.woda)*10)/10).replace(".","");
            }
            if((Droga.this.woda <= 0.7)&&(Droga.this.woda > 0.55)) {
                Droga.this.woda -= 0.05;
                Droga.this.currentImage = ("droga-mokra_" + (double) Math.round((Droga.this.woda)*10)/10).replace(".","");
            }
            if((Droga.this.woda <= 1.0)&&(Droga.this.woda > 0.7)) {
                Droga.this.woda -= 0.05;
                Droga.this.currentImage = ("bloto_" + (double) Math.round((Droga.this.woda)*10)/10).replace(".","");
            }
        }
    };
    
    @Override
    public void cross() {}
    
    @Override
    public double getNawoz() {
        return 0;
    }
    
    @Override
    public void setCurrentObject(boolean isCurrentObject) {
        this.currentObject = isCurrentObject;
    }
    
    @Override
    public boolean isCurrentObject() {
        return false;
    }
    
    @Override
    public void fertilize(){}
    
    @Override
    public double getNum() {
        return 0;
    }
    
    @Override
    public Color getColor() {
        return new Color(50,50,50);
    }

    @Override
    public double getCrossingCost() {
        return 3;
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
        return "Droga";
    }
    
    @Override
    public String getImageName() {
        return Droga.this.currentImage;
    }
    
    @Override
    public double getPlantMaturity(){
        return 0;
    }
}
