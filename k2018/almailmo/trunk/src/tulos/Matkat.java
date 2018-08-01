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
 * Tulosrekisterin matkat, joka osaa mm. lis‰t‰ uuden matkan
 * @author Aleksi Ilmonen
 * @version 29.6.2018
 *
 */
public class Matkat implements Iterable<Matka> {
    
    private ArrayList<Matka> alkiot = new ArrayList<Matka>();
    private String tiedostonPerusNimi = "matkat";
    private boolean muutettu = true;

    
    /**
     * Lis‰‰ matkan tietorakenteeseen
     * @param matka Matka, joka lis‰t‰‰n
     */
    public void lisaa(Matka matka) {
        alkiot.add(matka);
        muutettu = true;
    }
    
    
    /**
     * Palauttaa id numeroa vastaavan matkan
     * @param id Matkan id
     * @return Id numeroa vastaava matka
     * @example
     * <pre name="test">
     * Matkat matkat = new Matkat();
     * Matka matka1 = new Matka();
     * Matka matka2 = new Matka();
     * Matka matka3 = new Matka();
     * matka1.rekisteroi(); 
     * matka2.rekisteroi(); 
     * matka3.rekisteroi(); 
     * matkat.lisaa(matka1);
     * matkat.lisaa(matka2);
     * matkat.lisaa(matka3);
     * 
     * int a = matka1.getId();
     * matka1 == matkat.annaMatka(a) === true;
     * matka2 == matkat.annaMatka(a+1) === true;
     * matka3 == matkat.annaMatka(a+5) === false;
     * 
     * 
     */
    public Matka annaMatka(int id) {
        for(Matka matka : alkiot) {
            if (matka.getId() == id) return matka;
        }
        return null;
    }
    
    
    /**Palauttaa viitteen i:teen alkioon
     * @param i monesko alkio
     * @return i:nnes matka
     */
    public Matka anna(int i) {
        if (i<0 || alkiot.size() <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot.get(i);
        
    }
    
    
    /**
     * Asettaa tiedoston perusnimen
     * @param tied tallennustidoston nimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }
    
    
    /**
     * Lukee matkat tiedostosta
     * @param tied Tiedoston nimi
     * @throws SailoException Jos lukeminen ep‰onnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Matkat matkat = new Matkat();
     *  Matka matka1 = new Matka(), matka2 = new Matka();
     *  matka1.taytaMatkaTiedot();
     *  matka2.taytaMatkaTiedot();
     *  String hakemisto = "testitulos";
     *  String tiedNimi = hakemisto+"/matkat";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  matkat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  matkat.lisaa(matka1);
     *  matkat.lisaa(matka2);
     *  matkat.tallenna();
     *  matkat = new Matkat();            // Poistetaan vanhat luomalla uusi
     *  matkat.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  Iterator<Matka> i = matkat.iterator();
     *  i.next() === matka1;
     *  i.next() === matka2;
     *  i.hasNext() === false;
     *  matkat.lisaa(matka2);
     *  matkat.tallenna();
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
                Matka matka = new Matka();
                matka.parse(rivi);
                lisaa(matka);
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
     * Tallentaa matkojen tiedot tiedostoon.
     * @throws SailoException jos jokin menee pieleen
     */
    public void tallenna() throws SailoException {
        if (!muutettu) return;
        
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);

        try ( PrintStream fo = new PrintStream(new FileOutputStream(ftied.getCanonicalPath())) ) {
            for (Matka matka : this) {
                fo.println(matka.toString());
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
     * Palauttaa listan alkioiden lukum‰‰r‰n
     * @return alkioiden lukum‰‰r‰
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * Palauttaa alkiot
     * @return alkiot
     */
    public List<Matka> getMatkat() {
        return alkiot;
    }
    
    
    /**
     * Luokka matkojen iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Matkat matkat = new Matkat();
     * Matka matka1 = new Matka(), matka2 = new Matka();
     * matka1.rekisteroi(); matka2.rekisteroi();
     * matka1.getId();
     *
     * matkat.lisaa(matka1); 
     * matkat.lisaa(matka2); 
     * matkat.lisaa(matka1); 
     * 
     * StringBuilder sb = new StringBuilder(30);
     * for (Matka matka : matkat) {  // Kokeillaan for-silmukan toimintaa
     * sb.append(" " + matka.getId());
     * }           
     * 
     * String tulos = " " + matka1.getId() + " " + matka2.getId() + " " + matka1.getId();
     * 
     * sb.toString() === tulos; 
     * 
     * sb = new StringBuilder(30);
     * for (Iterator<Matka>  i=matkat.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Matka matka = i.next();
     *   sb.append(" "+matka.getId());           
     * }
     * 
     * sb.toString() === tulos;
     * 
     * Iterator<Matka>  i=matkat.iterator();
     * i.next() == matka1  === true;
     * i.next() == matka2  === true;
     * i.next() == matka1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class MatkatIterator implements Iterator<Matka> {
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
        public Matka next() throws NoSuchElementException {
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
     * Palauttaa iteraattorin matkoista
     * @return urheiljat iteraattori
     */
    @Override
    public Iterator<Matka> iterator() {
        return new MatkatIterator();
    }
    
    
    /**
     * Testataan matkat luokkaa
     * @param args ei k‰ytˆss‰
     */
    public static void main(String[] args) {
        Matkat matkat = new Matkat();
        Matka matka1 = new Matka();
        Matka matka2 = new Matka();
        matka1.rekisteroi();
        matka2.rekisteroi();
        matka1.taytaMatkaTiedot();
        matka2.taytaMatkaTiedot();
        
        matkat.lisaa(matka1);
        matkat.lisaa(matka2);
        
        System.out.println("============== Matkat testi ==============");
        
        Matka matka3 = matkat.annaMatka(1);
        matka3.tulosta(System.out);
        Matka matka4 = matkat.annaMatka(2);
        matka4.tulosta(System.out);
    }

}
