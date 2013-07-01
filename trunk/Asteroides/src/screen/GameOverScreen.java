package screen;

import framework.Key;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import framework.Util;
import framework.audio.Sound;
import framework.screen.Screen;
import game.Global;
import java.awt.Color;
import java.awt.Font;

public class GameOverScreen extends Screen {

    String s = null;
    Image bgImage;

    public GameOverScreen() {
        bgImage = Util.loadImage(Global.IMG_GAMEOVER);
        new Sound("sounds/gameOver.mid", false).play();
    }

    public void paint(Graphics g) {
        g.drawImage(bgImage, 0, 0, null);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        if (Global.score > 0) {
            s = "Você fez " + Global.score + " pontos.";
        }
        g.drawString(s, Util.centerStringX(s, g), Util.centerStringY(s, g) + 70);
        String continuar = "Pressione espaço para voltar ao MENU";
        g.drawString(continuar, Util.centerStringX(continuar, g), Util.centerStringY(continuar, g) + 130);
    }

    public void update() {
        if (Key.isTyped(KeyEvent.VK_SPACE)) {
            // Volta ao menu
            Screen.setCurrentScreen(new TitleScreen());
        } else if (Key.isDown(KeyEvent.VK_ESCAPE)) {
            // sair do jogo
            System.exit(0);
        }
    }
}
