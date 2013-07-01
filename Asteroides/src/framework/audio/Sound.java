package framework.audio;

import game.Global;
import java.io.File;
import java.io.IOException;
import javax.media.CannotRealizeException;
import javax.media.ControllerEvent;
import javax.media.EndOfMediaEvent;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.Time;

import javax.media.bean.playerbean.MediaPlayer;
import javax.media.bean.playerbean.MediaPlayerResource;
import javax.media.format.AudioFormat;

/**
 *
 * @author marcos
 */
public class Sound implements Runnable {

    //private static boolean enabled = true;
    private MediaPlayer audioPlayer = new MediaPlayer();
    private boolean playing;
    private int volume = 3;
    private String file;
    private boolean loop;

    static {
        Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
        Format input2 = new AudioFormat(AudioFormat.MPEG);
        Format output = new AudioFormat(AudioFormat.LINEAR);
        PlugInManager.addPlugIn(
                "com.sun.media.codec.audio.mp3.JavaDecoder",
                new Format[]{input1, input2},
                new Format[]{output},
                PlugInManager.CODEC);
        /*try {
        PlugInManager.commit();
        } catch (IOException ex) {
        ex.printStackTrace();
        }*/
    }

    public Sound(String file, boolean loop) {
        // Formatos suportados.
                    /* Tipos de audio
        audio/wav
        audio/midi
        audio/mp3
         */
        this.file = file;
        this.loop = loop;
    }

    /**
     * Para de tocar o audio, se este estiver tocando.
     */
    public void stop() {
        if (audioPlayer != null) {
            playing = false;
            audioPlayer.stop();
            audioPlayer.close();
        }
    }

    /**
     * Toca um audio, recebendo por parametro o endereco do arquivo e
     * se tocara repetitivamente.
     * Utiliza Java Media Framework (JMF) 2.1.1
     * Os formatos suportados sao: MID, MP3 e WAV.
     *
     * @param file
     * @param loop
     */
    public void play() {
        if (!Global.sound) {
            if(playing) {
                stop();
                System.out.println("STOP");
                return;
            }
            return;
        }

        if (audioPlayer != null) {
            stop();
        }

        this.run();
    }

    public synchronized void controllerUpdate(ControllerEvent e) {
        if (e instanceof EndOfMediaEvent) {
            audioPlayer.setMediaTime(new Time(0));
            System.out.println("End of Media - restarting");
            audioPlayer.stop();
            audioPlayer.close();
        }
    }

    /**
     * Define se o audio ira tocar repetitivamente.
     * @param loop
     */
    public void setLoop(boolean loop) {
        audioPlayer.setPlaybackLoop(loop);
    }

    /**
     * Retorna true se o player estiver tocando repetitivamente o audio.
     * Caso contrario, retorna false.
     *
     * @return true se o player esta em loop, false se não.
     */
    public boolean isLoop() {
        return audioPlayer.isPlayBackLoop();
    }

    /**
     * Define o volume que o audio ira tocar.
     * Valores aceitos são de 0 a 5, onde 0 é mudo e 5 é  volume máximo.
     * O padrão é 3. (*THREE*)
     * @param level
     */
    public void setVolume(int level) {
        audioPlayer.setVolumeLevel(String.valueOf(level));
    }

    /**
     * Retorna o volume atual do player.
     * O padrão é 3. (*THREE*)
     * @return volume
     */
    public int getVolume() {
        return Integer.parseInt(audioPlayer.getVolumeLevel());
    }

    public boolean isPlaying() {
        return playing;
    }

    /**
     * Se estiver mudo, retorna true.
     * @return true se mudo, false se não.
     */
    public boolean isMute() {
        return audioPlayer.getVolumeLevel().equals("0");
    }

    /**
     * Define se o player esta mudo ou nao.
     * Se true, define o volume para zero.
     * Caso contrario, define o valor que estava definido anteriormente.
     * @param mute
     */
    public void setMute(boolean mute) {
        if (mute) {
            audioPlayer.setVolumeLevel(String.valueOf(MediaPlayerResource.getString("0")));
        } else {
            audioPlayer.setVolumeLevel(String.valueOf(volume));
        }
    }

    /**
     * Retorna o tempo de execução da musica.
     * @return double segundos
     */
    public double getMediaTime() {
        return audioPlayer.getMediaTime().getSeconds();
    }

    public void run() {
        try {
            File audioFile = new File(file);
            MediaLocator ml = new MediaLocator("file:///" + audioFile.getAbsolutePath());

            //URL url = new URL("file:///" + file);
            //audioPlayer.setCachingControlVisible(true);
            //audioPlayer.setControlPanelVisible(true);
            Player p = Manager.createRealizedPlayer(ml);
            //p.realize();
            audioPlayer.setPlayer(p);
            //audioPlayer.realize();
            audioPlayer.setPlaybackLoop(loop);
            audioPlayer.start();
            playing = true;
        } catch (CannotRealizeException ex) {
            ex.printStackTrace();
        } catch (NoPlayerException e) {
            System.out.println("Formato de audio não suportado. Erro: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Arquivo de audio não encontrado. Erro: " + e.getMessage());
        }
    }
}
