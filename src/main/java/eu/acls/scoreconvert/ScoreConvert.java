package eu.acls.scoreconvert;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ScoreConvert {

  private static final Logger logger = LogManager.getLogger(ScoreConvert.class);

  private final boolean relativeMode;
  private TsValue previousTsVal;
  private List<Integer> lastValidNoteLengthReference = new ArrayList<>();
  private final TsValue referenceTsVal = new TsValue(20, 8);

  public ScoreConvert() {
    relativeMode = false;
  }

  public ScoreConvert(String noteNameRelative) {
    this.relativeMode = true;
    previousTsVal = NoteConvert.lilyToTs(NoteConvert.stringToLilyValue(noteNameRelative));
  }

  private void updateNoteValueReference() {
    if (NoteConvert.isRest(previousTsVal)) {
      return;
    }
    referenceTsVal.updateNoteValues(previousTsVal);
  }

  public String lilyScoreToTsScore(String lilyScore) {

    String[] values = prepareLilyScore(lilyScore);
    return processValues(values);
  }

  private String[] prepareLilyScore(String lilyScore) {

    lilyScore = lilyScore.trim();
    lilyScore = lilyScore.replace("\n", " ");
    lilyScore = lilyScore.replace("\t", " ");
    lilyScore = lilyScore.replaceAll(" +", " ");

    return lilyScore.split(" ");
  }

  private String processValues(String[] values) {

    StringBuilder result = new StringBuilder();
    TsValue tempSyncopated = null;

    for (String value : values) {

      updateNoteValueReference();

      if (!validateLilyValue(result, value)) continue;

      LilyValue lilyValue = NoteConvert.stringToLilyValue(value);
      TsValue tsValue = getTsValue(lilyValue);
      handleMissingNoteLengthAndCleanReference(tsValue);

      boolean warning = connectSyncopated(tempSyncopated, tsValue);
      if (warning) {
        logger.warn("Wrong use of syncopation! Ignoring syncopation..");
        result.append(tsValue);
        result.append(";\n");
      }

      if (tsValue.isSyncopation()) {
        tempSyncopated = tsValue;
      } else {
        tempSyncopated = null;
        result.append(tsValue);
        result.append(";\n");
        previousTsVal = tsValue;
      }
    }
    return result.toString();
  }

  private boolean connectSyncopated(TsValue tempSyncopated, TsValue tsValue) {
    if (tempSyncopated == null) {
      return false;
    }

    if (tempSyncopated.getSemitone() != tsValue.getSemitone() || tempSyncopated.getTone() != tsValue.getTone()) {
      return true;
    }

    tsValue.setNoteLenList(NoteConvert.sumNoteLengths(tempSyncopated.getNoteLenList(), tsValue.getNoteLenList()));
    return false;
  }

  private TsValue getTsValue(LilyValue lilyValue) {

    TsValue tsValue;

    if (relativeMode) {
      tsValue = NoteConvert.lilyToTs(lilyValue, referenceTsVal);
    } else {
      tsValue = NoteConvert.lilyToTs(lilyValue);
    }

    return tsValue;
  }

  private void handleMissingNoteLengthAndCleanReference(TsValue tsValue) {

    if (tsValue.missesNoteLength()) {
      tsValue.setNoteLenList(lastValidNoteLengthReference);
    } else {
      lastValidNoteLengthReference = tsValue.getNoteLenList();
    }
  }

  private boolean validateLilyValue(StringBuilder result, String value) {
    boolean isValid = NoteConvert.checkStringForLilyValue(value);
    if (!isValid) {

      if (NoteConvert.isBar(value)) {
        result.append("\n");
      } else {
        logger.warn("Unidentified value found {}. Ignoring and skipping", value);
      }
      return false;
    }
    return true;
  }
}
