package framework;

import java.awt.Graphics;
import java.awt.Image;

public abstract class GameObject {
    
    protected int x;
    protected int y;
    protected int speedX;
    protected int speedY;
    protected int height;
    protected int width;
    protected Image image;

    public abstract void update();

    public abstract void paint(Graphics g);

    public boolean collidesWith(GameObject obj) {
        return (x < obj.x + obj.width
                && obj.x < x + width
                && y < obj.y + obj.height
                && obj.y < y + height);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
        setWidth(image.getWidth(null));
        setHeight(image.getHeight(null));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
}
