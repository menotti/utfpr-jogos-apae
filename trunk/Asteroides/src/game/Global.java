package game;

import framework.audio.Sound;
import framework.text.Text;
import java.util.Locale;

/**
 * Configurações globais do jogo.
 * @author Marcos
 */
public class Global {

    // <<IMPORTANTE>>
    // Quando houver alteração nesta classe, utilize a opção
    // "limpar e construir" do NetBeans para forçar a recompilação desta
    // classe.
    // CONFIGURAÇÕES DO JOGO ///////////////////////////////////////////////////
    public static final boolean FULL_SCREEN = false;
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final int ASTEROIDS_DELAY = 120;
    public static final int ALIEN_DELAY = 180;
    public static final int MINIMUM_ITEM_DELAY = 900;
    public static final int MAX_LIVES = 3;
    
    public static int lives = 3;
    public static int score = 0;
    public static int stage = 1;

    // Velocidade inicial dos objetos inimigos
    public static double objectsSpeed = 4;
    public static int variationSpeed = 2;
    public static int itemDelayVariation = 200;

    // Quantidade inicial
    public static int asteroids = 1;
    public static int aliens = 0;

    public static int asteroidsDodge = 0;
    public static int aliensKill = 0;

    // Quanto maior, menos dificil fica. Responsavel pelas vias.
    // A cada determinada quantidade de fase (quantidade representada por dificulty)
    // aumenta o numero de vias por onde o inimigo pode vir na tela.
    // (Ver update da classe Control)
    public static int difficulty = 3;

    // Quantidade de vias inicial
    public static int vias = 2;
    // Tempo que  jogador levou na fase
    public static int time = 0;

    public static boolean sound = true;

    public static Locale locale = new Locale("pt", "BR");

    // RECURSOS DO JOGO ////////////////////////////////////////////////////////
    private static final String FOLDER = "images/";
    // cursor
    public static final String CURSOR = FOLDER + "cursor.png";
    //texto
    public static final Text text = new Text(Global.IMG_FONT, 16, 16, "ABCDEFGHIJKLMNOPQRSTUVWXYZ .,0123456789:-?!");

    // objetos
    public static final String IMG_ASTEROID = FOLDER + "asteroide.png";
    public static final String IMG_SHOT = FOLDER + "shot.png";
    public static final String IMG_ALIEN = FOLDER + "alien.png";
    public static final String IMG_NAVE = FOLDER + "nave.png";
    public static final String IMG_EXPLOSION = FOLDER + "explosion.png";
    public static final String IMG_LIFE = FOLDER + "life.png";
    public static final String IMG_ITEM = FOLDER + "item.png";
    public static final String IMG_FIRE = FOLDER + "fire.png";
    public static final String IMG_FONT = FOLDER + "font.png";
    // fundos e telas
    public static final String IMG_TITLE = FOLDER + "screen_title.png";
    public static final String IMG_TITLEBACK = FOLDER + "screen_title_scroll.png";
    public static final String IMG_GAMEOVER = FOLDER + "screen_gameover.png";
    public static final String IMG_GAMEWIN = FOLDER + "screen_gamewin.png";
    public static final String IMG_GAMEREADY = FOLDER + "screen_ready.png";
    public static final String IMG_BACK = FOLDER + "star_bg5.png";
    public static final String IMG_BACK2 = FOLDER + "stars_bg2.png";
    public static final String IMG_CREDITS = FOLDER + "credits.png";

    //soms
    public static Sound main = new Sound("sounds/main.mid", true);

    public static void reset() {
        lives = 3;
        score = 0;
        stage = 1;
        objectsSpeed = 4;
        variationSpeed = 2;
        itemDelayVariation = 300;
        difficulty = 1;
        vias = 3;
        asteroidsDodge = 0;
        aliensKill = 0;
        asteroids = 1;
        aliens = 0;
    }
}
