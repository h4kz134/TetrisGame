package tetrisgame;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.GameObject;
import java.awt.Dimension;

public class Main extends GameEngine {

    @Override
    public GameObject getGame(int i) {
        switch (i) {
            case 0:
                return new MakeGame(this);
            case 1:
                //return new MakeGame(this, settings, highScores);
        }
        return null;
        
    }
    
    public static void main(String[] args) {
        // GameEngine class creation is equal with Game class creation
        GameLoader game = new GameLoader();
        game.setup(new Main(), new Dimension(768, 672), false);
        game.start();
    }

}
