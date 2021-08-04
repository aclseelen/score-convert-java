package eu.acls.scoreconvert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteConvertTest {

  @BeforeEach
  void setUp() {
  }

  @Test
  void lilyToTs() {
    assertEquals("22 6", NoteConvert.lilyToTs("cisis"));
    assertEquals("24 10", NoteConvert.lilyToTs("bes"));
    assertEquals("25 10", NoteConvert.lilyToTs("c'"));
    assertEquals("10 4", NoteConvert.lilyToTs("c,,"));
    assertEquals("29 11", NoteConvert.lilyToTs("a'"));
    assertEquals("25 10 4", NoteConvert.lilyToTs("c'4"));
    assertEquals("25 10 4 8", NoteConvert.lilyToTs("c'4."));
    assertEquals("25 10 4 8 16", NoteConvert.lilyToTs("c'4.."));
    assertEquals("25 10 4 8 16 32", NoteConvert.lilyToTs("c'4..."));
    assertEquals("100 100 4", NoteConvert.lilyToTs("r4"));
  }

  @Test
  void isPowerOfTwo() {
    assertTrue(NoteConvert.isPowerOfTwo(1));
    assertTrue(NoteConvert.isPowerOfTwo(2));
    assertTrue(NoteConvert.isPowerOfTwo(4));
    assertTrue(NoteConvert.isPowerOfTwo(8));
    assertTrue(NoteConvert.isPowerOfTwo(16));
    assertTrue(NoteConvert.isPowerOfTwo(32));
    assertTrue(NoteConvert.isPowerOfTwo(64));

    assertFalse(NoteConvert.isPowerOfTwo(0));
    assertFalse(NoteConvert.isPowerOfTwo(3));
    assertFalse(NoteConvert.isPowerOfTwo(5));
    assertFalse(NoteConvert.isPowerOfTwo(6));
    assertFalse(NoteConvert.isPowerOfTwo(7));
    assertFalse(NoteConvert.isPowerOfTwo(9));
    assertFalse(NoteConvert.isPowerOfTwo(10));
    assertFalse(NoteConvert.isPowerOfTwo(11));
    assertFalse(NoteConvert.isPowerOfTwo(12));
    assertFalse(NoteConvert.isPowerOfTwo(13));
    assertFalse(NoteConvert.isPowerOfTwo(14));
    assertFalse(NoteConvert.isPowerOfTwo(15));
    assertFalse(NoteConvert.isPowerOfTwo(17));
    assertFalse(NoteConvert.isPowerOfTwo(18));
    assertFalse(NoteConvert.isPowerOfTwo(20));
    assertFalse(NoteConvert.isPowerOfTwo(63));
    assertFalse(NoteConvert.isPowerOfTwo(125));
  }
}