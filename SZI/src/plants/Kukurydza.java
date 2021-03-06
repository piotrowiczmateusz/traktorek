/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plants;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import szi.data.Komorka;

/**
 *
 * @author 407332
 */
public class Kukurydza implements Komorka{
    
    private double nawoz = Math.random();
    private double plantMaturity;
    static Timer mTimer;
    private String currentImage;
    private boolean currentObject;
    
    @Override
    public void cross() {}
    
    public Kukurydza() {
        this.mTimer = new Timer();
        this.nawoz = Math.random();
        this.plantMaturity = Math.random();
        this.currentObject = false;
        mTimer.scheduleAtFixedRate(mTask, 0, 8000);
    }
    
    private TimerTask mTask = new TimerTask() {
        @Override
        public void run() {
            if(Kukurydza.this.nawoz < 0.98) {
                Kukurydza.this.nawoz += 0.02;
            }
            Kukurydza.this.currentImage = ("kukurydza_" + (double) Math.round((Kukurydza.this.nawoz)*10)/10).replace(".","");
        }
    };
    
    @Override
    public double getNum() {
        return 2;
    }
    
    @Override
    public void setCurrentObject(boolean isCurrentObject) {
        this.currentObject = isCurrentObject;
    }
    
    @Override
    public void fertilize(){
        this.nawoz = 0;
        Kukurydza.this.currentImage = ("kukurydza_" + (double) Math.round((Kukurydza.this.nawoz)*10)/10).replace(".","");
    }
    
    @Override
    public boolean isCurrentObject() {
        return this.currentObject;
    }

    @Override
    public double getNawoz() {
        return nawoz;
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
        return "Kukurydza";
    }
    
    @Override
    public String getImageName() {
        return Kukurydza.this.currentImage;
    }
    
    @Override
    public double getPlantMaturity(){
        return plantMaturity;
    }
}
