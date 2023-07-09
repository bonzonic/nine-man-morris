package ninemanmorris.screen.screencontroller.gamescreen;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import ninemanmorris.shared.MoveType;

/**
 * A class that handles the game grid in the game scene
 */
public class GameScreenGrid {

    // Images path
    private static final String RED_TOKEN_IMG = "/img/9mm_token_red.png";
    private static final String BLUE_TOKEN_IMG = "/img/9mm_token_blue.png";

    // Audio path
    private static final String PICKUP_AUDIO = "/audio/pickup.mp3";

    // The number of lines to create as cache
    private static final int DEFAULT_LINE_AMT = 20;

    // The index of the item when getting stack pane children
    private static final int GREEN_LIGHT_INDEX = 0;
    private static final int WHITE_LIGHT_INDEX = 1;
    private static final int YELLOW_LIGHT_INDEX = 2;
    private static final int RED_LIGHT_INDEX = 3;
    private static final int RED_INDEX = 4;
    private static final int BLUE_INDEX = 5;

    // Sizes for token and shape
    private static final int TOKEN_WIDTH_HEIGHT = 40;
    private static final int CIRCLE_SIZE = 27;
    
    // Attributes
    private Pane parentPane;
    private GridPane uiGrid;
    private StackPane[][] stackPanes;
    private ArrayList<Line> lines;
    private IInputHandler inputHandler;
    private MediaPlayer mediaPlayer;


    /**
     * GameScreenGrid constructor to create a GameScreenGrid object
     * @param uiGrid - the grid of the dame board 
     * @param parentPane - the parent pane of the fxml file
     * @param inputHandler
     */
    public GameScreenGrid(GridPane uiGrid, Pane parentPane, 
                        IInputHandler inputHandler) {
        this.uiGrid = uiGrid;
        this.stackPanes = new StackPane[7][7];
        this.parentPane = parentPane;
        this.lines = new ArrayList<>(DEFAULT_LINE_AMT);
        this.inputHandler = inputHandler;

        this.mediaPlayer = new MediaPlayer(new Media(getClass()
                                                    .getResource(PICKUP_AUDIO)
                                                    .toExternalForm()));

        createStackPanes();
        createLines();

        this.uiGrid.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                                    createGridClickHandler());
    }

    /**
     * Create event handler to handle when player clicks the grid
     * @return event handler to handle grid clicks
     */
    private EventHandler<MouseEvent> createGridClickHandler() {
        return new EventHandler<MouseEvent>() {

            /**
             * Callback method for event handler to handle a MouseEvent
             * or a click
             */
            @Override
            public void handle(MouseEvent event) {
                Node clickedNode = event.getPickResult().getIntersectedNode();

                // only handle clicks within the ui grid
                if (clickedNode.getParent() != uiGrid) {
                    clickedNode = clickedNode.getParent();
                }

                // handle input if player clicks within the grid
                if (clickedNode != null) {
                    int rowIndex = GridPane.getRowIndex(clickedNode);
                    int colIndex = GridPane.getColumnIndex(clickedNode);
                    inputHandler.handleInput(rowIndex, colIndex);

                    mediaPlayer.seek(mediaPlayer.getStartTime());
                    mediaPlayer.play();
                }
            }
        };
    }

    /**
     * Create stack panes within the grid together with relevant UI
     * elements
     */
    private void createStackPanes() {
        // get images
        Image redTokenImg = new Image(getClass().getResource(RED_TOKEN_IMG)
                                        .toExternalForm()); 
        Image blueTokenImg = new Image(getClass().getResource(BLUE_TOKEN_IMG)
                                        .toExternalForm());

        // for each stack pane place all possible ui elements in them 
        // which will be be display based on the display logic                                
        for (int i = 0; i < stackPanes.length; i++) {
            for (int j = 0; j < stackPanes[i].length; j++) {
                ImageView red = new ImageView(redTokenImg);
                ImageView blue = new ImageView(blueTokenImg);
                Circle greenCircle = new Circle(CIRCLE_SIZE);
                Circle whiteCircle = new Circle(CIRCLE_SIZE);
                Circle yellowCircle = new Circle(CIRCLE_SIZE);
                Circle redCircle = new Circle(CIRCLE_SIZE);
                stackPanes[i][j] = new StackPane();

                // Circle indicator settings
                greenCircle.setEffect(new GaussianBlur());
                greenCircle.setStyle("-fx-fill: #7aee11; -fx-cursor: hand;");
                whiteCircle.setEffect(new GaussianBlur());
                whiteCircle.setStyle("-fx-fill: #ffffff; -fx-cursor: hand;");
                yellowCircle.setEffect(new GaussianBlur());
                yellowCircle.setStyle("-fx-fill: #e6cc00;");
                redCircle.setEffect(new GaussianBlur());
                redCircle.setStyle("-fx-fill: #D64141;");

                // Token settings
                red.setFitWidth(TOKEN_WIDTH_HEIGHT);
                red.setFitHeight(TOKEN_WIDTH_HEIGHT);
                blue.setFitWidth(TOKEN_WIDTH_HEIGHT);
                blue.setFitHeight(TOKEN_WIDTH_HEIGHT);

                // Add to stackpane
                stackPanes[i][j].getChildren().addAll(new Node[] {
                    greenCircle,
                    whiteCircle,
                    yellowCircle,
                    redCircle,
                    red,
                    blue
                });

                // set all visibility to false
                for (Node node : stackPanes[i][j].getChildren()) {
                    node.setVisible(false);
                }
                
                // add stack pane to grid
                uiGrid.add(stackPanes[i][j], j, i);
            }
        }
    }

    /**
     * Create lines as cache that represent the mill later in the game,
     * display of mills will be handled by display logic
     */
    private void createLines() {
        while (lines.size() < DEFAULT_LINE_AMT) {
            Line currentLine = new Line(0, 0, 0, 0);
            currentLine.setStrokeWidth(20);
            currentLine.setEffect(new GaussianBlur());
            currentLine.setStyle("-fx-stroke: #7aee11;");
            currentLine.setVisible(false);

            lines.add(currentLine);
        }

        this.parentPane.getChildren().addAll(0, lines);
    }

    /**
     * Update the token positions on the board
     * @param newState - boolean array representing the position of
     * tokens on the board
     */
    public void updateBoard(Boolean[][] newState) {
        for (int i = 0; i < newState.length; i++) {
            for (int j = 0; j < newState[i].length; j++) {
                if (newState[i][j] == null) {
                    stackPanes[i][j].getChildren().get(RED_INDEX)
                    .setVisible(false);
                    stackPanes[i][j].getChildren().get(BLUE_INDEX)
                    .setVisible(false);
                } else {
                    stackPanes[i][j].getChildren().get(RED_INDEX)
                    .setVisible(newState[i][j]);
                    stackPanes[i][j].getChildren().get(BLUE_INDEX)
                    .setVisible(!newState[i][j]);
                }
            }
        }
    }

    /**
     * Update the lighting on the boar based on the move 
     * @param newState - boolean array representing the positions to be
     * lit up
     * @param movetype - type of move during that turn of the game
     */
    public void updateInteractablePos(boolean[][] newState, MoveType movetype) {
        for (int i = 0; i < newState.length; i++) {
            for (int j = 0; j < newState[i].length; j++) {
                if (movetype == MoveType.SELECT_PHASE) {
                    stackPanes[i][j].getChildren().get(WHITE_LIGHT_INDEX)
                    .setVisible(newState[i][j]);
                    stackPanes[i][j].getChildren().get(GREEN_LIGHT_INDEX)
                    .setVisible(false);
                    stackPanes[i][j].getChildren().get(RED_LIGHT_INDEX)
                    .setVisible(false);
                } else if (movetype == MoveType.REMOVE_PHASE) {
                    stackPanes[i][j].getChildren().get(WHITE_LIGHT_INDEX)
                    .setVisible(false);
                    stackPanes[i][j].getChildren().get(GREEN_LIGHT_INDEX)
                    .setVisible(false);
                    stackPanes[i][j].getChildren().get(RED_LIGHT_INDEX)
                    .setVisible(newState[i][j]);
                } else {
                    stackPanes[i][j].getChildren().get(WHITE_LIGHT_INDEX)
                    .setVisible(false);
                    stackPanes[i][j].getChildren().get(GREEN_LIGHT_INDEX)
                    .setVisible(newState[i][j]);
                    stackPanes[i][j].getChildren().get(RED_LIGHT_INDEX)
                    .setVisible(false);
                }
            }
        }
    }

    /**
     * Update the lighting when a token has been selected
     * @param newPos - position of token which has been selected
     */
    public void updateSelectedPos(int[] newPos) {
        // for every selected position, light up the yellor circle
        // at that position
        for (int i = 0; i < stackPanes.length; i++) {
            for (int j = 0; j < stackPanes[0].length; j++) {
                if (newPos != null && i == newPos[0] && j == newPos[1]) {
                    stackPanes[i][j].getChildren().get(YELLOW_LIGHT_INDEX)
                    .setVisible(true);
                } else {
                    stackPanes[i][j].getChildren().get(YELLOW_LIGHT_INDEX)
                    .setVisible(false);
                }
            }
        }
    }

    /**
     * Update the mill lighting on the board
     * @param newMills integer list of mills on the board
     */
    public void updateMill(List<int[][]> newMills) {
        for (Line line : lines) {
            line.setVisible(false);
        }

        // for every mill calculate position of mill and add the mill 
        // line
        for (int i = 0; i < newMills.size(); i++) {
            int[][] currentTriplets = newMills.get(i);
            double startX = Math.min(currentTriplets[0][1], 
                                    Math.min(currentTriplets[1][1], 
                                            currentTriplets[2][1]));
            double startY = Math.min(currentTriplets[0][0], 
                                    Math.min(currentTriplets[1][0], 
                                            currentTriplets[2][0]));
            double endX = Math.max(currentTriplets[0][1], 
                                    Math.max(currentTriplets[1][1], 
                                            currentTriplets[2][1]));
            double endY = Math.max(currentTriplets[0][0], 
                                    Math.max(currentTriplets[1][0], 
                                            currentTriplets[2][0]));
            
            Line currentLine = lines.get(i);
            double width = Math.min(uiGrid.getColumnConstraints().get(0)
                                    .getPrefWidth(), 
                                    uiGrid.getPrefWidth() / uiGrid.getColumnCount());
            double height = Math.min(uiGrid.getRowConstraints().get(0)
                                    .getPrefHeight(), 
                                    uiGrid.getPrefHeight() / uiGrid.getRowCount());

            startX = (startX * width) + (width / 2) + uiGrid.getLayoutX();
            startY = (startY * height) + (height / 2) + uiGrid.getLayoutY();
            endX = (endX * width) + (width / 2) + uiGrid.getLayoutX();
            endY = (endY * height) + (height / 2) + uiGrid.getLayoutY();

            currentLine.setStartX(startX);
            currentLine.setStartY(startY);
            currentLine.setEndX(endX);
            currentLine.setEndY(endY);
            currentLine.setVisible(true);
        }
    }
}
