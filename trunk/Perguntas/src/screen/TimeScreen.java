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
public class TimeScreen extends Screen implements MenuListener {

    private Menu menu;
    private Image img = Util.loadImage(Global.IMG_MOUSE);
    private Background bg = new Background(Util.loadImage(Global.IMG_MENU_BG), Background.SCROLL_NONE);

    public TimeScreen() {
        Mouse.setCursor(img);
        String itens[] = new String[]{"0:30 min", "1:00 min", "1:30 min", "2:00 min", "2:30 min", "3:00 min", "Voltar"};

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
        menu.paint(g);
        bg.paint(g);
    }

    @Override
    public void menuAction(int menuIndex) {
        switch (menuIndex) {
            case 0: {
                Global.time = 30;
                Screen.setCurrentScreen(new ConfigurationScreen());
            }
            break;
            case 1: {
                Global.time = 60;
                Screen.setCurrentScreen(new ConfigurationScreen());
            }
            break;
            case 2: {
                Global.time = 90;
                Screen.setCurrentScreen(new ConfigurationScreen());
            }
            break;
            case 3: {
                Global.time = 120;
                Screen.setCurrentScreen(new ConfigurationScreen());
            }
            break;
            case 4: {
                Global.time = 150;
                Screen.setCurrentScreen(new ConfigurationScreen());
            }
            break;
            case 5: {
                Global.time = 180;
                Screen.setCurrentScreen(new ConfigurationScreen());
            }
            break;
            case 6:
                Screen.setCurrentScreen(new ConfigurationScreen());
        }
    }
}
