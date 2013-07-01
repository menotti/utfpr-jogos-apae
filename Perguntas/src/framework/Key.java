package framework;

/**
 *
 * @author marcos
 */
public class Key {

    private static boolean[] keys = new boolean[65535];
    private static int keyCode;
    private static boolean pressed;
    private static boolean released;

    public static boolean isDown(int keyEventCode) {
        return keys[keyEventCode];
    }

    public static boolean isUp(int keyEventCode) {
        return !keys[keyEventCode];
    }

    public static boolean isTyped(int keyEventCode) {
        if (!pressed && isDown(keyEventCode)) {
            pressed = true;
            released = false;
            keyCode = keyEventCode;
        }
        if (pressed && !released && isUp(keyEventCode)) {
            if (keyCode == keyEventCode) {
                released = true;
            }
        }
        if (pressed && released && keyCode == keyEventCode) {
            pressed = false;
            released = false;
            keyCode = -1;
            return true;
        }
        return false;
    }

    static void setKey(int keyEventCode, boolean event) {
        Key.keys[keyEventCode] = event;
    }

    public static void setDown(int keyEventCode, boolean isDown) {
        keys[keyEventCode] = isDown;
    }

    /*static final void setKey(int keyEventCode, int event) {
    Key.keys[keyEventCode] = event;
    }*/

    /*static final void setPressed(int keyEventCode, boolean event) {
    keys[keyEventCode] = event;

    if (!keys[keyEventCode]) {
    lastKeys[keyEventCode] = false;
    pressed[keyEventCode] = false;
    }
    }*/
}
