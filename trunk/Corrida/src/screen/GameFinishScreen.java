/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package screen;

import framework.Background;
import framework.Key;
import framework.Util;
import framework.screen.Screen;
import game.Global;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author Luiz Philipe
 */
public class GameFinishScreen extends Screen {

    Background bg;

    public GameFinishScreen() {
        bg = new Background(Util.loadImage(Global.IMG_GAMEFINISH), Background.SCROLL_NONE);

    }

    @Override
    public void update() {
        bg.update();

        if (Key.isDown(KeyEvent.VK_SPACE)) {
            Screen.setCurrentScreen(new GameCreditsScreen());
        } else if (Key.isDown(KeyEvent.VK_ESCAPE)) {
            Screen.setCurrentScreen(new GameCreditsScreen());
        } else if (Key.isDown(KeyEvent.VK_ENTER)) {
            Screen.setCurrentScreen(new GameCreditsScreen());
        }
    }

    @Override
    public void paint(Graphics g) {
        bg.paint(g);
        g.setFont(new Font("Comic Sans Ms", Font.BOLD, 48));
        g.setColor(Color.yellow);
        g.drawString("Você fez " + Global.score + " pontos.", 200, 400);
    }

}
