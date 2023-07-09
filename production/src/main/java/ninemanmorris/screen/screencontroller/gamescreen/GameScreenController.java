package ninemanmorris.screen.screencontroller.gamescreen;

import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ninemanmorris.gamelogic.IMorrisGameEndListener;
import ninemanmorris.gamelogic.IMorrisGameInputHandler;
import ninemanmorris.gamelogic.IMorrisGameStateListener;
import ninemanmorris.gamelogic.MorrisGameFactory;
import ninemanmorris.screen.ScreenPage;
import ninemanmorris.screen.screencontroller.Intent;
import ninemanmorris.screen.screencontroller.ScreenController;
import ninemanmorris.shared.MoveType;

/**
 * Controller class for the game screen of the game
 */
public class GameScreenController extends ScreenController implements IMorrisGameStateListener, 
                                                                      IMorrisGameEndListener, 
                                                                      IInputHandler {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private GridPane grid;

    @FXML
    private Text turn;

    @FXML
    private Text player1Txt;

    @FXML
    private Text player2Txt;

    @FXML
    private Label red_token_count;

    @FXML
    private Label blue_token_count;

    @FXML
    private Text move_quote;

    @FXML
    private Button pauseBtn;

    @FXML
    private AnchorPane pausePane;

    @FXML
    private Text resPlayer1Txt;

    @FXML
    private Text resPlayer2Txt;

    @FXML
    private Text playerWonTxt;

    @FXML
    private Text counterTxt;

    @FXML
    private VBox resultScreen;

    private IMorrisGameInputHandler morrisGame;
    private static int redWins = 0;
    private static int blueWins = 0; 
    private GameMode gameMode;
    private String player1String;
    private String player2String;

    private GameScreenGrid gameGrid;


    /**
     * Initialise the game and hide the pause menu and result menu
     */
    public void initialize() {
        switchNodeVisibility(pausePane, false);
        switchNodeVisibility(resultScreen, false);
        this.gameGrid = new GameScreenGrid(grid, mainPane, this);
    }

    @Override
    public void retrieveIntent(Intent intent) {
        gameMode = intent.getItem("Game Mode");

        // Create a game based on the game mode given
        if (gameMode == GameMode.TWO_PLAYER_MODE) {
            player1String = "Player 1";
            player2String = "Player 2";

            this.morrisGame = MorrisGameFactory.createMorrisGame(
                MorrisGameFactory.createHumanRequest(true, this),
                MorrisGameFactory.createHumanRequest(false, this),
                this
            );

        } else if (gameMode == GameMode.COMPUTER_MODE) {
            player1String = "Player";
            player2String = "Computer";

            this.morrisGame = MorrisGameFactory.createMorrisGame(
                MorrisGameFactory.createHumanRequest(true, this),
                MorrisGameFactory.createComputerRequest(false),
                this
            );
        }

        player1Txt.setText(player1String);
        player2Txt.setText(player2String);
        resPlayer1Txt.setText(player1String);
        resPlayer2Txt.setText(player2String);
    }

    @Override
    public void update(boolean isRed, int redToken, int blueToken, 
                        Boolean[][] board, boolean[][] interactables, 
                        List<int[][]> mills, MoveType move, int[] selectedPos) {
        
        // update the look of the board
        gameGrid.updateBoard(board);
        gameGrid.updateInteractablePos(interactables, move);
        gameGrid.updateMill(mills);
        gameGrid.updateSelectedPos(selectedPos);

        // update the annotations around the board
        updatePlayerTurn(isRed);
        updateMoveQuote(move);
        updatePlayerToken(redToken, blueToken);
    }

    /**
     * Update the player token counter GUI
     * @param redToken - number of red tokens
     * @param blueToken - number of blue tokens
     */
    private void updatePlayerToken(int redToken, int blueToken) {
        red_token_count.setText(redToken + "");
        blue_token_count.setText(blueToken + "");
    }

    /**
     * Updates which player's turn it is
     * @param isRedTurn - boolean is to indicate is red turn
     */
    private void updatePlayerTurn(boolean isRedTurn) {
        Color color;
        if (isRedTurn) {
            color = Color.RED;
            turn.setText(player1String + "'s");
        } else {
            color = Color.BLUE;
            turn.setText(player2String + "'s");
        }
        turn.setFill(color);
    }

    /**
     * Update the quote for the current move
     * @param move - the current type of move 
     */
    private void updateMoveQuote(MoveType move) {
        String newQuote = "";

        // check move type and display suitable quote
        if (move == MoveType.PLACE_PHASE) {
            newQuote = "Place your tokens on the board";
        } else if (move == MoveType.MOVE_PHASE) {
            newQuote = "Move your tokens to any empty adjacent spot";
        } else if (move == MoveType.FLY_PHASE) {
            newQuote = "Move your tokens to any empty spot";
        } else if (move == MoveType.REMOVE_PHASE) {
            newQuote = "Remove one of your opponent's token";
        } else if (move == MoveType.SELECT_PHASE) {
            newQuote = "Select your token";
        }

        move_quote.setText(newQuote);
    }

    /**
     * Update that the game has ended
     * @param isRed - determine it is red's turn or not
     */
    @Override
    public void updateGameEnd(boolean isRed) {
        // show the result screen
        switchNodeVisibility(resultScreen, true);

        // fill up result screen with winner details
        if (isRed) {
            redWins++;
            playerWonTxt.setText(player1String + " Won!");
        } else {
            blueWins++;
            playerWonTxt.setText(player2String + " Won!");
        }
        counterTxt.setText(redWins + " - " + blueWins);
    }

    @Override
    public void handleInput(int row, int col) {
        morrisGame.handleInput(row, col);
    }

    @Override
    public void updateGameDraw() {
        // show the game is a draw
        switchNodeVisibility(resultScreen, true);
        playerWonTxt.setText("Draw!");
        counterTxt.setText(redWins + " - " + blueWins);
    }

    /**
     * Pause the current game and show the pause menu
     * @param event - ActionEvent to detect when the game is paused
     * @throws IOException
     */
    public void pauseGame(ActionEvent event) throws IOException {
        switchNodeVisibility(pausePane, true);
    }

    /**
     * Resume the current game and hide the pause menu
     * @param event - ActionEvent to detect when the game is resumed
     * @throws IOException
     */
    public void resumeGame(ActionEvent event) throws IOException {
        switchNodeVisibility(pausePane, false);
    }

    /**
     * Switch back to the main title screen
     * @param event - ActionEvent to detect when to switch back to the
     * title screen
     * @throws IOException
     */
    public void backToMenu(ActionEvent event) throws IOException {
        // reset match counter 
        redWins = 0;
        blueWins = 0;

        switchScene(ScreenPage.TITLE_SCREEN.toString());
    }

    /**
     * Start a two human-player game
     * @param event - ActionEvent to detect when to start a two-player 
     * game
     * @throws IOException
     */
    public void restartGame(ActionEvent event) throws IOException {
        // Place param into an intent
        Intent intent = new Intent();
        intent.addItems("Game Mode", gameMode);

        // Pass param to new scene
        switchScene(ScreenPage.GAME_SCREEN.toString(), intent);
    }
}