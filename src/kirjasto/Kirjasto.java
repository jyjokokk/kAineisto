package kirjasto;

/**
 * @author jyrki
 * @version Mar 25, 2020
 */
public class Kirjasto {
    
    private final Teokset teokset = new Teokset();
    private final Kategoriat kategoriat = new Kategoriat();
    private final Aineistot aineisto = new Aineistot();

    
    /**
     * Lisaa teokset teoksiin.
     * @param teos joka lisataan
     * @throws TietoException jos ilmenee ongelmia
     */
    public void lisaa(Teos teos) throws TietoException {
        teokset.lisaa(teos);
    }
    
    /**
     * Lisaa kategorian kategorioihin
     * @param kat kategoria joka lisataan
     * @throws TietoException jos ilmenee ongelmia
     */
    public void lisaa(Kategoria kat) throws TietoException {
        kategoriat.lisaa(kat);
    }
    
    /**
     * Lisaa teoksen aineistoon.
     * @param a aineisto joka lisataan
     * @throws TietoException jos ilmenee onglemia
     */
    public void lisaa(Aineisto a) throws TietoException {
        aineisto.lisaa(a);
        teokset.lisaa(a.getTeos());
        kategoriat.lisaa(a.getKategoria());
    }
    
    /**
     * Poistaa indeksissa olevan kirjan.
     * @param nro Indeksi kirjalle
     * @return montako poistettiin.
     * TODO: kesken
     */
    public int poista(int nro) {
        return nro;
    }
    
    /**
     * Tallentaa tietoalueet tiedostoihinsa.
     * @throws TietoException jos ilmenee ongelma tallentaessa.
     */
    public void tallenna() throws TietoException {
        aineisto.tallenna();
        teokset.tallenna();
        kategoriat.tallenna();
    }
    
    
    /**
     * Lukee tietoalueet tiedostoistaan.
     * @throws TietoException jos lukemisessa ilmenee ongelma.
     */
    public void lueTiedostosta() throws TietoException {
        aineisto.lueTiedostosta();
        teokset.lueTiedostosta();
        kategoriat.lueTiedostosta();
    }
    
    
    /**
     * Palauttaa teosten lukumaaran.
     * @return teosten lukumaara.
     */
    public int getTeosLkm() {
        return teokset.getLkm();
    }
    
    /**
     * Palauttaa kategorioiden lukumaaran.
     * @return kategorioiden lukumaara.
     */
    public int getKategoriaLkm() {
        return kategoriat.getLkm();
    }
    
    /**
     * Palauttaa aineiston lukumaaran.
     * @return aineiston lukumaara.
     */
    public int getAineistoLkm() {
        return aineisto.getLkm();
    }
    
    
    /**
     * @param args ei kaytossa.
     */
    public static void main(String[] args) {
//        Teos lotr = new Teos();
//        Kategoria fant = new Kategoria();
        Kirjasto kirjasto = new Kirjasto();
        Aineisto uusi = new Aineisto();
        Aineisto toka = new Aineisto();
        uusi.vastaaLotr();
        toka.vastaaLotr();
        try {
            kirjasto.lisaa(uusi);
            kirjasto.lisaa(toka);
            for (var k : kirjasto.aineisto) {
                System.out.println(k.tiedot());
            }
        } catch (TietoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
    }

}
