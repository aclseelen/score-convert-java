package eu.acls.scoreconvert;

import java.util.ArrayList;
import java.util.List;

public class TsValue {

  private int tone;
  private int semitone;
  private List<Integer> noteLenList = new ArrayList<>();
  private boolean syncopation = false;

  public TsValue(int tone, int semitone) {
    this.tone = tone;
    this.semitone = semitone;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder("" + tone + " " + semitone);

    for (Integer noteLen : noteLenList) {
      result.append(" ");
      result.append(noteLen);
    }
    return result.toString();
  }

  public int getTone() {
    return tone;
  }

  public void setTone(int tone) {
    this.tone = tone;
  }

  public int getSemitone() {
    return semitone;
  }

  public void setSemitone(int semitone) {
    this.semitone = semitone;
  }

  public List<Integer> getNoteLenList() {
    return noteLenList;
  }

  public void setNoteLenList(List<Integer> noteLenList) {
    this.noteLenList = noteLenList;
  }

  public boolean isSyncopation() {
    return syncopation;
  }

  public void setSyncopation(boolean syncopation) {
    this.syncopation = syncopation;
  }

  public void addOctave() {
    this.setTone(this.getTone() + 5);
    this.setSemitone(this.getSemitone() + 2);
  }

  public void subtractOctave() {
    this.setTone(this.getTone() - 5);
    this.setSemitone(this.getSemitone() - 2);
  }

  public boolean missesNoteLength() {
    return noteLenList.isEmpty();
  }

  /**
   * Override / update values for tone and semitone by another Ts value object
   * @param tsValue object that offers the new tone and semitone values
   */
  public void updateNoteValues(TsValue tsValue) {
    this.setTone(tsValue.getTone());
    this.setSemitone(tsValue.getSemitone());
  }

  public void updateNoteLength(TsValue tsValue) {
    this.setNoteLenList(tsValue.getNoteLenList());
  }
}
