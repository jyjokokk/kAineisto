package isbn.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import static isbn.ISBNTarkistus.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.23 03:05:15 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class ISBNTarkistusTest {


  // Generated by ComTest BEGIN
  /** testTarkistaIsbn44 */
  @Test
  public void testTarkistaIsbn44() {    // ISBNTarkistus: 44
    String is1 = "2-31-64533-432343-7"; 
    String is2 = "22132-31-64533-432343-7"; 
    assertEquals("From: ISBNTarkistus line: 47", true, tarkistaIsbn(is1)); 
    assertEquals("From: ISBNTarkistus line: 48", false, tarkistaIsbn(is2)); 
  } // Generated by ComTest END
}