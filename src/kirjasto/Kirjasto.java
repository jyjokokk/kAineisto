package kirjasto;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author jyrki
 * @version Mar 25, 2020
 */
public class Kirjasto {

    private final Kategoriat kategoriat = new Kategoriat();
    private final Teokset teokset = new Teokset();
    private final Hyllyt hyllyt = new Hyllyt();

    /**
     * Lisaa aineiston kokoelmaan, parseamalla sen merkkijonosta.
     * @param s merkkijono joka annetaan.
     * @return Onnistuneesti lisatyn aineiston id.
     * @throws TietoException jos ilmenee ongelma lisatessa
     */
    public int lisaa(String s) throws TietoException {
        StringBuilder sb = new StringBuilder(s);
        String teosInfo = Mjonot.erota(sb, '#');
        String katInfo = Mjonot.erota(sb, '#');
        Teos teos = new Teos(teosInfo);
        Kategoria kat = kategoriat.lisaa(katInfo);
        String hyllyInfo = String.format("%d|%d|%s", teos.getId(), kat.getKid(),
                sb.toString());
        Hylly paikka = new Hylly(hyllyInfo);
        teokset.lisaa(teos);
        hyllyt.lisaa(paikka);
        return hyllyt.haeIx(paikka.getId());
    }
    
    
    /**
     * Korvaa id:lla loydetyt alkiot tietorakenteissa.
     * Jos id:lla ei loydy alkioita, lisataan uusi alkio.
     * @param s teoksen tiedot merkkijonona.
     * @return muutetun tai lisatyn teoksen id
     * @throws TietoException jos lisaamisessa ilmenee ongelmia
     */
    public int muutaTaiLisaa(String s) throws TietoException {
        StringBuilder sb = new StringBuilder(s);
        String teosInfo = Mjonot.erota(sb, '#');
        String katInfo = Mjonot.erota(sb, '#');
        Teos teos = teokset.lisaaTaiMuuta(new Teos(teosInfo)); // TODO: lisaaTaiMuuta(String s);
        Kategoria kat = kategoriat.lisaa(katInfo);
        String hyllyInfo = String.format("%d|%d|%s", teos.getId(), kat.getKid(),
                sb.toString());
        Hylly paikka = new Hylly(hyllyInfo);
        return hyllyt.lisaaTaiMuuta(paikka).getId();
//        return hyllyt.haeIx(paikka.getId());
    }
    
    
    /**
     * Tyhjentaa kaikki tietorakenteet, poistaen kaiken aineiston
     * nykyisessa sessiossa.
     */
    public void tyhjenna() {
        teokset.tyhjenna();
        kategoriat.tyhjenna();
        hyllyt.tyhjenna();
    }


    /**
     * Hakee kaikki teokset jotka vastaavat hakutermia.
     * @param nimi haettava nimi
     * @param ISBN haettava ISBN
     * @param tekija haettava tekija
     * @return Lista kaikista teoksista, jotka vastaavat hakutermia.
     */
    public ArrayList<Teos> hae(String nimi, String ISBN, String tekija) {
        return teokset.hae(nimi, ISBN, tekija);
    }


    /**
     * Lisaa uuden aineiston kokoelmaan.
     * @throws TietoException jos ilmenee ongelmia
     */
    public void lisaaLotr() throws TietoException {
        Teos teos = new Teos();
        Kategoria kat = new Kategoria();
        Hylly paikka;
        teos.vastaaLotrRand();
        kat.vastaaFantasiaRek();
        kat = kategoriat.lisaa(kat);
        paikka = new Hylly(teos.getId(), kat.getKid(), "JAG", 0);
        teokset.lisaa(teos);
        hyllyt.lisaa(paikka);
    }


    /**
     * Tallentaa tietorakenteet tiedostoihinsa.
     * @throws TietoException jos ongelmia 
     */
    public void tallenna() throws TietoException {
        kategoriat.tallenna("aineisto/kategoriat");
        teokset.tallenna("aineisto/teokset");
        hyllyt.tallenna("aineisto/hyllyt");
    }


    /**
     * Lukee tietorakenteet tiedostoistaan
     * @throws FileNotFoundException Jos tiedostoa ei loydy.
     * @throws TietoException jos ongelmia
     */
    public void lueTiedostosta() throws FileNotFoundException, TietoException {
            kategoriat.lueTiedostosta("aineisto/kategoriat.dat");
            teokset.lueTiedostosta("aineisto/teokset.dat");
            hyllyt.lueTiedostosta("aineisto/hyllyt.dat");
    }


    /**
     * Poistaa aineistosta ne teokset joiden id vastaa nro.
     * @param nro Numero, jota vastaavaa id:ta etsitaan
     * @return Boolean onnistuiko
     */
    public Boolean poista(@SuppressWarnings("unused") int nro) {
        return false;
    }


    /**
     * Palauttaa teosten maaran tietokannassa
     * @return kokoelman koko
     */
    public int getTeosLkm() {
        return teokset.getLkm();
    }


    /**
     * Antaa teokset indeksissa i olevassa paikassa
     * @param i indeksi
     * @return teos indeksissa i
     * @example
     * <pre name="test">
     * #THROWS TietoException;
     *  Kirjasto kirjasto = new Kirjasto();
     *  String s0 = "0|123-123-123-123|Neuromancer|William Gibson|1984#4|Scifi|Tieteiskirjallisuus on...#WGA|4";
     *  String s1 = "0|666-666-666-666|Filth|Irvin Welsh|1995#3|Modernismi|Modernismin piirteita....#WEL|2";
     *  String s2 = "0|021-6532-231-34|Mona Lisa Overdrive|William Gibson|1984#4|Scifi|Muuttunut kuvaus#WGA|4";
     *  kirjasto.lisaa(s0); kirjasto.lisaa(s1); kirjasto.lisaa(s2);
     *  kirjasto.getTeos(1).toString() === "1|1|WGA|4";
     * </pre>
     */
    public Teos getTeos(int i) {
        return teokset.anna(i);
    }


    /**
     * Palauttaa hyllyssa olevien teosten maaran tietokannassa.
     * @return hyllyssa olevien teosten lkm
     */
    public int getHyllyLkm() {
        return hyllyt.getLkm();
    }


    /**
     * Etsii teoksen sen id:n avulla, ja palauttaa sen nimen.
     * @param h Hyllypaikka
     * @return Teoksen nimi.
     * @throws TietoException jos IDn avulla ei loydy yhtaan teosta
     */
    public String getTeosNimi(Hylly h) throws TietoException {
        Teos t = teokset.haeId(h.getId());
        return t.getNimi();
    }


    /**
     * Hakee hyllypaikkojen tietorakenteen indeksissa i olevan viitteen 
     * @param i indeksi
     * @return indeksissa i oleva teos
     * @example
     * <pre name="test">
     *  #THROWS TietoException
     *   Kirjasto kirjasto = new Kirjasto();
     * </pre>
     */
    public Hylly anna(int i) {
        return hyllyt.anna(i);
    }


    /**
     * Etsii ja palauttaa viitteen hyllypaikkaan, jolla on haettu id.
     * @param id Id, jota haetaan.
     * @return viite hyllypaikkaan, null jos ei loydy.
     * @throws TietoException jos hyllypaikkaa ei loydy
     * @example
     * <pre name="test">
     * </pre>
     */
    public Hylly haeId(int id) throws TietoException {
        return hyllyt.haeId(id);
    }


    /**
     * Antaa hyllypaikassa olevan teoksen kaikki tiedot.
     * @param h hyllypaikassa oleva paikka
     * @return Teoksen kaikki tiedot merkkijonona.
     * @throws TietoException jos annetuilla arvoilla ei loydy teoksia.
     */
    public String annaTiedot(Hylly h) throws TietoException {
        StringBuilder sb = new StringBuilder();
        Teos t = teokset.haeId(h.getId());
        Kategoria kat = kategoriat.haeId(h.getKid());
        sb.append(t.toString() + "|");
        sb.append(kat.getTiedot() + "|");
        sb.append(h.getTiedot());
        return sb.toString();
    }
    
    
    /**
     * Hakee teokset tiedot id:n avulla.
     * @param id teoksen id
     * @return Teoksen tiedot
     * @throws TietoException jos annetulla id:lla ei loydy teosta.
     */
    public String annaTiedot(int id) throws TietoException {
        Hylly h = hyllyt.haeId(id);
        return annaTiedot(h);
    }


    /**
     * Tulostaa kirjan tiedot annettuun tulostusvirtaan.
     * @param os tulostusvirta
     * @param h Hyllypaikka, jonka tiedot tulostetaan.
     * @throws TietoException jos ongelmia teoksen loytymisessa.
     */
    public void tulostaTiedot(PrintStream os, Hylly h) throws TietoException {
        os.println(annaTiedot(h));
    }


    /**
     * Tulostaa kaikkien hyllyssa olevien teosten tiedot annettuun
     * PrintStreamiin.
     * @param os PrintSteam, johon tulostetaan.
     * @throws TietoException jos etsiminen epaonnistuu
     */
    public void tulostaKaikki(PrintStream os) throws TietoException {
        os.println("============================================");
        os.println("Kaikkien aineistoon kuuluvien teosten tiedot");
        os.println("============================================\n");
        // Nain voidaan suorittaa myos tyhjalla kirjastolla:
        if (this.getTeosLkm() == 0 && this.getHyllyLkm() == 0) {
            System.out.println("\nKirjasto on tyhja!");
            return;
        }
        for (int i = 0; i < this.getTeosLkm(); i++) {
            tulostaTiedot(os, this.anna(i));
            os.println();
        }
    }


    /**
     * @param args ei kaytossa.
     */
    public static void main(String[] args) {

        String s = "3|123-123-123-123|Neuromancer|William Gibson|1984#4|Scifi|Tieteiskirjallisuus on...#WGA|4";
        String s1 = "5|666-666-666-666|Filth|Irvin Welsh|1995#3|Modernismi|Modernismin piirteita....#WEL|2";
        String s2 = "2|021-6532-231-34|Mona Lisa Overdrive|William Gibson|1984#4|Scifi|Muuttunut kuvaus#WGA|4";

        Kirjasto kirjasto = new Kirjasto();

        try {
            kirjasto.lueTiedostosta();
            kirjasto.lisaaLotr();
            kirjasto.lisaaLotr();
            kirjasto.lisaa(s1);
            kirjasto.lisaa(s);
            kirjasto.lisaa(s2);
            // kirjasto.tyhjenna();
            kirjasto.tulostaKaikki(System.out);
            kirjasto.tallenna();
        } catch (TietoException e) {
            System.err.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }

}
