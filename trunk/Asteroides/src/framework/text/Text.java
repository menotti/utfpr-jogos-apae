package framework.text;

import framework.Key;
import framework.Sprite;
import framework.Util;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe baseada da versão J2ME.
 * Utiliza um BITMAP de um alfabeto para escrever o texto
 *
 * @author Kuroda
 */
public class Text {

    // Alinhamento do texto
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int CENTER = 3;

    private static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public  int charHeight = 16;
    public  int charWidth = 16;
    public  int lineSpace = 5;

    // É utilizado o sprite para "quebrar" o bitmap e desenhar
    // Futuramente eh possivel altera-lo para animacao do texto////////////////
    public Sprite imageFont = null;
    
    // usado no drawPages()
    private int currentPage;
    private int pages;

    // Somente letras maiusculas?
    private boolean upperCase = true;

    public Text() {
        this.currentPage = 0;
    }

    public Text (String fontFile, int charWidth, int charHeight, String alpha) {
        this.imageFont = new Sprite(Util.loadImage(fontFile), charWidth, charHeight);
        this.charHeight = charWidth;
        this.charWidth = charHeight;
        ALPHABET = alpha;
        currentPage = 0;
        pages = 0;
    }

    public void setFont(String fontFile, int charWidth, int charHeight, String alpha) {
        this.imageFont = new Sprite(Util.loadImage(fontFile), charWidth, charHeight);
        this.charHeight = charWidth;
        this.charWidth = charHeight;
        ALPHABET = alpha;
    }

    public void setFont(Image fontBitmap, int charWidth, int charHeight, String alpha) {
        this.imageFont = new Sprite(fontBitmap, charWidth, charHeight);
        this.charHeight = charWidth;
        this.charWidth = charHeight;
        ALPHABET = alpha;
    }

    /**
     * Escreve uma linha de texto (sem quebra de linha).
     * @param str
     * @param x
     * @param y
     * @param g
     */
    public void drawText(String str, int x, int y, Graphics g) {
        drawSubstring(str, 0, str.length(), x, y, ALPHABET,
                LEFT, g);
    }

    /**
     * Escreve um texto quebrando-o em várias linhas de acordo com a largura dada.
     * Escreve o texto em páginas.
     */
    public boolean drawText(List<String> textLines, int page, int x, int y,
            int width, int height, Graphics g, boolean showPageNumber) {

        int linesPerPage = (height / charHeight);
        int pagesNumber = (textLines.size() / linesPerPage);
        
        for (int i = 0; i < linesPerPage; i++) {
            int index = i + textLines.size() % linesPerPage; //Calcula o index do texto no vetor
            if (index >= textLines.size()) break;
            String line = (String) textLines.remove(index);
            drawSubstring(line, 0, line.length(), x, y + i * charHeight, ALPHABET, LEFT, g);
        }

        // Mostra o numero da página
        if (showPageNumber) {
            drawText((page + 1) + "/" + (pagesNumber + 1),
                    x + width - 5 * charWidth, y + lineSpace, g);
        }

        pages = this.getNumberOfPages(textLines, height);
        return true;
    }

    /**
     * @param textLines
     * @param height
     * @return
     */
    private int getNumberOfPages(List<String> textLines, int height) {
        return textLines.size() / (height / charHeight);
    }

    /**
     * Desenha o texto no formato de paginas
     * @param textLines
     * @param x
     * @param y
     * @param width
     * @param height
     * @param g
     * @param navigate
     * @return
     */
    public boolean drawPages(List<String> textLines, int x, int y,
            int width, int height, Graphics g, boolean navigate) {

        if (navigate) {
            // navega no texto <-- e -->
            if (Key.isTyped(KeyEvent.VK_RIGHT)) {
                currentPage++;
                if (currentPage > pages) {
                    currentPage = pages;
                }
            } else if (Key.isTyped(KeyEvent.VK_LEFT)) {
                currentPage--;
                if (currentPage < 0) {
                    currentPage = 0;
                }
            }
        } else {
            // tecla ENTER avanca paginas e sai da caixa de texto
            if (Key.isTyped(KeyEvent.VK_ENTER)) {
                currentPage++;
                if (currentPage > pages) {
                    return true;
                }
            }
        }
        System.out.println("Current Page " + currentPage);
        // desenha a pagina corrente
        drawText(textLines, currentPage, x, y, width, height, g, navigate);

        return false;
    }

    /**
     * Quebra o texto dado uma largura.
     * @param str
     * @param width
     * @return
     */
    public List<String> wrapText(String str, int width) {
        List<String> text = new ArrayList();
        int lineStart = 0, lineEnd = 0; // Posicao inicial e final da linha
        int lineLength = width / charWidth; // Qtde de caracteres por linha
        
        String line = "";
        while (lineEnd < str.length()) {
            // Calcula o final da linha
            lineEnd += lineLength;
            // linhas normais
            if (lineEnd - 1 < str.length()) {
                // 'Quebra' o texto utilizando substring
                line = str.substring(lineStart, lineEnd);
            } else {
                line = str.substring(lineStart, str.length());
                lineEnd += line.length();
            }
            ////////////////////////////////////////////////////////////////////
            // Verificar ocorrencia de uma quebra de linha no texto??///////////
            ////////////////////////////////////////////////////////////////////
            // Acrescenta a linha ao vetor
            text.add(line);
            // Incremente o offset dentro do texto (inicio da proxima linha)
            lineStart = lineEnd;
        }        
        return text;
    }

    /**
     * Desenha a String dada uma imagem bitmap e sua sequencia (alphabet)
     * @param str
     * @param x
     * @param y
     * @param charWidth
     * @param charHeight
     * @param lettersImage
     * @param alpha
     * @param anchor
     * @param g
     */
    public void drawText(String str, int x, int y, int charWidth,
            int charHeight, Image lettersImage,
            String alpha, int anchor, Graphics g) {
        imageFont = new Sprite(lettersImage);
        imageFont.setHeight(charHeight);
        this.charHeight = charHeight;
        imageFont.setWidth(charWidth);
        this.charWidth = charWidth;
        drawSubstring(str, 0, str.length(), x, y, alpha,
                anchor, g);
    }

    /*public void drawText(String str, short[] indexes, int x, int y,
            int charWidth, Image lettersImage, String alpha, int anchor,
            Graphics g) {

        short lines = 0;
        while (lines < indexes.length && indexes[lines] != 0) {
            lines++;
        }

        short offset = 0;
        for (int i = 0; i < lines; i++) {

            drawSubstring(str, offset, indexes[i] + 1, x, y,
                    alpha, RIGHT, g);

            y += charHeight;

            offset = (short) (indexes[i] + 1);
        }
    }*/

    /**
     * Desenha uma String, uma a uma, com o alinhamento na tela desejada.
     *
     * @param str String a ser escrita
     * @param start Indice da onde deve comecar a escrever
     * @param end Indice da onde deve terminar de escrever
     * @param x Eixo X na tela
     * @param y Eixo Y da tela
     * @param charWidth Largura do caractere
     * @param lettersImage Bitmap da fonte
     * @param alpha Alfabeto em sequencia de acordo com o Bitmap
     * @param g Graphics para desenhar
     */
    private void drawSubstring(String str, int start, int end, int x, int y,
            String alpha, int anchor, Graphics g) {

        // int value of a digit
        int charIndex = 0;

        /*switch (anchor) {
            case LEFT:
                x = x;
                break;
            case RIGHT:
                x = x - str.length();
                break;

            case CENTER:
                
                break;
        }*/

        /*
         * Desenha na esquerda ou na direita.
         * Para calcular onde vai ficar na tela
         * Se ficar a direita, calcular o x, onde x é o width - tamanho da
         * String
         */
        for (int i = start; i < end; i++) {
            // Pegando o valor do index do char no alfabeto
           /* if (upperCase) {
                charIndex = alpha.indexOf(str.toUpperCase().charAt(i));
            } else {*/
                charIndex = alpha.indexOf(str.charAt(i));
           // }
            // Se houver uma ocorrencia no alfabeto
            if (charIndex != -1) {
                //imageFont.setRefPixelPosition(0, 0);

                imageFont.setFrameSequence(new int[]{charIndex});
                imageFont.setX(x);
                imageFont.setY(y);

                imageFont.draw(g); // Apenas desenha, sem qualquer animação
                x += charWidth;
            }
        }
    }

    public int getCharHeight() {
        return charHeight;
    }

    public void setCharHeight(int charHeight) {
        this.charHeight = charHeight;
    }

    public int getCharWidth() {
        return charWidth;
    }

    public int getLineSpace() {
        return lineSpace;
    }

    public void setLineSpace(int lineSpace) {
        this.lineSpace = lineSpace;
    }

    public void setCharWidth(int charWidth) {
        this.charWidth = charWidth;
    }
}
