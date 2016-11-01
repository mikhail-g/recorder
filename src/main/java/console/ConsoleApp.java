package console;

import recorder.Recorder;
import recorder.RecorderImpl;

import java.util.Scanner;

/**
 * Main class for recorder console application
 * <p>
 * Created by Mykhailo on 22.09.16.
 */
public class ConsoleApp {

    private static final String RECORD = "record";
    private static final String STOP = "stop";
    private static final String QUITE = "quite";
    private static final String RECORD_SHORT = "r";
    private static final String STOP_SHORT = "s";
    private static final String QUITE_SHORT = "q";
    private static final String WELCOME_MESSAGE = "Welcome to Java Recorder! The first step to creation of best bass guitar tuner ever!";
    private static final String HELP_MESSAGE = "Waiting for your commands:\nr - record\ns - stop\nq - quite\n";
    private static final String UNKNOWN_COMMAND = "Unknown command";
    private static Recorder recorder;

    public static void main(String[] args) {
        recorder = RecorderImpl.getInstance();
        recorder.init();
        System.out.println(WELCOME_MESSAGE);
        System.out.println(HELP_MESSAGE);
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println(recorder.getStatus().getMessage());
            performAction(sc.nextLine());
        }
    }

    private static void performAction(final String command) {
        switch (command) {
            case RECORD:
            case RECORD_SHORT:
                recorder.record();
                break;
            case STOP:
            case STOP_SHORT:
                recorder.stop();
                break;
            case QUITE:
            case QUITE_SHORT:
                recorder.quit();
            default:
                System.out.println(UNKNOWN_COMMAND);
                break;
        }
    }
}
