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
     */
    public void lisaa(Aineisto k) {
        alkiot.add(k);
    }
    
    public Aineisto getAineisto(int i) throws IndexOutOfBoundsException {
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
        throw new TietoException("Tallennusta ei ole viela ohjelmoitu.");
    }
    
    
    /**
     * Luetaan kokoelma tiedostosta.
     * @throws TietoException jos lukeminen epaonnistuu.
     */
    public void lueTiedostosta() throws TietoException {
        throw new TietoException("Lukemista ei ole viela ohjelmoitu.");
    }
    
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
    // TODO Auto-generated method stub
        
    }

}
