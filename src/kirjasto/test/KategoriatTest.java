package kirjasto.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import kirjasto.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.04 08:43:40 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class KategoriatTest {



  // Generated by ComTest BEGIN
  /** 
   * testTyhjenna47 
   * @throws TietoException when error
   */
  @Test
  public void testTyhjenna47() throws TietoException {    // Kategoriat: 47
    Kategoriat kat = new Kategoriat(); 
    kat.lueTiedostosta("testFiles/Kategoriat.dat"); 
    assertEquals("From: Kategoriat line: 51", 5, kat.getLkm()); 
    kat.tyhjenna(); 
    assertEquals("From: Kategoriat line: 53", 0, kat.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testTallenna130 
   * @throws TietoException when error
   */
  @Test
  public void testTallenna130() throws TietoException {    // Kategoriat: 130
    Kategoriat uusi = new Kategoriat(); 
    Kategoriat toka = new Kategoriat(); 
    uusi.lueTiedostosta("testFiles/Kategoriat.dat"); 
    uusi.tallenna("testFiles/KategoriatUlos.dat"); 
    toka.lueTiedostosta("testFiles/KategoriatUlos.dat"); 
    assertEquals("From: Kategoriat line: 137", true, uusi.equals(toka)); 
    toka.lueTiedostosta("testFiles/KategoriatEri.dat"); 
    System.out.println(uusi.equals(toka)); 
    assertEquals("From: Kategoriat line: 140", true, uusi.equals(toka)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta176 
   * @throws TietoException when error
   */
  @Test
  public void testLueTiedostosta176() throws TietoException {    // Kategoriat: 176
    Kategoriat uusi = new Kategoriat(); 
    Kategoriat toka = new Kategoriat(); 
    uusi.lueTiedostosta("testFiles/Kategoriat.dat"); 
    toka.lueTiedostosta("testFiles/Kategoriat.dat"); 
    assertEquals("From: Kategoriat line: 182", true, uusi.equals(toka)); 
    toka.lueTiedostosta("testFiles/KategoriatEri.dat"); 
    assertEquals("From: Kategoriat line: 184", true, uusi.equals(toka)); 
  } // Generated by ComTest END
}