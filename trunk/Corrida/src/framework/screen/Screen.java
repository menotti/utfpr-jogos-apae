package framework.screen;

import framework.GameEngine;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public abstract class Screen {

    // Tipos de transicoes de tela
    public static final int FADE_IN_FADE_OUT = 0;
    public static final int HORIZONTAL_OPEN_CLOSE = 1;
    public static final int VERTICAL_OPEN_CLOSE = 2;
    
    // Utilizado para comparacao binaria para centralizacao
    public static final int TOP = 1; //00001
    public static final int RIGHT = 2; //00010
    public static final int BOTTOM = 4; //00100
    public static final int LEFT = 8; //01000
    public static final int CENTER = 16; //10000

    // Dados da tela
    private static int width;
    private static int height;

    // Telas armazenadas para detecção de transicao
    private static Screen currentScreen; // Tela corrente
    private static Screen lastScreen; // Ultima tela

    // Margem da tela para alinhamento
    public static int rightMargin = 5;
    public static int leftMargin = 5;
    public static int topMargin = 5;
    public static int bottomMargin = 5;

    // Auxiliares para calculo da posicao bottom e right
    // As posicoes left e top sao os valores de suas respectivas da margens
    private static int bottom = 0;
    private static int right = 0;
    // Auxiliar para calculo e atualizacao (caso altere a resolucao da tela)
    // Recebe valor na instancia do GameEngine
    private static int screenWidth;
    private static int screenHeight;

    // metodos a implementar das subclasses
    public abstract void update();
    public abstract void paint(Graphics g);

    public static Screen getCurrentScreen() {
        return currentScreen;
    }

    public static Screen getLastScreen() {
        return lastScreen;
    }

    public static void setCurrentScreen(Screen screen) {
        Screen.lastScreen = Screen.currentScreen;
        Screen.currentScreen = screen;
    }

    public static void setLastScreen(Screen screen) {
        Screen.lastScreen = screen;
    }

    public static void setCurrentScreen(Screen currentScreen, int transitionType) {
        GameEngine.setTransitionType(transitionType);
        Screen.lastScreen = currentScreen;
        Screen.currentScreen = currentScreen;
    }

    public static void setTransitionType(int type) {
        GameEngine.setTransitionType(type);
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        Screen.width = width;
        Screen.setScreenWidth(width); // Atualiza para alinhamento
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        Screen.height = height;
        Screen.setScreenHeight(height);// Atualiza para alinhamento
    }

    /**
     * Limpa a tela com a cor preta
     * @param g
     */
    public static void clear(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenWidth, screenHeight);
    }

    /**
     * Alinha um determinado objeto na tela.
     * @param anchor Posicao na tela ()
     * @param objHeight Altura do objeto
     * @param objWidth Largura do objeto
     * @return Point Ponto (x,y) onde o objeto deve ser desenhado
     */
    public static Point getAlignedPoint(int anchor, int objHeight, int objWidth) {
        /* right -> posicao x direito
         * bottom -> posicao y inferior
         */
        switch (anchor) {
            case LEFT | TOP:
                return new Point(leftMargin, topMargin);
            case LEFT | BOTTOM:
                return new Point(leftMargin, bottom - objHeight);
            case LEFT | CENTER:
                return new Point(leftMargin, (bottom - (objHeight / 2)) / 2);
            case RIGHT | TOP:
                return new Point(right - objWidth, topMargin);
            case RIGHT | BOTTOM:
                return new Point(right - objWidth, bottom - objHeight);
            case RIGHT | CENTER:
                return new Point(right - objWidth, (bottom - (objHeight / 2)) / 2);
            case CENTER | CENTER:
                return new Point((screenWidth / 2) - (objHeight / 2), (bottom - (objHeight / 2)) / 2);
            case CENTER | TOP:
                return new Point((right - (objWidth / 2)) / 2, topMargin);
            case CENTER | BOTTOM:
                return new Point((right - (objWidth / 2)) / 2, bottom - objHeight);
            default:
                return new Point(leftMargin, topMargin);
        }
    }

    public static int getBottomMargin() {
        return bottomMargin;
    }

    public static void setBottomMargin(int bottomMargin) {
        Screen.bottomMargin = bottomMargin;
        Screen.bottom = screenHeight - leftMargin;
    }

    public static int getLeftMargin() {
        return leftMargin;
    }

    public static void setLeftMargin(int leftMargin) {
        Screen.leftMargin = leftMargin;
    }

    public static int getRightMargin() {
        return rightMargin;
    }

    public static void setRightMargin(int rightMargin) {
        Screen.rightMargin = rightMargin;
        Screen.right = screenWidth - rightMargin;
    }

    public static int getTopSpace() {
        return topMargin;
    }

    public static void setTopMargin(int topMargin) {
        Screen.topMargin = topMargin;
    }

    /**
     *
     */
    public static void setMargin(int margin) {
        Screen.topMargin = margin;
        Screen.bottomMargin = margin;
        Screen.rightMargin = margin;
        Screen.leftMargin = margin;
    }

    /**
     * Atualiza o alinhamento do texto.
     * O valor é atualizado quando se instancia o GameEngine
     * @param width
     */
    static void setScreenWidth(int width) {
        screenWidth = width;
        right = width - rightMargin;
    }

    /**
     * Atualiza o alinhamento do texto.
     * O valor é atualizado quando se instancia o GameEngine
     * @param height
     */
    static void setScreenHeight(int height) {
        screenHeight = height;
        bottom = height - bottomMargin;
    }
}
