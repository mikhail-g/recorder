package recorder;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * A sample program is to demonstrate how to record sound in Java
 * author: www.codejava.net
 */
public class JavaSoundRecorder implements Runnable {

    private TargetDataLine inputLine;

    public void run() {
        try {
            AudioFormat format = Configurator.getAudioFormat();
            inputLine = createTargetDataLine(format);
            startCapturing(inputLine, format);
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
        System.out.println("Finished");
    }

    private TargetDataLine createTargetDataLine(AudioFormat audioFormat) throws LineUnavailableException {
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
        checkLineSupported(info);
        TargetDataLine targetDataLine;
        targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
        return targetDataLine;
    }

    private void checkLineSupported(DataLine.Info info) {
        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line not supported");
            System.exit(0);
        }
    }

    private void startCapturing(TargetDataLine targetDataLine, AudioFormat audioFormat) throws LineUnavailableException {
        targetDataLine.open(audioFormat);
        System.out.println("Start capturing...");
        targetDataLine.start();
    }

    private void startRecording(TargetDataLine targetDataLine, AudioFileFormat.Type fileType, File outputFile)
            throws IOException, URISyntaxException {
        AudioInputStream ais = new AudioInputStream(targetDataLine);
        System.out.println("Start recording...");
        AudioSystem.write(ais, fileType, outputFile);
    }
}