package ninemanmorris.gamelogic.game;

import java.util.ArrayList;
import java.util.List;

import ninemanmorris.gamelogic.IMorrisGameEndListener;
import ninemanmorris.gamelogic.IMorrisGameInputHandler;
import ninemanmorris.gamelogic.IMorrisGameStateListener;
import ninemanmorris.gamelogic.MorrisBoard;
import ninemanmorris.player.playertype.Player;

/**
 * Represents a single game of the 9 men's morris
 */
public abstract class MorrisGame implements IMorrisGameInputHandler {
    
    private MorrisBoard gameBoard;
    private Player[] players;
    private Player currentPlayerTurn;
    private List<IMorrisGameStateListener> gameStateListeners;
    private List<IMorrisGameEndListener> gameEndListeners;

    /**
     * Constructor to create a MorrisGame
     * @param p1 - the first player (red player)
     * @param p2 - the second player (blue player)
     * @param p1Listener - p1 game state listener
     * @param p2Listener - p2 game state listener
     */
    public MorrisGame(Player p1, Player p2, IMorrisGameStateListener p1Listener, IMorrisGameStateListener p2Listener) {
        gameStateListeners = new ArrayList<>();
        gameEndListeners = new ArrayList<>();
        players = new Player[] {p1, p2};
        currentPlayerTurn = p1;
        gameBoard = new MorrisBoard();

        addGameStateListener(p1Listener);
        addGameStateListener(p2Listener);
    }

    /**
     * Add a new listener to this class which would be updated about 
     * the current state of the game
     * @param listener - the new game state listener
     */
    public void addGameStateListener(IMorrisGameStateListener listener) {
        gameStateListeners.add(listener);
        listener.update(
            currentPlayerTurn.getIsRed(), 
            players[0].getTokenCount(), 
            players[1].getTokenCount(), 
            gameBoard.generatePlayerBoard(), 
            gameBoard.generatePreviewMove(currentPlayerTurn.getMove()), 
            gameBoard.generateMills(), 
            currentPlayerTurn.getMoveType(),
            gameBoard.getSelectedPos(currentPlayerTurn.getMove())
        );
    }

    /**
     * Add a new listener to this class which would be updated about
     * the game when the game end
     * @param listener - the new game end listener
     */
    public void addGameEndListener(IMorrisGameEndListener listener) {
        gameEndListeners.add(listener);
    }

    /**
     * Update all of the game state listeners subscribed to this class 
     * with the new state of the board
     */ 
    public void udpateGameStateListeners() {
        for (IMorrisGameStateListener listener : gameStateListeners) {
            listener.update(
                currentPlayerTurn.getIsRed(), 
                players[0].getTokenCount(), 
                players[1].getTokenCount(), 
                gameBoard.generatePlayerBoard(), 
                gameBoard.generatePreviewMove(currentPlayerTurn.getMove()), 
                gameBoard.generateMills(), 
                currentPlayerTurn.getMoveType(),
                gameBoard.getSelectedPos(currentPlayerTurn.getMove())
            );
        }
    }

    /**
     * Declare whether the winner of the game is the red token 
     * (player 1) or blue token (player 2) for all listeners 
     * subscribed to this class
     * @param isRed - true if the winner is the red token (player 1)
     * and false if it is not
     */
    protected void delcareWinner(boolean isRed) {
        for (IMorrisGameEndListener listener : gameEndListeners) {
            listener.updateGameEnd(isRed);
        }
    }

    /**
     * Declare that the game has come to a draw to all listeners 
     * subscribed to this class
     */
    protected void declareDraw() {
        for (IMorrisGameEndListener listener : gameEndListeners) {
            listener.updateGameDraw();
        }
    }

    /**
     * Validate and set the moves for both players
     */
    protected void validatePlayerMove() {
        players[0].setMove(gameBoard.validatePlayerMove(players[0].getMove()));
        players[1].setMove(gameBoard.validatePlayerMove(players[1].getMove()));
    }

    /**
     * Get the morris game board
     * @return the morris game board
     */
    protected MorrisBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Get the players that is playing this game
     * @return the two players playing this game
     */
    protected Player[] getPlayers() {
        return players;
    }

    /**
     * Get the current player turn
     * @return the current player who is playing the turn currently
     */
    protected Player getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    /**
     * Switch the player turn
     */
    protected void switchPlayerTurn() {
        if (currentPlayerTurn == players[0]) {
            this.currentPlayerTurn = players[1];
        } else {
            this.currentPlayerTurn = players[0];
        }
    }
}
