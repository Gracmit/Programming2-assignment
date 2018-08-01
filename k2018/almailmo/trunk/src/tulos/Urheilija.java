package tulos;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.SotuTarkistus;

import static kanta.SotuTarkistus.*;

/**
 * Tulosrekisterin urheilija, joka osaa mm. itse huolehtia lisenssistään
 * @author Aleksi Ilmonen
 * @version 22.6.2018
 *
 */
public class Urheilija implements Cloneable{
    
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
     * Antaa k:n kentän sisällön merkkijonona
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    public String anna(int k) {
        switch ( k ) {
        case 0: return "" + id;
        case 1: return "" + nimi;
        case 2: return "" + sotu;
        case 3: return "" + seura;
        case 4: return "" + lisenssi;
        case 5: return "" + puhelin;
        default: return "Idiootti";
        }
    }
    
    
    private static SotuTarkistus sotut = new SotuTarkistus();
    
    /**
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kentän arvo asetetaan
     * @param jono jonoa joka asetetaan kentän arvoksi
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus.
     * @example
     * <pre name="test">
     *   Urheilija urheilija = new Urheilija();
     *   urheilija.aseta(1,"Vemmelsääri Väiski") === null;
     *   urheilija.aseta(2,"kissa") =R= "Hetu liian lyhyt"
     *   urheilija.aseta(2,"030201-1111") === "Tarkistusmerkin kuuluisi olla C"; 
     *   urheilija.aseta(2,"030201-111C") === null; 
     *   urheilija.aseta(4,"kissa") === "Lisenssin oltava numeerinen";
     *   urheilija.aseta(4,"1940") === null;
     *   urheilija.anna(4) === "1940";
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
            nimi = tjono;
            return null;
        case 2:
            String virhe = sotut.tarkista(tjono);
            if ( virhe != null ) return virhe;
            sotu = tjono;
            return null;
        case 3:
            seura = tjono;
            return null;
        case 4:
            if( !tjono.matches("[0-9]*")) return "Lisenssin oltava numeerinen";
            lisenssi = tjono;
            return null;
        case 5:
            if( !tjono.matches("[0-9]*")) return "Puh. nro. oltava numeerinen";
            puhelin = tjono;
            return null;
        default:
            return "Idiootti";
        }
    }
    
    
    /**
     * Selvittää urheilijan tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaId on suurempi kuin tuleva id
     * @param rivi josta urheilijan tiedot otetaan
     * @example
     * <pre name="test">
     *   Urheilija urheilija = new Urheilija();
     *   urheilija.parse("   3  |  Vemmelsääri Väiski   | 030201-111C");
     *   urheilija.getId() === 3;
     *   urheilija.toString().startsWith("3|Vemmelsääri Väiski|030201-111C|") === true; 
     *
     *   urheilija.rekisteroi();
     *   int n = urheilija.getId();
     *   urheilija.parse(""+(n+20));      
     *   urheilija.rekisteroi();          
     *   urheilija.getId() === n+20;
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
     * Antaa urheilijalle seuraavan rekisterinumeron
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
     * Palauttaa halutun urheilijan id numeron
     * @return urheilijan id numero
     */
    public int getId() {
        return id;
    }
    
    
    /**Asettaa tunnusnumeron ja samalla varmistaa että seuraava numero on aina suurempi kuin tähän mennessä suurin
     * @param nro asetettava id
     */
    private void setId(int nro) {
        id = nro;
        if (id >= seuraavaId) seuraavaId = id + 1;
    }
    
    
    /**
     * Palauttaa urheilijan nimen
     * @return Urheilijan nimi
     */
    public String getNimi() {
        return this.nimi;
    }
    
    
    /**
     * Palauttaa kenttien määrän
     * @return kenttien määrä
     */
    public int getKenttia() {
        return 6;
    }
    
    
    /**
     * Palauttaa ensimmäisen kentän, jota käyttäjä voi muokata
     * @return eka kenttä, jota käyttäjä voi muokata
     */
    public int ekaKentta() {
        return 1;
    }
    
    
    /**
     * Palauttaa k:tta urheilijan kenttää vastaavan kysymyksen
     * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen)
     * @return k:netta kenttää vastaava kysymys
     */
    public String getKysymys(int k) {
        switch ( k ) {
        case 0: return "Id";
        case 1: return "Nimi";
        case 2: return "Sotu";
        case 3: return "Seura";
        case 4: return "Lisenssi";
        case 5: return "Puhelin";
        default: return "Idiootti";
        }
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
    public Urheilija clone() throws CloneNotSupportedException {
        Urheilija uusi;
        uusi = (Urheilija) super.clone();
        return uusi;
        
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
     * Palauttaa jäsenen tiedot merkkijonona
     * @example
     * <pre name="test">
     * Urheilija urheilija = new Urheilija();
     * urheilija.parse("   3  |  Vemmelsääri Väiski   | 030201-111C");
     * urheilija.toString().startsWith("3|Vemmelsääri Väiski|030201-111C|") === true; 
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
     * Urheilijoiden vertailija
     */
    public static class Vertailija implements Comparator<Urheilija> {
        private int k;
        
        /**
         * Muodostaja vertailijalle
         * @param k vertailtavan attribuutin indeksi
         */
        public Vertailija(int k) {
            this.k = k;
        }
        
        @Override
        public int compare(Urheilija u1, Urheilija u2) {
            return u1.getAvain(k).compareToIgnoreCase(u2.getAvain(k));
        }
        
    }
    
    
    /**
     * Antaa k:n kentän sisällön merkkijonona
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    public String getAvain(int k) {
        switch ( k ) {
        case 0: return "" + id;
        case 1: return "" + nimi.toUpperCase();
        case 2: return "" + muutaSotu(sotu);
        case 3: return "" + seura;
        case 4: return "" + lisenssi;
        case 5: return "" + puhelin;
        default: return "Idiootti";
        }
    }
    
    
    /**
     * vaihtaa sotun syntymäaikaosan järjestykseen vv, kk, d
     * @param hetu sotu merkkijonona
     * @return muutettu sotu
     * @example
     * <pre name="test">
     * Urheilija.muutaSotu("") === "";
     * Urheilija.muutaSotu("111213-1234") === "131211-1234";
     */
    public static String muutaSotu(String hetu) {
        if(hetu.length() == 0) return "";
        StringBuilder sb = new StringBuilder("");
        sb.append(hetu.substring(4, 6));
        sb.append(hetu.substring(2, 4));
        sb.append(hetu.substring(0, 2));
        sb.append(hetu.substring(6, hetu.length()));
        return sb.toString();
        
    }
    
    
//    @Override
//    public int compareTo(Urheilija urheilija) {
//        return getNimi().compareToIgnoreCase(urheilija.getNimi());
//    }

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
