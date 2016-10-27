import recorder.sound.JavaSoundRecorder;

import java.util.Scanner;

/**
 * Test for accepting commands from Scanner
 * <p>
 * Created by Mykhailo on 023 23.09.16.
 */
public class ScannerTest {

    public static final String RECORD = "record";
    public static final String STOP = "stop";
    public static final String QUITE = "quite";
    public static final String RECORD_SHORT = "r";
    public static final String STOP_SHORT = "s";
    public static final String QUITE_SHORT = "q";
    public static final String RECORDING_THREAD = "recording";
    final static JavaSoundRecorder recorder = new JavaSoundRecorder();
    private static Thread recording;
    private static Command record = () -> recording.start();
    private static Command stop = recorder::finish;
    private static Command quite = () -> {
        System.out.println("Bye-bye!");
        recording = null;
        System.exit(0);
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Java Recorder! The first step to creation of best bass guitar tuner ever!");
        System.out.println("Waiting for your commands:\nr - record\ns - stop\nq - quite");
        recording = new Thread(recorder, RECORDING_THREAD);
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

    public interface Command {
        void execute();
    }
}
