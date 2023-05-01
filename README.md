# COMP20050 Group12
[Eoin Creavin](https://github.com/eoin-cr/), [Mynah Bhattacharyya](https://github.com/mynah-bird),
[Ben McDowell](https://github.com/Benmc1/)

## How to play
Simply either run the jar release with `java -jar [RELEASE].jar` or run the 
`Main` java file in your IDE of choice.  The game will
then start.  It will ask you whether you want bot mode on or off. If you select to
have bot mode on, two bots will play each other in cascadia, selecting where to place
tiles, tokens, how to rotate the tiles, whether to activate a cull or not, etc.
The bots will play their turns in under a five-second time limit.

## Bot strategies
### Tokens
The token class will base the ranking of the deck tokens on how many points each
token would give it if it were placed. It iterates through the possible placements
for each token and sees what the score for each placement would be.  Due to the
limited number of tiles, this is not incredibly time intensive, and does not come
close to the 5-second time limit.

The bot has two implementations of this—constructive and destructive. The 
constructive method attempts to increase the current players score the most, 
whilst the destructive tries to take the token away from the other player which
would have increased the other player's score the most.

### Tiles
The bot first prioritises increasing the size of habitat corridors which are close
in size (-1,0, or 1 away) to the opponents same habitat corridor. This is because
bonus points are allocated to the player with the longest of each type of habitat
corridor, so we don't want to lose our advantage.

For any tiles that do not have habitat corridors that are close in size the bot
finds the smallest habitat corridor and tries to increase its size, so we have
multiple consistently sized habitat corridors to maximise scoring. The bot also
uses a constructive and destructive version of this.

The tile bot also selects the tile rotation which will maximise its habitat corridor
length.

### Tile token selection
The bot then gets the tile and token preferences.  It then iterates through, finding
the best tile token pair for placement.  Usually you can only place a tile and token
from the same pair, however, you can spend a nature token to select any tile and any
token and make a pair. If the max possible score is a decent bit larger than the
max pair, if the bot has a nature token it will spend it in order to select the
maximum score.

# Requirements

Use of certain newer Java features means a requirement of at least Java 17 has been
imposed in the gradle build file. Gradle is not required to run the game, however, 
the game tests use the Mockito library which requires gradle to include.

We have used the default gradle package layout (`src/main/java/...` and `src/test/java/...`).
Make sure to change the package source file from `src` to `src/main/java` and set the
main run configuration to `cascadia.Main`.

### Links
**This repo:** https://github.com/eoin-cr/COMP20050_Group12

**Sprint 5 kanban board:** https://github.com/users/mynah-bird/projects/2