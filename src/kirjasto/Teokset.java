package kirjasto;

import java.io.*;
import java.util.Scanner;

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
     * Lisaa uuden alkion taulukkoon, alustaen sen arvot annetusta merkkijonosta.
     * @param syote Merkkijono, josta arvot alustetaan
     * @throws TietoException jos taulukossa ei tilaa.
     * TODO: Tee dynaamisesti kasvavaksi.
     */
    public void lisaa(String syote) throws TietoException {
        if (lkm >= alkiot.length) throw new TietoException("Liikaa alkioita.");
        alkiot[lkm] = new Teos(syote);
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
     * @param tiedNimi Tiedoston nimi, josta luetaan.
     * @throws TietoException jos tallentaminen epaonnistuu.
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
    public Teos anna(int i) throws IndexOutOfBoundsException {
        if (i < 0)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        for (var t : alkiot) {
            if (t.getId() == i) return t;
        }
        return null;
        
    }
    
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        
        Teokset luettelo = new Teokset();
//        Teos kirja1 = new Teos();
//        Teos kirja2 = new Teos();
//        kirja1.vastaaLotr();
//        kirja2.vastaaLotrRand();
        try {
//            luettelo.lisaa(kirja1);
//            luettelo.lisaa(kirja2);
            luettelo.lueTiedostosta("testkirjat.txt");
            for (int i = 0; i < luettelo.getLkm(); i++) {
                Teos teos = luettelo.alkiot[i];
                System.out.println("Teos nro: " + i);
                teos.tulosta(System.out);
            }
        } catch (TietoException e) {
//            System.out.println(e.getMessage());;
            e.printStackTrace();
        }
        

    }

}
