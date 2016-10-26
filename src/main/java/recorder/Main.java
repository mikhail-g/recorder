package recorder;

import java.util.Scanner;

/**
 * Main class of recorder app
 * <p>
 * Created by Mykhailo on 22.09.16.
 */
public class Main {

    public static final String RECORD = "record";
    public static final String STOP = "stop";
    public static final String QUITE = "quite";
    public static final String RECORD_SHORT = "r";
    public static final String STOP_SHORT = "s";
    public static final String QUITE_SHORT = "q";
    public static final String RECORDING_THREAD = "recording";
    final static JavaSoundRecorder recorder = new JavaSoundRecorder();
    private static Thread recording;
    private static Command record = () -> {
        recording = new Thread(recorder, RECORDING_THREAD);
        recording.start();
    };
    private static Command stop = recorder::finish;
    private static Command quite = () -> {
        System.out.println("Bye-bye!");
        recording = null;
        System.exit(0);
    };

    public static void main(String[] args) {
        System.out.println("Welcome to Java Recorder! The first step to creation of best bass guitar tuner ever!");
        System.out.println("Waiting for your commands:\nr - record\ns - stop\nq - quite");
        Scanner sc = new Scanner(System.in);
        while (true) {
            performAction(sc.nextLine());
        }
    }

    private static void performAction(final String command) {
        switch (command) {
            case RECORD:
            case RECORD_SHORT:
                record.execute();
                break;
            case STOP:
            case STOP_SHORT:
                stop.execute();
                break;
            case QUITE:
            case QUITE_SHORT:
                quite.execute();
            default:
                System.out.println("Unknown command");
                break;
        }
    }

    @Deprecated
    public static void originalMain(String[] args) {
        final JavaSoundRecorder recorder = new JavaSoundRecorder();
        long recordTimeMs = 10000;

        // creates a new thread that waits for a specified
        // of time before stopping
        Thread stopper = new Thread(() -> {
            try {
                Thread.sleep(recordTimeMs);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                recorder.finish();
            }
        });

        stopper.start();
        recorder.run();
    }

    public interface Command {
        void execute();
    }
}
