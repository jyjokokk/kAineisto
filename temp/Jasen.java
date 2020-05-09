package kerho;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.HetuTarkistus;

import static kanta.HetuTarkistus.*;

/**
 * Kerhon j�sen joka osaa mm. itse huolehtia tunnusNro:staan.
 *
 * @author Vesa Lappalainen
 * @version 1.0, 22.02.2003
 */
public class Jasen implements Cloneable {
    private int        tunnusNro;
    private String     nimi           = "";
    private String     hetu           = "";
    private String     katuosoite     = "";
    private String     postinumero    = "";
    private String     postiosoite    = "";
    private String     kotipuhelin    = "";
    private String     tyopuhelin     = "";
    private String     autopuhelin    = "";
    private int        liittymisvuosi = 0;
    private double     jmaksu         = 0;
    private double     maksu          = 0;
    private String     lisatietoja    = "";

    private static int seuraavaNro    = 1;


    /**
     * @return j�senen nimi
     * @example
     * <pre name="test">
     *   Jasen aku = new Jasen();
     *   aku.vastaaAkuAnkka();
     *   aku.getNimi() =R= "Ankka Aku .*";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }

    
    /**
     * @return j�senen hetu 
     */ 
    public String getHetu() { 
        return hetu; 
    } 
    
    
    /** 
     * @return j�senen katuosoite 
     */ 
    public String getKatuosoite() { 
        return katuosoite; 
    } 
    
    
    /** 
     * @return j�senen postinumeri 
     */  
    public String getPostinumero() { 
        return postinumero; 
    } 

    
    /**
     * @param s j�senelle laitettava nimi
     * @return virheilmoitus, null jos ok
     */
    public String setNimi(String s) {
        nimi = s;
        return null;
    }

    
    private HetuTarkistus hetut = new HetuTarkistus();
    
    /**
     * @param s j�senelle laitettava hetu
     * @return virheilmoitus, null jos ok
     */
    public String setHetu(String s) {
        
        String virhe = hetut.tarkista(s);
        if ( s != null ) return virhe;
        hetu = s;
        return null;
    }

    
    /**
     * @param s j�senelle laitettava katuosoite
     * @return virheilmoitus, null jos ok
     */
    public String setKatuosoite(String s) {
        katuosoite = s;
        return null;
    }

    
    /**
     * @param s j�senelle laitettava postinumero
     * @return virheilmoitus, null jos ok
     */
    public String setPostinumero(String s) {
        if ( !s.matches("[0-9]*") ) return "Postinmeron oltava numeerinen";
        postinumero = s;
        return null;
    }

    
    /**
     * Apumetodi, jolla saadaan t�ytetty� testiarvot j�senelle.
     * @param apuhetu hetu joka annetaan henkil�lle 
     */
    public void vastaaAkuAnkka(String apuhetu) {
        nimi = "Ankka Aku " + rand(1000, 9999);
        hetu = apuhetu;
        katuosoite = "Paratiisitie 13";
        postinumero = "12345";
        postiosoite = "ANKKALINNA";
        kotipuhelin = "12-1234";
        tyopuhelin = "";
        autopuhelin = "";
        liittymisvuosi = 1996;
        jmaksu = 50.00;
        maksu = 30.00;
        lisatietoja = "Velkaa Roopelle";
    }


    /**
     * Apumetodi, jolla saadaan t�ytetty� testiarvot j�senelle.
     * Henkil�tunnus arvotaan, jotta kahdella j�senell� ei olisi
     * samoja tietoja.
     */
    public void vastaaAkuAnkka() {
        String apuhetu = arvoHetu();
        vastaaAkuAnkka(apuhetu);
    }


    /**
     * Tulostetaan henkil�n tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro, 3) + "  " + nimi + "  "
                + hetu);
        out.println("  " + katuosoite + "  " + postinumero + " " + postiosoite);
        out.println("  k: " + kotipuhelin +
                " t: " + tyopuhelin +
                " a: " + autopuhelin);
        out.print("  Liittynyt " + liittymisvuosi + ".");
        out.println("  J�senmaksu " + String.format("%4.2f", jmaksu) + " e." +
                "  Maksettu " + String.format("%4.2f", maksu) + " e.");
        out.println("  " + lisatietoja);
    }


    /**
     * Tulostetaan henkil�n tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Antaa j�senelle seuraavan rekisterinumeron.
     * @return j�senen uusi tunnusNro
     * @example
     * <pre name="test">
     *   Jasen aku1 = new Jasen();
     *   aku1.getTunnusNro() === 0;
     *   aku1.rekisteroi();
     *   Jasen aku2 = new Jasen();
     *   aku2.rekisteroi();
     *   int n1 = aku1.getTunnusNro();
     *   int n2 = aku2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }


    /**
     * Palauttaa j�senen tunnusnumeron.
     * @return j�senen tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }


    /**
     * Asettaa tunnusnumeron ja samalla varmistaa ett�
     * seuraava numero on aina suurempi kuin t�h�n menness� suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    

    /**
     * Palauttaa j�senen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return j�sen tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Jasen jasen = new Jasen();
     *   jasen.parse("   3  |  Ankka Aku   | 030201-111C");
     *   jasen.toString().startsWith("3|Ankka Aku|030201-111C|") === true; // on enemm�kin kuin 3 kentt��, siksi loppu |
     * </pre>  
     */
    @Override
    public String toString() {
        return "" +
                getTunnusNro() + "|" +
                nimi + "|" +
                hetu + "|" +
                katuosoite + "|" +
                postinumero + "|" +
                postiosoite + "|" +
                kotipuhelin + "|" +
                tyopuhelin + "|" +
                autopuhelin + "|" +
                liittymisvuosi + "|" +
                jmaksu + "|" +
                maksu + "|" +
                lisatietoja;
    }


    /**
     * Selvit�� j�senen tiedot | erotellusta merkkijonosta
     * Pit�� huolen ett� seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta j�senen tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Jasen jasen = new Jasen();
     *   jasen.parse("   3  |  Ankka Aku   | 030201-111C");
     *   jasen.getTunnusNro() === 3;
     *   jasen.toString().startsWith("3|Ankka Aku|030201-111C|") === true; // on enemm�kin kuin 3 kentt��, siksi loppu |
     *
     *   jasen.rekisteroi();
     *   int n = jasen.getTunnusNro();
     *   jasen.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   jasen.rekisteroi();           // ja tarkistetaan ett� seuraavalla kertaa tulee yht� isompi
     *   jasen.getTunnusNro() === n+20+1;
     *     
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        nimi = Mjonot.erota(sb, '|', nimi);
        hetu = Mjonot.erota(sb, '|', hetu);
        katuosoite = Mjonot.erota(sb, '|', katuosoite);
        postinumero = Mjonot.erota(sb, '|', postinumero);
        postiosoite = Mjonot.erota(sb, '|', postiosoite);
        kotipuhelin = Mjonot.erota(sb, '|', kotipuhelin);
        tyopuhelin = Mjonot.erota(sb, '|', tyopuhelin);
        autopuhelin = Mjonot.erota(sb, '|', autopuhelin);
        liittymisvuosi = Mjonot.erota(sb, '|', liittymisvuosi);
        jmaksu = Mjonot.erota(sb, '|', jmaksu);
        maksu = Mjonot.erota(sb, '|', maksu);
        lisatietoja = Mjonot.erota(sb, '|', lisatietoja);
    }
    
    
    /**
     * Tehd��n identtinen klooni j�senest�
     * @return Object kloonattu j�sen
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Jasen jasen = new Jasen();
     *   jasen.parse("   3  |  Ankka Aku   | 123");
     *   Jasen kopio = jasen.clone();
     *   kopio.toString() === jasen.toString();
     *   jasen.parse("   4  |  Ankka Tupu   | 123");
     *   kopio.toString().equals(jasen.toString()) === false;
     * </pre>
     */
    @Override
    public Jasen clone() throws CloneNotSupportedException {
        Jasen uusi;
        uusi = (Jasen) super.clone();
        return uusi;
    }
    
    
    @Override
    public boolean equals(Object jasen) {
        if ( jasen == null ) return false;
        return this.toString().equals(jasen.toString());
    }


    @Override
    public int hashCode() {
        return tunnusNro;
    }


    /**
     * Testiohjelma j�senelle.
     * @param args ei k�yt�ss�
     */
    public static void main(String args[]) {
        Jasen aku = new Jasen(), aku2 = new Jasen();
        aku.rekisteroi();
        aku2.rekisteroi();
        aku.tulosta(System.out);
        aku.vastaaAkuAnkka();
        aku.tulosta(System.out);

        aku2.vastaaAkuAnkka();
        aku2.tulosta(System.out);

        aku2.vastaaAkuAnkka();
        aku2.tulosta(System.out);
    }

}
