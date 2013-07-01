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
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Rodrigo Kuroda
 */
public class ConfigurationScreen extends Screen implements MenuListener {

    private Menu menu;
    private Image img = Util.loadImage(Global.IMG_MOUSE);
    private Background bg = new Background(Util.loadImage(Global.IMG_MENU_BG), Background.SCROLL_NONE);

    public ConfigurationScreen() {
        Mouse.setCursor(img);
        //String itens[] = new String[]{"Selecinar Perguntas", "Tamanho das imagens", "Tempo", "Som", "Voltar"};
        String itens[] = new String[]{"Selecinar Perguntas", "Tamanho das imagens", "Tempo", "Voltar"};

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
            Screen.setCurrentScreen(new MenuScreen());
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
                // Selecionar pasta
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                File file = null;
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION && chooser.accept(chooser.getCurrentDirectory())) {
                    file = chooser.getSelectedFile();
                    Global.imagePath = file.getAbsolutePath();
                    System.out.println("Pasta selecionada: " + Global.imagePath);
                } else {
                    break;
                }
            }
            break;
            case 1:
                Screen.setCurrentScreen(new ImageSizeScreen());
                break;
            case 2:
                Screen.setCurrentScreen(new TimeScreen());
                break;
            /*case 3:
            Screen.setCurrentScreen(new SoundScreen());
            break;*/
            case 3:
                Screen.setCurrentScreen(new MenuScreen());
                break;
        }
    }
}
