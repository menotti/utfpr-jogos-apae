package framework;

import game.Global;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

public class Util {

    public static final int TARGET_FPS = 60;
    public static final int SECONDS_PER_MINUTE = 60;
    public static final int MINUTES_PER_HOUR = 60;
    public static final int MILISSECONDS_PER_SECOND = 1000;
    public static final int HOUR = 3;
    public static final int MINUTE = 2;
    public static final int SECOND = 1;
    public static final int MILISSECOND = 0;

    public static final Image loadImage(String fileName) {
        return new ImageIcon(fileName).getImage();
    }

    public static final Image loadFile(File file) {
        return new ImageIcon(file.getAbsolutePath()).getImage();
    }

    //////////////////////////////////////////////////////////////////////////
    // ALTERAR PARA CLASSE SCREENUTIL ////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    /**
     * Acha um ponto x na tela, de maneira que
     * @param width O comprimento do objeto
     * @return O ponto x onde o objeto deve ficar para centralizar
     */
    public static int centerX(int width) {
        return (Global.SCREEN_WIDTH - width) / 2;
    }

    /**
     * Acha um ponto y na tela, de maneira que
     * @param height O comprimento do objeto
     * @return O ponto y onde o objeto deve ficar para centralizar
     */
    public static int centerY(int height) {
        return (Global.SCREEN_HEIGHT - height) / 2;
    }

    public static int centerStringX(String s, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        return (Global.SCREEN_WIDTH - fm.stringWidth(s)) / 2;
    }

    public static int centerStringY(String s, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        return (Global.SCREEN_WIDTH - fm.getWidths()[0]) / 2;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Formata os segundos passados em uma String no formato passado.
     *
     * @param seconds Tempo em segundos
     * @param format Tipos de formato - Util.HOUR, Util.MINUTE, Util.SECOND, Util.MILISSECOND
     * @return O segundo no formato passado.
     */
    public static String formatSeconds(int seconds, int format) {
        switch (format) {
            case HOUR:
                return String.format("%02d:%02d:%02d",
                        (seconds / SECONDS_PER_MINUTE) / MINUTES_PER_HOUR,
                        seconds / SECONDS_PER_MINUTE,
                        seconds % SECONDS_PER_MINUTE);
            case MINUTE:
                return String.format("%d:%02d",
                        seconds / SECONDS_PER_MINUTE,
                        seconds % SECONDS_PER_MINUTE);
            case SECOND:
                return String.format("%d", seconds);
            case MILISSECOND:
                return String.format("%d", seconds * MILISSECONDS_PER_SECOND);
            default:
                return "";
        }
    }
}
