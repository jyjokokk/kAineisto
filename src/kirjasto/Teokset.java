package kirjasto;

/**
 * Teosten kokoelma, jokaa osaa mm lisata ja poistaa teoksen.
 * TODO: Selvita onnistuisiko yksittaiset sailomisluokat joka tyypille korvata <TYPE> tyylisella ratkaisulla.
 * @author jyrki
 * @version Mar 4, 2020
 */
public class Teokset {

    private static final int MAX_TEOKSIA = 5;
    private int lkm = 0;
    private String tiedostonNimi = "";
    private Teos alkiot[] = new Teos[MAX_TEOKSIA];
    
    /**
     * Vakiomuodostaja.
     */
    public Teokset() {
        // Alustettu esittelyssa.
    }
    
    
    /**
     * Lisaa uuden alkion tietorakenteeseen.
     * @param alkio joka lisataan.
     */
    public void lisaa(Teos alkio) {
        alkiot[lkm] = alkio;
        lkm++;
    }
    
    
    /**
     * Tallentaa teosluettelon tiedostoon.
     * @throws TietoException jos tallentaminen epaonnistuu.
     */
    public void tallenna() throws TietoException {
        throw new TietoException("Tallenusta ei ole viela ohjelmoitu." + tiedostonNimi);
    }
    
    
    /**
     * Lukee teosluettelon tiedostosta.
     * @throws TietoException jos tallentaminen epaonnistuu.
     */
    public void lueTiedostosta() throws TietoException {
        throw new TietoException("Lukemista ei ole viela ohjelmoitu.");
    }
    
    
    /**
     * Palauttaa teosten lukumaaran.
     * @return Teosten lukumaara.
     */
    public int getLkm() {
        return this.lkm;
    }
    
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
    // TODO Auto-generated method stub
    }

}
