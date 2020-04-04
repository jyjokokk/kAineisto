package fxKirjasto;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.PrintStream;
import java.net.URL;
import java.util.*;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
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
    @FXML private MenuItem navDelete;
    @FXML private MenuItem navMuokkaa;
    @FXML private MenuItem menuHelp;
    
    @FXML private AnchorPane tiedotPanel;
    @FXML private GridPane tiedotGrid;
    @FXML private ListChooser<Teos> hakuTulokset;
    
    
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

    // ==========================================================
    // Alapuolella ei kayttomliittymaan suoraan liittyvia metodeja tai
    // funktioita

    private Kirjasto kirjasto;
    private Teos kirjaKohdalla;
    private TextArea tiedotArea = new TextArea();

    private void alusta() {
        kirjasto = new Kirjasto();
        kirjasto.lueTiedostosta();
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
     * Hakee tietokannasta kaikki teokset jotka vastaavat
     * hakutermeja.
     */
    private void hae() {
        haeId(0);
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
     * Nayttaa tiedot ohjelmasta ja haettavasta tietokannasta.
     */
    private void help() {
        eiToimi();
    }


    /**
     * Lisaa uuden teoksen tietokantaan.
     */
    private void lisaa() {
        // eiToimi();
        uusiKirja();
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
     */
    private void poistu() {
        eiToimi();
    }


    /**
     * Placeholder toiminalle.
     */
    private void eiToimi() {
        //
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Ei toimi");
        alert.setHeaderText(null);
        alert.setContentText("Ei toimi!");
        alert.showAndWait();

    }

}
