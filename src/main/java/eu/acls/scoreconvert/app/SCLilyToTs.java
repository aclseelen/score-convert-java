package eu.acls.scoreconvert.app;

import java.io.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class SCLilyToTs {

  private static final Logger logger = LogManager.getLogger(SCLilyToTs.class);

  public static void main(String[] args) {

    File inputFile = new File(args[0]);
    try {
      FileInputStream fileInputStream = new FileInputStream(inputFile);
      InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

      while (bufferedReader.ready()) {
        String line = bufferedReader.readLine();
        System.out.println(line);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
