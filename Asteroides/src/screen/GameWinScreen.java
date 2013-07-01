package screen;

import framework.Key;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import framework.Util;
import framework.audio.Sound;
import framework.screen.Screen;
import game.Global;
import game.GameControl;
import java.awt.Color;
import java.awt.Font;

public class GameWinScreen extends Screen {

    Image bgImage;
    static final Sound clear = new Sound("sounds/clear.mid", false);

    public GameWinScreen() {
        bgImage = Util.loadImage(Global.IMG_GAMEWIN);
        clear.play();
    }

    public void paint(Graphics g) {
        // Dar mensagem de fase concluida
        g.drawImage(bgImage, 0, 0, null);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.setColor(Color.black);
        String score = "Pontos: " + Global.score;
        g.drawString(score, Util.centerStringX(score, g), 330);
        //score = "Bônus: 0";
        //g.drawString(score, Util.centerStringX(score, g), 400);
    }

    public void update() {
        if (Key.isTyped(KeyEvent.VK_SPACE)) {
            GameControl.update(); // Atualiza a dificuldade
            System.gc();
            System.gc();
            System.gc();
            //Volta para o jogo
            Screen.setCurrentScreen(new GameReadyScreen());
        } else
        if (Key.isDown(KeyEvent.VK_ESCAPE)) {
            // sair do jogo
            System.exit(0);
        }
    }
}
