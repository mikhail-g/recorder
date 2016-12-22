package recorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import recorder.config.Configurator;
import recorder.sound.AudioUtils;
import recorder.sound.RecordTask;

import javax.sound.sampled.AudioFileFormat;
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
    private Status status;
    private Thread recording;
    private TargetDataLine inputLine;
    private AudioFileFormat.Type fileType;
    private File outputFile;

    private RecorderImpl(TargetDataLine inputLine) {
        this.inputLine = inputLine;
        fileType = Configurator.getFileType();
        outputFile = Configurator.getNextOutputFile();
        this.status = Status.READY;
    }

    public static Recorder getInstance() {
        TargetDataLine inputLine = AudioUtils.createTargetDataLine(Configurator.getAudioFormat());
        return new RecorderImpl(inputLine);
    }

    @Override
    public void record() {
        if (!inputLine.isRunning()) {
            RecordTask recordTask = RecordTask.getInstance(inputLine, fileType, outputFile);
            recording = new Thread(recordTask, RECORDING_THREAD);
            recording.start();
            status = Status.RECORDING;
        }
    }

    @Override
    public void pause() {
        inputLine.stop();
        status = Status.PAUSE;
    }

    @Override
    public void stop() {
        inputLine.stop();
//        inputLine.drain();
        outputFile = Configurator.getNextOutputFile();
        log.debug("Finished");
        status = Status.READY;
    }

    @Override
    public void quit() {
        status = Status.CLOSING;
        log.info(BYE_MESSAGE);
        inputLine.close();
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
        PAUSE("Record is on pause"),
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
