package framework.audio;

/**
 * Classe abstrata de um player de audio basico
 * Cotem metodos basicos para players
 * @author Kuroda
 */
public abstract class BasicPlayer {

    static int ERROR          = 4;
    protected BasicRenderer renderer;
    protected boolean       loop;
    protected int           status;

    abstract Exception   getException();
    abstract boolean     isVolumeSupported();
    abstract int         getStatus();
    abstract void        play();
    abstract void        setVolume(float volume);
    abstract void        setLoop(boolean loop);
    abstract void        stop();
}
