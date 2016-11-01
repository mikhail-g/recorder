package recorder;

import recorder.sound.JavaSoundRecorder;

/**
 * API for sound recorder
 * <p>
 * Created by Mykhailo on 027 27.10.16.
 */
public class CommandApi {

    public static final String RECORDING_THREAD = "recording";

    static JavaSoundRecorder recorder;
    private static Thread recording;
    private static Status status = Status.READY;

    public static Command getCommandInit() {
        return () -> recorder = new JavaSoundRecorder();
    }

    public static Command getCommandRecord() {
        return () -> {
            if (!recorder.isLineAvailable()) {
                recording = new Thread(recorder, RECORDING_THREAD);
                recording.start();
                status = Status.RECORDING;
            }
        };
    }

    public static Command getCommandStop() {
        return () -> {
            recorder.finish();
            status = Status.READY;
        };
    }

    public static Command getCommandQuite() {
        return () -> {
            System.out.println("Bye-bye!");
            recording = null;
            System.exit(0);
        };
    }

    private enum Status {
        READY("Ready to record"),
        RECORDING("Recording is in progress");

        private String message;

        Status(final String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
