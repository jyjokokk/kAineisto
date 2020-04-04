package fxKirjasto;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URL;
import java.util.*;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import kirjasto.*;

/**
 * @author jyrki
 * @version Feb 15, 2020
 */
public class KirjastoGUIController implements Initializable {

    // Hakukentant elementit
    @FXML private TextField hakuNimi;
    @FXML private TextField hakuTekija;
    @FXML private TextField hakuISBN;
    @FXML private Button hakuButton;
    @FXML private ListChooser<Teos> hakuTulokset;
    // Paanakyman elementit
    @FXML private TextField tietoTeos;
    @FXML private TextField tietoTekija;
    @FXML private TextField tietoISBN;
    @FXML private TextField tietoStatus;
    @FXML private TextField tietoSijainti;
    // Buttonbarin elementit
    @FXML private Button bottomLisaa;
    @FXML private Button bottomMuokkaa;
    @FXML private Button bottomPoista;
    // Navbarin elementit
    @FXML private MenuItem navClose;
    @FXML private MenuItem navSave;
    @FXML private MenuItem navDelete;
    @FXML private MenuItem navMuokkaa;
    @FXML private MenuItem navHelp;
    @FXML private MenuItem navTietoa;
    // Lisaysdialogin elementit
    @FXML private Button teosPeruuta;
    @FXML private Button toesLisaa;
    
    // Valia-aikainen TextField tulostukselle.
    @FXML private AnchorPane tiedotPanel;
    @FXML private GridPane tiedotGrid;
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }


    @FXML
    private void handleHae() {
        hae(hakuNimi.getText());
    }


    @FXML
    private void handleHelp() {
        help();
    }


    @FXML
    private void handleLisaa() {
        lisaa();
    }


    @FXML
    private void handleMuokkaa() {
        muokkaa();
    }


    @FXML
    private void handlePoista() {
        poista();
    }


    @FXML
    private void handlePoistu() {
        poistu();
    }
    

    @FXML
    private void handleTallenna() {
        tallenna();
    }
    
    
    @FXML
    private void handleTietoa() {
        ModalController.showModal(KirjastoGUIController.class.getResource("AboutView.fxml"), "Kirjasto", null, "");
    }

    // ==========================================================
    // Alapuolella ei kayttomliittymaan suoraan liittyvia metodeja tai
    // funktioita


    private Kirjasto kirjasto;
    private Teos kirjaKohdalla;
    private TextArea tiedotArea = new TextArea();

    private void alusta() {
        kirjasto = new Kirjasto();
        try {
            kirjasto.lueTiedostosta();
        } catch (FileNotFoundException ex) {
            Dialogs.showMessageDialog(ex.getMessage());
        }
        tiedotPanel.getChildren().removeAll(tiedotGrid);
        tiedotPanel.getChildren().add(tiedotArea);
        taytaLista();
        hakuTulokset.addSelectionListener(e -> naytaTeos());
    }


    /**
     * Tulostaa teoksen tiedot valittuun streamiin.
     * Tulostaa toistaiseksi valiaikaiseen TextAreaan
     */
    private void naytaTeos() {
        kirjaKohdalla = hakuTulokset.getSelectedObject();
        if (kirjaKohdalla == null)
            return;
        tiedotArea.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(tiedotArea)) {
            kirjasto.tulostaTiedot(os, kirjasto.haeId(kirjaKohdalla.getId()));
        }
    }

    
    /**
     * Populoi hakutulokset tietorakenteista.
     */
    private void taytaLista() {
        hakuTulokset.clear();
        for (int i = 0; i < kirjasto.getHyllyLkm(); i++) {
            Teos temp = kirjasto.getTeos(i);
            hakuTulokset.add(temp.getNimi(), temp);
        }
        hakuTulokset.setSelectedIndex(0);
    }
    

    /**
     * Lisaa uuden kirjan kokoelmaan, ja paivittaa listan.
     * Lisaa toistaiseksi vain Lotrin.
     */
    @SuppressWarnings("unused")
    private void uusiKirja() {
        try {
            kirjasto.lisaaLotr();
        } catch (TietoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
        taytaLista();
        hakuTulokset.setSelectedIndex(0);
    }


    /**
     * Hakee ja valitsee id:lla teoksen listasta, ja paivittaa listan.
     * Tarkoitettu ohjelman sisaiselle toiminalle, ei kayttajan kaytettavissa.
     */
    @SuppressWarnings("unused")
    private void haeId(int id) {
        hakuTulokset.clear();
        for (int i = 0; i < kirjasto.getTeosLkm(); i++) {
            Hylly kirja = kirjasto.anna(i);
            String nimi = kirjasto.getTeosNimi(kirja);
            Teos teos = kirjasto.getTeos(kirja.getId());
            hakuTulokset.add(nimi, teos);
        }
        hakuTulokset.setSelectedIndex(id);
    }


    /**
     * Hakee kaikki teokset jotka vastaavat hakuehtoa, ja populoi
     * tulokset hakutulos-containeriin.
     * @param ehto Ehto, jolla haetaan.
     */
    private void hae(String ehto) {
        hakuTulokset.clear();
        ArrayList<Teos> tulokset = kirjasto.hae(ehto);
        for (var t : tulokset) {
            hakuTulokset.add(t.getNimi(), t);
        }
        hakuTulokset.setSelectedIndex(0);
    }
    
    
    /**
     * Tallentaa muutokset tiedostoihin.
     */
    private void tallenna() {
        try {
            kirjasto.tallenna();
        } catch (TietoException ex) {
            Dialogs.showMessageDialog(ex.getMessage());
        }
    }


    /**
     * Nayttaa tiedot ohjelmasta ja haettavasta tietokannasta.
     */
    private void help() {
        eiToimi();
    }


    /**
     * Lisaa uuden teoksen tietokantaan.
     */
    private void lisaa() {
        ModalController.showModal(KirjastoGUIController.class.getResource("TeosDialogView.fxml"), "Lisaa teos", null, "");
        // uusiKirja();
    }


    /**
     * Muokkaa valitun teoksen tietoja ja tallentaa muutokset tietokantaan.
     */
    private void muokkaa() {
        eiToimi();
    }


    /**
     * Poistaa valitun teoksen tietokannasta.
     */
    private void poista() {
        eiToimi();
    }


    /**
     * Poistuu ohjelmasta.
     * TODO: Kysy, halutaanko tallentaa jos on tehty muutoksia.
     *      Taman saisi tehtya tekemalla "muutotettu" bool flagin,
     *      joka aina lisattaessa tai poistaessa asettuisi true, ja
     *      tallentaessa false.
     */
    private void poistu() {
        Platform.exit();
    }


    /**
     * Placeholder toiminalle.
     */
    private void eiToimi() {
        Dialogs.showMessageDialog("Ei toimi viela!");
    }

}
