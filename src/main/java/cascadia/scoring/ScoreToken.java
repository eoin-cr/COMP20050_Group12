package cascadia.scoring;

import cascadia.PlayerMap;

public abstract class ScoreToken {
    public static int calculateScore(PlayerMap map, Scorable option) {
        return option.score(map);
    }
}
