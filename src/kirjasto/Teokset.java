package kirjasto;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import fi.jyu.mit.ohj2.*;

/**
 * Teosten kokoelma, jokaa osaa mm lisata ja poistaa teoksen.
 * @author Jyrki Kokkola
 * @version Apr 4, 2020
 * 
 */
public class Teokset implements Iterable<Teos> {

    private static final int MAX_TEOKSIA = 5;
    private int lkm = 0;
    private String tiedostonNimi = "";
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
     * TODO: Tee dynaamisesti kasvavaksi.
     */
    public void lisaa(Teos alkio) throws TietoException {
        if (lkm >= alkiot.length)
            throw new TietoException("Liikaa alkioita.");
        alkiot[lkm] = alkio;
        lkm++;
    }


    /**
     * Lisaa uuden alkion taulukkoon, alustaen sen arvot annetusta merkkijonosta.
     * @param syote Merkkijono, josta arvot alustetaan
     * @throws TietoException jos taulukossa ei tilaa.
     * TODO: Tee dynaamisesti kasvavaksi.
     */
    public void lisaa(String syote) throws TietoException {
        if (lkm >= alkiot.length)
            throw new TietoException("Liikaa alkioita.");
        alkiot[lkm] = new Teos(syote);
        lkm++;
    }


    /**
     * Tyhjentaa tietorakenteen.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Teokset uusi = new Teokset();
     *  uusi.lueTiedostosta("testFiles/Teokset.dat");
     *  uusi.tyhjenna();
     *  uusi.getLkm() === 0;
     *  uusi.anna(2).getNimi() === "Markus"; #THROWS IndexOutOfBoundsException 
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
     * #THROWS TietoException
     *  Teokset uusi = new Teokset();
     *  Teokset toka = new Teokset();
     *  uusi.lueTiedostosta("testFiles/Teokset.dat");
     *  uusi.tallenna("testFiles/TeoksetUlos.dat");
     *  toka.lueTiedostosta("testFiles/TeoksetUlos.dat");
     *  uusi.equals(toka) === true;
     *  toka.lueTiedostosta("testFiles/TeoksetEri.dat");
     *  System.out.println(uusi.equals(toka));
     *  uusi.equals(toka) === false;
     * </pre>
     */
    public void tallenna(String tiedNimi) throws TietoException {
        try (PrintStream fo = new PrintStream(new FileOutputStream(tiedNimi))) {
            for (int i = 0; i < lkm; i++) {
                fo.println(alkiot[i].toString());
            }
        } catch (FileNotFoundException ex) {
            System.err.println(
                    "Ongelma tiedostoon kirjottaessa! " + ex.getMessage());
        }
    }


    /**
     * Lukee teosluettelon tiedostosta.
     * @param tiedNimi Tiedoston nimi, josta luetaan.
     * @throws TietoException jos tallentaminen epaonnistuu.
     * <pre name="test">
     *  #THROWS TietoException
     *  Teokset uusi = new Teokset();
     *  Teokset toka = new Teokset();
     *  uusi.lueTiedostosta("testFiles/Teokset.dat");
     *  toka.lueTiedostosta("testFiles/Teokset.dat");
     *  uusi.equals(toka) === true;
     *  toka.lueTiedostosta("testFiles/TeoksetEri.dat");
     *  uusi.equals(toka) === false;
     *  uusi.getLkm() === 3;
     * </pre>
     */
    public void lueTiedostosta(String tiedNimi) throws TietoException {
        try (Scanner fi = new Scanner(
                new FileInputStream(new File(tiedNimi)))) {
            while (fi.hasNext()) {
                String s = fi.nextLine();
                if (s.length() == 0)
                    continue;
                if (s.charAt(0) == '#')
                    continue;
                this.lisaa(s);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Ongelma tiedostoa avatessa!" + ex.getMessage());
        }
    }


    /**
     * Vertaa, onko olion sisaltamat tiedot samat kuin verratavan.
     * @param toinen Olio, jonka tietoihin verrataan
     * @return Oliko samat
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Teokset tama = new Teokset();
     *  Teokset toinen = new Teokset();
     *  tama.lueTiedostosta("testFiles/Teokset.dat");
     *  toinen.lueTiedostosta("testFiles/Teokset.dat");
     *  tama.equals(toinen) === true;
     *  toinen.lueTiedostosta("testFiles/TeoksetEri.dat");
     *  tama.equals(toinen) === false;
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
     */
    public int getLkm() {
        return this.lkm;
    }


    /**
     * Palalauttaa viitteen i:teen teosluettelon olioon.
     * @param i monennenko alkion viite halutaan
     * @return viite alkioon, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Teos anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i > lkm)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }


    /**
     * Hakee viitteen olioon, jonka id on i. 
     * @param i Id, jolla oliota haetaan
     * @return Olio, jonka id on i.
     * @throws IndexOutOfBoundsException jos id on laiton.
     */
    public Teos hae(int i) throws IndexOutOfBoundsException {
        if (i < 0)
            throw new IndexOutOfBoundsException("Laiton id: " + i);
        for (var k : alkiot) {
            if (k.getId() == i)
                return k;
        }
        return null;
    }


    /**
     * Hakee kaikki teoksen, jotka tayttavat hakuehdon.
     * @param ehto Ehto, jolla haetaan
     * @return Teos joka vastaa hakuehtoa.
     */
    public ArrayList<Teos> hae(String ehto) {
        ArrayList<Teos> tulokset = new ArrayList<Teos>();
        for (var k : alkiot) {
            if (k.getNimi().contains(ehto) || k.getTekija().contains(ehto)
                    || k.getIsbn().contains(ehto))
                tulokset.add(k);
        }
        return tulokset;
    }

    /**
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
            // TODO Auto-generated method stub
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
            // TODO Auto-generated method stub
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


    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {

        Teokset luettelo = new Teokset();
        // Teos kirja1 = new Teos();
        // Teos kirja2 = new Teos();
        // kirja1.vastaaLotr();
        // kirja2.vastaaLotrRand();
        try {
            // luettelo.lisaa(kirja1);
            // luettelo.lisaa(kirja2);
            luettelo.lueTiedostosta("testFiles/Teokset.dat");
            for (int i = 0; i < luettelo.getLkm(); i++) {
                Teos teos = luettelo.alkiot[i];
                System.out.println("Teos nro: " + i);
                teos.tulosta(System.out);
            }
            System.out.println("=============");
            System.out.println("Ulos");
            System.out.println("=============");
            luettelo.tallenna("teoksetUlos2.txt");
        } catch (TietoException e) {
            // System.out.println(e.getMessage());;
            e.printStackTrace();
        }

    }


    /**
     * Hakee ja palauttaa viitteen teokseen jolla on haettu id.
     * @param id jota haetaan
     * @return Teos, jolla on vastaava id, null jos ei loydy.
     */
    public Teos haeId(int id) {
        for (int i = 0; i < this.getLkm(); i++) {
            Teos temp = alkiot[i];
            if (temp.getId() == id) return temp;
        }
        return null;
    }

}
