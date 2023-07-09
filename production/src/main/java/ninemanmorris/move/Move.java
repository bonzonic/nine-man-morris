package ninemanmorris.move;

import ninemanmorris.gamelogic.Position;
import ninemanmorris.shared.MoveType;

/**
 * Represents a type of move that the player can make in the 9 men's 
 * morris game
 */
public abstract class Move {

    private int row;
    private int col;
    private boolean switchTurn;
    private boolean isRedMove;
    private boolean isDraw;

    /**
     * The move constructor to instantiate a Move object
     * @param isRedMove - true if this move belongs to the red player, 
     * false otherwise
     */
    public Move(boolean isRedMove) {
        this.isRedMove = isRedMove;
    }
    
    /**
     * Perform the current move and returns the next move that the 
     * player can make
     * @param pos - the position that the player has selected
     * @return the next move that the player can make
     */
    public abstract Move performMove(Position pos, Position[][] board);

    /**
     * Get a list of positions that can be selected for the current 
     * move
     * @param positions - the game board
     * @return the list of positions that can be selected for the 
     * current move
     */
    public abstract boolean[][] previewMove(Position[][] positions);

    /**
     * Validate if the current move is still valid
     * @param positions - the game board
     * @return returns itself if the move is still valid, return a new 
     * move otherwise
     */
    public abstract Move validateCurrentMove(Position[][] positions);

    /**
     * Get the move type specific for this move
     * @return the move type of this move
     */
    public abstract MoveType getMoveType();

    /**
     * Get the winner of the game  
     * @param positions - position table containing all positions of 
     * the board
     * @return true if red token (player 1) wins and false if blue 
     * token (player 2) wins
     */
    public Boolean getWinPlayer(Position[][] positions) {
        int redCount = 0;
        int blueCount = 0;

        // count the number of red and blue tokens on the board
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                if (positions[i][j] != null && positions[i][j].getToken() != null) {
                    if (positions[i][j].getIsRedToken()) {
                        redCount += 1;
                    } else if (!positions[i][j].getIsRedToken()) {
                        blueCount += 1;
                    }
                }
            }
        }

        // check winning condition which is when one player has 
        // less than three tokens so the other player wins
        if (redCount >= 3 && blueCount >= 3) {
            // if there both players have at least 3 tokens, continue
            // game
            return null;
        } else if (blueCount < 3) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get selected positions within a move
     * @return an integer array of positions selected
     */
    public int[] getSelectedPos() {
        return null;
    }

    /**
     * Get the row and col that has been selected for this move
     * @return The row and col that has been selected for this move
     */
    public int[] getMovePosition() {
        return new int[] {row, col};
    }

    /**
     * Pre-select the row and col for this move
     * @param row - the row on the board
     * @param col - the col on the board
     */
    public void setMovePosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Get if the player should switch turn
     * @return true if the player should switch turn, false otherwise
     */
    public boolean getSwitchTurn() {
        return switchTurn;
    }

    /**
     * Reset the switch turn 
     */
    public void resetSwitchTurn() {
        this.switchTurn = false;
    }

    /**
     * Enable switching turn 
     */
    protected void enableSwitchTurn() {
        this.switchTurn = true;
    }

    /**
     * Get if the current move is a red move
     * @return true if the current move belonngs to red player, 
     * false otherwise
     */
    protected boolean getIsRedMove() {
        return this.isRedMove;
    }

    /**
     * Get if the game has come to a draw
     * @return true if the game has reached a draw, false otherwise
     */
    public boolean getIsDraw() {
        return isDraw;
    }

    /**
     * Set that the game has come to a draw
     */
    protected void enableDraw() {
        isDraw = true;
    }
}
