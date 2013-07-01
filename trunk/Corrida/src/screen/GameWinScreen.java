package screen;

import framework.Key;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import framework.Util;
import framework.screen.Screen;
import game.Global;

public class GameWinScreen extends Screen {

    Image bgImage;

    public GameWinScreen() {
        Global.level++;
        Global.speed++;
        Global.stopAllSongs();
        if (Global.sound) {
            Global.levelCompleteSound.play();
        }

        if (Global.level == 5) {
            Global.fuelSpend = 6;
            Global.speed = (int) (7 * Global.progressao);
        }

        if (Global.fuelSpend <= 2) {
            Global.fuelSpend++;
        } else {
            Global.fuelSpend--;
        }

        bgImage = Util.loadImage(Global.IMG_GAMEWIN);

    }

    public void paint(Graphics g) {
        g.drawImage(bgImage, 0, 0, null);
    }

    public void update() {
        if (Key.isDown(KeyEvent.VK_SPACE)) {
            // continuar jogo
            if (Global.level >= 5) {
                Global.pista = 2;
            }

            if (Global.level == 10) {
                Screen.setCurrentScreen(new GameFinishScreen());
            } else {
                Screen.setCurrentScreen(new GameScreen());
            }


        } else if (Key.isDown(KeyEvent.VK_ESCAPE)) {
            // sair do jogo
            System.exit(0);
        }
    }
}
