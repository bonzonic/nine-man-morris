package ninemanmorris.gamelogic;

import ninemanmorris.gamelogic.game.LocalGame;
import ninemanmorris.gamelogic.game.MorrisGame;
import ninemanmorris.player.PlayerCreationReqRes;
import ninemanmorris.player.PlayerType;

/**
 * A factory class for creating MorrisGame
 */
public class MorrisGameFactory {
    
    /**
     * Private constructor to prevent instantiation
     */
    private MorrisGameFactory() {

    }

    /**
     * Creates a MorrisGame based on the specified parameter
     * @param p1 - the creation request of player 1 (red player)
     * @param p2 - the creation request of player 2 (blue player)
     * @param listeners - the game end listeners 
     * @return the newly created MorrisGame based on the specified 
     * parameters
     */
    public static MorrisGame createMorrisGame(PlayerCreationReqRes p1, PlayerCreationReqRes p2, 
                                            IMorrisGameEndListener... listeners) {
        p1 = PlayerCreationReqRes.createPlayer(p1);
        p2 = PlayerCreationReqRes.createPlayer(p2);

        MorrisGame output = new LocalGame(
            p1.getPlayer(), 
            p2.getPlayer(), 
            p1.getListener(), 
            p2.getListener()
        );

        for (IMorrisGameEndListener listener : listeners) {
            output.addGameEndListener(listener);
        }

        return output;
    }

    /**
     * Create a request object to create a human player
     * @param isRed - true if the player is red player, false otherwise
     * @param listener - The game end listener for this player
     * @return A request object containing the request to create a human
     * player
     */
    public static PlayerCreationReqRes createHumanRequest(boolean isRed, 
                                                        IMorrisGameStateListener listener) {
        return new PlayerCreationReqRes(PlayerType.HUMAN, isRed, listener);
    }

    /**
     * Create a request object to create a computer player
     * @param isRed - true if the palyer is red player, false otherwise
     * @return A request object containing the request to create a 
     * computer player
     */
    public static PlayerCreationReqRes createComputerRequest(boolean isRed) {
        return new PlayerCreationReqRes(PlayerType.COMPUTER, isRed);
    }
}
