package tulos;

import java.util.ArrayList;
import java.util.List;

/**
 * Tulosrekisterin ajat, joka osaa mm. lisätä uuden ajan
 * @author Aleksi Ilmonen
 * @version 29.6.2018
 *
 */
public class Ajat {
    
    private ArrayList<Aika> alkiot = new ArrayList<Aika>();
    
    /**
     * Lisää ajan listaan
     * @param aika Aika, joka lisätään listaan
     * 
     */
    public void lisaa(Aika aika) {
        alkiot.add(aika);
    }
    
    
    /**
     * Palauttaa alkioiden määrän listassa
     * @return alkioiden määrä listassa
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * Haetaan kaikki urheilijan ajat
     * @param urheilijaId urheilijan id, jolla aikoja haetaan
     * @return tietorakenne jossa viiteet löydetteyihin aikoihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Ajat ajat = new Ajat();
     *  Aika aika21 = new Aika(2); ajat.lisaa(aika21);
     *  Aika aika11 = new Aika(1); ajat.lisaa(aika11);
     *  Aika aika22 = new Aika(2); ajat.lisaa(aika22);
     *  Aika aika12 = new Aika(1); ajat.lisaa(aika12);
     *  Aika aika23 = new Aika(2); ajat.lisaa(aika23);
     *  Aika aika51 = new Aika(5); ajat.lisaa(aika51);
     *  
     *  List<Aika> loytyneet;
     *  loytyneet = ajat.annaAjat(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = ajat.annaAjat(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == aika11 === true;
     *  loytyneet = ajat.annaAjat(5);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == aika51 === true;
     * </pre> 
     */
    public List<Aika> annaAjat(int urheilijaId) {
        List<Aika> loydetyt = new ArrayList<Aika>();
        for(Aika aika : alkiot) {
            if(aika.getUrheilijaId() == urheilijaId) loydetyt.add(aika);
        }
        return loydetyt;
    }

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Ajat ajat = new Ajat();
        Aika aika1 = new Aika();
        Aika aika2 = new Aika();
        aika1.taytaAikaTiedot();
        aika2.taytaAikaTiedot();
        aika1.rekisteroi();
        aika2.rekisteroi();
        
        ajat.lisaa(aika1);
        ajat.lisaa(aika2);

        System.out.println("============== Ajat testi ==============");
        
        List<Aika> ajat2 = ajat.annaAjat(1);

        for (Aika aika : ajat2) {
            System.out.print(aika.getUrheilijaId() + " ");
            aika.tulosta(System.out);
        }
        
    }

}
