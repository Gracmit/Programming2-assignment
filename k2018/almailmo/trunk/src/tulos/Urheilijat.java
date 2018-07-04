package tulos;

/**
 * Tulosrekisterin urheilijat, joka osaa mm. lisätä uuden urheilijan
 * @author Aleksi Ilmonen
 * @version 28.6.2018
 *
 */
public class Urheilijat {

    private int lkm = 0;
    private Urheilija[] alkiot = new Urheilija[5];
    
    
    /**
     * Lisää urheilijan rekisteriin
     * @param urheilija Urheilija, joka lisätään rekisteriin
     * @example
     * <pre name="test">
     * Urheilijat urheilijat = new Urheilijat();
     * Urheilija vaiski1 = new Urheilija();
     * Urheilija vaiski2 = new Urheilija();
     * urheilijat.lisaa(vaiski1); urheilijat.getLkm() === 1;
     * urheilijat.lisaa(vaiski2); urheilijat.getLkm() === 2;
     * urheilijat.lisaa(vaiski1); urheilijat.getLkm() === 3;
     * urheilijat.anna(0) === vaiski1;
     * urheilijat.anna(1) === vaiski2;
     * urheilijat.anna(2) === vaiski1;
     * urheilijat.anna(1) == vaiski1 === false;
     * urheilijat.anna(0) == vaiski1 === true;
     * urheilijat.anna(3) === vaiski1; #THROWS IndexOutOfBoundsException
     * urheilijat.lisaa(vaiski1); urheilijat.getLkm() === 4;
     * urheilijat.lisaa(vaiski1); urheilijat.getLkm() === 5;
     * urheilijat.lisaa(vaiski1); urheilijat.getLkm() === 6;
     * </pre>
     */
    public void lisaa(Urheilija urheilija) {
        if(lkm == alkiot.length) kasvataTaulukko();
        alkiot[lkm++] = urheilija;
    }
    
    
    /**
     * Kasvattaa Urheilija taulukon kokoa
     */
    public void kasvataTaulukko() {
        Urheilija[] alkiotUusi = new Urheilija[alkiot.length*2];
        for (int i = 0; i < alkiot.length; i++) {
            alkiotUusi[i] = alkiot[i];
        }
        alkiot = alkiotUusi;
    }
    
    
    /**
     * @return alkioiden määrä taulukossa
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Palauttaa viitteen i:teen urheilijaan. Väliaikainen!
     * @param i monennenko urheilijan viite halutaan
     * @return viite urheilijaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Urheilija anna(int i) throws IndexOutOfBoundsException {
        if (i<0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];

    }
    
    
    /**
     * Testataan Urheilijat luokkaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Urheilijat urheilijat = new Urheilijat();
        Urheilija vaiski1 = new Urheilija();
        Urheilija vaiski2 = new Urheilija();
        vaiski1.taytaUrheilijaTiedot();
        vaiski2.taytaUrheilijaTiedot();
        vaiski1.rekisteroi();
        vaiski2.rekisteroi();
        
        urheilijat.lisaa(vaiski1);
        urheilijat.lisaa(vaiski2);
        
        System.out.println("===========Urheilijat testi ===========");
        
        for (int i = 0; i < urheilijat.getLkm(); i++) {
            Urheilija urheilija = urheilijat.anna(i); // väliaikainen!!!
            System.out.println("Urheilija nro: " + i);
            urheilija.tulosta(System.out);
        }
    }

}
