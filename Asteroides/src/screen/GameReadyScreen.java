/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import framework.Key;
import framework.Util;
import framework.internacionalization.Internacionalization;
import framework.screen.Screen;
import framework.screen.ScreenUtil;
import framework.text.Text;
import game.GameControl;
import game.Global;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import objects.Nave;

/**
 *
 * @author Rodrigo Kuroda
 */
public class GameReadyScreen extends Screen {

    //Image bgImage;
    Nave nave;
    Image img = Util.loadImage(Global.IMG_GAMEREADY);

    public GameReadyScreen() {
        //bgImage = Util.loadImage(Global.IMG_BACK);
        nave = new Nave();
    }

    @Override
    public void update() {
        if (Key.isTyped(KeyEvent.VK_SPACE)) {
            Screen.setCurrentScreen(new GameScreen());
        } else if (Key.isDown(KeyEvent.VK_ESCAPE)) {
            Screen.setCurrentScreen(new TitleScreen());
        }
    }

    @Override
    public void paint(Graphics g) {
        ScreenUtil.clear(g);
        //g.drawImage(bgImage, 0, 0, null);
        g.drawImage(img, 0, 0, null);
        nave.paint(g);

        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.setColor(Color.white);
        String level = Internacionalization.get("Level") + " " + Global.stage;
        g.drawString(level, Util.centerStringX(level, g) - 15, Util.centerStringY(level, g)- 190);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        List<String> wrap = new ArrayList<String>();
        if (GameControl.stageObject.indexOf(" e ") > 0) {
            wrap.add(GameControl.stageObject.substring(0, GameControl.stageObject.indexOf(" e ")));
            wrap.add(GameControl.stageObject.substring(GameControl.stageObject.indexOf(" e ")));
        } else {
            wrap.add(GameControl.stageObject);
        }
        for (int i = 0; i < wrap.size(); i++) {
            g.drawString(wrap.get(i), Util.centerStringX(wrap.get(i), g),
                345 + (40 * i));
        }
        String press = Internacionalization.get("Continuar");
        g.drawString(press, Util.centerStringX(press, g), Util.centerStringY(press, g) + 40);
    }
}
