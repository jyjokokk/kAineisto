package kirjasto;

import java.io.PrintStream;

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
     */
    public void lisaa(@SuppressWarnings("unused") String s) {
        // TODO: Parserit alaspain
    }
    
    
    /**
     * Lisaa uuden aineiston kokoelmaan.
     * @throws TietoException jos ilmenee ongelmia
     */
    public void lisaaLotr() throws TietoException {
        Teos teos = new Teos();
        Kategoria kat = new Kategoria();
        Hylly paikka = new Hylly();
        teos.vastaaLotrRand();
        kat.vastaaFantasiaRek();
        paikka = new Hylly(teos.getId(), kat.getKid(), "JAG", 0);
        teokset.lisaa(teos);
        kategoriat.lisaa(kat);
        hyllyt.lisaa(paikka);
    }
    
    
    /**
     * Tallentaa tietorakenteet tiedostoihinsa.
     * @throws TietoException jos ongelmia 
     */
    public void tallenna() throws TietoException {
        kategoriat.tallenna("kategoriatUlos.txt");
        teokset.tallenna("teoksetUlos.txt");
        hyllyt.tallenna("hyllytUlos.txt");
    }
    
    
    /**
     * Lukee tietorakenteet tiedostoistaan
     * @throws TietoException jos ongelma
     */
    public void lueTiedostosta() throws TietoException {
        kategoriat.lueTiedostosta("kategoriat.txt");
        teokset.lueTiedostosta("teokset.txt");
        hyllyt.lueTiedostosta("hyllyt.txt");
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
     * Palauttaa hyllypaikassa h olevan teoksen nimen.
     * @param h Hyllypaikka
     * @return Teoksen nimi.
     */
    public String getTeosNimi(Hylly h) {
        Teos t = teokset.anna(h.getId());
        return t.getNimi();
    }

    /**
     * Hakee hyllypaikkojen tietorakenteen indeksissa i olevan
     * viitteen 
     * @param i indeksi
     * @return indeksissa i oleva teos
     */
    public Hylly anna(int i) {
        return hyllyt.anna(i);
    }

    /**
     * Antaa hyllypaikassa olevan teoksen kaikki tiedot.
     * @param h hyllypaikassa oleva paikka
     * @return Teoksen kaikki tiedot merkkijonona.
     */
    public String annaTiedot(Hylly h) {
        StringBuilder sb = new StringBuilder();
        Teos t = teokset.anna(h.getId());
        Kategoria kat = kategoriat.anna(h.getKid());
        sb.append(t.toString() + "\n");
        sb.append(kat.getTiedot() + "\n");
        sb.append(h.getTiedot());
        return sb.toString();
    }
    
    
    public void tulostaTiedot(PrintStream os, Hylly h) {
        os.println(annaTiedot(h));
    }
    
    
    /**
     * @param args ei kaytossa.
     */
    public static void main(String[] args) {
        Kirjasto kirjasto = new Kirjasto();
        try {
            kirjasto.lisaaLotr();
            kirjasto.lisaaLotr();
            kirjasto.lisaaLotr();
            System.out.println(kirjasto.anna(1));
            System.out.println(kirjasto.anna(2));
            System.out.println(kirjasto.annaTiedot(kirjasto.anna(1)));
        } catch (TietoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
