package ninemanmorris.screen.screencontroller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * Abstract class for screen controllers
 */
public abstract class ScreenController {

    // Default window width and height of the game
    private final static int WINDOW_WIDTH = 860;
    private final static int WINDOW_HEIGHT = 720;

    // The stage that the game is using
    private Stage stage;

    /**
     * Initialises the stage attribute of the controller with the 
     * stage passed in as a parameter
     * @param stage - a stage instance
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Main method for switching from one scene to another
     * @param name - name of file directory to switch the scene to
     * @param intent - intent to retrieve information from for scene
     * switching
     * @return FXMLLoader instance 
     * @throws IOException
     */
    public FXMLLoader switchScene(String name, Intent intent) throws IOException {
        // load fxml to switch to
        FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
        Parent root = loader.load();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        ScreenController controller = loader.getController();

        // retrieve the objects in an intent
        controller.retrieveIntent(intent);

        // set the GUI
        controller.setStage(stage);
        stage.setScene(scene);
        stage.show();

        return loader;
    }

    /**
     * Main method for switching from one scene to another
     * @param name - name of file directory to switch the scene to
     * @return FXMLLoader instance 
     * @throws IOException
     */
    public FXMLLoader switchScene(String name) throws IOException {
        return switchScene(name, new Intent());
    }

    /**
     * Method to retrieve items in an intent
     * @param intent
     */
    public void retrieveIntent(Intent intent) {

    }

    /**
     * Method to show or hide a particular node in the GUI
     * @param node - node to toggle visibility for
     * @param visible - true to show the node and false to hide the 
     * node
     */
    public void switchNodeVisibility(Pane node, boolean visible) {
        node.setVisible(visible);
        for (Node child : node.getChildren()) {
            child.setVisible(visible);
        }
    }

}
