package ninemanmorris.gamelogic.game;

import ninemanmorris.gamelogic.IMorrisGameStateListener;
import ninemanmorris.player.playertype.Player;

/**
 * Represents a game that is hosted locally on the current device
 */
public class LocalGame extends MorrisGame {

    /**
     * LocalGame constructor
     * @param p1 - the first player (red player)
     * @param p2 - the second player (blue player)
     * @param p1Listener - p1 game state listener
     * @param p2Listener - p2 game state listener
     */
    public LocalGame(Player p1, Player p2, IMorrisGameStateListener p1Listener, IMorrisGameStateListener p2Listener) {
        super(p1, p2, p1Listener, p2Listener);
    }

    @Override
    public void handleInput(int row, int col) {
        // game loop
        do {
            // get the current player
            Player currentPlayer = getCurrentPlayerTurn();

            // if the current player does not require input, execute 
            // the move
            if (!getCurrentPlayerTurn().getIsRequireInput()) {
                currentPlayer.setMove(getGameBoard()
                                    .executeMove(currentPlayer.getMove()));
            } else {
                currentPlayer.setMove(getGameBoard()
                                    .executeMove(currentPlayer.getMove(), 
                                    row, col));
            }

            // if the game has a winner, declare the winner and if the
            // game is a draw, declare draw
            if (getGameBoard().getWinPlayer() != null) {
                delcareWinner(getGameBoard().getWinPlayer());
                break;
            } if (getGameBoard().getIsDrawGame()) {
                declareDraw();
                break;
            }

            // switch to the next player's turn
            if (getGameBoard().getSwitchTurn()) {
                switchPlayerTurn();
                getGameBoard().resetSwitchTurn();
                validatePlayerMove();
            }

            udpateGameStateListeners();

        } while (!getCurrentPlayerTurn().getIsRequireInput());
    }
}
