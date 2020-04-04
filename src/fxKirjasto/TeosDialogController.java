package fxKirjasto;

import java.net.URL;
import java.util.EventListener;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.*;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kirjasto.Kirjasto;

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

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }


    @FXML
    private void handleOK() {
        for (var t : arvot) {
            if (t.getText().trim().equals("") && t != editKuvaus
                    && t != editMaara) {
                t.setStyle("-fx-border-color:RED");
                setVirhe("Tayta merkityt arvot!");
                return;
            }
        }
        if (editMaara.getText().length() == 0)
            editMaara.setText("0");
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
        palaute = String.format("0|%s|%s|%s|%s#0|%s|%s#0|0|%s|%s",
                editISBN.getText(), editNimi.getText(), editTekija.getText(),
                editJulkaisuvuosi.getText(), editKategoria.getText(),
                editKuvaus.getText(), editHyllypaikka.getText(),
                editMaara.getText());
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


    public static void main(String[] args) {
        System.out.println(String.format("0|%s|%s|%s|%s#0|%s|%s#0|0|%s|%s",
                "123-123-123-123", "Neuromancer", "William Gibson", "1984",
                "Scifi", "Kuvaus Scifista", "JAG", "5"));

    }

}
