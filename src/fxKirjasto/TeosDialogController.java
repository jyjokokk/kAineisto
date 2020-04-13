package fxKirjasto;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.*;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller dialogi-ikkunalle, jolla lisataan uutta aineistoa
 * tietokantaan kysymalla kayttajalle nakyvat attribuutit tekstikenttien
 * kautta.
 * @author jyjokokk
 * @version Apr 4, 2020
 */
public class TeosDialogController
        implements ModalControllerInterface<String>, Initializable {

    @FXML
    private TextField editNimi;
    @FXML
    private TextField editTekija;
    @FXML
    private TextField editISBN;
    @FXML
    private TextField editJulkaisuvuosi;
    @FXML
    private TextField editKategoria;
    @FXML
    private TextField editKuvaus;
    @FXML
    private TextField editHyllypaikka;
    @FXML
    private TextField editMaara;
    @FXML
    private Label labelVirhe;
    @FXML
    private Button buttonOK;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }


    @FXML
    private void handleOK() {
        for (var t : arvot) {
            if (t.getText().trim().equals("") && t != editKuvaus) {
                t.setStyle("-fx-border-color:RED");
                setVirhe("Tayta merkityt arvot!");
                return;
            }
        }
        concat();
        ModalController.closeStage(labelVirhe);
    }


    @FXML
    private void handleCancel() {
        palaute = null;
        ModalController.closeStage(labelVirhe);
    }

    // ===============================================================
    // Alapuolella ei-kayttomliittymaan suoraan liittyvia metodeja tai
    // funktioita
    // ===============================================================

    private String palaute = "";
    private int id;
    private int kid;
    private TextField arvot[];

    @Override
    public String getResult() {
        return palaute;
    }


    @Override
    public void handleShown() {
        editNimi.requestFocus();
    }


    @Override
    public void setDefault(String oletus) {
        labelVirhe.setText(oletus);
    }


    /**
     * Asettaa annetun viestin virhelabelille.
     * @param s viesti joka asetetaan.
     */
    private void setVirhe(String s) {
        labelVirhe.setText(s);
    }


    /**
     * Alustaa arvot kohdilleen.
     */
    protected void alusta() {
        setVirhe(
                "Lisaa uusi teos tayttamalla jokainen kentta, ja sitten painamalla 'OK'");
        arvot = new TextField[] { editNimi, editTekija, editISBN,
                editJulkaisuvuosi, editKategoria, editHyllypaikka, editMaara };
        palaute = "";
    }


    /**
     * Yhdistaa eri tekstikentissa olleet arvot yhdeksi parsettavaksi merkkijonoksi.
     */
    private void concat() {
        palaute = String.format("%d|%s|%s|%s|%s#%d|%s|%s#%s|%s", this.id,
                editISBN.getText(), editNimi.getText(), editTekija.getText(),
                editJulkaisuvuosi.getText(), this.kid, editKategoria.getText(),
                editKuvaus.getText(), editHyllypaikka.getText(),
                editMaara.getText());
    }


    private void asetaKentat(String s) {
        StringBuilder sb = new StringBuilder(s);
        id = Mjonot.erota(sb, '|', 0);
        editISBN.setText(Mjonot.erota(sb, '|'));
        editNimi.setText(Mjonot.erota(sb, '|'));
        editTekija.setText(Mjonot.erota(sb, '|'));
        editJulkaisuvuosi.setText(Mjonot.erota(sb, '|'));
        kid = Mjonot.erota(sb, '|', 0);
        editKategoria.setText(Mjonot.erota(sb, '|'));
        editKuvaus.setText(Mjonot.erota(sb, '|'));
        editHyllypaikka.setText(Mjonot.erota(sb, '|'));
        editMaara.setText(sb.toString());
        buttonOK.setText("Muokkaa");
    }


    /**
     * Luodaan kyselydialogi uudelle teokselle, ja palautetaan siihen kirjoitetut
     * arvot yhdistettyna merkkijonona, tai null.
     * @param modalityStage Mille ollaan modaalisia, null = sovellukselle
     * @param oletus mika viesti annetaan oletuksena
     * @return null jos painetaan Cancel, muuten syotetyt arvot parsettava merkkijonona.
     */
    public static String kysyArvot(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                TeosDialogController.class.getResource("TeosDialogView.fxml"),
                "Lisaa teos", modalityStage, oletus);
    }


    /**
     * Luodaan kyselydialogi uudelle teokselle, ja alustetaan kenttien arvoksi merkkijonona
     * annetut arvot, ja palauttaa joko muutet tiedot merkkijonona, tai null.
     * @param modalityStage Mille ollaan modaalisia.
     * @param oletus mika viesti annetaan oletuksena
     * @param arvot merkkijono josta parsetaan arvot
     * @return null jos Canccel, muuten muutetut arvot merkkijonona.
     */
    public static String naytaTiedot(Stage modalityStage, String oletus,
            String arvot) {
        return ModalController.<String, TeosDialogController> showModal(
                TeosDialogController.class.getResource("TeosDialogView.fxml"),
                "Muokataan teosta", modalityStage, oletus,
                ctrl -> ctrl.asetaKentat(arvot));
    }


    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        System.out.println(String.format("0|%s|%s|%s|%s#0|%s|%s#0|0|%s|%s",
                "123-123-123-123", "Neuromancer", "William Gibson", "1984",
                "Scifi", "Kuvaus Scifista", "JAG", "5"));

    }

}
