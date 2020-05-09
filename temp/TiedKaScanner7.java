package tiedosto;

import java.util.*;
import java.io.*;

/**
 * Lukujen lukeminen tiedostosta Scanner-luokkaa käyttäen.
 * Käytetään Java 1.7 try-lausetta
 * @author Vesa Lappalainen
 * @version 1.0, 03.03.2012
 */
public class TiedKaScanner7 {

    /** 
     * Luetaan tiedosto ja lasketaan siellä olevien lukujen
     * summa, keskiarvo ja määrä.  Jos tiedoston nimeä ei
     * anneta, se on luvut.dat
     * @param args tiedoston nimi 
     * @example
     * <pre name="test">
     * #THROWS IOException
     * #import java.io.IOException;
     * #import fi.jyu.mit.ohj2.Suuntaaja;
     * #import fi.jyu.mit.ohj2.VertaaTiedosto;
     * String tiednimi = "tiedka7koe.txt";
     * VertaaTiedosto.kirjoitaTiedosto(tiednimi,"33\n11\nkissa\n5");
     * Suuntaaja.StringOutput so = new Suuntaaja.StringOutput();
     * main(new String[]{tiednimi});
     * so.palauta();
     * String tulos = "Lukuja oli 3 kappaletta.\n"+
     *                "Niiden summa oli 49.00\n"+
     *                "ja keskiarvo oli 16.33\n";  
     * so.toString() =R= "(?s)"+tulos.replaceAll("\n","\\\\s*");
     * so.ero(tulos) === null; // yksinkertaisempi kuin edellä
     * VertaaTiedosto.tuhoaTiedosto(tiednimi);
     * Suuntaaja.StringOutput se = new Suuntaaja.StringOutput(true);
     * main(new String[]{"xxx.xxx"});
     * se.palauta();
     * // se.ero("Tiedosto ei aukea! xxx.xxx (The system cannot find the file specified)\n") === null;
     * se.toString() =R= "(?s)"+"Tiedosto ei aukea! xxx\\.xxx .*";
     * </pre>
     */
    public static void main(String[] args) {
        double summa = 0;
        int n = 0;
        String tiedNimi = "luvut.dat";
        if (args.length > 0 ) tiedNimi = args[0];

        try (Scanner fi = new Scanner(new FileInputStream(new File(tiedNimi)))) { // Jotta UTF8/ISO-8859 toimii
            while ( fi.hasNext() ) {
                try {
                    String s = fi.nextLine();
                    double luku = Double.parseDouble(s);
                    summa += luku;
                    n++;
                } catch (NumberFormatException ex) {
                    // Hylätään
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Tiedosto ei aukea! " + ex.getMessage());
            return;
        }

        double ka = 0;
        if (n > 0) ka = summa / n;
        System.out.printf("Lukuja oli %d kappaletta.%n",n);
        System.out.printf("Niiden summa oli %4.2f%n",summa);
        System.out.printf("ja keskiarvo oli %4.2f%n",ka);
    }
}
