package kirjasto;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

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
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lisaa("1|000000|Kirja|Tekija|1900#1|Kategoria|Kuvaus#ABC|10");
     *  kirjasto.getTeosLkm() === 1;
     *  kirjasto.lisaa("") === 0; #THROWS TietoException
     * </pre>
     */
    public int lisaa(String s) throws TietoException {
        if (s.length() == 0) throw new TietoException("Vika annetussa merkkijonossa.");
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
     * #THROWS TietoException
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lisaa("1|000000|Kirja|Tekija|1900#1|Kategoria|Kuvaus#ABC|10");
     *  kirjasto.muutaTaiLisaa("1|000000|MuutettuKirja|MuutettuTekija|1900#1|Kategoria|Kuvaus#ABC|10");
     *  kirjasto.annaTiedot(1) === "1|000000|MuutettuKirja|MuutettuTekija|1900|1|Kategoria|Kuvaus|1|1|ABC|10";
     *  kirjasto.muutaTaiLisaa("2|11111|ToinenKirja|Tekija|1900#1|Kategoria|Kuvaus#ABC|10");
     *  kirjasto.annaTiedot(2) === "2|11111|ToinenKirja|Tekija|1900|1|Kategoria|Kuvaus|2|1|ABC|10";
     */
    public int muutaTaiLisaa(String s) throws TietoException {
        StringBuilder sb = new StringBuilder(s);
        String teosInfo = Mjonot.erota(sb, '#');
        String katInfo = Mjonot.erota(sb, '#');
        Teos teos = teokset.lisaaTaiMuuta(new Teos(teosInfo)); // TODO:
                                                               // lisaaTaiMuuta(String
                                                               // s);
        Kategoria kat = kategoriat.lisaa(katInfo);
        String hyllyInfo = String.format("%d|%d|%s", teos.getId(), kat.getKid(),
                sb.toString());
        Hylly paikka = new Hylly(hyllyInfo);
        return hyllyt.lisaaTaiMuuta(paikka).getId();
    }


    /**
     * Hakee teoksen annetun id:n perusteella, ja poistaa sen tietorakenteista.
     * @param id jolla haetaan
     * @throws TietoException jos ongelmia
     * <pre name="test">
     * #THROWS TietoException
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostostaTest();
     *  kirjasto.getTeosLkm() === 9;
     *  kirjasto.poista(4);
     *  kirjasto.poista(3);
     *  kirjasto.getTeosLkm() === 7;
     * </pre>
     */
    public void poista(int id) throws TietoException {
        teokset.poista(id);
        hyllyt.poista(id);
    }


    /**
     * Tyhjentaa kaikki tietorakenteet, poistaen kaiken aineiston
     * nykyisessa sessiossa.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostostaTest();
     *  kirjasto.getTeosLkm() === 9;
     *  kirjasto.tyhjenna();
     *  kirjasto.getTeosLkm() === 0;
     * </pre>
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
     * @example
     * <pre name="test">
     * #THROWS TietoException
     * #import java.util.*;
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostostaTest();
     *  List<Teos> tulokset = kirjasto.hae("", "", "William");
     *  tulokset.get(0).toString() === "7|0-575-03696-6|Count Zero|William Gibson|1987";
     * </pre>
     */
    public ArrayList<Teos> hae(String nimi, String ISBN, String tekija) {
        return teokset.hae(nimi, ISBN, tekija);
    }


    /**
     * Tallentaa tietorakenteet tiedostoihinsa.
     * @param dir hakemisto, johon tiedostot tallenetaan
     * @throws TietoException jos ongelmia 
     * @example
     * @example
     * <pre name="test">
     * #THROWS TietoException 
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostosta("testFiles");
     *  kirjasto.tallenna("testFiles");
     *  Kirjasto kirjasto2 = new Kirjasto();
     *  kirjasto2.lueTiedostosta("testFiles");
     *  kirjasto2.getTeosLkm() === 9;
     * </pre>
     */
    public void tallenna(String dir) throws TietoException {
        kategoriat.tallenna(dir + "/kategoriat");
        teokset.tallenna(dir + "/teokset");
        hyllyt.tallenna(dir + "/hyllyt");
    }


    /**
     * Lukee tietorakenteet tiedostoistaan
     * @param dir hakemisto, josta tiedostot luetaan
     * @throws TietoException jos ongelmia
     * @example
     * <pre name="test">
     * #THROWS TietoException 
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostosta("testFiles");
     *  kirjasto.getTeosLkm() === 9;
     * </pre>
     */
    public void lueTiedostosta(String dir) throws TietoException {
        kategoriat.lueTiedostosta(dir + "/kategoriat.dat");
        teokset.lueTiedostosta(dir + "/teokset.dat");
        hyllyt.lueTiedostosta(dir + "/hyllyt.dat");
    }
    
    
    /**
     * Testauksen apuna kaytetty metodi, joka lukee fixturetiedostot tietorakenteisiin.
     * @throws TietoException jos lukemisessa ongelmia.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostostaTest();
     *  kirjasto.getTeosLkm() === 9;
     * </pre>
     */
    public void lueTiedostostaTest() throws TietoException {
        kategoriat.lueTiedostosta("testFiles/kategoriat.dat");
        teokset.lueTiedostosta("testFiles/teokset.dat");
        hyllyt.lueTiedostosta("testFiles/hyllyt.dat");
    }

    

    /**
     * Palauttaa teosten maaran tietokannassa
     * @return kokoelman koko
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostostaTest();
     *  kirjasto.getTeosLkm() === 9;
     * </pre>
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
     * #THROWS TietoException
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostostaTest();
     *  Teos tulos = kirjasto.getTeos(0);
     *  tulos.toString() === "1|31-29-6561398-999432-6|The Lord of the Rings|J.R.R. Tolkien|1954";
     * </pre>
     */
    public Teos getTeos(int i) {
        return teokset.anna(i);
    }


    /**
     * Palauttaa hyllyssa olevien teosten maaran tietokannassa.
     * @return hyllyssa olevien teosten lkm
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostostaTest();
     *  kirjasto.getHyllyLkm() === 9;
     * </pre>
     */
    public int getHyllyLkm() {
        return hyllyt.getLkm();
    }


    /**
     * Etsii teoksen sen id:n avulla, ja palauttaa sen nimen.
     * @param h Hyllypaikka
     * @return Teoksen nimi.
     * @throws TietoException jos IDn avulla ei loydy yhtaan teosta
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostostaTest();
     * </pre>
     */
    public String getTeosNimi(Hylly h) throws TietoException {
        Teos t = teokset.haeId(h.getId());
        return t.getNimi();
    }


    /**
     * Hakee teoksen hyllypaikan id:n avulla
     * @param id jolla haetaan
     * @return teoksen hyllypaikka
     * @throws TietoException jos ei loydy.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostostaTest();
     *  kirjasto.getTeosPaikka(1) === "JAG";
     *  kirjasto.getTeosPaikka(25) === "ERR"; #THROWS TietoException
     * </pre>
     */
    public String getTeosPaikka(int id) throws TietoException {
        String s = hyllyt.haeId(id).getPaikka();
        return s;
    }


    /**
     * Hakee teosten maaran hyllyssa merkkijonona ilmaistuna.
     * @param id jolla haetaan
     * @return teosten maara hyllyssa, merkkijonona
     * @throws TietoException jos ei id:lla ei loydy.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostostaTest();
     *  kirjasto.getTeosMaara(1) === "0";
     *  kirjasto.getTeosMaara(3) === "2";
     *  kirjasto.getTeosMaara(25) === "ERR"; #THROWS TietoException
     * </pre>
     */
    public String getTeosMaara(int id) throws TietoException {
        String s = String.valueOf(hyllyt.haeId(id).getMaara());
        return s;
    }


    /**
     * Hakee hyllypaikkojen tietorakenteen indeksissa i olevan viitteen 
     * @param i indeksi
     * @return indeksissa i oleva teos
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostostaTest();
     *  Hylly tulos = kirjasto.anna(0);
     *  tulos.toString() === "1|1|JAG|0";
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
     * #THROWS TietoException
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostostaTest();
     *  Hylly tulos = kirjasto.haeId(1);
     *  tulos.toString() === "1|1|JAG|0";
     *  kirjasto.haeId(64).toString() === "ERR"; #THROWS TietoException
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
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostostaTest();
     *  Hylly h = kirjasto.haeId(1);
     *  String tulos = kirjasto.annaTiedot(h);
     *  tulos === "1|31-29-6561398-999432-6|The Lord of the Rings|J.R.R. Tolkien|1954|1|Fantasia|Fantasiakirjallisuus on kirjallisuuden...|JAG|0";
     * </pre>
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
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostostaTest();
     *  String tulos = kirjasto.annaTiedot(1);
     *  tulos === "1|31-29-6561398-999432-6|The Lord of the Rings|J.R.R. Tolkien|1954|1|Fantasia|Fantasiakirjallisuus on kirjallisuuden...|JAG|0";
     *  kirjasto.annaTiedot(64) === "ERR"; #THROWS TietoException
     * </pre>
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
     * Tulostaa kirjan tiedot ihmisluettavassa muodossa.
     * @param id jolla haetaan
     * @return Teoksen tiedot merkkijonona, tulostusta varten formatoituna.
     * @throws TietoException jos ei loydy.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostostaTest();
     *  String s = kirjasto.tulostusMuoto(1);
     *  s === "\nTeoksen nimi    The Lord of the Rings\nKirjailija      J.R.R. Tolkien\nISBN            31-29-6561398-999432-6\nKategoria       Fantasia\nHyllypaikka     JAG\nMaara           0\n----------------------------------";
     * </pre>
     */
    public String tulostusMuoto(int id) throws TietoException {
        Hylly hylly = new Hylly();
        Teos teos = new Teos();
        Kategoria kat = new Kategoria();
        StringBuilder sb = new StringBuilder();
        hylly = hyllyt.haeId(id);
        teos = teokset.haeId(hylly.getId());
        kat = kategoriat.haeId(hylly.getKid());
        sb.append("\nTeoksen nimi    " + teos.getNimi());
        sb.append("\nKirjailija      " + teos.getTekija());
        sb.append("\nISBN            " + teos.getIsbn());
        sb.append("\nKategoria       " + kat.getNimi());
        sb.append("\nHyllypaikka     " + hylly.getPaikka());
        sb.append("\nMaara           " + hylly.getMaara());
        sb.append("\n----------------------------------");
        return sb.toString();
    }


    /**
     * Palauttaa annetun id-listan teosten tulostusmuodot merkkijonoolistana.
     * @param idt lista teod-ideista
     * @return Lista teosten tiedoista tulostusmuodossa.
     * @throws TietoException jos teosta ei loydy jollain annetulla id:lla.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     * #import java.util.ArrayList;
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostostaTest();
     *  ArrayList<Integer> idt = new ArrayList<Integer>();
     *  idt.add(65);
     *  kirjasto.teoksetTulosteeksi(idt) === "ERR"; #THROWS TietoException
     * </pre>
     */
    public String teoksetTulosteeksi(List<Integer> idt) throws TietoException {
        StringBuilder sb = new StringBuilder();
        for (int i : idt) {
            sb.append(tulostusMuoto(i));
        }
        return sb.toString();
    }


    /**
     * Tulostaa annetun id-listan teokset tulostusvirtaan.
     * @param os tulostusvirta
     * @param idt Lista teoksien id-numeroita
     * @throws TietoException jos joukossa on virheellinen id.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     * #import java.util.ArrayList;
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostostaTest();
     *  ArrayList<Integer> idt = new ArrayList<Integer>();
     *  idt.add(65);
     *  kirjasto.tulostaTeokset(System.out, idt); #THROWS TietoException
     * </pre>
     */
    public void tulostaTeokset(PrintStream os, List<Integer> idt)
            throws TietoException {
        String tuloste = teoksetTulosteeksi(idt);
        os.println(tuloste);
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
        Kirjasto testKirjasto = new Kirjasto();

        try {
            kirjasto.lueTiedostosta("aineisto");
            kirjasto.lisaa(s1);
            kirjasto.lisaa(s);
            kirjasto.lisaa(s2);
            ArrayList<Integer> ids = new ArrayList<Integer>();
            ids.add(1);
            ids.add(2);
            String tuloste = kirjasto.teoksetTulosteeksi(ids);
            System.out.println(tuloste);
            System.out.println("========\ntestKirjasto\n=========");
            testKirjasto.lueTiedostostaTest();
            System.out.println(testKirjasto.getTeosLkm());
            System.out.println(testKirjasto.getTeos(0));
            System.out.println(testKirjasto.annaTiedot(1));
            System.out.println(testKirjasto.tulostusMuoto(1));
        } catch (TietoException e) {
            System.err.println(e.getMessage());
        } 
    }

}
