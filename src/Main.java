public class Main {
    public static void main(String[] args) {
    	
       Output.Welcome(); //prints giant cascadia welcome message
       
       //refresh to get thread caught up
       try {
           Thread.sleep(700);
       } catch (InterruptedException ignored) {}

       // NOTE: the clear screen command does NOT work in the IDE terminal,
       // just in an actual command prompt.
       Output.clearScreen();
       
       //game starts here
       Game g = new Game();
       g.start();

    }
}
