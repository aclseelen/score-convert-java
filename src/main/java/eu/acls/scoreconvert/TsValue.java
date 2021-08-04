package eu.acls.scoreconvert;

import java.util.ArrayList;
import java.util.List;

public class TsValue {

  private int tone;
  private int semitone;
  private List<Integer> noteLenList = new ArrayList<>();

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
}
