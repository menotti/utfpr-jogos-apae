/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import framework.Util;
import framework.internacionalization.Internacionalization;
import framework.menu.Menu;
import framework.menu.MenuListener;
import framework.screen.Screen;
import framework.screen.ScreenUtil;
import game.Global;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Locale;

/**
 *
 * @author Rodrigo Kuroda
 */
class LanguageScreen extends Screen implements MenuListener {

    Menu menu;
    Image title = Util.loadImage(Global.IMG_TITLE);

    public LanguageScreen() {
        String[] itens = new String[]{Internacionalization.get("Português"),
            Internacionalization.get("Inglês"),
            Internacionalization.get("Voltar")};
        menu = new Menu(itens, -1, 280, 200, this);
        menu.setTransparent(true);
        menu.setFont(new Font("Arial", Font.BOLD, 20));
        menu.setColorFont(new Color(255, 255, 255));
    }

    @Override
    public void update() {
        menu.update();
    }

    @Override
    public void paint(Graphics g) {
        ScreenUtil.clear(g);
        g.drawImage(title, 0, 0, null);
        menu.paint(g);
    }

    public void menuAction(int menuIndex) {
        switch (menuIndex) {
            case 0:
                Global.locale = new Locale("pt", "BR");
                Internacionalization.setLocale(new Locale("pt", "BR"));
                break;
            case 1:
                Global.locale = new Locale("en", "US");
                Internacionalization.setLocale(new Locale("en", "US"));
                break;
            case 2:
                Screen.setCurrentScreen(new TitleScreen());
                break;
        }
    }
}
