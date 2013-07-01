/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.text;

import java.awt.Graphics;

/**
 *
 * @author Rodrigo Kuroda
 */
public class TextUtil {

    public static int getStringWidth(String string, Graphics g) {
        return g.getFontMetrics().stringWidth(string);
    }

    public static int getStringHeight(Graphics g) {
        return g.getFontMetrics().getHeight();
    }
}
