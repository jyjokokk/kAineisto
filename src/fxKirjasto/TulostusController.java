package fxKirjasto;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;

/**
 * Tulostusikkunan hallitseva kontrolleriluokka.
 * 
 * @author jyjokokk
 * @version Apr 15, 2020
 */
public class TulostusController implements ModalControllerInterface<String> {

    @FXML
    TextArea areaPrint;

    
    @FXML
    private void handleClose() {
        ModalController.closeStage(areaPrint);
    }

    
    /**
     * Kaynnistaa tulostuksen. Huom! Ei toimi Linuxilla, eventkutsu ei
     * loyda oikeaa kirjastoa.
     */
    @FXML
    private void handlePrint() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(null)) {
            WebEngine webEngine = new WebEngine();
            webEngine.loadContent("<pre>" + areaPrint.getText() + "</pre>");
            webEngine.print(job);
            job.endJob();
        }
    }
    

    /**
     * Palautetaan null dialogin sulkeutuessa, koska dialogi on jo suorittanut
     * tehtavansa, ja ohjelma ei tarvitse silta mitaan.
     */
    @Override
    public String getResult() {
        return null;
    }


    /**
     * Mitä tehdään dialogin näyttämisen jälkeen
     */
    @Override
    public void handleShown() {
        // Voidaan jattaa tyhjaksi
    }


    /**
     * Asettaa dialogin kaynnistaessa sille lahetetyn merkkijonon tulostusikkunan
     * sisalloksi, tassa maaritellyn headerin peraan.
     */
    @Override
    public void setDefault(String oletus) {
        String header = "Valitut teokset ja niiden tiedot\n"
                      + "----------------------------------";
        areaPrint.setText(header + oletus);
    }


    /**
     * Tayttaa tulostusalueen annetulla tekstilla esikatseltavaksi.
     * @param tuloste teksi, jota ollaan tulostamassa.
     * @return kontrolleri, joka voi valittaa lisaa tietoa.
     */
    public static TulostusController tulosta(String tuloste) {
        TulostusController tulostusCtrl = ModalController.showModeless(
                TulostusController.class.getResource("PrintView.fxml"),
                "Tulostus", tuloste);
        return tulostusCtrl;
    }

}
