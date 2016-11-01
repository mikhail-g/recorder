package recorder;

import recorder.sound.JavaSoundRecorder;

/**
 * Sound Recorder
 * <p>
 * Created by Mykhailo on 002 02.11.16.
 */
public class RecorderImpl implements Recorder {
    public static final String RECORDING_THREAD = "recording";

    static JavaSoundRecorder recorder;
    private static Thread recording;
    private static Status status = Status.READY;

    public static Recorder getInstance() {
        return new RecorderImpl();
    }

    @Override
    public void init() {
        recorder = new JavaSoundRecorder();
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
        recorder.finish();
        status = Status.READY;
    }

    @Override
    public void quit() {
        System.out.println("Bye-bye!");
        recording = null;
        System.exit(0);
    }

    @Override
    public Recorder.Status getStatus() {
        return status;
    }

    private enum Status implements Recorder.Status {
        READY("Ready to record"),
        RECORDING("Recording is in progress");

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
