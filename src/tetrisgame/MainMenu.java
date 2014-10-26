package tetrisgame;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import java.awt.Graphics2D;

public class MainMenu extends GameObject {

    private int menuState;

    public MainMenu(GameEngine ge) {
        super(ge);
    }

    @Override
    public void initResources() {
        menuState = 0;
    }

    @Override
    public void update(long l) {

    }

    @Override
    public void render(Graphics2D gd) {

    }

    private void checkInput() {

    }
}
