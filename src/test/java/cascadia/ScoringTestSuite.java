package cascadia;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ScoringSalmonTest.class,
        ScoringElkTest.class,
        ScoringFoxTest.class,
        ScoringHawkTest.class,
        ScoringHabitatCorridorsTest.class,
})

public class ScoringTestSuite {
    // the class remains empty,
    // used only as a holder for the above annotations
}
