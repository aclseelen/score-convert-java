package eu.acls.scoreconvert;

public class LilyValue {

  private String letters;
  private String specialChars;
  private String numbers;
  private String dots;

  public LilyValue() {
    letters = "";
    specialChars = "";
    numbers = "";
    dots = "";
  }

  @Override
  public String toString() {
    return letters + specialChars + numbers;
  }

  public String getLetters() {
    return letters;
  }

  public void setLetters(String letters) {
    this.letters = letters;
  }

  public String getSpecialChars() {
    return specialChars;
  }

  public void setSpecialChars(String specialChars) {
    this.specialChars = specialChars;
  }

  public String getNumbers() {
    return numbers;
  }

  public void setNumbers(String numbers) {
    this.numbers = numbers;
  }

  public String getDots() {
    return dots;
  }

  public void setDots(String dots) {
    this.dots = dots;
  }
}
