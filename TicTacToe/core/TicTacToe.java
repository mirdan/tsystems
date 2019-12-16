package TicTacToe.core;



public class TicTacToe {
    /** User interface. */
    private UserInterface userInterface;
 
    /**
     * Constructor.
     */
    private TicTacToe() {
        userInterface = new ConsoleUI();
        
        Field field = new Field(19, 19);
        userInterface.newGameStarted(field);
    }

    /**
     * Main method.
     * @param args arguments
     */
    public static void main(String[] args) {
        new TicTacToe();
    }
}

