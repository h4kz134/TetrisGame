package tetrisgame;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.util.ImageUtil;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

public class MakeGame extends GameObject {

    //CONSTANTS
    private final int BLOCKSIZE = 32;
    private final int INGAME = 0;
    private final int PAUSE = 1;
    private final int GAMEOVER = 2;

    //GAME WORLD
    GameWorld world;

    //ASSETS
    private ArrayList<Sprite> gameBorder;
    private ArrayList<ArrayList<Sprite>> world_sprites;
    private Sprite nextPieceSprite;
    private Sprite storedPiece1;
    private Sprite storedPiece2;
    private Sprite storedPiece3;

    //Buttons
    private Sprite resumeButton;
    private Sprite restartButton;
    private Sprite exitButton;

    private Sprite menuSprite;
    private ImageBackground background;

    //IMAGE ASSETS
    private ArrayList<BufferedImage> blocks;
    private ArrayList<BufferedImage> nextBlocks;
    private BufferedImage pauseImage;
    private BufferedImage gameOverImage;

    //GAME SETTINGS
    private final GameSettings settings;
    private final int DELAY = 100;
    private final int KEY_DELAY = 12;

    //Counters
    private int keyTimer;
    private int timer;
    private int score;
    private int[] nextPieceIndex = new int[3];

    //VARIABLE STORAGE
    private int nextPieceID;
    private int gameStatus; //0 = in game. 1 = paused, 2 = game over

    int[][] updateGrid;

    public MakeGame(GameEngine ge, GameSettings settings) {
        super(ge);
        this.settings = settings;
    }

    @Override
    public void initResources() {
        gameBorder = new ArrayList();
        world_sprites = new ArrayList();
        blocks = new ArrayList();
        nextBlocks = new ArrayList();

        world = new GameWorld(settings.getHandicap(), new int[9]);

        //ADD IF EXTREME MODE LATER
        world.getPieceSet().addPiece(new boolean[][]{
            {true, true, true},
            {true, true, true}
        });
        world.getPieceSet().addPiece(new boolean[][]{
            {true, true, true, false},
            {true, false, true, true}
        });

        switch (settings.getGameMode()) {
            case GameSettings.CLASSIC:
                background = new ImageBackground(getImage("TetrisAssets/classicBackground.jpg"));
                break;
            case GameSettings.EXTREME:
                background = new ImageBackground(getImage("TetrisAssets/extremeBackground.png"));
                break;
        }
        
        pauseImage = getImage("TetrisAssets/pauseMenu.jpg");
        gameOverImage = getImage("TetrisAssets/gameOverMenu.jpg");

        initializeBorder();
        initializeBlockImages();
        initializeNextBlockImages();
        initializeBG();

        updateWorldSprites();
        nextPieceSprite = new Sprite(416, 32);
        storedPiece1 = new Sprite(416, 176);
        storedPiece2 = new Sprite(416, 256);
        storedPiece3 = new Sprite(416, 336);

        menuSprite = new Sprite(0, 0);

        resetGame();
    }

    private void resetGame() {
        timer = 1;
        score = 0;
        gameStatus = 0;
        updateNextPiecePreview();
    }

    @Override
    public void update(long l) {
        listenInput();

        if (gameStatus == INGAME) {
            if (world.checkGameover()) {
                gameStatus = GAMEOVER;
                menuSprite.setImage(gameOverImage);
                System.out.println("gameover");
            }
            timer = (timer + 1) % DELAY;

            if (timer == 0) {
                world.movePiece(GameWorld.DOWN);
                updateNextPiecePreview();
            }

            updateWorldSprites();//Refresh
            updateNextPiecePreview();
            updateStoredPieces();
        }
    }

    @Override
    public void render(Graphics2D gd) {
        gd.setColor(Color.black);
        gd.fillRect(0, 0, getWidth(), getHeight());
        background.render(gd);
        nextPieceSprite.render(gd);

        storedPiece1.render(gd);
        storedPiece2.render(gd);
        storedPiece3.render(gd);

        //Render the game border
        for (Sprite s : gameBorder) {
            s.render(gd);
        }

        //Render the world sprites
        for (ArrayList<Sprite> a : world_sprites) {
            for (Sprite s : a) {
                s.render(gd);
            }
        }
        
        if(gameStatus != INGAME) {
            menuSprite.render(gd);
        }

    }

    //Input Listener
    private void listenInput() {
        if (gameStatus == INGAME) {
            if ((keyPressed(KeyEvent.VK_W) || keyPressed(KeyEvent.VK_UP))) {
                world.rotatePiece();
            } else if ((keyDown(KeyEvent.VK_A) || keyDown(KeyEvent.VK_LEFT))) {
                if (keyTimer == 0) {
                    world.movePiece(GameWorld.LEFT);
                }
                keyTimer = (keyTimer + 1) % KEY_DELAY;
            } else if ((keyDown(KeyEvent.VK_D) || keyDown(KeyEvent.VK_RIGHT))) {
                if (keyTimer == 0) {
                    world.movePiece(GameWorld.RIGHT);
                }
                keyTimer = (keyTimer + 1) % KEY_DELAY;
            } else if ((keyDown(KeyEvent.VK_S) || keyDown(KeyEvent.VK_DOWN))) {
                if (keyTimer == 0) {
                    world.movePiece(GameWorld.DOWN);
                }
                keyTimer = (keyTimer + 1) % Math.min(KEY_DELAY, DELAY / 2);
            } else if (keyPressed(KeyEvent.VK_SPACE)) {
                world.quickDrop();
            } else if (keyPressed(KeyEvent.VK_SHIFT) && settings.getGameMode() == 1) {//DISABLE IN CLASSIC
                world.pushPiece();
            } else if (keyPressed(KeyEvent.VK_CONTROL) && settings.getGameMode() == 1) {//DISABLE IN CLASSIC
                world.pullPiece();
            } else if (keyPressed(KeyEvent.VK_ESCAPE)) {
                menuSprite.setActive(true);
                menuSprite.setImage(pauseImage);
                gameStatus = PAUSE;
            } else {
                keyTimer = 0;
            }

        } else if (gameStatus == PAUSE) {
            if (keyPressed(KeyEvent.VK_ESCAPE)) {
                menuSprite.setActive(false);
                gameStatus = INGAME;
            }

        } else if (gameStatus == GAMEOVER) {
            if (keyPressed(KeyEvent.VK_ESCAPE)) {
                gameStatus = INGAME;
                resetGame();
                world.resetGame(settings.getHandicap());
            }
        }
    }

    //Initialize the border
    private void initializeBorder() {
        for (int y = 1; y <= 21; y++) {
            for (int x = 0; x <= 11; x++) {
                if (x == 0 || x == 11) {
                    gameBorder.add(new Sprite(getImage("TetrisAssets/BorderBlock.png"), x * BLOCKSIZE, y * BLOCKSIZE));
                }
            }
        }
    }

    //Initialize the background
    private void initializeBG() {
        for (int y = 0; y < GameWorld.ROW; y++) {
            world_sprites.add(new ArrayList());

            for (int x = 0; x < GameWorld.COL; x++) {
                world_sprites.get(y).add(
                        new Sprite(blocks.get(0), (x + 1) * BLOCKSIZE, (y + 1) * BLOCKSIZE));
            }
        }
    }

    private void updateNextPiecePreview() {
        int temp;
        if ((temp = world.getPrev_index()) != nextPieceID) {
            nextPieceID = temp;
            nextPieceSprite.setImage(nextBlocks.get(nextPieceID));
        }
    }

    private void updateStoredPieces() {
        LinkedList<Integer> storedIndex = world.getStoredIndex();
        int size = storedIndex.size();

        if (size >= 1) {
            if (nextPieceIndex[0] != storedIndex.get(0)) {
                nextPieceIndex[0] = storedIndex.get(0);
                storedPiece1.setImage(ImageUtil.resize(nextBlocks.get(storedIndex.get(0)), 64, 64));
            }
        } else {
            if (nextPieceIndex[0] != 9) {
                nextPieceIndex[0] = 9;
                storedPiece1.setImage(ImageUtil.resize(nextBlocks.get(9), 64, 64));
            }
        }

        if (size >= 2) {
            if (nextPieceIndex[1] != storedIndex.get(1)) {
                nextPieceIndex[1] = storedIndex.get(1);
                storedPiece2.setImage(ImageUtil.resize(nextBlocks.get(nextPieceIndex[1]), 64, 64));
            }
        } else {
            if (nextPieceIndex[1] != 9) {
                nextPieceIndex[1] = 9;
                storedPiece2.setImage(ImageUtil.resize(nextBlocks.get(9), 64, 64));
            }
        }

        if (size >= 3) {
            if (nextPieceIndex[2] != storedIndex.get(2)) {
                nextPieceIndex[2] = storedIndex.get(2);
                storedPiece3.setImage(ImageUtil.resize(nextBlocks.get(nextPieceIndex[2]), 64, 64));
            }
        } else {
            if (nextPieceIndex[2] != 9) {
                nextPieceIndex[2] = 9;
                storedPiece3.setImage(ImageUtil.resize(nextBlocks.get(9), 64, 64));
            }
        }
    }

    //Update world sprites
    private void updateWorldSprites() {
        for (int y = 2; y < GameWorld.ROW; y++) {
            for (int x = 0; x < GameWorld.COL; x++) {
                world_sprites.get(y - 2).get(x).setImage(blocks.get(world.getStatic_layer()[y][x] + 1));
                if (world.getMoving_layer()[y][x] != 0) {
                    world_sprites.get(y - 2).get(x).setImage(blocks.get(world.getMoving_layer()[y][x] + 1));
                }
            }
        }
    }

    //Initialize the block images.
    private void initializeBlockImages() {
        blocks.add(getImage("TetrisAssets/PrevBlock.png"));
        blocks.add(getImage("TetrisAssets/BGBlock.png"));
        blocks.add(getImage("TetrisAssets/Block1.png"));
        blocks.add(getImage("TetrisAssets/Block2.png"));
        blocks.add(getImage("TetrisAssets/Block3.png"));
        blocks.add(getImage("TetrisAssets/Block4.png"));
        blocks.add(getImage("TetrisAssets/Block5.png"));
        blocks.add(getImage("TetrisAssets/Block6.png"));
        blocks.add(getImage("TetrisAssets/Block7.png"));
        blocks.add(getImage("TetrisAssets/Block8.png"));
        blocks.add(getImage("TetrisAssets/Block9.png"));
        blocks.add(getImage("TetrisAssets/Block10.png"));
        blocks.add(getImage("TetrisAssets/Block11.png"));
        blocks.add(getImage("TetrisAssets/Block12.png"));
    }

    private void initializeNextBlockImages() {
        nextBlocks.add(getImage("TetrisAssets/BlockPreview1.png"));
        nextBlocks.add(getImage("TetrisAssets/BlockPreview2.png"));
        nextBlocks.add(getImage("TetrisAssets/BlockPreview3.png"));
        nextBlocks.add(getImage("TetrisAssets/BlockPreview4.png"));
        nextBlocks.add(getImage("TetrisAssets/BlockPreview5.png"));
        nextBlocks.add(getImage("TetrisAssets/BlockPreview6.png"));
        nextBlocks.add(getImage("TetrisAssets/BlockPreview7.png"));
        nextBlocks.add(getImage("TetrisAssets/BlockPreview8.png"));
        nextBlocks.add(getImage("TetrisAssets/BlockPreview9.png"));
        nextBlocks.add(getImage("TetrisAssets/BlockPreview.png"));
    }
}
