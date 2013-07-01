package framework;

/**
 * BASIC GAME 1
 * =======================================
 * Exemplo basico de codigo de jogo
 */
import framework.screen.Screen;
import framework.text.Text;
import game.Global;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public final class GameEngine extends JFrame implements Runnable {

    private static final boolean SHOW_INFO = false;
    private static GameEngine instance;

    private BufferedImage screenBuffer; // os desenhos sao feitos nesta imagem
    private Graphics2D graphics; // graphics da BufferedImage para desenhar nela
    private Graphics panelGraphics; // graphics do ContentPane do JFrame para desenhar nele
    
    private int fps;
    private long frameStart;
    private long frameTime;

    private Cursor mouseCursorOriginal;

    // Variaveis para transicao animada de tela
    private static int alpha = 0; // Transparencia -> Fade
    private static int right = Screen.getWidth(); // Posicao inicial da direita
    private static int left = 0; // Posicao inicial da esquerda
    private static int top = 0; //Pocicao inicial de cima
    private static int bottom = Screen.getHeight(); // Posicao inicial de baixo
    private static boolean close = false;
    private static int transitionType;
    private static Color transitionColor;

    /*
     * PROCESSO DE DESENHO Passo 1: desenhamos os objetos do jogo na
     * BufferedImage atraves de seu Graphics. Passo 2: desenhamos a
     * BufferedImage no painel contido no JFrame.
     */
        /**
     * Cria um novo motor de jogo.
     *
     * @param width Largura em pixels da tela.
     * @param height Altura em pixels da tela.
     * @param fullScreen Deve rodar em tela inteira
     */
    public GameEngine(int width, int height, boolean fullScreen) {
        Screen.setWidth(width);
        Screen.setHeight(height);

        // PREPARACAO
        init(fullScreen);

        instance = this;
        mouseCursorOriginal = getCursor();
        resetMouseCursor();
    }

    /**
     * Iniciar o jogo
     * @param Screen firstScreen
     */
    public void start(Screen firstScreen) {
        Screen.setCurrentScreen(firstScreen);
        new Thread(this).start();
    }

    /**
     * Retorna referencia ao objeto GameEngine.
     * (deve ser usado somente apos chamada do construtor GameEngine()).
     * @return Referencia ao objeto GameEngine.
     */
    public static GameEngine getInstance() {
        return instance;
    }

    public void run() {
        // LACO DO JOGO ////////////////////////////////
        while (true) {
            // PASSO 1: atualiza logica do jogo
            update();

            // PASSO 2: desenha todos os objetos do jogo
            paint();

            // PASSO 3: sincronizacao para 30 fps
            syncronizeTime();
        }
    }

    private void syncronizeTime() {
        frameTime = System.nanoTime() - frameStart;

        if (frameTime < 1) {
            frameTime = 1;
        }
        fps = (int) (1000000000 / frameTime);

        frameStart = System.nanoTime();

        if (frameTime < (1000000000 / 30)) {
            try {
                Thread.sleep(((1000000000 / 30) - frameTime) / 1000000); //passando para milisegundos
            } catch (Throwable t) {
            }
        }
    }

    private void init(boolean fullScreen) {
        //JPanel panel = new JPanel();
        //add(panel);

        JPanel panel = (JPanel) getContentPane();
        panel.setPreferredSize(new Dimension(Screen.getWidth(),Screen.getHeight()));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(Global.TITLE);
        setIconImage(Util.loadImage(Global.ICON));
        setResizable(false);

        enableEvents(MouseEvent.MOUSE_EVENT_MASK | MouseEvent.MOUSE_MOTION_EVENT_MASK);

        screenBuffer = new BufferedImage(Screen.getWidth(),
                Screen.getHeight(), BufferedImage.TYPE_INT_RGB);
        graphics = screenBuffer.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias para tudo
                                  RenderingHints.VALUE_ANTIALIAS_OFF);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                                  RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // Anti-alias para texto
        
        // Referencia do graphics para a classe Text
        Text.setGraphics(graphics);

        if (fullScreen) {
            setUndecorated(true);
            panelGraphics = getFullScreenGraphics(panel);
            pack();
        } else {
            pack();
            setLocationRelativeTo(null);
            panelGraphics = panel.getGraphics();
        }

        // tempo atual do jogo
        frameStart = System.nanoTime();
        frameTime = 0;

        setVisible(true);
    }

    /**
     * Troca o cursor do mouse para a imagem passada.
     * @param mouseCursor Imagem do cursor. Caso seja null, cursor fica invisível.
     */
    final void setMouseCursor(Image mouseCursor) {
        if (mouseCursor == null) {
            mouseCursor = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }
        setCursor(getToolkit().createCustomCursor(mouseCursor, new Point(0, 0), "null"));
    }
    
    final void resetMouseCursor() {
        setCursor(mouseCursorOriginal);
    }

    private void update() {
        if ((Screen.getLastScreen() != null) && Screen.getCurrentScreen().equals(Screen.getLastScreen())) {
            Screen.getCurrentScreen().update();
        }
    }

    private void paint() {
        // PASSO 3: DESENHO /////////////////////////

        // Verifica transição de tela
        if (!Screen.getCurrentScreen().equals(Screen.getLastScreen())) {
            screenTransition(graphics);
        } else {
            Screen.getCurrentScreen().paint(graphics);
        }

        Mouse.paint(graphics);

        // desenha fps
        if (SHOW_INFO) {
            graphics.setColor(Color.GREEN);
            graphics.setFont(new Font("Arial", Font.PLAIN, 10));
            graphics.drawString("FPS  " + fps, Screen.getWidth() - 60, Screen.getHeight() - 20);
        }
        // agora desenha a imagem no painel
        panelGraphics.drawImage(screenBuffer, 0, 0, null);
    }

    private Graphics getFullScreenGraphics(JPanel panel) {
        panelGraphics = panel.getGraphics();

        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        setLayout(null);
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR),
                new Point(0, 0), "empty"));

        graphicsDevice.setFullScreenWindow(this);
        graphicsDevice.setDisplayMode(new DisplayMode(Screen.getWidth(),
                Screen.getHeight(), 32, 60));
        panelGraphics = graphicsDevice.getFullScreenWindow().getGraphics();
        return panelGraphics;
    }

    /**
     * Sobrescreve metodo de captura de teclas da JFrame.
     * @param e Objeto de evento de teclado.
     */
    @Override
    protected void processKeyEvent(KeyEvent e) {
        /**
         * Constantes
         * http://download.oracle.com/javase/1.4.2/docs/api/constant-values.html#java.awt.event.KeyEvent.VK_TAB
         * public static final int 	KEY_PRESSED 	401
         * public static final int 	KEY_RELEASED 	402
         * public static final int 	KEY_TYPED 	400
         */
        Key.setKey(e.getKeyCode(), (e.getID() == KeyEvent.KEY_PRESSED));
        //System.out.println("code: " + e.getKeyCode() + "  event: " + e.getID());
    }

    /**
     * Sobrescreve metodo de captura do clique do mouse da JFrame.
     * @param e Objeto de evento de clique do mouse.
     */
    @Override
    protected void processMouseEvent(MouseEvent e) {
        Mouse.setPressed(e.getButton(), (e.getID() == MouseEvent.MOUSE_PRESSED));
    }

    /**
     * Sobrescreve metodo de captura de movimento do mouse da JFrame.
     * @param e Objeto de evento de movimento do mouse.
     */
    @Override
    protected void processMouseMotionEvent(MouseEvent e) {
        //e.getID() move ou drag
        Mouse.setX(e.getX());
        Mouse.setY(e.getY());
    }

    public static void setTransitionType(int type) {
        transitionType = type;
    }

    private void screenTransition(Graphics g) {
        if (transitionColor == null) {
            transitionColor = new Color(0, 0, 0);
        }

        switch (transitionType) {
            case Screen.HORIZONTAL_OPEN_CLOSE: {
                if (!close) {
                    left += 10;
                    right += 10;
                    g.setColor(transitionColor);
                    g.fillRect(0, 0, left, Screen.getHeight());
                    g.fillRect(Screen.getWidth() - right, 0, right, Screen.getHeight());
                    if (right >= Screen.getWidth() / 2 && left >= Screen.getWidth() / 2) {
                        close = true;
                    }
                } else {
                    if (right > 0 && left > 0) {
                        Screen.getCurrentScreen().paint(g);
                        left -= 10;
                        right -= 10;
                        g.setColor(transitionColor);
                        g.fillRect(0, 0, left, Screen.getHeight());
                        g.fillRect(Screen.getWidth() - right, 0, right, Screen.getHeight());
                    } else {
                        Screen.getCurrentScreen().paint(g);
                        Screen.setLastScreen(Screen.getCurrentScreen());
                        alpha = 0;
                        close = false;
                    }
                }
            }
            break;
            case Screen.VERTICAL_OPEN_CLOSE: {
                if (!close) {
                    top += 10;
                    bottom += 10;
                    g.setColor(transitionColor);
                    g.fillRect(0, 0, Screen.getWidth(), top);
                    g.fillRect(0, Screen.getHeight() - bottom, Screen.getWidth(), bottom);
                    if (top >= Screen.getWidth() / 2 && bottom >= Screen.getWidth() / 2) {
                        close = true;
                    }
                } else {
                    if (top > 0 && bottom > 0) {
                        Screen.getCurrentScreen().paint(g);
                        top -= 10;
                        bottom -= 10;
                        g.setColor(transitionColor);
                        g.fillRect(0, 0, Screen.getWidth(), top);
                        g.fillRect(0, Screen.getHeight() - bottom, Screen.getWidth(), bottom);
                    } else {
                        //currentScreen.paint(g);
                        Screen.setLastScreen(Screen.getCurrentScreen());
                        alpha = 0;
                        close = false;
                    }
                }
            }
            break;

            case Screen.FADE_IN_FADE_OUT: {
                if (!close) {
                    if (Screen.getLastScreen() == null) {
                        alpha = 255;
                        close = true;
                    } else {
                        if ((alpha += 10) > 255) {
                            alpha = 255;
                            close = true;
                        }
                        //lastScreen.paint(graphics);
                        g.setColor(new Color(transitionColor.getRed(),
                                transitionColor.getGreen(),
                                transitionColor.getBlue(), alpha));
                        g.fillRect(0, 0, Screen.getWidth(), Screen.getHeight());
                    }
                } else {
                    Screen.getCurrentScreen().paint(g);
                    if (alpha > 0) {
                        if ((alpha -= 10) < 0) {
                            alpha = 0;
                        }
                        g.setColor(new Color(transitionColor.getRed(),
                                transitionColor.getGreen(),
                                transitionColor.getBlue(), alpha));
                        g.fillRect(0, 0, Screen.getWidth(), Screen.getHeight());
                    } else {
                        alpha = 0;
                        close = false;
                        Screen.setLastScreen(Screen.getCurrentScreen());
                    }
                }
            }
        }
    }

    public static boolean isShowInfo() {
        return SHOW_INFO;
    }
}
