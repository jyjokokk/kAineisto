package kirjasto;

import java.util.*;

/**
 * Luokka kategorioille.
 * @author jyrki
 * @version Mar 25, 2020
 * TODO: Yleisen toiminallisuuden kannalta, TreeMap voisi olla parempi tiedostomuoto.
 */
public class Kategoriat implements Iterable<Kategoria> {
    
    private String tiedostonNimi = "";
    private final ArrayList<Kategoria> alkiot = new ArrayList<Kategoria>(0);

    /**
     * Perusmuodostaja
     */
    public Kategoriat() {
        // Alustuu esittelyssa.
    }
    
    
    /**
     * Iteraattori luokalle.
     */
    @Override
    public Iterator<Kategoria> iterator() {
        return alkiot.iterator();
    }
    
    
    /**
     * Lisaa uuden alkion kokoelmaan.
     * @param k lisattava alkio
     * TODO: korjaa yksilollisyystarkistaja.
     */
    public void lisaa(Kategoria k) {
//        if (this.getLkm() > 0) {
//            for (int i = 0; i < this.getLkm(); i++) {
//                if (k.getNimi().equals(alkiot.get(i).getNimi())) alkiot.add(k);
//            }
//        } else alkiot.add(k);
        alkiot.add(k);
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
        Kategoriat kategoriat = new Kategoriat();
        Kategoria fantasia = new Kategoria();
        Kategoria scifi = new Kategoria("Scifi", "Scifi on...");
        fantasia.vastaaFantasiaRek();
        scifi.rekisteroi();
        kategoriat.lisaa(fantasia);
        kategoriat.lisaa(scifi);
//        System.out.println(kategoriat.getLkm());
        for (Kategoria k: kategoriat) {
            System.out.println(k);
        }
        
    }

}
