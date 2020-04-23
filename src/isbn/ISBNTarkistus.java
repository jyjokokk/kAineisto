package isbn;

import java.util.regex.*;

/**
 * Luokka, jolla voidaan luoda satunnainen ISBN numbero, ja tarkistaa
 * ISBN numeron oikeellisuus.
 * @author jyrki
 * @version Mar 4, 2020
 */
public class ISBNTarkistus {

    /**
     * @param args ei kaytossa.
     */
    public static void main(String[] args) {
//        String s = arvoIsbn();
//        System.out.println(s);
        String is = "23231233-42-12354-64322-2";
        if (tarkistaIsbn(is)) System.out.println("Toimii!");
        else System.out.println("Ei toimi");
    }
    
    /**
     * Arpoo sattumanvaraisen pseudo ISBN-numeron.
     * @return ISBN numero.
     */
    public static String arvoIsbn() {
        StringBuilder sb = new StringBuilder();
        sb.append(rand(1, 199) + "-");
        sb.append(rand(1, 99) + "-");
        sb.append(rand(1, 9999999) + "-");
        sb.append(rand(1, 999999) + "-");
        sb.append(rand(1, 9));
        return sb.toString();
    }
    
    
    /**
     * Tarkistaa vastaako ISBN oiekaa formaattia.
     * @param isbn joka tarkisteaan
     * @return true tai false.
     * @example
     * <pre name="test">
     *  String is1 = "2-31-64533-432343-7";
     *  String is2 = "22132-31-64533-432343-7";
     *  tarkistaIsbn(is1) === true;
     *  tarkistaIsbn(is2) === false;
     * </pre>
     */
    public static boolean tarkistaIsbn(String isbn) {
        String pattern = "\\d{1,3}-\\d{1,2}-\\d{1,7}-\\d{1,6}-\\d{1}";
//        Pattern re = Pattern.compile(pattern);
        if (Pattern.matches(pattern, isbn)) return true;
        return false;
    }
    
    /**
     * Arvotaan satunnainen kokonaisluku välille [ala,yla]
     * @param ala arvonnan alaraja
     * @param yla arvonnan yläraja
     * @return satunnainen luku väliltä [ala,yla]
     */
    public static int rand(int ala, int yla) {
      double n = (yla-ala)*Math.random() + ala;
      return (int)Math.round(n);
    }


}
