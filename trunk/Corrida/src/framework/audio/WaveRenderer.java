package framework.audio;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class WaveRenderer extends BasicRenderer implements LineListener {

    /**
     * O nome do arquivo que esta nessa instancia
     */
    private String filename;
    /**
     * O formato do audio
     */
    private AudioFormat format;
    /**
     * Responavel pela entrada de dados a partir de um arquivo de audio
     */
    private AudioInputStream audioInputStream;
    /**
     * Armazena a exception
     */
    private Exception exception = null;
    private boolean loop = false;
    /**
     * Representa dados do audio em uma linha de saida 
     * @see Clip
     */
    private Clip clip;
    /**
     * Status da renderizacao
     */
    private int status = STOPPED;

    public WaveRenderer(String wavfile, boolean loop) {
        this.filename = wavfile;
        this.loop = loop;
        init();
    }

    private void init() {
        File soundFile = new File(filename);
        if (!soundFile.exists()) {
            System.err.println("Wave file not found: " + filename);
            this.exception = new Exception("Wave file not found: " + filename);
            this.status = ERROR;
            return;
        }

        try {
            /* Pegando o AudioInputStream pela classe AudioSystem, passando um
             * arquivo de audio .WAV
             */
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            /* O formato de audio e pego e' cria um DataLine, que por sua vez
             * e' usada para saida de audio
             */
            format = audioInputStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat(),
                    ((int) audioInputStream.getFrameLength() * format.getFrameSize()));

            this.clip = (Clip) AudioSystem.getLine(info);
            this.clip.addLineListener(this);
            this.clip.open(audioInputStream);
        } catch (LineUnavailableException ex) {
            this.exception = ex;
            this.status = ERROR;
            return;
        } catch (UnsupportedAudioFileException ex) {
            this.exception = ex;
            this.status = ERROR;
            return;
        } catch (IOException ex) {
            this.exception = ex;
            this.status = ERROR;
            return;
        }
    }

    public void run() {
        if (this.status == PLAYING || this.status == ERROR) {
            return;
        }
        if (!clip.isOpen()) {
            try {
                clip.open(audioInputStream);
                clip.start();
            } catch (LineUnavailableException ex) {
                this.status = ERROR;
                this.exception = ex;
                return;
            } catch (IOException ex) {
                this.status = ERROR;
                this.exception = ex;
                return;
            }
        }

        this.status = PLAYING;
        clip.setMicrosecondPosition(0);
        clip.start();
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.drain();
            clip.stop();
            clip.close();
        }
    }

    protected void setVolume(float volume) {
        if (this.clip == null) {
            return;
        }

        Control.Type vol1 = FloatControl.Type.VOLUME, vol2 = FloatControl.Type.MASTER_GAIN;

        if (this.clip.isControlSupported(vol1)) {
            FloatControl volumeControl = (FloatControl) this.clip.getControl(vol1);
            volumeControl.setValue(volume);

        } else if (this.clip.isControlSupported(vol2)) {
            FloatControl gainControl = (FloatControl) this.clip.getControl(vol2);
            float dB = (float) (Math.log(((volume == 0.0) ? 0.0001 : volume))
                    / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    public boolean isVolumeSupported() {
        if (clip != null) {
            return clip.isControlSupported(FloatControl.Type.VOLUME);
        }
        return false;
    }

    /**
     * Retorna o estado do arquivo do audio MIDI
     * Valores possiveis: PLAYING, STOPPED, END_OF_SOUND, ERROR
     * @return int Estado do arquivo de audio MIDI
     */
    public int getStatus() {
        return this.status;
    }

    /**
     * Notified when the sound is stopped externally.
     */
    @Override
    public void update(LineEvent e) {
        if (e.getType() == LineEvent.Type.STOP) {
            if (loop) {
                this.status = END_OF_SOUND;
                run();
            } else {
                this.clip.drain();
                this.clip.close();
                this.status = END_OF_SOUND;
            }
        }
    }

    public Exception getException() {
        return this.exception;
    }

    @Override
    protected BasicRenderer clone() {
        return new WaveRenderer(filename, loop);
    }

    @Override
    void setLoop(boolean loop) {
        this.loop = loop;
    }
}
