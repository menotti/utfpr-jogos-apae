package framework.text;

import framework.Key;
import framework.Sprite;
import framework.Util;
import java.awt.FontMetrics;
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

    // Valores possíveis para alinhamento do texto
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int JUSTIFIED = 3;
    public static final int CENTER = 4;
    private int currentAlign = LEFT;
    private static double charSpace = 0; // Váriavel auxiliar para justificação do texto
    /** Alfabeto padrão é "ABCDEFGHIJKLMNOPQRSTUVWXYZ " */
    private static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
    private static int charHeight = 16; // Altura do caractere
    private static int charWidth = 16; // Largura do caractere
    private static int lineSpace = 5; // Espaçamento entre linhas
    // É utilizado o sprite para "quebrar" o bitmap e desenhar
    // Futuramente eh possivel altera-lo para animacao do texto////////////////
    private Sprite bitmapFont = null;
    // Usado para desenhar o texto em páginas
    private int currentPage = 0;
    private int pages;
    // Graphics utilizado para cálculo do largura e altura do texto baseado na fonte
    private static Graphics g;

    public Text() {

    }

    /**
     * Cria um novo Text. Os seguintes parâmetros são necessários para se usá-lo:
     * @param bitmap O caminho da imagem bitmap da fonte.
     * @param charWidth A largura de cada caractere na imagem.
     * @param charHeight A altura de cada caractere na imagem.
     * @param alpha Ordem dos caracteres no bitmap, da esquerda para direita,
     * de cima para baixo.
     */
    public Text(String bitmap, int charWidth, int charHeight, String alpha) {
        bitmapFont = new Sprite(Util.loadImage(bitmap), charWidth, charHeight);
        Text.charHeight = charHeight;
        Text.charWidth = charWidth;
        ALPHABET = alpha;
        pages = 0;
    }

    /**
     * Define um novo bitmap de fonte através do caminho do bitmap.
     *
     * @param fontFile O caminho da imagem.
     * @param charWidth Largura de cada caractere
     * @param charHeight Altura de cada caractere
     * @param alpha Ordem dos caracteres no bitmap, da esquerda para direita,
     * de cima para baixo.
     */
    public void setFont(String fontFile, int charWidth, int charHeight, String alpha) {
        bitmapFont = new Sprite(Util.loadImage(fontFile), charWidth, charHeight);
        charHeight = charWidth;
        charWidth = charHeight;
        ALPHABET = alpha;
    }

    /**
     * Define um novo bitmap de fonte através de uma imagem bitmap da fonte.
     *
     * @param fontFile O caminho da imagem.
     * @param charWidth Largura de cada caractere
     * @param charHeight Altura de cada caractere
     * @param alpha Ordem dos caracteres no bitmap, da esquerda para direita,
     * de cima para baixo.
     */
    public void setFont(Image fontBitmap, int charWidth, int charHeight, String alpha) {
        bitmapFont = new Sprite(fontBitmap, charWidth, charHeight);
        Text.charHeight = charWidth;
        Text.charWidth = charHeight;
        ALPHABET = alpha;
    }

    /**
     * Escreve uma linha de texto (sem quebra de linha).
     * @param str Texto a ser desenhado.
     * @param x Ponto X na tela.
     * @param y Ponto Y na tela
     * @param g Graphics
     */
    public void drawText(String str, int x, int y, Graphics g) {
        drawSubstring(str, 0, str.length(), x, y, ALPHABET, g);
    }

    /**
     * Desenha as lista de linhas com um alinhamento.
     *
     * Alerta: o (x + width) não pode ser maior que a tela. Case seja, quando o
     * alinhamento for centralizado ou à direita, desenhará fora da tela.
     *
     * @param textLines Lista de cada linha que deve ser denhada.
     * @param x Ponto Y da tela.
     * @param y Ponto X da tela.
     * @param width Largura do local onde a string deve ser desenhada.
     * @param height Altura do local onde a string deve ser desenhada.
     * @param align Valores possível são Text.LEFT, Text.RIGHT e Text.CENTER.
     * O alinhamento padrão é Text.LEFT (à esquerda).
     * @param g Graphics.
     */
    public void drawText(List<String> textLines, int x, int y, int width,
            int height, int align, Graphics g) {

        int alignX = 0; // Váriavel auxiliar para cálculo do eixo X
        int alignY = y; // Váriavel auxiliar para cálculo do eixo Y
        for (String line : textLines) {
            alignX = calculeAlignedPointX(line, x, width, align);
            // Desenhando a linha
            drawSubstring(line, 0, line.length(), alignX, alignY, ALPHABET, g);
            alignY += charWidth + lineSpace; // Nova linha
        }
    }

    public static int calculeAlignedPointX(String line, int x, int width, int align) {
        switch (align) {
            case RIGHT:
                /* Calcula o espaço que sobrou, diminuindo 'a posição mais a
                 * largura onde o texto deve ser desenhado' da 'largura do
                 * texto'. Este será o pronto x onde se deve desenhar para que o
                 * texto esteja alinhado à direita.
                 */
                return (x + width) - getFontMetrics().stringWidth(line);
            case CENTER:
                /* Calcula o espaço que sobrou, diminuindo 'a posição mais a
                 * 'largura divido por 2 (meio)'' da 'largura do texto divido
                 * por 2 (metade)'. Este será o pronto x onde se deve desenhar
                 * para que o texto esteja centralizado.
                 */
                return (x + (width / 2)) - (getFontMetrics().stringWidth(line) / 2);
            case JUSTIFIED:
                /* Calcula o espaço que sobrou e divide pela quantidade de
                 * 'caracteres da linha menos 1 (não adiciona no final da
                 * linha)'. O resultado é o espaço que deve ser adicinado entre
                 * cada caractere.
                 */
                charSpace = (width - (getFontMetrics().stringWidth(line))) / line.length() - 1;

                /* Calcula o espaço que sobrou e divide pela quantidade de
                 * 'espaços na linha'. O resultado é o espaço que deve ser
                 * adicinado a cada cada espaço da linha.
                 */
                //charSpace = (width - (getStringWidth(line))) / getSpaceCount(line);
                return x;
            default:
                return x;
        }
    }

    /**
     * Calcula a quantidade de página que uma determinada lista de linhas 
     * ocupará.
     * @param textLines Lista de cada linha.
     * @param height Altura onde o texto deve ser desenhado
     * @return A quantidade total de páginas.
     */
    private int getNumberOfPages(List<String> textLines, int height) {
        return textLines.size() / (height / charHeight);
    }

    /**
     *
     * @param textLines Lista de cada linha do texto
     * @param x Posição X do texto na tela
     * @param y Posição Y do texto na tela
     * @param width Limite de largura para desenhar as linhas do texto
     * @param height Limite de altura para desenhar as linhas do texto
     * @param align Valores possível são Text.LEFT, Text.RIGHT e Text.CENTER.
     * O alinhamento padrão é Text.LEFT (à esquerda).
     * @param g Graphics
     * @param navigate Se true, jogador pode navegar pelas setas avaçando e
     * voltando as páginas. Caso contrário, pressiona Enter para avançar e não
     * pode voltar / navegar nas páginas.
     * @return
     */
    public void drawPages(List<String> textLines, int x, int y,
            int width, int height, int align, Graphics g, boolean navigate) {
        // -1 porque as páginas vão de 0 a total de páginas - 1.
        // Se removido, desenha uma ultima página em branco.
        pages = textLines.size() / (height / charHeight) - 1;
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
                    return;
                }
            }
        }
        System.out.println(currentPage + " / " + pages);
        // desenha a pagina corrente
        drawCurrentPage(textLines, x, y, width, height, align, g);
    }

    private void drawCurrentPage(List<String> textLines, int x,
            int y, int width, int height, int align, Graphics g) {
        int alignX = 0; // Váriavel auxiliar para cálculo do eixo X
        int alignY = y; // Váriavel auxiliar para cálculo do eixo Y
        // Linhas por página
        int linePerPage = height / charHeight;
        for (int i = (currentPage * linePerPage);
                i < (currentPage * linePerPage) + linePerPage
                && i < textLines.size(); i++) {
            // Alinhando o texto
            alignX = calculeAlignedPointX(textLines.get(i), x, width, align);
            // Desenhando a linha
            drawSubstring(textLines.get(i), 0, textLines.get(i).length(),
                    alignX, alignY, ALPHABET, g);
            alignY += charWidth + lineSpace; // Nova linha
        }
    }

    /**
     * Quebra o texto dado uma largura.
     * A quebra de linha detecta os espaços. Caso uma palavra não couber em
     * uma única linha, esta é autaticamente quebrada no meio.
     * Funciona com fontes monoespaçadas.
     *
     * @param str O texto a ser quebrado por linhas
     * @param width A largura em que o texto tem que ser desenhado. Se for menor
     * que a largura do caracter, automaticamente a largura para se desenhar
     * sera a largura do caracter, ou seja, 1 caracter por linha.
     * @return Uma lista de cada linha.
     */
    public List<String> wrapText(String str, int width) {
        List<String> text = new ArrayList();
        int lineStart = 0, lineEnd = 0; // Posição inicial e final da linha

        if (width < charWidth) { // Largura mínima para linha
            width = charWidth;
        }

        int lineLength = width / charWidth; // Qtde de caracteres por linha
        int[] spaceIndexes = getSpaceIndexes(str);
        String line = "";
        int lastSpaceIndexInLine = 0; // Index do ultimo espaço na linha
        while (lineEnd < str.length()) {
            // Calcula o final da linha
            lineEnd += lineLength;
            lastSpaceIndexInLine = 0;
            for (int i = 0; i < spaceIndexes.length; i++) {
                // Pega o último espaço dentro da linha
                if (spaceIndexes[i] > lineStart && spaceIndexes[i] < lineEnd) {
                    lastSpaceIndexInLine = spaceIndexes[i];
                }
            }
            // Se encontrar um espaço, quebra a string onde tiver o espaço,
            // sem quebrar palavras ao meio
            if (lastSpaceIndexInLine > 0) {
                lineEnd = lastSpaceIndexInLine;
            }
            // linhas normais
            if (lineEnd - 1 < str.length()) {
                // Quebra o texto utilizando substring
                line = str.substring(lineStart, lineEnd);
            } else { // Final da string
                line = str.substring(lineStart, str.length());
                lineEnd += line.length();
            }
            if (line.substring(0, 1).equals(" ")) { // Caso haja espaço no inicio da linha
                line = line.substring(1); // remove
            }
            // Acrescenta a linha ao vetor
            text.add(line);
            // Incremente o offset dentro do texto (inicio da proxima linha)
            lineStart = lineEnd;
        }
        return text;
    }

    /**
     * Desenha a string dada uma imagem bitmap e sua sequencia (alphabet).
     * @param str Texto a ser desenhado.
     * @param x Ponto X da tela.
     * @param y Ponto Y da tela.
     * @param charWidth Largura do caractere.
     * @param charHeight Altura do caractere.
     * @param lettersImage Imagem bitmap da fonte.
     * @param alpha Alfabeto do bitmap (sequencia dos caracteres da imagem).
     * @param align Valores possível são Text.LEFT, Text.RIGHT e Text.CENTER.
     * O alinhamento padrão é Text.LEFT (à esquerda).
     * @param g Graphics.
     */
    public void drawText(String str, int x, int y, int charWidth, int charHeight,
            Image lettersImage, String alpha, int anchor, Graphics g) {
        bitmapFont = new Sprite(lettersImage);
        bitmapFont.setHeight(charHeight);
        Text.charHeight = charHeight;
        bitmapFont.setWidth(charWidth);
        Text.charWidth = charWidth;
        drawSubstring(str, 0, str.length(), x, y, alpha, g);
    }

    /**
     * Desenha uma String, uma a uma, com o alinhamento na tela desejada.
     * Alerta: cuidado com o alfabeto, pois é case sensitive.
     *
     * @param str String a ser desenhada.
     * @param start Índice de onde deve se iniciar a desenhar.
     * @param end Índice de onde deve se terminar de desenhar.
     * @param x Ponto X da tela.
     * @param y Ponto Y da tela.
     * @param charWidth Largura do caractere.
     * @param lettersImage Bitmap da fonte.
     * @param alpha Alfabeto em sequencia de acordo com o Bitmap.
     * @param g Graphics para desenhar.
     */
    protected void drawSubstring(String str, int start, int end, int x, int y,
            String alpha, Graphics g) {
        double dx = x; // Precisão para justificar o texto
        // int value of a digit
        int charIndex = 0;
        for (int i = start; i < end; i++) {
            // Pegando o valor do index do char no alfabeto
            charIndex = alpha.indexOf(str.charAt(i));

            // Se houver uma ocorrencia no alfabeto
            if (charIndex != -1) {
                bitmapFont.setFrameSequence(new int[]{charIndex});
                /*if(str.charAt(i) == ' ') {
                 *   dx += charSpace;
                 *}
                 */
                bitmapFont.setX((int) dx);
                bitmapFont.setY(y);
                bitmapFont.draw(g); // Apenas desenha, sem qualquer animação

                /* Adiciona o tamanho do caractere mais o espaço caso for o
                 * texto seja justificado.
                 */
                dx += charWidth + charSpace;
            }
        }
    }

    /**
     * Retorna a quantidade de espaços que existem na String.
     * @param content Texto a ser analisado.
     * @return A quantidade de caracteres.
     */
    protected static int getSpaceCount(String content) {
        int result = 0;
        char[] charArray = content.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == ' ') {
                result++;
            }
        }
        return result;
    }

    /**
     * Retorna todos os index dos espaços existentes numa String.
     * @param content O texto a ser analisado.
     * @return Os índices de todas os espços contidos na string.
     */
    protected static int[] getSpaceIndexes(String content) {
        int[] result = new int[getSpaceCount(content)];
        int counter = 0;
        char[] charArray = content.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == ' ') {
                result[counter] = i;
                counter++;
            }
        }
        return result;
    }

    public static void setGraphics(Graphics g) {
        Text.g = g;
    }

    public static Graphics getGraphics() {
        return Text.g;
    }

    public static FontMetrics getFontMetrics() {
        return g.getFontMetrics();
    }

    public int getAlign() {
        return currentAlign;
    }

    public void setCurrentAlign(int align) {
        this.currentAlign = align;
    }

    public int getCharHeight() {
        return charHeight;
    }

    public void setCharHeight(int charHeight) {
        Text.charHeight = charHeight;
    }

    public int getCharWidth() {
        return charWidth;
    }

    public int getLineSpace() {
        return lineSpace;
    }

    public void setLineSpace(int lineSpace) {
        Text.lineSpace = lineSpace;
    }

    public void setCharWidth(int charWidth) {
        Text.charWidth = charWidth;
    }
}
