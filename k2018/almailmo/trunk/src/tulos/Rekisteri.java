package tulos;

import java.io.File;
import java.util.List;

/**
 * @author Aleksi Ilmonen
 * @version 3.7.2018
 *
 */
public class Rekisteri {
    
    private Urheilijat urheilijat = new Urheilijat();
    private Matkat matkat = new Matkat();
    private Ajat ajat = new Ajat();
    
    
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
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdir();
        String hakemistonNimi = "";
        if ( !nimi.isEmpty()) hakemistonNimi = nimi + "/";
        urheilijat.setTiedostonPerusNimi(hakemistonNimi + "urheilijat");
        matkat.setTiedostonPerusNimi(hakemistonNimi + "matkat");
        ajat.setTiedostonPerusNimi(hakemistonNimi + "tulokset");
        
    }
    
    
    /**
     * Lukee rekisterin tiedot tiedostosta
     * @param nimi tiedoston nimi
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.*;
     * #import java.util.*;
     * 
     * Rekisteri rekisteri = new Rekisteri();
     * 
     * Urheilija vaiski1 = new Urheilija(); vaiski1.taytaUrheilijaTiedot(); vaiski1.rekisteroi();
     * Urheilija vaiski2 = new Urheilija(); vaiski2.taytaUrheilijaTiedot(); vaiski2.rekisteroi();
     * Aika aika1 = new Aika(vaiski1.getId()); aika1.taytaAikaTiedot;
     * Aika aika2 = new Aika(vaiski1.getId()); aika2.taytaAikaTiedot;
     * Aika aika3 = new Aika(vaiski1.getId()); aika3.taytaAikaTiedot;
     * Aika aika4 = new Aika(vaiski2.getId()); aika4.taytaAikaTiedot;
     * Aika aika5 = new Aika(vaiski2.getId()); aika5.taytaAikaTiedot;
     * 
     * String hakemisto = "testitulos";
     * File dir = new File(hakemisto);
     * 
     * 
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        urheilijat = new Urheilijat();
        ajat = new Ajat();
        matkat = new Matkat();
        
        setTiedosto(nimi);
        urheilijat.lueTiedostosta();
        ajat.lueTiedostosta();
        matkat.lueTiedostosta();
        
    }
    
    
    /**
     * Tallennetaan rekisterin tiedot tiedostoon.
     * Vaikka, jokin tallennus epäonnistuu, yritetään tallentaa muut ennen poikkeuksen heittämistä
     * @throws SailoException Jos tallennus epäonnistuu
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            urheilijat.tallenna();
        } catch (SailoException ex) {
            virhe = ex.getMessage();
            System.out.println(virhe);
        }
        
        try {
            ajat.tallenna();
        } catch (SailoException ex) {
            virhe = ex.getMessage();
        }
        
        try {
            matkat.tallenna();
        } catch (SailoException ex) {
            virhe = ex.getMessage();
        }
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
