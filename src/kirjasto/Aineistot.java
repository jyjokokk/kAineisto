package kirjasto;

import java.util.*;

/**
 * Luokka aineistoille.
 * @author jyrki
 * @version Mar 25, 2020
 */
public class Aineistot implements Iterable<Aineisto> {
    
    private String tiedostonNimi = "";
    private final ArrayList<Aineisto> alkiot = new ArrayList<Aineisto>();
    private final Kategoriat kategoriat = new Kategoriat();
    private final Teokset teokset = new Teokset();

    /**
     * Perusmuodostaja
     */
    public Aineistot() {
        // Alustuu esittelyssa.
    }
    
    
    /**
     * Iteraattori luokalle.
     */
    @Override
    public Iterator<Aineisto> iterator() {
        return alkiot.iterator();
    }
    
    
    /**
     * Lisaa uuden alkion kokoelmaan.
     * @param k lisattava alkio
     * @throws TietoException jos ilmenee ongelmia
     */
    public void lisaa(Aineisto k) throws TietoException  {
            teokset.lisaa(k.getTeos());
            // TODO: Kategoriaan lisattava duplikaattitarkastus
            kategoriat.lisaa(k.getKategoria());
            alkiot.add(k);
    }
    
    
    /**
     * Apumetodi jolla listaan aineistoon valmiiksi taytetty tiedosto.
     * @throws TietoException jos ilmenee ongelmia
     */
    public void lisaaLotr() throws TietoException {
        Aineisto uusi = new Aineisto();
        uusi.vastaaLotr();
        this.lisaa(uusi);
    }
    
    
    /**
     * Palauttaa viitteen alkioon indeksissa i
     * @param i alkion indeksi
     * @return viite alkioon indeksissa i
     * @throws IndexOutOfBoundsException jos indeksi ei taulukossa
     */
    public Aineisto anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= alkiot.size()) throw new IndexOutOfBoundsException("Laiton indeksi.");
        return alkiot.get(i);
    }
    
    
    /**
     * Palauttaa alkoiden lukumaaran kokoelmassa.
     * @return alkoiden lukumaara.
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * Tallentaa kokoelman tiedostoon.
     * @throws TietoException jos tallennus epaonnistuu.
     */
    public void tallenna() throws TietoException {
//        teokset.tallenna();
//        kategoriat.tallenna();
        throw new TietoException("Tallennusta ei ole viela ohjelmoitu.");
    }
    
    
    /**
     * Luetaan kokoelma tiedostosta.
     * @throws TietoException jos lukeminen epaonnistuu.
     */
    public void lueTiedostosta() throws TietoException {
//        teokset.lueTiedostosta();
//        kategoriat.lueTiedostosta();
        throw new TietoException("Lukemista ei ole viela ohjelmoitu.");
    }
    
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Aineistot kokoelma = new Aineistot();
        try {
            kokoelma.lisaaLotr();
            kokoelma.lisaaLotr();
            kokoelma.lisaaLotr();
            kokoelma.lisaaLotr();
            for (var k : kokoelma) {
                System.out.println(k.tiedot());
            }
        } catch (TietoException e) {
//            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        
    }

}
