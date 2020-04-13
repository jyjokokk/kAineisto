package kirjasto;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Luokka kategorialle.
 * @author jyrki
 * @version Mar 24, 2020
 */
public class Kategoria {

    private int kid;
    private String nimi = "";
    private String kuvaus = "";

    private static int seuraavaKid = 1;

    /**
     * Vakiomuodostaja.
     */
    public Kategoria() {
        // Alustettu esittelyssa.
    }


    /**
     * Alustaa olion parseamalla sen arvot annetusta merkkijonosta
     * @param syote annettu merkkijono
     */
    public Kategoria(String syote) {
        this.parse(syote);
    }


    /**
     * Muodostaja kuvauksella
     * @param nimi kategorialle
     * @param kuvaus kategorialle
     */
    public Kategoria(String nimi, String kuvaus) {
        this.nimi = nimi;
        this.kuvaus = kuvaus;
    }


    /**
     * Alustaa olion attribuutit merkkijonosta seulotuilla arvoilla.
     * @param syote merkkijonono, josta seulotaan.
     * @example
     * <pre name="test">
     *  Kategoria uusi = new Kategoria();
     *  uusi.parse("1|Fantasia|Fantasiakirjallisuus on...");
     *  uusi.toString() === "1|Fantasia|Fantasiakirjallisuus on...";
     *  uusi.parse("5|Scifi|BlaaBlaa");
     *  uusi.toString() === "5|Scifi|BlaaBlaa";
     * </pre>
     */
    public void parse(String syote) {
        if (syote.length() == 0) return;
        StringBuilder sb = new StringBuilder(syote);
        setKid(Mjonot.erota(sb, '|', getKid()));
        this.nimi = Mjonot.erota(sb, '|');
        this.kuvaus = sb.toString();
    }
    

    /**
     * Muodostaja kategoria idlla ja kuvauksella
     * @param kid kategoria id
     * @param nimi nimi kategorialle 
     * @param kuvaus kuvaus
     */
    public Kategoria(int kid, String nimi, String kuvaus) {
        this.kid = kid;
        this.nimi = nimi;
        this.kuvaus = kuvaus;
    }


    /**
     * Apumetodi, jolla saadaan luotua testattava olio,
     * annetuilla arvoilla.
     */
    public void vastaaFantasia() {
        this.setNimi("Fantasia");
        this.setKuvaus("Fantasiakirjallisuus on kirjallisuuden...");
    }


    /**
     * Apumetodi, jolla saadaan luotua testattava olio,
     * annetuilla arvoilla, ja rekisteroi sen samalla.
     */
    public void vastaaFantasiaRek() {
        this.setNimi("Fantasia");
        this.setKuvaus("Fantasiakirjallisuus on kirjallisuuden...");
        this.rekisteroi();
    }


    /**
     * Muuttaa kategorian kuvausta.
     * @param k Uusi kuvaus kategorialle.
     * @example
     * <pre name="test">
     *  Kategoria uusi = new Kategoria("Fantasia", "Fantasiakirjallisuus on...");
     *  uusi.toString() === "0|Fantasia|Fantasiakirjallisuus on...";
     *  uusi.muutaKuvausta("Uusi kuvaus");
     *  uusi.toString() === "0|Fantasia|Uusi kuvaus";
     * </pre>
     */
    public void muutaKuvausta(String k) {
        this.kuvaus = k;
    }


    /**
     * Antaa teokselle seuraavan kid numeron.
     * @return Kategorian uusi id numero.
     * @example
     * <pre name="test">
     *  Kategoria kat1 = new Kategoria();
     *  Kategoria kat2 = new Kategoria();
     *  Kategoria kat3 = new Kategoria();
     *  kat1.rekisteroi(); kat2.rekisteroi(); kat3.rekisteroi();
     *  int kid1 = kat1.getKid();
     *  kat1.getKid() === kid1;
     *  kat2.getKid() === kid1 + 1;
     *  kat3.getKid() === kid1 + 2;
     * </pre>
     */
    public int rekisteroi() {
        this.kid = seuraavaKid;
        seuraavaKid++;
        return this.kid;
    }


    /**
     * Palauttaa kategorian tiedot merkkijonona, muodossa kid|kuvaus
     * @return kategorian tiedot merkkijonona
     * @example
     * <pre name="test">
     *  Kategoria k1 = new Kategoria(1, "Kategoria", "Esimerkki kuvauksesta");
     *  k1.toString() === "1|Kategoria|Esimerkki kuvauksesta";
     * </pre>
     */
    @Override
    public String toString() {
        return String.format("%d|%s|%s", kid, nimi, kuvaus);
    }


    /**
     * Palauttaa nimen ja kuvauksen merkkijonona.
     * @return nimi ja kuvaus.
     */
    public String getTiedot() {
        return String.format("%d|%s|%s", kid, nimi, kuvaus);
    }


    /**
     * Tulostetaan kategorian tiedot.
     * @param out tietovirta johon tulostetaan. 
     */
    public void tulosta(PrintStream out) {
        out.println(this.toString());
    }


    /**
     * Tulostetaan kategorian tiedot.
     * @param os tietovirta johon tulostetaan.
     * @example
     * <pre name="test">
     * #import java.io.ByteArrayOutputStream;
     * ByteArrayOutputStream out = new ByteArrayOutputStream(); 
     * Kategoria kat1 = new Kategoria("2|Scifi|Scifi on...");
     * Kategoria kat2 = new Kategoria();
     * kat1.tulosta(out);
     * kat2.tulosta(out);
     * out.toString() === "2|Scifi|Scifi on...\n0||\n";
     * </pre>
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Palauttaa kategorian kuvauksen.
     * @return kuvaus
     */
    public String getKuvaus() {
        return this.kuvaus;
    }


    /**
     * Asettaa nimen
     * @param nimi kategorialle
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }


    /**
     * Palauttaa nimen
     * @return nimi
     */
    public String getNimi() {
        return this.nimi;
    }


    /**
     * Palaauttaa kategoria-id:n.
     * @return kategorian id
     */
    public int getKid() {
        return this.kid;
    }

    
    /**
     * Asettaa kategoriatunnuksen, ja samalla varmistaa
     * etta seuraava numero sailyy aina suurempi kuin tahan
     * mennessa esiintyva kid.
     * @param nr Asetettava kid.
     */
    private void setKid(int nr) {
        this.kid = nr;
        if (kid >= seuraavaKid) seuraavaKid = kid + 1;
    }
    

    /**
     * Asettaa kuvauksen kategorialle.
     * @param kuvaus joka asetetaan
     */
    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }


    /**
     * @param args ei kaytossa.
     */
    public static void main(String[] args) {

        Kategoria fantasia = new Kategoria();
        Kategoria scifi = new Kategoria("Scifi", "Scifi on...");
        Kategoria hist = new Kategoria("0|Historia|Historiallinen...");
        System.out.println(hist);
        fantasia.vastaaFantasia();
        fantasia.rekisteroi();
        scifi.rekisteroi();
        hist.rekisteroi();
        System.out.println("==== Rekisteroity ====");
        System.out.println(fantasia);
        System.out.println(scifi);
        System.out.println(hist);

    }

}
