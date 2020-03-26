package kirjasto;

import java.io.*;

/**
 * @author jyrki
 * @version Mar 26, 2020
 */
public class Hylly {

    private int id;
    private int kid;
    private String paikka;
    private int maara;
    
    
    /**
     * Vakiomuodostaja.
     */
    public Hylly() {
        this.id = 0;
        this.kid = 0;
        this.paikka = "";
        this.maara = 0;
    }
    
    
    /**
     * Muodostaja kaikilla annetuilla arvoilla.
     * @param id teoksen id
     * @param kid kategorian id
     * @param paikka paikka kirjahyllyssa
     * @param maara teosten maara
     */
    public Hylly(int id, int kid, String paikka, int maara) {
        this.id = id;
        this.kid = kid;
        this.paikka = paikka;
        this.maara = maara;
    }
    
    
    
    /**
     * Lisaa teosten maaraa yhdella
     * @return uusi maara teoksia
     * @example
     * <pre name="test">
     *  Hylly uusi = new Hylly(1, 2, "JAG", 0);
     *  uusi.getMaara() === 0;
     *  uusi.lisaa();
     *  uusi.getMaara() === 1;
     * </pre>
     */
    public int lisaa() {
        return this.maara++;
    }
    
    
    /**
     * Vahentaa teosten maaraa yhdella
     * @return uusi maara teoksia
     * @example
     * <pre name="test">
     *  Hylly uusi = new Hylly(1, 2, "JAG", 3);
     *  uusi.getMaara() === 3;
     *  uusi.vahenna();
     *  uusi.getMaara() === 2;
     * </pre>
     */
    public int vahenna() {
        return this.maara--;
    }
    

    /**
     * @return paikka
     */
    public String getPaikka() {
        return paikka;
    }

    /**
     * @param paikka to set
     */
    public void setPaikka(String paikka) {
        this.paikka = paikka;
    }

    /**
     * @return maara
     */
    public int getMaara() {
        return maara;
    }

    /**
     * @param maara to set
     */
    public void setMaara(int maara) {
        this.maara = maara;
    }
    
    
    /**
     * Palauttaa hyllypaikassa olevan teoksen id:n.
     * @return Teoksen id.
     */
    public int getId() {
        return this.id;
    }
    

    /** Palauttaa hylypaikka olevan teoksen kategorian id:n.
     * @return Kategorian id.
     */
    public int getKid() {
        return this.kid;
    }
    
    
    
    /**
     * Palauttaa olion tiedot merkkijonona.
     * @return Olion tiedot
     * @example
     * <pre name="test">
     *  Hylly uusi = new Hylly();
     *  uusi.toString() === "0|0||0";
     *  uusi = new Hylly(1, 2, "JAG", 0);
     *  uusi.toString() === "1|2|JAG|0";
     * </pre>
     */
    @Override
    public String toString() {
        return String.format("%d|%d|%s|%d", id, kid, paikka, maara);
    }
    
    
    /**
     * Tulostaa tiedot annettuun PrintStreamiin.
     * @param out PrintStream johon tulostetaan.
     */
    public void tulosta(PrintStream out) {
        out.println(this.toString());
    }
    
    
    /**
     * Tulostaa tiedot annettuun OutputStreamiin
     * @param out OutputStream johon tulostetaan.
     */
    public void tulosta(OutputStream out) {
        tulosta(new PrintStream(out));
    }
    
    /**
     * Palauttaa tiedot teoksen paikasta ja maarasta merkkijonona.
     * @return paikka ja maara
     * @example
     * <pre name="test">
     *  Hylly uusi = new Hylly();
     *  uusi.getTiedot() === "|0";
     *  uusi = new Hylly(1, 2, "JAG", 0);
     *  uusi.getTiedot() === "JAG|0";
     * </pre>
     */
    public String getTiedot() {
        return String.format("%s|%d", paikka, maara);
    }
    
    
    /**
     * Tulostaa tiedot annettuun PrintStreamiin.
     * @param out PrintStream johon tulostetaan.
     */
    public void tulostaTiedot(PrintStream out) {
        out.println(this.getTiedot());
    }
    
    
    /**
     * Tulostaa tiedot annettuun OutputStreamiin
     * @param out OutputStream johon tulostetaan.
     */
    public void tulostaTiedot(OutputStream out) {
        tulostaTiedot(new PrintStream(out));
    }
    

    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }

}
