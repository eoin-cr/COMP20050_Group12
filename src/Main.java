public class Main {
    public static void main(String[] args) {
    	
        Output.Welcome(); //prints giant cascadia welcome message

        // sleep so there's a bit of time after the welcome to cascadia message
        // is printed, just to make the output nicer.  Feel free to remove/modify
        // the period of time it waits for.
        // (Also note that the time is a bit long because on a terminal the welcome
        // message is wiped after the time) - actually nvm this probably won't be
        // played on a terminal, the lecturers will prob run it on eclipse.
         try {
            Thread.sleep(300);
        } catch (InterruptedException ignored) {}

        // NOTE: the clear screen command does NOT work in the IDE terminal,
        // just in an actual command prompt.
        Output.clearScreen();

        //game starts here
        Game g = new Game();
        g.startGameSetup();
        g.startPlayerTurns();
    }
}
