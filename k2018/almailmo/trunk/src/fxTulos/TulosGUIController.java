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
        TulosMuokkaaTulosController.avaaTulos("Uusi tulos");
        
    }

    @FXML
    void handleMuokkaaTulos() {
        TulosMuokkaaTulosController.avaaTulos("Muokkaa tulos");
    }
    
    @FXML
    void handleUusiUrheilija() {
        TulosMuokkaaUrheilijaController.avaaUrheilija("Uusi urheilija");
    }
    
    @FXML
    void handleMuokkaaUrheilija() {
        TulosMuokkaaUrheilijaController.avaaUrheilija("Muokkaa urheilija");
    }
    
    @FXML
    void handlePoistaUrheilija() {
        poista();
    }
    
    @FXML
    void handlePoistaTulos() {
        poista();
    }
    /**
     * Tietojen tallennus
     *      
     */
     private void tallenna() {
         Dialogs.showMessageDialog("Tallennetetaan! Mutta ei toimi vielä");
     }
     
     private void poista() {
         boolean vastaus = Dialogs.showQuestionDialog("Poisto?",
                 "Poistetaanko varmasti?", "Kyllä", "Ei");
         if(vastaus == true ) {
             Dialogs.showMessageDialog("Poistetaan! Mutta ei osata vielä");
         }
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
