package tetrisgame;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Sprite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MakeGame extends GameObject {
    //CONSTANTS
    private final int BLOCKSIZE = 32;
    
    //GAME WORLD
    GameWorld world;
    
    //ASSETS
    private ArrayList<Sprite> gameBorder;
    private ArrayList<ArrayList<Sprite>> world_sprites;
    
    //IMAGE ASSETS
    private ArrayList<BufferedImage> blocks;
    
    //GAME SETTINGS
    private final int delay = 10;
    private int timer;

    public MakeGame(GameEngine ge) {
        super(ge);
    }

    @Override
    public void initResources() {
        gameBorder = new ArrayList();
        world_sprites = new ArrayList();
        blocks = new ArrayList();
        
        world = new GameWorld();
                
        initializeBorder();
        initializeBlockImages();
        initializeBG();
        
        updateWorldSprites();
        
        timer = 0;
    }

    @Override
    public void update(long l) {
        timer = (timer + 1) % delay;
    }

    @Override
    public void render(Graphics2D gd) {
        //Render the game border
        for (Sprite s : gameBorder) {
            s.render(gd);
        }

        //Render the world sprites
        for (ArrayList<Sprite> a : world_sprites) {
            for(Sprite s: a){
                s.render(gd);
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
        for (int y = 0; y < 20; y++) {
            world_sprites.add(new ArrayList<Sprite>());
            
            for (int x = 0; x < 10; x++) {
                world_sprites.get(y).add(
                        new Sprite(blocks.get(0), (x+1) * BLOCKSIZE, (y+1) * BLOCKSIZE));
            }
        }
    }

    //Update world sprites
    private void updateWorldSprites() {
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 10; x++) {
                if(world.getStatic_layer()[y][x] != 0)
                    world_sprites.get(y).get(x).setImage(blocks.get(world.getStatic_layer()[y][x]));
                if(world.getMoving_layer()[y][x] != 0)
                    world_sprites.get(y).get(x).setImage(blocks.get(world.getMoving_layer()[y][x]));
            }
        }
    }

    //Initialize the block images.
    private void initializeBlockImages() {
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
}