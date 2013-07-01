package game;

import framework.GameEngine;
import screen.GameMenu;

public class Main {

    public static void main(String[] args) {

        // se isto não for colocado, o jogo não roda em Linux (UBUNTU TESTED!)
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new GameEngine(Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT,
                        Global.FULL_SCREEN).start(new GameMenu());
            }
        });
    }
}
