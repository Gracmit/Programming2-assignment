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
     * Korvaa urheilijan tietorakenteessa.  Ottaa urheilija omistukseensa. 
     * Etsitään samalla tunnusnumerolla oleva urheilija.  Jos ei löydy, 
     * niin lisätään uutena urheilijana. 
     * @param urheilija lisättävän urheilijan viite. 
     * @throws SailoException jos ongelmia
     */ 
    public void korvaaTaiLisaa(Urheilija urheilija) throws SailoException { 
        urheilijat.korvaaTaiLisaa(urheilija); 
    } 
    
    
    /**
     * Korvaa ajan tietorakenteessa.  Ottaa ajan omistukseensa. 
     * Etsitään samalla tunnusnumerolla oleva aika.  Jos ei löydy, 
     * niin lisätään uutena aikana. 
     * @param aika lisättävän ajan viite
     * @throws SailoException jos ongelmia
     */
    public void korvaaTaiLisaa(Aika aika) throws SailoException {
        ajat.korvaaTaiLisaa(aika);
    }
    
    /**
     * Palauttaa taulukossa hakuehtoon vastaavien urheilijoiden viitteet
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return tietorakenne löytyneistä jäsenistä
     * @throws SailoException Jos jotakin menee väärin
     */
    public List<Urheilija> etsi(String hakuehto, int k) throws SailoException {
        return urheilijat.etsi(hakuehto, k);
    }
    
    
    /**
     * Haetaan kaikki urheilijan tulokset tietyltä matkalta.
     * Tässä vaiheessa haetaan urheilijan kaikki tulokset
     * @param urheilijaId Urheilijan id
     * @return Lista, jossa kaikki löydetyt ajat
     */
    public List<Aika> annaAjat(int urheilijaId) {
        return ajat.annaAjat(urheilijaId, -1);
    }
    
    
    /**
     * Haetan kaikki urheilijan tulokset tietyltä matkalta
     * @param urheilija kenen ajat haetaan
     * @param matka Matka, jolta tulokset haetaan
     * @return Lista, jossa kaikki löydetyt ajat
     */
    public List<Aika> annaAjat(Urheilija urheilija, Matka matka) {
        return ajat.annaAjat(urheilija.getId(), matka.getId());
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
     * Palauttaa kaikki urheilijat taulukossa
     * @return taulukko, jossa kaikki urheiljat
     */
    public Urheilija[] annaUrheilijat() {
        return urheilijat.annaUrheilijat();
    }
    
    
    /**
     * Poistaa urheilijoista ja ajoista urheilijan tiedot 
     * @param urheilija urheilija joka poistetaan
     * @return montako urheilijaa poistettiin
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   Rekisteri rek = new Rekisteri();
     *   Urheilija vaiski1 = new Urheilija(); vaiski1.taytaUrheilijaTiedot(); vaiski1.rekisteroi();
     *   Urheilija vaiski2 = new Urheilija(); vaiski2.taytaUrheilijaTiedot(); vaiski2.rekisteroi();
     *   int uid1 = vaiski1.getId();
     *   int uid2 = vaiski2.getId();
     *   Aika aika21 = new Aika(uid2); aika21.taytaAikaTiedot();
     *   Aika aika11 = new Aika(uid1); aika11.taytaAikaTiedot();
     *   Aika aika22 = new Aika(uid2); aika22.taytaAikaTiedot(); 
     *   Aika aika12 = new Aika(uid1); aika12.taytaAikaTiedot(); 
     *   Aika aika23 = new Aika(uid2); aika23.taytaAikaTiedot();
     *   rek.lisaa(vaiski1);
     *   rek.lisaa(vaiski2);
     *   rek.lisaa(aika21);
     *   rek.lisaa(aika11);
     *   rek.lisaa(aika22);
     *   rek.lisaa(aika12);
     *   rek.lisaa(aika23);
     *   rek.etsi("*",0).size() === 2;
     *   rek.annaAjat(vaiski1.getId()).size() === 2;
     *   rek.poista(vaiski1) === 1;
     *   rek.etsi("*",0).size() === 1;
     *   rek.annaAjat(vaiski1.getId()).size() === 0;
     *   rek.annaAjat(vaiski2.getId()).size() === 3;
     * </pre>
     */
    public int poista(Urheilija urheilija) {
        if ( urheilija == null ) return 0;
        int ret = urheilijat.poista(urheilija.getId()); 
        ajat.poista(urheilija.getId()); 
        return ret; 
    }
    
    
    /** 
     * Poistaa tämän ajan
     * @param aika poistettava aika
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   Rekisteri rek = new Rekisteri();
     *   Urheilija vaiski1 = new Urheilija(); vaiski1.taytaUrheilijaTiedot(); vaiski1.rekisteroi();
     *   Urheilija vaiski2 = new Urheilija(); vaiski2.taytaUrheilijaTiedot(); vaiski2.rekisteroi();
     *   int uid1 = vaiski1.getId();
     *   int uid2 = vaiski2.getId();
     *   Aika aika21 = new Aika(uid2); aika21.taytaAikaTiedot();
     *   Aika aika11 = new Aika(uid1); aika11.taytaAikaTiedot();
     *   Aika aika22 = new Aika(uid2); aika22.taytaAikaTiedot(); 
     *   Aika aika12 = new Aika(uid1); aika12.taytaAikaTiedot(); 
     *   Aika aika23 = new Aika(uid2); aika23.taytaAikaTiedot();
     *   rek.lisaa(vaiski1);
     *   rek.lisaa(vaiski2);
     *   rek.lisaa(aika21);
     *   rek.lisaa(aika11);
     *   rek.lisaa(aika22);
     *   rek.lisaa(aika12);
     *   rek.lisaa(aika23);
     *   rek.annaAjat(vaiski1.getId()).size() === 2;
     *   rek.poistaAika(aika11);
     *   rek.annaAjat(vaiski1.getId()).size() === 1;
     */ 
    public void poistaAika(Aika aika) { 
        ajat.poista(aika); 
    }
    
    
    /**
     * Palauttaa kaikki matkat listassa
     * @return matkat listassa
     */
    public List<Matka> getMatkat() {
        return matkat.getMatkat();
    }
    
    
    /**
     * Palauttaa matkojen määrän
     * @return matkojen määrä
     */
    public int getMatkaLkm() {
        return matkat.getLkm();
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
     * Aika aika1 = new Aika(vaiski1.getId()); aika1.taytaAikaTiedot();
     * Aika aika2 = new Aika(vaiski1.getId()); aika2.taytaAikaTiedot();
     * Aika aika3 = new Aika(vaiski2.getId()); aika3.taytaAikaTiedot();
     * Aika aika4 = new Aika(vaiski2.getId()); aika4.taytaAikaTiedot();
     * Aika aika5 = new Aika(vaiski2.getId()); aika5.taytaAikaTiedot();
     * 
     * String hakemisto = "testitulos";
     * File dir = new File(hakemisto);
     * File ftied  = new File(hakemisto+"/urheilijat.dat");
     * File fhtied = new File(hakemisto+"/ajat.dat");
     * dir.mkdir();  
     * ftied.delete();
     * fhtied.delete();
     * rekisteri.lueTiedostosta(hakemisto); #THROWS SailoException
     * rekisteri.lisaa(vaiski1);
     * rekisteri.lisaa(vaiski2);
     * rekisteri.lisaa(aika1);
     * rekisteri.lisaa(aika2);
     * rekisteri.lisaa(aika3);
     * rekisteri.lisaa(aika4);
     * rekisteri.lisaa(aika5);
     * rekisteri.tallenna();
     * rekisteri = new Rekisteri();
     * rekisteri.lueTiedostosta(hakemisto);
     * Urheilija[] kaikki = rekisteri.annaUrheilijat(); 
     * kaikki[0] === vaiski1;
     * kaikki[1] === vaiski2;
     * List<Aika> loytyneet = rekisteri.annaAjat(vaiski1.getId());
     * Iterator<Aika> ih = loytyneet.iterator();
     * ih.next() === aika1;
     * ih.next() === aika2;
     * ih.hasNext() === false;
     * loytyneet = rekisteri.annaAjat(vaiski2.getId());
     * ih = loytyneet.iterator();
     * ih.next() === aika3;
     * ih.next() === aika4;
     * ih.next() === aika5;
     * ih.hasNext() === false;
     * rekisteri.lisaa(vaiski2);
     * rekisteri.lisaa(aika5);
     * rekisteri.tallenna();
     * ftied.delete()  === true;
     * fhtied.delete() === true;
     * File fbak = new File(hakemisto+"/urheilijat.bak");
     * File fhbak = new File(hakemisto+"/ajat.bak");
     * fbak.delete() === true;
     * fhbak.delete() === true;
     * dir.delete() === true;
     * </pre>
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
            System.out.println(virhe);
        }
        
        try {
            matkat.tallenna();
        } catch (SailoException ex) {
            virhe = ex.getMessage();
            System.out.println(virhe);
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
