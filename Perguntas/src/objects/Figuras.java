/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import framework.Util;
import game.Global;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kuroda
 */
public class Figuras {

    private int width;
    private int height;
    private int x;
    private int y;
    private String imagemCorreta;
    private List<Figura> figuras;

    public Figuras(File folder, String imagemCorreta) {
        this.imagemCorreta = imagemCorreta;
        this.width = Global.imageWidth;
        this.height = Global.imageHeight;
        this.x = Global.imageMarginX;
        this.y = Global.imageMarginY;
        this.loadImage(folder);
    }

    private void loadImage(File folder) {

        FileFilter ff = new FileFilter() {
            public boolean accept(File file){
                boolean accepted = false;
                for (String extension : Global.extensions) {
                    accepted = file.getName().endsWith(extension);
                    if (accepted) break;
                }
                return accepted;
            }
        };

        File[] files = folder.listFiles(ff);

        figuras = new ArrayList<Figura>();

        // Para cada imagem encontrada
        for (int i = 1; i <= files.length; i++) {
            String name = files[i - 1].getName();
            Image imageFile = Util.loadFile(files[i - 1]);
            int normalWidth = 0;
            int normalHeight = 0;
            if (Global.imageHeight == -1) {
                normalHeight = imageFile.getHeight(null);
            } else {
                normalHeight = height;
            }
            if (Global.imageWidth == -1) {
                normalWidth = imageFile.getWidth(null);
            } else {
                normalWidth = width;
            }
            Figura figura = new Figura(x, y, normalHeight, normalWidth, imageFile, files[i - 1].getName());
            
            if (name.substring(0, name.lastIndexOf(".")).equals(imagemCorreta)) {
                figura.setCorreto(true);
                System.out.println("Imagem correta encontrada. " + figura.getNome());
            }

            figuras.add(figura);

            // Aumenta o x de acordo com o tamanho da imagem + margem
            x += normalWidth + Global.imageMarginX;

            // Se a próxima imagem for desenhada para fora da tela
            if (x + normalWidth + Global.imageMarginX > Global.SCREEN_WIDTH) {
                x = Global.imageMarginX; // Reseta o x, volta para o inicio
                y += normalWidth + Global.imageMarginY; // Incrementa o y, uma linha abaixo
            }

            // Se ultrapassar as imagens
            if (y + normalHeight + Global.imageMarginY > Global.SCREEN_HEIGHT) {
                System.out.println("Alerta: todas as imagens não puderam ser " +
                        "desenhadas na tela");
            }
        }
    }

    public void update() {
        for (Figura figura : figuras) {
            figura.update();
        }
    }

    public void paint(Graphics g) {
        for (Figura figura : figuras) {
            figura.paint(g);
        }
    }
}
