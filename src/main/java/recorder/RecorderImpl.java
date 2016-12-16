package recorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import recorder.sound.SoundRecorder;

/**
 * Sound Recorder
 *
 * Created by Mykhailo on 002 02.11.16.
 */
public class RecorderImpl implements Recorder {

    private static final String RECORDING_THREAD = "recording";
    private static final Logger log = LoggerFactory.getLogger(RecorderImpl.class);
    private static final Thread NULL_THREAD = null;
    private static final int POSITIVE_EXIT_STATUS = 0;
    private static final String BYE_MESSAGE = "Bye-bye!";
    private static SoundRecorder recorder;
    private static Thread recording;
    private static Status status = Status.READY;

    public static Recorder getInstance() {
        return new RecorderImpl();
    }

    @Override
    public void init() {
        recorder = new SoundRecorder();
    }

    @Override
    public void record() {
        if (!recorder.isLineAvailable()) {
            recording = new Thread(recorder, RECORDING_THREAD);
            recording.start();
            status = Status.RECORDING;
        }
    }

    @Override
    public void stop() {
        status = Status.SAVING;
        recorder.finish();
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
