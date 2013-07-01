package objects;

import java.awt.Graphics;

import framework.GameObject;
import framework.Util;
import framework.screen.Screen;
import game.Global;

public class Ball extends GameObject {

    private Paddle paddle;

    public Ball(int x, int y, int speedX, int speedY, int size, Paddle paddle) {
        super();
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.image = Util.loadImage(Global.IMG_BALL);
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.paddle = paddle;
    }

    public void update() {

        // verifica limites da tela
        if (((x + width) > Screen.getWidth()) || (x < 0)) {
            speedX = -speedX;
        }
        if (((y + width) > Screen.getHeight()) || (y < 0)) {
            speedY = -speedY;
        }

        // verifica colisao com paddle
        if ((y + paddle.getHeight()) >= paddle.getY()) {
            if ((x >= paddle.getX()) && (x <= (paddle.getX() + paddle.getWidth()))) {
                speedY = -speedY;
            }
        }

        x += speedX;
        y += speedY;
    }

    public void paint(Graphics g) {
        g.drawImage(image, x, y, null);
    }


    @Override
    public void collided(GameObject obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
