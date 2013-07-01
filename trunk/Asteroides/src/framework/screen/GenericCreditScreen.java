package framework.screen;

import framework.Background;
import framework.Key;
import framework.text.Text;
import framework.text.TextUtil;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * 
 * @author Rodrigo Kuroda
 */
public class GenericCreditScreen extends Screen {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private Background background;
    private List<String> credits;
    private Text text;
    private Point point;
    private int index, x, y, showTime, stringWidth, stringHeight, speed = 5;
    private boolean enter = false, exit = false;
    private int direction;
    private Screen screen;

    public GenericCreditScreen() {
    }

    /**
     * Cria uma tela generica de creditos.
     *
     * @param credits Uma lista de string com creditos
     * @param background Um fundo ja configurado
     * @param direction Direcao (GenericCrediScreen.VERTICAL ou GenericCrediScreen.HORIZONTAL)
     */
    public GenericCreditScreen(List<String> credits, Background background,
            Text text, int direction, Screen backScreen) {
        this.background = background;
        this.credits = credits;
        this.text = text;
        this.index = 0;
        this.direction = direction;
        this.screen = backScreen;
    }

    @Override
    public void update() {
        if (background != null) {
            background.update();
        }
        if (credits == null || point == null) {
            return;
        }

        if (Key.isTyped(KeyEvent.VK_ESCAPE)) {
            Screen.setCurrentScreen(screen);
        }

        switch (direction) { // HORIZONTAL /////////////////////////////////////////
            case 0:
                if (!enter) {
                    x -= speed;
                    if (x + (stringWidth / 2) <= Screen.getWidth() / 2) {
                        enter = true;
                    }
                }
                if (enter && !exit && showTime++ >= 120) {
                    exit = true;
                    showTime = 0;
                }
                if (exit) {
                    x -= speed;
                    if (x + stringWidth < 0) {
                        index++;
                        index %= credits.size();
                        exit = false;
                        enter = false;
                        point = null;
                    }
                }
                break;
            case 1: // VERTICAL ///////////////////////////////////////////////////
                if (!enter) {
                    y += speed;
                    if (y >= point.y) {
                        enter = true;
                    }
                }
                if (enter && !exit && showTime++ >= 120) {
                    exit = true;
                    showTime = 0;
                }
                if (exit) {
                    y += speed;
                    if (y - stringHeight > Screen.getHeight()) {
                        index++;
                        index %= credits.size();
                        exit = false;
                        enter = false;
                        point = null;
                    }
                }
                break;
            case 2: // VERTICAL WITHOUT PAUSE //////////////////////////////
                if (y <= Screen.getHeight()) {
                    y += 1;
                }
        }
    }

    @Override
    public void paint(Graphics g) {
        ScreenUtil.clear(g);
        if (background != null) {
            background.paint(g);
        }
        if (credits == null) {
            return;
        }

        if (point == null) {
            this.point = ScreenUtil.getAlignedPoint(
                    ScreenUtil.CENTER | ScreenUtil.CENTER,
                    stringHeight = TextUtil.getStringHeight(g),
                    stringWidth = credits.get(index).length() * text.getCharWidth());

            switch (direction) { // HORIZONTAL /////////////////////////////////////
                case 0:
                    x = Screen.getWidth() + stringWidth;
                    y = point.y; // Posicao central
                break;
                case 1: // VERTICAL ///////////////////////////////////////////////
                    x = point.x; // Posicao central
                    y = 0 - stringHeight;
                    break;
                case 2:
                    x = point.x;
                    y = -(text.getCharHeight() * credits.size());
                    break;
            }
        }
        switch (direction) {
            case 2:
                for (int i = credits.size() -1; i >= 0; i--) {
                    text.drawText(credits.get(i), x, y + (text.getCharHeight() * i), g);
                }
                break;
            case 3:
                for (int i = credits.size() -1; i >= 0; i--) {
                    text.drawText(credits.get(i), (Screen.getWidth() / 2) - (credits.get(i).length() * text.getCharWidth() / 2),
                            (Screen.getHeight() / 2) - (text.getCharHeight() * credits.size() / 2) + (text.getCharHeight() * i) + 16, g);
                }
                break;
            default: text.drawText(credits.get(index), x, y, g);
            break;
        }
    }

    public int getShowTime() {
        return showTime;
    }

    public void setShowTime(int showTime) {
        this.showTime = showTime;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public List<String> getCredits() {
        return credits;
    }

    public void setCredits(List<String> credits) {
        this.credits = credits;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isEnter() {
        return enter;
    }

    public void setEnter(boolean enter) {
        this.enter = enter;
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }
}
