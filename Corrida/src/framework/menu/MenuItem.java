package framework.menu;

import framework.GameObject;
import framework.Mouse;
import framework.Sprite;
import framework.text.Text;
import game.Global;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;

/**
 * Classe que representa um item
 * @author Rodrigo Kuroda
 */
class MenuItem extends GameObject {

    private Font font;
    private String string;
    private Sprite sprite;
    private final int DESCEND;
    private static Color defaultColor;
    private static Color selectedColor;

    protected MenuItem(String option, Font font, int x, int y) {
        this.x = x;
        this.y = y;
        defaultColor = Color.black;
        selectedColor = Color.white;
        this.font = font;
        this.string = option;
        this.image = null;
        this.sprite = null;
        // Calcula a largura e altura da string (=~)
        Text.getGraphics().setFont(font);
        this.width = Text.getFontMetrics().stringWidth(option);
        this.height = Text.getFontMetrics().getHeight();
        this.DESCEND = Text.getFontMetrics().getDescent(); // Offset abaixo da string
    }

    protected MenuItem(Image option, int x, int y) {
        this.x = x;
        this.y = y;
        this.string = null;
        this.image = option;
        this.sprite = null;
        // Altura e largura da imagem
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.DESCEND = 0;
    }

    protected MenuItem(Sprite option, int x, int y) {
        this.x = x;
        this.y = y;
        this.string = null;
        this.image = null;
        this.sprite = option;
        // Altura e largura do sprite
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
        this.DESCEND = 0;
    }

    @Override
    public void update() {
    }

    @Override
    public void paint(Graphics g) {
        if (string != null) {
            g.setFont(font);
            //g.setColor(new Color(25, 25, 25, 40));
            //g.fillRoundRect(x, y - height, width, height, 20, 20);
            if (collidesWithMouse()) {
                g.setColor(selectedColor);
            } else {
                g.setColor(defaultColor);
            }
            g.drawString(string, x, y + height);
        } else if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else if (sprite != null) {
            sprite.draw(g);
        }
    }

    /**
     *
     * @return
     */
    protected boolean collidesWithMouse() {
        if (Global.FULL_SCREEN) {
            if (Mouse.getX() <= x + width
                    && Mouse.getX() >= x
                    && Mouse.getY() <= y + height + DESCEND // Ajustando offset
                    && Mouse.getY() >= y + DESCEND) {
                return true;
            }
        } else {
            if (Mouse.getX() <= x + width
                    && Mouse.getX() >= x
                    && Mouse.getY() <= y + height + DESCEND // Ajustando offset
                    && Mouse.getY() >= y + DESCEND) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return true se mouse esta colidindo com a Option, false se nao
     */
    protected boolean isSelected() {
        if (collidesWithMouse() && Mouse.isClicked(MouseEvent.BUTTON1)) {
            return true;
        }
        return false;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }
}
