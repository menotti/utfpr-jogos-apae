package objects;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import framework.GameObject;
import framework.Key;
import framework.Util;
import framework.screen.Screen;
import game.Global;

public class Paddle extends GameObject {

    private static final int SPEED_X = 10;

    public Paddle(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        this.speedX = 0;
        this.image = Util.loadImage(Global.IMG_PADDLE);
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    public void update() {
        if (Key.isDown(KeyEvent.VK_LEFT)) {
            speedX = -SPEED_X;
        } else if (Key.isDown(KeyEvent.VK_RIGHT)) {
            speedX = SPEED_X;
        } else {
            speedX = 0;
        }

        x += speedX;

        if ((x + width) > Screen.getWidth()) {
            x = Screen.getWidth() - width;
        } else if (x < 0) {
            x = 0;
        }
    }

    public void paint(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    @Override
    public void collided(GameObject obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
