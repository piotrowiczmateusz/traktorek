/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szi.data;
import java.awt.*;

/**
 *
 * @author s407332
 */

public interface Komorka {
    void cross();
    double getNum();
    boolean isCurrentObject();
    void setCurrentObject(boolean x);
    void fertilize();
    Color getColor();
    double getNawoz();
    double getCrossingCost();
    double getIncome();
    boolean isCrossable();
    String getName();
    String getImageName();
    double getPlantMaturity();
}
