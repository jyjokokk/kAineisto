package kirjasto;

/**
 * @author jyrki
 * @version Mar 25, 2020
 */
public class Kirjasto {
    
    private final Aineistot aineisto = new Aineistot();
    
    /**
     * Lisaa uuden aineiston kokoelmaan.
     * @param a aineisto joka lisataan
     * @throws TietoException jos ilmenee ongelmia
     * @example
     * <pre name="test">
     *  #THROWS TietoException
     *  Kirjasto kirjasto = new Kirjasto();
     *  Aineisto k1 = new Aineisto(), k2 = new Aineisto();
     *  k1.vastaaLotr(); k2.vastaaLotr();
     *  kirjasto.getLkm() === 0;
     *  kirjasto.lisaa(k1); kirjasto.lisaa(k2);
     *  kirjasto.getLkm() === 2;
     *  kirjasto.annaKirja(0) === k1;
     *  kirjasto.annaKirja(1) === k2;
     *  kirjasto.annaKirja(3); #THROWS IndexOutOfBoundsException
     *  kirjasto.lisaaLotr();
     *  kirjasto.getLkm() === 3;
     * </pre>
     */
    public void lisaa(Aineisto a) throws TietoException {
        aineisto.lisaa(a);
    }
    
    
    /**
     * Lisaa aineiston kokoelmaan, parseamalla sen merkkijonosta.
     * @param s merkkijono joka annetaan.
     */
    public void lisaa(@SuppressWarnings("unused") String s) {
        // TODO: Parserit alaspain
    }
    
    
    /**
     * Lisaa uuden aineiston kokoelmaan.
     * @throws TietoException jos ilmenee ongelmia
     */
    public void lisaaLotr() throws TietoException {
        Aineisto lotr = new Aineisto();
        lotr.vastaaLotr();
        lisaa(lotr);
    }
    
    
    /**
     * Tallentaa tietorakenteet tiedostoihinsa.
     * @throws TietoException jos ongelmia 
     */
    public void tallenna() throws TietoException {
        aineisto.tallenna();
    }
    
    
    /**
     * Lukee tietorakenteet tiedostoistaan
     * @throws TietoException jos ongelma
     */
    public void lueTiedostosta() throws TietoException {
        aineisto.lueTiedostosta();
    }
    
    
    /**
     * Poistaa aineistosta ne teokset joiden id vastaa nro.
     * @param nro Numero, jota vastaavaa id:ta etsitaan
     * @return Boolean onnistuiko
     * TODO: Kesken
     */
    public Boolean poista(@SuppressWarnings("unused") int nro) {
        return false;
    }
    
    
    /**
     * Palauttaa aineiston maaran kokoelmassa.
     * @return kokoelman koko
     */
    public int getLkm() {
        return aineisto.getLkm();
    }
    
    
    /**
     * Palauttaa i:n kirjan
     * @param i monesto kirja palautetaan
     * @return viite i:teen kirjaan
     * @throws IndexOutOfBoundsException jos indeksi on laiton
     */
    public Aineisto annaKirja(int i) throws IndexOutOfBoundsException {
        return aineisto.anna(i);
    }
    
    
    /**
     * @param args ei kaytossa.
     */
    public static void main(String[] args) {
        Kirjasto kirjasto = new Kirjasto();
        System.out.println("=========  getLkm  ===========");
        System.out.println(kirjasto.getLkm());
        try {
            kirjasto.lisaaLotr();
            kirjasto.lisaaLotr();
            kirjasto.lisaaLotr();
            kirjasto.lisaaLotr();
            System.out.println(kirjasto.getLkm());
            System.out.println("========= kirjasto ===========");
            for (var kirja : kirjasto.aineisto) {
//                System.out.println(kirja);
                kirja.tulosta(System.out);
            }
            System.out.println("========= annaKirja ==========");
            System.out.println(kirjasto.annaKirja(2).tiedot());
        } catch (TietoException e) {
//            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
