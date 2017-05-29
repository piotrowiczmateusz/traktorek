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
    private double plantMaturity;
    static Timer mTimer;
    private String currentImage;
    private boolean currentObject;
    
    public Buraki() {
        this.mTimer = new Timer();
        this.nawoz = Math.random();
        this.plantMaturity = Math.random();
        this.currentObject = false;
        mTimer.scheduleAtFixedRate(mTask, 0, 12000);
    }
    
    @Override
    public void cross() {}
    
    private TimerTask mTask = new TimerTask() {
        @Override
        public void run() {
            if(Buraki.this.nawoz < 0.95) {
                Buraki.this.nawoz += 0.05;
                
                //System.out.println(Buraki.this.nawoz+ "," + (double) Math.round((Buraki.this.nawoz)*10)/10);
            }
            Buraki.this.currentImage = ("buraki_" + (double) Math.round((Buraki.this.nawoz)*10)/10).replace(".","");
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
        this.nawoz = 0;
        Buraki.this.currentImage = ("buraki_" + (double) Math.round((Buraki.this.nawoz)*10)/10).replace(".","");
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
    
    @Override
    public String getImageName() {
        return Buraki.this.currentImage;
    }
    
    @Override
    public double getPlantMaturity(){
        return plantMaturity;
    }
}
