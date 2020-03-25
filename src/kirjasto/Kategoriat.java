package kirjasto;

import java.util.*;

/**
 * Luokka kategorioille.
 * @author jyrki
 * @version Mar 25, 2020
 * TODO: Yleisen toiminallisuuden kannalta, TreeMap voisi olla parempi tietorakenne ArrayList.
 */
public class Kategoriat implements Iterable<Kategoria> {
    
    private String tiedostonNimi = "";
    private static final ArrayList<Kategoria> alkiot = new ArrayList<Kategoria>(0);

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
//        String uusiNimi = k.getNimi().toUpperCase();
//        for (var a : alkiot) {
//            String vanhaNimi = a.getNimi().toUpperCase();
//            if (uusiNimi.equals(vanhaNimi)) System.out.println(uusiNimi + "==" + vanhaNimi);
//        }
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
     * Palauttaa viitteen alkioon indeksissa i.
     * @param i alkion indeksi
     * @throws IndexOutOfBoundsException jos indeksi ei alkioissa
     * @return viite alkioon
     */
    public Kategoria anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= alkiot.size()) throw new IndexOutOfBoundsException("Laiton indeksi.");
        return alkiot.get(i);
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
        Kategoriat kategoriat = new Kategoriat();
        Kategoria fantasia = new Kategoria();
        Kategoria scifi = new Kategoria("Scifi", "Scifi on...");
        fantasia.vastaaFantasiaRek();
        scifi.rekisteroi();
        kategoriat.lisaa(fantasia);
        kategoriat.lisaa(scifi);
        for (Kategoria k: kategoriat) {
            System.out.println(k);
        }
        Kategoria scifi2 = new Kategoria("Scifi", "Scifi on...");
        kategoriat.lisaa(scifi2);
        for (var a : alkiot) {
            if (scifi2.getNimi().toUpperCase().equals(a.getNimi().toUpperCase()))
                System.out.println("Sama loytyi.");
        }
        
    }

}
