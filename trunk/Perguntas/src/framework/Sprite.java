package framework;

import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author marcos
 */
public class Sprite {
    static int TRANS_NONE;
    static int TRANS_ROT90;
    static int TRANS_ROT180;
    static int TRANS_ROT270;
    static int TRANS_MIRROR;
    static int TRANS_MIRROR_ROT180;
    static int TRANS_MIRROR_ROT270;
    static int TRANS_MIRROR_ROT90;

    private int x;
    private int y;
    private int frameSequence[];
    private Image image;
    private int currentFrame;
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
        this.currentFrame = 0;
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
    // FRAME CONTROL

    public int getFrameSequenceLength() {
        return this.frameSequence.length;
    }


    public void nextFrame() {
        currentFrame = (currentFrame + 1) % getFrameSequenceLength();
    }

    /**
     * Desenha uma parte do sprite.
     * Nao ha verificacao alguma, ja que so desenha
     * @param g
     */
    public void draw(Graphics g) {
        g.drawImage(image, x, y, x+width*scale-1, y+height*scale-1,
                frameSequence[currentFrame]*width, 0,
                frameSequence[currentFrame]*width+width, height, null);
    }

    /////////////////////////////////////////////////////////////
    // ANIMATION CONTROLL
    public int getFrame() {
        return currentFrame;
    }

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

    /**
     * Desenha um sprite, com animacao.
     * Antes de desenhar, verifica o delay e a sequencia para animar.
     * @param g
     */
    public void paint(Graphics g) {
        if (++animationCount >= animationDelay) {
            nextFrame();
            animationCount = 0;
        }
        draw(g);
    }

    /////////////////////////////////////////////////////////////
    // GENERAL CONTROLL
    public int getScaledHeight() {
        return height*scale;
    }

    public int getScaledWidth() {
        return width*scale;
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void defineReferencePixel(int x, int y) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void setRefPixelPosition(int x, int y) {
        
    }

    void setTransform(int transform) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void setPosition(int x, int y) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    boolean collidesWith(Sprite sprite, boolean precise) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void defineCollisionRectangle(int offsetX, int offsetY, int width, int height) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
