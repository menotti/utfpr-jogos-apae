package screen;

import framework.Key;
import framework.screen.Screen;
import framework.Util;
import framework.screen.ScreenUtil;
import game.Global;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

/**
 * 
 * @author Rodrigo Kuroda
 */
class GameFinishScreen extends Screen {

    Image bgImage;
    boolean flag = true;

    public GameFinishScreen() {
        bgImage = Util.loadImage(Global.IMG_GAMEWIN);
    }

    public void paint(Graphics g) {
        ScreenUtil.clear(g);
        g.setFont(Global.font);
        g.setColor(Color.WHITE);
        g.drawString("Parabéns. Você repondeu todas as perguntas!", 10, Util.centerY(0) - 70);
        g.drawString("Respondeu " + Global.corretas + " perguntas certas.", 10, Util.centerY(0) -40);
        g.drawString("Respondeu " + Global.incorretas + " perguntas erradas.", 10, Util.centerY(0) - 10);
        g.drawString("Fez " + Global.score + " pontos.", 10, Util.centerY(0) + 50);
        g.drawString("Pressione espaço para ir ao MENU...", 10, Util.centerY(0) + 80);

    }

    public void update() {
        if (Key.isTyped(KeyEvent.VK_SPACE)) {
            // Volta para o jogo
            Screen.setCurrentScreen(new MenuScreen());
        } else if (Key.isTyped(KeyEvent.VK_ESCAPE)) {
            // Sair do jogo
            System.exit(0);
        }
    }
}
