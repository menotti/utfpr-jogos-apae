package framework;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class GameObjectSprite extends GameObject {
    public static final int TRANS_NONE   = Sprite.TRANS_NONE;
    public static final int TRANS_ROT90  = Sprite.TRANS_ROT90;
    public static final int TRANS_ROT180 = Sprite.TRANS_ROT180;
    public static final int TRANS_ROT270 = Sprite.TRANS_ROT270;

    public static final int TRANS_MIRROR        = Sprite.TRANS_MIRROR;
    public static final int TRANS_MIRROR_ROT180 = Sprite.TRANS_MIRROR_ROT180;
    public static final int TRANS_MIRROR_ROT270 = Sprite.TRANS_MIRROR_ROT270;
    public static final int TRANS_MIRROR_ROT90  = Sprite.TRANS_MIRROR_ROT90;

    private Sprite sprite;
    private int animationDelay;
    private int animationCount;
    private int[] animationCurrent;
    private Rect collisionRect = new Rect();
    private boolean alive = true;
    private String name;

	public abstract void update();
    public abstract void paint(Graphics g);
    public abstract void collided(GameObjectSprite collidedObject);

    /*public GameObjectSprite() {
        this(new ImageIcon());
    }*/

    public GameObjectSprite(Image image, int frameWidth, int frameHeight) {
        sprite = new Sprite(image, frameWidth, frameHeight);
        setSprite(sprite);
        name = getClass().getName();
        // retira o nome do pacote, caso haja
        if (name.lastIndexOf('.') > 0) {
            name = name.substring(name.lastIndexOf('.')+1);
        }
    }

    public GameObjectSprite(Image image) {
        this(image, image.getWidth(null), image.getHeight(null));
    }

    /**
     * Obtém o nome do objeto. Por padrão, o nome retornando é o nome da classe.
     * Caso o nome pode ser alterado via setNome().
     * @return Nome (da classe) do objeto.
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do objeto. Por padrão, o nome retornando é o nome da classe.
     * @param name Novo nome para o objeto.
     */
    public void setName(String name) {
        this.name = name;
    }

    /////////////////////////////////////////////////////////////
    // RECURSOS PARA COLISAO

    public void defineReferencePixel(int x, int y) {
        sprite.defineReferencePixel(x, y);
    }

    public void setReferencePixelPosition(int x, int y) {
        sprite.setRefPixelPosition(x, y);
    }

    public void setTransformation(int transform) {
        sprite.setTransform(transform);
    }

    public void setAnimation(int[] animation) {
        // soh troca animacao se for diferente da atual
        if (animation != animationCurrent) {
            sprite.setFrameSequence(animation);
            animationCurrent = animation;
        }
    }

    public void setAnimationDelay(int delayPerFrame) {
        animationDelay = delayPerFrame;
    }

    public boolean animationEnded() {
        return (sprite.getFrame() == (sprite.getFrameSequenceLength()-1));
    }

    public void drawAnimatedSprite(Graphics g) {
        if (++animationCount >= animationDelay) {
            sprite.nextFrame();
            animationCount = 0;
        }
        drawSprite(g);
    }
    public void drawSprite(Graphics g) {
        sprite.paint(g);
        if (GameEngine.isShowInfo()) {
            drawInfo(g);
        }
    }

    public void drawSprite(Graphics g, int x, int y) {
        sprite.setPosition(x, y);
        drawSprite(g);
        sprite.setPosition(getX(),getY());
    }

    public void drawInfo(Graphics g) {
        g.setColor(new Color(0x0000FF)); // collision box
        g.drawRect(getX(), getY(), 2, 2);
        g.setColor(new Color(0x000000)); // reference point
        g.drawRect(
                getX()+collisionRect.x,
                getY()+collisionRect.y,
                collisionRect.width,
                collisionRect.height);
    }

    public void setVisible(boolean visible) {
        sprite.setVisible(visible);
    }

    public boolean isVisible() {
        return sprite.isVisible();
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean collidesWith(GameObjectSprite objSprite) {
        if (sprite.collidesWith(objSprite.getSprite(), false)) {
            this.collided(objSprite);
            objSprite.collided(this);
            return true;
        }
        return false;
    }

    public boolean collidesWithPrecise(GameObjectSprite objSprite) {
        // por otimizacao, pergunta primeiro se colide com caixa de colisao
        if (sprite.collidesWith(objSprite.getSprite(), false)) {
            if (sprite.collidesWith(objSprite.getSprite(), true)) {
                this.collided(objSprite);
                objSprite.collided(this);
                return true;
            }
        }
        return false;
    }

    public boolean collidesWith(GameObjectSprite list[], boolean precise) {
        for (int i = 0; i < list.length; i++) {
            GameObjectSprite obj = list[i];
            if (obj.isAlive()) {
                if (sprite.collidesWith(obj.getSprite(), precise)) {
                    this.collided(obj);
                    obj.collided(this);
                    return true;
                }
            }
        }
        return false;
    }

   /* public boolean collidesWith(TiledLayer layer, boolean precise) {
        if (sprite.collidesWith(layer, precise)) {
            this.collided(null);
            return true;
        }
        return false;
    }*/

    public void defineCollisionRectangle (int offsetX, int offsetY, int width, int height) {
        // soh faz sentido para colisao simples (nao pixel-a-pixel)
        sprite.defineCollisionRectangle(offsetX, offsetY, width, height);
        collisionRect.x = offsetX;
        collisionRect.y = offsetY;
        collisionRect.width = width;
        collisionRect.height = height;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        animationCount = 0;
        animationDelay = 0;
        collisionRect.x = 0;// offsetX
        collisionRect.y = 0;// offsetY
        collisionRect.width = sprite.getWidth();// width
        collisionRect.height= sprite.getHeight();// height
    }

	public void setX(int x) {
        super.setX(x);
		sprite.setPosition(x, getY());
	}

	public void setY(int y) {
        super.setY(y);
		sprite.setPosition(getX(), y);
	}

    public void setWidth(int width) {
    }

    public void setHeight(int width) {
    }

    public int getWidth() {
        return sprite.getWidth();
    }

    public int getHeight() {
        return sprite.getHeight();
    }
}
