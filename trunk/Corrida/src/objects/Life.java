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
 * @author Luiz Philipe
 */
public class Life extends GameObject {

    boolean active = true;

    public Life(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        this.image = Util.loadImage(Global.IMG_LIFE);
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }
    
    @Override
    public void update() {

    }

    @Override
    public void paint(Graphics g) {
        if (active == false){
            return;
        }
        g.drawImage(image, x, y, null);
    }

    @Override
    public void collided(GameObject obj) {

    }

    public void setActive(boolean active){
        this.active = active;
    }




}
