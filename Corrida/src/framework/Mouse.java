package framework;

import java.awt.Graphics;
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
    private static boolean visible = true;
    private static Image cursor;

    public static boolean isPressed(int buttonEventCode) {
        return buttons[buttonEventCode];
    }

    public static boolean isReleased(int buttonEventCode) {
        return !buttons[buttonEventCode];
    }

    public static boolean isClicked(int buttonEventCode) {
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

    public static void setPressed(int buttonEventCode, boolean event) {
        buttons[buttonEventCode] = event;
    }

    public static void setCursor(Image mouseCursor) {
        GameEngine.getInstance().setMouseCursor(null);
        Mouse.cursor = mouseCursor;
    }

    public static void hide() {
        Mouse.visible = false;
    }

    public static void show() {
        Mouse.visible = true;
    }

    public static void paint(Graphics g) {
        if (visible) {
            g.drawImage(cursor, Mouse.getX(), Mouse.getY(), null);
        }
    }

    public static int getX() {
        return x;
    }

    public static void setX(int x) {
        Mouse.x = x;
    }

    public static int getY() {
        return y;
    }

    public static void setY(int y) {
        Mouse.y = y;
    }
}
