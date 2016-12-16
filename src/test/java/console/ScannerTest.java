package console;

import recorder.sound.SoundRecorder;

import java.util.Scanner;

/**
 * Test for accepting commands from Scanner
 * <p>
 * Created by Mykhailo on 023 23.09.16.
 */
public class ScannerTest {

    private static final String RECORD = "record";
    private static final String STOP = "stop";
    private static final String QUITE = "quite";
    private static final String RECORD_SHORT = "r";
    private static final String STOP_SHORT = "s";
    private static final String QUITE_SHORT = "q";
    private static final String RECORDING_THREAD = "recording";
    private final static SoundRecorder recorder = new SoundRecorder();
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
