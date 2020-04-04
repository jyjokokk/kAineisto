package kirjasto;

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
     */
    public void lisaa(@SuppressWarnings("unused") String s) {
        StringBuilder sb = new StringBuilder(s);
        String teosInfo = Mjonot.erota(sb, '#');
        String katInfo = Mjonot.erota(sb, '#');
        StringBuilder hyllyInfo = new StringBuilder();
        try {
            this.teokset.lisaa(teosInfo);
            this.kategoriat.lisaa(katInfo);
            hyllyInfo.append(Mjonot.erotaInt(teosInfo, 0) + "|");
            hyllyInfo.append(Mjonot.erotaInt(katInfo, 0) + "|");
            hyllyInfo.append(sb);
            hyllyt.lisaa(hyllyInfo.toString());
            
        } catch (TietoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    /**
     * Hakee kaikki teokset jotka vastaavat hakutermia.
     * @param ehto Hakuehto, jolla haetaan.
     * @return Lista kaikista teoksista, jotka vastaavat hakutermia.
     */
    public ArrayList<Teos> hae(String ehto) {
        return teokset.hae(ehto);
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
        kategoriat.tallenna("aineisto/kategoriatUlos.dat");
        teokset.tallenna("aineisto/teoksetUlos.dat");
        hyllyt.tallenna("aineisto/hyllytUlos.dat");
    }
    
    
    /**
     * Lukee tietorakenteet tiedostoistaan
     * @throws TietoException jos ongelma
     */
    public void lueTiedostosta() throws TietoException {
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
     * Hakee hyllypaikkojen tietorakenteen indeksissa i olevan viitteen 
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
        Teos t = teokset.hae(h.getId());
        Kategoria kat = kategoriat.hae(h.getKid());
        sb.append(t.toString() + "\n");
        sb.append(kat.getTiedot() + "\n");
        sb.append(h.getTiedot());
        return sb.toString();
    }
    
    
    /**
     * Tulostaa kirjan tiedot annettuun tulostusvirtaan.
     * @param os tulostusvirta
     * @param h Hyllypaikka, jonka tiedot tulostetaan.
     */
    public void tulostaTiedot(PrintStream os, Hylly h) {
        os.println(annaTiedot(h));
    }
    
    
    /**
     * @param args ei kaytossa.
     */
    public static void main(String[] args) {
//        Kirjasto kirjasto = new Kirjasto();
//        try {
//            kirjasto.lisaaLotr();
//            kirjasto.lisaaLotr();
//            kirjasto.lisaaLotr();
//            System.out.println(kirjasto.anna(1));
//            System.out.println(kirjasto.anna(2));
//            System.out.println(kirjasto.annaTiedot(kirjasto.anna(1)));
//        } catch (TietoException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        String s = "3|123-123-123-123|Neuromancer|William Gibson|1984#";
//        s += "4|Scifi|Tieteiskirjallisuus on...#";
//        s += "WGA|4";
//        System.out.println(s);
//        StringBuilder sb = new StringBuilder(s);
//        String teosInfo = Mjonot.erota(sb, '#');
//        String katInfo = Mjonot.erota(sb, '#');
//        System.out.println(teosInfo);
//        System.out.println(katInfo);
//        System.out.println(sb.toString());
//        StringBuilder hyllyInfo = new StringBuilder();
//        hyllyInfo.append(Mjonot.erotaInt(teosInfo, 0) + "|");
//        hyllyInfo.append(Mjonot.erotaInt(katInfo, 0) + "|");
//        hyllyInfo.append(sb);
//        System.out.println(hyllyInfo);
//        Kirjasto kirjasto = new Kirjasto();
//        String s = "31|123-123-123-123|Neuromancer|William Gibson|1984#";
//        s += "4|Scifi|Tieteiskirjallisuus on...#";
//        s += "WGA|4";
//        String s1 = "8|123-123-123-123|Neuromancer|William Gibson|1984#";
//        s1 += "4|Scifi|Tieteiskirjallisuus on...#";
//        s1 += "WGA|4";
//        try {
//            kirjasto.lueTiedostosta();
//            kirjasto.lisaa(s);
//            kirjasto.lisaa(s1);
//            ArrayList<Teos> tulokset = kirjasto.hae("William");
//            for (var t : tulokset) {
//                System.out.println(t);
//            }
//        } catch (TietoException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

}
