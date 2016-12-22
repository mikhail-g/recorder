package recorder.sound;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.TargetDataLine;
import java.io.File;

/**
 * Record task for separate thread
 */
public class RecordTask implements Runnable {

    private TargetDataLine inputLine;
    private AudioFileFormat.Type fileType;
    private File outputFile;

    private RecordTask() {
    }

    public static RecordTask getInstance(TargetDataLine inputLine, AudioFileFormat.Type fileType, File outputFile) {
        RecordTask task = new RecordTask();
        task.inputLine = inputLine;
        task.fileType = fileType;
        task.outputFile = outputFile;
        return task;
    }

    @Override
    public void run() {
        AudioUtils.startRecording(inputLine, fileType, outputFile);
    }
}