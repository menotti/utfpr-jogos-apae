package screen;

import framework.Key;
import framework.Mouse;
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
import java.awt.event.KeyEvent;

/**
 *
 * @author Rodrigo Kuroda
 */
public class ConfigurationScreen extends Screen implements MenuListener {

    private Menu menu;
    private Image img = Util.loadImage(Global.CURSOR);
    Image title = Util.loadImage(Global.IMG_TITLE);

    public ConfigurationScreen() {
        Mouse.setCursor(img);
        String itens[] = new String[]{
            Internacionalization.get("Som"),
            Internacionalization.get("Idioma"),
            Internacionalization.get("Voltar")};

        /* Imagem para o menu */
        //img = Util.loadImage(null);
        menu = new Menu(itens, -1, 280, 200, this);
        menu.setTransparent(true);
        menu.setFont(new Font("Arial", Font.BOLD, 20));
        menu.setColorFont(new Color(255, 255, 255));
    }

    @Override
    public void update() {
        menu.update();
        if (Key.isTyped(KeyEvent.VK_BACK_SPACE)) {
            Screen.setCurrentScreen(new TitleScreen());
        }
    }

    @Override
    public void paint(Graphics g) {
        ScreenUtil.clear(g);
        g.drawImage(title, 0, 0, null);
        menu.paint(g);
    }

    public void menuAction(int menuIndex) {
        switch (menuIndex) {
            //case 0: Screen.setCurrentScreen(new DifficultyScreen());
            //    break;
            case 0:
                Screen.setCurrentScreen(new SoundScreen());
                break;
            case 1:
                Screen.setCurrentScreen(new LanguageScreen());
                break;
            case 2:
                Screen.setCurrentScreen(new TitleScreen());
                break;
        }
    }
}
