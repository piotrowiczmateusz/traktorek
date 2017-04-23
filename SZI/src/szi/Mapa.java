/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szi;
import szi.data.cells.*;
import szi.data.cells.Trawa;
import szi.data.Komorka;

/**
 *
 * @author s407332
 */

public class Mapa {
    public static Komorka[][] create() {       
        Komorka[][] komorki = new Komorka[][]{
                new Komorka[]{new Trawa(), (new Trawa()), new Trawa(), new Trawa(), new Droga(), new Droga(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo()},
                new Komorka[]{new Trawa(), (new Trawa()), new Trawa(), new Trawa(), new Droga(), new Droga(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo()},
                new Komorka[]{new Trawa(), (new Trawa()), new Trawa(), new Trawa(), new Droga(), new Droga(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo()},
                new Komorka[]{new Trawa(), (new Trawa()), new Trawa(), new Trawa(), new Droga(), new Droga(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo()},
                new Komorka[]{new Droga(), (new Droga()), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Drzewo()), new Drzewo(), new Drzewo(), new Trawa(), new Droga(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Buraki()), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Buraki()), new Buraki(), new Kamien(), new Buraki(), new Droga(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Kamien(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Buraki()), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Buraki()), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Buraki(), new Buraki(), new Buraki(), new Kamien(), new Buraki(), new Buraki(), new Droga(), new Drzewo()},                
                new Komorka[]{new Drzewo(), (new Buraki()), new Kamien(), new Buraki(), new Buraki(), new Droga(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Buraki()), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Buraki()), new Buraki(), new Buraki(), new Kamien(), new Droga(), new Buraki(), new Kamien(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Buraki()), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Buraki()), new Buraki(), new Buraki(), new Buraki(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Droga(), new Drzewo()},
                new Komorka[]{new Drzewo(), (new Drzewo()), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo(), new Drzewo()}

        };
        return komorki;
    }

}
