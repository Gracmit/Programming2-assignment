package tulos;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Tulosrekisterin matka, joka osaa itse huolehtia esim. omasta id numerosta
 * @author Aleksi Ilmonen
 * @version 23.6.2018
 *
 */
public class Matka {
    
    private int id;
    private String juoksuMatka = "";
    
    private static int seuraavaId = 1;
    
    
    /**
     * Antaa ajalle seuraavan rekisterinumeron
     * TODO: PARANNA METODIA TIEDOSTONLUKUA VARTEN!!
     * @example
     * <pre name="test">
     *  Matka matka = new Matka();
     *  matka.getId() === 0;
     *  matka.rekisteroi();
     *  Matka matka2 = new Matka();
     *  matka2.rekisteroi();
     *  int n1 = matka.getId();
     *  int n2 = matka2.getId();
     *  n1 === n2-1;
     *  matka.rekisteroi();
     *  matka.getId() === n1;
     */
    public void rekisteroi() {
        if (id != 0) return;
        id = seuraavaId;
        seuraavaId++;
    }
    
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot matkalle.
     * Väliaikainen!
     */
    public void taytaMatkaTiedot() {
        juoksuMatka = "3000m";
    }
    
    /**
     * Tulostetaan matkan tiedot
     * @param os Tietavirta johon tulostetaan
     */
    public void tulosta(OutputStream os)  {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Tulostetaan matkan tiedot
     * @param out Tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(id + " " + juoksuMatka);
    }
    
    
    /**
     * Palauttaa matkan  id:n
     * @return matkan id
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Matka matka = new Matka();
        Matka matka2 = new Matka();
        
        matka.rekisteroi();
        matka2.rekisteroi();
        
        matka.taytaMatkaTiedot();
        matka2.taytaMatkaTiedot();
        
        matka.tulosta(System.out);
        matka2.tulosta(System.out);
    }


}
