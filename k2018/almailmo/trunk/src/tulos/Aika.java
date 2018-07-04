package tulos;

import java.io.OutputStream;
import java.io.PrintStream;

import static kanta.SotuTarkistus.*;

/**
 * Urheilijan aika, joka osaa itse huolehtia esim, kenen juoksema kyseinen aika on.
 * @author Aleksi Ilmonen
 * @version 23.6.2018
 *
 */
public class Aika {
     
    private int id;
    private int urheilijaId;
    private int matkaId;
    private int h;
    private int min;
    private double s;
    private String pvm = "";
    
    private static int seuraavaId = 1;
    

    /**
     * Alustetaan harrastus
     */
    public Aika() {
        //
    }
    
    
    /**
     * Alustetaan aika, tietyn urheilijan id:llä 
     * @param uId urheilijan id
     */
    public Aika(int uId) {
        this.urheilijaId = uId;
    }
    
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot ajalle.
     * Sekunnit arvotaan, jotta kahdella ajalla ei olisi
     * samoja tietoja.
     * Väliaikainen!
     */
    public void taytaAikaTiedot( ) {
        matkaId = 5; 
        h = 0;
        min = 8;
        s = rand (0, 59);
        pvm = "10:02:2018";
    }
    
    
    /**
     * Antaa ajalle seuraavan rekisterinumeron
     * TODO: PARANNA METODIA TIEDOSTONLUKUA VARTEN!!
     * @example
     * <pre name="test">
     *  Aika aika = new Aika();
     *  aika.getId() === 0;
     *  aika.rekisteroi();
     *  Aika aika2 = new Aika();
     *  aika2.rekisteroi();
     *  int n1 = aika.getId();
     *  int n2 = aika2.getId();
     *  n1 === n2-1;
     *  aika.rekisteroi();
     *  aika.getId() === n1;
     */
    public void rekisteroi() {
        if (id != 0) return;
        id = seuraavaId;
        seuraavaId++;
        
    }
    
    
    /**
     * Palauttaa ajan id numeron
     * @return ajan id numero
     */
    public int getId() {
        return id;
    }
    
    
    /**
     * Palauttaa urheilijan id:n
     * @return urheilijan id
     */
    public int getUrheilijaId() {
        return urheilijaId; 
    }
    
    
    /**
     * Palauttaa matkan id:n
     * @return matkan id
     */
    public int getMatkaId() {
        return matkaId;
    }
    
    
    /**
     * Tulostetaan ajan tiedot
     * @param os Tietovirta, johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Tulostetaan ajan tiedot
     * @param out Tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("matkan id: " + matkaId +  " Aika: " + h + ":" + String.format("%02d", min) + ":" + String.format("%05.2f", s));
        out.println("Pvm: " + pvm);
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Aika aika = new Aika();
        Aika aika2 = new Aika();
        aika.rekisteroi();
        aika2.rekisteroi();
        
        aika.taytaAikaTiedot();
        aika2.taytaAikaTiedot();
        
        aika.tulosta(System.out);
        aika2.tulosta(System.out);

    }

}
