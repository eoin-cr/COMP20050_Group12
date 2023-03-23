package cascadia;

public class Main {
    public static void main(String[] args) {
        Display.welcome();  // prints cascadia welcome message

        /*
         * sleep so there's a bit of time after the welcome to cascadia message
         * is printed, just to make the output nicer.
         */
        Display.sleep(300);

        // game starts here
        Game g = new Game();
        g.startGame();
        Display.endScreen();
    }
}
