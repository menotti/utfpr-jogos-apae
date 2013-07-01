package objects;

import java.awt.Graphics;

import framework.GameObject;
import framework.Util;
import game.Global;

public class Block extends GameObject {

    private boolean dead;

    public Block(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        this.image = Util.loadImage(Global.IMG_BLOCK);
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.dead = false;
    }

    public void update() {
    }

    public void paint(Graphics g) {
        if (!dead) {
            g.drawImage(image, x, y, null);
        }
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void collided(GameObject obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
