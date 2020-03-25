package kirjasto;

/**
 * Teosten kokoelma, jokaa osaa mm lisata ja poistaa teoksen.
 * TODO: Selvita onnistuisiko yksittaiset sailomisluokat joka tyypille korvata <TYPE> tyylisella ratkaisulla.
 * TODO: Toimisi varmaan todella paljon paremmin listalla toteutettuna.
 * @author jyrki
 * @version Mar 25, 2020
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
     * @throws TietoException jos taulukossa ei tilaa.
     * TODO: Tee dynaamisesti kasvavaksi.
     */
    public void lisaa(Teos alkio) throws TietoException {
        if (lkm >= alkiot.length) throw new TietoException("Liikaa alkioita.");
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
     * Palalauttaa viitteen i:teen teosluettelon olioon.
     * @param i monennenko alkion viite halutaan
     * @return viite alkioon, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Teos getTeos(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        
        Teokset luettelo = new Teokset();

        Teos kirja1 = new Teos();
        Teos kirja2 = new Teos();
        kirja1.vastaaLotr();
        kirja2.vastaaLotrRand();
        try {
            luettelo.lisaa(kirja1);
            luettelo.lisaa(kirja2);
            for (int i = 0; i < luettelo.getLkm(); i++) {
                Teos teos = luettelo.getTeos(i);
                System.out.println("Teos nro: " + i);
                teos.tulosta(System.out);
            }
            
        } catch (TietoException e) {
            System.out.println(e.getMessage());;
        }
        

    }

}
