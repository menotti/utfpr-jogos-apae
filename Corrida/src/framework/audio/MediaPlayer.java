package framework.audio;

import java.io.File;

/**
 * Media Player
 * Suporta arquivos de audio MIDI e WAVE
 * @author Rodrigo Kuroda
 */
public class MediaPlayer extends BasicPlayer {

    public static final int SINGLE = -1;
    public static final int MULTIPLE = 1;

    private int type = SINGLE;

    /**
     * Cria um player Midi, se o arquivo tiver extensao .mid
     * Ou Wave, se a extensao for .wav
     * Caso contrario, nao faz nada.
     *
     * Recomendavel usar wave para efeitos como efeitos de som (ex. tiro); e
     * midi para tocar musicas 
     * @param filename Nome / Caminho do arquivo de audio
     * @param type Indica se vai ser tocado apenas uma instancia ou se podera
     * sobrepor um ao outro. Valores: SINGLE, MULTIPLE
     * SINGLE: Nao implementa Thread
     * MULTIPLE: Implementra Thread, criando uma nova a cada vez tocado
     */
    public MediaPlayer(String filename, int type) {
        File audiofile = new File(filename);
        if (audiofile.isFile()) {
            if (filename.endsWith(".mid")) {
                renderer = new MidiRenderer(filename);
            } else if (filename.endsWith(".wav")) {
                renderer = new WaveRenderer(filename,loop);
            } else {
                return;
            }
        }
        this.type = type;
    }

    /**
     * Cria um player Midi, se o arquivo tiver extensao .mid
     * Ou Wave, se a extensao for .wav
     * Caso contrario, nao faz nada.
     *
     * Recomendavel usar wave para efeitos como efeitos de som (ex. tiro); e
     * midi para tocar musicas
     * @param filename Nome / Caminho do arquivo de audio
     */
    public MediaPlayer(String filename, boolean loop) {
        File audiofile = new File(filename);
        if (audiofile.isFile()) {
            if (filename.endsWith(".mid")) {
                renderer = new MidiRenderer(filename);
            } else if (filename.endsWith(".wav")) {
                renderer = new WaveRenderer(filename,loop);
            } else {
                return;
            }
        }
        renderer.setLoop(loop);
        this.loop = loop;
    }

    /**
     * Toca o audio especificado
     */
    public void play() {
        if (this.type == MULTIPLE) {
            new Thread(renderer.clone()).start();
        } else {
            renderer.run();
        }
    }

    /**
     * Para de tocar o audio, se este estiver tocando
     */
    public void stop() {
        renderer.stop();
    }

    public Exception getException() {
        return renderer.getException();
    }

    /**
     * Retorna true se houver suporte a controle de volume. Caso contrario,
     * retorna false.
     * @return boolean Se ha suporte a controle de volume, retorna true.
     */
    public boolean isVolumeSupported() {
        if (renderer instanceof MidiRenderer) {
            return ((MidiRenderer) renderer).isVolumeSupported();
        }
        return false;
    }

    public void setVolume(float volume) {
        renderer.setVolume(volume);
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
        renderer.setLoop(loop);
    }

    public int getStatus() {
        return renderer.getStatus();
    }
}
