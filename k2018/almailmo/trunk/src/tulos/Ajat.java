package tulos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Tulosrekisterin ajat, joka osaa mm. lis�t� uuden ajan
 * @author Aleksi Ilmonen
 * @version 29.6.2018
 *
 */
public class Ajat implements Iterable<Aika> {
    
    private ArrayList<Aika> alkiot = new ArrayList<Aika>();
    private String tiedostonPerusNimi = "tulokset";
    private boolean muutettu = false;
    
    /**
     * Lis�� ajan listaan
     * @param aika Aika, joka lis�t��n listaan
     * 
     */
    public void lisaa(Aika aika) {
        alkiot.add(aika);
        muutettu = true;
    }
    
    
    /**
     * Palauttaa alkioiden m��r�n listassa
     * @return alkioiden m��r� listassa
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * Haetaan kaikki urheilijan ajat
     * @param urheilijaId urheilijan id, jolla aikoja haetaan
     * @return tietorakenne jossa viiteet l�ydetteyihin aikoihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Ajat ajat = new Ajat();
     *  Aika aika21 = new Aika(2); ajat.lisaa(aika21);
     *  Aika aika11 = new Aika(1); ajat.lisaa(aika11);
     *  Aika aika22 = new Aika(2); ajat.lisaa(aika22);
     *  Aika aika12 = new Aika(1); ajat.lisaa(aika12);
     *  Aika aika23 = new Aika(2); ajat.lisaa(aika23);
     *  Aika aika51 = new Aika(5); ajat.lisaa(aika51);
     *  
     *  List<Aika> loytyneet;
     *  loytyneet = ajat.annaAjat(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = ajat.annaAjat(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == aika11 === true;
     *  loytyneet = ajat.annaAjat(5);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == aika51 === true;
     * </pre> 
     */
    public List<Aika> annaAjat(int urheilijaId) {
        List<Aika> loydetyt = new ArrayList<Aika>();
        for(Aika aika : alkiot) {
            if(aika.getUrheilijaId() == urheilijaId) loydetyt.add(aika);
        }
        return loydetyt;
    }
    
    
    /**Palauttaa viitteen i:teen alkioon
     * @param i monesko alkio
     * @return i:nnes aika
     */
    public Aika anna(int i) {
        if (i<0 || alkiot.size() <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot.get(i);
        
    }
    
    
    /**
     * Asettaa tiedoston perusnimen
     * @param tied tidoston nimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }
    
    
    /**
     * Lukee ajat tiedostosta
     * @param tied tiedoston nimi
     * @throws SailoException jos tulee virhe
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Ajat ajat = new Ajat();
     *  Aika aika1 = new Aika(), aika2 = new Aika();
     *  aika1.taytaAikaTiedot();
     *  aika2.taytaAikaTiedot();
     *  String hakemisto = "testitulos";
     *  String tiedNimi = hakemisto+"/tulokset";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  ajat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  ajat.lisaa(aika1);
     *  ajat.lisaa(aika2);
     *  ajat.tallenna();
     *  ajat = new Ajat();            // Poistetaan vanhat luomalla uusi
     *  ajat.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  //Iterator<Aika> i = ajat.iterator();
     *  //i.next() === aika1;
     *  //i.next() === aika2;
     *  //i.hasNext() === false;
     *  ajat.lisaa(aika2);
     *  ajat.tallenna();
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
                Aika aika = new Aika();
                aika.parse(rivi);
                lisaa(aika);
            }
            muutettu = true;
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto" + tiedostonPerusNimi + "ei aukea");
        }
    }
    
    
    /**
     * Luetaan aikaisemmin annetun nimisest� tiedostosta
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
            for (Aika aika : this) {
                fo.println(aika.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }
    
    
    /**
     * Palauttaa tiedoston nimen, jota k�ytet��n tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    
    
    /**
     * Palauttaa tiedoston nimen, jota k�ytet��n tallennukseen
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
     * Luokka aikojen iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Ajat ajat = new Ajat();
     * Aika aika1 = new Aika(), aika2 = new Aika();
     * aika1.rekisteroi(); aika2.rekisteroi();
     *
     * ajat.lisaa(aika1); 
     * ajat.lisaa(aika2); 
     * ajat.lisaa(aika1); 
     * 
     * StringBuilder sb = new StringBuilder(30);
     * for (Aika aika:ajat)   // Kokeillaan for-silmukan toimintaa
     * sb.append(" " + aika.getId());           
     * 
     * String tulos = " " + aika1.getId() + " " + aika2.getId() + " " + aika1.getId();
     * 
     * sb.toString() === tulos; 
     * 
     * sb = new StringBuilder(30);
     * for (Iterator<Aika>  i=ajat.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Aika aika = i.next();
     *   sb.append(" "+aika.getId());           
     * }
     * 
     * sb.toString() === tulos;
     * 
     * Iterator<Aika>  i=ajat.iterator();
     * i.next() == aika1  === true;
     * i.next() == aika2  === true;
     * i.next() == aika1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class AjatIterator implements Iterator<Aika> {
        private int kohdalla = 0;


        /**
         * Onko olemassa viel� seuraavaa j�sent�
         * @see java.util.Iterator#hasNext()
         * @return true jos on viel� j�seni�
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava j�sen
         * @return seuraava j�sen
         * @throws NoSuchElementException jos seuraava alkiota ei en�� ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Aika next() throws NoSuchElementException {
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
     * @return ajat iteraattori
     */
    @Override
    public Iterator<Aika> iterator() {
        return new AjatIterator();
    }
    

    /**
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Ajat ajat = new Ajat();
        Aika aika1 = new Aika();
        Aika aika2 = new Aika();
        aika1.taytaAikaTiedot();
        aika2.taytaAikaTiedot();
        aika1.rekisteroi();
        aika2.rekisteroi();
        
        ajat.lisaa(aika1);
        ajat.lisaa(aika2);

        System.out.println("============== Ajat testi ==============");
        
        List<Aika> ajat2 = ajat.annaAjat(1);

        for (Aika aika : ajat2) {
            System.out.print(aika.getUrheilijaId() + " ");
            aika.tulosta(System.out);
        }
        
    }

}
