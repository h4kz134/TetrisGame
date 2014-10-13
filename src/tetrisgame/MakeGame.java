package tetrisgame;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Sprite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MakeGame extends GameObject {

    //ASSETS
    private ArrayList<Sprite> GameBorder;
    private ArrayList<Sprite> GameBG;
    //IMAGE ASSETS
    private BufferedImage block1;
    private BufferedImage block2;
    private BufferedImage block3;
    private BufferedImage block4;
    private BufferedImage block5;
    private BufferedImage block6;
    private BufferedImage block7;
    private BufferedImage block8;
    private BufferedImage block9;
    private BufferedImage block10;
    private BufferedImage block11;
    private BufferedImage block12;

    public MakeGame(GameEngine ge) {
        super(ge);
    }

    @Override
    public void initResources() {
        GameBorder = new ArrayList();
        GameBG = new ArrayList();

        initializeBorder();
        initializeBG();
        initializeBlockImages();
    }

    @Override
    public void update(long l) {

    }

    @Override
    public void render(Graphics2D gd) {
        //Render the game border
        for (Sprite s : GameBorder) {
            s.render(gd);
        }

        //Render the game background
        for (Sprite s : GameBG) {
            s.render(gd);
        }
    }

    //Initialize the border
    private void initializeBorder() {
        for (int i = 1; i <= 21; i++) {
            for (int j = 0; j <= 11; j++) {
                if (j == 0 || j == 11) {
                    GameBorder.add(new Sprite(getImage("TetrisAssets/BorderBlock.png"), j * 32, i * 32));
                }
            }
        }
    }

    //Initialize the background
    private void initializeBG() {
        for (int i = 1; i <= 20; i++) {
            for (int j = 1; j <= 10; j++) {
                GameBG.add(new Sprite(getImage("TetrisAssets/BGBlock.png"), j * 32, i * 32));
            }
        }
    }

    //Initialize the block images.
    private void initializeBlockImages() {
        block1 = getImage("TetrisAssets/Block1.png");
        block2 = getImage("TetrisAssets/Block2.png");
        block3 = getImage("TetrisAssets/Block3.png");
        block4 = getImage("TetrisAssets/Block4.png");
        block5 = getImage("TetrisAssets/Block5.png");
        block6 = getImage("TetrisAssets/Block6.png");
        block7 = getImage("TetrisAssets/Block7.png");
        block8 = getImage("TetrisAssets/Block8.png");
        block9 = getImage("TetrisAssets/Block9.png");
        block10 = getImage("TetrisAssets/Block10.png");
        block11 = getImage("TetrisAssets/Block11.png");
        block12 = getImage("TetrisAssets/Block12.png");
    }
}
