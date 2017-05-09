/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szi.data.cells;
import java.awt.*;
import szi.data.Komorka;

/**
 *
 * @author s407332
 */

public class Trawa implements Komorka {

    @Override
    public void cross() {}
    
    @Override
    public double getNawoz() {
        return 0;
    } 
    
    @Override
    public double getNum() {
        return 0;
    }
    
    @Override
    public void setCurrentObject(boolean isCurrentObject) {
        
    }
    
    @Override
    public boolean isCurrentObject() {
        return false;
    }
    
    @Override
    public void fertilize(){}

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
        return "Trawa";
    }
}
