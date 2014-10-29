package tetrisgame;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.background.ImageBackground;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MainMenu extends GameObject {
    
    private final GameSettings settings;
    private final int MAIN_MENU = 0;
    private final int OPTIONS_MENU = 1;
    private final int DIFFICULTY_MENU = 2;
    
    private int menuState;  //0 = Main, 1 = Options
    private ImageBackground background;
    private Sprite classicButton;
    private Sprite extremeButton;
    private Sprite optionsButton;
    private Sprite exitButton;
    private Sprite easyButton;
    private Sprite mediumButton;
    private Sprite hardButton;
    private Sprite toggleStoreButton;
    private Sprite toggleHandicapButton;

    //Images
    private BufferedImage classicImage;
    private BufferedImage classicHighlight;
    private BufferedImage extremeImage;
    private BufferedImage extremeHighlight;
    private BufferedImage optionsImage;
    private BufferedImage optionsHighlight;
    private BufferedImage exitImage;
    private BufferedImage exitHighlight;
    private BufferedImage easyImage;
    private BufferedImage easyHighlight;
    private BufferedImage mediumImage;
    private BufferedImage mediumHighlight;
    private BufferedImage hardImage;
    private BufferedImage hardHighlight;
    private BufferedImage toggleStoreOn;
    private BufferedImage toggleStoreOff;
    private BufferedImage toggleHandicapOn;
    private BufferedImage toggleHandicapOff;
    
    public MainMenu(GameEngine ge, GameSettings settings) {
        super(ge);
        this.settings = settings;
    }
    
    @Override
    public void initResources() {
        menuState = MAIN_MENU;
        
        initializeImages();
        initializeSprites();
        
        background = new ImageBackground(getImage("TetrisAssets/templateblankfinal.jpg"));
        classicButton.setBackground(background);
        extremeButton.setBackground(background);
        optionsButton.setBackground(background);
        exitButton.setBackground(background);
    }
    
    @Override
    public void update(long l) {
        checkInput();
        updateButtons(l);
    }
    
    @Override
    public void render(Graphics2D gd) {
        background.render(gd);
        if (menuState == MAIN_MENU) {
            classicButton.render(gd);
            extremeButton.render(gd);
            optionsButton.render(gd);
            exitButton.render(gd);
        } else if (menuState == OPTIONS_MENU) {
            toggleStoreButton.render(gd);
            toggleHandicapButton.render(gd);
            exitButton.render(gd);
        } else if (menuState == DIFFICULTY_MENU) {
            easyButton.render(gd);
            mediumButton.render(gd);
            hardButton.render(gd);
            exitButton.render(gd);
        }
    }
    
    private void checkInput() {
        if (click()) {
            if (menuState == MAIN_MENU) {
                if (this.checkPosMouse(classicButton, true)) {
                    settings.setGameMode(GameSettings.CLASSIC);
                    menuState = DIFFICULTY_MENU;
                } else if (checkPosMouse(extremeButton, true)) {
                    settings.setGameMode(GameSettings.EXTREME);
                    menuState = DIFFICULTY_MENU;
                } else if (checkPosMouse(optionsButton, true)) {
                    menuState = OPTIONS_MENU;
                } else if (checkPosMouse(exitButton, true)) {
                    System.exit(0);
                }
            } else if (menuState == OPTIONS_MENU) {
                if (checkPosMouse(exitButton, true)) {
                    menuState = MAIN_MENU;
                } else if (checkPosMouse(toggleStoreButton, true)) {
                    settings.setToggleStore(!settings.isToggleStore());
                } else if (checkPosMouse(toggleHandicapButton, true)) {
                    settings.setToggleHandicap(!settings.isToggleHandicap());
                }
            } else if (menuState == DIFFICULTY_MENU) {
                if (this.checkPosMouse(easyButton, true)) {
                    settings.setHandicap(GameSettings.EASY);
                    settings.setSpeed(GameSettings.EASY);
                    parent.nextGameID = 1;
                    finish();
                } else if (checkPosMouse(mediumButton, true)) {
                    settings.setHandicap(GameSettings.MEDIUM);
                    settings.setSpeed(GameSettings.MEDIUM);
                    parent.nextGameID = 1;
                    finish();
                } else if (checkPosMouse(hardButton, true)) {
                    settings.setHandicap(GameSettings.HARD);
                    settings.setSpeed(GameSettings.HARD);
                    parent.nextGameID = 1;
                    finish();
                } else if (checkPosMouse(exitButton, true)) {
                    menuState = MAIN_MENU;
                }
            }
        }
    }
    
    public void updateButtons(long l) {
        if (menuState == MAIN_MENU) {
            if (checkPosMouse(classicButton, true)) {
                classicButton.setImage(classicHighlight);
            } else {
                classicButton.setImage(classicImage);
            }
            
            if (checkPosMouse(extremeButton, true)) {
                extremeButton.setImage(extremeHighlight);
            } else {
                extremeButton.setImage(extremeImage);
            }
            
            if (checkPosMouse(optionsButton, true)) {
                optionsButton.setImage(optionsHighlight);
            } else {
                optionsButton.setImage(optionsImage);
            }

            //DIFFICULTY MENU OPTIONS
        } else if (menuState == DIFFICULTY_MENU) {
            
            if (checkPosMouse(easyButton, true)) {
                easyButton.setImage(easyHighlight);
            } else {
                easyButton.setImage(easyImage);
            }
            
            if (checkPosMouse(mediumButton, true)) {
                mediumButton.setImage(mediumHighlight);
            } else {
                mediumButton.setImage(mediumImage);
            }
            
            if (checkPosMouse(hardButton, true)) {
                hardButton.setImage(hardHighlight);
            } else {
                hardButton.setImage(hardImage);
            }

            //OPTIONS MENU BUTTONS
        } else if (menuState == OPTIONS_MENU) {
            if (settings.isToggleStore()) {
                toggleStoreButton.setImage(toggleStoreOn);
            } else {
                toggleStoreButton.setImage(toggleStoreOff);
            }
            
            if (settings.isToggleHandicap()) {
                toggleHandicapButton.setImage(toggleHandicapOn);
            } else {
                toggleHandicapButton.setImage(toggleHandicapOff);
            }
        }
        
        if (checkPosMouse(exitButton, true)) {
            exitButton.setImage(exitHighlight);
        } else {
            exitButton.setImage(exitImage);
        }
    }
    
    private void initializeSprites() {
        classicButton = new Sprite(192, 192);
        extremeButton = new Sprite(192, 256);
        optionsButton = new Sprite(192, 320);
        exitButton = new Sprite(192, 448);
        
        easyButton = new Sprite(192, 192);
        mediumButton = new Sprite(192, 256);
        hardButton = new Sprite(192, 320);
        
        toggleStoreButton = new Sprite(192, 192);
        toggleHandicapButton = new Sprite(192, 256);
    }
    
    private void initializeImages() {
        //Images
        classicImage = getImage("TetrisAssets/buttons/classic.jpg");
        classicHighlight = getImage("TetrisAssets/buttons/highlight/classicsel.jpg");
        extremeImage = getImage("TetrisAssets/buttons/extreme.jpg");
        extremeHighlight = getImage("TetrisAssets/buttons/highlight/extremesel.jpg");
        optionsImage = getImage("TetrisAssets/buttons/options.jpg");
        optionsHighlight = getImage("TetrisAssets/buttons/highlight/optionsel.jpg");
        exitImage = getImage("TetrisAssets/buttons/exit.jpg");
        exitHighlight = getImage("TetrisAssets/buttons/highlight/exitsel.jpg");
        easyImage = getImage("TetrisAssets/buttons/easy.jpg");
        easyHighlight = getImage("TetrisAssets/buttons/highlight/easysel.jpg");
        mediumImage = getImage("TetrisAssets/buttons/med.jpg");
        mediumHighlight = getImage("TetrisAssets/buttons/highlight/medsel.jpg");
        hardImage = getImage("TetrisAssets/buttons/hard.jpg");
        hardHighlight = getImage("TetrisAssets/buttons/highlight/hardsel.jpg");
        toggleStoreOff = getImage("TetrisAssets/buttons/toggleStoreOff.jpg");
        toggleStoreOn = getImage("TetrisAssets/buttons/toggleStoreOn.jpg");
        toggleHandicapOn = getImage("TetrisAssets/buttons/toggleHandicapOn.jpg");
        toggleHandicapOff = getImage("TetrisAssets/buttons/toggleHandicapOff.jpg");
    }
}
