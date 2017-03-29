package szi.data;

import java.awt.*;

public interface Cell {
    void cross();
    Color getColor();
    double getCrossingCost();
    double getIncome();
    boolean isCrossable();
    void nextDay();
    String getName();

}
