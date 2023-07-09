package ninemanmorris.screen.screencontroller.gamescreen;

/**
 * An interface to handle the input passed into the game for the GUI
 */
public interface IInputHandler {

    /**
     * Insert input from user into morris game to change the GUI
     * @param row - the row that the player selected
     * @param col - the col that the player selected
     */
    void handleInput(int row, int col);
}
