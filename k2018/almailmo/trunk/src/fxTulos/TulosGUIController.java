package fxTulos;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import tulos.Aika;
import tulos.Matka;
import tulos.Rekisteri;
import tulos.SailoException;
import tulos.Urheilija;

/**
 * @author Aleksi Ilmonen
 * @version 28.5.2018
 *
 */
public class TulosGUIController implements Initializable {
    @FXML private TextField textHaku;
    @FXML private TextField textNimi;
    @FXML private TextField textHetu;
    @FXML private TextField textSeura;
    @FXML private TextField textLisenssi;
    @FXML private TextField textPuhelin; 
    @FXML private ListChooser<Urheilija> chooserUrheilija;
    @FXML private ScrollPane panelUrheilija;
    @FXML private GridPane  gridUrheilija;
    @FXML private StringGrid<Aika> tableAika;
    @FXML private ComboBoxChooser<String> cbHaku;
    @FXML private ComboBoxChooser<Matka> cbMatkat;
    
    
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
        hae(0);
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
        muokkaaAika();
    }
    
    @FXML
    void handleUusiUrheilija() {
        uusiUrheilija();
    }
    
    @FXML
    void handleMuokkaaUrheilija() {
        muokkaaUrheilija(kentta);
        
    }
    
    @FXML
    void handlePoistaUrheilija() {
        poistaUrheilija();
    }
    
    @FXML
    void handlePoistaTulos() {
        poistaTulos();
    }
    
    
    //===================================================================================
    
    private Rekisteri rekisteri;
    private Urheilija urheilijaKohdalla;
    private TextField[] texts;
    private int kentta = 0;
    private static Aika apuAika = new Aika();
    private static Urheilija apuUrheilija = new Urheilija();
    
    /**
     * Tekee tarvittavat alustukset.
     * (Vaihtaa gridpanen tilalle ison tekstikentän, johon tulostetaan urheilijoiden tiedot)
     * Alustaa myös urheilijalistan kuuntelijan
     */
    private void alusta() {
        chooserUrheilija.clear();
        chooserUrheilija.addSelectionListener(e -> naytaUrheilija());
        texts = TulosMuokkaaUrheilijaController.luoKentat(gridUrheilija);
        for(TextField text : texts) {
            if(text != null ) {
                text.setOnMouseClicked(e -> {if (e.getClickCount() > 1 ) muokkaaUrheilija(TulosMuokkaaUrheilijaController.getFieldId(e.getSource(),0)); });
                text.focusedProperty().addListener((a,o,n) -> kentta = TulosMuokkaaUrheilijaController.getFieldId(text,0));
            }
        }
        
        cbHaku.clear();
        for (int k = apuUrheilija.ekaKentta(); k < apuUrheilija.getKenttia(); k++) {
            cbHaku.add(apuUrheilija.getKysymys(k), null);
        }
        cbHaku.setSelectedIndex(0);
        
        cbMatkat.addSelectionListener(e -> naytaUrheilija());
        
        // harrastusten alustus
        int eka = apuAika.ekaKentta();
        int lkm = apuAika.getKenttia();
        String[] headings = new String[lkm - eka]; 
        for(int i = 0, k = eka; k<lkm; i++, k++) {
            headings[i] = apuAika.getKysymys(k);
        }
        tableAika.initTable(headings);
        tableAika.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        tableAika.setEditable(false);
        tableAika.setPlaceholder(new Label("Ei vielä tuloksia"));
        tableAika.setColumnSortOrderNumber(1);
        tableAika.setColumnSortOrderNumber(2);
        tableAika.setColumnWidth(1, 60);
        tableAika.setColumnWidth(2, 60);
        
        tableAika.setOnMouseClicked(e -> { if(e.getClickCount() > 1) muokkaaAika();} );
    }
    
    
    /**
     * Alustaa ComboBoxChooserin matkoilla
     * @param cb alustettava ComboBoxChooser
     * @return Alustettu ComboBoxChooser
     */
    public ComboBoxChooser<Matka> alustaMatkat(ComboBoxChooser<Matka> cb) {
        cbMatkat.clear();
        
        List<Matka> matkat = rekisteri.getMatkat();
        for(int i = 0; i < rekisteri.getMatkaLkm(); i++) {
            cb.add(matkat.get(i).getMatka(), matkat.get(i));
        }
        cb.setSelectedIndex(0);
        return cb;
    }
    
    
    /**
     * Lukee tiedostosta tiedot
     * @param nimi tiedoston nimi
     * @return virhe, jos tulee ongelmia
     */
    private String lueTiedosto(String nimi) {
        try {
            rekisteri.lueTiedostosta(nimi);
            cbMatkat = alustaMatkat(cbMatkat);
            hae(0);
            return null;
        } catch (SailoException e) {
            cbMatkat = alustaMatkat(cbMatkat);
            hae(0);
            String virhe = e.getMessage();
            if (virhe != null) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
            
    }
    
    
    /**
     * Luetaan tiedosto
     */
    protected void avaa() {
        lueTiedosto("tulosrekisteri");
    }
    
    
    /**
     * Tietojen tallennus
     * @return null jos onnistuu, muuten virhe tekstinä
     */
     private String tallenna() {
         try {
             rekisteri.tallenna();
             return null;
         } catch (SailoException ex) {
             Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
             return ex.getMessage();
         }
     }
     
     
     /**
      * Poistetaan urheilijan tiedot
      */
     private void poistaUrheilija() {
         if (urheilijaKohdalla == null) return;
         if (!Dialogs.showQuestionDialog("Poisto?", "Poistetaanko varmasti?", "Kyllä", "Ei")) return;
         rekisteri.poista(urheilijaKohdalla);
         int index = chooserUrheilija.getSelectedIndex();
         hae(0);
         chooserUrheilija.setSelectedIndex(index);    
     }
     
     
     /**
     * Poistetaan tulos
     */
    public void poistaTulos() {
        if (!Dialogs.showQuestionDialog("Poisto?", "Poistetaanko varmasti?", "Kyllä", "Ei")) return;
         Aika aika = tableAika.getObject();
         if(aika == null) return; 
         int rivi = tableAika.getRowNr();
         rekisteri.poistaAika(aika);
         naytaAjat(urheilijaKohdalla);
         tableAika.selectRow(rivi);
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
              Dialogs.showMessageDialog("Ongelmia apu-ikkunan avauksessa " + e.getMessage());
              return;
          } catch (IOException e) {
              Dialogs.showMessageDialog("Ongelmia apu-ikkunan avauksessa " + e.getMessage());
              return;
          }
     }
     
     
     /**
      * Näyttää listasta valitun urheilijan tiedot
      */
     private void naytaUrheilija() {
         urheilijaKohdalla = chooserUrheilija.getSelectedObject();
         TulosMuokkaaUrheilijaController.naytaUrheilija(urheilijaKohdalla, texts);
         naytaAjat(urheilijaKohdalla);

     }
     
     
     /**
     * Muokaaan urheilijan tietoja
     */
    private void muokkaaUrheilija(int k) {
         try {
            Urheilija urheilija;
            urheilija = TulosMuokkaaUrheilijaController.avaaUrheilija("Muokkaa Urheilija", urheilijaKohdalla.clone(), k);
            if (urheilija == null) return;
            rekisteri.korvaaTaiLisaa(urheilija);
            hae(urheilija.getId());
        } catch (CloneNotSupportedException e) {
            Dialogs.showMessageDialog("Ongelmia kloonauksessa " + e.getMessage());
        } catch (SailoException e)  {
            Dialogs.showMessageDialog("Ongelmia muokkauksessa " + e.getMessage());
        }
     }
    
    /**
     * Näytetään ajat taulukkoon.
     * Tyhjennetään ensin tauluko ja sitten lisätään siihen kaikki ajat
     * @param urheilija , jonka ajat näytetään
     */
    private void naytaAjat(Urheilija urheilija) {
        tableAika.clear();
        if(urheilija == null) return;
        
        List<Aika> ajat = rekisteri.annaAjat(urheilija, cbMatkat.getSelectedObject());
        if(ajat.size() == 0) return;
        for(Aika aika : ajat) {
            naytaAika(aika);
        }
    }
    
    
    /**
     * Lisätään yhden ajan tiedot taulukkoon
     * @param aika
     */
    private void naytaAika(Aika aika) {
        int kenttia = aika.getKenttia();
        String[] rivi = new String[kenttia-aika.ekaKentta()];
        for(int i = 0, k= aika.ekaKentta(); k < kenttia; i++, k++) {
            rivi[i] = aika.anna(k);
        }
        tableAika.add(aika, rivi);
    }
    
    /**
     * Muokataan urheilijan tulosta
     */
    private void muokkaaAika() {
        int r = tableAika.getRowNr();
        if( r < 0) return;
        Aika aika = tableAika.getObject();
        if(aika == null) return;
        int k = tableAika.getColumnNr() + aika.ekaKentta();
        try {
            aika = TulosMuokkaaTulosController.avaaTulos("Muokkaa tulos", aika.clone(), k, rekisteri);
            if(aika == null) return;
            rekisteri.korvaaTaiLisaa(aika);
            naytaAjat(urheilijaKohdalla);
            tableAika.selectRow(r);
        } catch (CloneNotSupportedException e) {
            Dialogs.showMessageDialog("Ongelmia lisäämisessä (Kloonauksessa): " + e.getMessage());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia lisäämisessä: " + e.getMessage());
        }
    }
     
     
     /**
      * Hakee urheilijoiden tiedot listaan
      * @param urheilijan id numero, joka aktivoidaan haun jälkeen
      */
     private void hae(int id) {
         int nro = id;
         if (nro <= 0 ) {
             Urheilija kohdalla = urheilijaKohdalla;
             if (kohdalla != null) nro = kohdalla.getId();
         }
         
         int k = cbHaku.getSelectedIndex() + apuUrheilija.ekaKentta();
         String ehto = textHaku.getText();
         if(ehto.indexOf('*') < 0) ehto = "*" + ehto + "*";
         chooserUrheilija.clear();
         
         int index = 0;
         List<Urheilija> urheilijat;
         try {
             urheilijat = rekisteri.etsi(ehto, k);
             int i = 0;
             for(Urheilija urheilija : urheilijat) {
                 if(urheilija.getId() == nro) index = i;
                 chooserUrheilija.add(urheilija.getNimi(), urheilija);
                 i++;
             }
         } catch (SailoException ex ) {
             Dialogs.showMessageDialog("Urheilija hakemisessa ongelmia! " + ex.getMessage());
         }
         chooserUrheilija.setSelectedIndex(index);
     }
     
     
     /**
     * Luo uuden jäsenen, jota aletaan editoimaan
     */
    private void uusiUrheilija() {
         Urheilija urheilija = new Urheilija();
         urheilija = TulosMuokkaaUrheilijaController.avaaUrheilija("Uusi urheilija", urheilija, 1);
         urheilija.rekisteroi();
         rekisteri.lisaa(urheilija);
         hae(urheilija.getId());
     }
    
    
    /**
     * Luo uuden ajan, jota aletaan editoimaan
     */
    public void uusiAika() {
        if(chooserUrheilija == null) return;
        Aika aika = new Aika();
        aika = TulosMuokkaaTulosController.avaaTulos("Uusi tulos", aika, 1, rekisteri);
        aika.rekisteroi();
        aika.setUrheilija(chooserUrheilija.getSelectedObject().getId());
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
