package ninemanmorris.player.playertype;

/**
 * Represents a Human Player
 */
public class HumanPlayer extends Player {

    private static final boolean IS_REQUIRE_INPUT = true;

    /**
     * Constructor for HumanPlayer
     * @param isRed - true if the current player is red player, false 
     * otherwise
     */
    public HumanPlayer(boolean isRed) {
        super(isRed);
    }

    @Override
    public boolean getIsRequireInput() {
        return IS_REQUIRE_INPUT;
    }
}
