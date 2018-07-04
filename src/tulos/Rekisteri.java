package tulos;

import java.util.List;

/**
 * @author Aleksi Ilmonen
 * @version 3.7.2018
 *
 */
public class Rekisteri {
    
    private final Urheilijat urheilijat = new Urheilijat();
    // private final Matkat matkat = new Matkat();
    private final Ajat ajat = new Ajat();
    
    
    /**
     * Lisää urheilijan rekisteriin
     * @param urheilija Urheilija, joka lisätään rekisteriin
     * @example
     * <pre name="test">
     * Rekisteri reki = new Rekisteri();
     * Urheilija vaiski1 = new Urheilija();
     * Urheilija vaiski2 = new Urheilija();
     * reki.getUrheilijaLkm() === 0;
     * reki.lisaa(vaiski1); reki.getUrheilijaLkm() === 1;
     * reki.lisaa(vaiski2); reki.getUrheilijaLkm() === 2;
     * reki.lisaa(vaiski1); reki.getUrheilijaLkm() === 3;
     * reki.annaUrheilija(0) === vaiski1;
     * reki.annaUrheilija(1) === vaiski2;
     * reki.annaUrheilija(2) === vaiski1;
     * reki.annaUrheilija(1) == vaiski1 === false;
     * reki.annaUrheilija(0) == vaiski1 === true;
     * reki.annaUrheilija(3) === vaiski1; #THROWS IndexOutOfBoundsException
     * reki.lisaa(vaiski1); reki.getUrheilijaLkm() === 4;
     * reki.lisaa(vaiski1); reki.getUrheilijaLkm() === 5;
     * reki.lisaa(vaiski1); reki.getUrheilijaLkm() === 6;
     * </pre>
     */
    public void lisaa(Urheilija urheilija) {
        urheilijat.lisaa(urheilija);
    }
    
    
    /**
     * Lisää ajan rekisteriin
     * @param aika lisättävä aika
     */
    public void lisaa(Aika aika) {
        ajat.lisaa(aika);
    }
    
    
    /**
     * Haetaan kaikki urheilijan tulokset tietyltä matkalta.
     * Tässä vaiheessa haetaan urheilijan kaikki tulokset
     * @param urheilijaId Urheilijan id
     * @return Lista, jossa kaikki löydetyt ajat
     */
    public List<Aika> annaAjat(int urheilijaId) {
        return ajat.annaAjat(urheilijaId);
    }
    
    /**
     * Palauttaa i:nnessä paikassa olevan urheilijan
     * @param i indeksi taulukossa
     * @return i:nnes urheilija
     */
    public Urheilija annaUrheilija(int i) {
        return urheilijat.anna(i);
    }
    
    
    /**
     * Palauttaa alkioiden määrän urheilijoiden taulukosta
     * @return urheilijoiden määrä
     */
    public int getUrheilijaLkm() {
        return urheilijat.getLkm();
    }
    
    
    /**
     * Palauttaa alkioiden määrän aikojen taulukosta
     * @return aikojen määrä
     */
    public int getAikaLkm() {
        return ajat.getLkm();
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Rekisteri rekisteri = new Rekisteri();
        
        Urheilija vaiski1 = new Urheilija();
        Urheilija vaiski2 = new Urheilija();
        vaiski1.taytaUrheilijaTiedot();
        vaiski2.taytaUrheilijaTiedot();
        vaiski1.rekisteroi();
        vaiski2.rekisteroi();
        
        rekisteri.lisaa(vaiski1);
        rekisteri.lisaa(vaiski2);
        
        System.out.println("========== Rekisterin testi ==========");
        for(int i = 0; i < rekisteri.getUrheilijaLkm(); i++) {
            Urheilija urheilija = rekisteri.annaUrheilija(i);
            urheilija.tulosta(System.out);
        }
        
        
    }

}
