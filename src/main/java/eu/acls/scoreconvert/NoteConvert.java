package eu.acls.scoreconvert;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NoteConvert {

  private static final int REST = 100;
  private static final Logger logger = LogManager.getLogger(NoteConvert.class);

  private NoteConvert() {
  }

  /**
   * converts lily note value to ts
   *
   * @param lily note value in lily format
   * @return ts value of one note
   */
  public static String lilyToTs(String lily) {

    lily = lily.trim();
    LilyValue lilyValue = stringToLilyValue(lily);

    TsValue tsValue = letterToTs(lilyValue.getLetters());
    processOctaves(tsValue, lilyValue.getOctaveIndication());
    processNoteLength(tsValue, lilyValue.getNumbers(), lilyValue.getDots());
    processSyncopation(tsValue, lilyValue);
    return tsValue.toString();
  }

  public static TsValue lilyToTs(LilyValue lilyValue) {

    TsValue tsValue = letterToTs(lilyValue.getLetters());
    processOctaves(tsValue, lilyValue.getOctaveIndication());
    processNoteLength(tsValue, lilyValue.getNumbers(), lilyValue.getDots());
    processSyncopation(tsValue, lilyValue);
    return tsValue;
  }

  public static TsValue lilyToTs(LilyValue lilyValue, TsValue reference) {

    TsValue newTs = letterToTs(lilyValue.getLetters());
    adjustRelative(newTs, reference);
    processOctaves(newTs, lilyValue.getOctaveIndication());
    processNoteLength(newTs, lilyValue.getNumbers(), lilyValue.getDots());
    processSyncopation(newTs, lilyValue);
    return newTs;
  }

  private static void processSyncopation(TsValue tsValue, LilyValue lilyValue) {
    if (lilyValue.hasSyncopationTilde()) {
      tsValue.setSyncopation(true);
    }
  }

  private static void adjustRelative(TsValue tsValue, TsValue reference) {

    if (isRest(tsValue)) {
      return;
    }

    int sumTs = tsValue.getTone() + tsValue.getSemitone();
    int sumRef = reference.getTone() + reference.getSemitone();

    int difference = sumRef - sumTs;

    while (Math.abs(difference) >= 4) {

      if (difference > 0) {
        tsValue.addOctave();
        difference -= 7;
      } else {
        tsValue.subtractOctave();
        difference += 7;
      }
    }
  }

  public static boolean isRest(TsValue tsValue) {
    return tsValue.getTone() == 100 && tsValue.getSemitone() == 100;
  }

  static LilyValue stringToLilyValue(String lily) {

    LilyValue lilyValue = new LilyValue();
    lily = getAllLetters(lily, lilyValue);
    lily = getSpecialChars(lily, lilyValue);
    lily = getAllDigits(lily, lilyValue);
    getSpecialChars(lily, lilyValue);

    return lilyValue;
  }

  private static void processNoteLength(TsValue tsValue, String numbers, String dots) {

    if (StringUtils.isBlank(numbers)) {
      return;
    }

    int number = Integer.parseInt(numbers);

    if (!isPowerOfTwo(number)) {
      return;
    }
    tsValue.getNoteLenList().add(number);

    while (dots.length() > 0) {
      char c = dots.charAt(0);
      dots = dots.substring(1);
      if (c == '.') {
        number *= 2;
        tsValue.getNoteLenList().add(number);
      }
    }
  }

  protected static boolean isPowerOfTwo(int number) {
    while (number > 1) {
      if ((number % 2) != 0) {
        return false;
      }
      number /= 2;
    }
    return number == 1;
  }

  private static void processOctaves(TsValue tsValue, String specialChars) {

    loop:
    while (specialChars.length() > 0) {
      char c = specialChars.charAt(0);
      specialChars = specialChars.substring(1);
      switch (c) {
        case '\'':
          tsValue.addOctave();
          break;
        case ',':
          tsValue.subtractOctave();
          break;
        default:
          break loop;
      }
    }
  }

  private static TsValue letterToTs(String letters) {

    if (letters.length() == 0) {
      return new TsValue(0, 0);
    }

    TsValue tsValue;

    char c = letters.charAt(0);
    letters = letters.substring(1);

    switch (c) {
      case 'c':
        tsValue = new TsValue(20, 8);
        break;
      case 'd':
        tsValue = new TsValue(21, 8);
        break;
      case 'e':
        tsValue = new TsValue(22, 8);
        break;
      case 'f':
        tsValue = new TsValue(22, 9);
        break;
      case 'g':
        tsValue = new TsValue(23, 9);
        break;
      case 'a':
        tsValue = new TsValue(24, 9);
        break;
      case 'b':
        tsValue = new TsValue(25, 9);
        break;
      case 'r':
        return new TsValue(REST, REST);
      default:
        return new TsValue(0, 0);
    }

    processMollDur(letters, tsValue);
    return tsValue;
  }

  private static void processMollDur(String letters, TsValue tsValue) {

    while (letters.length() > 1) {
      String mollDur = letters.substring(0, 2);
      switch (mollDur) {
        case "is":
          tsValue.setTone(tsValue.getTone() + 1);
          tsValue.setSemitone(tsValue.getSemitone() - 1);
          break;
        case "es":
          tsValue.setTone(tsValue.getTone() - 1);
          tsValue.setSemitone(tsValue.getSemitone() + 1);
          break;
        default:
      }
      letters = letters.substring(2);
    }
  }

  private static String getAllDigits(String lily, LilyValue lilyValue) {

    while (lily.length() > 0) {
      char c = lily.charAt(0);
      if (Character.isDigit(c)) {
        lilyValue.setNumbers(lilyValue.getNumbers() + c);
        lily = lily.substring(1);
      } else {
        break;
      }
    }
    return lily;
  }

  private static String getSpecialChars(String lily, LilyValue lilyValue) {

    while (lily.length() > 0) {
      char c = lily.charAt(0);
      if (!Character.isLetterOrDigit(c)) {
        switch (c) {
          case '\'', ',' -> lilyValue.setOctaveIndication(lilyValue.getOctaveIndication() + c);
          case '.' -> lilyValue.setDots(lilyValue.getDots() + c);
          case '~' -> lilyValue.setSyncopationTilde(true);
          default -> logger.warn("Unexpected character: '{}'", c);
        }

        lily = lily.substring(1);
      } else {
        break;
      }
    }
    return lily;
  }

  private static String getAllLetters(String lily, LilyValue lilyValue) {

    while (lily.length() > 0) {
      char c = lily.charAt(0);
      if (Character.isLetter(c)) {
        lilyValue.setLetters(lilyValue.getLetters() + c);
        lily = lily.substring(1);
      } else {
        break;
      }
    }
    return lily;
  }

  public static boolean checkStringForLilyValue(String s) {

    boolean startsWithAtLeastOneLetter = false;

    StringBuilder noteName = new StringBuilder();

    // TODO: works, but possibly improve validity checks on order of letters
    while (s.length() > 0 && Character.isLetter(s.charAt(0))) {
      startsWithAtLeastOneLetter = true;
      noteName.append(s.charAt(0));
      s = s.substring(1);
    }

    if (startsWithAtLeastOneLetter) {
      // TODO: check note name validity
      logger.debug("notename: {}", noteName);
    }

    boolean specialCharsAreValidOctaveDesignations = true;

    // TODO: should work, but possibly improve validity checks on order of chars
    while (s.length() > 0 && !Character.isLetterOrDigit(s.charAt(0))) {
      switch (s.charAt(0)) {
        case '\'':
          break;
        case ',':
          break;
        case '~':
          break;
        default:
          specialCharsAreValidOctaveDesignations = false;
      }
      s = s.substring(1);
    }

    return startsWithAtLeastOneLetter && specialCharsAreValidOctaveDesignations;
  }

  public static boolean isBar(String s) {
    if (s.length() > 0) {
      return s.charAt(0) == '|';
    }
    return false;
  }

  public static List<Integer> sumNoteLengths(List<Integer> first, List<Integer> second) {
    List<Integer> joinedList = new ArrayList<>();
    List<Integer> sum = new ArrayList<>();
    joinedList.addAll(first);
    joinedList.addAll(second);
    joinedList = joinedList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

    Integer previous = null;
    for (Integer current : joinedList) {
      if (!isPowerOfTwo(current)) {
        logger.warn("List consists of number not a power of 2: {}. Returning empty list..", current);
        return Collections.emptyList();
      }

      if (current.equals(previous) && current > 1) {
        sum.remove(previous);
        current /= 2;
      }
      sum.add(current);
      previous = current;
    }

    return sum.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
  }
}

