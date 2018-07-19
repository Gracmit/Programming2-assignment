package tulos;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

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
     * Lukee ajan tiedot merkkijonosta
     * @param rivi merkkijono, josta luetaan
     * @example
     * <pre name="test">
     *   Aika aika = new Aika();
     *   aika.parse("   3  |  2   | 4");
     *   aika.getId() === 3;
     *   aika.toString().startsWith("3|2|4|") === true; 
     *
     *   aika.rekisteroi();
     *   int n = aika.getId();
     *   aika.parse(""+(n+20));      
     *   aika.rekisteroi();          
     *   aika.getId() === n+20;
     *     
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setId(Mjonot.erota(sb, '|', id));
        urheilijaId = Mjonot.erota(sb, '|', urheilijaId);
        matkaId = Mjonot.erota(sb, '|', matkaId);
        h = Mjonot.erota(sb, '|', h);
        min = Mjonot.erota(sb, '|', min);
        s = Mjonot.erota(sb, '|', s);
        pvm = Mjonot.erota(sb, '|', pvm);
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
     * Asettaa id:n ajalle ja pitää huolen että seuraavaId on aina suurempi kuin tähän mennessä suurin id
     * @param nro joka asetetaan
     */
    public void setId(int nro) {
        id = nro;
        if (seuraavaId <= id) seuraavaId = id + 1;
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
     * Palauttaa jäsenen tiedot merkkijonona
     * @example
     * <pre name="test">
     * Aika aika = new Aika();
     * aika.parse("   3  |  2   | 4");
     * aika.toString().startsWith("3|2|4|") === true; 
     * </pre>
     */
    @Override
    public String toString() {
        return id + "|" + urheilijaId + "|" + matkaId + "|" + h + "|" + min + "|" + s + "|" + pvm;  
        
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
