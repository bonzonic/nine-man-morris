package ninemanmorris.player.playertype.computer;

import ninemanmorris.move.Move;
import ninemanmorris.player.playertype.Player;

/**
 * Represents a Computer AI player
 */
public class ComputerAI extends Player {

    private static final boolean IS_REQUIRE_INPUT = false;
    
    private ComputerBoard computerBoard;

    /**
     * Constructor for ComputerAI
     * @param isRed - true if the current player is a red player, 
     * false otherwise
     */
    public ComputerAI(boolean isRed) {
        super(isRed);
        this.computerBoard = new ComputerBoard();
    }

    @Override
    public boolean getIsRequireInput() {
        return IS_REQUIRE_INPUT;
    }
    
    /**
     * Insert random move positions and return the move 
     */
    @Override
    public Move getMove() {
        Move currentMove = super.getMove();
        int[] selectedPos = computerBoard.selectPosition();

        currentMove.setMovePosition(selectedPos[0], selectedPos[1]);

        return currentMove;
    }

    /**
     * Get the computer board that this AI is using
     * @return the computer board this ai has
     */
    public ComputerBoard getComputerBoard() {
        return computerBoard;
    }
}
