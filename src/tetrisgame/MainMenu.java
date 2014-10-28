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
    private Sprite ratioButton;

    public MainMenu(GameEngine ge, GameSettings settings) {
        super(ge);
        this.settings = settings;
    }

    @Override
    public void initResources() {
        menuState = 0;
    }

    @Override
    public void update(long l) {
        if (click()) {
            if (this.checkPosMouse(classicButton, true)) {
                settings.setGameMode(GameSettings.CLASSIC);
                parent.nextGameID = 1;
                finish();
            } else if (checkPosMouse(extremeButton, true)) {
                settings.setGameMode(GameSettings.EXTREME);
                parent.nextGameID = 1;
                finish();
            } else if(checkPosMouse(optionsButton, true)) {
                menuState = OPTIONS_MENU;
            }
        }
    }

    @Override
    public void render(Graphics2D gd) {

    }

    private void checkInput() {

    }
}
