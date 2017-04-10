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
    public Color getColor() {
        return new Color(50,50,50);
    }

    @Override
    public String getName() {
        return "Trawa";
    }
}
