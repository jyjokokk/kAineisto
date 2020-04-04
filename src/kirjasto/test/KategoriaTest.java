package kirjasto.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import kirjasto.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.04 07:37:59 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class KategoriaTest {



  // Generated by ComTest BEGIN
  /** testParse51 */
  @Test
  public void testParse51() {    // Kategoria: 51
    Kategoria uusi = new Kategoria(); 
    uusi.parse("1|Fantasia|Fantasiakirjallisuus on..."); 
    assertEquals("From: Kategoria line: 54", "1|Fantasia|Fantasiakirjallisuus on...", uusi.toString()); 
    uusi.parse("5|Scifi|BlaaBlaa"); 
    assertEquals("From: Kategoria line: 56", "5|Scifi|BlaaBlaa", uusi.toString()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testMuutaKuvausta106 */
  @Test
  public void testMuutaKuvausta106() {    // Kategoria: 106
    Kategoria uusi = new Kategoria("Fantasia", "Fantasiakirjallisuus on..."); 
    assertEquals("From: Kategoria line: 108", "0|Fantasia|Fantasiakirjallisuus on...", uusi.toString()); 
    uusi.muutaKuvausta("Uusi kuvaus"); 
    assertEquals("From: Kategoria line: 110", "0|Fantasia|Uusi kuvaus", uusi.toString()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString133 */
  @Test
  public void testToString133() {    // Kategoria: 133
    Kategoria k1 = new Kategoria(1, "Kategoria", "Esimerkki kuvauksesta"); 
    assertEquals("From: Kategoria line: 135", "1|Kategoria|Esimerkki kuvauksesta", k1.toString()); 
  } // Generated by ComTest END
}