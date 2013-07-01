package framework.screen;

import framework.GameEngine;
import java.awt.Graphics;

public abstract class Screen {

    public static final int FADE_IN_FADE_OUT = 0;
    public static final int HORIZONTAL_OPEN_CLOSE = 1;
    public static final int VERTICAL_OPEN_CLOSE = 2;
    
    private static Screen currentScreen;
    private static int width;
    private static int height;

    // metodos a implementar das subclasses
    public abstract void update();
    public abstract void paint(Graphics g);

    public static Screen getCurrentScreen() {
        return currentScreen;
    }

    public static void setCurrentScreen(Screen currentScreen) {
        Screen.currentScreen = currentScreen;
    }

    public static void setCurrentScreen(Screen currentScreen, int type) {
        GameEngine.setTransitionType(type);
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
        ScreenUtil.setScreenWidth(width); // Atualiza para alinhamento
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        Screen.height = height;
        ScreenUtil.setScreenHeight(height);// Atualiza para alinhamento
    }
}
