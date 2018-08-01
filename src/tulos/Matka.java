package tulos;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Tulosrekisterin matka, joka osaa itse huolehtia esim. omasta id numerosta
 * @author Aleksi Ilmonen
 * @version 23.6.2018
 *
 */
public class Matka {
    
    private int id;
    private String juoksuMatka = "";
    
    private static int seuraavaId = 1;
    
    
    /**
     * Antaa ajalle seuraavan rekisterinumeron
     * @example
     * <pre name="test">
     *  Matka matka = new Matka();
     *  matka.getId() === 0;
     *  matka.rekisteroi();
     *  Matka matka2 = new Matka();
     *  matka2.rekisteroi();
     *  int n1 = matka.getId();
     *  int n2 = matka2.getId();
     *  n1 === n2-1;
     *  matka.rekisteroi();
     *  matka.getId() === n1;
     */
    public void rekisteroi() {
        if (id != 0) return;
        id = seuraavaId;
        seuraavaId++;
    }
    
    
    /**Asettaa tunnusnumeron ja samalla varmistaa ett� seuraava numero on aina suurempi kuin t�h�n menness� suurin
     * @param nro asetettava id
     */
    public void setId(int nro) {
        id = nro;
        if (id >= seuraavaId) seuraavaId = id + 1;
    }
    
    
    /**
     * Selvitt�� matkan tiedot | erotellusta merkkijonosta
     * Pit�� huolen ett� seuraavaId on suurempi kuin tuleva id
     * @param rivi josta matkan tiedot otetaan
     * @example
     * <pre name="test">
     *   Matka matka = new Matka();
     *   matka.parse("   3  |  100m");
     *   matka.getId() === 3;
     *   matka.toString().equals("3|100m") === true; 
     *
     *   matka.rekisteroi();
     *   int n = matka.getId();
     *   matka.parse(""+(n+20));      
     *   matka.rekisteroi();          
     *   matka.getId() === n+20;
     *     
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setId(Mjonot.erota(sb, '|', id));
        juoksuMatka = Mjonot.erota(sb, '|', juoksuMatka);
    }
    
    
    /**
     * Apumetodi, jolla saadaan t�ytetty� testiarvot matkalle.
     * V�liaikainen!
     */
    public void taytaMatkaTiedot() {
        juoksuMatka = "3000m";
    }
    
    /**
     * Tulostetaan matkan tiedot
     * @param os Tietavirta johon tulostetaan
     */
    public void tulosta(OutputStream os)  {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Tulostetaan matkan tiedot
     * @param out Tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(id + " " + juoksuMatka);
    }
    
    
    /**
     * Palauttaa matkan  id:n
     * @return matkan id
     * 
     */
    public int getId() {
        return id;
    }
    
    
    /**
     * Palauttaa juoksumatkan
     * @return juoksuMatka
     */
    public String getMatka() {
        return juoksuMatka;
    }
    
    
    /** 
     * Palauttaa j�senen tiedot merkkijonona
     * @example
     * <pre name="test">
     * Matka matka = new Matka();
     * matka.parse("   3  |  100m ");
     * matka.toString().equals("3|100m") === true;  
     * </pre>
     */
    @Override
    public String toString() {
        return id + "|" + juoksuMatka;
        
    }

    /**
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Matka matka = new Matka();
        Matka matka2 = new Matka();
        
        matka.rekisteroi();
        matka2.rekisteroi();
        
        matka.taytaMatkaTiedot();
        matka2.taytaMatkaTiedot();
        
        matka.tulosta(System.out);
        matka2.tulosta(System.out);
    }


}
