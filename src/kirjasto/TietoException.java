package kirjasto;

/**
 * Poikkeusluokka tietorakenteen muistitilaan littyyville
 * ongelmille. (EI LIITY ohjelman tilaan (State)).
 * @author jyrki
 * @version Mar 24, 2020
 */
public class TietoException extends Exception {

    // Versionumero, tarvitaan jos ohjelmistosta liikkuu eri versioita.
    private static final long serialVersionUID = 1L;
    
    /**
     * Poikkeuksen muodostaja, jolle annetaan poikkeustilanteessa
     * kaytettava viesti
     * @param viesti Poikkeustilan viesti.
     */
    public TietoException(String viesti) {
        super(viesti);
    }
    
}
