package fxTulos;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import tulos.Aika;
import tulos.Rekisteri;
import tulos.Urheilija;

/**
 * @author Aleksi Ilmonen
 * @version 28.5.2018
 *
 */
public class TulosGUIController implements Initializable {
    @FXML private TextField texthaku;
    @FXML private TextField textNimi;
    @FXML private TextField textHetu;
    @FXML private TextField textSeura;
    @FXML private TextField textLisenssi;
    @FXML private TextField textPuhelin; 
    @FXML private ListChooser<Urheilija> chooserUrheilija;
    @FXML private ScrollPane panelUrheilija;

    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
        
    }
    
    
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
        uusiAika();
        
    }

    @FXML
    void handleMuokkaaTulos() {
        TulosMuokkaaTulosController.avaaTulos("Muokkaa tulos");
    }
    
    @FXML
    void handleUusiUrheilija() {
        // TulosMuokkaaUrheilijaController.avaaUrheilija("Uusi urheilija");
        uusiUrheilija();
    }
    
    @FXML
    void handleMuokkaaUrheilija() {
        //TulosMuokkaaUrheilijaController.avaaUrheilija("Muokkaa urheilija");
    }
    
    @FXML
    void handlePoistaUrheilija() {
        poista();
    }
    
    @FXML
    void handlePoistaTulos() {
        poista();
    }
    
    
    //===================================================================================
    
    private Rekisteri rekisteri;
    private TextArea areaUrheilija = new TextArea();
    private Urheilija urheilijaKohdalla;
    
    /**
     * Tekee tarvittavat alustukset.
     * (Vaihtaa gridpanen tilalle ison tekstikentän, johon tulostetaan urheilijoiden tiedot)
     * Alustaa myös urheilijalistan kuuntelijan
     */
    private void alusta() {
        panelUrheilija.setContent(areaUrheilija);
        areaUrheilija.setFont(new Font("Courier New", 14));
        panelUrheilija.setFitToHeight(true);
        chooserUrheilija.clear();
        chooserUrheilija.addSelectionListener(e -> naytaUrheilija());
        
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
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
     public boolean voikoSulkea() {
         tallenna();
         return true;
     }
  
     
     /**
      * Avaa apu-ikkunan
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
     
     
     /**
      * Näyttää listasta valitun urheilijan tiedot
      */
     private void naytaUrheilija() {
         urheilijaKohdalla = chooserUrheilija.getSelectedObject();
         if (urheilijaKohdalla == null) return;
         areaUrheilija.setText("");
         
         try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaUrheilija)) {
             tulosta(os, urheilijaKohdalla);
         }
     }
     
     
     /**
      * Hakee urheilijoiden tiedot listaan
      * @param urheilijan id numero, joka aktivoidaam haun jälkeen
      */
     private void hae(int id) {
         chooserUrheilija.clear();
         int index = 0;
         for (int i = 0; i < rekisteri.getUrheilijaLkm(); i++) {
             Urheilija urheilija = rekisteri.annaUrheilija(i);
             if(urheilija.getId() == id) index = i;
             chooserUrheilija.add(urheilija.getNimi(), urheilija);
         }
         chooserUrheilija.getSelectionModel().select(index);
     }
     
     
     /**
     * Luo uuden jäsenen, jota aletaan editoimaan
     */
    public void uusiUrheilija() {
         Urheilija urheilija = new Urheilija();
         urheilija.rekisteroi();
         urheilija.taytaUrheilijaTiedot();
         rekisteri.lisaa(urheilija);
         hae(urheilija.getId());
     }
    
    
    /**
     * Luo uuden ajan, jota aletaan editoimaan
     */
    public void uusiAika() {
        if (urheilijaKohdalla == null) return;
        Aika aika = new Aika(urheilijaKohdalla.getId());
        aika.rekisteroi();
        aika.taytaAikaTiedot();
        rekisteri.lisaa(aika);
        hae(urheilijaKohdalla.getId());
    }
    
    
    /**
     * Tulostaa urheilijan tiedot
     * @param os Tietovirta, johon tulostetaan
     * @param urheilija tulostettava urheilija
     */
    public void tulosta (PrintStream os, final Urheilija urheilija) {
        urheilija.tulosta(os);
        os.println("----------------------------------------------");
        List<Aika> ajat = rekisteri.annaAjat(urheilija.getId());
        for (Aika aika : ajat) {
            aika.tulosta(os);
        }
    }

     
     /**
      * Asetetaan käytettävä rekisteri käyttöliittymään
      * @param rekisteri Rekisteri, jota käytetään tässä käyttöliittymässä
      */
    public void setRekisteri(Rekisteri rekisteri) {
        this.rekisteri = rekisteri;
        
    }


}
