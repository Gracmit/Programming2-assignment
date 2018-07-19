package tulos;

/**
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille.
 * @author Aleksi Ilmonen
 * @version 16.7.2018
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;


    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa
     * käytettävä viesti
     * @param viesti Poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}