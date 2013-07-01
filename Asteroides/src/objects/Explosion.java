/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

import framework.GameObject;
import framework.Sprite;
import framework.Util;
import framework.audio.Sound;
import game.Global;
import java.awt.Graphics;

/**
 *
 * @author Kuroda
 */
public class Explosion extends GameObject{
    
    Sprite explosion;
    int[] frameSequence = {0,1,2,3,4,5,6};

    public Explosion(int frameWidth, int frameHeight, int animationDelay) {
        super();
        this.image = Util.loadImage(Global.IMG_EXPLOSION);
        explosion = new Sprite(image, frameWidth, frameHeight);
        explosion.setFrameSequence(frameSequence);
        explosion.setAnimationDelay(animationDelay);
    }

    @Override
    public void paint(Graphics g) {
        explosion.paint(g);
    }

    @Override
    public void update() {
    
    }

    public void setAnimationDelay(int delayPerFrame) {
        explosion.setAnimationDelay(delayPerFrame);
    }

    public void setFrameSequence(int[] frameSequence) {
        explosion.setFrameSequence(frameSequence);
    }

    public boolean animationEnded() {
        return explosion.animationEnded();
    }

    public void setPosition(int x, int y) {
        explosion.setX(x);
        explosion.setY(y);
    }
    
}
