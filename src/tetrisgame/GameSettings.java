package tetrisgame;

import java.util.Random;

public class GameSettings {

    public static final int CLASSIC = 0;
    public static final int EXTREME = 1;

    private int[] blockRatio = new int[9];
    private int speed;
    private boolean blockPreview;
    private int handicap;
    private int gameMode; //Game mode 0 = classic, game mode 1 = extreme
    int[][] gameWorld = new int[22][10];
    Random rand = new Random(); //For spawning new piece

    public GameSettings() {
        for (int i = 0; i < blockRatio.length; i++) {
            blockRatio[i] = 1;
        }
        gameMode = 1;
        handicap = 1;
    }

    private void setRow(int[][] gameWorld, int i, Random rand) {
        int zeroIndex = rand.nextInt(10);
        for (int j = 0; j < 10; j++) {
            if (zeroIndex != j) {
                gameWorld[i][j] = rand.nextInt(3);
            } else {
                gameWorld[i][j] = 0;
            }
        }
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public int getGameMode() {
        return gameMode;
    }

    public int[] getBlockRatio() {
        return blockRatio;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isBlockPreview() {
        return blockPreview;
    }

    public void setBlockPreview(boolean blockPreview) {
        this.blockPreview = blockPreview;
    }

    public int[][] getHandicap() {
        switch (handicap) {
            case 1:
                for (int i = 21; i > 19; i--) {
                    setRow(gameWorld, i, rand);
                }
                break;
            case 2:
                for (int i = 21; i > 17; i--) {
                    setRow(gameWorld, i, rand);
                }
                break;
            case 3:
                for (int i = 21; i > 15; i--) {
                    setRow(gameWorld, i, rand);
                }
                break;
        }
        return gameWorld;
    }

    public void setHandicap(int handicap) {
        this.handicap = handicap;
    }

    //Line
    public void setLinePieceRatio(int ratio) {
        blockRatio[0] = ratio;
    }
    //Left L

    public void setLeftLPieceRatio(int ratio) {
        blockRatio[1] = ratio;
    }

    public void setRightLPieceRatio(int ratio) {
        blockRatio[2] = ratio;
    }

    public void setSquarePieceRatio(int ratio) {
        blockRatio[3] = ratio;
    }

    public void setLeftSPieceRatio(int ratio) {
        blockRatio[4] = ratio;
    }

    public void setTPieceRatio(int ratio) {
        blockRatio[5] = ratio;
    }

    public void setRightSPieceRatio(int ratio) {
        blockRatio[6] = ratio;
    }

    public void setCustomPiece1Ratio(int ratio) {
        blockRatio[7] = ratio;
    }

    public void setCustomPiece2Ratio(int ratio) {
        blockRatio[8] = ratio;
    }
    //Right L
    //Square
    //LeftZ
    //T
    //RightZ
}
