package fxTulos;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import tulos.Urheilija;

/**
 * @author Aleksi Ilmonen
 * @version 7.6.2018
 *
 *Kontrolleri urheilijan muokkausikkunalle
 */
public class TulosMuokkaaUrheilijaController implements ModalControllerInterface<Urheilija>, Initializable {

    @FXML private TextField textNimi;
    @FXML private TextField textSotu;
    @FXML private TextField textSeura;
    @FXML private TextField textLisenssi;
    @FXML private TextField textPuhelin;
    @FXML private Label     labelVirhe;
    @FXML private GridPane  gridUrheilija;
    
    
    @FXML
    void handlePeruuta() {
        urheilijaKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }

    @FXML
    void handlePoista() {
        boolean vastaus = Dialogs.showQuestionDialog("Poisto?",
                "Poistetaanko varmasti?", "Kyll‰", "Ei");
        if(vastaus == true ) {
            Dialogs.showMessageDialog("Poistetaan! Mutta ei osata viel‰");
            ModalController.closeStage(labelVirhe);
        }
        else 
            ModalController.closeStage(labelVirhe);
    }
    
    @FXML
    void handleTallenna() {
        if( urheilijaKohdalla != null && urheilijaKohdalla.getNimi().trim().equals("") ) {
            naytaVirhe("Nimi ei saa olla tyhj‰");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }
    
    /**
     * avaa urheilijan muokkausikkunan
     * @param otsikko muokkausikkunan otsikko
     * @param oletus urheilija, jonka tietoja muokataan
     * @param kentta mik‰ kentta saa fokuksen kun n‰ytet‰‰n
     * @return Urheilija, jota muokataan
     */
    public static Urheilija avaaUrheilija(String otsikko, Urheilija oletus, int kentta) {
        return ModalController.<Urheilija, TulosMuokkaaUrheilijaController>showModal(TulosMuokkaaUrheilijaController.class.getResource("TulosMuokkaaUrheilijaView.fxml"),
                otsikko, null, oletus, ctrl -> ctrl.setKentta(kentta));
    }

    
    /**
     * Palauttaa urheilijan, jonka tietoja muokataan
     */
    @Override
    public Urheilija getResult() {
        return urheilijaKohdalla;
    }

    
    /**
     * Asettaa tietojen muokkauskenttien fokuksen
     */
    @Override
    public void handleShown() {
        kentta = Math.max(apuUrheilija.ekaKentta(), Math.min(kentta, apuUrheilija.getKenttia()-1));
        texts[kentta].requestFocus();
        
    }

    
    /**
     * Asettaa oletus-urheilijan, jonka tiedot n‰ytet‰‰n
     */
    @Override
    public void setDefault(Urheilija urheilija) {
        urheilijaKohdalla = urheilija;
        naytaUrheilija(urheilijaKohdalla, texts);
    }

    
    /**
     * Alustaa Dialogin
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
        
    }
    
    
    //==============================================================================
    
    private Urheilija urheilijaKohdalla;
    private static Urheilija apuUrheilija = new Urheilija();
    private TextField[] texts;
    private int kentta = 0;
    
    
    /**
     * N‰ytt‰‰ urheilijan tiedot
     * @param urheilija urheilija
     * @param text taulukko texts-kentist‰, joissa tiedot n‰kyv‰t
     */
    public static void naytaUrheilija(Urheilija urheilija, TextField[] text) {
        if (urheilija == null) return;
        for(int k = urheilija.ekaKentta(); k < urheilija.getKenttia(); k++) {
            text[k].setText(urheilija.anna(k));
        }
    }
    
    
    private void naytaVirhe(String virhe) {
        if( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
    
    /**
     * Alustaa dialogin
     */
    private void alusta() {
        texts = luoKentat(gridUrheilija);
        for( TextField text : texts) {
            if(text != null) {
                text.setOnKeyReleased(e -> kasitteleMuutosUrheilijaan((TextField)(e.getSource())));
            }
        }
    }
    
    
    /**
     * Luodaan GridPaneen urheilijan tiedot
     * @param gridUrheilija mihin tiedot luodaan
     * @return tekstikent‰t
     */
    public static TextField[] luoKentat(GridPane gridUrheilija) {
        gridUrheilija.getChildren().clear();
        TextField[] texts = new TextField[apuUrheilija.getKenttia()];
        
        for(int i = 0, k = apuUrheilija.ekaKentta(); k < apuUrheilija.getKenttia(); k++, i++) {
            Label label = new Label(apuUrheilija.getKysymys(k));
            gridUrheilija.add(label, 0, i);
            TextField text = new TextField();
            texts[k] = text;
            text.setId("t" + k);
            gridUrheilija.add(text,  1,  i);
        }
        return texts;
    }
    
    
    /**
     * Tyhjent‰‰ tekstikent‰t
     * @param texts tyhjennett‰v‰t kent‰t
     */
    public static void tyhjenna(TextField[] texts) {
        for (TextField text: texts) {
            if(text != null) text.setText("");
        }
    }
    
    
    /**
     * K‰sitell‰‰n urheilijaan tullut muutos
     * @param k Monesko kentt‰
     * @param text muuttunut kett‰ 
     */
    private void kasitteleMuutosUrheilijaan(TextField text) {
        if(urheilijaKohdalla == null) return;
        int k = getFieldId(text, apuUrheilija.ekaKentta());
        String s = text.getText();
        String virhe = null;
        virhe = urheilijaKohdalla.aseta(k, s);
        if(virhe == null) {
            Dialogs.setToolTipText(text, "");
            text.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(text, virhe);
            text.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        }
    }
    
    
    /**
     * Palauttaan komponentin id:st‰ saatava luku
     * @param obj tutkittava komponentti
     * @param oletus mik‰ arvo, jos id ei ole kunnollinen
     * @return komponentin id lukuna
     */
    public static int getFieldId(Object obj, int oletus) {
        if( !(obj instanceof Node)) return oletus;
        Node node = (Node)obj;
        String s = node.getId();
        if(s.length() < 1) return oletus;
        return Mjonot.erotaInt(s.substring(1),  oletus);
    }
    
    
    /** 
     * Asettaa kent‰n arvon
     * @param kentta kent‰ksi asetettava arvo
     */
    private void setKentta(int kentta) {
        this.kentta = kentta;
    }
    
    
}
