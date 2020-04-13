package kirjasto;

import java.io.*;

/**
 * Hyllyten kokoelma, jokaa osaa mm lisata ja poistaa teoksen.
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
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Hyllyt hyllyt = new Hyllyt();
     *  Hylly h1 = new Hylly(), h2 = new Hylly(), h3 = new Hylly();
     *  hyllyt.lisaa(h1); hyllyt.lisaa(h2); hyllyt.lisaa(h3);
     *  hyllyt.getLkm() === 3;
     * </pre>
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
     * Etsii alkion tietorakenteesta id:n perusteella, ja korvaa sen.
     * Jos alkiota ei loydy, lisataan se tietorakenteeseen.
     * @param alkio jota lisataan
     * @return viiteo muutettuun tai lisattyyn alkioon
     * @throws TietoException jos lisaamisessa on ongelmia.
     */
    public Hylly lisaaTaiMuuta(Hylly alkio) throws TietoException {
        for (int i=0; i <= lkm; i++) {
            if (alkiot[i].getId() == alkio.getId()) {
                alkiot[i] = alkio;
                return alkiot[i];
            }
        }
        lisaa(alkio);
        return alkio;
    }


    /**
     * Tyhjentaa tietorakenteen alkioista.
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Hyllyt hyl = new Hyllyt();
     *  hyl.lisaa(new Hylly());
     *  hyl.lisaa(new Hylly());
     *  hyl.lisaa(new Hylly());
     *  hyl.getLkm() === 3;
     *  hyl.tyhjenna();
     *  hyl.getLkm() === 0;
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
     * @example
     * <pre name="test">
     * #THROWS TietoException
     *  Hyllyt hyl = new Hyllyt();
     *  hyl.lisaa("1|2|GWB|0"); hyl.lisaa("2|1|ABC|0");
     *  hyl.getLkm() === 2;
     *  hyl.anna(0).toString() === "1|2|GWB|0";
     *  hyl.haeId(2).toString() === "2|1|ABC|0";
     * </pre>
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
        if (this.getLkm() != comp.getLkm()) return false;
        for (int i = 0; i < this.getLkm(); i++) {
            if (this.anna(i).toString().equals(comp.anna(i).toString())) 
                continue;
            return false;
        }
        return true;
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
     *  uusi.lueTiedostosta("testFiles/hyllyt.dat");
     *  uusi.tallenna("testFiles/hyllytUlos.dat");
     *  toka.lueTiedostosta("testFiles/hyllytUlos.dat");
     *  uusi.equals(toka) === true;
     *  toka.lueTiedostosta("testFiles/hyllytEri.dat");
     *  uusi.equals(toka) === false;
     * </pre>
     */
    public void tallenna(String tiedNimi) throws TietoException {
        File backupFile = new File(tiedNimi + ".bak");
        File saveFile = new File(tiedNimi + ".dat");
        backupFile.delete();
        saveFile.renameTo(backupFile);
 

        try (PrintWriter fo = new PrintWriter(new FileWriter(saveFile.getCanonicalPath())) ) {
            fo.println("# Teosten tallenustiedosto");
            fo.println("# Viimeksi tallennettu: " + java.time.LocalTime.now());
            for (int i = 0; i < lkm; i++) {
                Hylly hylly = alkiot[i];
                fo.println(hylly.toString());
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
     *  uusi.lueTiedostosta("testFiles/hyllyt.dat");
     *  toka.lueTiedostosta("testFiles/hyllyt.dat");
     *  uusi.equals(toka) === true;
     *  toka.lueTiedostosta("testFiles/hyllytEri.dat");
     *  uusi.equals(toka) === false;
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
        } catch (IOException e) {
            throw new TietoException(e.getMessage());
        }
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
        if (i < 0)
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
     * Etsii ja palauttaa olion indeksin tietorakenteessa
     * id:n avulla
     * @param id mita etsitaan
     * @return paikka tietorakenteessa.
     */
    public int haeIx(int id) {
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getId() == id) return i;
        }
        return id;
        
    }


    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {

        Hyllyt hyl = new Hyllyt();
        try {
            hyl.lueTiedostosta("testFiles/hyllyt");
        } catch (TietoException e) {
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

    }
}
