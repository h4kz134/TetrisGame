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

    private int menuState;  //0 = Main, 1 = Options
    private ImageBackground background;
    private Sprite classicButton;
    private Sprite extremeButton;
    private Sprite optionsButton;
    private Sprite exitButton;
    private Sprite ratioButton;

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
        if (menuState == MAIN_MENU) {
            background.render(gd);
            classicButton.render(gd);
            extremeButton.render(gd);
            optionsButton.render(gd);
        } else if (menuState == OPTIONS_MENU) {
            exitButton.render(gd);
        }
    }

    private void checkInput() {
        if (click()) {
            if (menuState == MAIN_MENU) {
                if (this.checkPosMouse(classicButton, true)) {
                    settings.setGameMode(GameSettings.CLASSIC);
                    parent.nextGameID = 1;
                    finish();
                } else if (checkPosMouse(extremeButton, true)) {
                    settings.setGameMode(GameSettings.EXTREME);
                    parent.nextGameID = 1;
                    finish();
                } else if (checkPosMouse(optionsButton, true)) {
                    menuState = OPTIONS_MENU;
                }
            } else if (menuState == OPTIONS_MENU) {

            }
        }
    }

    public void updateButtons(long l) {
        if (checkPosMouse(classicButton, true)) {
            classicButton.setImage(classicHighlight);
            classicButton.update(l);
        } else {
            classicButton.setImage(classicImage);
            classicButton.update(l);
        }

        if (checkPosMouse(extremeButton, true)) {
            extremeButton.setImage(extremeHighlight);
            extremeButton.update(l);
        } else {
            extremeButton.setImage(extremeImage);
            extremeButton.update(l);
        }

        if (checkPosMouse(optionsButton, true)) {
            optionsButton.setImage(optionsHighlight);
            optionsButton.update(l);
        } else {
            optionsButton.setImage(optionsImage);
            optionsButton.update(l);
        }
    }

    private void initializeSprites() {
        classicButton = new Sprite(192, 192);
        extremeButton = new Sprite(192, 256);
        optionsButton = new Sprite(192, 320);
        exitButton = new Sprite(192, 384);
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
    }
}
