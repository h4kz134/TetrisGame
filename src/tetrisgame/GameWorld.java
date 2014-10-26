package tetrisgame;

import java.util.Random;

/**
 *
 * @author rhett
 */
public final class GameWorld {
    /*CONSTANTS*/

    /*For printing and clearing*/
    public static final int MOVING = 1;
    public static final int STATIC = 2;

    /*For moving pieces*/
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int UP = 4;

    /*World size*/
    public static final int COL = 10;//x
    public static final int ROW = 20;//y

    /*Default starting position*/
    private static final int DEFAULT_X = 4;
    private static final int DEFAULT_Y = -2;

    /*ATTRIBUTES*/
    private int[][] moving_layer;//2d array layer for moving piece
    private int[][] static_layer;//2d array layer for collied pieces

    private PieceSet piece_set;//ArrayList of Pieces

    private int curr_index;
    private Piece curr_piece;//Holds current piece

    private int prev_index;

    private int game_score; //Keeps track of the lines completed

    //QUEUE 3 stored pieces
    Random rand = new Random();//For spawning new piece

    /*METHODS*/
    public GameWorld() {
        //Initialize
        moving_layer = new int[ROW][COL];
        static_layer = new int[ROW][COL];
        piece_set = new PieceSet();

        curr_index = rand.nextInt(piece_set.size());
        curr_piece = new Piece(piece_set.get(curr_index).getStructure());
        prev_index = rand.nextInt(piece_set.size());

        resetGameScore();

        getCurr_piece().setPosition(DEFAULT_X, DEFAULT_Y);
        updateMovingLayer();
    }

    private void spawnNewPiece() {
        curr_index = prev_index;
        curr_piece = new Piece(piece_set.get(curr_index).getStructure());
        int temp = rand.nextInt(piece_set.size()); // Get Next piece

        //Checks if the next piece could be the same as the spawned piece.
        if (temp == prev_index) {
            if (rand.nextInt(3) == 0) {
                while (temp == prev_index) {
                    temp = rand.nextInt(piece_set.size());
                }
            }
        }

        prev_index = temp;

        getCurr_piece().setPosition(DEFAULT_X, DEFAULT_Y);
        updateMovingLayer();

        while (collision()) {
            getCurr_piece().setY(getCurr_piece().getY() - 1);
            updateMovingLayer();
        }
    }

    public void movePiece(int dir) {
        switch (dir) {
            case DOWN:
                getCurr_piece().setY(getCurr_piece().getY() + 1);
                updateMovingLayer();

                if (collision()) { //rollback move if collied
                    getCurr_piece().setY(getCurr_piece().getY() - 1);
                    updateMovingLayer();
                    moveToStatic();
                    game_score += checkCompletedLines();
                    spawnNewPiece();
                }
                break;
            case RIGHT:
                getCurr_piece().setX(getCurr_piece().getX() + 1);
                updateMovingLayer();

                if (collision()) {//rollback move if collied
                    getCurr_piece().setX(getCurr_piece().getX() - 1);
                    updateMovingLayer();
                }
                break;
            case LEFT:
                getCurr_piece().setX(getCurr_piece().getX() - 1);
                updateMovingLayer();

                if (collision()) {//rollback move if collied
                    getCurr_piece().setX(getCurr_piece().getX() + 1);
                    updateMovingLayer();
                }
                break;
        }
    }

    public void quickDrop() {
        while (!collision()) {
            getCurr_piece().setY(getCurr_piece().getY() + 1);
            updateMovingLayer();
        }

        getCurr_piece().setY(getCurr_piece().getY() - 1);
        updateMovingLayer();
        moveToStatic();
        game_score = checkCompletedLines();
        spawnNewPiece();
    }

    public void rotatePiece() {
        getCurr_piece().rotate();
        if (collision())//adjust move if collieded
        {
            getCurr_piece().rotate();
        }
        updateMovingLayer();
    }

    private boolean collision() {
        if (curr_piece.getX() >= 0 && curr_piece.getX() + curr_piece.getStructure().length <= COL && curr_piece.getY() + curr_piece.getStructure()[0].length <= ROW) {
            for (int y = 0; y < ROW; y++) {
                for (int x = 0; x < COL; x++) {
                    if (moving_layer[y][x] != 0 && static_layer[y][x] != 0) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            return true;
        }
    }

    private void moveToStatic() {
        for (int y = 0; y < ROW; y++) {
            for (int x = 0; x < COL; x++) {
                if (moving_layer[y][x] != 0 && static_layer[y][x] == 0) {
                    static_layer[y][x] = moving_layer[y][x];
                    moving_layer[y][x] = 0;
                }
            }
        }
    }

    public int checkCompletedLines() {//Remove and then Returns number of completed lines
        int lines_completed = 0;

        for (int y = 0; y < ROW; y++) {
            boolean complete = true;
            int x = 0;
            while (x < COL) {
                if (static_layer[y][x] == 0) {
                    complete = false;
                }
                x++;
            }

            if (complete) {
                lines_completed++;

                for (int Y = y - 1; Y >= 0; Y--) {
                    for (int X = 0; X < COL; X++) {
                        static_layer[Y + 1][X] = static_layer[Y][X];
                    }
                }
            }
        }

        return lines_completed;
    }

    private void printPieceToMovingLayer(Piece piece, int X, int Y, int pieceNum) {
        for (int y = 0; y < piece.getStructure()[0].length; y++) {
            for (int x = 0; x < piece.getStructure().length; x++) {
                if (Y + y >= 0 && X + x >= 0 && Y + y < ROW && X + x < COL) {
                    if (getCurr_piece().getStructure(x, y)) {
                        moving_layer[Y + y][X + x] = pieceNum;
                    }
                }
            }
        }
    }

    public void insertPiecePreview() {
        int tempX = getCurr_piece().getX();
        int tempY = getCurr_piece().getY();
        boolean stop = false;
        while (!stop && tempY + getCurr_piece().getStructure()[0].length - 1 < 20) {
            clearLayer(MOVING);
            printPieceToMovingLayer(curr_piece, tempX, tempY, -1);

            if (!collision()) {
                tempY++;
            } else {
                tempY--;
                clearLayer(MOVING);
                printPieceToMovingLayer(curr_piece, tempX, tempY, -1);
                stop = true;
            }
            //}
        }
    }

    public void updateMovingLayer() {
        clearLayer(MOVING);

        insertPiecePreview();

        printPieceToMovingLayer(curr_piece, curr_piece.getX(), curr_piece.getY(), curr_index + 1);

    }

    public void clearLayer(int l) {//MOVING or STATIC
        int[][] layer;
        switch (l) {
            case MOVING:
                layer = getMoving_layer();
                break;
            case STATIC:
                layer = getStatic_layer();
                break;
            default:
                layer = getMoving_layer();
        }
        for (int y = 0; y < ROW; y++) {
            for (int x = 0; x < COL; x++) {
                layer[y][x] = 0;
            }
        }
    }

    public void printLayer(int l) {//MOVING or STATIC
        int[][] layer;
        switch (l) {
            case MOVING:
                layer = getMoving_layer();
                break;
            case STATIC:
                layer = getStatic_layer();
                break;
            default:
                layer = getMoving_layer();
        }
        for (int y = 0; y < ROW; y++) {
            for (int x = 0; x < COL; x++) {
                System.out.print(layer[y][x]);
            }
            System.out.println();
        }
    }

    public final void resetGameScore() {
        game_score = 0;
    }

    public int[][] getMoving_layer() {
        return moving_layer;
    }

    public int[][] getStatic_layer() {
        return static_layer;
    }

    public final Piece getCurr_piece() {
        return curr_piece;
    }

    public int getPrev_index() {
        return prev_index;
    }

    public PieceSet getPieceSet() {
        return piece_set;
    }

    public int getGameScore() {
        return game_score;
    }
}
