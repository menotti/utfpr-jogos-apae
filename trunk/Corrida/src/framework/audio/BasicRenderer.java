package framework.audio;

/**
 *
 * @author Rodrigo Kuroda
 */
public abstract class BasicRenderer implements Runnable {
    /**
     * Audio renderer status indicates that the audio is currently playing.
     */
    public static int PLAYING        = 1;
    /**
     * Audio renderer status indicates that the audio is currently stopped.
     */
    static int STOPPED        = 2;
    /**
     * Audio renderer status indicates that the audio has finished played.
     */
    static int END_OF_SOUND   = 3;
    /**
     * Audio renderer status indicates that the audio is failed to play.
     */
    static int ERROR          = 4;

    abstract int        getStatus();
    abstract Exception  getException();
    @Override
    protected abstract  BasicRenderer clone();
    abstract void       stop();
    abstract void       setVolume(float volume);

    abstract void       setLoop(boolean loop);

}
