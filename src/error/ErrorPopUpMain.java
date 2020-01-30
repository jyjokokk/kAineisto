package error;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author jyrki
 * @version 30.1.2020
 *
 */
public class ErrorPopUpMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("ErrorPopUpGUIView.fxml"));
            final Pane root = ldr.load();
            //final ErrorPopUpGUIController errorpopupCtrl = (ErrorPopUpGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("errorpopup.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("ErrorPopUp");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}