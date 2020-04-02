package kirjasto;

import java.util.*;
import java.io.*;

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
        alkiot.add(k);
    }
    
    
    /**
     * Lisaa uuden olion taulukkoon, ja alustaa sen arvot
     * syotetysta merkkijonosta seulomalla
     * @param syote Merkkijono, josta parsetaan.
     */
    public void lisaa(String syote) {
        alkiot.add(new Kategoria(syote));
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
     * @param tiedNimi Tiedoston nimi, josta luetaan.
     * @throws TietoException jos lukeminen epaonnistuu.
     */
    public void lueTiedostosta(String tiedNimi) throws TietoException {
        try (Scanner fi = new Scanner (new FileInputStream(new File(tiedNimi)))) {
            while (fi.hasNext()) {
                String s = fi.nextLine();
                if (s.charAt(0) == '#') continue;
                this.lisaa(s);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Ongelma tiedostoa avatessa!" + ex.getMessage());
        }
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
        
    }

}
