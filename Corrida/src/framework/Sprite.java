package framework;

import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author marcos
 */
public class Sprite {

    private int x;
    private int y;
    private int frameSequence[];
    private Image image;
    private int frame; // current frame
    private int width;
    private int height;
    private boolean visible;
    private int scale;

    private int animationDelay;
    private int animationCount;

    public Sprite(Image image, int frameWidth, int frameHeight) {
        this.image = image;
        this.width = frameWidth;
        this.height = frameHeight;
        this.frame = 0;
        this.visible = true;
        this.scale = 1;
        this.frameSequence = new int[image.getWidth(null)/frameWidth];
        for (int i = 0; i < frameSequence.length; i++) {
            frameSequence[i] = i;
        }
    }

    public Sprite(Image image) {
        this(image, image.getWidth(null), image.getHeight(null));
    }

    /////////////////////////////////////////////////////////////
    // FRAME CONTROLL
/*
    public void setFrameSequence(int[] frameSequence) {
        this.frameSequence = frameSequence;
    }
*/
    public int getFrameSequenceLength() {
        return this.frameSequence.length;
    }

    public int getFrame() {
        return frame;
    }

    public void nextFrame() {
        frame = (frame + 1) % getFrameSequenceLength();
    }
    
    public void draw(Graphics g) {
//        g.drawImage(image, x, y, null);
        g.drawImage(image, x, y, x+width*scale-1, y+height*scale-1,
                frameSequence[frame]*width, 0,
                frameSequence[frame]*width+width, height, null);
    }

    /////////////////////////////////////////////////////////////
    // ANIMATION CONTROLL

    public void setFrameSequence(int[] sequence) {
        // soh troca animacao se for diferente da atual
        if (sequence != frameSequence) {
            frameSequence = sequence;
        }
    }

    public void setAnimationDelay(int delayPerFrame) {
        animationDelay = delayPerFrame;
    }

    /*
     * Problema > ele não vai desenhar o ultimo frame.
     * Retorna true no ultimo frame
     */
    public boolean animationEnded() {
        return (getFrame() == (getFrameSequenceLength() - 1) && animationCount >= (animationDelay - 1));
    }

    public void paint(Graphics g) {
        if (++animationCount >= animationDelay) {
            nextFrame();
            animationCount = 0;
        }
        draw(g);
    }

    /////////////////////////////////////////////////////////////
    // GENERAL CONTROLL

    public void setVisible(boolean visible) {
        visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getScaledHeight() {
        return height*scale;
    }

    public int getScaledWidth() {
        return width*scale;
    }
}
