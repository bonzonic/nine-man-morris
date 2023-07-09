package ninemanmorris.screen.screencontroller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ninemanmorris.screen.ScreenPage;
import ninemanmorris.screen.screencontroller.gamescreen.GameMode;
import javafx.scene.layout.VBox;

/**
 * Controller for the title screen of the game
 */
public class TitleScreenController extends ScreenController {

    @FXML
    private Button startGameButton;

    @FXML
    private Button quitGameButton;

    @FXML
    private Button instructionButton;

    @FXML
    private VBox instructionScreen;

    @FXML
    private VBox selectScreen;

    /**
     * Initialise the game and hide the instructions and select screen
     */
    public void initialize() {
        // hide instruction 
        switchNodeVisibility(instructionScreen, false);

        // hide game selection
        switchNodeVisibility(selectScreen, false);
    }   

    /**
     * Load the start screen of the game
     * @param stage - Stage to load the start screen of the game on
     * @throws IOException
     */
    public void loadStartScreen(Stage stage) throws IOException {
        setStage(stage);
        switchScene(ScreenPage.TITLE_SCREEN.toString());
    }

    /**
     * Start a two human-player game
     * @param event - ActionEvent to detect when to start a two-player 
     * game
     * @throws IOException
     */
    public void startTwoPlayerGame(ActionEvent event) throws IOException {
        // Put all required param in an intent
        Intent intent = new Intent();
        intent.addItems("Game Mode", GameMode.TWO_PLAYER_MODE);

        switchScene(ScreenPage.GAME_SCREEN.toString(), intent);
    }

    public void startOnePlayerGame(ActionEvent event) throws IOException {
        Intent intent = new Intent();
        intent.addItems("Game Mode", GameMode.COMPUTER_MODE);

        switchScene(ScreenPage.GAME_SCREEN.toString(), intent);
    }

    /**
     * Enable selection screen to allow player to choose to play one 
     * or two player game
     * @param event - ActionEvent to detect when to start a two-player 
     * game with a human or computer
     * @throws IOException
     */
    public void selectPlayer(ActionEvent event) throws IOException {
        switchNodeVisibility(selectScreen, true);
    }


    /**
     * Method to hide the select screen of the game
     * @param event - ActionEvent to detect when to close the select 
     * screen of the game
     * @throws IOException
     */
    public void closeSelectScreen(ActionEvent event) throws IOException {
        switchNodeVisibility(selectScreen, false);
    }

    /**
     * Method to display the instructions of the game
     * @param event - ActionEvent to detect when to display the 
     * instructions of the game
     * @throws IOException
     */
    public void displayInstruction(ActionEvent event) throws IOException {
        switchNodeVisibility(instructionScreen, true);
    }

    /**
     * Method to hide the instruction screen of the game
     * @param event - ActionEvent to detect when to close the 
     * instructions screen of the game
     * @throws IOException
     */
    public void closeInstructionScreen(ActionEvent event) throws IOException {
        switchNodeVisibility(instructionScreen, false);
    }

    /**
     * Quit the game 
     * @param event - ActionEvent that triggers the game quit
     */
    public void quitGame(ActionEvent event) {
        Stage stage = (Stage) quitGameButton.getScene().getWindow();
        stage.close();
    }
}
