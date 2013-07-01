package framework.gameAPI;

import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author marcos
 */
public class Sprite {

    private Image image;

    private int x;
    private int y;
    private int frameSequence[];
    private int currentFrame;
    private int width;
    private int height;
    private int scale;
    private int frameLines;
    private int frameColumns;

    private boolean visible;

    private int animationDelay;
    private int animationCount;

    public Sprite(Image image, int frameWidth, int frameHeight) {
        this.image = image;
        this.width = frameWidth;
        this.height = frameHeight;
        this.currentFrame = 0;
        this.visible = true;
        this.scale = 1;
        this.frameLines  = image.getHeight(null)/frameHeight;
        this.frameColumns= image.getWidth(null)/frameWidth;
        this.frameSequence = new int[frameLines*frameColumns];
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
        return currentFrame;
    }

    public void nextFrame() {
        currentFrame = (currentFrame + 1) % getFrameSequenceLength();
    }
    
    public void draw(Graphics g) {
/*
        g.drawImage(image, x, y, x+width*scale-1, y+height*scale-1,
                frameSequence[currentFrame]*width, 0,
                frameSequence[currentFrame]*width+width, height, null);
*/
        g.drawImage(image, x, y, x+width*scale-1, y+height*scale-1,
                frameSequence[currentFrame]%frameColumns*width, //x1
                frameSequence[currentFrame]/frameColumns*height, //y1
                frameSequence[currentFrame]%frameColumns*width+width, //x2
                frameSequence[currentFrame]/frameColumns*height+height, null); //y2

    }

    /////////////////////////////////////////////////////////////
    // ANIMATION CONTROL

    public void setFrameSequence(int[] sequence) {
        // soh troca animacao se for diferente da atual
        if (sequence != frameSequence) {
            frameSequence = sequence;
        }
    }

    public void setAnimationDelay(int delayPerFrame) {
        animationDelay = delayPerFrame;
    }

    public boolean animationEnded() {
        return (getFrame() == (getFrameSequenceLength() - 1));
    }

    public void paint(Graphics g) {
        if (++animationCount >= animationDelay) {
            nextFrame();
            animationCount = 0;
        }
        draw(g);
    }

    /////////////////////////////////////////////////////////////
    // GENERAL CONTROL

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

    public int getAnimationCount() {
        return animationCount;
    }

    public void setAnimationCount(int animationCount) {
        this.animationCount = animationCount;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getFrameColumns() {
        return frameColumns;
    }

    public void setFrameColumns(int frameColumns) {
        this.frameColumns = frameColumns;
    }

    public int getFrameLines() {
        return frameLines;
    }

    public void setFrameLines(int frameLines) {
        this.frameLines = frameLines;
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
