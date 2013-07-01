package objects;

import java.awt.Graphics;

import framework.GameObject;
import framework.Util;
import framework.screen.Screen;
import game.Global;
import java.util.Random;
import game.GameControl;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author Kuroda
 */
public class Asteroid extends GameObject {

    private final int INITIAL_POSITION_Y = -150;
    private final double ROTATION = 0.007;
    private int middlePointX;
    private int middlePointY;
    //private boolean active;
    private int delayToActive;
    private double angle;
    private Random rand = new Random();
    private boolean dodged = false;

    public Asteroid() {
        super();

        speedDoubleY = Global.objectsSpeed + rand.nextInt(Global.variationSpeed);

        this.image = Util.loadImage(Global.IMG_ASTEROID);
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);

        reset();
    }

    public void reset() {
        y = INITIAL_POSITION_Y;
        active = false;
        dodged = false;
        x = GameControl.randomizeXPosition() - (width / 2);
        delayToActive = rand.nextInt(Global.ASTEROIDS_DELAY);
    }

    public void update() {
        if (active == false) {
            if (--delayToActive < 0) {
                active = true;
            }
            return;
        }
        y += speedDoubleY;

        middlePointX = x + (width / 2);
        middlePointY = (int) y + (height / 2);

        // verifica limites da tela
        if (y > Screen.getHeight()) {
            reset();
        }
    }

    public void paint(Graphics g) {
        if (active == false) {
            return;
        }
        // Duplica um Graphics, transformando em Graphics2D
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);

        // O angulo de rotacao
        angle = angle + ROTATION;
        //g.drawImage(image, x, (int) y, null);
        // Rotaciona
        g2d.translate(middlePointX, middlePointY);
        g2d.rotate(Math.PI * angle);
        g2d.translate(-middlePointX, -middlePointY);
        g2d.drawImage(image, x, (int) y, null);

        g2d.dispose();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void collided(GameObject obj) {
        if (obj.getClass().getSimpleName().equals("Nave")) {
            dodged = true;
        }
    }

    public void dodged(GameObject obj) {
        if (obj.getClass().getSimpleName().equals("Nave")) {
            if (y > (obj.getY() + obj.getHeight()) && !dodged) {
                dodged = true;
                Global.asteroidsDodge++;
            }
        }
    }

    public double getDoubleSpeedY() {
        return this.speedDoubleY;
    }
}
