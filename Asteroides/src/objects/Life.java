package objects;

import framework.GameObject;
import framework.Util;
import game.Global;
import java.awt.Graphics;

/**
 *
 * @author Kuroda
 */
public class Life extends GameObject {

    public Life(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        this.image = Util.loadImage(Global.IMG_LIFE);
    }

    public void update() {
        
    }

    public void paint(Graphics g) {
        g.drawImage(image, x, y, null);
    }

}
