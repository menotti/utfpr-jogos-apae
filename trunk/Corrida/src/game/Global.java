/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import framework.audio.MediaPlayer;
import java.awt.Font;

/**
 * Configurações globais do jogo.
 * @author marcos
 */
public class Global {

    // <<IMPORTANTE>>
    // Quando houver alteração nesta classe, utilize a opção
    // "limpar e construir" do NetBeans para forçar a recompilação desta
    // classe.

    // CONFIGURAÇÕES DO JOGO ///////////////////////////////////////////////////
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final boolean FULL_SCREEN = false;
    public static final String TITLE = "Corrida Maluca";
    public static final String ICON = "Icone.ico";

    //Aumentar/Diminuir a progressão
    public static final double progressao = 1.8;

    //Delays
    public static final int CARS_DELAY = (int) (8 / progressao);
    public static final int FUEL_DELAY = (int) (26 / progressao);
    public static final int DIAMOND_DELAY = (int) (100 / progressao);
    public static final int STAR_DELAY = (int) (120 / progressao);
    public static final int LIFEUP_DELAY = (int) (120 / progressao);
  

    //Variáveis Globais
    public static int score = 0;
    public static int pista = 1;
    public static int life = 3;
    public static int level = 1;
    public static int speed = (int) (7 * progressao);

    //Posição do combustível
    public static int fuel = 200;
    public static int yFuelPosition = 160;
    public static int fuelSpend = 6;
    public static boolean rock = false;

    //Som
    public static boolean sound = true;

    public static MediaPlayer backgroundMenuSound     = new MediaPlayer("sound/funky.mid", true);
    public static MediaPlayer backgroundGrassSound    = new MediaPlayer("sound/ForestGhosts.mid", true);
    public static MediaPlayer invencibleSound         = new MediaPlayer("sound/star.mid", true);
    public static MediaPlayer backgroundDesertSound   = new MediaPlayer("sound/Desert.mid", true);
    public static MediaPlayer gameOver                = new MediaPlayer("sound/gameover.wav", MediaPlayer.MULTIPLE);
    public static MediaPlayer explosionSound          = new MediaPlayer("sound/explosion.wav", MediaPlayer.MULTIPLE);
    public static MediaPlayer lifeUpSound             = new MediaPlayer("sound/lifeup.wav", MediaPlayer.MULTIPLE);
    public static MediaPlayer gasolinaSound           = new MediaPlayer("sound/gasolina.wav", MediaPlayer.MULTIPLE);
    public static MediaPlayer levelCompleteSound      = new MediaPlayer("sound/levelcomplete.wav", MediaPlayer.MULTIPLE);

    public static final Font MENU_FONT = new Font("Comic Sans Ms", Font.BOLD, 25);



    //Posição dos objetos
    public static final int POSITION_LEFT_LEVEL1 = 405;
    public static final int POSITION_RIGHT_LEVEL1 = 280;
    public static final int POSITION_RIGHT_LEVEL2 = 230;
    public static final int POSITION_MID_LEVEL2 = 355;
    public static final int POSITION_LEFT_LEVEL2 = 485;

    // RECURSOS DO JOGO ////////////////////////////////////////////////////////
    private static final String FOLDER = "images/";

    //cursor
    public static final String IMG_CURSOR       = FOLDER + "cursor.png";

    // objetos
    public static final String IMG_BALL         = FOLDER + "ball.png";
    public static final String IMG_BLOCK        = FOLDER + "block.png";
    public static final String IMG_PADDLE       = FOLDER + "paddle.png";
    public static final String IMG_EXPLOSION    = FOLDER + "explosion2.png";
    public static final String IMG_ENEMYCAR1    = FOLDER + "enemycar1.png";
    public static final String IMG_ENEMYCAR2    = FOLDER + "enemycar2.png";
    public static final String IMG_ENEMYCAR3    = FOLDER + "enemycar3.png";
    public static final String IMG_ENEMYCAR4    = FOLDER + "enemycar4.png";
    public static final String IMG_ENEMYCAR5    = FOLDER + "enemycar5.png";
    public static final String IMG_ENEMYCAR6    = FOLDER + "enemycar6.png";
    public static final String IMG_CAR          = FOLDER + "car.png";
    public static final String IMG_CAR_FRAME    = FOLDER + "car_frame.png";
    public static final String IMG_FUEL2        = FOLDER + "fuel2.png";
    public static final String IMG_FRAME_FUEL   = FOLDER + "frame_gasolina.png";


    public static final String IMG_FUEL         = FOLDER + "fuel.png";
    public static final String IMG_SCOREUP      = FOLDER + "diamante.png";
    public static final String IMG_STAR         = FOLDER + "star.png";
    public static final String IMG_ROCK         = FOLDER + "rock.png";
    public static final String IMG_LIFE         = FOLDER + "life.png";
    public static final String IMG_LIFEUP       = FOLDER + "lifeup.png";

    // fundos e telas
    public static final String IMG_BG_MENU      = FOLDER + "background_menu.png";
    public static final String IMG_TITLEBACK    = FOLDER + "screen_title_scroll.png";
    public static final String IMG_GAMEOVER     = FOLDER + "screen_gameover.png";
    public static final String IMG_GAMEWIN      = FOLDER + "screen_gamewin.png";
    public static final String IMG_GAMEFINISH   = FOLDER + "game_finish.png";
    public static final String IMG_GAMECREDITS  = FOLDER + "game_credits.png";

    //Pistas
    public static final String IMG_DESERT_2             = FOLDER + "bg_desert_2.png";
    public static final String IMG_DESERT_3             = FOLDER + "bg_desert_3.png";
    public static final String IMG_DESERT_NIGHT_2       = FOLDER + "bg_desert_night_2.png";
    public static final String IMG_DESERT_NIGHT_3       = FOLDER + "bg_desert_night_3.png";


    public static void stopAllSongs(){
        Global.backgroundGrassSound.stop();
        Global.backgroundDesertSound.stop();
        Global.invencibleSound.stop();
        Global.backgroundMenuSound.stop();
    }

}
