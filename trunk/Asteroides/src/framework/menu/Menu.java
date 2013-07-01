package framework.menu;

import framework.GameObject;
import framework.Key;
import framework.Util;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

/**
 * 
 * @author Marcos
 *
 */
public class Menu extends GameObject {

    protected static final int CENTER = -1;
    protected static final int CHAR_HEIGHT = 15; // Altura da fonte
    protected static final int CHAR_WIDTH = 15; // Largura da fonte
    protected static int lineSpace = 15;
    protected static int borderSpace = 15;
    protected static int borderSize = 1; // tamanho da borda do quadrado cinza
    private String[] items;
    private Option[] options;
    private String selector;
    private Color colorBorder = Color.WHITE; // cor preta
    private Color colorBack = Color.GRAY; // cor acinzentada
    private Color colorFont = Color.GREEN; // cor acinzentada
    private int selectedIndex = 0;
    private int selectorLength = 5;// = selector.length();
    private MenuListener listener;
    private Image selectorImg = null;
    // Delay entre o 'pressionamento' das teclas
    private boolean pressed = false; // indica se alguma tecla foi pressionada
    private int delay = 0; // contador
    private final int MAX_DELAY = 10; // 0,16 segundos
    // Fonte do menu
    private Font font;
    private String name = "Arial";
    private int style = Font.PLAIN;
    private int size = 15;
    private boolean transparent = true;

    /**
     * Cria um menu
     *
     * @param items
     * @param x se -1, centraliza, caso contrario o x sera o especificado
     * @param y se -1, centraliza, caso contrario o y sera o especificado
     * @param width
     * @param listener
     */
    public Menu(String[] items, int x, int y, int width, MenuListener listener) {
        this.items = items;

        setWidth(width);
        setHeight(items.length * (CHAR_HEIGHT + lineSpace) + borderSpace * 2);
        if (x == CENTER) {
            x = Util.centerX(width);
        }
        if (y == CENTER) {
            y = Util.centerY(getHeight());
        }
        setX(x);
        setY(y);

        this.options = new Option[items.length];
        for (int i = 0; i < items.length; i++) {
            options[i] = new Option(items[i],
                    x + borderSpace + CHAR_WIDTH * 2,
                    y + (CHAR_HEIGHT + lineSpace) * i);
        }

        this.listener = listener;
        setSelectorText("-");
        font = new Font(name, style, size);
    }

    public String getSelectorText() {
        return selector;
    }

    /**
     * Seta um texto para o seletor
     * @param selector
     */
    public void setSelectorText(String sel) {
        this.selector = sel;
        selectorLength = selector.length();
        selectorImg = null;
    }

    public Image getSelectorImg() {
        return selectorImg;
    }

    /**
     * Seta uma imagem para o seletor
     * @param selectorImg
     */
    public void setSelectorImg(Image selectorImg) {
        this.selectorImg = selectorImg;
        selectorLength = selectorImg.getWidth(null);
    }

    /**
     * Desenha uma caixa quadrada para inserir o texto
     * @param g
     */
    protected void paintBox(Graphics g) {
        if (!transparent) {
            g.setColor(colorBorder);//desenha um quadrado preto
            g.fillRoundRect(getX(), getY(),
                    getWidth(), (items.length - 1) * (CHAR_HEIGHT + lineSpace) + borderSpace * 2,
                    10, 10);

            g.setColor(colorBack); //desenha um quadrado acinzentado
            g.fillRoundRect(getX() + borderSize, getY() + borderSize,
                    getWidth() - borderSize * 2,
                    (items.length - 1) * (CHAR_HEIGHT + lineSpace) + borderSpace * 2 - borderSize * 2,
                    10, 10);
        }
    }

    /**
     * Somente imprime o texto item nas posições especificadas por x,y
     * @param x posX inicial
     * @param y posY inicial
     * @param item o item a ser impresso
     * @param g
     */
    protected void paintItem(int x, int y, String item, Graphics g) {
        g.setFont(font);
        g.drawString(item,
                x + borderSpace + selectorLength + CHAR_WIDTH * 2,
                y + (CHAR_HEIGHT + lineSpace) + borderSpace);
    }

    /**
     * Este método imprime todos os itens da String, menos o da posição #selectedIndex
     * @param x posX inicial
     * @param y posY inicial
     * @param itens um array com os itens, caso não definido é usado o array de itens
     * passado como parametro para o método #setItems
     * @param g
     */
    protected void paintItem(int x, int y, String itens[], Graphics g) {
        if (itens == null) {
            itens = items;
        }
        for (int i = 0; i < itens.length; i++) {
            String text = itens[i];
            if (i != selectedIndex) {
                /* Desenha o texto do menu */
                g.setColor(colorFont);
                g.setFont(font);
                g.drawString(text,
                        x + borderSpace + selectorLength + CHAR_WIDTH * 2,
                        y + (CHAR_HEIGHT + lineSpace) * i + borderSpace);
            }
        }
    }

    /**
     * Este método imprime o Seletor, caso a #selectorImg já tenha sido setado, ele é usado.
     * caso contrário #selector é usado(podendo também ser modificado, caso necessário).
     * Obs.: o método considera #selectedIndex para determinar a posição a ser impressa
     * @param x posX inicial
     * @param y posY inicial
     * @param g
     */
    protected void paintSelector(int x, int y, Graphics g) {
        g.setFont(font);
        if (selectorImg != null) {
            g.drawImage(selectorImg, x + borderSpace,
                    y + (CHAR_HEIGHT + lineSpace) * selectedIndex + borderSpace, null);
        } else {
            g.drawString(selector, x + borderSpace,
                    y + (CHAR_HEIGHT + lineSpace) * selectedIndex + borderSpace);
        }
    }

    protected void paintSelectedItem(int x, int y, String item, Graphics g) {
        g.setFont(font);
        g.drawString(item, x, y);
    }

    protected void paintSelectedItem(int x, int y, Graphics g) {
        g.setFont(font);
        g.drawString(items[selectedIndex], x, y);
    }

    public void paint(Graphics g) {
        paintBox(g); //desenha o "quadrado atrás"
        paintItem(x, y, items, g);
        paintSelectedItem(x + borderSpace + selectorLength + CHAR_WIDTH * 2,
                y + (CHAR_HEIGHT + lineSpace) * selectedIndex + borderSpace, g);
        paintSelector(x, y, g);
        for (Option option : options) {
            option.paint(g);
        }
    }

    /**
     * Verifica se pressionou "UP", "DOWN" ou "ENTER"
     *
     */
    public void update() {
        if (!pressed) {
            if (Key.isTyped(KeyEvent.VK_DOWN)) {
                selectedIndex++;
                if (selectedIndex >= items.length) {
                    selectedIndex = 0;
                }
                pressed = true;
            } else if (Key.isTyped(KeyEvent.VK_UP)) {
                selectedIndex--;
                if (selectedIndex < 0) {
                    selectedIndex = items.length - 1;
                }
                pressed = true;
            } else if (Key.isTyped(KeyEvent.VK_ENTER)) {
                listener.menuAction(selectedIndex);
            }
        } else if (delay++ >= MAX_DELAY) {
            pressed = false;
            delay = 0;
        }
        for (int i = 0; i < options.length; i++) {
            if (options[i].collidesWithMouse()) {
                selectedIndex = i;
                //break;
            }
            if (options[i].isSelected()) {
                listener.menuAction(selectedIndex);
            }
        }
    }

    public int getLineSpace() {
        return lineSpace;
    }

    public void setLineSpace(int lineSpace) {
        this.lineSpace = lineSpace;
    }

    public int getBorderSize() {
        return borderSize;
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
    }

    public int getBorderSpace() {
        return borderSpace;
    }

    public void setBorderSpace(int borderSpace) {
        this.borderSpace = borderSpace;
    }

    public Color getColorBack() {
        return colorBack;
    }

    public void setColorBack(Color colorBack) {
        this.colorBack = colorBack;
    }

    public Color getColorFont() {
        return colorFont;
    }

    public void setColorFont(Color colorFont) {
        this.colorFont = colorFont;
    }

    public Option[] getOptions() {
        return options;
    }

    public void setOptions(Option[] options) {
        this.options = options;
    }

    public Color getColorBorder() {
        return colorBorder;
    }

    public void setColorBorder(Color colorBorder) {
        this.colorBorder = colorBorder;
    }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    public void setItem(String item, int index) {
        items[index] = item;
    }

    public String getItem(int index) {
        return items[index];
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }
    
}
