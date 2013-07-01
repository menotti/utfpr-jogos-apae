package screen;

import framework.Key;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import framework.Util;
import framework.screen.Screen;
import game.Global;

public class GameOverScreen extends Screen {

    Image bgImage;

    public GameOverScreen() {
        Global.stopAllSongs();
        if(Global.sound){
            Global.gameOver.play();
        }

        bgImage = Util.loadImage(Global.IMG_GAMEOVER);
        Global.score = 0;
        Global.pista = 1;
        Global.life = 3;
        Global.level = 1;
        Global.fuelSpend = 6;
        Global.speed = (int) (7 * Global.progressao);
    }

    public void paint(Graphics g) {
        g.drawImage(bgImage, 0, 0, null);
    }

    public void update() {
        if (Key.isDown(KeyEvent.VK_SPACE)) {
            // reiniciar jogo
            Screen.setCurrentScreen(new GameMenu());
        } else
        if (Key.isDown(KeyEvent.VK_ESCAPE)) {
            // sair do jogo
            System.exit(0);
        }
    }
}
