/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import framework.GameObject;
import framework.Util;
import game.Global;
import java.awt.Graphics;

/**
 *
 * @author Kuroda
 */
public class Shot extends GameObject {

    private boolean visible = true;

    public Shot(int x, int y, int speedY) {
        super();
        this.x = x;
        this.y = y;
        this.speedDoubleY = speedY;
        this.image = Util.loadImage(Global.IMG_SHOT);
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    @Override
    public void update() {
        this.y -= speedDoubleY;
    }

    @Override
    public void paint(Graphics g) {
        if (visible) {
            g.drawImage(image, x, y, null);
        }
    }

    public void collided(GameObject obj) {
        if (this.isVisible()) {
            if (obj.getClass().getSimpleName().equals("Alien")) {
                Global.score += 10;
                Global.aliensKill++;
            }

            if (obj.getClass().getSimpleName().equals("Alien")
                    || obj.getClass().getSimpleName().equals("Asteroid")) {
                this.visible = false;
            }
        }

    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
