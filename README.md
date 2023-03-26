# COMP20050 Group12
Eoin Creavin, Mynah Bhattacharyya, Ben McDowell

Use of certain newer Java features means a requirement of at least Java 17 has been
imposed in the gradle build file.

Gradle should not be required to run the game, however, the tests use the Mockito
library which requires gradle to include.

A screenshot of the test coverage of the project has been included 
(see TestCoverage.png).

A modified version of the Google stylesheet has been used for the code 
styling (see stylesheet.xml)

We have used the default gradle package layout (`src/main/java/...` and `src/test/java/...`).
Make sure to change the package source file from `src` to `src/main/java` and set the
main run configuration to `cascadia.Main`.
