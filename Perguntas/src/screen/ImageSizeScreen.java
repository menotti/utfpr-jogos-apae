/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import framework.Background;
import framework.Key;
import framework.Mouse;
import framework.screen.Screen;
import framework.Util;
import framework.menu.Menu;
import framework.menu.MenuListener;
import framework.screen.ScreenUtil;
import game.Global;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

/**
 *
 * @author Rodrigo Kuroda
 */
public class ImageSizeScreen extends Screen implements MenuListener {

    private Menu menu;
    private Image img = Util.loadImage(Global.IMG_MOUSE);
    private Background bg = new Background(Util.loadImage(Global.IMG_MENU_BG), Background.SCROLL_NONE);

    public ImageSizeScreen() {
        Mouse.setCursor(img);
        String itens[] = new String[]{"Normal", "100", "125", "150", "175", "200", "Voltar"};

        menu = new Menu(itens, -1, 150, 200, this);
        menu.setColorFont(Color.white);
        menu.setFont(new Font("Arial", Font.BOLD, 20));
        menu.setColorBack(new Color(255, 255, 255, 0));
        menu.setColorBorder(new Color(255, 255, 255, 0));
        menu.setSelectorImg(Util.loadImage(Global.IMG_SELECTOR));
    }

    @Override
    public void update() {
        menu.update();
        if (Key.isTyped(KeyEvent.VK_BACK_SPACE)) {
            Screen.setCurrentScreen(new ConfigurationScreen());
        }
    }

    @Override
    public void paint(Graphics g) {
        ScreenUtil.clear(g);
        bg.paint(g);
        menu.paint(g);
    }

    public void menuAction(int menuIndex) {
        switch (menuIndex) {
            case 0: {
                Global.imageHeight = -1;
                Global.imageWidth = -1;
            }
            case 1: {
                Global.imageWidth = Global.imageHeight = 100;
                Screen.setCurrentScreen(new ConfigurationScreen());
            }
            break;
            case 2: {
                Global.imageWidth = Global.imageHeight = 125;
                Screen.setCurrentScreen(new ConfigurationScreen());
            }
            break;
            case 3: {
                Global.imageWidth = Global.imageHeight = 150;
                Screen.setCurrentScreen(new ConfigurationScreen());
            }
            break;
            case 4: {
                Global.imageWidth = Global.imageHeight = 175;
                Screen.setCurrentScreen(new ConfigurationScreen());
            }
            break;
            case 5: {
                Global.imageWidth = Global.imageHeight = 200;
                Screen.setCurrentScreen(new ConfigurationScreen());
            }
            break;
            case 6:
                Screen.setCurrentScreen(new ConfigurationScreen());
        }
    }
}
