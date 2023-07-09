package ninemanmorris.move;

import ninemanmorris.gamelogic.Position;
import ninemanmorris.shared.MoveType;

/**
 * Represents a specific type of move that the player can make in the 
 * 9 men's morris game, where the player selects a token and places it 
 * into any empty position on the board
 */
public class FlyingMove extends Move {

    private Position selectedPos;

    /**
     * The constructor for creating a FlyingMove
     * @param isRedMove - true if the move belongs to the red player, 
     * false otherwise
     */
    public FlyingMove(boolean isRedMove) {
        super(isRedMove);
    }

    @Override
    public Move performMove(Position pos, Position[][] board) {
        Move output = null;

        // if no position of token has been selected, set position if
        // token is present
        if (selectedPos == null) {
            if (pos.getToken() != null && pos.getIsRedToken() == getIsRedMove()) {
                selectedPos = pos;
                output = this;
            } else {
                output = this;
            }

        } else {
            // validity check to figure out future move if there is any

            // selected another of your own token, change selected to 
            // the new token instead
            if (pos.getToken() != null && pos.getIsRedToken() == getIsRedMove()) {
                selectedPos = pos;
                output = this;

            } else if (pos.getToken() == null) {
                // move token to empty position
                pos.addToken(selectedPos.removeToken());

                if (pos.getIsMill()) {
                    // if formed a mill, allow player to remove 
                    // opponent's token
                    output = new RemoveToken(getIsRedMove(), 
                                            new FlyingMove(getIsRedMove()));
                } else {
                    // switch to other player's turn
                    enableSwitchTurn();
                    output = new FlyingMove(getIsRedMove());
                }

            } else {
                output = new FlyingMove(getIsRedMove());
            }
        }

        return output;
    }

    @Override
    public boolean[][] previewMove(Position[][] positions) {
        boolean[][] output = new boolean[positions.length][positions[0].length];

        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                if (selectedPos == null) {
                    // get positions of tokens which can be selected
                    output[i][j] = positions[i][j] != null 
                                    && positions[i][j].getToken() != null
                                    && positions[i][j].getIsRedToken() 
                                        == getIsRedMove();

                } else {
                    // get positions the selected token can be moved to
                    output[i][j] = positions[i][j] != null 
                    && positions[i][j].getToken() == null 
                    && positions[i][j] != selectedPos;
                }
            }
        }

        return output; 
    }

    @Override
    public Move validateCurrentMove(Position[][] positions) {
        return this;
    }

    @Override
    public MoveType getMoveType() {
        if (selectedPos == null) {
            // if no token has been selected to move yet
            return MoveType.SELECT_PHASE;
        }

        // if one token selected
        return MoveType.FLY_PHASE;
    }

    @Override
    public int[] getSelectedPos() {
        if (selectedPos != null) {
            // return row and column of selected position
            return selectedPos.getRowCol();
        }
        return null;
    }
}
