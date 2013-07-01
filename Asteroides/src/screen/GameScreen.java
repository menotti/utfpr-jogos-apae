package screen;

import framework.Background;
import framework.Key;
import framework.Mouse;
import java.awt.Color;
import java.awt.Graphics;

import framework.Util;
import framework.audio.Sound;
import framework.internacionalization.Internacionalization;
import framework.screen.Screen;
import framework.screen.ScreenUtil;
import game.GameControl;
import game.Global;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;
import objects.Alien;
import objects.Asteroid;
import objects.Explosion;
import objects.Item;
import objects.Life;
import objects.Nave;
import objects.Shot;

public class GameScreen extends Screen {

    Background bgGame;
    Nave nave;
    Shot shot;
    Life[] lives;
    Item item;
    Asteroid[] asteroids;
    Alien[] aliens;
    Random rand;
    Explosion explosion;
    final int BLINK_DELAY = 1;
    int blinkTimer = 0;
    int shotDelay = 0;
    int incrementDelay = 0;

    public GameScreen() {
        if (GameWinScreen.clear.isPlaying()) {
            GameWinScreen.clear.stop();
        }
        // Esconder o ponteiro do mouse
        Mouse.setCursor(null);
        Global.main.stop();
        bgGame = new Background(Util.loadImage(Global.IMG_BACK), Background.SCROLL_DOWN);
        bgGame.setSpeedY(1);

        // Criando objetos do jogo
        nave = new Nave();


        asteroids = new Asteroid[Global.asteroids];
        for (int i = 0; i < asteroids.length; i++) {
            asteroids[i] = new Asteroid();
        }

        aliens = new Alien[Global.aliens];
        for (int i = 0; i < aliens.length; i++) {
            aliens[i] = new Alien();
        }

        lives = new Life[Global.MAX_LIVES];
        for (int i = 0; i < lives.length; i++) {
            lives[i] = new Life(Global.SCREEN_WIDTH - (50 * (i + 1)), Global.SCREEN_HEIGHT - 70);
        }

        item = new Item();

        //explosion = new Explosion(87, 72, 5);
        explosion = new Explosion(88, 114, 5);

        rand = new Random();
        shot = null;
    }

    public void paint(Graphics g) {
        // Limpa tela
        ScreenUtil.clear(g);

        bgGame.paint(g);

        /*****************************************
         *            Desenha objetos            *
         *****************************************/
        for (int i = 0; i < Global.lives; i++) {
            lives[i].paint(g);
        }

        for (int i = 0; i < asteroids.length; i++) {
            asteroids[i].paint(g);
        }
        for (int i = 0; i < aliens.length; i++) {
            aliens[i].paint(g);
        }

        item.paint(g);
        nave.paint(g);

        // Verifica se a nave colidiu e se não esta invencivel
        if (!nave.isVisible() && !nave.isInvencible()) { // Se colidiu, acontece a explosao
            explosion.setPosition(nave.getX() - 8, nave.getY());
            explosion.paint(g);
            Mouse.setX(nave.getX());
            
            if (explosion.animationEnded()) { // Se acabou a explosao, a nave volta
                nave.setVisible(true);
                nave.setInvencible(true);
            }
        } else if (nave.isInvencible()) { // Fica invisivel e pisca
            if (blinkTimer++ % 5 == 0) {
                nave.setVisible(!nave.isVisible());
            }
            if (blinkTimer > 100) {
                nave.setInvencible(false);
                nave.setVisible(true);
                blinkTimer = 0;
            }
        }

        // Quando clica com o boão esquerdo, um novo tiro é criado
        if (Mouse.isPressed(MouseEvent.BUTTON1) && nave.isVisible() && shot == null) {
            shot = new Shot(nave.getX() + (nave.getWidth() / 2) - 9, nave.getY() - 5, 12);
            new Sound("sounds/shot.wav", false).play();
        }

        // Se o tiro nao for nulo, desenha. Caso sai da tela, recebe null
        if (shot != null) {
            shot.paint(g);
            if (shot.getY() < 0 || !shot.isVisible()) {
                shot = null;
            }
        }

        /*****************************************
         *           Desenha informacoes         *
         *****************************************/
        // Mostra o estágio atual e os pontos
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString(Internacionalization.get("Level") + " " + Global.stage + "", 10, 15);
        g.drawString(Internacionalization.get("Pontos") + " " + Global.score, Screen.getWidth() - 150, 20);
        if (Global.asteroidsDodge >= GameControl.asteroidsToDodge) {
            g.setColor(Color.green);
        } else {
            g.setColor(Color.yellow);
        }
        if (GameControl.asteroidsToDodge > 0)
        g.drawString(Internacionalization.get("Asteroides") + " " + Global.asteroidsDodge + "/"+ GameControl.asteroidsToDodge, 10, 40);
        if (Global.aliensKill >= GameControl.aliensToKill) {
            g.setColor(Color.green);
        } else {
            g.setColor(Color.yellow);
        }
        if (GameControl.aliensToKill > 0)
        g.drawString(Internacionalization.get("Inimigos") + " " + Global.aliensKill + "/"+ GameControl.aliensToKill, 10, 65);
    }

    public void update() {
        if (incrementDelay++ == 60) {
            Global.score++;
            Global.time++;
            incrementDelay = 0;
        }

        if (shot != null) {
            shot.update();
        }

        bgGame.update();

        nave.update();

        item.update();

        // Verifica colisoes com o asteroide
        for (int i = 0; i < asteroids.length; i++) {
            asteroids[i].update();
            if (asteroids[i].isActive()) {
                if (asteroids[i].collidesWith(nave) && nave.isVisible()) {
                    nave.collided(asteroids[i]);
                    asteroids[i].collided(nave);
                }
                if (shot != null && shot.isVisible()) {
                    if (asteroids[i].collidesWith(shot)) {
                        shot.collided(asteroids[i]);
                    }
                }
                asteroids[i].dodged(nave);
            }
            for (int j = 0; j < aliens.length; j++) {
                if (asteroids[i].willCollide(aliens[j]) && aliens[j].isActive()) {
                    asteroids[i].reset();
                }
            }
            for (int j = 0; j < asteroids.length; j++) {
                if (asteroids[i].willCollide(asteroids[j]) && i != j && asteroids[j].isActive()) {
                    asteroids[i].reset();
                }
            }
        }
        for (int i = 0; i < aliens.length; i++) {
            aliens[i].update();

            // Verifica colisoes com o alien
            if (aliens[i].isActive() && !aliens[i].isCollided()) {
                if (nave.collidesWith(aliens[i]) && nave.isVisible()) {
                    aliens[i].collided(nave);
                    nave.collided(aliens[i]);
                }

                if ((shot != null) && (shot.isVisible())) {
                    if (aliens[i].collidesWith(shot)) {
                        aliens[i].collided(shot);
                        shot.collided(aliens[i]);
                    }
                }
            }
            for (int j = 0; j < asteroids.length; j++) {
                if (aliens[i].willCollide(asteroids[j]) && asteroids[j].isActive()) {
                    aliens[i].reset();
                }
            }
            for (int j = 0; j < aliens.length; j++) {
                if (aliens[i].willCollide(aliens[j]) && i != j && aliens[j].isActive()) {
                    aliens[i].reset();
                }
            }
        }

        if (item.isActive() && !item.wasCollided()) {
            if (nave.collidesWith(item) && nave.isVisible()) {
                item.collided(nave);
                nave.collided(item);
            }

            if ((shot != null) && (shot.isVisible())) {
                if (item.collidesWith(shot)) {
                    item.collided(shot);
                    shot.collided(item);
                }
            }
        }

        if (Global.asteroidsDodge >= GameControl.asteroidsToDodge
                && Global.aliensKill >= GameControl.aliensToKill || Key.isTyped(KeyEvent.VK_ENTER)) {
            if(Global.stage < 10) {
               Screen.setCurrentScreen(new GameWinScreen());
            } else {
                Screen.setCurrentScreen(new GameFinishScreen());
            }
        }
    }
}
