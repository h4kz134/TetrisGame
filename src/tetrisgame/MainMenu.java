package tetrisgame;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Sprite;
import java.awt.Graphics2D;

public class MainMenu extends GameObject {

    private final GameSettings settings;
    private final int MAIN_MENU = 0;
    private final int OPTIONS_MENU = 1;

    private int menuState;  //0 = Main, 1 = Options
    private Sprite classicButton;
    private Sprite extremeButton;
    private Sprite optionsButton;
    private Sprite exitButton;
    private Sprite ratioButton;

    public MainMenu(GameEngine ge, GameSettings settings) {
        super(ge);
        this.settings = settings;
    }

    @Override
    public void initResources() {
        menuState = MAIN_MENU;
    }

    @Override
    public void update(long l) {
        checkInput();
    }

    @Override
    public void render(Graphics2D gd) {
        if(menuState == MAIN_MENU) {
            classicButton.render(gd);
            extremeButton.render(gd);
            optionsButton.render(gd);
        } else if(menuState == OPTIONS_MENU) {
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
            } else if(menuState == OPTIONS_MENU) {
                
            }
        }
    }
}
