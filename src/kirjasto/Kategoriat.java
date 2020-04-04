package kirjasto;

import java.util.*;
import java.io.*;

/**
 * Luokka kategorioille.
 * @author jyrki
 * @version Mar 25, 2020
 * TODO: Yleisen toiminallisuuden kannalta, TreeMap voisi olla parempi tietorakenne ArrayList.
 */
public class Kategoriat implements Iterable<Kategoria> {

    private static final ArrayList<Kategoria> alkiot = new ArrayList<Kategoria>(0);

    /**
     * Perusmuodostaja
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
     * Lisaa uuden kategorian tietokantaan, ja palauttaa sen kid:n.
     * @param k lisattava alkio
     * @return Lisatyn alkion id
     */
    public Kategoria lisaa(Kategoria k) {
        Kategoria lisatty = tarkistaDuplikaatti(k);
        return lisatty;
    }
    
    
    /**
     * Etsii, onko kategoria jo olemassa tietokannassa. Jos loytyy, palauttaa
     * viitteen tahan olioon. Jos ei, palauttaa viitteen annettuun olioon.
     * @param k Kategoria, jota etsitaan
     * @return Viite olemassa olevaan olioon, jos loytyy. Viite annettuun olioon, jos ei.
     */
    public Kategoria tarkistaDuplikaatti(Kategoria k) {
        for (var a: alkiot) {
            if (k.getNimi().equals(a.getNimi())) {
                if (k.getKuvaus() != "") a.muutaKuvausta(k.getKuvaus());
                return a;
            }
        }
        k.rekisteroi();
        alkiot.add(k);
        return k;
    }


    /**
     * Lisaa uuden olion taulukkoon, ja alustaa sen arvot
     * syotetysta merkkijonosta seulomalla
     * @param syote Merkkijono, josta parsetaan.
     */
    public void lisaa(String syote) {
        Kategoria uusi = new Kategoria(syote);
        this.lisaa(uusi);
    }


    /**
     * Palauttaa alkoiden lukumaaran kokoelmassa.
     * @return alkoiden lukumaara.
     */
    public int getLkm() {
        return alkiot.size();
    }


    /**
     * Palauttaa viitteen alkioon indeksissa i.
     * @param i alkion indeksi
     * @throws IndexOutOfBoundsException jos indeksi ei alkioissa
     * @return viite alkioon
     */
    public Kategoria anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= alkiot.size()) throw new IndexOutOfBoundsException("Laiton indeksi.");
        return alkiot.get(i);
    }
    
    
    /**
     * Hakee viitteen alkioon, jonka kategoria id on i.
     * @param i id jota haetaan
     * @return Viite alkioon, jos loytyy. Null, jos ei.
     */
    public Kategoria hae(int i) {
        for (var k : alkiot) {
            if (k.getKid() == i) return k;
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
     *  uusi.lueTiedostosta("testFiles/Kategoriat.dat");
     *  uusi.tallenna("testFiles/KategoriatUlos.dat");
     *  toka.lueTiedostosta("testFiles/KategoriatUlos.dat");
     *  uusi.equals(toka) === true;
     *  toka.lueTiedostosta("testFiles/KategoriatEri.dat");
     *  System.out.println(uusi.equals(toka));
     *  uusi.equals(toka) === false;
     * </pre>
     */
    public void tallenna(String tiedNimi) throws TietoException {
        try (PrintStream fo = new PrintStream(new FileOutputStream(tiedNimi))) {
            for (Kategoria kat : alkiot) {
                fo.println(kat.toString());
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Ongelma tallentaessa tiedostoon! " + ex.getMessage());
        }
    }



    /**
     * Vertaa onko taman olion tiedot samat, kuin verrattavan.
     * @param toinen olio johon verrataan
     * @return Onko sisalto sama.
     */
    public boolean equals(Kategoriat toinen) {
        String tama = "";
        String toka = "";
        for (int i = 0; i < this.getLkm(); i++) {
            tama += this.anna(i).toString();
        }
        for (int i = 0; i < toinen.getLkm(); i++) {
            toka += toinen.anna(i).toString();
        }
        if (tama.equals(toka)) return true;
        return false;
    }


    /**
     * Luetaan kokoelma tiedostosta.
     * @param tiedNimi Tiedoston nimi, josta luetaan.
     * @throws TietoException jos lukeminen epaonnistuu.
     * <pre name="test">
     *  #THROWS TietoException
     *  Kategoriat uusi = new Kategoriat();
     *  Kategoriat toka = new Kategoriat();
     *  uusi.lueTiedostosta("testFiles/testKategoriat.dat");
     *  toka.lueTiedostosta("testFiles/testKategoriat.dat");
     *  uusi.equals(toka) === true;
     *  toka.lueTiedostosta("testFiles/testKategoriatEri.dat");
     *  uusi.equals(toka) === false;
     * </pre>
     */
    public void lueTiedostosta(String tiedNimi) throws TietoException {
        try (Scanner fi = new Scanner (new FileInputStream(new File(tiedNimi)))) {
            while (fi.hasNext()) {
                String s = fi.nextLine();
                if (s.length() == 0) continue;
                if (s.charAt(0) == '#') continue;
                this.lisaa(s);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Ongelma tiedostoa avatessa!" + ex.getMessage());
        }
    }


    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Kategoriat kategoriat = new Kategoriat();
        try {
//            kategoriat.lueTiedostosta("testFiles/Kategoriat.dat");
            kategoriat.lueTiedostosta("aineisto/kategoriat.dat");
        } catch (TietoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (var k : alkiot) {
            System.out.println(k);
        }
//        Kategoria fantasia = new Kategoria();
//        Kategoria fantasia2 = new Kategoria("Fantasia", "Duplikaatti");
//        Kategoria scifi = new Kategoria("Scifi", "Scifi on...");
//        fantasia.vastaaFantasiaRek();
//        scifi.rekisteroi();
//        fantasia2.rekisteroi();
//        kategoriat.lisaa(fantasia);
//        System.out.println(kategoriat.anna(0));
//        Kategoria viite = kategoriat.lisaa(fantasia2);
//        System.out.println(viite);
//        kategoriat.lisaa(scifi);
//        System.out.println(kategoriat.tarkistaDuplikaatti(new Kategoria("Scifi", "Duplikaatti...")));
//
//        try {
//            // Luetaan tiedostosta
//            kategoriat.lueTiedostosta("testFiles/Kategoriat.dat");
//            for (Kategoria k: kategoriat) {
//                System.out.println(k);
//            }
//            // Tallenetaan tiedostoon
//            kategoriat.tallenna("testFiles/KategoriatUlos.dat");
//        } catch (TietoException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        Kategoriat uusi = new Kategoriat();
//        Kategoriat toka = new Kategoriat();
//        try {
//            uusi.lueTiedostosta("testFiles/Kategoriat.dat");
//            uusi.tallenna("testFiles/KategoriatUlos.dat");
//            toka.lueTiedostosta("testFiles/KategoriatUlos.dat");
//            System.out.println(uusi.equals(toka));
////            toka.lueTiedostosta("testFiles/testKategoriatEri.dat");
//            toka.lueTiedostosta("testFiles/KategoriatEri.dat");
//            System.out.println(uusi.equals(toka));
//        } catch (TietoException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        //        Kategoria scifi2 = new Kategoria("Scifi", "Scifi on...");
//        //        kategoriat.lisaa(scifi2);

    }

}
