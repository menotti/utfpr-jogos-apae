/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import game.Global;
import java.awt.Graphics;
import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author Rodrigo Kuroda
 */
public class Levels {

    private Figuras figuras;
    private Pergunta pergunta;

    public Levels() {
        loadLevels();
    }

    public void loadLevels() {
        /* Dentro da pasta tera outras pastas */
        File file = new File(Global.imagePath);
        File[] files = file.listFiles();

        /* De acordo com o level, ele carrega as imagens e a pergunta */
        if (files[Global.level].isDirectory()) {
            File[] txt = files[Global.level].listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.getName().equalsIgnoreCase("pergunta.txt");
                }
            });

            /* Se existir a pergunta, carrega as imagens e a pergunta */
            if (txt != null && txt.length > 0) {
                pergunta = new Pergunta(txt[0]);
                figuras = new Figuras(files[Global.level], pergunta.getImagemCorreta());
            } else {
                System.out.println("Pegunta (pergunta.txt) nao encontrada.");
            }
        }
    }

    public void update() {
        figuras.update();
        pergunta.update();
    }

    public void paint(Graphics g) {
        figuras.paint(g);
        pergunta.paint(g);
    }
}
