package screen;

import framework.Background;
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
import java.io.File;
import java.io.FileFilter;

/**
 * Tela inicial com menu
 *
 * @author Rodrigo Kuroda
 */
public class MenuScreen extends Screen implements MenuListener {

    private int erro = -1;
    private Menu menu;
    private Image img = Util.loadImage(Global.IMG_MOUSE);
    private Background bg = new Background(Util.loadImage(Global.IMG_MENU_BG), Background.SCROLL_NONE);

    public MenuScreen() {
        Mouse.setCursor(img);
        String itens[] = new String[]{"Começar", "Configurações", "Créditos", "Sair"};

        menu = new Menu(itens, -1, 150, 200, this);
        menu.setColorFont(Color.white);
        menu.setFont(new Font("Arial", Font.BOLD, 20));
        menu.setColorBack(new Color(255, 255, 255, 0));
        menu.setColorBorder(new Color(255, 255, 255, 0));
        menu.setSelectorImg(Util.loadImage(Global.IMG_SELECTOR));
        Global.resetStatus();
    }

    public void update() {
        menu.update();
    }

    public void paint(Graphics g) {
        ScreenUtil.clear(g);
        bg.paint(g);
        menu.paint(g);
        switch (erro) {
            case 0: {
                g.setColor(Color.red);
                g.setFont(new Font("Arial", Font.BOLD, 11));
                g.drawString("Nenhum diretório com as imagens e a pergunta foi "
                        + " selecionado. Por favor, selecione um diretório.",
                        5, Global.SCREEN_HEIGHT - 15);
            }
            break;
            case 1: {
                g.setColor(Color.red);
                g.setFont(new Font("Arial", Font.BOLD, 11));
                g.drawString("Nenhum diretório com as imagens e a pergunta foi "
                        + " encontrado. Por favor, selecione um diretório que contenha"
                        + " as imagens e perguntas.",
                        5, Global.SCREEN_HEIGHT - 15);
            }
            break;
        }

    }

    public void menuAction(int selection) {
        try {
            switch (selection) {
                case 0: {
                    // Começar
                    if (Global.imagePath != null) {
                        /* Verifica a quantidade de pastas que tem perguntas */
                        File[] directories = new File(Global.imagePath).listFiles();
                        for (File dir : directories) {
                            if (dir.isDirectory()) {
                                File[] txt = dir.listFiles(new FileFilter() {

                                    public boolean accept(File file) {
                                        return file.getName().equalsIgnoreCase("pergunta.txt");
                                    }
                                });
                                if (txt != null) {
                                    Global.maxLevel++;
                                }
                            }
                        }
                        if (Global.maxLevel > 0) {
                            Screen.setCurrentScreen(new GameReadyScreen());
                        } else {
                            erro = 1;
                        }
                    } else {
                        erro = 0;
                    }
                }
                break;

                case 1: {
                    // Configuração do jogo
                    Screen.setCurrentScreen(new ConfigurationScreen());
                }
                break;

                case 2: {
                    // Ajuda
                    Screen.setCurrentScreen(new CreditsScreen());
                }
                break;

                case 3: {
                    // Sair
                    System.exit(-1);
                }
                break;
            }
        } catch (Exception e) {
        }
    }
}
