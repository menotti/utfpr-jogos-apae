/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import framework.Background;
import framework.Key;
import framework.internacionalization.Internacionalization;
import framework.Mouse;
import framework.Util;
import framework.audio.BasicRenderer;
import framework.audio.MediaPlayer;
import framework.menu.Menu;
import framework.menu.MenuListener;
import framework.screen.Screen;
import game.Global;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Collections;

/**
 *
 * @author Luiz Philipe
 */
public class GameMenu extends Screen implements MenuListener {

    Menu menu;
    String itens[] = {Internacionalization.get("começar"),
                    Internacionalization.get("configurações"),
                    Internacionalization.get("creditos"),
                    Internacionalization.get("sair")};
    Background bg = new Background(Util.loadImage(Global.IMG_BG_MENU), Background.SCROLL_NONE);
    int error = 0;

    public GameMenu() {
        if(!(Global.backgroundMenuSound.getStatus() == BasicRenderer.PLAYING) && Global.sound){
            Global.backgroundMenuSound.play();
        }
        Mouse.show();
        Mouse.setCursor(Util.loadImage(Global.IMG_CURSOR));
        menu = new Menu(335, 280, itens, Global.MENU_FONT, this);
        menu.setDefaultColor(new Color(0x0080FF));
        menu.setSelectorColor(new Color(0x0080FF));
        menu.setSelectedColor(Color.BLUE);
        menu.setSelectorText('>');

        

    }

    @Override
    public void update() {
        bg.update();
        menu.update();
        if (Key.isDown(KeyEvent.VK_ESCAPE)) {
            System.exit(0);
        }
    }

    @Override
    public void paint(Graphics g) {
        Screen.clear(g);
        bg.paint(g);
        menu.paint(g);
        
    }

    public void menuAction(int menuIndex) {

        switch (menuIndex) {
            case 0: 
                Screen.setCurrentScreen(new GameScreen());
                break;
            case 1:
                Screen.setCurrentScreen(new GameMenuOptions());
                break;
            case 2:
                Screen.setCurrentScreen(new GameCreditsScreen());
                break;
            case 3:
                System.exit(0);
                break;
        }

    }

  }
