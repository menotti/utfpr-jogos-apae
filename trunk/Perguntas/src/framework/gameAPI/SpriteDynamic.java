/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.gameAPI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 *
 * @author Rodrigo Kuroda
 */
public class SpriteDynamic extends Sprite {

    public static final double TRANS_NONE = 0;
    public static final double TRANS_ROT90 = 90.0 * Math.PI / 180.0;
    public static final double TRANS_ROT180 = 180.0 * Math.PI / 180.0;
    public static final double TRANS_ROT270 = 270.0 * Math.PI / 180.0;

    private int middlePointX;
    private int middlePointY;
    private int transformation;

   /* public static final int TRANS_MIRROR = Sprite.TRANS_MIRROR;
    public static final int TRANS_MIRROR_ROT180 = Sprite.TRANS_MIRROR_ROT180;
    public static final int TRANS_MIRROR_ROT270 = Sprite.TRANS_MIRROR_ROT270;
    public static final int TRANS_MIRROR_ROT90 = Sprite.TRANS_MIRROR_ROT90;*/

    public SpriteDynamic(Image image, int frameWidth, int frameHeight) {
        super(image, frameWidth, frameHeight);
    }

    public SpriteDynamic(Image image) {
        super(image);
    }

    public void transformation(int transformation) {
        this.transformation = transformation;
    }

    public void scale() {

    }

    @Override
    public void paint(Graphics g) {
        if (transformation != TRANS_NONE) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.translate(middlePointX, middlePointY);
            g2.rotate(TRANS_NONE);
            g2.translate(-middlePointX, -middlePointY);
            g2.dispose();
        }
        g.drawImage(image, x, y, x+width*scale-1, y+height*scale-1,
                frameSequence[currentFrame]%frameColumns*width, //x1
                frameSequence[currentFrame]/frameColumns*height, //y1
                frameSequence[currentFrame]%frameColumns*width+width, //x2
                frameSequence[currentFrame]/frameColumns*height+height, null); //y2
    }

    public void update() {
        middlePointX = x + (width / 2);
        middlePointY = (int) y + (height / 2);
    }

}
