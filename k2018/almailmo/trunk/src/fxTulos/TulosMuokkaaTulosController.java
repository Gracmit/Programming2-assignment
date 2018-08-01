package fxTulos;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
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
import tulos.Aika;
import tulos.Matka;
import tulos.Rekisteri;


/**
 * @author Aleksi Ilmonen
 * @version 7.6.2018
 *
 */
public class TulosMuokkaaTulosController implements ModalControllerInterface<Aika>, Initializable {

    @FXML private TextField textNimi;
    @FXML private TextField textAika;
    @FXML private TextField textPvm;
    @FXML private ComboBoxChooser<Matka> chooserMatka;
    @FXML private GridPane gridAika;
    @FXML private Label labelVirhe;
    
    @FXML
    void handlePeruuta() {
        ModalController.closeStage(textNimi);
    }

    @FXML
    void handlePoista() {
        boolean vastaus = Dialogs.showQuestionDialog("Poisto?",
                "Poistetaanko varmasti?", "Kyll‰", "Ei");
        if(vastaus == true ) {
            Dialogs.showMessageDialog("Poistetaan! Mutta ei osata viel‰");
            ModalController.closeStage(textNimi);
        }
        else 
            ModalController.closeStage(textNimi);
    }
    
    @FXML
    void handleTallenna() {
        ModalController.closeStage(labelVirhe);
    }
    
    
    @Override
    public Aika getResult() {
        return aikaKohdalla;
    }

    @Override
    public void setDefault(Aika aika) {
        aikaKohdalla = aika;
        naytaAika(aikaKohdalla, texts);
        
        
        
    }
    

    @Override
    public void handleShown() {
        kentta = Math.max(apuAika.ekaKentta(), Math.min(kentta, apuAika.getKenttia()-1));
        texts[kentta].requestFocus();
        
    }


    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
        
    }
    
    /**
     * avaa urheilijan muokkausikkunan
     * @param otsikko muokkausikkunan otsikko
     * @param oletus urheilija, jonka tietoja muokataan
     * @param kenttaa mik‰ kentta saa fokuksen kun n‰ytet‰‰n
     * @param rekister rekisteri
     * @return Urheilija, jota muokataan
     */
    public static Aika avaaTulos(String otsikko, Aika oletus, int kenttaa, Rekisteri rekister) {
        rekisteri = rekister;
        return ModalController.<Aika, TulosMuokkaaTulosController>showModal(TulosMuokkaaTulosController.class.getResource("TulosMuokkaTulosView.fxml"),
                otsikko, null, oletus, ctrl -> ctrl.setKentta(kenttaa));
    }
    
    
    //======================================================================================================================
    
    private static Rekisteri rekisteri;
    private Aika aikaKohdalla;
    private static Aika apuAika = new Aika();
    private TextField[] texts;
    private int kentta = 0;
    
    
    /**
     * Alustaa Dialogin
     */
    private void alusta() {
        texts = luoKentat(gridAika, chooserMatka);
        chooserMatka.addSelectionListener(e -> kasitteleMuutos());
        for( TextField text : texts) {
            if(text != null) {
                text.setOnKeyReleased(e -> kasitteleMuutosAikaan((TextField)(e.getSource())));
            }
        }
    }
    
    
    /**
     * Alustaa ComboBoxChooserin matkoilla
     * @param cb alustettava ComboBoxChooser
     * @return Alustettu ComboBoxChooser
     */
    public static ComboBoxChooser<Matka> alustaMatkat(ComboBoxChooser<Matka> cb) {
        cb.clear();
        List<Matka> matkat = rekisteri.getMatkat();
        for(int i = 0; i < rekisteri.getMatkaLkm(); i++) {
            cb.add(matkat.get(i).getMatka(), matkat.get(i));
        }
        return cb;
    }
    
    
    /**
     * Luodaan GridPaneen ajan tiedot
     * @param gridAika mihin tiedot luodaan
     * @param cb ComboBoxChooser
     * @return tekstikent‰t
     */
    public static TextField[] luoKentat(GridPane gridAika, ComboBoxChooser<Matka> cb) {
        gridAika.getChildren().clear();
        TextField[] texts = new TextField[apuAika.getKenttia()];
        ComboBoxChooser<Matka> cbc = cb;
        cbc = alustaMatkat(cb);
        gridAika.add(new Label("Matka"), 0, 0);
        gridAika.add(cbc, 1, 0);
        
        
        
        for(int i = 0, k = apuAika.ekaKentta(); k < apuAika.getKenttia(); k++, i++) {
            Label label = new Label(apuAika.getKysymys(k));
            gridAika.add(label, 0, i+1);
            TextField text = new TextField();
            texts[k] = text;
            text.setId("t" + k);
            gridAika.add(text,  1,  i+1);
        }
        return texts;
    }
    
    
    /**
     * K‰sitell‰‰n aikaan tullut muutos
     * @param k Monesko kentt‰
     * @param text muuttunut kett‰ 
     */
    private void kasitteleMuutosAikaan(TextField text) {
        if(aikaKohdalla == null) return;
        int k = getFieldId(text, apuAika.ekaKentta());
        String s = text.getText();
        String virhe = null;
        virhe = aikaKohdalla.aseta(k, s);
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
    
    
    private void kasitteleMuutos() {
        aikaKohdalla.setMatka(chooserMatka.getSelectedObject().getId());
    }
    
    
    /**
     * N‰ytt‰‰ ajan tiedot
     * @param aika Aika
     * @param text taulukko texts-kentist‰, joissa tiedot n‰kyv‰t
     */
    public static void naytaAika(Aika aika, TextField[] text) {
        if (aika == null) return;
        for(int k = aika.ekaKentta(); k < aika.getKenttia(); k++) {
            text[k].setText(aika.anna(k));
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
