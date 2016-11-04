package recorder.sound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import recorder.config.Configurator;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * A sample program is to demonstrate how to record sound in Java
 * author: www.codejava.net
 */
public class JavaSoundRecorder implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(JavaSoundRecorder.class);
    private TargetDataLine inputLine;

    public JavaSoundRecorder() {
        try {
            inputLine = createTargetDataLine(Configurator.getAudioFormat());
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static TargetDataLine createTargetDataLine(AudioFormat audioFormat) throws LineUnavailableException {
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
        checkLineSupported(info);
        return (TargetDataLine) AudioSystem.getLine(info);
    }

    private static void checkLineSupported(DataLine.Info info) {
        if (!AudioSystem.isLineSupported(info)) {
            log.error("Line not supported");
            System.exit(0);
        }
    }

    public void run() {
        try {
            startCapturing(inputLine, Configurator.getAudioFormat());
            startRecording(inputLine, Configurator.getFileType(), Configurator.getOutputFile());
        } catch (LineUnavailableException | IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Closes the target data line to finish capturing and recording
     */
    public void finish() {
        inputLine.stop();
        inputLine.close();
        log.debug("Finished");
    }

    public boolean isLineAvailable() {
        return inputLine.isRunning();
    }

    private void startCapturing(TargetDataLine targetDataLine, AudioFormat audioFormat) throws LineUnavailableException {
        targetDataLine.open(audioFormat);
        log.debug("Start capturing...");
        targetDataLine.start();
    }

    private void startRecording(TargetDataLine targetDataLine, AudioFileFormat.Type fileType, File outputFile)
            throws IOException, URISyntaxException {
        AudioInputStream ais = new AudioInputStream(targetDataLine);
        log.debug("Start recording...");
        AudioSystem.write(ais, fileType, outputFile);
    }
}