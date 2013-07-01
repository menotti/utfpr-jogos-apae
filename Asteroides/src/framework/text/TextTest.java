package framework.text;

import java.util.List;

/**
 *
 * @author Rodrigo Kuroda
 */
public class TextTest {
    public static void main(String[] args) {
        List v = new Text().wrapText("Nenhum diretorio com as imagens e a pergunta foi ".toUpperCase()
                        + " encontrado. Por favor, selecione um diretorio que contenha".toUpperCase()
                        + " as imagens e perguntas.".toUpperCase(), 256);
        for (Object object : v) {
            System.out.println(object);
        }
    }
}
