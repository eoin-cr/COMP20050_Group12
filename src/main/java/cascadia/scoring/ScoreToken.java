package cascadia.scoring;

import cascadia.PlayerMap;

public interface ScoreToken {
    static int calculateScore(PlayerMap map, Option option) {
        return option.score(map);
    }
}
enum Option {
    OPTION_A {
        @Override
        public int score(PlayerMap map) {
            return 1;
        }
    };
    public abstract int score(PlayerMap map);
}
