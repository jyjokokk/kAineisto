package kirjasto.test;
// Generated by ComTest BEGIN
import java.io.ByteArrayOutputStream;
import static org.junit.Assert.*;
import org.junit.*;
import kirjasto.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.06 12:10:49 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class KategoriaTest {



  // Generated by ComTest BEGIN
  /** testParse52 */
  @Test
  public void testParse52() {    // Kategoria: 52
    Kategoria uusi = new Kategoria(); 
    uusi.parse("1|Fantasia|Fantasiakirjallisuus on..."); 
    assertEquals("From: Kategoria line: 55", "1|Fantasia|Fantasiakirjallisuus on...", uusi.toString()); 
    uusi.parse("5|Scifi|BlaaBlaa"); 
    assertEquals("From: Kategoria line: 57", "5|Scifi|BlaaBlaa", uusi.toString()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testMuutaKuvausta107 */
  @Test
  public void testMuutaKuvausta107() {    // Kategoria: 107
    Kategoria uusi = new Kategoria("Fantasia", "Fantasiakirjallisuus on..."); 
    assertEquals("From: Kategoria line: 109", "0|Fantasia|Fantasiakirjallisuus on...", uusi.toString()); 
    uusi.muutaKuvausta("Uusi kuvaus"); 
    assertEquals("From: Kategoria line: 111", "0|Fantasia|Uusi kuvaus", uusi.toString()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testRekisteroi123 */
  @Test
  public void testRekisteroi123() {    // Kategoria: 123
    Kategoria kat1 = new Kategoria(); 
    Kategoria kat2 = new Kategoria(); 
    Kategoria kat3 = new Kategoria(); 
    kat1.rekisteroi(); kat2.rekisteroi(); kat3.rekisteroi(); 
    int kid1 = kat1.getKid(); 
    assertEquals("From: Kategoria line: 129", kid1, kat1.getKid()); 
    assertEquals("From: Kategoria line: 130", kid1 + 1, kat2.getKid()); 
    assertEquals("From: Kategoria line: 131", kid1 + 2, kat3.getKid()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString145 */
  @Test
  public void testToString145() {    // Kategoria: 145
    Kategoria k1 = new Kategoria(1, "Kategoria", "Esimerkki kuvauksesta"); 
    assertEquals("From: Kategoria line: 147", "1|Kategoria|Esimerkki kuvauksesta", k1.toString()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testTulosta178 */
  @Test
  public void testTulosta178() {    // Kategoria: 178
    ByteArrayOutputStream out = new ByteArrayOutputStream(); 
    Kategoria kat1 = new Kategoria("2|Scifi|Scifi on..."); 
    Kategoria kat2 = new Kategoria(); 
    kat1.tulosta(out); 
    kat2.tulosta(out); 
    assertEquals("From: Kategoria line: 185", "2|Scifi|Scifi on...\n0||\n", out.toString()); 
  } // Generated by ComTest END
}