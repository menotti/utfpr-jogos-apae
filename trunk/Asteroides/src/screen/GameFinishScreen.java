/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import framework.Key;
import framework.Util;
import framework.internacionalization.Internacionalization;
import framework.screen.Screen;
import game.Global;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.text.MessageFormat;

/**
 *
 * @author Rodrigo Kuroda
 */
class GameFinishScreen extends Screen {
    Image bgImage;

    public GameFinishScreen() {
        bgImage = Util.loadImage(Global.IMG_GAMEWIN);
    }

    @Override
    public void paint(Graphics g) {
        // Dar mensagem de fase concluida
        g.drawImage(bgImage, 0, 0, null);
        String gameFinish = MessageFormat.format(Internacionalization.get("Finalizado"), Global.score);
        String finish2 = MessageFormat.format(Internacionalization.get("Finalizado2"), Global.score);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.setColor(Color.BLACK);
        String parabens = Internacionalization.get("Parabens");
        g.drawString(parabens, Util.centerStringX(parabens, g) + 10, Util.centerStringY(parabens, g) - 50);
        g.drawString(finish2, Util.centerStringX(parabens, g) + 10, -20);
        g.drawString(gameFinish, Util.centerStringX(gameFinish, g), Util.centerStringY(gameFinish, g)+20);
        g.setColor(Color.white);
        g.drawString(Internacionalization.get("Continuar"), Util.centerStringX(Internacionalization.get("Continuar"), g), Util.centerStringY(gameFinish, g)+130);
    }

    @Override
    public void update() {
        if (Key.isDown(KeyEvent.VK_SPACE)) {
            //Volta para o jogo
            Screen.setCurrentScreen(new CreditsScreen());
        } else if (Key.isDown(KeyEvent.VK_ESCAPE)) {
            Screen.setCurrentScreen(new TitleScreen());
        }
    }
}
