package fxTulos;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author Aleksi Ilmonen
 * @version 7.6.2018
 *
 */
public class TulosMuokkaaTulosController implements ModalControllerInterface<String> {

    @FXML private TextField textNimi;
    @FXML private TextField textAika;
    @FXML private TextField textPvm;
    @FXML private ComboBoxChooser<?> chooserMatka;
    
    @FXML
    void handlePeruuta() {
        ModalController.closeStage(textNimi);
    }

    @FXML
    void handlePoista() {
        boolean vastaus = Dialogs.showQuestionDialog("Poisto?",
                "Poistetaanko varmasti?", "Kyllä", "Ei");
        if(vastaus == true ) {
            Dialogs.showMessageDialog("Poistetaan! Mutta ei osata vielä");
            ModalController.closeStage(textNimi);
        }
        else 
            ModalController.closeStage(textNimi);
    }
    
    @FXML
    void handleTallenna() {
        Dialogs.showMessageDialog("Tallennetaan! Mutta ei vielä osata");
    }
    
    /**
     * Avaa tulostemuokkaus ikkunan
     * @param otsikko muokkausikkunan otsikko 
     */
    public static void avaaTulos(String otsikko) {
        ModalController.showModal(TulosMuokkaaTulosController.class.getResource("TulosMuokkaTulosView.fxml"), otsikko, null, "");
    }
    
    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(String arg0) {
        // TODO Auto-generated method stub
        
    }

}
