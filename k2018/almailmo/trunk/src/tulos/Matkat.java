package tulos;

import java.util.ArrayList;

/**
 * Tulosrekisterin matkat, joka osaa mm. lis�t� uuden matkan
 * @author Aleksi Ilmonen
 * @version 29.6.2018
 *
 */
public class Matkat {
    
    private ArrayList<Matka> alkiot = new ArrayList<Matka>();

    
    /**
     * Lis�� matkan tietorakenteeseen
     * @param matka Matka, joka lis�t��n
     */
    public void lisaa(Matka matka) {
        alkiot.add(matka);
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
    
    /**
     * Palauttaa listan alkioiden lukum��r�n
     * @return alkioiden lukum��r�
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * Testataan matkat luokkaa
     * @param args ei k�yt�ss�
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
