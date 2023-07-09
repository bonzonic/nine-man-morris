package ninemanmorris.player;

import ninemanmorris.gamelogic.IMorrisGameStateListener;
import ninemanmorris.player.playertype.*;
import ninemanmorris.player.playertype.computer.ComputerAI;

/**
 * A class that encapsulates the parameters or response of the creation
 * of a player
 */
public class PlayerCreationReqRes {
    
    private PlayerType playerType;
    private IMorrisGameStateListener listener;
    private Player player;
    private boolean isRed;

    /**
     * Creates a player based on the request
     * @param request - The player creation request
     * @return A response object where the created player is contained
     */
    public static PlayerCreationReqRes createPlayer(PlayerCreationReqRes request) {
        PlayerCreationReqRes response = null;

        if (request.getPlayerType() == PlayerType.HUMAN) {
            response = new PlayerCreationReqRes(
                request.getPlayerType(),
                request.getIsRed(),
                request.getListener(),
                new HumanPlayer(request.getIsRed())
            );

        } else if (request.getPlayerType() == PlayerType.COMPUTER) {
            ComputerAI aiPlayer = new ComputerAI(request.getIsRed());

            response = new PlayerCreationReqRes(
                request.getPlayerType(),
                request.getIsRed(),
                aiPlayer.getComputerBoard(),
                aiPlayer
            );
        }

        return response;
    }

    /**
     * private constructor to create request/response
     * @param playerType - The type of player
     * @param isRed - true if red player, false otherwise
     * @param listener - The game state listener of the player
     * @param player - The created player object
     */
    private PlayerCreationReqRes(PlayerType playerType, boolean isRed, 
                                IMorrisGameStateListener listener, Player player) {
        this.playerType = playerType;
        this.listener = listener;
        this.isRed = isRed;
        this.player = player;
    }

    /**
     * constructor to create request/response
     * @param playerType - The type of player
     * @param isRed - true if red player, false otherwise
     * @param listener - The game state listener of the player
     */
    public PlayerCreationReqRes(PlayerType playerType, boolean isRed, 
                                IMorrisGameStateListener listener) {
        this(playerType, isRed, listener, null);
    }

    /**
     * constructor to create request/response
     * @param playerType - The type of player
     * @param isRed - true if red player, false otherwise
     */
    public PlayerCreationReqRes(PlayerType playerType, boolean isRed) {
        this(playerType, isRed, null);
    }

    /**
     * Get the player type
     * @return The player type
     */
    public PlayerType getPlayerType() {
        return playerType;
    }

    /**
     * Get the game state listener
     * @return The game state listener
     */
    public IMorrisGameStateListener getListener() {
        return listener;
    }

    /**
     * Get the player object
     * @return The player object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get if player is red
     * @return true if player is red, false otherwise
     */
    public boolean getIsRed() {
        return isRed;
    }
}
