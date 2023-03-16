package eu.acls.scoreconvert;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreConvertTest {

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void lilyScoreToTsScore_notesWithoutDuration() {

    ScoreConvert scoreConvert = new ScoreConvert("c'");

    String lilyScore = "d16 e f g a bes";
    String expectedTsScore =
            """
                    26 10 16;
                    27 10 16;
                    27 11 16;
                    28 11 16;
                    29 11 16;
                    29 12 16;
                    """;
    String actualTsScore = scoreConvert.lilyScoreToTsScore(lilyScore);

    assertEquals(expectedTsScore, actualTsScore);
  }

  @Test
  void lilyScoreToTsScore_syncope() {
    ScoreConvert scoreConvert = new ScoreConvert("c'");

    String lilyScore = "b8 c4.~ | c2~ | c8 b c c~ | c2";
    String expectedTsScore =
            """
                    25 9 8;
                                
                                
                    25 10 1;
                    25 9 8;
                    25 10 8;
                                
                    25 10 2 8;
                    """;
    String actualTsScore = scoreConvert.lilyScoreToTsScore(lilyScore);

    assertEquals(expectedTsScore, actualTsScore);
  }

  @Test
  void lilyScoreToTsScore_syncopationMultipleBlocks() {
    ScoreConvert scoreConvert = new ScoreConvert("c'");

    String lilyScoreBlock1 = "b8 c4.~ |";
    String lilyScoreBlock2 = "c2 |";

    String expectedTsScore =
            """
                    25 9 8;
                                
                    25 10 2 4 8;
                                
                    """;
    StringBuilder actualTsScore = new StringBuilder();

    actualTsScore.append(scoreConvert.lilyScoreToTsScore(lilyScoreBlock1));
    actualTsScore.append(scoreConvert.lilyScoreToTsScore(lilyScoreBlock2));

    assertEquals(expectedTsScore, actualTsScore.toString());
  }

  @Test
  void lilyScoreToTsScore_syncopeAndRest() {

    ScoreConvert scoreConvert = new ScoreConvert("c'");

    String lilyScore = "f,8 r bes~ |\nbes a g |";
    String expectedTsScore =
            """
                    22 9 8;
                    100 100 8;
                                
                    24 10 4;
                    24 9 8;
                    23 9 8;
                                
                    """;
    String actualTsScore = scoreConvert.lilyScoreToTsScore(lilyScore);

    assertEquals(expectedTsScore, actualTsScore);
  }

  @Test
  void lilyScoreToTsScore_barAndNewline() {

    ScoreConvert scoreConvert = new ScoreConvert("c'");

    String lilyScore = "d16 e f g a bes |\ncis,16 bes' a g f e |";
    String expectedTsScore =
            """
                    26 10 16;
                    27 10 16;
                    27 11 16;
                    28 11 16;
                    29 11 16;
                    29 12 16;

                    26 9 16;
                    29 12 16;
                    29 11 16;
                    28 11 16;
                    27 11 16;
                    27 10 16;
                                
                    """;
    String actualTsScore = scoreConvert.lilyScoreToTsScore(lilyScore);

    assertEquals(expectedTsScore, actualTsScore);
  }

  @Test
  void lilyScoreToTsScore_syncopeWithOctave() {

    ScoreConvert scoreConvert = new ScoreConvert("c'");

    String lilyScore = "f8 d g,~ |\ng16 r8 r8. |";
    String expectedTsScore =
            """
                    27 11 8;
                    26 10 8;
                                       
                    23 9 8 16;
                    100 100 8;
                    100 100 8 16;
                                 
                     """;
    String actualTsScore = scoreConvert.lilyScoreToTsScore(lilyScore);

    assertEquals(expectedTsScore, actualTsScore);
  }
}