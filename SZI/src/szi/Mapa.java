/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szi;
import plants.Kukurydza;
import plants.Buraki;
import szi.data.cells.*;
import szi.data.Komorka;

/**
 *
 * @author s407332
 */

public class Mapa {
    public static Komorka[][] create() {       
        Komorka[][] komorki = new Komorka[][]{
                new Komorka[]{new Dom(), (new Dom()), new Dom(), new Dom(), new Droga(), new Droga(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo()},
                new Komorka[]{new Dom(), (new Dom()), new Dom(), new Dom(), new Droga(), new Droga(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo()},
                new Komorka[]{new Dom(), (new Dom()), new Dom(), new Dom(), new Droga(), new Droga(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo()},
                new Komorka[]{new Dom(), (new Dom()), new Dom(), new Dom(), new Droga(), new Droga(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo()},
                new Komorka[]{new Droga(), (new Paliwo()), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Drzewo()), new Drzewo(), new Drzewo(), new Trawa(), new Droga(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Kukurydza()), new Kukurydza(), new Kukurydza(), new Kukurydza(), new Droga(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Kukurydza()), new Kukurydza(), new Kamien(), new Kukurydza(), new Droga(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Kamien(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Kukurydza()), new Kukurydza(), new Kukurydza(), new Kukurydza(), new Droga(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Kukurydza()), new Kukurydza(), new Kukurydza(), new Kukurydza(), new Droga(), new Buraki(), new Buraki(), new Buraki(), new Kamien(), new Buraki(), new Buraki(), new Droga(), new Drzewo()},                
                new Komorka[]{new Drzewo(), (new Kukurydza()), new Kamien(), new Kukurydza(), new Kukurydza(), new Droga(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Kukurydza()), new Kukurydza(), new Kukurydza(), new Kukurydza(), new Droga(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Kukurydza()), new Kukurydza(), new Kukurydza(), new Kukurydza(), new Droga(), new Buraki(), new Kamien(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Kukurydza()), new Kukurydza(), new Kukurydza(), new Kukurydza(), new Droga(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Kukurydza()), new Kukurydza(), new Kukurydza(), new Kukurydza(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Drzewo()), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo()}

        };
        return komorki;
    }

}
