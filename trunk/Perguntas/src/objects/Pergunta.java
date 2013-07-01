/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import framework.GameObject;
import game.Global;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import screen.GameScreen;

/**
 *
 * @author Rodrigo Kuroda
 */
public class Pergunta extends GameObject {

    private String pergunta;
    private String imagemCorreta;

    public Pergunta(File file) {
        load(file);
    }

    public void load(File file) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ISO-8859-1"));

            this.pergunta = reader.readLine();
            this.imagemCorreta = reader.readLine();
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo nao encontrado.");
        } catch (IOException ex) {
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void paint(Graphics g) {
        if (!Global.hit) {
            g.setColor(Color.black);
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            String[] wrap = pergunta.split("/n");
            if (wrap.length > 1) {
                for (int i = 0; i < wrap.length; i++) {
                    g.drawString(wrap[i], 40, Global.SCREEN_HEIGHT - 70 + ( 20 * i));
                }
            } else {
                g.drawString(pergunta, 60, Global.SCREEN_HEIGHT - 40);
            }
        }
    }

    public String getImagemCorreta() {
        return imagemCorreta;
    }

    public void setImagemCorreta(String imagemCorreta) {
        this.imagemCorreta = imagemCorreta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }
}
