package framework;

import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author marcos
 */
public class Background extends GameObject {

    public static final int SCROLL_NONE = 0;
    public static final int SCROLL_UP = 1;
    public static final int SCROLL_DOWN = 2;
    public static final int SCROLL_RIGHT = 3;
    public static final int SCROLL_LEFT = 4;
    private int offsetX, offsetY;
    private int scroll;
    private boolean scrollEnded = false;

    public Background(Image img, int scroll) {
        setImage(img);
        setScroll(scroll);
    }

    public void setScroll(int scroll) {
        this.scroll = scroll;
        switch (scroll) {
            case SCROLL_LEFT:
                if (getSpeedX() == 0) {
                    setSpeed(1);
                }
                offsetX = getWidth();
                break;
            case SCROLL_RIGHT:
                if (getSpeedX() == 0) {
                    setSpeed(1);
                }
                offsetX = -getWidth();
                break;
            case SCROLL_UP:
                if (getSpeedY() == 0) {
                    setSpeed(1);
                }
                offsetY = getHeight();
                break;
            case SCROLL_DOWN:
                if (getSpeedY() == 0) {
                    setSpeed(1);
                }
                offsetY = -getHeight();
                break;
        }
    }

    public boolean isScrollEnded() {
        return scrollEnded;
    }

    public void setSpeed(int speed) {
        if (scroll == SCROLL_DOWN) {
            setSpeedY(Math.abs(speed));
        } else if (scroll == SCROLL_UP) {
            setSpeedY(-Math.abs(speed));
        } else if (scroll == SCROLL_LEFT) {
            setSpeedX(-Math.abs(speed));
        } else if (scroll == SCROLL_RIGHT) {
            setSpeedX(Math.abs(speed));
        }
    }

    public void update() {
        x += speedX;
        y += speedY;

        if (scroll == SCROLL_LEFT) {
            if (getX() < -getWidth()) {
                setX(0);
                scrollEnded = true;
            } else {
                scrollEnded = false;
            }
        } else if (scroll == SCROLL_RIGHT) {
            if (getX() >= getWidth()) {
                setX(0);
                scrollEnded = true;
            } else {
                scrollEnded = false;
            }
        } else if (scroll == SCROLL_UP) {
            if (getY() < -getHeight()) {
                setY(0);
                scrollEnded = true;
            } else {
                scrollEnded = false;
            }
        }
        if (scroll == SCROLL_DOWN) {
            if (getY() >= getHeight()) {
                setY(0);
                scrollEnded = true;
            } else {
                scrollEnded = false;
            }
        }
    }

    public void paint(Graphics g) {
        g.drawImage(getImage(), getX(), getY(), null);
        if (scroll != SCROLL_NONE) {
            g.drawImage(getImage(), getX() + offsetX, getY() + offsetY, null);
        }
    }
}
