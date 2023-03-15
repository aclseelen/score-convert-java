package eu.acls.scoreconvert.app;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SCLilytoTsTest {

  @Test
  void main_invention4() {

    String[] args = new String[]{"src/test/resources/invention4-snippet.ly", "c'"};
    SCLilyToTs.main(args);

    File testOutputFile = new File("src/test/resources/invention4-snippet-sc-out.txt");

    StringBuilder actualContent = new StringBuilder();
    FileInputStream fileInputStream = null;
    try {
      fileInputStream = new FileInputStream(testOutputFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    assert fileInputStream != null;
    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
    try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

      while (bufferedReader.ready()) {
        actualContent.append(bufferedReader.readLine());
        actualContent.append("\n");
      }
      // remove last newline
      actualContent.deleteCharAt(actualContent.length() - 1);

    } catch (IOException e) {
      e.printStackTrace();
    }

    if (testOutputFile.delete()) {
      System.out.println("Deleted file " + testOutputFile);
    }

    String expectedContent =
            """
                    25 10 16;
                    26 10 16;
                    27 10 16;
                    25 10 16;
                    26 10 16;
                    27 10 16;
                                        
                    22 9 8;
                    100 100 8;
                                        
                    24 10 4;
                    24 9 8;
                    23 9 8;
                                        
                    25 10 16;
                    24 10 16;
                    24 9 16;
                    23 9 16;
                    22 9 16;
                    22 8 16;
                    """;

    assertEquals(expectedContent, actualContent.toString());
  }
}