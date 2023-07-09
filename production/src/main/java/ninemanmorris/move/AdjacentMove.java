package ninemanmorris.move;

import ninemanmorris.gamelogic.Position;
import ninemanmorris.shared.MoveType;

/**
 * Represents a specific type of move that the player can make in the 
 * 9 men's morris game, where the player selects one token and places 
 * it to an adjacent position
 */
public class AdjacentMove extends Move {

    private Position selectedPos;

    /**
     * The AdjacentMove constructor for creating an AdjacentMove
     * @param isRedMove - true if the move belongs to a red player, 
     * false otherwise
     */
    public AdjacentMove(boolean isRedMove) {
        super(isRedMove);
    }

    @Override
    public Move performMove(Position pos, Position[][] board) {
        Move output = null;

        // if no position of token has been selected, set position if
        // token is movable
        if (selectedPos == null) {
            if (pos.getToken() != null && pos.getIsRedToken() == getIsRedMove() 
                && checkIsMovable(pos)) {
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

            } else if (pos.getToken() == null && 
                        (isNeighbour(selectedPos.getVerticalNeighbours(), pos) 
                        || isNeighbour(selectedPos.getHorizontalNeighbours(), pos))) {
                // move token to adjacent empty position
                pos.addToken(selectedPos.removeToken());

                if (pos.getIsMill()) {
                    // if formed a mill, allow player to remove 
                    // opponent's token
                    output = new RemoveToken(getIsRedMove(), 
                                            new AdjacentMove(getIsRedMove()));
                } else {
                    // switch to other player's turn
                    enableSwitchTurn();
                    output = new AdjacentMove(getIsRedMove());
                }

            } else {
                output = new AdjacentMove(getIsRedMove());
            }
        }

        return output;
    }

    /**
     * Check whether the token at a particular position is movable
     * @param pos - position to check for mobility
     * @return true if token at that position can be moved, false
     * otherwise
     */
    private boolean checkIsMovable(Position pos) {
        // check if there are adjacent horizontal empty positions
        for (Position currentNeighbour : pos.getHorizontalNeighbours()) {
            if (currentNeighbour.getToken() == null) {
                return true;
            }
        }

        // check if there are adjacent vertical empty positions
        for (Position currentNeighbour : pos.getVerticalNeighbours()) {
            if (currentNeighbour.getToken() == null) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the position is in the neighbour list 
     * @param neighbours - the list of neighbours
     * @param current - the position to compare with
     * @return true if the current position is in the neighbour list, false otherwise
     */
    private boolean isNeighbour(Position[] neighbours, Position current) {
        for (Position neighbour : neighbours) {
            if (current == neighbour) {
                return true;
            }
        }

        return false;
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
                    && positions[i][j].getIsRedToken() == getIsRedMove() 
                    && checkIsMovable(positions[i][j]);

                } else {
                    // get positions the selected token can be moved to
                    boolean currentIsNeighbour = 
                        isNeighbour(selectedPos.getHorizontalNeighbours(), 
                                    positions[i][j]) 
                        || isNeighbour(selectedPos.getVerticalNeighbours(), 
                                    positions[i][j]);   
                    output[i][j] = positions[i][j] != null 
                                    && positions[i][j].getToken() == null 
                                    && currentIsNeighbour 
                                    && positions[i][j] != selectedPos;
                }
            }
        }

        return output; 
    }

    @Override
    public Move validateCurrentMove(Position[][] positions) {
        int count = 0;

        // get number of tokens on the board
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                if (positions[i][j] != null 
                    && positions[i][j].getToken() != null 
                    && positions[i][j].getIsRedToken() == getIsRedMove()) {
                    count += 1;
                }
            }
        }

        // if there are 3 tokens return a flying move
        if (count == 3) {
            return new FlyingMove(getIsRedMove());
        }
        return this;
    }

    @Override
    public MoveType getMoveType() {
        if (selectedPos == null) {
            // if no token has been selected to move yet
            return MoveType.SELECT_PHASE;
        }

        // if one token selected
        return MoveType.MOVE_PHASE;
    }
    
    @Override
    public Boolean getWinPlayer(Position[][] positions) {
        boolean redMove = false;
        boolean blueMove = false;

        // check whether both tokens are movable or not
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                if (positions[i][j] != null && positions[i][j].getToken() != null 
                    && checkIsMovable(positions[i][j])) {
                    if (positions[i][j].getIsRedToken()) {
                        redMove = true;
                    } else {
                        blueMove = true;
                    }
                }

                if (redMove && blueMove) {
                    break;
                }
            }
        }

        // check winning condition which is when one player cannot 
        // move their token so the other player wins
        if (redMove && blueMove) {
            // if both tokens are movable, check other winning 
            // condition
            return super.getWinPlayer(positions);
        } else if (redMove) {
            return true;
        } else if (blueMove) {
            return false;
        } else {
            enableDraw();
            return null;
        }
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
