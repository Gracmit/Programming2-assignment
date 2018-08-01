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
public class Aika implements Cloneable{
     
    private int id;
    private int urheilijaId;
    private int matkaId;
    private int h;
    private int min;
    private double s;
    private String pvm = "";
    //private String[] pituudet = new String[] {"100m", "200m", "400m", "800m", "3000m", "5000m", "10000m", "puoli marathon", "marathon"}; 
    
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
     * Antaa k:n kentän sisällön merkkijonona
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    public String anna(int k) {
        switch ( k ) {
        case 0: return "" + id;
        case 1: return "" + urheilijaId;
        case 2: return "" + matkaId;
        case 3: return "" + h;
        case 4: return "" + min;
        case 5: return "" + s;
        case 6: return "" + pvm;
        default: return "Idiootti";
        }
    }
    
    
    /**
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kentän arvo asetetaan
     * @param jono jonoa joka asetetaan kentän arvoksi
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus.
     * @example
     * <pre name="test">
     *   Aika aika = new Aika();
     *   aika.aseta(3,"1") === null;
     *   aika.aseta(3,"kissa") === "Tuntien oltava numeerinen"
     *   aika.aseta(4,"030201-1111") === "Tarkistusmerkin kuuluisi olla C"; 
     *   aika.aseta(5,"030201-111C") === null; 
     *   aika.aseta(6,"kissa") === "Lisenssin oltava numeerinen";
     *   aika.aseta(6,"1940") === null;
     * </pre>
     */
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuilder sb = new StringBuilder(tjono);
        switch ( k ) {
        case 0:
            setId(Mjonot.erota(sb, '§', getId()));
            return null;
        case 1:
            urheilijaId = Mjonot.erota(sb, '§', urheilijaId);
            return null;
        case 2:
            matkaId = Mjonot.erota(sb, '§', matkaId);
            return null;          
        case 3:
            if( !tjono.matches("[0-9]*")) return "Tuntien oltava numeerinen";
            h = Mjonot.erota(sb, '§', h);
            return null;
        case 4:
            if( !tjono.matches("[0-9]*")) return "Minuuttien oltava numeerinen";
            min = Mjonot.erota(sb, '§', min);
            return null;
        case 5:
            s = Mjonot.erota(sb, '§', s);
            return null;
        case 6: 
            if(sb.length() < 10) return "Päivänmäärä liian lyhyt";
            if(sb.charAt(2) != ':' || sb.charAt(5) != ':') return "erota luvut toisistaan kaksoispisteellä";
            pvm = tjono;
            return null;
        default:
            return "Idiootti";
        }
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
        for(int k = 0; k < getKenttia(); k++) {
            aseta(k, Mjonot.erota(sb, '|'));
        }
    }
    
    
    /**
     * Antaa ajalle seuraavan rekisterinumeron
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
     * Asettaa matkan id:n
     * @param id asetettava id
     */
    public void setMatka(int id) {
        matkaId = id;
    }
    
    
    /**
     * Asettaa matkan id:n
     * @param id asetettava id
     */
    public void setUrheilija(int id) {
        urheilijaId = id;
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
     * Palauttaa kenttien määrän
     * @return kenttien määrä
     */
    public int getKenttia() {
        return 7;
    }
    
    
    /**
     * Palauttaa ensimmäisen kentän, jota käyttäjä voi muokata
     * @return eka kenttä, jota käyttäjä voi muokata
     */
    public int ekaKentta() {
        return 3;
    }
    
    
    /**
     * Palauttaa k:tta urheilijan kenttää vastaavan kysymyksen
     * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen)
     * @return k:netta kenttää vastaava kysymys
     */
    public String getKysymys(int k) {
        switch ( k ) {
        case 0: return "Id";
        case 1: return "urheilijaId";
        case 2: return "matkaId";
        case 3: return "h";
        case 4: return "min";
        case 5: return "s";
        case 6: return "Päivänmäärä";
        default: return "Idiootti";
        }
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
        StringBuilder sb = new StringBuilder("");
        String erotin = "";
        for( int k = 0; k < getKenttia(); k++) {
            sb.append(erotin);
            sb.append(anna(k));
            erotin = "|";
        }
        return sb.toString(); 
        
    }
    
    
    /**
     * Kloonaa urheilijan
     * @return Object kloonattu urheilija
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException
     * Urheilija urheilija = new Urheilija();
     * urheilija.parse(" 3 | vaiski | 241");
     * Urheilija kopio = urheilija.clone();
     * kopio.toString() === urheilija.toString();
     * urheilija.parse("4 | Repe | 432");
     * kopio.toString().equals(urheilija.toString()) === false
     * </pre>;
     */
    @Override
    public Aika clone() throws CloneNotSupportedException {
        Aika uusi;
        uusi = (Aika) super.clone();
        return uusi;
        
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
