package kirjasto;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Teosten kokoelma, joka osaa lukea materiaalia tiedostosta, tallentaa itsensa
 * tiedostoon, hakea ja palauttaa omia jaseniaan, ja muokata olioitaan.
 * 
 * Testit ovat tehty isolla osaa hyodyntaen testiaineistohakemistoon 
 * tallenettua esimerkkitiedostoa, jonka URL on "testFiles/teokset.dat".
 * Selkeyden vuoksi, tassa on sen sisalto:
 * 
 *      # Teosten tallenustiedosto
 *      1|31-29-6561398-999432-6|The Lord of the Rings|J.R.R. Tolkien|1954
 *      2|123-123-123-123|Sinuhe|Mika Waltari|1945
 *      3|323-768-564-544|Harry Potter|J.K. Rowling|1999
 *      4|978-951-0-42545-9|Tuntematon Sotilas|Vaino Linna|2017
 *      5|978-0-224-08790-2|Skagboys|Irvine Welsh|2012
 *      6|0-441-56956-0|Neuromancer|William Gibson|1984
 *      7|0-575-03696-6|Count Zero|William Gibson|1987
 *      8|978-951-0-09875-2|Joku Muu|Mika Waltari|1945
 *      9|978-951-0-09875-2|Sinuhe|Mika Waltari|1945
 * 
 * @author Jyrki Kokkola
 * @version Apr 22, 2020
 * 
 */
public class Teokset implements Iterable<Teos> {

    private static final int MAX_TEOKSIA = 5;
    private int lkm = 0;
    private Teos alkiot[] = new Teos[MAX_TEOKSIA];

    /**
     * Vakiomuodostaja.
     */
    public Teokset() {
        // Alustettu esittelyssa.
    }


    /**
     * Lisaa uuden alkion tietorakenteeseen.
     * @param alkio joka lisataan.
     * @throws TietoException jos taulukossa ei tilaa.
     * @example
     * <pre name="test">
     *  #THROWS TietoException
     *  Teokset teokset = new Teokset();
     *  Teos lotr1 = new Teos(), lotr2 = new Teos();
     *  Teos lotr3 = new Teos();
     *  teokset.getLkm() === 0;
     *  teokset.lisaa(lotr1); teokset.getLkm() === 1; 
     *  teokset.lisaa(lotr2); teokset.getLkm() === 2; 
     *  teokset.lisaa(lotr3); teokset.getLkm() === 3;
     * </pre>
     */
    public void lisaa(Teos alkio) throws TietoException {
        if (lkm >= alkiot.length) {
            Teos temp[] = new Teos[alkiot.length + 5];
            for (int i = 0; i < lkm; i++) {
                temp[i] = alkiot[i];
            }
            alkiot = temp;
        }
        alkiot[lkm] = alkio;
        lkm++;
    }


    /**
     * Hakee ja palauttaa viitteen teokseen jolla on haettu id.
     * @param id jota haetaan
     * @return Teos, jolla on vastaava id, null jos ei loydy.
     * @throws TietoException jos teosta annetulla id:lla ei loydy.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Teokset teokset = new Teokset();
     *  Teos lotr1 = new Teos(), lotr2 = new Teos(), lotr3 = new Teos(); 
     *  lotr1.vastaaLotr(); lotr3.vastaaLotr(); lotr2.vastaaLotr();
     *  int idLotr1 = lotr1.getId();
     *  teokset.lisaa(lotr1); teokset.lisaa(lotr2); teokset.lisaa(lotr3); 
     *  teokset.haeId(idLotr1) == lotr1 === true;
     *  teokset.haeId(idLotr1 + 2) == lotr2 === true;
     * </pre>
     */
    public Teos haeId(int id) throws TietoException {
        for (int i = 0; i < this.getLkm(); i++) {
            Teos temp = alkiot[i];
            if (temp.getId() == id)
                return temp;
        }
        throw new TietoException(
                "Tietokannasta ei loydy teosta jonka id: " + id);
    }


    /**
     * Lisaa uuden alkion taulukkoon, alustaen sen arvot annetusta merkkijonosta.
     * @param syote Merkkijono, josta arvot alustetaan
     * @example
     * <pre name="test">
     *  Teokset teokset = new Teokset();
     *  teokset.lisaa("1|123-123-123-123|Uusi Kirja|Matti Meikalainen|1990");
     *  teokset.getLkm() === 1;
     *  teokset.lisaa("2|222-333-444-555|Toinen Kirja|Matti Meikalainen|1990");
     *  teokset.hae(2).toString() === "2|222-333-444-555|Toinen Kirja|Matti Meikalainen|1990";
     * </pre>
     */
    public void lisaa(String syote) {
        if (lkm >= alkiot.length) {
            Teos temp[] = new Teos[alkiot.length + 5];
            for (int i = 0; i < lkm; i++) {
                temp[i] = alkiot[i];
            }
            alkiot = temp;
        }
        alkiot[lkm] = new Teos(syote);
        lkm++;
    }


    /**
     * Korvaa id:lla loydetyn alkion tietorakenteesta.
     * Lisaa uuden alkion, jos vastaavaa alkiota ei loydy.
     * @param teos jota etsitaan
     * @return viite muutettuun tai lisattyyn alkioon
     * @throws TietoException jos lisaamisessa ilmenee ongelma.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Teokset teokset = new Teokset();
     *  teokset.lueTiedostosta("testFiles/teokset.dat");
     *  Teos kirja = new Teos("1|000-000-000-000|Muutettu Kirja|Matti Meikalainen|1990");
     *  teokset.lisaaTaiMuuta(kirja);
     *  teokset.hae(1).toString() === "1|000-000-000-000|Muutettu Kirja|Matti Meikalainen|1990";
     * </pre>
     */
    public Teos lisaaTaiMuuta(Teos teos) throws TietoException {
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getId() == teos.getId()) {
                alkiot[i] = teos;
                return teos;
            }
        }
        lisaa(teos);
        return teos;
    }


    /**
     * Poistaa teoksen haetun id:n perusteella.
     * @param id joka haetaan
     * @return true tai false, loytymisen mukaan.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Teokset teokset = new Teokset();
     *  teokset.lueTiedostosta("testFiles/teokset.dat");
     *  teokset.getLkm() === 9;
     *  teokset.poista(1);
     *  teokset.poista(2);
     *  teokset.getLkm() === 7;
     * </pre>
     */
    public boolean poista(int id) {
        int ind = annaIndeksi(id);
        if (ind < 0)
            return false;
        lkm--;
        for (int i = ind; i < lkm; i++) {
            alkiot[i] = alkiot[i + 1];
        }
        alkiot[lkm] = null;
        return true;
    }


    /**
     * Tyhjentaa tietorakenteen.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Teokset uusi = new Teokset();
     *  uusi.lueTiedostosta("testFiles/teokset.dat");
     *  uusi.getLkm() === 9;
     *  uusi.tyhjenna();
     *  uusi.getLkm() === 0;
     * </pre>
     */
    public void tyhjenna() {
        alkiot = new Teos[MAX_TEOKSIA];
        lkm = 0;
    }


    /**
     * Tallentaa teosluettelon tiedostoon.
     * @param tiedNimi Tiedosto, johon tallenetaan
     * @throws TietoException jos tallentaminen epaonnistuu.
     * @example
     * <pre name="test">
     *  #THROWS TietoException
     *  Teokset teokset = new Teokset();
     *  teokset.lueTiedostosta("testFiles/teokset.dat");
     *  teokset.tallenna("testFiles/teokset.bak");
     *  Teokset toka = new Teokset();
     *  toka.lueTiedostosta("testFiles/teokset.bak");
     *  toka.hae(1).toString() === teokset.hae(1).toString();
     *  teokset.equals(toka) === true;
     * </pre>
     */
    public void tallenna(String tiedNimi) throws TietoException {
        File backupFile = new File(tiedNimi + ".bak");
        File saveFile = new File(tiedNimi + ".dat");
        backupFile.delete();
        saveFile.renameTo(backupFile);

        try (PrintWriter fo = new PrintWriter(
                new FileWriter(saveFile.getCanonicalPath()))) {
            fo.println("# Teosten tallenustiedosto");
            fo.println("# Viimeiksi tallennettu: " + java.time.LocalTime.now());
            for (int i = 0; i < lkm; i++) {
                Teos teos = alkiot[i];
                fo.println(teos.toString());
            }
        } catch (FileNotFoundException ex) {
            throw new TietoException(
                    "Ongelma tiedosta avatessa! " + ex.getMessage());
        } catch (IOException ex) {
            throw new TietoException("Tiedoston kirjoittamisessa on ongelmia."
                    + ex.getMessage());
        }
    }


    /**
     * Lukee teosluettelon tiedostosta, ja tallentaa sen tietorakenteisiin.
     * @param tiedNimi Tiedoston nimi, josta luetaan.
     * @throws TietoException jos ongelmia tiedon avaamisessa.
     * <pre name="test">
     *  #THROWS TietoException
     *  Teokset uusi = new Teokset();
     *  Teokset toka = new Teokset();
     *  uusi.lueTiedostosta("testFiles/teokset.dat");
     *  toka.lueTiedostosta("testFiles/teokset.dat");
     *  uusi.equals(toka) === true;
     *  toka.lueTiedostosta("testFiles/teoksetEri.dat");
     *  uusi.equals(toka) === false;
     * </pre>
     */
    public void lueTiedostosta(String tiedNimi) throws TietoException {
        try (Scanner fi = new Scanner(new FileInputStream(tiedNimi))) {
            while (fi.hasNext()) {
                Teos t = new Teos();
                String s = fi.nextLine();
                if ("".equals(s) || s.charAt(0) == '#')
                    continue;
                t.parse(s);
                this.lisaa(t);
            }
        } catch (FileNotFoundException ex) {
            throw new TietoException(
                    "Ongelma tiedoston " + tiedNimi + "avaamisessa.");
        }
    }


    /**
     * Vertaa, onko olion sisaltamat tiedot samat kuin verratavan.
     * @param toinen Olio, jonka tietoihin verrataan
     * @return Oliko samat
     * @example
     * <pre name="test">
     * #THROWS TietoException
     * Teokset tama = new Teokset();
     * Teokset toinen = new Teokset();
     * toinen.lisaa(new Teos());
     * tama.equals(tama) === true;
     * tama.equals(toinen) === false;
     * </pre>
     */
    public boolean equals(Teokset toinen) {
        String tama = "";
        String toka = "";
        for (int i = 0; i < this.lkm; i++) {
            tama += this.alkiot[i].toString();
        }
        for (int i = 0; i < toinen.lkm; i++) {
            toka += toinen.alkiot[i].toString();
        }
        return tama.equals(toka);
    }


    /**
     * Palauttaa teosten lukumaaran.
     * @return Teosten lukumaara.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Teokset tama = new Teokset();
     *  tama.getLkm() === 0;
     *  tama.lueTiedostosta("testFiles/teokset.dat");
     *  tama.getLkm() === 9;
     * </pre>
     */
    public int getLkm() {
        return this.lkm;
    }


    /**
     * Palalauttaa id:lla haetun teoksen paikan taulukossa.
     * @param id mita teosta haetaan
     * @return teoksen indeksi alkiossa, -1 jos ei loydy
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Teokset teokset = new Teokset();
     *  teokset.lueTiedostosta("testFiles/teokset.dat");
     *  teokset.annaIndeksi(1) === 0;
     *  teokset.annaIndeksi(50) === -1;
     * </pre>
     */
    public int annaIndeksi(int id) {
        for (int i = 0; i < lkm; i++) {
            if (id == alkiot[i].getId())
                return i;
        }
        return -1;
    }


    /**
     * Hakee viitteen olioon, jonka id on i. 
     * @param id Id, jolla oliota haetaan
     * @return Olio, jonka id on i.
     * @throws IndexOutOfBoundsException jos id on laiton.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *   Teokset t1 = new Teokset(); 
     *   t1.lueTiedostosta("testFiles/teokset.dat");
     *   t1.hae(-5).getNimi() === "Nothing"; #THROWS IndexOutOfBoundsException
     *   t1.hae(53).getNimi() === "Nothing"; #THROWS IndexOutOfBoundsException
     *   t1.hae($id).getNimi() === $tulos;
     *   
     *      $id  |  $tulos
     *    -----------------
     *       1   | "The Lord of the Rings"
     *       6   | "Neuromancer"
     *       5   | "Skagboys"
     * </pre>
     */
    public Teos hae(int id) throws IndexOutOfBoundsException {
        if (id < 0 || id > this.lkm)
            throw new IndexOutOfBoundsException("Laiton id: " + id);
        for (int i = 0; i < this.getLkm(); i++) {
            if (alkiot[i].getId() == id)
                return alkiot[i];
        }
        return null;
    }


    /**
     * Palauttaa viitteen teokseen indeksissa i.
     * @param i indeksi josta katsotaan.
     * @return viite teokseen.
     * @example
     * <pre name="test">
     *  #THROWS TietoException
     *  Teokset teokset = new Teokset();
     *  Teos kirja = new Teos();
     *  teokset.lisaa(kirja);
     *  teokset.anna(0).equals(kirja) === true;
     * </pre>
     */
    public Teos anna(int i) {
        return alkiot[i];
    }


    /**
     * Hakee kaikki teoksen, jotka tayttavat hakuehdon.
     * @param nimi teoksen nimi
     * @param ISBN teoksen ISBN
     * @param tekija teoksen tekija
     * @return Kaikki teokset, jotka vastaavat hakuehtoa.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     * #import java.util.*;
     *  Teokset teokset = new Teokset();
     *  teokset.lueTiedostosta("testFiles/teokset.dat");
     *  List<Teos> tulokset = teokset.hae("", "", "William");
     *  tulokset.get(0).toString() === "6|0-441-56956-0|Neuromancer|William Gibson|1984"
     * </pre>
     */
    public ArrayList<Teos> hae(String nimi, String ISBN, String tekija) {
        ArrayList<Teos> tulokset = new ArrayList<Teos>();
        for (int i = 0; i < lkm; i++) {
            Teos temp = alkiot[i];
            if (temp.getTekija().contains(tekija)
                    && temp.getIsbn().contains(ISBN)
                    && temp.getNimi().contains(nimi))
                tulokset.add(temp);
        }
        Collections.sort(tulokset);
        return tulokset;
    }


    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {

        Teokset luettelo = new Teokset();
        Teokset tama = new Teokset();
        Teokset toinen = new Teokset();
        try {
            tama.lueTiedostosta("testFiles/teokset.dat");
            toinen.lueTiedostosta("testFiles/teoksetEri.dat");
            System.out.println(tama.equals(tama));
            System.out.println(tama.equals(toinen));
            toinen.tyhjenna();
            toinen.lueTiedostosta("testFiles/teokset.dat");
            System.out.println(tama.equals(luettelo));
            System.out.println(tama.equals(toinen));
            System.out.println(tama.getLkm());
            Teos kirja = new Teos(
                    "1|000-000-000-000|Muutettu Kirja|Matti Meikalainen|1990");
            tama.lisaaTaiMuuta(kirja);
//            tama.poista(1);
//            tama.poista(3);
            for (var v : tama) {
                System.out.println(v);
            }
            System.out.println(tama.getLkm());
            List<Teos> tulokset = tama.hae("", "", "William");
            System.out.println(tulokset.get(0));
        } catch (TietoException e) {
            e.printStackTrace();
        }

    }

    /**
     * Iteraattori Teokset-luokalle.
     * @author jyrki
     * @version Apr 4, 2020
     */
    public class TeosIterator implements Iterator<Teos> {

        private int kohdalla = 0;

        /**
         * Tarkistaa, onko seuraava alkiota olemassa.
         * @see java.util.Iterator#hasNext()
         * @return true, jos viela alkoita.
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Antaa viitteen seuraavaan alkioon.
         * @return seuraava jasen
         * @throws NoSuchElementException jos seuraavaa alkiota ei ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Teos next() throws NoSuchElementException {
            if (!hasNext())
                throw new NoSuchElementException("Seuraavaa olioita ei ole.");
            return anna(kohdalla++);
        }


        /**
         * Poistamista ei viela toteutettu.
         * @throws UnsupportedOperationException koittaessa
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException(
                    "Poistamista ei ole sallittu.");
        }

    }

    /**
     * Palautetaan iteraattori
     * @return Teos iteraattori
     */
    @Override
    public Iterator<Teos> iterator() {
        return new TeosIterator();
    }

}
