package eu.acls.scoreconvert;

public class LilyValue {

  private String letters;
  private String octaveIndication;
  private String numbers;
  private String dots;
  private boolean syncopationTilde;

  public LilyValue() {
    letters = "";
    octaveIndication = "";
    numbers = "";
    dots = "";
    syncopationTilde = false;
  }

  @Override
  public String toString() {
    return letters + octaveIndication + numbers;
  }

  public String getLetters() {
    return letters;
  }

  public void setLetters(String letters) {
    this.letters = letters;
  }

  public String getOctaveIndication() {
    return octaveIndication;
  }

  public void setOctaveIndication(String octaveIndication) {
    this.octaveIndication = octaveIndication;
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

  public boolean hasSyncopationTilde() {
    return syncopationTilde;
  }

  public void setSyncopationTilde(boolean syncopationTilde) {
    this.syncopationTilde = syncopationTilde;
  }
}
