/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.menu;

import framework.GameObject;
import framework.Mouse;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 *
 * @author Rodrigo Kuroda
 */
public class Option extends GameObject {

    public Option(String option, int x, int y) {
        this.x = x;
        this.y = y;
        this.width = option.length() * Menu.charWidth;
        this.height = Menu.charHeight;
    }

    @Override
    public void update() {
    }

    @Override
    public void paint(Graphics g) {
        
    }

    public boolean collidesWithMouse() {
        if (Mouse.getX() <= getWidth() + getX()
                && Mouse.getX() >= getX()
                && Mouse.getY() <= getHeight() + getY()
                && Mouse.getY() >= getY()) {
            return true;
        }
        return false;
    }

    public boolean isSelected() {
        if (collidesWithMouse() && Mouse.isClicked(MouseEvent.BUTTON1)) {
            return true;
        }
        return false;
    }
}
