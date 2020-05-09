package fxKerho;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kerho.Jasen;

/**
 * Kysytaan jasenen tiedot luomalla sille uusi dialogi
 * 
 * @author vesal
 * @version 11.1.2016
 *
 */
public class JasenDialogController implements ModalControllerInterface<Jasen>,Initializable  {

    @FXML private TextField editNimi;
    @FXML private TextField editHetu;
    @FXML private TextField editKatuosoite;
    @FXML private TextField editPostinumero;    
    @FXML private Label labelVirhe;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();  
    }
    
    @FXML private void handleOK() {
        if ( jasenKohdalla != null && jasenKohdalla.getNimi().trim().equals("") ) {
            naytaVirhe("Nimi ei saa olla tyhja");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }

    
    @FXML private void handleCancel() {
        jasenKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }

// ========================================================    
    private Jasen jasenKohdalla;
    private TextField edits[];
   

    /**
     * Tyhjentaan tekstikentat 
     * @param edits tauluko jossa tyhjennettavia tektsikenttia
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            edit.setText("");
    }


    /**
     * Tekee tarvittavat muut alustukset. Mm laittaa edit-kentista tulevan
     * tapahtuman menemaan kasitteleMuutosJaseneen-metodiin ja vie sille
     * kentannumeron parametrina.
     */
    protected void alusta() {
        edits = new TextField[]{editNimi, editHetu, editKatuosoite, editPostinumero};
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased( e -> kasitteleMuutosJaseneen(k, (TextField)(e.getSource())));
        }
    }
    
    
    @Override
    public void setDefault(Jasen oletus) {
        jasenKohdalla = oletus;
        naytaJasen(edits, jasenKohdalla);
    }

    
    @Override
    public Jasen getResult() {
        return jasenKohdalla;
    }
    
    
    /**
     * Mita tehdaan kun dialogi on naytetty
     */
    @Override
    public void handleShown() {
        editNimi.requestFocus();
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

    
    /**
     * Kasitellaan jaseneen tullut muutos
     * @param edit muuttunut kentta
     */
    private void kasitteleMuutosJaseneen(int k, TextField edit) {
        if (jasenKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
           case 1 : virhe = jasenKohdalla.setNimi(s); break;
           case 2 : virhe = jasenKohdalla.setHetu(s); break;
           case 3 : virhe = jasenKohdalla.setKatuosoite(s); break;
           case 4 : virhe = jasenKohdalla.setPostinumero(s); break;
           default:
        }
        if (virhe == null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit,virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }
    
    
    /**
     * Naytetaan jasenen tiedot TextField komponentteihin
     * @param edits taulukko jossa tekstikenttia
     * @param jasen naytettava jasen
     */
    public static void naytaJasen(TextField[] edits, Jasen jasen) {
        if (jasen == null) return;
        edits[0].setText(jasen.getNimi());
        edits[1].setText(jasen.getHetu());
        edits[2].setText(jasen.getKatuosoite());
        edits[3].setText(jasen.getPostinumero());
    }
    
    
    /**
     * Luodaan jasenen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * TODO: korjattava toimimaan
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mita dataan naytetaan oletuksena
     * @return null jos painetaan Cancel, muuten taytetty tietue
     */
    public static Jasen kysyJasen(Stage modalityStage, Jasen oletus) {
        return ModalController.<Jasen, JasenDialogController>showModal(
                    JasenDialogController.class.getResource("JasenDialogView.fxml"),
                    "Kerho",
                    modalityStage, oletus, null 
                );
    }

}
