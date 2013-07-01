/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import framework.Key;
import framework.Util;
import framework.screen.GenericCreditScreen;
import game.Global;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author Rodrigo Kuroda
 */
public class CreditsScreen extends GenericCreditScreen {

    public CreditsScreen() {
        
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(Util.loadImage(Global.IMG_CREDITS), 0, 0, null);
    }

    @Override
    public void update() {
        if (Key.isTyped(KeyEvent.VK_ESCAPE) || Key.isTyped(KeyEvent.VK_ENTER) || Key.isTyped(KeyEvent.VK_SPACE)) {
            setCurrentScreen(new MenuScreen());
        }
    }
}
