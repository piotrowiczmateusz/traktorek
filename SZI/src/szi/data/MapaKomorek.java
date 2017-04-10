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

public class MapaKomorek {
    private Komorka[][] map;
    
    public MapaKomorek(Komorka[][] Komorki) {
        map = Komorki;
    }

    public Komorka[][] getMap() {
        return map;
    }
}

