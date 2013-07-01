/*
 * http://www.exampledepot.com/egs/java.awt/AntiAlias.html
 * 
 */
package screen;

import framework.Key;
import framework.screen.Screen;
import framework.Util;
import framework.screen.ScreenUtil;
import game.Global;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author Rodrigo Kuroda
 */
public class GameReadyScreen extends Screen {

    @Override
    public void update() {
        if (Key.isTyped(KeyEvent.VK_SPACE)) {
            Screen.setCurrentScreen(new GameScreen());
        } else if (Key.isDown(KeyEvent.VK_ESCAPE)) {
            Screen.setCurrentScreen(new MenuScreen());
        }
    }

    @Override
    public void paint(Graphics g) {
        ScreenUtil.clear(g);
        g.setFont(Global.font);
        g.setColor(Color.WHITE);
        g.drawString("Pressione espaço para responder à " + (Global.level + 1) + "ª pergunta.", 70, Util.centerY(0));
    }
}
