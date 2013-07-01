package screen;

import framework.Mouse;
import java.awt.Graphics;
import java.awt.Image;

import framework.Util;
import framework.audio.Sound;
import framework.internacionalization.Internacionalization;
import framework.menu.Menu;
import framework.menu.MenuListener;
import framework.screen.Screen;
import framework.screen.ScreenUtil;
import game.GameControl;
import game.Global;
import java.awt.Color;
import java.awt.Font;

public class TitleScreen extends Screen implements MenuListener {

    //Background background;
    Image title = Util.loadImage(Global.IMG_TITLE);
    Menu menu;
    static {
        Global.main.play();
    }
    public TitleScreen() {
       if(!Global.main.isPlaying()) {
            Global.main.play();
        }
       System.gc();
       System.gc();
       System.gc();
        // IMPORTANTE: no construtor da 1a tela nao e possivel acessar o tamanho da tela
        Mouse.setCursor(Util.loadImage(Global.CURSOR));
        // Reseta o estado do jogo.
        GameControl.reset();
        Global.reset();

        //background = new Background(Util.loadImage(Global.IMG_TITLEBACK), Background.SCROLL_LEFT);
        String itens[] = new String[]{Internacionalization.get("Começar"),
            Internacionalization.get("Configurações"),
            Internacionalization.get("Créditos"),
            Internacionalization.get("Sair")};

        menu = new Menu(itens, -1, 280, 200, this);
        menu.setSelectorText("-");
        menu.setTransparent(true);
        menu.setFont(new Font("Arial", Font.BOLD, 20));
        menu.setColorFont(new Color(255, 255, 255));
        Internacionalization.setLocale(Global.locale);

    }

    @Override
    public void paint(Graphics g) {
        ScreenUtil.clear(g);
        //background.paint(g);
        g.drawImage(title, 0, 0, null);
        menu.paint(g);
        //text.drawText("ABC", 50, 50, g);
    }

    @Override
    public void update() {
        //background.update();
        menu.update();
    }

    public void menuAction(int menuIndex) {
        switch (menuIndex) {
            case 0: {
                // Começar
                GameControl.reset(); // Reset das variaveis do jogo
                Global.reset();
                Screen.setCurrentScreen(new GameReadyScreen());
            }
            break;
            case 1: {
                // Configuração do jogo
                Screen.setCurrentScreen(new ConfigurationScreen());
            }
            break;

            case 2: {
                // Creditos
                
                Screen.setCurrentScreen(new CreditsScreen());
            }
            break;

            case 3:
                // Sair
                System.exit(-1);
        }
    }
}
