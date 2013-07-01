package framework;

/**
 * BASIC GAME 1
 * =======================================
 * Exemplo basico de codigo de jogo
 */
import framework.screen.Screen;
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

public class GameEngine extends JFrame implements Runnable {

    private static final boolean SHOW_INFO = false;

    private BufferedImage screenBuffer; // os desenhos sï¿½o feitos nesta imagem
    private Graphics graphics; // graphics da BufferedImage para desenhar nela
    private Graphics panelGraphics; // graphics do ContentPane do JFrame para desenhar nele
    private int fps;
    private long frameStart;
    private long frameTime;
    private static GameEngine instance;
    private Cursor mouseCursorOriginal;

    // AUXILIARES PARA VERIFICAR TRANSICAO DE TELAS
    private Screen currentScreen;
    private Screen lastScreen;

    // TIPOS DE TRANSICOES DE TELAS
    private final int HORIZONTAL_OPEN_CLOSE = 1;
    private final int VERTICAL_OPEN_CLOSE = 2;

    // VARIAVES AUXILIARES PARA TRANSICAO DE TELAS
    private int alpha = 0;
    private int right = Screen.getWidth();
    private int left = 0;
    private int top = 0;
    private int bottom = Screen.getHeight();
    private boolean close = false;
    private static int transitionType;
    private Color transitionColor;

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
        /*
        while (frameTime < (1000000000 / Util.TARGET_FPS)) {
        frameTime = System.nanoTime() - frameStart;
        }
         */
    }

    private void init(boolean fullScreen) {
        // setTitle("BASIC GAME");
        JPanel panel = (JPanel) getContentPane();
        panel.setPreferredSize(new Dimension(Screen.getWidth(),
                Screen.getHeight()));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        enableEvents(MouseEvent.MOUSE_EVENT_MASK | MouseEvent.MOUSE_MOTION_EVENT_MASK);

        screenBuffer = new BufferedImage(Screen.getWidth(),
                Screen.getHeight(), BufferedImage.TYPE_INT_RGB);
        graphics = screenBuffer.getGraphics();

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
    void setMouseCursor(Image mouseCursor) {
        if (mouseCursor == null) {
            mouseCursor = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
        }
        setCursor(getToolkit().createCustomCursor(mouseCursor, new Point(0, 0), "null"));
    }

    void resetMouseCursor() {
        setCursor(mouseCursorOriginal);
    }

    private void update() {
        if ((lastScreen != null) && currentScreen.equals(lastScreen)) {
            currentScreen.update();
        }
    }

    private void paint() {
        // PASSO 3: DESENHO /////////////////////////
        currentScreen = Screen.getCurrentScreen();
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);

        // TRANSICAO DE TELA ////////////////////////
        if (!currentScreen.equals(lastScreen)) {
            screenTransition(graphics);
        } else {
            Screen.getCurrentScreen().paint(graphics);
            lastScreen = Screen.getCurrentScreen();
        }

        // desenha fps
        /*if (SHOW_INFO) {
            graphics.setColor(Color.GREEN);
            graphics.setFont(new Font("Arial", Font.PLAIN, 10));
            graphics.drawString("FPS  " + fps, Screen.getWidth() - 60,
                Screen.getHeight() - 20);
        }*/
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
                Screen.getHeight(), 16, 0));
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
        //Key.setKey(e.getKeyCode(), e.getID());
        System.out.println("code: " + e.getKeyCode() + "  event: " + e.getID());
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
            case HORIZONTAL_OPEN_CLOSE: {
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
                        currentScreen.paint(g);
                        left -= 10;
                        right -= 10;
                        g.setColor(transitionColor);
                        g.fillRect(0, 0, left, Screen.getHeight());
                        g.fillRect(Screen.getWidth() - right, 0, right, Screen.getHeight());
                    } else {
                        currentScreen.paint(g);
                        lastScreen = currentScreen;
                        alpha = 0;
                        close = false;
                    }
                }
            }
            break;
            case VERTICAL_OPEN_CLOSE: {
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
                        currentScreen.paint(g);
                        top -= 10;
                        bottom -= 10;
                        g.setColor(transitionColor);
                        g.fillRect(0, 0, Screen.getWidth(), top);
                        g.fillRect(0, Screen.getHeight() - bottom, Screen.getWidth(), bottom);
                    } else {
                        //currentScreen.paint(g);
                        lastScreen = currentScreen;
                        alpha = 0;
                        close = false;
                    }
                }
            }
            break;

            // FADE_IN_FADE_OUT
            default: {
                if (!close) {
                    if (lastScreen == null) {
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
                    currentScreen.paint(g);
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
                        lastScreen = currentScreen;
                    }
                }
            }
        }
    }
    public static boolean isShowInfo() {
        return SHOW_INFO;
    }
}
