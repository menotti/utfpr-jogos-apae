package framework;

import framework.screen.Screen;
import java.awt.Graphics;
import java.awt.Image;

public abstract class GameObject {

    protected int x;
    protected int y;
    protected int speedX;
    protected int speedY;
    protected double speedDoubleY;
    protected int height;
    protected int width;
    protected boolean active;
    protected Image image;

    public abstract void update();

    public abstract void paint(Graphics g);

    public boolean collidesWith(GameObject obj) {
        return (x < obj.x + obj.width
                && obj.x < x + width
                && y < obj.y + obj.height
                && obj.y < y + height);
    }

    public boolean willCollide(GameObject obj) {
        // A velocidade dos objetos sao diferentes e esta em area de colisao?
        if (obj.speedDoubleY != speedDoubleY
                && x < obj.x + obj.width
                && obj.x < x + width
                && !active) {
            // Tempo do objeto na tela
            double timeInScreen = obj.speedDoubleY / Screen.getHeight();
            // Calcula a distancia entre os dois objetos
            double distance = (y + height) - obj.y;
            // Calcula a diferenca de velocidade
            double speedDiference = obj.speedDoubleY - speedDoubleY;
            speedDiference = speedDiference < 0 ? -speedDiference : speedDiference;
            // Variação do tempo = Variação do espaço percorrido / Velocidade ou t = S/V
            // Tempo para colidir
            double timeToCollide = speedDiference / distance;
            // Se não colidir enquanto o obj não sair da tela
            if ((y < obj.y + obj.height)
                    || ((obj.y < y + height) &&
                    (timeToCollide < timeInScreen))) {
                return true;
            }

        }
        return false;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
        setWidth(image.getWidth(null));
        setHeight(image.getHeight(null));
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

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public double getSpeedDoubleY() {
        return speedDoubleY;
    }

    public void setSpeedDoubleY(double speedDoubleY) {
        this.speedDoubleY = speedDoubleY;
    }

     public void collided(GameObject obj) {}
}
