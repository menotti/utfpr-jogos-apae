package objects;

import java.awt.Graphics;

import framework.GameObject;
import framework.Util;
import framework.screen.Screen;
import game.Global;
import game.GameControl;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;

/**
 * 
 * @author Kuroda
 */
public class Alien extends GameObject {

    private final int INITIAL_POSITION_Y = -240;
    private boolean isCollided;
    private boolean hit;
    //private boolean active;
    private Random rand = new Random();
    private int messageDelay = 40;
    private int messageAlpha = 255; // 255 = opaco
    private int messageY;
    private int delayToActive;

    public Alien() {
        super();

        this.speedDoubleY = Global.objectsSpeed + rand.nextInt(Global.variationSpeed);

        this.image = Util.loadImage(Global.IMG_ALIEN);
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);

        this.delayToActive = 0;

        reset();
    }

    public void reset() {
        isCollided = false;
        active = false;
        hit = false;
        y = INITIAL_POSITION_Y;
        x = GameControl.randomizeXPosition() - (width / 2);
        delayToActive = rand.nextInt(Global.ALIEN_DELAY);
    }

    public void update() {
        // Nao desenha nem atualiza se estiver na primeira fase
        if (active == false && Global.stage != 1) {
            if (--delayToActive < 0) {
                active = true;
            }
            return;
        }
        // Verifica limites da tela
        if (y > Screen.getHeight()) {
            reset();
        }
        y += speedDoubleY;
    }

    public void paint(Graphics g) {

        if (hit) {
            if (--messageDelay > 0) {
                // Transparencia
                messageAlpha -= 6;
                g.setColor(new Color(0, 255, 0, messageAlpha));
                g.setFont(new Font("arial", Font.BOLD, 30));
                g.drawString("+10", x, messageY--);
            } else {
                messageDelay = 40;
                messageAlpha = 255;
                hit = false;
            }
        }
        // Se nao estiver ativo ou colidiu, nao desenha nada
        if (!active || isCollided) {
            return;
        }
        g.drawImage(image, x, y, width, height, null);
    }

    public void collided(GameObject obj) {
        if (obj.getClass().getSimpleName().equals("Shot")) {
            active = false;
            isCollided = true; // boolean para controle (se ele colidiu, não desenha)
            hit = true;
            messageY = (int) y;
        } else if (obj.getClass().getSimpleName().equals("Asteroid")) {
            this.reset();
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isCollided() {
        return isCollided;
    }
}
