package kirjasto;

import java.io.*;

/**
 * Teos, joka sisaltaa kirjan tarkeimmat ominaisuudet,
 * ja tarvittaessa muuttaa ja tarkistaa niita itse.
 * @author jyrki
 * @version Mar 4, 2020
 */
public class Teos {
    
    private int id;
    private String isbn;
    private String nimi;
    private String tekija;
    private int julkaisuVuosi;
    
    private int seuraavaId = 1;
    
    
    /**
     * Vakiomuodostoja, joka alustaa arvot tyhjiksi.
     */
    public Teos() {
        this.id = 0;
        this.isbn = "0-000-00000-0";
        this.tekija = "";
        this.julkaisuVuosi = 0;
    }
    
    
    /**
     * Palauttaa olion tiedot merkkijonona.
     * @returns Tiedot merkkijonona.
     * @example
     * <pre name="test">
     *     Teos t1 = new Teos();
     *     t1.toString === "0|0-000-00000-0||0";
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
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Teos t1 = new Teos();
        System.out.println(t1);
    
    }

}
