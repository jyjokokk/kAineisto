package kirjastoGUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author jyrki
 * @version Feb 15, 2020
 */
public class KirjastoGUIController {

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
    
    
    @FXML
    private void handleHae() {
        hae();
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

    /**
     * Hakee tietokannasta kaikki teokset jotka vastaavat
     * hakutermeja.
     */
    private void hae() {
        eiToimi();
        
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
        eiToimi();
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
