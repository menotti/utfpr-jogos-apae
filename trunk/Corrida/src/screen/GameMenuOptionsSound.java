/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package screen;

import framework.Background;
import framework.Mouse;
import framework.Util;
import framework.internacionalization.Internacionalization;
import framework.menu.Menu;
import framework.menu.MenuListener;
import framework.screen.Screen;
import game.Global;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Luiz Philipe
 */
public class GameMenuOptionsSound extends Screen implements MenuListener {

    Menu menu;
    String itens[] = {Internacionalization.get("ativar"),
                    Internacionalization.get("desativar"),
                    Internacionalization.get("voltar")};

    boolean flag = false;
    Background bg = new Background(Util.loadImage(Global.IMG_BG_MENU), Background.SCROLL_NONE);

    public GameMenuOptionsSound() {
        Mouse.setCursor(Util.loadImage(Global.IMG_CURSOR));
        menu = new Menu(355, 280, itens, Global.MENU_FONT, this);
        menu.setDefaultColor(new Color(0x0080FF));
        menu.setSelectorColor(new Color(0x0080FF));
        menu.setSelectedColor(Color.BLUE);
        menu.setSelectorText('>');
    }

    @Override
    public void update() {
        bg.update();
        menu.update();
    }

    @Override
    public void paint(Graphics g) {
        //Screen.clear(g, Color.BLACK);
        bg.paint(g);
        menu.paint(g);
    }

    public void menuAction(int menuIndex) {
        switch(menuIndex){
            case 0:
                Global.sound = true;
                Screen.setCurrentScreen(new GameMenu());
                break;
            case 1:
                Global.sound = false;
                Global.stopAllSongs();
                Screen.setCurrentScreen(new GameMenu());
                break;
            case 2:
                Screen.setCurrentScreen(new GameMenu());
                break;
        }
    }

}
