package tulos;

import java.io.OutputStream;
import java.io.PrintStream;
import static kanta.SotuTarkistus.*;

/**
 * Tulosrekisterin urheilija, joka osaa mm. itse huolehtia lisenssistään
 * @author Aleksi Ilmonen
 * @version 22.6.2018
 *
 */
public class Urheilija {
    
    private int         id;     
    private String      nimi        = "";
    private String      sotu        = "";
    private String      seura       = "";
    private String      lisenssi    = "";
    private String      puhelin     = "";
    
    private static int  seuraavaId  = 1; 
    
    
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot urheilijalle.
     * Henkilötunnus arvotaan, jotta kahdella urheilijalla ei olisi
     * samoja tietoja.
     * Väliaikainen!
     */
    public void taytaUrheilijaTiedot() {
        this.nimi = "Väiski Vemmelsääri " + rand(1000, 9999);
        this.sotu = "121212-1212";
        this.seura = "JKU";
        this.lisenssi = "123456";
        this.puhelin = "0404040404";
    }    
    
    /**
     * Antaa urheilijalle seuraavan rekisterinumeron
     * TODO: PARANNA METODIA TIEDOSTONLUKUA VARTEN!!
     * @example
     * <pre name="test">
     *  Urheilija vaiski1 = new Urheilija();
     *  vaiski1.getId() === 0;
     *  vaiski1.rekisteroi();
     *  Urheilija vaiski2 = new Urheilija();
     *  vaiski2.rekisteroi();
     *  int n1 = vaiski1.getId();
     *  int n2 = vaiski2.getId();
     *  n1 === n2-1;
     *  vaiski1.rekisteroi();
     *  vaiski1.getId() === n1;
     */
    public void rekisteroi() {
        if( getId() != 0) return;
        id = seuraavaId;
        seuraavaId++;
    }
    
    
    /**
     * Palauttaa urheilijan nimen
     * @return Urheilijan nimi
     */
    public String getNimi() {
        return this.nimi;
    }
    
    /**
     * Palauttaa halutun urheilijan id numeron
     * @return urheilijan id numero
     */
    public int getId() {
        return id;
    }
    
    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta, johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Tulostetaan henkilön tiedot
     * @param out Tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", id) + " " + nimi + " " + sotu);
        out.println(" " + seura + " " + lisenssi + " " + puhelin);
    }

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Urheilija vaiski = new Urheilija();
        Urheilija vaiski2 = new Urheilija();
        vaiski.rekisteroi();
        vaiski2.rekisteroi();
        
        vaiski.taytaUrheilijaTiedot(); //väliaikainen
        vaiski2.taytaUrheilijaTiedot();
        
        vaiski.tulosta(System.out);
        vaiski2.tulosta(System.out);
    }

}
