package fxTulos;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author aleks
 * @version 28.5.2018
 *
 */
public class TulosGUIController {
    @FXML private TextField texthaku;
    @FXML private TextField textNimi;
    @FXML private TextField textHetu;
    @FXML private TextField textSeura;
    @FXML private TextField textLisenssi;
    @FXML private TextField textPuhelin; 

    @FXML
    void handleApua() {
        avustus();
    }

    @FXML
    void handleAvaa() {
        Dialogs.showMessageDialog("Avataan! Mutta ei toimi vielä");
    }

    @FXML
    void handleHaku() {
        Dialogs.showMessageDialog("Haetaan! Mutta ei toimi vielä");
    }

    @FXML
    void handleLopeta() {
        tallenna();
        Platform.exit();
    }

    @FXML
    void handleTallenna() {
        tallenna();
    }

    @FXML
    void handleTietoja() {
        ModalController.showModal(TulosGUIController.class.getResource("TulosTietojaView.fxml"), "Tietoja", null, "");
    }

    @FXML
    void handleTop10() {
        Dialogs.showMessageDialog("Tulostetaan! Mutta ei toimi vielä");
    }

    @FXML
    void handleTulosta() {
        Dialogs.showMessageDialog("Tulostetaan! Mutta ei toimi vielä");
    }

    @FXML
    void handleUusiTulos() {
        ModalController.showModal(TulosGUIController.class.getResource("TulosMuokkaTulosView.fxml"), "Muokkaa Tulos", null, "");
    }

    @FXML
    void handleMuokkaaTulos() {
        ModalController.showModal(TulosGUIController.class.getResource("TulosMuokkaTulosView.fxml"), "Muokkaa Tulos", null, "");
    }
    
    @FXML
    void handleUusiUrheilija() {
        ModalController.showModal(TulosMuokkaaUrheilijaController.class.getResource("TulosMuokkaaUrheilijaView.fxml"), "Muokkaa Urheilija", null, "");
    }
    
    @FXML
    void handleMuokkaaUrheilija() {
        ModalController.showModal(TulosGUIController.class.getResource("TulosMuokkaaUrheilijaView.fxml"), "Muokkaa Tulos", null, "");
    }
    
    /**
     * Tietojen tallennus
     *      
     */
     private void tallenna() {
         Dialogs.showMessageDialog("Tallennetetaan! Mutta ei toimi vielä");
     }
     
     /**
      * Avaa apu ikkunan
      */
     private void avustus() {
         Desktop desktop = Desktop.getDesktop();
         try {
              URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2018k/ht/almailmo");
              desktop.browse(uri);
          } catch (URISyntaxException e) {
              return;
          } catch (IOException e) {
              return;
          }
     }
}
