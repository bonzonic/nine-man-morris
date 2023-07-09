package ninemanmorris.gamelogic;

import java.util.ArrayList;
import java.util.List;

import ninemanmorris.move.Move;

/**
 * Represents the 9 men's morris board
 */
public class MorrisBoard {
    
    // Board size
    private static final int BOARD_LENGTH = 7;
    private static final int BOARD_WIDTH = 7;

    // Attributes
    private Position[][] board;

    private boolean switchTurn;
    private Boolean winPlayer;
    private boolean isDrawGame;

    /**
     * Constuctor for creating a MorrisBoard
     */
    public MorrisBoard() {
        this.board = new Position[BOARD_LENGTH][BOARD_WIDTH];
        this.switchTurn = false;
        this.winPlayer = null;

        createPositions();
        createNeighbours();
    }

    /**
     * Create neighbours for every position on the board
     */
    private void createNeighbours() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    board[i][j].setHorizontalNeighbours(identifyNeighbour(i, j, 
                                                false));
                    board[i][j].setVerticalNeighbours(identifyNeighbour(i, j, 
                                                true));
                }
            }
        }
    }

    /**
     * Helper method to identify the vertical or horizontal neighbour 
     * of the current position
     * @param row - the row to identify
     * @param col - the col to identify
     * @param isRow - true to identify vertical, false to identify 
     * horizontal
     * @return the array of neighbour position for the current 
     * position
     */
    private Position[] identifyNeighbour(int row, int col, boolean isRow) {
        // initialise variables
        ArrayList<Position> neighbours = new ArrayList<>();
        int rowAdder = (isRow) ? 1 : 0;
        int colAdder = (!isRow) ? 1 : 0;

        int currentRow = (isRow) ? row - 1 : row;
        int currentCol = (!isRow) ? col - 1 : col;

        // append left and top neighbours to array if they exist
        while (currentRow >= 0 && currentCol >= 0) {
            if (board[currentRow][currentCol] != null) {
                neighbours.add(board[currentRow][currentCol]);
                break;
            } if (currentCol == 3 && currentRow == 3) {
                break;
            }

            currentRow -= rowAdder;
            currentCol -= colAdder;
        }

        currentRow = (isRow) ? row + 1 : row;
        currentCol = (!isRow) ? col + 1 : col;

        // append right and bottom neighbours to array if they exist
        while (currentRow < BOARD_LENGTH && currentCol < BOARD_WIDTH) {
            if (board[currentRow][currentCol] != null) {
                neighbours.add(board[currentRow][currentCol]);
                break;
            } if (currentCol == 3 && currentRow == 3) {
                break;
            }

            currentRow += rowAdder;
            currentCol += colAdder;
        }

        return neighbours.toArray(new Position[neighbours.size()]);
    }

    /**
     * Create the positions on the board
     */
    private void createPositions() {
        circularCreatePositions(0, 0, 3);
        circularCreatePositions(1, 1, 2);
        circularCreatePositions(2, 2, 1);
    }

    /**
     * Helper method to create position in a circular manner
     * @param row - the row to start
     * @param col - the col to start
     * @param step - the amount of steps to take 
     */
    private void circularCreatePositions(int row, int col, int step) {
        int progress = 1;
        int dist = 2;

        // to create 3 by 3 circular positions on the board
        while (progress <= 4) {
            if (progress % 2 == 1) {
                row += step;
            } else {
                col += step;
            }

            board[row][col] = new Position(row, col);
            dist -= 1;

            if (dist == 0) {
                progress += 1;
                dist = 2;

                if (progress > 2) {
                    step = Math.abs(step) * -1;
                }
            }
        }
    }

    /**
     * Execute the given move (without row and col)
     * @param move - the move to execute
     * @return the next move that can be executed
     */
    public Move executeMove(Move move) {
        int[] coordinates = move.getMovePosition();
        return executeMove(move, coordinates[0], coordinates[1]);
    }

    /**
     * Execute the given move (with row and col)
     * @param move - the move to execute
     * @param row - the row of the position
     * @param col - the col of the position
     * @return the next move that can be executed
     */
    public Move executeMove(Move move, int row, int col) {
        Position position = null;
        Move output = null;

        // Do nothing if invalid move
        if (board[row][col] == null) {
            return move;
        } 

        // Execute move
        position = board[row][col];
        output = move.performMove(position, board);

        // Get post move information
        switchTurn = move.getSwitchTurn();
        winPlayer = move.getWinPlayer(board);
        isDrawGame = move.getIsDraw();
        move.resetSwitchTurn();

        // Check the winner using hte new move
        if (move != output && winPlayer == null && !isDrawGame) {
            winPlayer = output.getWinPlayer(board);
            isDrawGame = output.getIsDraw();
        }

        return output;
    }

    /**
     * Validates the move of the current player before execution
     * @param move - move to be validated
     * @return the move that has been validated to be executed
     */
    public Move validatePlayerMove(Move move) {
        return move.validateCurrentMove(board);
    }

    /**
     * Get the winner of the game
     * @return true if player 1 won and false if player 2 won and null
     * if no player win
     */
    public Boolean getWinPlayer() {
        return winPlayer;
    }

    /**
     * Get whether the game is a draw 
     * @return true if the game is a draw and false means it is not
     */
    public boolean getIsDrawGame() {
        return isDrawGame;
    }

    /**
     * Get if the player should switch turn
     * @return true if player should switch, false otherwise
     */
    public boolean getSwitchTurn() {
        return switchTurn;
    }

    /**
     * Reset player switch turn
     */
    public void resetSwitchTurn() {
        this.switchTurn = false;
    }

    /**
     * Get the selected position in a move
     * @param move - move to get the selected position from
     * @return an pair of integers that represent the row and col that
     * has been selected for the current move
     */
    public int[] getSelectedPos(Move move) {
        return move.getSelectedPos();
    }

    /**
     * Generate a boolean table that represents on the token placed on 
     * the board, where true represents red token, false represent 
     * blue and null represent blank
     * @return a table of tokens
     */
    public Boolean[][] generatePlayerBoard() {
        Boolean[][] output = new Boolean[BOARD_LENGTH][BOARD_WIDTH];

        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[i].length; j++) {
                if (board[i][j] != null && board[i][j].getIsRedToken() != null) {
                    output[i][j] = board[i][j].getIsRedToken();
                }
            }
        }

        return output;
    }

    /**
     * Preview the possible moves by the player for a current move
     * @param move - move to be previewed
     * @return boolean table of positions to be lit up in the move 
     * preview
     */
    public boolean[][] generatePreviewMove(Move move) {
        return move.previewMove(board);
    }

    /**
     * Generate mills on the board
     * @return a list of triplets that represents the mill positions
     * on the board
     */
    public List<int[][]> generateMills() {
        List<int[][]> output = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].getHorizontalNeighbours().length == 2 
                    && board[i][j].checkMill(false)) {
                        // add horizontal mills
                        output.add(new int[][] 
                                {board[i][j].getHorizontalNeighbours()[0].getRowCol(), 
                                board[i][j].getRowCol(), 
                                board[i][j].getHorizontalNeighbours()[1].getRowCol()});

                    } if (board[i][j].getVerticalNeighbours().length == 2 
                    && board[i][j].checkMill(true)) {
                        // add vertical mills
                        output.add(new int[][] 
                                {board[i][j].getVerticalNeighbours()[0].getRowCol(), 
                                board[i][j].getRowCol(), 
                                board[i][j].getVerticalNeighbours()[1].getRowCol()});
                    }
                }
            }
        }

        return output;
    }
}
