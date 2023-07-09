package ninemanmorris.player;

import ninemanmorris.player.playertype.*;
import ninemanmorris.player.playertype.computer.ComputerAI;

/**
 * A factory class for creating Players
 */
public class PlayerFactory {

    /**
     * A private constructor to disallow instantiation
     */
    private PlayerFactory() {

    }

    /**
     * Creates a player based on the type of player specified
     * @param playerType - The type of player
     * @param isRed - true if the player is a red player, false 
     * otherwise
     * @return The newly created player based on the type specified
     */
    public static Player createPlayer(PlayerType playerType, boolean isRed) {
        if (playerType == PlayerType.HUMAN) {
            return new HumanPlayer(isRed);
        } else if (playerType == PlayerType.COMPUTER) {
            return new ComputerAI(isRed);
        }

        return null;
    }
}
