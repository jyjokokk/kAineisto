package kirjasto;

/**
 * Hyllyten kokoelma, jokaa osaa mm lisata ja poistaa teoksen.
 * TODO: Selvita onnistuisiko yksittaiset sailomisluokat joka tyypille korvata <TYPE> tyylisella ratkaisulla.
 * TODO: Toimisi varmaan todella paljon paremmin listalla toteutettuna.
 * @author jyrki
 * @version Mar 25, 2020
 */
public class Hyllyt {

    private static final int MAX_PAIKKOJA = 10;
    private int lkm = 0;
    private String tiedostonNimi = "";
    private Hylly alkiot[] = new Hylly[MAX_PAIKKOJA];
    
    /**
     * Vakiomuodostaja.
     */
    public Hyllyt() {
        // Alustettu esittelyssa.
    }
    
    
    /**
     * Lisaa uuden alkion tietorakenteeseen.
     * @param alkio joka lisataan.
     * @throws TietoException jos taulukossa ei tilaa.
     * TODO: Tee dynaamisesti kasvavaksi.
     */
    public void lisaa(Hylly alkio) throws TietoException {
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
     * @return Hyllyten lukumaara.
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
    public Hylly anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || alkiot.length <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        
        Hyllyt luettelo = new Hyllyt();
        Hylly kirja1 = new Hylly();
        Hylly kirja2 = new Hylly();
        try {
            luettelo.lisaa(kirja1);
            luettelo.lisaa(kirja2);
            for (int i = 0; i < luettelo.getLkm(); i++) {
                Hylly teos = luettelo.anna(i);
                System.out.println("Hylly nro: " + i);
                teos.tulosta(System.out);
            }
        } catch (TietoException e) {
//            System.out.println(e.getMessage());;
            e.printStackTrace();
        }
        

    }

}

