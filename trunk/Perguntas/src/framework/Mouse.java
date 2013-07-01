package framework;

import java.awt.Image;

/**
 *
 * @author marcos
 */
public class Mouse {

    private static int x;
    private static int y;
    private static boolean buttons[] = new boolean[4];
    private static int keyCode;
    private static boolean pressed;
    private static boolean released;

    public static final boolean isPressed(int buttonEventCode) {
        return buttons[buttonEventCode];
    }

    public static final boolean isReleased(int buttonEventCode) {
        return !buttons[buttonEventCode];
    }

    public static final boolean isClicked(int buttonEventCode) {
        if (!pressed && isPressed(buttonEventCode)) {
            pressed = true;
            released = false;
            keyCode = buttonEventCode;
        }
        if (pressed && !released && isReleased(buttonEventCode)) {
            if (keyCode == buttonEventCode) {
                released = true;
            }
        }
        if (pressed && released && keyCode == buttonEventCode) {
            pressed = false;
            released = false;
            keyCode = -1;
            return true;
        }
        return false;
    }

    static final void setPressed(int buttonEventCode, boolean event) {
        buttons[buttonEventCode] = event;
    }

    public static final void setCursor(Image mouseCursor) {
        GameEngine.getInstance().setMouseCursor(mouseCursor);
    }

    public static final void hide() {
        GameEngine.getInstance().setMouseCursor(null);
    }

    public static final void show() {
        GameEngine.getInstance().resetMouseCursor();
    }

    public static final int getX() {
        return x;
    }

    public static final void setX(int x) {
        Mouse.x = x;
    }

    public static final int getY() {
        return y;
    }

    public static final void setY(int y) {
        Mouse.y = y;
    }
}
