package kanta;

import static kanta.SisaltaaTarkistaja.*;

/**
 * Luokka sosiaaliturvatunnuksen tarkistamiseksi
 * @author Aleksi Ilmonen
 * @version 22.6.2018
 *
 */
public class SotuTarkistus {
    /**Hetuun kelpaavat tarkistusmerkit j�rjestyksess� */
    //                                            0123456789012345678901234567890
    public static final String TARKISTUSMERKIT = "0123456789ABCDEFHJKLMNPRSTUVWXY";
   
    /** Kuukausien maksimipituudet */
    public static int[] KUUKAUDET = {31,29,31,30,31,30,31,31,30,31,30,31};
    
            
    /**
     * Palauttaa mik� olisi sotun tarkistusmerkki. Tuotava parametrin� laillistamuotoa oleva hetu,
     * jossa mahdollisesti tarkistusmerkki puuttuu
     * @param sotu tutkittava sotu
     * @return sotun tarkistusmerkki
     * @example
     * <pre name="test">
     *    sotunTarkistusMerkki("121212-222")    === 'N';
     *    sotunTarkistusMerkki("121212-222S")   === 'N';
     *    sotunTarkistusMerkki("121212-222N")   === 'N';
     *    sotunTarkistusMerkki("121212-231Y")   === 'Y';
     *    sotunTarkistusMerkki("311212-2317")   === '7';
     *    sotunTarkistusMerkki("311212-2317XY") === '7'; // vaikka on liikaa merkkej�
     *    sotunTarkistusMerkki("999999-9999XY") === 'F'; // vaikka on pvm v��rin
     *    sotunTarkistusMerkki("12121A-222S")   === 'N'; #THROWS NumberFormatException
     *    sotunTarkistusMerkki("12121A-22")     === 'N'; #THROWS StringIndexOutOfBoundsException
     *    sotunTarkistusMerkki("121")           === 'N'; #THROWS StringIndexOutOfBoundsException
     * </pre>
     */
    public static char sotunTarkistusMerkki(String sotu) {
        String pvm = sotu.substring(0,6);
        String yksilo = sotu.substring(7, 10);
        long luku = Long.parseLong(pvm+yksilo);
        int jakojaannos = (int)(luku % 31L);
        return TARKISTUSMERKIT.charAt(jakojaannos);
    }
    
    
    /**
     * Tarkistetaan hetu.  Sallitaan my�s muoto jossa vain syntym�aika.
     * @param hetu joka tutkitaan.
     * @return null jos oikein, muuten virhett� kuvaava teksti
     * TODO tarkistukset kuntoon my�s karkausvuodesta.
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * HetuTarkistus hetut = new HetuTarkistus();
     * hetut.tarkista("12121")       === "Hetu liian lyhyt";
     * hetut.tarkista("k")           === "Hetu liian lyhyt";
     * hetut.tarkista("12121k")      === "Alkuosassa saa olla vain numeroita";
     * hetut.tarkista("121212")      === null;   // sallitaan pelkk� syntym�aika
     * hetut.tarkista("001212")      === "Liian pieni p�iv�m��r�";
     * hetut.tarkista("321212")      === "Liian suuri p�iv�m��r�";
     * hetut.tarkista("300212")      === "Liian suuri p�iv�m��r�";
     * hetut.tarkista("310412")      === "Liian suuri p�iv�m��r�";
     * hetut.tarkista("121312")      === "Liian suuri kuukausi";
     * hetut.tarkista("120012")      === "Liian pieni kuukausi";
     * hetut.tarkista("121212B222Q") === "V��r� erotinmerkki";
     * hetut.tarkista("121212-2k2Q") === "Yksil�osassa kirjaimia";
     * hetut.tarkista("121212-2")    === "Yksil�osa liian lyhyt";
     * hetut.tarkista("1212121")     === "V��r� erotinmerkki";
     * hetut.tarkista("12121212")    === "V��r� erotinmerkki";  
     * hetut.tarkista("121212-")     === "Yksil�osa liian lyhyt";
     * hetut.tarkista("121212-12345")=== "Hetu liian pitk�";
     * hetut.tarkista("121212-222S") === "Tarkistusmerkin kuuluisi olla N";
     * hetut.tarkista("121212-222N") === null;
     * hetut.tarkista("121212-231Y") === null;
     * hetut.tarkista("311212-2317") === null;
     * </pre>
     */  
    public String tarkista(String hetu) {
        int pituus = hetu.length();
        if ( pituus < 6 ) return "Hetu liian lyhyt";
        String pvm = hetu.substring(0,6);
        if ( !onkoVain(pvm,NUMEROT)) return "Alkuosassa saa olla vain numeroita"; 
        int pv = Integer.parseInt(pvm.substring(0,2));
        int kk = Integer.parseInt(pvm.substring(2,4));
        // int vv = Integer.parseInt(pvm.substring(4,6)); TODO viel� tarkempi pvm tarkistus
        if ( kk < 1 )  return "Liian pieni kuukausi";
        if ( 12 < kk ) return "Liian suuri kuukausi";
        int pvmkk = KUUKAUDET[kk-1];
        if ( pv < 1 )  return "Liian pieni p�iv�m��r�";
        if ( pvmkk < pv ) return "Liian suuri p�iv�m��r�";
        if ( pituus == 6 ) return null;   // pelkk� syntym�aika kelpaa
        String erotin = hetu.substring(6,7);
        if ( !onkoVain(erotin,"+-A")) return "V��r� erotinmerkki";
        if ( pituus < 11 ) return "Yksil�osa liian lyhyt";
        if ( pituus > 11 ) return "Hetu liian pitk�";
        String yksilo = hetu.substring(7,10);
        if ( !onkoVain(yksilo,NUMEROT)) return "Yksil�osassa kirjaimia";
        char merkki = sotunTarkistusMerkki(hetu);
        if ( hetu.charAt(10) != merkki ) return "Tarkistusmerkin kuuluisi olla " + merkki;
        return null;
    }
    
    
    /**
     * Arvotaan satunnainen kokonaisluku v�lille [ala, yla]
     * @param ala arvonnan rajaraja
     * @param yla arvonnan yl�raja
     * @return satunnainen luku v�lilt� [ala, yla]
     */
    public static int rand(int ala, int yla) {
        double n = (yla-ala)*Math.random() + ala;
        return (int)Math.round(n);
    }
}
