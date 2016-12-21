package recorder.sound;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Record task for separate thread
 */
public class RecordTask implements Runnable {

    private TargetDataLine inputLine;
    private AudioFormat audioFormat;
    private AudioFileFormat.Type fileType;
    private File outputFile;

    private RecordTask() {
    }

    public static RecordTask getInstance(TargetDataLine inputLine, AudioFormat audioFormat, AudioFileFormat.Type fileType, File outputFile) {
        RecordTask task = new RecordTask();
        task.inputLine = inputLine;
        task.audioFormat = audioFormat;
        task.fileType = fileType;
        task.outputFile = outputFile;
        return task;
    }

    @Override
    public void run() {
        try {
            AudioUtils.startCapturing(inputLine, audioFormat);
            AudioUtils.startRecording(inputLine, fileType, outputFile);
        } catch (LineUnavailableException | IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }
    }
}