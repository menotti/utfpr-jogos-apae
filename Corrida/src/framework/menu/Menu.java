package framework.menu;

import framework.GameObject;
import framework.Key;
import framework.screen.Screen;
import framework.text.Text;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Kuroda
 */
public class Menu extends GameObject {

    private final MenuListener LISTENER;
    private final int PRESS_DELAY = 10; // Delay de pressionamento entre teclas
    private int delay = 0; // Contador
    private int lineMargin = 10; // Espacamento entre linhas
    private int margin = 10; // Margens
    private int selectorMargin = 20; // Margem entre o seletor e o item (item maior se centralizado)
    private int selectedIndex = 0; // Index selecionado / em selecao
    private char selectorText = '-'; // Texto padrao do seletor
    private boolean pressed = false; // Auxiliar para tecla do menu
    private ArrayList<MenuItem> menuItems;
    private Color selectorColor = Color.black;
    private Font font;
    private Image selectorImage; // Imagem do seletor
    private Image imageItems[];
    private Image menuBackground;
    private Point center; // Ponto central
    private String items[];

    public Menu(int x, int y, String items[], Font font, MenuListener listener) {
        this.menuItems = new ArrayList<MenuItem>();
        this.items = items;
        this.imageItems = null;
        this.font = font;
        this.x = x;
        this.y = y;
        this.width = 0;
        this.height = 0;

        // Fonte necessaria para medir
        Text.getGraphics().setFont(font);

        // Calcula os valores de altura e largura
        for (String string : items) {
            int textWidth = Text.getFontMetrics().stringWidth(string);
            if (textWidth > width) {
                width = textWidth;
            }
        }
        this.height = (Text.getFontMetrics().getHeight() * items.length)
                        + (lineMargin * items.length);

        // Calculando o centro da tela
        center = Screen.getAlignedPoint(Screen.CENTER, width + margin, height + margin);
        // Centralizando menu ou posicionando
        if (x == -1) {
            this.x = center.x;
        } else {
            this.x = x;
        }
        if (y == -1) {
            this.y = center.y;
        } else {
            this.y = y;
        }

        // Criando os itens do menu e calculando suas posicoes
        for (int i = 0; i < items.length; i++) {
            menuItems.add(new MenuItem(items[i], font,
                    Text.calculeAlignedPointX(items[i], this.x, width, Text.CENTER),
                    this.y - Text.getFontMetrics().getDescent()
                    + ((Text.getFontMetrics().getHeight() + lineMargin) * i)));
        }

        this.LISTENER = listener; // Listener do menu -> acao ao selecionar item
        
    }

    public Menu(int x, int y, Image items[], MenuListener listener) {
        this.imageItems = items;
        this.items = null;
        this.menuItems = new ArrayList<MenuItem>();
        this.font = null;
        this.x = x;
        this.y = y;
        this.width = 0;
        this.height = 0;

        // Calcula os valores de altura e largura
        for (Image image : items) {
            int textWidth = image.getWidth(null);
            if (textWidth > width) {
                width = textWidth;
            }
            this.height += image.getHeight(null) + lineMargin;
        }
        //this.height += margin + margin;
        // Calculando o centro da tela
        center = Screen.getAlignedPoint(Screen.CENTER, width + margin, height + margin);

        // Centralizando menu ou posicionando
        if (x == -1) {
            this.x = center.x;
        } else {
            this.x = x;
        }
        if (y == -1) {
            this.y = center.y;
        } else {
            this.y = y;
        }

        // Criando os itens do menu e calculando suas posicoes
        for (int i = 0; i < items.length; i++) {
            menuItems.add(new MenuItem(items[i],
                    this.x,
                    this.y + ((items[i].getHeight(null) + lineMargin) * i)));
        }

        this.LISTENER = listener; // Listener do menu -> acao ao selecionar item
    }

    protected void paintSelector(Graphics g) {
        if (selectorImage != null) { // Imagem do seletor
            if (items != null) {
                g.setFont(font);
                g.drawImage(selectorImage,
                        x - selectorMargin,
                        y - (g.getFontMetrics().getWidths()[(int)selectorText] / 2) 
                        - g.getFontMetrics().getDescent()
                        + ((g.getFontMetrics().getHeight() * (selectedIndex + 1))
                        + (lineMargin * selectedIndex)), null);

            } else if (imageItems != null) {
                g.drawImage(selectorImage,
                        x - selectorMargin,
                        y - ((selectorImage.getHeight(null)
                        + imageItems[selectedIndex].getHeight(null)) / 2)
                        + ((imageItems[selectedIndex].getHeight(null) * (selectedIndex + 1))
                        + (lineMargin * (selectedIndex))), null);
            }
        } else { // Texto do seletor
            g.setColor(selectorColor);
            if (items != null) {
                g.setFont(font);
                g.drawString(selectorText + "",
                        x - selectorMargin,
                        y - (g.getFontMetrics().getWidths()[(int)selectorText] / 2)
                        - g.getFontMetrics().getDescent()
                        + ((g.getFontMetrics().getHeight() * (selectedIndex + 1))
                        + (lineMargin * selectedIndex)));
            } else if (imageItems != null) {
                g.drawString(selectorText + "",
                        x - selectorMargin,
                        y - (imageItems[selectedIndex].getHeight(null) / 2)
                        + ((imageItems[selectedIndex].getHeight(null) * (selectedIndex + 1))
                        + (lineMargin * selectedIndex)));
            }
        }
    }

    @Override
    public void update() {
        if (!pressed) {
            if (Key.isTyped(KeyEvent.VK_DOWN)) { // Pressionou 'para baixo'
                selectedIndex++;
                if (selectedIndex >= menuItems.size()) {
                    selectedIndex = 0;
                }
                pressed = true;
            } else if (Key.isTyped(KeyEvent.VK_UP)) { //Pressionou 'para cima'
                selectedIndex--;
                if (selectedIndex < 0) {
                    selectedIndex = menuItems.size() - 1;
                }
                pressed = true;
            } else if (Key.isTyped(KeyEvent.VK_ENTER)) { // Pressionou 'enter'
                LISTENER.menuAction(selectedIndex);
            }
        } else if (delay++ >= PRESS_DELAY) { // Delay entre pressionamento
            pressed = false;
            delay = 0;
        }

        for (int i = 0; i < menuItems.size(); i++) { // Verifica dos itens do menu a colisao com mouse
            if (menuItems.get(i).collidesWithMouse()) {
                selectedIndex = i;
            }
            if (menuItems.get(i).isSelected()) {
                LISTENER.menuAction(selectedIndex);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setFont(font);
        paintSelector(g); // Pinta o seletor
        for (MenuItem mi : menuItems) { // Pinta todos os itens
            mi.paint(g);
        }
        //g.drawRoundRect(x - margin, y - margin, width + margin + margin, height + margin + margin, arc, arc);
    }

    public Image getSelectorImage() {
        return selectorImage;
    }

    public void setSelectorImage(Image selectorImage) {
        this.selectorImage = selectorImage;
    }

    public void setDefaultColor(Color defaultColor) {
        for (MenuItem menuItem : menuItems) {
            menuItem.setDefaultColor(defaultColor);
        }
    }

    public void setSelectedColor(Color selectedColor) {
        for (MenuItem menuItem : menuItems) {
            menuItem.setSelectedColor(selectedColor);
        }
    }

    public void setSelectorColor(Color selectorColor) {
        this.selectorColor = selectorColor;
    }

    public void setSelectorText(char selectorText) {
        this.selectorText = selectorText;
    }

}
