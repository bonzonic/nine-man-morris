package ninemanmorris.gamelogic;

/**
 * An interface for allowing classes to listen to the game result when
 * the game ends
 */
public interface IMorrisGameEndListener {
    
    /**
     * This method will be called when the game ends
     * @param isRed - the player that won (true for red, false for blue)
     */
    void updateGameEnd(boolean isRed);

    /**
     * This method will be called when the game is a draw
     */
    void updateGameDraw();
}
