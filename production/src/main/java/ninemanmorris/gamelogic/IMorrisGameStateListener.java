package ninemanmorris.gamelogic;

import java.util.List;

import ninemanmorris.shared.MoveType;

/**
 * An interface for allowing the classes to be notfied about changes 
 * from morrisgame
 */
public interface IMorrisGameStateListener {

    /**
     * This method will be called whenever a board is in a new state
     * @param isRed - the current player turn
     * @param redToken - the number of token the red player has
     * @param blueToken - the number of tokne the blue player has
     * @param board - the board of tokens, where true represents red, 
     * false represents blue and null represents no token
     * @param interactables - the cell with true value means that the
     * token can be selected
     * @param mills - the list of triplets that is part of a mill
     * @param move - the type of move that can currently being executed
     * @param selectedPos - the position that has been selected
     */
    void update(
        boolean isRed, 
        int redToken, 
        int blueToken, 
        Boolean[][] board, 
        boolean[][] interactables, 
        List<int[][]> mills, 
        MoveType move, 
        int[] selectedPos
    );
}
