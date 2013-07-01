package screen;

import framework.Key;
import java.awt.Color;
import java.awt.Graphics;

import framework.screen.Screen;
import framework.Util;
import game.Global;
import java.awt.Font;
import java.awt.event.KeyEvent;
import objects.Levels;

public class GameScreen extends Screen {

    Levels levels;
    int incrementDelay;
    static int count = 0;

    public GameScreen() {
        // Ocultar o ponteiro do mouse
        //Mouse.setCursor(Util.loadImage(Global.IMG_BALL));

        incrementDelay = 0;
        Global.hit = false;
        levels = new Levels();
    }

    public void paint(Graphics g) {
        // Limpa tela
        g.setColor(Color.white);
        g.fillRect(0, 0, Screen.getWidth(), Screen.getHeight());
        g.drawImage(Util.loadImage(Global.IMG_BACK), 0, 0, null);
        g.setColor(Color.black);

        // Desenha fundo
        //bgGame.paint(g);

        levels.paint(g);

        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.drawString("Pontos " + Global.score, Global.SCREEN_WIDTH - 190, Global.SCREEN_HEIGHT - 40);
        g.drawString("Pergunta " + (Global.level + 1) + "/" + Global.maxLevel, Global.SCREEN_WIDTH - 190, Global.SCREEN_HEIGHT - 60);
        g.drawString("Tempo " + Util.formatSeconds(Global.time, Util.MINUTE), Global.SCREEN_WIDTH - 190, Global.SCREEN_HEIGHT - 20);

        if (Global.hit && Global.tentativas < 3) {
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("Você acertou!", 60, Global.SCREEN_HEIGHT - 55);
            g.drawString("Pressione espaço para continuar...", 60, Global.SCREEN_HEIGHT - 25);

        } else if (Global.hit && Global.tentativas >= 3) {
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("Você errou!", 60, Global.SCREEN_HEIGHT - 55);
            g.drawString("Pressione espaço para continuar...", 60, Global.SCREEN_HEIGHT - 25);
        }
    }

    public void update() {

        levels.update();

        // Contador de tempo (pontuacao basesada no tempo e nos erros)
        if ((incrementDelay++ == 60) && !Global.hit && !(Global.tentativas > 3)) {
            Global.time--;
            incrementDelay = 0;
        }

        if (Global.time < 1 || (Global.tentativas >= 3 && !Global.hit)) {
            Global.hit = true;
        }
        if (Global.hit && Global.tentativas < 3) {
            if (Key.isTyped(KeyEvent.VK_SPACE)) {
                int score = Global.MAX_SCORE;

                /* Diminui metade da pontuacao a cada tentativa */
                for (int i = 1; i < Global.tentativas; i++) {
                    score /= Global.DIV;
                }
                Global.score += score + Global.time;
                Global.time = Global.MAX_TIME;
                Global.tentativas = 0;
                Global.level++;
                Global.corretas++;
                if (Global.level >= Global.maxLevel) {
                    Screen.setCurrentScreen(new GameFinishScreen());
                } else {
                    Screen.setCurrentScreen(new GameScreen());
                }
            }
        }
        if (Global.hit && Global.tentativas >= 3) {
            if (Key.isDown(KeyEvent.VK_SPACE)) {
                System.out.println("BB");
                Global.time = Global.MAX_TIME;
                Global.tentativas = 0;
                Global.level++;
                Global.incorretas++;
                if (Global.level >= Global.maxLevel) {
                    Screen.setCurrentScreen(new GameFinishScreen());
                } else {
                    Screen.setCurrentScreen(new GameScreen());
                }
            }
            //System.out.println("EE");
        }
        // Quanto teclar o ESC volta para o menu
        if (Key.isTyped(KeyEvent.VK_ESCAPE)) {
            Screen.setCurrentScreen(new MenuScreen());
        }
    }
}
