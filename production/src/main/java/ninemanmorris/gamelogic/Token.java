package ninemanmorris.gamelogic;

/**
 * Represents a Token in the 9 men's morris game
 */
public class Token {
    
    private boolean isRedPlayer;

    /**
     * Constructor for creating a Token
     * @param isRedPlayer - true if the token belongs to a red player, 
     * false otherwise
     */
    public Token(boolean isRedPlayer) {
        this.isRedPlayer = isRedPlayer;
    }

    /**
     * Get if the token belongs to a red player
     * @return true if the token belongs to a red player, false 
     * otherwise
     */
    public boolean getIsRedPlayer() {
        return this.isRedPlayer;
    }
}
