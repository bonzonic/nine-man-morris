package ninemanmorris.player.playertype;

import java.util.ArrayList;
import java.util.List;

import ninemanmorris.gamelogic.Token;
import ninemanmorris.move.Move;
import ninemanmorris.move.PlaceToken;
import ninemanmorris.shared.MoveType;

/**
 * Represents a player 
 */
public abstract class Player {
    
    // Attributes
    private Move currentMove;
    private boolean isRed;
    private List<Token> tokens;

    /**
     * Player constructor for creating Player
     * @param isRed - true if the player is a red player, false 
     * otherwise
     */
    public Player(boolean isRed) {
        this.isRed = isRed;
        this.tokens = new ArrayList<>(10);
        this.currentMove = new PlaceToken(isRed, tokens);

        while (tokens.size() < 9) {
            tokens.add(new Token(isRed));
        }
    }

    /**
     * Get the player's token count
     * @return the number of tokens player has left
     */
    public int getTokenCount() {
        return tokens.size();
    }

    /**
     * Get if the this player if a red player
     * @return true if this player is a red player, false otherwise
     */
    public boolean getIsRed() {
        return isRed;
    }

    /**
     * Get the move that the player can make currently
     * @return the move that the player can make currently
     */
    public Move getMove() {
        return currentMove;
    }

    /**
     * Get the type of move the player can make
     * @return an enum representing the move type
     */
    public MoveType getMoveType() {
        return currentMove.getMoveType();
    }

    /**
     * Get if the player require user input
     * @return true if the player require user input, false otherwise
     */
    public abstract boolean getIsRequireInput();

    /**
     * Set the new move that the player can make
     * @param move - the new move that the player can make
     */
    public void setMove(Move move) {
        this.currentMove = move;
    }
}
