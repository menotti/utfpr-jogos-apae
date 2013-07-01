/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

import framework.GameObject;
import framework.Sprite;
import framework.Util;
import game.Global;
import java.awt.Graphics;

/**
 *
 * @author Kuroda
 */
public class Fire extends GameObject {
    Sprite fire;
    int[] frameSequence = {0,1,2};

    public Fire(int animationDelay) {
        super();
        fire = new Sprite(Util.loadImage(Global.IMG_FIRE), 30, 60);
        fire.setFrameSequence(frameSequence);
        fire.setAnimationDelay(animationDelay);
    }

    @Override
    public void paint(Graphics g) {
        fire.paint(g);
    }

    @Override
    public void update() {}

    public void setAnimationDelay(int delayPerFrame) {
        fire.setAnimationDelay(delayPerFrame);
    }

    public void setFrameSequence(int[] frameSequence) {
        fire.setFrameSequence(frameSequence);
    }

    public boolean animationEnded() {
        return fire.animationEnded();
    }

    public void setPosition(int x, int y) {
        fire.setX(x);
        fire.setY(y);
    }
}
