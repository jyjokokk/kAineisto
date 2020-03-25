package kirjasto;

import java.io.*;

/**
 * Luokka, jolla voidaan sitoa Teos, Kategoria, lukumaara ja hyllypaikka.
 * @author jyrki
 * @version Mar 25, 2020
 */
public class Aineisto {

    private Teos teos; 
    private Kategoria kategoria;
    private String hyllypaikka = "";
    private int lkm;
    
    
    /**
     * Vakiomuodostaja
     */
    public Aineisto() {
        this.teos = new Teos();
        this.kategoria = new Kategoria();
        this.hyllypaikka = "";
        this.lkm = 0;
    }
    
    /**
     * Muodostaja
     * @param teos teos
     * @param kategoria teoksen kategoria
     * @param hyllypaikka teoksen hyllypaikka
     * @param lkm teosten lukumaara hyllyssa
     */
    public Aineisto(Teos teos, Kategoria kategoria, String hyllypaikka, int lkm) {
        this.teos = teos;
        this.setKategoria(kategoria);
        this.hyllypaikka = hyllypaikka;
        this.lkm = lkm;
    }
    
    /**
     * Apumetodi, joka alustaa luokan testiarvoilla.
     */
    public void vastaaLotr() {
        this.teos = new Teos();
        this.teos.vastaaLotrRand();
        this.kategoria = new Kategoria();
        this.kategoria.vastaaFantasiaRek();
        this.hyllypaikka = "JAA";
        this.lkm = 1;
    }
    
    /**
     * Lisaa teosten lukumaaraa, ja palauttaa uuden lukumaaran.
     * @return teosten uusi lukumaara.
     */
    public int lisaa() {
        this.lkm++;
        return this.lkm;
    }
    
    
    /**
     * Vahentaa teosten lukumaaraa, ja palauttaa uuden lukumaaran.
     * @return teosten uusi lukumaara.
     */
    public int vahenna() {
        this.lkm--;
        return this.lkm;
    }
    
    
    /**
     * Palauttaa teoksen tiedot merkkijonona.
     */
    @Override
    public String toString() {
        return teos.toString() + "|" + kategoria + "|" + hyllypaikka + "|" + lkm;
    }
    
    
    /**
     * Tulostetaan aineiston tiedot
     * @param out tietovirta johon tulostetaan. 
     */
    public void tulosta(PrintStream out) {
        out.println(this.toString());
    }
    
    
    /**
     * Tulostetaan aineiston tiedot
     * @param os tietovirta johon tulostetaan.
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Antaa teoksen oleelliset tiedot
     * @return Tiedot merkkijonona.
     */
    public String tiedot() {
        return teos.toString() + "|" + kategoria.getNimi() + "|" + hyllypaikka + "|" + lkm;
    }
    
    /**
     * Tulostetaan aineiston tiedot
     * @param out tietovirta johon tulostetaan. 
     */
    public void tulostaTiedot(PrintStream out) {
        out.println(this.tiedot());
    }
    
    
    /**
     * Tulostetaan aineiston tiedot
     * @param os tietovirta johon tulostetaan.
     */
    public void tulostaTiedot(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Palauttaa viitteen aineiston teokseen.
     * @return viite teokseen.
     */
    public Teos getTeos() {
        return this.teos;
    }
    
    
    /**
     * Palauttaa teoksen id:n
     * @return teoksen id
     */
    public int getId() {
        return teos.getId();
    }
    
 
    /**
     * Asettaa tai muuttaa teoksen hyllypaikkaa.
     * @param hyllypaikka Hyllypaikka teokselle.
     */
    public void setHyllypaikka(String hyllypaikka) {
        this.hyllypaikka = hyllypaikka;
    }
    
    
    /**
     * Palauttaa teoksen hyllypaikan.
     * @return hyllypaikka
     */
    public String getHyllypaikka() {
        return this.hyllypaikka;
    }


    /**
     * Palauttaa teoksen kategorian.
     * @return Teoksen kategoria.
     */
    public Kategoria getKategoria() {
        return kategoria;
    }


    /**
     * @param kategoria the kategoria to set
     */
    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
    }

    
    /**
     * @param args ei kaytossa.
     */
    public static void main(String[] args) {
        Teos lotr = new Teos();
        Kategoria fantasia = new Kategoria();
        lotr.vastaaLotr();
        fantasia.vastaaFantasiaRek();
        Aineisto kirja = new Aineisto(lotr, fantasia, "JAG", 1);
        kirja.lisaa();
        System.out.println(kirja.tiedot());
    
    }


}
