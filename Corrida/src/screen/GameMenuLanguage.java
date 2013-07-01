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
import java.util.Locale;

/**
 *
 * @author Luiz Philipe
 */
public class GameMenuLanguage extends Screen implements MenuListener {

    Menu menu;
    String itens[] = {Internacionalization.get("português"),
                    Internacionalization.get("inglês"),
                    Internacionalization.get("voltar")};
    Background bg = new Background(Util.loadImage(Global.IMG_BG_MENU), Background.SCROLL_NONE);

    public GameMenuLanguage() {
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
        switch (menuIndex){
            case 0:
                Internacionalization.setLocale(new Locale("pt", "BR"));
                Screen.setCurrentScreen(new GameMenu());
                break;
            case 1:
                Internacionalization.setLocale(new Locale("en", "US"));
                Screen.setCurrentScreen(new GameMenu());
                break;
            case 2:
                Screen.setCurrentScreen(new GameMenu());
                break;           
        }
    }



}
