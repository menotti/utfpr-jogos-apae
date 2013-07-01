package screen;

import framework.Key;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import framework.Util;
import framework.screen.Screen;
import framework.screen.ScreenUtil;
import game.Global;
import java.awt.Color;

public class GameWinScreen extends Screen {

    //Image bgImage;
    public GameWinScreen() {
        //bgImage = Util.loadImage(Global.IMG_GAMEWIN);
    }

    @Override
    public void update() {
        if (Key.isDown(KeyEvent.VK_SPACE)) {
            Screen.setCurrentScreen(new GameScreen());
        } else if (Key.isDown(KeyEvent.VK_ESCAPE)) {
            // sair do jogo
            System.exit(0);
        }
    }

    @Override
    public void paint(Graphics g) {
        ScreenUtil.clear(g);
        g.setFont(Global.font);
        g.setColor(Color.blue);
        g.drawString("Pressione espaço para ir para pergunta " + Global.level, 100, Util.centerY(0));
    }
}
