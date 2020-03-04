package kirjasto;

import java.io.*;

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
     * Apumetodi, jolla saadaan luotua testattava olio,
     * annetuilla arvoilla.
     * TODO: Lisaa randomoitu ISBN.
     */
    public void vastaaLotr() {
        this.isbn = "978-0544003415";
        this.nimi = "The Lord of the Rings";
        this.tekija = "J.R.R. Tolkien";
        this.julkaisuVuosi = 1954;
    }
    
    
    /**
     * Palauttaa olion tiedot merkkijonona.
     * @returns Tiedot merkkijonona.
     * @example
     * <pre name="test">
     *     Teos t1 = new Teos();
     *     t1.toString() === "0||||0";
     *     t1.vastaaLotr();
     *     t1.toString() === "0|978-0544003415|The Lord of the Rings|J.R.R. Tolkien|1954";
     * </pre>
     */
    @Override
    public String toString() {
        return this.id + "|" +
               this.isbn + "|" +
               this.nimi + "|" +
               this.tekija + "|" +
               this.julkaisuVuosi;
    }
    
    
    /**
     * Antaa teokselle seuraavan id-numeron.
     * @return Teoksen uusi id-numero.
     * @example
     * <pre name="test">
     *  Teos t1 = new Teos();
     *  t1.getId() === 0;
     *  t1.rekisteroi();
     *  t1.getId() === 1;
     *  Teos t2 = new Teos();
     *  t2.rekisteroi();
     *  t2.getId() === 2;
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
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Teos t1 = new Teos();
        System.out.println(t1);
        t1.vastaaLotr();
        t1.tulosta(System.out);
        t1.rekisteroi();
        t1.tulosta(System.out);
        Teos t2 = new Teos();
        t2.rekisteroi();
        t2.tulosta(System.out);
    
    }

}
