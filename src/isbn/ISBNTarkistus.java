package isbn;

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
        String s = arvoIsbn();
        System.out.println(s);
    
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
