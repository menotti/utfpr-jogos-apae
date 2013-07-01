package objects;

import framework.GameObject;
import framework.Mouse;
import framework.Sprite;
import framework.Util;
import framework.audio.Sound;
import framework.screen.Screen;
import java.awt.Graphics;

import game.Global;
import screen.GameOverScreen;

/**
 *
 * @author Kuroda
 */
public class Nave extends GameObject {

    private int movimentDelay = 0;
    private boolean visible = true;
    private boolean invencible = false;
    private Fire fire;
    private Sprite nave;
    private int[] neutral = {0};
    private int[] left = {1};
    private int[] right = {2};
    private int lastX = 0;

    /**
     * Contrutor padrão da nave, que já o desenha dado um ponto (x,y)
     * 
     * @param x Posição inicial x
     * @param y Posição inicial y
     */
    public Nave() {
        super();
        this.image = Util.loadImage(Global.IMG_NAVE);
        this.width = image.getWidth(null) / 3;
        this.height = image.getHeight(null);
        this.lastX = x;
        this.x = Util.centerX(70);
        this.y = Screen.getHeight() - height - 35;
        nave = new Sprite(image, 70, height);
        nave.setFrameSequence(neutral);
        nave.setY(y);
        nave.setX(x);
        this.fire = new Fire(3);
        fire.setPosition(x + 21, y + height - 6);
    }

    @Override
    public void update() {
        if (visible) {
            x = Mouse.getX();

            // Nao deixa a nave sair da tela
            if (x > 730) {
                x = 730;//Screen.getWidth() - width;
                Mouse.setX(730);
            } else if (x < 0) {
                x = 0;
                Mouse.setX(0);
            }

            // Atribuir x depois da verificacao
            nave.setX(x);
            fire.setPosition(x + 21, y + height - 6);

            if (movimentDelay++ > 5) {
                if (lastX < x) {
                    nave.setFrameSequence(left);
                    lastX = x;
                } else if (lastX > x) {
                    nave.setFrameSequence(right);
                    lastX = x;
                } else {
                    nave.setFrameSequence(neutral);
                }
                movimentDelay = 0;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        if (visible) {
            nave.paint(g);
            fire.paint(g);
        }
    }

    public void collided(GameObject obj) {
        if (!invencible) {
            if (obj.getClass().getSimpleName().equals("Asteroid")
                    || obj.getClass().getSimpleName().equals("Alien")) {
                this.visible = false;
                new Sound("sounds/explosion.wav", false).play();
                if (Global.lives > 0) {
                    Global.lives--;
                } else {
                    // Game Over
                    Screen.setCurrentScreen(new GameOverScreen());
                }
            }
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isInvencible() {
        return invencible;
    }

    public void setInvencible(boolean invencible) {
        this.invencible = invencible;
    }
}
