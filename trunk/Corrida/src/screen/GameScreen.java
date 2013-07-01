package screen;

import framework.Background;
import framework.Key;
import framework.Mouse;
import java.awt.Color;
import java.awt.Graphics;

import framework.Sprite;
import framework.Util;
import framework.screen.Screen;
import game.Global;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.Random;
import objects.Enemy;
import objects.Car;
import objects.Explosion;
import objects.Fuel;
import objects.Life;
import objects.LifeUp;
import objects.Diamond;
import objects.Star;

public class GameScreen extends Screen {

    // 5 colunas de 14 blocos = 5 x 14 = 70
    Background bg;
    Car car;
    Enemy[] enemy;
    Fuel[] fuel;
    Diamond[] scoreUp;
    Star[] star;
    Life[] lifes;
    LifeUp[] lifeup;
    Random rand;
    Explosion explosion;
    Explosion explosion2;
    Sprite frameFuel;
    //largura do carro 95
    //altura 150
    boolean explosionOcurred = false;
    boolean explosion2Ocurred = false;
    int xCarExplosion = 0;
    int yCarExplosion = 0;
    String score_text = "Pontos 0";
    int score_cont;
    int fuel_cont;

    //Frames combustível
    int[] fuel1 = {0, 2};
    
    public GameScreen() {
        Global.stopAllSongs();
        Mouse.hide();
        
        if (Global.pista == 1) {

            if (Global.level == 1 || Global.level == 3){
                bg = new Background(Util.loadImage(Global.IMG_DESERT_2), Background.SCROLL_DOWN);
            } else {
                bg = new Background(Util.loadImage(Global.IMG_DESERT_NIGHT_2), Background.SCROLL_DOWN);
            }
            
            if (Global.sound){
                Global.backgroundDesertSound.play();
            }

            car = new Car(Global.POSITION_LEFT_LEVEL1, 420);
            enemy = new Enemy[1];

            for (int i = 0; i < enemy.length; i++) {
                enemy[i] = new Enemy(-240, 0, Global.speed);
            }

        } else {
            
            if (Global.level == 5 || Global.level == 7 || Global.level == 9){
                bg = new Background(Util.loadImage(Global.IMG_DESERT_3), Background.SCROLL_DOWN);
            } else {
                bg = new Background(Util.loadImage(Global.IMG_DESERT_NIGHT_3), Background.SCROLL_DOWN);
            }

            if (Global.sound){
                Global.backgroundGrassSound.play();                
            }

            car = new Car(Global.POSITION_MID_LEVEL2, 420);

            enemy = new Enemy[2];

            for (int i = 0; i < enemy.length; i++) {
                enemy[i] = new Enemy(-240, 0, Global.speed);
            }

        }
        bg.setSpeedY(Global.speed);
        // criando objetos do jogo
        explosion = new Explosion(88, 114, 6);
        explosion2 = new Explosion(88, 114, 6);


        fuel = new Fuel[1];
        scoreUp = new Diamond[1];
        star = new Star[1];
        lifeup = new LifeUp[1];
        lifes = new Life[3];

        for (int i = 0; i < star.length; i++) {
            fuel[i] = new Fuel(i * 100, -240, 0, Global.speed);
            scoreUp[i] = new Diamond(i * 100, -240, 0, Global.speed);
            star[i] = new Star(i * 100, -240, 0, Global.speed);
            lifeup[i] = new LifeUp(i * 100, -240, 0, Global.speed);
        }

        lifes[0] = new Life(690, 530);
        lifes[1] = new Life(722, 530);
        lifes[2] = new Life(754, 530);


        rand = new Random();

        frameFuel = new Sprite(Util.loadImage(Global.IMG_FRAME_FUEL), 40, 16);
        frameFuel.setFrameSequence(fuel1);
        frameFuel.setAnimationDelay(13);
        frameFuel.setX(711);

        //reiniciando combustível
        Global.fuel = 200;
        Global.yFuelPosition = 160;
    }

    public void paint(Graphics g) {
        // limpa tela
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Screen.getWidth(), Screen.getHeight());

        // desenha fundo
        bg.paint(g);

        //car.paint(g);

        // desenha objetos
        for (int i = 0; i < enemy.length; i++) {
            enemy[i].paint(g);
        }

        for (int i = 0; i < star.length; i++) {
            fuel[i].paint(g);
            scoreUp[i].paint(g);
            star[i].paint(g);
            lifeup[i].paint(g);
        }

        if (explosionOcurred) {
            explosion.paint(g);
            car.setVisible(false);
            explosion.setPosition(car.getX() + 10, car.getY() + 50);

            if (explosion.animationEnded()) {
                explosionOcurred = false;
                car.setVisible(true);
                car.setInvencible(true);
            }
        }

        if (explosion2Ocurred){
            explosion2.paint(g);
            explosion2.setPosition(xCarExplosion, yCarExplosion + 60);

            if (explosion2.animationEnded()){
                explosion2Ocurred = false;
            }
        }

        car.paint(g);

        if (Global.fuel == 0) {
            Global.life--;
            Global.fuel = 200;
            Global.yFuelPosition = 160;
            explosionOcurred = true;
            car.setInvencible(true);
        }

        //Desenhando o quadrado do combustível
        Font font0 = new Font("Arial", Font.PLAIN, 17);
        g.drawImage(Util.loadImage(Global.IMG_FUEL2), 690, 143, null);
        

        g.setFont(font0);
        g.drawRect(710, 159, 40, 211);
        g.setColor(Color.gray);
        g.fillRect(711, 160, 39, 210);
        

        g.setColor(new Color(0xfec12c));
        g.fillRect(711, Global.yFuelPosition+10, 39, Global.fuel);

        frameFuel.paint(g);

        //Desenhando o texto dos Pontos
        Font font1 = new Font("Gill Sans Ultra Bold", Font.PLAIN, 34);
        g.setColor(Color.black);
        g.setFont(font1);
        g.drawString(score_text, 510, 35);
        Font font2 = new Font("Arial", Font.PLAIN, 20);
        g.setFont(font2);

        //Desenhando o texto do Level
        g.drawString("LEVEL " + Global.level, 10, 20);

        //Desenhando o texto das vidas
        g.drawString("Vidas: " + Global.life, 690, 525);
        lifes[0].paint(g);
        lifes[1].paint(g);
        lifes[2].paint(g);


    }

    public void update() {
        /********************************
         * ESC para SAIR
         ********************************/
        if (Key.isDown(KeyEvent.VK_ESCAPE)) {
            System.exit(0);
        }

        if (Key.isDown(KeyEvent.VK_X) && Key.isDown(KeyEvent.VK_D)){
            Screen.setCurrentScreen(new GameWinScreen());
        }

        frameFuel.setY(Global.yFuelPosition -4);
        
        //SE NÃO ACONTECER UMA EXPLOSÃO A TELA ATUALIZA.
        if (!explosionOcurred) {
            car.update();
            bg.update();

            for (int i = 0; i < enemy.length; i++) {
                enemy[i].update();
            }

            for (int i = 0; i < star.length; i++) {
                fuel[i].update();
                scoreUp[i].update();
                star[i].update();
                lifeup[i].update();
            }

        }
        /***********************************
         * Verificações de Colisão
         ***********************************/
        for (int i = 0; i < enemy.length; i++) {
            if (!car.isInvencible()) {
                if (car.isVisible()) {
                    //verifica colisão com o carro inimigo
                    if (car.collidesWith(enemy[i])) {
                        explosion2Ocurred = true;
                        xCarExplosion = enemy[i].getX();
                        yCarExplosion = enemy[i].getY();
                        car.collided(enemy[i]);
                        enemy[i].collided(car);
                        explosionOcurred = true;
                        if(Global.sound){
                            Global.explosionSound.play();
                        }
                    }
                }
            }

            for (int j = 0; j < enemy.length; j++){
                if (enemy[i].collidesWith(enemy[j]) && i != j){
                    enemy[i].reset();
                }
            }
            
        }

        for (int i = 0; i < star.length; i++) {
            //verifica colisão com o combustível
            if (car.isVisible()) {
                if (car.collidesWith(fuel[i])) {
                    if(Global.sound){
                        Global.gasolinaSound.play();
                    }
                    car.collided(fuel[i]);
                    fuel[i].collided(car);
                }

                //verifica colisão com o item Diamond
                if (car.collidesWith(scoreUp[i])) {
                    car.collided(scoreUp[i]);
                    scoreUp[i].collided(car);
                    if (Global.level == 10){
                        Screen.setCurrentScreen(new GameFinishScreen());
                    } else {
                        Screen.setCurrentScreen(new GameWinScreen());
                    }

                    
                }

                //verifica colisão com a estrela invencibilidade
                if (car.collidesWith(star[i])) {
                    if (Global.sound){
                        Global.invencibleSound.play();
                    }
                    car.collided(star[i]);
                    star[i].collided(car);
                }

                if (car.collidesWith(lifeup[i])) {
                    if(Global.sound){
                        Global.lifeUpSound.play();
                    }
                    car.collided(lifeup[i]);
                    lifeup[i].collided(car);

                    if(Global.life == 3){
                        lifes[2].setActive(true);
                        lifes[1].setActive(true);
                        lifes[0].setActive(true);
                    } else if (Global.life == 2){
                        lifes[1].setActive(true);
                        lifes[0].setActive(true);
                    } else if (Global.life == 1){
                        lifes[0].setActive(true);
                    }
                }
            }     
        }

        /***************************************
         * Vidas
         ***************************************/
        switch (Global.life) {
            case 2:
                lifes[2].setActive(false);
                break;

            case 1:
                lifes[2].setActive(false);
                lifes[1].setActive(false);
                break;

            case 0:
                lifes[2].setActive(false);
                lifes[1].setActive(false);
                lifes[0].setActive(false);
                break;
            case -1:
                frameFuel.setY(Global.yFuelPosition -4);
                Screen.setCurrentScreen(new GameOverScreen());
                break;
        }


        //PONTOS
        if (score_cont++ % 60 == 0) {
            Global.score++;
            score_text = "PONTOS " + Global.score;
        }

        /***************************
         * Gastos do Combustível
         ***************************/
        if (car.isInvencible() == false) {
            if (fuel_cont++ % Global.fuelSpend == 0) {
                Global.fuel--;
                Global.yFuelPosition++;
            }
            Global.invencibleSound.stop();
        }
    }
}
