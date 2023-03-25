package cascadia.scoring;

import cascadia.PlayerMap;

/**
 * Implements calculate score method.
 */
public abstract class ScoreToken extends Scoring {
    public static int calculateScore(PlayerMap map, Scorable option) {
        return option.score(map);
    }
}
