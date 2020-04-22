package kirjasto;

import java.util.*;
import java.io.*;

/**
 * Luokka kategorioille.
 * @author jyrki
 * @version Mar 25, 2020
 */
public class Kategoriat implements Iterable<Kategoria> {


    private final ArrayList<Kategoria> alkiot = new ArrayList<Kategoria>(0);
    

    /**
     * Perusmuodostaja
     * @example
     * <pre name="test">
     * Kategoriat kat = new Kategoriat();
     * kat.getLkm() === 0;
     * </pre>
     */
    public Kategoriat() {
        // Alustuu esittelyssa.
    }


    /**
     * Iteraattori luokalle.
     */
    @Override
    public Iterator<Kategoria> iterator() {
        return alkiot.iterator();
    }


    /**
     * Etsii, onko kategoria jo olemassa tietokannassa. Jos loytyy, palauttaa
     * viitteen tahan olioon. Jos ei, palauttaa viitteen annettuun olioon.
     * @param k Kategoria, jota etsitaan
     * @return Viite olemassa olevaan olioon, jos loytyy. Viite annettuun olioon, jos ei.
     */
    public Kategoria tarkistaDuplikaatti(Kategoria k) {
        for (var a : alkiot) {
            if (k.getNimi().equals(a.getNimi())) {
                if (k.getKuvaus().length() != 0)
                    a.muutaKuvausta(k.getKuvaus());
                return a;
            }
        }
        if (k.getKid() == 0) k.rekisteroi();
        if (k.getKuvaus().length() == 0) k.setKuvaus("Placeholder Kuvaus");
        alkiot.add(k);
        return k;
    }


    /**
     * Lisaa uuden kategorian tietokantaan, ja palauttaa sen kid:n.
     * @param k lisattava alkio
     * @return Lisatyn alkion id
     * @example
     * <pre name="test">
     *  Kategoriat kategoriat = new Kategoriat();
     *  Kategoria kat1 = new Kategoria(), kat2 = new Kategoria();
     *  kat1.parse("2|Scifi|Kuvaus|"); kat2.parse("2|Scifi|Placeholder");
     *  kategoriat.lisaa(kat1);
     *  kat2 = kategoriat.lisaa(kat2);
     *  kat2.equals(kat1);
     *  Kategoria kat3 = new Kategoria();
     *  kat3 = kategoriat.lisaa(new Kategoria("4|Kauhu|Kuvaus"));
     *  kat3.equals(kat3) === true;
     * </pre>
     */
    public Kategoria lisaa(Kategoria k) {
        Kategoria lisatty = tarkistaDuplikaatti(k);
        return lisatty;
    }


    /**
     * Alustaa uuden kategorian, ja tarkistaa onko se duplikaatti, ja lopulta
     * palauttaa joko viitteen uuteen lisattyyn kategoriaan, tai alkuperaiseen
     * duplikaattitapauksessa.
     * @param syote Merkkijono, josta parsetaan.
     * @return Viite uuteen merkkijonoon, ja vanhaan duplikaatio tapauksessa.
     * @throws TietoException jos lisaaminen epaonnistuu
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Kategoriat kategoriat = new Kategoriat();
     *  kategoriat.tyhjenna();
     *  Kategoria kat1 = new Kategoria(), kat2 = new Kategoria(), kat3 = new Kategoria();
     *  kat1.parse("2|Scifi|Kuvaus|"); kat2.parse("2|Scifi|Placeholder");
     *  kat1 = kategoriat.lisaa("1|Fantasia|Kuvaus");
     *  kat2 = kategoriat.lisaa("2|Scifi|Placeholder");
     *  kat3 = kategoriat.lisaa("3|Fantasia|Tarkempi kuvaus");
     *  kat2.toString() === "2|Scifi|Placeholder";
     *  kat1.toString() === "1|Fantasia|Tarkempi kuvaus";
     *  kat3.toString() === "1|Fantasia|Tarkempi kuvaus";
     * </pre>
     */
    public Kategoria lisaa(String syote) throws TietoException {
        return tarkistaDuplikaatti(new Kategoria(syote));
    }


    /**
     * Tyhjentaa tietorakenteen.
     * @example
     * <pre name="test">
     *  #THROWS TietoException
     *  Kategoriat kat = new Kategoriat();
     *  kat.tyhjenna();
     *  kat.lueTiedostosta("testFiles/kategoriat.dat");
     *  kat.getLkm() === 6;
     *  kat.tyhjenna();
     *  kat.getLkm() === 0;
     * </pre>
     */
    public void tyhjenna() {
        alkiot.clear();
    }


    /**
     * Palauttaa alkoiden lukumaaran kokoelmassa.
     * @return alkoiden lukumaara.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Kategoriat kat = new Kategoriat(); 
     *  kat.lueTiedostosta("testFiles/kategoriat.dat");
     *  kat.getLkm() === 6;
     * </pre>
     */
    public int getLkm() {
        return alkiot.size();
    }


    /**
     * Palauttaa viitteen alkioon indeksissa i.
     * @param i alkion indeksi
     * @throws IndexOutOfBoundsException jos indeksi ei alkioissa
     * @return viite alkioon
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Kategoriat kategoriat = new Kategoriat();
     *  kategoriat.tyhjenna();
     *  Kategoria kat1 = new Kategoria(), kat2 = new Kategoria();
     *  Kategoria kat3 = new Kategoria();
     *  kat1.parse("1|Fantasia|Kuvaus"); kat2.parse("2|Scifi|Placeholder");
     *  kat3.parse("3|Kauhu|Descriptor");
     *  kategoriat.lisaa(kat1); kategoriat.lisaa(kat2); kategoriat.lisaa(kat3); 
     *  kategoriat.anna(0) === kat1;
     *  kategoriat.anna(1) === kat2;
     *  kategoriat.anna(2) === kat3;
     * </pre>
     */
    public Kategoria anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= alkiot.size())
            throw new IndexOutOfBoundsException("Laiton indeksi.");
        return alkiot.get(i);
    }


    /**
     * Hakee viitteen alkioon, jonka kategoria-id on i.
     * @param id jota haetaan
     * @return Viite alkioon, jos loytyy. Null, jos ei.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Kategoriat kategoriat = new Kategoriat();
     *  kategoriat.tyhjenna();
     *  Kategoria kat1 = new Kategoria(), kat2 = new Kategoria(), kat3 = new Kategoria();
     *  kat1 = kategoriat.lisaa("1|Fantasia|Kuvaus");
     *  kat2 = kategoriat.lisaa("2|Scifi|Placeholder");
     *  kat3 = kategoriat.lisaa("3|Fantasia|Tarkempi kuvaus");
     *  kategoriat.haeId(1) === kat1;
     *  kategoriat.haeId(1) === kat3;
     *  kategoriat.haeId(2) === kat2;
     *  kategoriat.haeId(3) === null;
     * </pre>
     */
    public Kategoria haeId(int id) {
        for (var k : alkiot) {
            if (k.getKid() == id)
                return k;
        }
        return null;
    }


    /**
     * Tallentaa kokoelman tiedostoon.
     * @param tiedNimi Tiedoston nimi, johon tallenetaan.
     * @throws TietoException jos tallennus epaonnistuu.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Kategoriat uusi = new Kategoriat();
     *  Kategoriat toka = new Kategoriat();
     *  uusi.lueTiedostosta("testFiles/kategoriat.dat");
     *  toka.lueTiedostosta("testFiles/kategoriatEri.dat");
     *  uusi.equals(toka) === false;
     *  toka.tyhjenna();
     *  toka.lueTiedostosta("testFiles/kategoriat.dat");
     *  uusi.equals(toka) === true;
     * </pre>
     */
    public void tallenna(String tiedNimi) throws TietoException {
        File backupFile = new File(tiedNimi + ".bak");
        File saveFile = new File(tiedNimi + ".dat");
        backupFile.delete(); // IDEA: Move all .bak files to their own directory.
        saveFile.renameTo(backupFile);
        
        try (PrintWriter fo = new PrintWriter(new FileWriter(saveFile.getCanonicalPath())) ) {
            fo.println("# Kategorioiden tallenustiedosto");
            fo.println("# Viimeiksi tallennettu: " + java.time.LocalTime.now());
            for (var kat : alkiot) {
                fo.println(kat.toString());
            }
        } catch (FileNotFoundException ex) {
            throw new TietoException("Ongelma tiedosta avatessa! " + ex.getMessage());
        } catch (IOException ex) {
            throw new TietoException("Tiedoston kirjoittamisessa on ongelmia." + ex.getMessage());
        }
    }


    /**
     * Vertaa onko taman olion tiedot samat, kuin verrattavan.
     * @param toinen olio johon verrataan
     * @return Onko sisalto sama.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Kategoriat tama = new Kategoriat();
     *  Kategoriat toinen = new Kategoriat();
     *  tama.lisaa(new Kategoria());
     *  toinen.lisaa("2|Scifi|Placeholder");
     *  tama.equals(tama) === true;
     *  tama.equals(toinen) === false;
     *  toinen.tyhjenna(); tama.tyhjenna();
     *  tama.equals(toinen) === true;
     * </pre>
     */
    public boolean equals(Kategoriat toinen) {
        if (this.getLkm() != toinen.getLkm())
            return false;
        for (int i = 0; i < this.getLkm(); i++) {
            if (this.anna(i).toString().equals(toinen.anna(i).toString()))
                continue;
            return false;
        }
        return true;
    }


    /**
     * Luetaan kokoelma tiedostosta.
     * @param tiedNimi Tiedoston nimi, josta luetaan.
     * <pre name="test">
     *  #THROWS TietoException
     *  Kategoriat uusi = new Kategoriat();
     *  Kategoriat toka = new Kategoriat();
     *  uusi.lueTiedostosta("testFiles/kategoriat.dat");
     *  toka.lueTiedostosta("testFiles/kategoriat.dat");
     *  uusi.equals(toka) === true;
     *  toka.lueTiedostosta("testFiles/kategoriatEri.dat");
     *  uusi.equals(toka) === false;
     * </pre>
     */
    public void lueTiedostosta(String tiedNimi) {
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
        } catch (TietoException ex) {
            System.err.println("Ongelma tiedostoa lukiessa! " + ex.getMessage());
        }
    }


    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {

        Kategoriat kat = new Kategoriat();
        kat.lueTiedostosta("testFiles/kategoriat.dat");
        System.out.println(kat.getLkm());
        Kategoriat tama = new Kategoriat();
        Kategoriat toinen = new Kategoriat();
        tama.lueTiedostosta("testFiles/kategoriat.dat");
        toinen.lueTiedostosta("testFiles/kategoriat.dat");
        for (int i = 0; i < tama.getLkm(); i++) {
            System.out.println(tama.anna(i).toString().equals(toinen.anna(i).toString()));
        }
    }

}
