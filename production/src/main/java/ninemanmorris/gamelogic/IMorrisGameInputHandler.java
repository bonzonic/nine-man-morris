package ninemanmorris.gamelogic;

/**
 * An interface for allowing classes to insert input into morrisgame
 */
public interface IMorrisGameInputHandler {
    
    /**
     * Insert input from user into morris game
     * @param row - the row that the player selected
     * @param col - the col that the player selected
     */
    void handleInput(int row, int col);
}
