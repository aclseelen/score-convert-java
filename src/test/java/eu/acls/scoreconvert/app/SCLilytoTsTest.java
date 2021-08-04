package eu.acls.scoreconvert.app;

import org.junit.jupiter.api.Test;

class SCLilytoTsTest {

  @Test
  void main() {

    String[] args = new String[]{"src/test/resources/lilyInputFile.ly"};
    SCLilyToTs.main(args);
  }
}