package objects;

import java.awt.Graphics;

import framework.GameObject;
import framework.Util;
import framework.screen.Screen;
import game.Global;
import java.util.Random;

public class Star extends GameObject {

    private boolean active;
    private int startY;
    private int activeDelay;
    private Random rand;
    

    public Star(int x, int y, int speedX, int speedY) {
        super();
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.image = Util.loadImage(Global.IMG_STAR);
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.startY = y;
        this.activeDelay = 0;
        rand = new Random();
        reset();
    }

    public void reset() {
        active = false;
        y = startY;
        activeDelay = rand.nextInt(Global.STAR_DELAY * Global.speed)+800;
        if (Global.pista == 1){
            switch (rand.nextInt(2)) {
                case 0:
                    x = Global.POSITION_LEFT_LEVEL1;
                    break;
                case 1:
                    x = Global.POSITION_RIGHT_LEVEL1;
                    break;
            }
        } else {
            switch(rand.nextInt(3)){
                case 0:
                    x = Global.POSITION_RIGHT_LEVEL2;
                    break;
                case 1:
                    x = Global.POSITION_MID_LEVEL2;
                    break;
                case 2:
                    x = Global.POSITION_LEFT_LEVEL2;
            }
        }
    }

    public void update() {
        if (active == false) {
            if (--activeDelay < 0) {
                active = true;
            }
            return;
        }
        // verifica limites da tela
        if (y > Screen.getHeight()) {
            reset();
        }
        y += speedY;
    }

    public void paint(Graphics g) {
        if (active == false) {
            return;
        }
        g.drawImage(image, x, y, null);
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void collided(GameObject obj) {
        active = false;
        reset();
    }
}
