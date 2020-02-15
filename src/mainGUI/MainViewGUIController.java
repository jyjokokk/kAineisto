package mainGUI;

import javafx.event.ActionEvent;
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
public class MainViewGUIController {

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
    @FXML private MenuItem menuHelp;
    
    
    @FXML
    private void hae() {
        eiToimi();
    }

    @FXML
    private void help() {
        eiToimi();
    }

    @FXML
    private void lisaa() {
        eiToimi();
    }

    @FXML
    private void muokkaa() {
        eiToimi();
    }

    @FXML
    private void poista() {
        eiToimi();
    }

    @FXML
    private void poistu() {
        eiToimi();
    }
    
    
    private void eiToimi() {
        //
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Ei toimi");
        alert.setHeaderText(null);
        alert.setContentText("Ei toimi!");
        alert.showAndWait();

    }
    

}
