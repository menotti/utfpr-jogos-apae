package framework.audio;

import java.io.File;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

/**
 * Player de arquivos de audio midi.
 * 
 * @author Kuroda
 */
public class MidiRenderer extends BasicRenderer implements Cloneable {

    /**
     * Codigo do evento que indica que o final do track (End Of Track)
     */
    private static final int MIDI_EOT_MESSAGE = 47;
    /**
     * Controle de ganho de volume
     */
    private static final int GAIN_CONTROLLER = 7;
    /**
     * Guarda o estado atual do player
     * Quando inicializada, seu valor e' STOPPED (2)
     */
    private int status = STOPPED;
    /**
     * Indica se o sistema de audio suporta volume
     */
    private boolean volumeSupported;
    /**
     * E' o software ou hardware responsavel pela saida do som
     * @see Sequencer
     */
    private Sequencer sequencer;
    /**
     * Audio midi
     */
    private Sequence sequence;
    /**
     * Responsavel por gerar o som
     * @see Synthesizer
     */
    private Synthesizer synthesizer;
    /**
     * O nome do arquivo que esta nessa instancia
     */
    private String filename;
    /**
     * Armazena a exception
     */
    private Exception exception = null;

    /**
     * No construtor sao instaciados o Sequencer e o Synthesizer 
     * @param file Arquivo MIDI
     */
    public MidiRenderer(String filename) {
        this.filename = filename;
        File soundFile = new File(filename);
        if (!soundFile.exists()) {
            System.err.println("Midi file not found: " + filename);
            this.exception = new Exception("Midi file not found: " + filename);
            this.status = ERROR;
            return;
        }
        try {
            /* Lemos no arquivo MIDI para um objeto Sequence. Este objeto
             * é setado depois no Sequencer.
             */
            sequence = MidiSystem.getSequence(soundFile);

            /* Now, we need a Sequencer to play the sequence.  Here, we
             * simply request the default sequencer without an implicitly
             * connected synthesizer
             */
            sequencer = MidiSystem.getSequencer(false);

            /* Verifica se ha suporte a controle de volume. Se o sequencer
             * for um objeto instanciado de Synthesizer, ha suporte.
             */
            volumeSupported = (sequencer instanceof Synthesizer);

            /* The Sequencer is still a dead object.  We have to open() it
             * to become live.  This is necessary to allocate some
             * ressources in the native part.
             */
            sequencer.open();

            /* Next step is to tell the Sequencer which Sequence it has to
             * play. In this case, we set it as the Sequence object
             * created above.
             */
            sequencer.setSequence(sequence);

            /* We try to get the default synthesizer, open() it and chain
             * it to the sequencer with a Transmitter-Receiver pair.
             */
            synthesizer = MidiSystem.getSynthesizer();

            synthesizer.open();
            Receiver synthReceiver = synthesizer.getReceiver();
            Transmitter seqTransmitter = sequencer.getTransmitter();
            seqTransmitter.setReceiver(synthReceiver);

            /* To free system resources, it is recommended to close the
             * synthesizer and sequencer properly.
             *
             * To accomplish this, we register a Listener to the
             * Sequencer. It is called when there are "meta" events. Meta
             * event 47 is end of track.
             *
             * Thanks to Espen Riskedal for finding this trick.
             */
            sequencer.addMetaEventListener(new MetaEventListener() {

                public void meta(MetaMessage event) {
                    // Fecha quando o arquivo termina
                    if (event.getType() == MidiRenderer.MIDI_EOT_MESSAGE) {
                        sequencer.stop();
                        sequencer.close();
                        status = END_OF_SOUND;
                    }
                }
            });
        } catch (MidiUnavailableException ex) {
            this.exception = ex;
            this.status = ERROR;
            return;
        } catch (InvalidMidiDataException ex) {
            this.exception = ex;
            this.status = ERROR;
            return;
        } catch (IOException ex) {
            this.exception = ex;
            this.status = ERROR;
            return;
        }
    }

    /**
     * Para de tocar o audio, se este estiver tocando.
     */
    public void stop() {
        // Para de tocar se estiver tocando
        if (this.status == PLAYING) {
            this.status = STOPPED;
            sequencer.stop();
        }
    }

    /**
     * Toca o arquivo de audio MIDI, se estiver parado ou terminado
     */
    public void run() {
        // Se estiver parado, toca
        if (this.status == PLAYING || this.status == ERROR) {
            return;
        }
        this.status = PLAYING;
        if (!sequencer.isOpen()) {
            try {
                sequencer.open();
                sequencer.setSequence(sequence);
            } catch (Exception ex) {
                this.exception = ex;
                this.status = ERROR;
                return;
            }
        }
        // Rebobinando a fita
        sequencer.setMicrosecondPosition(0);
        sequencer.start();
    }

    public void replay() {
        sequencer.setMicrosecondPosition(0);
        sequencer.start();
    }

    /**
     * Define a quantidade de vezes que o audio ira tocar repetitivamente.
     * Utilize -1 para tocar repetitivamente.
     * @param int loop quantidade de vezes que o som irá tocar
     */
    public void setLoop(boolean loop) {
        /* Here, we set the loop points to loop over the whole
         * sequence. Setting the loop end point to -1 means using the
         * last tick of the sequence as end point of the loop.
         *
         * Furthermore, we set the number of loops to loop infinitely.
         */
        sequencer.setLoopStartPoint(0);
        sequencer.setLoopEndPoint(-1);
        if (loop) {
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
        } else {
            sequencer.setLoopCount(0); // Toca uma vez
        }
    }

    /**
     * Define o volume que o audio ira tocar.
     * Valores aceitos são de 0.0 a 1.0, onde 0.0 é mudo e 1.0 é  volume máximo.
     * @param float level
     */
    public void setVolume(float volume) {
        // Se o sequencer esta instaciado ou nao ha suporte a volume, retorna
        if (this.sequencer == null || !volumeSupported) {
            //return;
        }

        // Aumenta o volume baseado no valor float passado
        MidiChannel[] channels = ((Synthesizer) this.synthesizer).getChannels();
        for (int i = 0; i < channels.length; i++) {
            channels[i].controlChange(MidiRenderer.GAIN_CONTROLLER,
                    (int) (volume * 127));
        }
    }

    /**
     * Retorna true se houver suporte a controle de volume. Caso contrario,
     * retorna false.
     * @return boolean Se ha suporte a controle de volume, retorna true.
     */
    public boolean isVolumeSupported() {
        return this.volumeSupported;
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
     * Faz um clone dessa instancia
     * @return Object Midi
     */
    @Override
    public BasicRenderer clone() {//shallow copy
        return new MidiRenderer(filename);
    }

    public Exception getException() {
        return this.exception;
    }
}
