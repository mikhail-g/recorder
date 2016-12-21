package console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import recorder.Recorder;
import recorder.RecorderImpl;

import java.util.Scanner;

/**
 * Main class for recorder console application
 * <p>
 * Created by Mykhailo on 22.09.16.
 */
public class RecorderCmd {

    private static final Logger log = LoggerFactory.getLogger(RecorderCmd.class);
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
        log.info(WELCOME_MESSAGE);
        log.info(HELP_MESSAGE);
        Scanner sc = new Scanner(System.in);
        while (true) {
            log.info(recorder.getStatus().getMessage());
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
                log.info(UNKNOWN_COMMAND);
                break;
        }
    }
}
