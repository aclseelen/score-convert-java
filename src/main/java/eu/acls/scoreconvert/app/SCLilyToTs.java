package eu.acls.scoreconvert.app;

import eu.acls.scoreconvert.ScoreConvert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import java.io.*;

public class SCLilyToTs {

  private static final Logger logger = LogManager.getLogger(SCLilyToTs.class);

  public static void main(String[] args) {

    String inputFilename = args[0];
    File inputFile = new File(inputFilename);

    int dotIndex = inputFilename.lastIndexOf('.');
    String outputFilename = (dotIndex == -1) ? inputFilename : inputFilename.substring(0, dotIndex);
    outputFilename += "-sc-out.txt";

    File outputFile = new File(outputFilename);

    String relativeToNote;
    if (args.length > 1) {
      relativeToNote = args[1];
    } else {
      relativeToNote = Strings.EMPTY;
    }

    FileInputStream fileInputStream = null;
    try {
      fileInputStream = new FileInputStream(inputFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    assert fileInputStream != null;
    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
    try (
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            OutputStreamWriter outputStreamWriter = new FileWriter(outputFile);
    ) {

      String output = convertScore(relativeToNote, bufferedReader);
      outputStreamWriter.write(output);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String convertScore(String relativeToNote, BufferedReader bufferedReader) throws IOException {
    ScoreConvert scoreConvert = new ScoreConvert(relativeToNote);

    StringBuilder output = new StringBuilder();
    while (bufferedReader.ready()) {
      String line = bufferedReader.readLine();
      output.append(scoreConvert.lilyScoreToTsScore(line));
    }
    return output.toString();
  }
}
