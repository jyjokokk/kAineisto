package kirjasto;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;
import isbn.ISBNTarkistus;

/**
 * Teos, joka sisaltaa kirjan tarkeimmat ominaisuudet,
 * ja tarvittaessa muuttaa ja tarkistaa niita itse.
 * @author jyrki
 * @version Mar 4, 2020
 */
public class Teos {
    
    private int id = 0;
    private String isbn = "";
    private String nimi = "";
    private String tekija = "";
    private int julkaisuVuosi = 0;
    
    private static int seuraavaId = 1;
    
    
    /**
     * Vakiomuodostaja.
     */
    public Teos() {
        // Alustukset hoidettu esittelyssa.
    }
    
    
    /**
     * Alustaa olion merkkijonosta parsetuista attribuuteista.
     * @param syote Merkkijono, josta parsetaan.
     * @example
     * <pre name="test">
     *  Teos uusi = new Teos("1|123-123-123-123|Uusi Kirja|Matti Meikalainen|1995");
     *  uusi.toString() === "1|123-123-123-123|Uusi Kirja|Matti Meikalainen|1995";
     * </pre>
     */
    public Teos(String syote) {
        this.parse(syote);
        this.rekisteroi();
    }
    
    
    /**
     * Alustaa olion arvot merkkijonosta parsetuilla arvoilla.
     * @param syote merkkijono, josta arvot parsetaan
     * @example
     * <pre name="test">
     *  Teos uusi = new Teos();
     *  uusi.parse("1|123-123-123-123|Uusi Kirja|Matti Meikalainen|1995");
     *  uusi.toString() === "1|123-123-123-123|Uusi Kirja|Matti Meikalainen|1995";
     * </pre>
     */
    public void parse(String syote) {
        StringBuilder sb = new StringBuilder(syote);
        this.setId(Mjonot.erota(sb, '|', this.getId()));
        this.isbn = Mjonot.erota(sb, '|');
        this.nimi = Mjonot.erota(sb, '|');
        this.tekija = Mjonot.erota(sb, '|');
        this.julkaisuVuosi = Mjonot.erotaInt(sb, 0);
    }
    

    /**
     * Apumetodi, jolla saadaan luotua testattava olio,
     * annetuilla arvoilla.
     */
    public void vastaaLotr() {
        this.setIsbn("978-0544003415");
        this.setNimi("The Lord of the Rings");
        this.setTekija("J.R.R. Tolkien");
        this.setJulkaisuVuosi(1954);
        this.rekisteroi();
    }
    
    
    /**
     * Apumetodi, jolla alustetaan olio annetuilla arvoilla
     * ja randomoidulla ISBN-numerolla.
     */
    public void vastaaLotrRand() {
        String randomIsbn = ISBNTarkistus.arvoIsbn();
        this.setIsbn(randomIsbn);
        this.setNimi("The Lord of the Rings");
        this.setTekija("J.R.R. Tolkien");
        this.setJulkaisuVuosi(1954);
        this.rekisteroi();
    }
    
    
    /**
     * Palauttaa olion tiedot merkkijonona.
     * @returns Tiedot merkkijonona.
     * @example
     * <pre name="test">
     *     Teos t1 = new Teos();
     *     t1.toString() === "0||||0";
     *     t1.vastaaLotr();
     *     t1.toString() === "1|978-0544003415|The Lord of the Rings|J.R.R. Tolkien|1954";
     * </pre>
     */
    @Override
    public String toString() {
        return this.id + "|" +
               this.getIsbn() + "|" +
               this.getNimi() + "|" +
               this.getTekija() + "|" +
               this.getJulkaisuVuosi();
    }
    
    
    /**
     * Antaa teokselle seuraavan id-numeron.
     * @return Teoksen uusi id-numero.
     * @example
     * <pre name="test">
     *  Teos t1 = new Teos();
     *  t1.getId() === 0;
     *  t1.rekisteroi();
     *  t1.getId() === 2;
     *  Teos t2 = new Teos();
     *  t2.rekisteroi();
     *  t2.getId() === 3;
     *  int n1 = t1.getId();
     *  int n2 = t2.getId();
     *  n1 === n2 - 1;
     * </pre>
     */
    public int rekisteroi() {
        this.id = seuraavaId;
        seuraavaId++;
        return this.id;
    }
    
    
    /**
     * Palauttaa teoksen id-numeron.
     * @return teoksen id
     */
    public int getId() {
        return this.id;
    }
    
    
    /**
     * Asettaa id:n, ja samalla varmistaa etta
     * seuraavaId tulee pysymaan suurempana kuin
     * suurin esiintyva id.
     * @param id joka asetetaan.
     */
    private void setId(int id) {
        this.id = id;
        if (id >= seuraavaId) seuraavaId = id + 1;
    }
    
    
    /**
     * Tulostetaan teoksen tiedot.
     * @param out tietovirta johon tulostetaan. 
     */
    public void tulosta(PrintStream out) {
        out.println(this.toString());
    }
    
    
    /**
     * Tulostetaan teoksen tiedot.
     * @param os tietovirta johon tulostetaan.
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    

    /**
     * Palauttaa teoksen ISBN:n
     * @return isbn
     */
    public String getIsbn() {
        return isbn;
    }


    /**
     * Asettaa ISBN-numeron teokselle
     * @param isbn joka asetetaan
     * TODO: Kirjoita tarkastaja
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    /**
     * Palauttaa teoksen nimen
     * @return nimi
     */
    public String getNimi() {
        return nimi;
    }


    /**
     * Asettaa nimen teokselle
     * @param nimi teokselle
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }


    /**
     * Palauttaa teoksen tekijan
     * @return tekija
     */
    public String getTekija() {
        return tekija;
    }


    /**
     * Asettaa tekijan teokselle.
     * @param tekija joka asetetaan.
     */
    public void setTekija(String tekija) {
        this.tekija = tekija;
    }


    /**
     * Palauttaa teoksen julkaisuvuoden.
     * @return julkaisuVuosi
     */
    public int getJulkaisuVuosi() {
        return julkaisuVuosi;
    }


    /**
     * Asettaa julkaisuvuoden
     * @param julkaisuVuosi Mihin vuoteen asetetaan.
     */
    public void setJulkaisuVuosi(int julkaisuVuosi) {
        this.julkaisuVuosi = julkaisuVuosi;
    }
    

    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Teos t1 = new Teos();
        System.out.println(t1);
        t1.vastaaLotrRand();
        System.out.println(t1);
        Teos t2 = new Teos();
        t2.vastaaLotrRand();
        t2.tulosta(System.out);
        Teos t3 = new Teos();
        t3.vastaaLotrRand();
        t3.tulosta(System.out);
    }

}
