package eu.acls.scoreconvert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoteConvertTest {

  @BeforeEach
  void setUp() {
  }

  @Test
  void lilyToTs_String_String() {
    assertEquals("22 6", NoteConvert.lilyToTs("cisis"));
    assertEquals("24 10", NoteConvert.lilyToTs("bes"));
    assertEquals("25 10", NoteConvert.lilyToTs("c'"));
    assertEquals("10 4", NoteConvert.lilyToTs("c,,"));
    assertEquals("29 11", NoteConvert.lilyToTs("a'"));
    assertEquals("25 10 4", NoteConvert.lilyToTs("c'4"));
    assertEquals("25 10 16", NoteConvert.lilyToTs("c'16"));
    assertEquals("25 10 4 8", NoteConvert.lilyToTs("c'4."));
    assertEquals("25 10 4 8 16", NoteConvert.lilyToTs("c'4.."));
    assertEquals("25 10 4 8 16 32", NoteConvert.lilyToTs("c'4..."));
    assertEquals("100 100 4", NoteConvert.lilyToTs("r4"));
  }

  @Test
  void lilyToTs_Lily_Ts_abs() {
    LilyValue lilyValue = new LilyValue();
    lilyValue.setLetters("cisis");
    TsValue tsValue = new TsValue(22, 6);
    assertEquals(tsValue.toString(), NoteConvert.lilyToTs(lilyValue).toString());

    lilyValue = new LilyValue();
    lilyValue.setLetters("c");
    lilyValue.setOctaveIndication("'");
    lilyValue.setNumbers("4");
    lilyValue.setDots(".");
    tsValue = new TsValue(25, 10);
    tsValue.setNoteLenList(Arrays.asList(4, 8));
    assertEquals(tsValue.toString(), NoteConvert.lilyToTs(lilyValue).toString());
  }

  @Test
  void lilyToTs_Lily_Ts_rel() {
    LilyValue lilyValue = new LilyValue();
    lilyValue.setLetters("d");
    lilyValue.setNumbers("16");
    TsValue tsValue = new TsValue(26, 10);
    tsValue.setNoteLenList(List.of(16));
    TsValue reference = new TsValue(25, 10);
    assertEquals(tsValue.toString(), NoteConvert.lilyToTs(lilyValue, reference).toString());

    lilyValue = new LilyValue();
    lilyValue.setLetters("g");
    lilyValue.setNumbers("8");
    lilyValue.setOctaveIndication("'");
    tsValue = new TsValue(28, 11);
    tsValue.setNoteLenList(List.of(8));
    reference = new TsValue(25, 10);
    assertEquals(tsValue.toString(), NoteConvert.lilyToTs(lilyValue, reference).toString());

    lilyValue = new LilyValue();
    lilyValue.setLetters("g");
    lilyValue.setNumbers("8");
    tsValue = new TsValue(8, 3);
    tsValue.setNoteLenList(List.of(8));
    reference = new TsValue(10, 4);
    assertEquals(tsValue.toString(), NoteConvert.lilyToTs(lilyValue, reference).toString());
  }

  @Test
  void stringToLily_syncopation() {
    LilyValue lilyValue;

    lilyValue = NoteConvert.stringToLilyValue("c''4.~");
    assertEquals("c", lilyValue.getLetters());
    assertEquals("''", lilyValue.getOctaveIndication());
    assertEquals("4", lilyValue.getNumbers());
    assertEquals(".", lilyValue.getDots());
    assertTrue(lilyValue.hasSyncopationTilde());

    lilyValue = NoteConvert.stringToLilyValue("beseses~");
    assertEquals("beseses", lilyValue.getLetters());
    assertEquals("", lilyValue.getOctaveIndication());
    assertEquals("", lilyValue.getNumbers());
    assertEquals("", lilyValue.getDots());
    assertTrue(lilyValue.hasSyncopationTilde());
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

  @Test
  void sumNoteLengths() {
    assertEquals(Arrays.asList(2, 4), NoteConvert.sumNoteLengths(Arrays.asList(4, 8), Arrays.asList(4, 8)));
    assertEquals(Arrays.asList(2, 16, 32), NoteConvert.sumNoteLengths(Arrays.asList(4, 8, 16), Arrays.asList(8, 32)));
    assertEquals(Arrays.asList(1, 1), NoteConvert.sumNoteLengths(Arrays.asList(1, 2), Arrays.asList(4, 4)));
    assertEquals(List.of(1), NoteConvert.sumNoteLengths(Arrays.asList(4, 8, 16), Arrays.asList(2, 16)));
  }
}