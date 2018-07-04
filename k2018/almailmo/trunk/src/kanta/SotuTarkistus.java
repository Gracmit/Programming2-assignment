package kanta;

/**
 * Luokka sosiaaliturvatunnuksen tarkistamiseksi
 * @author Aleksi Ilmonen
 * @version 22.6.2018
 *
 */
public class SotuTarkistus {
    /**Hetuun kelpaavat tarkistusmerkit järjestyksessö */
    //                                            0123456789012345678901234567890
    public static final String TARKISTUSMERKIT = "0123456789ABCDEFHJKLMNPRSTUVWXY";
    
            
    /**
     * Palauttaa mikä olisi sotun tarkistusmerkki. Tuotava parametrinä laillistamuotoa oleva hetu,
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
     *    sotunTarkistusMerkki("311212-2317XY") === '7'; // vaikka on liikaa merkkejä
     *    sotunTarkistusMerkki("999999-9999XY") === 'F'; // vaikka on pvm väärin
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
     * Arvotaan satunnainen kokonaisluku välille [ala, yla]
     * @param ala arvonnan rajaraja
     * @param yla arvonnan yläraja
     * @return satunnainen luku väliltä [ala, yla]
     */
    public static int rand(int ala, int yla) {
        double n = (yla-ala)*Math.random() + ala;
        return (int)Math.round(n);
    }
}
