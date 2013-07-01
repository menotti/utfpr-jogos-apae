package objects;

import framework.GameObject;
import framework.Util;
import framework.audio.Sound;
import framework.screen.Screen;
import game.Global;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

/**
 * Items para o jogador
 *
 * @author Rodrigo Kuroda
 */
public class Item extends GameObject {
    
    private int activeDelay;
    private Random rand;
    private boolean active;
    private boolean catched;
    private int imageDelay = 30;
    private boolean wasCollided;
    private final int START_Y = -100;
    private int auxY;
    private int messageAlpha = 255;
    private Sound item = new Sound("sounds/item.mid", false);

    public Item() {
        rand = new Random();
        this.x = rand.nextInt(Screen.getWidth() - 100) + 100;
        this.image = Util.loadImage(Global.IMG_ITEM);
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.activeDelay = rand.nextInt(Global.itemDelayVariation) + Global.MINIMUM_ITEM_DELAY;
        this.speedY = 3;
    }

    private void reset() {
        active = false;
        wasCollided = false;
        y = START_Y;
        
        //Randomizar para tela inteira
        this.x = rand.nextInt(Screen.getWidth() - 100) + 50;
    }

    @Override
    public void update() {
        if (active == false) {
            if (--activeDelay < 0) {
                active = true;
                activeDelay = rand.nextInt(Global.itemDelayVariation) + Global.MINIMUM_ITEM_DELAY;
            }
            return;
        }
        if (y > Screen.getHeight()) {
            reset();
        }
        this.y += speedY;
        //Sortear entre diversos itens

        //Item de tiro para destruir asteroides
        //Item para o tiro ficar mais rapido
        //Item para ficar invencivel
        
    }

    @Override
    public void paint(Graphics g) {
        if (catched) {
            if (--imageDelay > 0 && messageAlpha > 0) {
                messageAlpha -= 6;
                g.setColor(new Color(0, 255, 0, messageAlpha));
                g.setFont(new Font("arial", Font.BOLD, 30));
                g.drawString("+1 vida", x, auxY--);
            } else {
                messageAlpha = 255;
                imageDelay = 30;
                catched = false;
            }
        }

        if (active == false || wasCollided) return;

        //Pintar um para cada tipo de item
        g.drawImage(image, x, y, null);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean collided(GameObject obj) {
        if (obj.getClass().getSimpleName().equals("Shot")
                || obj.getClass().getSimpleName().equals("Nave")) {
            active = false;
            wasCollided = true;
            catched = true;
            auxY = y;
            item.play();
            if (Global.lives <= 2) Global.lives++;
        }
        return false;
    }

    public int getActiveDelay() {
        return activeDelay;
    }

    public void setActiveDelay(int activeDelay) {
        this.activeDelay = activeDelay;
    }

    public boolean wasCollided() {
        return wasCollided;
    }

    public void setWasCollided(boolean wasCollided) {
        this.wasCollided = wasCollided;
    }
    
}
