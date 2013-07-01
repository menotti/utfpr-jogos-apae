package framework.gameAPI;

import java.awt.Graphics;

/**
 * Class that mimics J2ME Game API's Layer.
 * @author marcos
 */
abstract class Layer {
    private int height;
    private int width;
    private int x;
    private int y;
    private boolean visible;

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public abstract void paint(Graphics g);
}
