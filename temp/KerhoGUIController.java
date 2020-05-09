package fxKerho;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kerho.Harrastus;
import kerho.Jasen;
import kerho.Kerho;
import kerho.SailoException;

/**
 * Luokka kerhon käyttöliittymän tapahtumien hoitamiseksi.
 * 
 * @author vesal
 * @version 31.12.2015
 * @version 5.1.2015
 * @version 5.2.2017 - lisätty jäsenien käsittely 
 * @version 5.2.2017 - lisätty harrastusten käsittely 
 */
public class KerhoGUIController implements Initializable {

    @FXML private TextField hakuehto;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private Label labelVirhe;
    @FXML private ScrollPane panelJasen;
    @FXML private ListChooser<Jasen> chooserJasenet;
    @FXML private StringGrid<Harrastus> tableHarrastukset;
    @FXML private TextField editNimi; 
    @FXML private TextField editHetu; 
    @FXML private TextField editKatuosoite;
    @FXML private TextField editPostinumero;    
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();      
    }

    
    @FXML private void handleHakuehto() {
        if ( jasenKohdalla != null )
            hae(jasenKohdalla.getTunnusNro()); 
    }
    
    
    @FXML private void handleTallenna() {
        tallenna();
    }
    
    
    @FXML private void handleAvaa() {
        avaa();
    }
    
    
    @FXML private void handleTulosta() {
        TulostusController tulostusCtrl = TulostusController.tulosta(null); 
        tulostaValitut(tulostusCtrl.getTextArea()); 
    } 

    
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    } 

    
    @FXML private void handleUusiJasen() {
        uusiJasen();
    }
    
    
    @FXML private void handleMuokkaaJasen() {
        muokkaa();
    }
    
    
    @FXML private void handlePoistaJasen() {
        Dialogs.showMessageDialog("Vielä ei osata poistaa jäsentä");
    }
    
     
    @FXML private void handleUusiHarrastus() {
        uusiHarrastus(); 
    }
    

    @FXML private void handleMuokkaaHarrastus() {
        ModalController.showModal(KerhoGUIController.class.getResource("HarrastusDialogView.fxml"), "Harrastus", null, "");
    }
    

    @FXML private void handlePoistaHarrastus() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa harrastusta");
    }
    

    @FXML private void handleApua() {
        avustus();
    }
    

    @FXML private void handleTietoja() {
        // Dialogs.showMessageDialog("Ei osata vielä tietoja");
        ModalController.showModal(KerhoGUIController.class.getResource("AboutView.fxml"), "Kerho", null, "");
    }
    

//===========================================================================================    
// Tästä eteenpäin ei käyttöliittymään suoraan liittyvää koodia    
    
    private String kerhonnimi = "kelmit";
    private Kerho kerho;
    private Jasen jasenKohdalla;
    private TextField edits[]; 
    
    
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaan tulostaa jäsenten tiedot.
     * Alustetaan myös jäsenlistan kuuntelija 
     */
    protected void alusta() {
        chooserJasenet.clear();
        chooserJasenet.addSelectionListener(e -> naytaJasen());
        
        edits = new TextField[]{editNimi, editHetu, editKatuosoite, editPostinumero}; 
    }

    
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
    
    private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
    }
    
    
    /**
     * Alustaa kerhon lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta kerhon tiedot luetaan
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    protected String lueTiedosto(String nimi) {
        kerhonnimi = nimi;
        setTitle("Kerho - " + kerhonnimi);
        try {
            kerho.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
     }

    
    /**
     * Kysytään tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     */
    public boolean avaa() {
        String uusinimi = KerhonNimiController.kysyNimi(null, kerhonnimi);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }

    
    /**
     * Tietojen tallennus
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    private String tallenna() {
        try {
            kerho.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
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
     * Näyttää listasta valitun jäsenen tiedot tekstikenttiin. 
     */
    protected void naytaJasen() {
        jasenKohdalla = chooserJasenet.getSelectedObject();
        if (jasenKohdalla == null) return;
        
        JasenDialogController.naytaJasen(edits, jasenKohdalla); 
        naytaHarrastukset(jasenKohdalla);
    }


    /**
     * Hakee jäsenten tiedot listaan
     * @param jnro jäsenen numero, joka aktivoidaan haun jälkeen
     */
    protected void hae(int jnro) {
        int k = cbKentat.getSelectionModel().getSelectedIndex();
        String ehto = hakuehto.getText(); 
        if (k > 0 || ehto.length() > 0)
            naytaVirhe(String.format("Ei osata hakea (kenttä: %d, ehto: %s)", k, ehto));
        else
            naytaVirhe(null);
        
        chooserJasenet.clear();

        int index = 0;
        Collection<Jasen> jasenet;
        try {
            jasenet = kerho.etsi(ehto, k);
            int i = 0;
            for (Jasen jasen:jasenet) {
                if (jasen.getTunnusNro() == jnro) index = i;
                chooserJasenet.add(jasen.getNimi(), jasen);
                i++;
            }
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Jäsenen hakemisessa ongelmia! " + ex.getMessage());
        }
        chooserJasenet.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää jäsenen
    }


    /**
     * Luo uuden jäsenen jota aletaan editoimaan 
     */
    protected void uusiJasen() {
        try {
            Jasen uusi = new Jasen();
            uusi = JasenDialogController.kysyJasen(null, uusi);  
            if ( uusi == null ) return;
            uusi.rekisteroi();
            kerho.lisaa(uusi);
            hae(uusi.getTunnusNro());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
    }
    
    
    private void naytaHarrastukset(Jasen jasen) {
        tableHarrastukset.clear();
        if ( jasen == null ) return;
        
        try {
            List<Harrastus> harrastukset = kerho.annaHarrastukset(jasen);
            if ( harrastukset.size() == 0 ) return;
            for (Harrastus har: harrastukset)
                naytaHarrastus(har);
        } catch (SailoException e) {
            // naytaVirhe(e.getMessage());
        } 
    }

    
    private void naytaHarrastus(Harrastus har) {
        String[] rivi = har.toString().split("\\|"); // TODO: huono ja tilapäinen ratkaisu
        tableHarrastukset.add(har,rivi[2],rivi[3],rivi[4]);
    }
    
    
    /**
     * Tekee uuden tyhjän harrastuksen editointia varten
     */
    public void uusiHarrastus() {
        if ( jasenKohdalla == null ) return; 
        Harrastus har = new Harrastus(); 
        har.rekisteroi(); 
        har.vastaaPitsinNyplays(jasenKohdalla.getTunnusNro()); 
        try {
            kerho.lisaa(har);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia lisäämisessä! " + e.getMessage());
        } 
        hae(jasenKohdalla.getTunnusNro());         
    }


    private void muokkaa() { 
        if ( jasenKohdalla == null ) return; 
        try { 
            Jasen jasen; 
            jasen = JasenDialogController.kysyJasen(null, jasenKohdalla.clone()); 
            if ( jasen == null ) return; 
            kerho.korvaaTaiLisaa(jasen); 
            hae(jasen.getTunnusNro()); 
        } catch (CloneNotSupportedException e) { 
            // 
        } catch (SailoException e) { 
            Dialogs.showMessageDialog(e.getMessage()); 
        } 
    }     
    
    
    /**
     * @param kerho Kerho jota käytetään tässä käyttöliittymässä
     */
    public void setKerho(Kerho kerho) {
        this.kerho = kerho;
        naytaJasen();
    }

    
    /**
     * Näytetään ohjelman suunnitelma erillisessä selaimessa.
     */
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2019k/ht/vesal");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }

    
    /**
     * Tulostaa jäsenen tiedot
     * @param os tietovirta johon tulostetaan
     * @param jasen tulostettava jäsen
     */
    public void tulosta(PrintStream os, final Jasen jasen) {
        os.println("----------------------------------------------");
        jasen.tulosta(os);
        os.println("----------------------------------------------");
        try {
            List<Harrastus> harrastukset = kerho.annaHarrastukset(jasen);
            for (Harrastus har:harrastukset) 
                har.tulosta(os);     
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Harrastusten hakemisessa ongelmia! " + ex.getMessage());
        }      
    }
    
    
    /**
     * Tulostaa listassa olevat jäsenet tekstialueeseen
     * @param text alue johon tulostetaan
     */
    public void tulostaValitut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan kaikki jäsenet");
            Collection<Jasen> jasenet = kerho.etsi("", -1); 
            for (Jasen jasen:jasenet) { 
                tulosta(os, jasen);
                os.println("\n\n");
            }
        } catch (SailoException ex) { 
            Dialogs.showMessageDialog("Jäsenen hakemisessa ongelmia! " + ex.getMessage()); 
        }
    }
}
