package eu.acls.scoreconvert;

import org.apache.commons.lang3.StringUtils;

// TODO: (multiple) dotted notes
public class NoteConvert {

  private static final int REST = 100;

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
    processOctaves(tsValue, lilyValue.getSpecialChars());
    processNoteLength(tsValue, lilyValue.getNumbers(), lilyValue.getDots());
    return tsValue.toString();
  }

  public static TsValue lilyToTs(LilyValue lilyValue) {

    TsValue tsValue = letterToTs(lilyValue.getLetters());
    processOctaves(tsValue, lilyValue.getSpecialChars());
    processNoteLength(tsValue, lilyValue.getNumbers(), lilyValue.getDots());
    return tsValue;
  }

  public static TsValue lilyToTs(LilyValue lilyValue, TsValue previousTsValue) {

    TsValue tsValue = letterToTs(lilyValue.getLetters());
    adjustRelative(tsValue, previousTsValue);
    processOctaves(tsValue, lilyValue.getSpecialChars());
    processNoteLength(tsValue, lilyValue.getNumbers(), lilyValue.getDots());
    return tsValue;
  }

  private static void adjustRelative(TsValue tsValue, TsValue reference) {
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

  static LilyValue stringToLilyValue(String lily) {

    LilyValue lilyValue = new LilyValue();
    lily = getAllLetters(lily, lilyValue);
    lily = getAllSpecialChars(lily, lilyValue);
    lily = getAllDigits(lily, lilyValue);
    lily = getAllDots(lily, lilyValue);

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

  private static String getAllDots(String lily, LilyValue lilyValue) {

    while (lily.length() > 0) {
      char c = lily.charAt(0);
      if (!Character.isLetterOrDigit(c)) {
        lilyValue.setDots(lilyValue.getDots() + c);
        lily = lily.substring(1);
      } else {
        break;
      }
    }
    return lily;
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

  private static String getAllSpecialChars(String lily, LilyValue lilyValue) {

    while (lily.length() > 0) {
      char c = lily.charAt(0);
      if (!Character.isLetterOrDigit(c)) {
        lilyValue.setSpecialChars(lilyValue.getSpecialChars() + c);
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
}
