package game;

import framework.text.Text;
import java.awt.Font;

/**
 * Configurações globais do jogo.
 *
 * @author Kuroda
 */

public class Global {

    // << IMPORTANTE >>
    // Quando houver alteração nesta classe, utilize a opção
    // "limpar e construir" do NetBeans para forçar a recompilação desta
    // classe.

    // CONFIGURAÇÕES DO JOGO ///////////////////////////////////////////////////
    public static final int     SCREEN_WIDTH    = 800;
    public static final int     SCREEN_HEIGHT   = 600;
    public static final boolean FULL_SCREEN     = false;
   
    public static final int MAX_TENTATIVAS      = 3; // Numero de tentativas
    public static final int MAX_TIME            = 120; // Tempo
    public static final int MAX_SCORE           = 100; // Pontos
    
    // Divide os pontos por 2 a quantidade de tentativas
    // Se tentou 3 vezes e acertou, divide o MAX_SCORE 3 vezes por 2
    public static final int DIV                 = 2;

    // Variaveis do jogo -> GameScreen.java
    public static int tentativas                = 0; // tentativas
    public static int score                     = 0; // pontuacao
    public static int time                      = 120; // segundos
    public static int level                     = 0; // level
    public static int maxLevel                  = 0; // total de level
    public static int corretas                  = 0; // perguntas respondidas corretas
    public static int incorretas                = 0; // perguntas respondidas incorretas

    // Acertou? -> controlado pela classe Figura.java
    public static boolean hit                   = false;

    // Caminhos dos arquivos de configuracao -> MenuScreen.java
    public static String imagePath              = "levels/";
    
    // Margem das imagens
    public static int imageMarginX              = 25; // Margin right, left
    public static int imageMarginY              = 10; // Margin top, bottom

    // Tamanho das imagens
    public static int imageWidth                = 150;
    public static int imageHeight               = 150;

    // Som habilitado?
    public static boolean sound;

    // Extensoes aceitaveis de imagens
    public static String[] extensions = {".png", ".jpg"};

    // Fonte
    public static Font font = new Font("Arial", Font.PLAIN, 30);

    // RECURSOS DO JOGO ////////////////////////////////////////////////////////
    private static final String FOLDER = "images/";

    // Imagens dos objetos

    // Fundos e telas
    public static final String IMG_SELECTOR   = FOLDER + "selector.png";
    public static final String IMG_MENU_BG    = FOLDER + "blackboard_bg.png";
    public static final String IMG_GAMEOVER   = FOLDER + "screen_gameover.png";
    public static final String IMG_GAMEWIN    = FOLDER + "screen_gamewin.png";
    public static final String MISS           = FOLDER + "miss.png";
    public static final String HIT            = FOLDER + "hit.png";
    public static final String IMG_MOUSE      = FOLDER + "cursor.png";
    public static final String IMG_FONT       = FOLDER + "font.png";
    public static final String IMG_CREDITS    = FOLDER + "credits.png";
    public static final String IMG_BACK    = FOLDER + "game.png";
    
    public static final Text text = new Text(Global.IMG_FONT, 16, 16, "ABCDEFGHIJKLMNOPQRSTUVWXYZ .,0123456789:-?!");;

    public static void resetStatus() {
        /*
         * Reseta a pontuacao, vida, delay e estagio
         */
        Global.score = 0;
        Global.level = 0;
        Global.tentativas = 0;
        Global.maxLevel = 0;
        Global.hit = false;
        Global.time = Global.MAX_TIME;
    }
}
