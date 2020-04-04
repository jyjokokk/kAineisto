package kirjasto;

import java.io.*;
//import fi.jyu.mit.ohj2.*;

/**
 * Hyllyten kokoelma, jokaa osaa mm lisata ja poistaa teoksen.
 * TODO: Selvita onnistuisiko yksittaiset sailomisluokat joka tyypille korvata <TYPE> tyylisella ratkaisulla.
 * TODO: Toimisi varmaan todella paljon paremmin listalla toteutettuna.
 * @author jyrki
 * @version Mar 25, 2020
 */
public class Hyllyt {

    private static final int MAX_PAIKKOJA = 10;
    private int lkm = 0;
    private Hylly alkiot[] = new Hylly[MAX_PAIKKOJA];

    /**
     * Vakiomuodostaja.
     */
    public Hyllyt() {
        // Alustettu esittelyssa.
    }


    /**
     * Lisaa uuden alkion tietorakenteeseen.
     * @param alkio joka lisataan.
     * @throws TietoException jos taulukossa ei tilaa.
     */
    public void lisaa(Hylly alkio) throws TietoException {
        if (lkm >= alkiot.length) {
            Hylly temp[] = new Hylly[alkiot.length + MAX_PAIKKOJA];
            for (int i = 0; i < lkm; i++) {
                temp[i] = alkiot[i];
            }
            alkiot = temp;
        }
        alkiot[lkm] = alkio;
        lkm++;
    }


    /**
     * Tyhjentaa tietorakenteen alkioista.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Hyllyt hyl = new Hyllyt();
     *  hyl.lueTiedostosta("testFiles/Hyllyt.dat");
     *  hyl.getLkm() === 4;
     *  hyl.tyhjenna();
     *  hyl.getLkm() === 0;
     *  hyl.anna(4); #THROWS IndexOutOfBoundsException
     * </pre>
     */
    public void tyhjenna() {
        this.alkiot = new Hylly[MAX_PAIKKOJA];
        this.lkm = 0;
    }


    /**
     * Lisaa taulukkoon uuden alkion, ja alustaa sen arvot syotetysta
     * merkkijonosta.
     * @param syote merkkijono, josta arvot alustetaan
     */
    public void lisaa(String syote) {
        if (lkm >= alkiot.length) {
            Hylly temp[] = new Hylly[alkiot.length + 5];
            for (int i = 0; i < lkm; i++) {
                temp[i] = alkiot[i];
            }
            alkiot = temp;
        }
        alkiot[lkm] = new Hylly(syote);
        lkm++;
    }


    /**
     * Vertaa onko taman olion tiedot samat kuin toisen.
     * @param comp verrattava olio
     * @return True jos sama, false jos ei.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Hyllyt tama = new Hyllyt();
     *  Hyllyt toinen = new Hyllyt();
     *  tama.lisaa(new Hylly("1|1|JAG|2")); toinen.lisaa(new Hylly("1|1|JAG|2"));
     *  tama.equals(toinen) === true;
     * </pre>
     */
    public boolean equals(Hyllyt comp) {
        String tama = "";
        for (int i = 0; i < lkm; i++) {
            tama += alkiot[i].toString();
        }
        String toinen = "";
        for (int i = 0; i < comp.getLkm(); i++) {
            toinen += comp.anna(i).toString();
        }
        if (tama.equals(toinen))
            return true;
        return false;
    }


    /**
     * Tallentaa teosluettelon tiedostoon.
     * @param tiedNimi Tiedoston nimi, johon tallenetaan.
     * @throws TietoException jos tallentaminen epaonnistuu.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Hyllyt uusi = new Hyllyt();
     *  Hyllyt toka = new Hyllyt();
     *  uusi.lueTiedostosta("testFiles/Hyllyt.dat");
     *  uusi.tallenna("testFiles/HyllytUlos.dat");
     *  toka.lueTiedostosta("testFiles/HyllytUlos.dat");
     *  uusi.equals(toka) === true;
     *  toka.lueTiedostosta("testFiles/HyllytEri.dat");
     *  System.out.println(uusi.equals(toka));
     *  uusi.equals(toka) === false;
     * </pre>
     */
    public void tallenna(String tiedNimi) throws TietoException {
        File backupFile = new File("backup_" + tiedNimi);
        File saveFile = new File(tiedNimi);
        backupFile.delete();
        saveFile.renameTo(backupFile);
        
        try (PrintWriter fo = new PrintWriter(new FileWriter(saveFile.getCanonicalPath())) ) {
            fo.println("# Teosten tallenustiedosto");
            for (var teos : alkiot) {
                fo.println(teos.toString());
            }
        } catch (FileNotFoundException ex) {
            throw new TietoException("Ongelma tiedosta avatessa! " + ex.getMessage());
        } catch (IOException ex) {
            throw new TietoException("Tiedoston kirjoittamisessa on ongelmia." + ex.getMessage());
        }
    
    }


    /**
     * Lukee teosluettelon tiedostosta.
     * @param tiedNimi tiedoston nimi, joka luetaan
     * @throws TietoException jos onglemia
     * @example
     * <pre name="test">
     *  #THROWS TietoException
     *  Hyllyt uusi = new Hyllyt();
     *  Hyllyt toka = new Hyllyt();
     *  uusi.lueTiedostosta("testFiles/Hyllyt.dat");
     *  toka.lueTiedostosta("testFiles/Hyllyt.dat");
     *  uusi.equals(toka) === true;
     *  toka.lueTiedostosta("testFiles/HyllytEri.dat");
     *  uusi.equals(toka) === false;
     *  uusi.getLkm() === 4;
     *  uusi.anna(0).toString() === "1|1|JAG|2";
     * </pre>
     */
    public void lueTiedostosta(String tiedNimi) throws TietoException {
        try ( BufferedReader fi = new BufferedReader(new FileReader(tiedNimi))) {
            String rivi = "";
            while ( (rivi = fi.readLine()) != null ) {
                if ("".equals(rivi) || rivi.charAt(0) == '#') continue;
                Hylly paikka = new Hylly();
                paikka.parse(rivi);
                lisaa(paikka);
            }
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        try (Scanner fi = new Scanner(
//                new FileInputStream(new File(tiedNimi)))) {
//            while (fi.hasNext()) {
//                String s = fi.nextLine();
//                if (s.length() == 0)
//                    continue;
//                if (s.charAt(0) == '#')
//                    continue;
//                this.lisaa(s);
//            }
//        } catch (FileNotFoundException ex) {
//            System.err.println("Ongelma tiedostoa avatessa!" + ex.getMessage());
//        }

    }


    /**
     * Palauttaa teosten lukumaaran.
     * @return Hyllyten lukumaara.
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
    public Hylly anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm < i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }


    /**
     * Etsii ja palauttaa viitteen hyllypaikkaan, jolla on haettu id.
     * Palauttaa null, jos ei loydy teosta haetulla id:lla.
     * @param id Id, jota haetaan.
     * @return viite hyllypaikkaan, null jos ei loydy.
     * @throws TietoException jos ei loydy
     */
    public Hylly haeId(int id) throws TietoException {
        for (int i = 0; i < lkm; i++) {
            Hylly temp = alkiot[i];
            if (temp.getId() == id)
                return temp;
        }
        throw new TietoException(
                String.format("Hyllypaikkaa id:lla '%d' ei loydy!", id));
    }


    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {

        Hyllyt hyl = new Hyllyt();
        try {
            hyl.lueTiedostosta("testFiles/Hyllyt.dat");
        } catch (TietoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (var h : hyl.alkiot) {
            System.out.println(h);
        }

        for (int i = 0; i < hyl.getLkm(); i++) {
            Hylly temp = hyl.anna(i);
            if (temp.getId() == 4)
                System.out.println(temp);
        }

        // Hyllyt luettelo = new Hyllyt();
        // try {
        // System.out.println("===================");
        // System.out.println("Luetaan tiedostosta");
        // System.out.println("===================");
        // luettelo.lueTiedostosta("hyllyt.txt");
        // for (int i = 0; i < luettelo.getLkm(); i++) {
        // Hylly teos = luettelo.anna(i);
        // System.out.println("Hylly nro: " + i);
        // teos.tulosta(System.out);
        // }
        // luettelo.tallenna("hyllytUlosTest.txt");
        // } catch (TietoException e) {
        // e.printStackTrace();
        // }

    }
}
