/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import framework.GameObject;
import framework.Mouse;
import framework.Util;
import game.Global;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import screen.GameScreen;

/**
 *
 * @author Rodrigo Kuroda
 */
public class Figura extends GameObject {

    private int id;
    private boolean correto;
    private boolean clicou;
    private boolean selecionou;
    private String nome;

    public Figura(int x, int y, int height, int width, Image image, String name) {
        super();
        this.height = height;
        this.width = width;
        this.image = image;
        this.x = x;
        this.y = y;
        this.nome = name;
        correto = false;
    }

    @Override
    public void update() {
        /*
         * Verifica se o mouse foi pressionado, se nao foi selecionado
         * anteriormente, se ja nao acertou
         * se nao ultrapassou o limite de tentativas.
         */
        if (Mouse.isPressed(MouseEvent.BUTTON1) && !selecionou
                && (Global.tentativas < Global.MAX_TENTATIVAS) && !Global.hit) {

            /* Verifica se clicou em cim da imagem */
            if (Mouse.getX() <= width + x
                    && Mouse.getX() >= x
                    && Mouse.getY() <= height + y
                    && Mouse.getY() >= y) {

                selecionou = true;
                if (correto) {
                    Global.hit = true;
                }
                Global.tentativas++;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
        if (selecionou && correto) {
            g.drawImage(Util.loadImage(Global.HIT), x, y, width, height, null);
        } else if (selecionou && !correto) {
            g.drawImage(Util.loadImage(Global.MISS), x, y, width, height, null);
        }
    }

    public boolean isClicou() {
        return clicou;
    }

    public void setClicou(boolean clicou) {
        this.clicou = clicou;
    }

    public boolean isSelecionou() {
        return selecionou;
    }

    public void setSelecionou(boolean selecionou) {
        this.selecionou = selecionou;
    }

    public boolean isCorreto() {
        return correto;
    }

    public void setCorreto(boolean correto) {
        this.correto = correto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
