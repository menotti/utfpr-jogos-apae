/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package screen;

import framework.Background;
import framework.GameEngine;
import framework.Key;
import framework.Util;
import framework.screen.Screen;
import game.Global;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author Luiz Philipe
 */
public class GameCreditsScreen extends Screen {

    Background bg;

    public GameCreditsScreen() {
        bg = new Background(Util.loadImage(Global.IMG_GAMECREDITS), Background.SCROLL_NONE);

    }

    @Override
    public void update() {
        bg.update();

        if (Key.isDown(KeyEvent.VK_SPACE)) {
            Screen.setCurrentScreen(new GameMenu());
        } else if (Key.isDown(KeyEvent.VK_ESCAPE)) {
            Screen.setCurrentScreen(new GameMenu());
        } else if (Key.isDown(KeyEvent.VK_ENTER)) {
            Screen.setCurrentScreen(new GameMenu());
        }
    }

    @Override
    public void paint(Graphics g) {
        bg.paint(g);
    }

}
