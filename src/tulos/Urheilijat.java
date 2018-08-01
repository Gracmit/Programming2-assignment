package tulos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import fi.jyu.mit.ohj2.WildChars;

/**
 * Tulosrekisterin urheilijat, joka osaa mm. lis‰t‰ uuden urheilijan
 * @author Aleksi Ilmonen
 * @version 28.6.2018
 *
 */
public class Urheilijat implements Iterable<Urheilija> {

    private int lkm = 0;
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "urheilijat";
    private Urheilija[] alkiot = new Urheilija[5];
    
    
    /**
     * Lis‰‰ urheilijan rekisteriin
     * @param urheilija Urheilija, joka lis‰t‰‰n rekisteriin
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
        muutettu = true;
    }
    
    
    /**
     * Korvaa urheilijan tietorakenteessa.  Ottaa urheilijan omistukseensa.
     * Etsit‰‰n samalla id:ll‰ oleva urheilija.  Jos ei lˆydy,
     * niin lis‰t‰‰n uutena urheilijana.
     * @param urheilija lis‰t‰‰v‰n urheilijan viite.
     * <pre name="test">
     * #THROWS CloneNotSupportedException
     * #PACKAGEIMPORT
     * Urheilijat urheilijat = new Urheilijat();
     * Urheilija vaiski1 = new Urheilija(), vaiski2 = new Urheilija();
     * vaiski1.rekisteroi(); vaiski2.rekisteroi();
     * urheilijat.getLkm() === 0;
     * urheilijat.korvaaTaiLisaa(vaiski1); urheilijat.getLkm() === 1;
     * urheilijat.korvaaTaiLisaa(vaiski2); urheilijat.getLkm() === 2;
     * Urheilija vaiski3 = vaiski1.clone();
     * Iterator<Urheilija> it = urheilijat.iterator();
     * it.next() == vaiski1 === true;
     * urheilijat.korvaaTaiLisaa(vaiski3); urheilijat.getLkm() === 2;
     * it = urheilijat.iterator();
     * Urheilija u0 = it.next();
     * u0 === vaiski3;
     * u0 == vaiski3 === true;
     * u0 == vaiski1 === false;
     * </pre>
     */
    public void korvaaTaiLisaa(Urheilija urheilija) {
        int id = urheilija.getId();
        for (int i = 0; i < lkm; i++) {
            if ( alkiot[i].getId() == id ) {
                alkiot[i] = urheilija;
                muutettu = true;
                return;
            }
        }
        lisaa(urheilija);
    }
    
    
    /**
    *Palauttaa taulukossa hakuehtoon vastaavien urheilijoiden viitteet
    * @param hakuehto hakuehto
    * @param k etsitt‰v‰n kent‰n indeksi
    * @return tietorakenne lˆytyneist‰ j‰senist‰
    */
    public List<Urheilija> etsi(String hakuehto, int k) {
        String ehto = "*";
        if (hakuehto != null && hakuehto.length() > 0) ehto = hakuehto;
        int hk = k;
        List<Urheilija> loytyneet = new ArrayList<Urheilija>();
        for(Urheilija urheilija: this) {
            if(WildChars.onkoSamat(urheilija.anna(hk), ehto)) loytyneet.add(urheilija);
        }
        Collections.sort(loytyneet, new Urheilija.Vertailija(hk));
        return loytyneet;
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
     * Asettaa tiedoston perusnimen
     * @param tied tallennustidoston nimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }
    
    
    /**
     * Lukee urheilijat tiedostosta
     * @param tied Tiedoston nimi
     * @throws SailoException Jos lukeminen ep‰onnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Urheilijat urheilijat = new Urheilijat();
     *  Urheilija vaiski1 = new Urheilija(), vaiski2 = new Urheilija();
     *  vaiski1.taytaUrheilijaTiedot();
     *  vaiski2.taytaUrheilijaTiedot();
     *  String hakemisto = "testitulos";
     *  String tiedNimi = hakemisto+"/urheilijat";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  urheilijat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  urheilijat.lisaa(vaiski1);
     *  urheilijat.lisaa(vaiski2);
     *  urheilijat.tallenna();
     *  urheilijat = new Urheilijat();            // Poistetaan vanhat luomalla uusi
     *  urheilijat.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  Iterator<Urheilija> i = urheilijat.iterator();
     *  i.next().equals(vaiski1) === true;
     *  i.next() === vaiski2;
     *  i.hasNext() === false;
     *  urheilijat.lisaa(vaiski2);
     *  urheilijat.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try (Scanner fi = new Scanner(new FileInputStream(new File(getTiedostonNimi()))) ) {
            String rivi;
            while (fi.hasNext()) {
                rivi = fi.nextLine();
                rivi.trim();
                if (rivi.charAt(0) == ';') continue;
                Urheilija urheilija = new Urheilija();
                urheilija.parse(rivi);
                lisaa(urheilija);
            }
            muutettu = true;
        } catch (FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + tiedostonPerusNimi + " ei aukea");
        }  
    }
    
    
    /**
     * Luetaan aikaisemmin annetun nimisest‰ tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException{
        lueTiedostosta(tiedostonPerusNimi);
    }
    
    
    /**
     * Tallentaa urheilijoiden tiedot tiedostoon.
     * @throws SailoException jos jokin menee pieleen
     */
    public void tallenna() throws SailoException {
        if (!muutettu) return;
        
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);

        try ( PrintStream fo = new PrintStream(new FileOutputStream(ftied.getCanonicalPath())) ) {
            for (Urheilija urheilija : this) {
                fo.println(urheilija.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }
    
    
    /**
     * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    
    
    /**
     * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }
    
    
    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }
    
    
    /**
     * @return alkioiden m‰‰r‰ taulukossa
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Palauttaa viitteen i:teen urheilijaan. V‰liaikainen!
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
     * Palauttaa alkiot taulukon
     * @return alkiot
     */
    public Urheilija[] annaUrheilijat() {
        return alkiot;
    }
    
    
    /**
     * Luokka urheilijoiden iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Urheilijat urheilijat = new Urheilijat();
     * Urheilija vaiski1 = new Urheilija(), vaiski2 = new Urheilija();
     * vaiski1.rekisteroi(); vaiski2.rekisteroi();
     *
     * urheilijat.lisaa(vaiski1); 
     * urheilijat.lisaa(vaiski2); 
     * urheilijat.lisaa(vaiski1); 
     * 
     * StringBuilder sb = new StringBuilder(30);
     * for (Urheilija urheilija:urheilijat)   // Kokeillaan for-silmukan toimintaa
     * sb.append(" " + urheilija.getId());           
     * 
     * String tulos = " " + vaiski1.getId() + " " + vaiski2.getId() + " " + vaiski1.getId();
     * 
     * sb.toString() === tulos; 
     * 
     * sb = new StringBuilder(30);
     * for (Iterator<Urheilija>  i=urheilijat.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Urheilija urheilija = i.next();
     *   sb.append(" "+urheilija.getId());           
     * }
     * 
     * sb.toString() === tulos;
     * 
     * Iterator<Urheilija>  i=urheilijat.iterator();
     * i.next() == vaiski1  === true;
     * i.next() == vaiski2  === true;
     * i.next() == vaiski1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class UrheilijatIterator implements Iterator<Urheilija> {
        private int kohdalla = 0;


        /**
         * Onko olemassa viel‰ seuraavaa j‰sent‰
         * @see java.util.Iterator#hasNext()
         * @return true jos on viel‰ j‰seni‰
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava j‰sen
         * @return seuraava j‰sen
         * @throws NoSuchElementException jos seuraava alkiota ei en‰‰ ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Urheilija next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei ole");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }
    
    
    /**
     * Palauttaa iteraattorin urheilijoista
     * @return urheiljat iteraattori
     */
    @Override
    public Iterator<Urheilija> iterator() {
        return new UrheilijatIterator();
    }
    
    
    /**
     * Testataan Urheilijat luokkaa
     * @param args ei k‰ytˆss‰
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
            Urheilija urheilija = urheilijat.anna(i); // v‰liaikainen!!!
            System.out.println("Urheilija nro: " + i);
            urheilija.tulosta(System.out);
        }
    }




}
