package recorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import recorder.config.Configurator;
import recorder.sound.AudioUtils;
import recorder.sound.RecordTask;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.TargetDataLine;
import java.io.File;

/**
 * Sound Recorder
 *
 * Created by Mykhailo on 002 02.11.16.
 */
public class RecorderImpl implements Recorder {

    private static final Logger log = LoggerFactory.getLogger(RecorderImpl.class);

    private static final String RECORDING_THREAD = "recording";
    private static final String BYE_MESSAGE = "Bye-bye!";
    private static final Thread NULL_THREAD = null;
    private static final int POSITIVE_EXIT_STATUS = 0;
    private static Status status;
    private static Thread recording;

    private TargetDataLine inputLine;
    private RecordTask recordTask;

    public static Recorder getInstance() {
        RecorderImpl recorder = new RecorderImpl();

        recorder.inputLine = AudioUtils.createTargetDataLine(Configurator.getAudioFormat());
        AudioFormat audioFormat = Configurator.getAudioFormat();
        AudioFileFormat.Type fileType = Configurator.getFileType();
        File outputFile = Configurator.getOutputFile();

        recorder.recordTask = RecordTask.getInstance(recorder.inputLine, audioFormat, fileType, outputFile);
        status = Status.READY;
        return recorder;
    }

    @Override
    public void record() {
        if (!inputLine.isRunning()) {
            recording = new Thread(recordTask, RECORDING_THREAD);
            recording.start();
            status = Status.RECORDING;
        }
    }

    @Override
    public void stop() {
        status = Status.SAVING;
        inputLine.stop();
        inputLine.close();
        log.debug("Finished");
        status = Status.READY;
    }

    @Override
    public void quit() {
        status = Status.CLOSING;
        log.info(BYE_MESSAGE);
        recording = NULL_THREAD;
        System.exit(POSITIVE_EXIT_STATUS);
    }

    @Override
    public Recorder.Status getStatus() {
        return status;
    }

    private enum Status implements Recorder.Status {
        READY("Ready to record"),
        RECORDING("Recording is in progress"),
        SAVING("Record is saving"),
        CLOSING("Recording is closing"),
        ERROR("An error is occurred");

        private String message;

        Status(final String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }
}
