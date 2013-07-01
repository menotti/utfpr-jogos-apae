package objects;

import java.awt.Graphics;

import framework.GameObject;
import framework.Util;
import framework.screen.Screen;
import game.Global;
import java.util.Random;

public class Enemy extends GameObject {

    private boolean active;
    private int startY;
    private int activeDelay;
    private Random rand = new Random();

    private int faixa;
    


    public Enemy(int y, int speedX, int speedY) {
        super();
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        changeImage();
        this.startY = y;
        this.activeDelay = 0;
        reset();
    }

    public void reset() {
        active = false;
        y = startY;
        activeDelay = rand.nextInt(Global.CARS_DELAY * Global.speed);
        if (Global.pista == 1){
        faixa = rand.nextInt(2);
            switch (faixa) {
                case 0:
                    x = Global.POSITION_LEFT_LEVEL1;
                    break;
                case 1:
                    x = Global.POSITION_RIGHT_LEVEL1;
                    break;
            }
        } else {
        faixa = rand.nextInt(3);
            switch(faixa){
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
        changeImage();        
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

    public int getFaixa() {
        return faixa;
    }

    public void changeImage(){
        int rd = rand.nextInt(7);
        System.out.println(rd);
        switch(rd){
            case 0:
                this.image = Util.loadImage(Global.IMG_ENEMYCAR1);
                break;
            case 1:
                this.image = Util.loadImage(Global.IMG_ENEMYCAR2);
                break;
            case 2:
                this.image = Util.loadImage(Global.IMG_ENEMYCAR3);
                break;
            case 3:
                this.image = Util.loadImage(Global.IMG_ENEMYCAR4);
                break;
            case 4:
                this.image = Util.loadImage(Global.IMG_ENEMYCAR5);
                break;
            case 5:
                this.image = Util.loadImage(Global.IMG_ENEMYCAR6);
                break;
            case 6:
                this.image = Util.loadImage(Global.IMG_ROCK);                
                break;
        }
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }


}
