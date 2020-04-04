package kirjasto;

import java.io.*;
import java.util.Scanner;
//import fi.jyu.mit.ohj2.*;

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
     * Lisaa taulukkoon uuden alkion, ja alustaa sen arvot syotetysta
     * merkkijonosta.
     * @param syote merkkijono, josta arvot alustetaan
     * @throws TietoException jos taulukossa ei ole tilaa.
     */
    public void lisaa(String syote) throws TietoException {
        if (lkm >= alkiot.length) throw new TietoException("Liikaa alkioita");
        alkiot[lkm] = new Hylly(syote);
        lkm++;
    }
    
    
    /**
     * Vertaa onko taman olion tiedot samat kuin toisen.
     * @param comp verrattava olio
     * @return True jos sama, false jos ei.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Hyllyt tama = new Hyllyt();
     *  Hyllyt toinen = new Hyllyt();
     *  tama.lisaa(new Hylly("1|1|JAG|2")); toinen.lisaa(new Hylly("1|1|JAG|2"));
     *  tama.equals(toinen) === true;
     * </pre>
     */
    public boolean equals(Hyllyt comp) {
        String tama = "";
        for (int i = 0; i < lkm; i++) {
            tama += alkiot[i].toString();
        }
        String toinen = "";
        for (int i = 0; i < comp.getLkm(); i++) {
            toinen += comp.anna(i).toString();
        }
        if (tama.equals(toinen)) return true;
        return false;
    }
    
    
    /**
     * Tallentaa teosluettelon tiedostoon.
     * @param tiedNimi Tiedoston nimi, johon tallenetaan.
     * @throws TietoException jos tallentaminen epaonnistuu.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Hyllyt uusi = new Hyllyt();
     *  Hyllyt toka = new Hyllyt();
     *  uusi.lueTiedostosta("testFiles/testHyllyt.dat");
     *  uusi.tallenna("testFiles/testHyllytUlos.dat");
     *  toka.lueTiedostosta("testFiles/testHyllytUlos.dat");
     *  uusi.equals(toka) === true;
     *  toka.lueTiedostosta("testFiles/testHyllytEri.dat");
     *  System.out.println(uusi.equals(toka));
     *  uusi.equals(toka) === false;
     * </pre>
     */
    public void tallenna(String tiedNimi) throws TietoException {
//        String kohde = "aineisto/" + tiedNimi;
        try (PrintStream fo = new PrintStream(new FileOutputStream(tiedNimi))) {
            for (int i = 0; i < lkm; i++) {
                fo.println(alkiot[i].toString());
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Ongelma tiedostoon kirjoittaessa! " + ex.getMessage());
        }
    }
    
    
    /**
     * Lukee teosluettelon tiedostosta.
     * @param tiedNimi tiedoston nimi, joka luetaan
     * @throws TietoException jos lisaamissa ilmenee ongelmia
     * @example
     * <pre name="test">
     *  #THROWS TietoException
     *  Hyllyt uusi = new Hyllyt();
     *  Hyllyt toka = new Hyllyt();
     *  uusi.lueTiedostosta("testFiles/testHyllyt.dat");
     *  toka.lueTiedostosta("testFiles/testHyllyt.dat");
     *  uusi.equals(toka) === true;
     *  toka.lueTiedostosta("testFiles/testHyllytEri.dat");
     *  uusi.equals(toka) === false;
     *  uusi.getLkm() === 4;
     *  uusi.anna(0).toString() === "1|1|JAG|2";
     * </pre>
     */
    public void lueTiedostosta(String tiedNimi) throws TietoException {
        try (Scanner fi = new Scanner (new FileInputStream(new File(tiedNimi)))) {
            while (fi.hasNext()) {
                String s = fi.nextLine();
                if (s.length() == 0) continue;
                if (s.charAt(0) == '#') continue;
                this.lisaa(s);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Ongelma tiedostoa avatessa!" + ex.getMessage());
        }
        
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
//        Hylly kirja1 = new Hylly();
//        Hylly kirja2 = new Hylly();
        try {
//            luettelo.lisaa(kirja1);
//            luettelo.lisaa(kirja2);
//            for (int i = 0; i < luettelo.getLkm(); i++) {
//                Hylly teos = luettelo.anna(i);
//                System.out.println("Hylly nro: " + i);
//                teos.tulosta(System.out);
//            }
            System.out.println("===================");
            System.out.println("Luetaan tiedostosta");
            System.out.println("===================");
            luettelo.lueTiedostosta("hyllyt.txt");
//            System.out.println(luettelo.anna(0));
            for (int i = 0; i < luettelo.getLkm(); i++) {
                Hylly teos = luettelo.anna(i);
                System.out.println("Hylly nro: " + i);
                teos.tulosta(System.out);
            }
            luettelo.tallenna("hyllytUlosTest.txt");
        } catch (TietoException e) {
//            System.out.println(e.getMessage());;
            e.printStackTrace();
        }
        
        

    }

}

