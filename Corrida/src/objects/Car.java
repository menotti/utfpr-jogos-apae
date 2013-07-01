/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

import framework.GameObject;
import framework.Mouse;

import framework.Sprite;
import framework.Util;
import java.awt.Graphics;

import game.Global;
import java.awt.event.MouseEvent;

/**
 *
 * @author Luiz
 */
public class Car extends GameObject {

    Sprite car;

    boolean visible = true;
    int invencible = 0;
    

    boolean clicked = false;
    boolean lastState = false;

    int right[] = {2, 0};
    int left[] = {1, 0};
    int stoped[] = {0};

    public int getLife() {
        return Global.life;
    }

    /**
     * Contrutor padrão da nave, que já o desenha dado um ponto (x,y)
     * @param x Posição inicial x
     * @param y Posição inicial y
     */
    public Car(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        car = new Sprite(Util.loadImage(Global.IMG_CAR_FRAME), 103,180);
        car.setFrameSequence(stoped);        
        this.width = 80;
        this.height = 130;
    }

    @Override
    public void update() {

    
    car.setX(x);
    car.setY(y);
    car.setAnimationDelay(20);
    //car.setFrameSequence(stoped);
    
    if (car.animationEnded()){
        car.setFrameSequence(stoped);
    }
        
    if (Global.pista == 1){
         if (Mouse.isClicked(MouseEvent.BUTTON1)){
                this.x = Global.POSITION_RIGHT_LEVEL1;
                car.setFrameSequence(left);
                
         }
            if (Mouse.isClicked(MouseEvent.BUTTON3)){
                this.x = Global.POSITION_LEFT_LEVEL1;
                car.setFrameSequence(right);
            }
    } else {
        if (Mouse.isClicked(MouseEvent.BUTTON1)){
            if (this.x == Global.POSITION_MID_LEVEL2){
                this.x = Global.POSITION_RIGHT_LEVEL2;
                car.setFrameSequence(left);
                
            } else if (this.x == Global.POSITION_LEFT_LEVEL2) {
                this.x = Global.POSITION_MID_LEVEL2;
                car.setFrameSequence(left);
            }
        }

        if (Mouse.isClicked(MouseEvent.BUTTON3)){
            if (this.x == Global.POSITION_RIGHT_LEVEL2){
                car.setFrameSequence(right);
                this.x = Global.POSITION_MID_LEVEL2;
            } else if (this.x == Global.POSITION_MID_LEVEL2) {
                this.x = Global.POSITION_LEFT_LEVEL2;
                car.setFrameSequence(right);
            }
        }
    }
         if (invencible > 0){
            if (invencible-- % 6 == 0){
                visible = false;
            } else {
                visible = true;
            }
        }
}

    @Override
    public void paint(Graphics g) {

        if(visible){
            car.paint(g);
        }

    }

    @Override
    public void collided(GameObject obj) {
        System.out.println(obj.getClass().getSimpleName());
        if (obj.getClass().getSimpleName().equals("Enemy")) {
            Global.life--;
            this.setInvencible(true);
            Global.fuel = 200;
            Global.yFuelPosition = 160;
        }
        if (obj.getClass().getSimpleName().equals("Diamond")) {
            Global.score += 100;
        }
        if (obj.getClass().getSimpleName().equals("Fuel")) {

                if (Global.fuel <= 160 ){
                    Global.fuel += 40;
                    Global.yFuelPosition -=40;
                    System.out.println(Global.fuel);
                } else {
                    Global.fuel = 200;
                    Global.yFuelPosition = 160;
                }
            
        }
        if (obj.getClass().getSimpleName().equals("Star")) {
            this.setInvencible(500);
        }
        if (obj.getClass().getSimpleName().equals("LifeUp")) {
            if(Global.life < 3){
                Global.life++;
            }
        }

    }

    public boolean isInvencible(){
        if (invencible > 0){
            return true;
        }
        return false;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible(){
        return visible;
    }

    public void setInvencible(boolean inv){
        if (inv){
            this.invencible = 120;
        } else {
            this.invencible = 0;
        }
    }

    public void setInvencible(int time){
        this.invencible = time;
    }

    @Override
    public boolean collidesWith(GameObject obj){
        /*int space = 0;
        if (Global.rock){
            space = 90;
        } else if (!obj.getClass().getSimpleName().equals("Enemy")){
            space = 15;
        }*/
        
        return x < obj.getX() + obj.getWidth()
                && obj.getX() < x + width
                && y + 25  < obj.getY() + obj.getHeight()
                && obj.getY() < y + 25 + height;

    }
    
}
