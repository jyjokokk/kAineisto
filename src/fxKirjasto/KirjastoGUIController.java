package fxKirjasto;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringAndObject;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import kirjasto.*;

/**
 * @author jyrki
 * @version Feb 15, 2020
 */
public class KirjastoGUIController implements Initializable {

    // Hakukentant elementit
    @FXML
    private TextField hakuNimi;
    @FXML
    private TextField hakuTekija;
    @FXML
    private TextField hakuISBN;
    @FXML
    private Button hakuButton;
    @FXML
    private ListChooser<Teos> hakuTulokset;
    // Paanakyman elementit
    @FXML
    private TextField tietoTeos;
    @FXML
    private TextField tietoTekija;
    @FXML
    private TextField tietoISBN;
    @FXML
    private TextField tietoStatus;
    @FXML
    private TextField tietoSijainti;
    // Buttonbarin elementit
    @FXML
    private Button bottomLisaa;
    @FXML
    private Button bottomMuokkaa;
    @FXML
    private Button bottomPoista;
    // Navbarin elementit
    @FXML
    private MenuItem navClose;
    @FXML
    private MenuItem navPrint;
    @FXML
    private MenuItem navSave;
    @FXML
    private MenuItem navDelete;
    @FXML
    private MenuItem navMuokkaa;
    @FXML
    private MenuItem navHelp;
    @FXML
    private MenuItem navTietoa;
    // Lisaysdialogin elementit
    @FXML
    private Button teosPeruuta;
    @FXML
    private Button toesLisaa;
    // Valia-aikainen TextField tulostukselle.
    @FXML
    private AnchorPane tiedotPanel;
    @FXML
    private GridPane tiedotGrid;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }


    @FXML
    private void handleHae() {
        hae(hakuNimi.getText(), hakuISBN.getText(), hakuTekija.getText());
    }


    @FXML
    private void handleHelp() {
        apua();
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
    private void handleTulosta() {
        tulosta();
    }


    @FXML
    private void handleTietoa() {
        ModalController.showModal(
                KirjastoGUIController.class.getResource("AboutView.fxml"),
                "Kirjasto", null, "");
    }

    // ===============================================================
    // Alapuolella ei kayttomliittymaan suoraan liittyvia metodeja tai
    // funktioita
    // ===============================================================

    private Kirjasto kirjasto;
    private Teos kirjaKohdalla;
    // TODO: Poista kun ei tarvita:
    // private TextArea tiedotArea = new TextArea();
    private boolean saveStatus;

    private void alusta() {
        kirjasto = new Kirjasto();
        try {
            kirjasto.lueTiedostosta("aineisto");
        } catch (TietoException ex) {
            Dialogs.showMessageDialog(ex.getMessage());
        }
        saveStatus = true;
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
        try {
            tietoTeos.setText(kirjaKohdalla.getNimi());
            tietoTekija.setText(kirjaKohdalla.getTekija());
            tietoISBN.setText(kirjaKohdalla.getIsbn());
            tietoStatus.setText(kirjasto.getTeosMaara(kirjaKohdalla.getId()));
            tietoSijainti
                    .setText(kirjasto.getTeosPaikka(kirjaKohdalla.getId()));
        } catch (TietoException e) {
            Dialogs.showMessageDialog(
                    "Ongelma tietojen hakemisessa: " + e.getMessage());
        }
    }


    /**
     * Populoi hakutulokset tietorakenteista.
     */
    private void taytaLista() {
        hakuTulokset.clear();
        // for (int i = 0; i < kirjasto.getHyllyLkm(); i++) {
        // Teos temp = kirjasto.getTeos(i);
        // hakuTulokset.add(temp.getNimi(), temp);
        // }
        hae("", "", "");
        hakuTulokset.setSelectedIndex(0);
    }


    /**
     * Hakee ja valitsee id:lla teoksen listasta, ja paivittaa listan.
     * Tarkoitettu ohjelman sisaiselle toiminalle, ei kayttajan kaytettavissa.
     * @throws TietoException jos id:n avulla ei loydy teosta.
     */
    @SuppressWarnings("unused")
    private void haeId(int id) throws TietoException {
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
    private void hae(String nimi, String ISBN, String tekija) {
        hakuTulokset.clear();
        List<Teos> tulokset = kirjasto.hae(nimi, ISBN, tekija);
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
            kirjasto.tallenna("aineisto");
            saveStatus = true;
        } catch (TietoException ex) {
            Dialogs.showMessageDialog(ex.getMessage());
        }
    }


    /**
     * Avaa tiedoston suunnitelman kayttojarjestelman maarittamassa selaimessa.
     * NOTE: Selaimen kaynnistys ei onnistu linuxilla GUIn kautta.
     */
    private void apua() {
        // Desktop desk = Desktop.getDesktop();
        // try {
        // URI suunnitelma = new
        // URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2020k/ht/jyjokokk");
        // desk.browse(suunnitelma);
        // return;
        // } catch (IOException | URISyntaxException e) {
        // e.printStackTrace();
        // }
        ModalController.showModal(
                KirjastoGUIController.class.getResource("HelpView.fxml"),
                "Kirjasto", null, "");
    }


    /**
     * Avaa uuden dialogi-ikkunan uuden teoksen lisaamiseksi.
     */
    private void lisaa() {
        try {
            String s = TeosDialogController.kysyArvot(null,
                    "Syota uuden kirjan tiedot");
            if (s == null)
                return;
            int id = kirjasto.lisaa(s);
            saveStatus = false;
            taytaLista();
            hakuTulokset.setSelectedIndex(id);
        } catch (TietoException e) {
            Dialogs.showMessageDialog(e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Muokkaa valitun teoksen tietoja ja tallentaa muutokset tietokantaan.
     */
    private void muokkaa() {
        if (kirjaKohdalla == null)
            return;
        try {
            String s;
            s = TeosDialogController.naytaTiedot(null,
                    "Paivita tiedot kenttiin ja Muokkaa, tai paina Peruuuta",
                    kirjasto.annaTiedot(kirjaKohdalla.getId()));
            if (s == null)
                return;
            int id = kirjasto.muutaTaiLisaa(s);
            saveStatus = false;
            taytaLista();
            hakuTulokset.setSelectedIndex(id - 1);
        } catch (TietoException ex) {
            Dialogs.showMessageDialog(ex.getMessage());
        }
    }


    /**
     * Poistaa valitun teoksen tietokannasta.
     */
    private void poista() {
        if (kirjaKohdalla == null)
            return;
        try {
            kirjasto.poista(kirjaKohdalla.getId());
            saveStatus = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        taytaLista();
    }


    /**
     * Poistuu ohjelmasta.
     */
    private void poistu() {
        if (saveStatus == false) {
            if (tallennusDialog()) {
                tallenna();
                Platform.exit();
            }
            Platform.exit();
        }
        Platform.exit();
    }


    /**
     * Avaa dialogin, joka tarkistaa haluatko tallentaa tehdyt muutokset tiedostoon.
     * @return true jos kylla, false jos ei
     */
    private boolean tallennusDialog() {
        return Dialogs.showQuestionDialog("Tallentamattomia muutoksia!",
                "Tallennetaanko tehdyt muutokset?", "Tallenna",
                "Jatka tallentamatta");
    }


    /**
     * Kaynnistaa ikkunanakyman tulostuksen mahdollisuudelle.
     */
    private void tulosta() {
        List<Teos> teosList = hakuTulokset.getObjects();
        List<Integer> idList = teosList.stream().map(t -> t.getId())
                .collect(Collectors.toList());
        try {
            String s;
            s = kirjasto.teoksetTulosteeksi(idList);
            fxKirjasto.TulostusController.tulosta(s);
        } catch (TietoException e) {
            Dialogs.showMessageDialog(
                    "Ongelma tietoja hakiessa!" + e.getMessage());
        }
    }

}
