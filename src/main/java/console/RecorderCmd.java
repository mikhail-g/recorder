package console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import recorder.Recorder;
import recorder.RecorderImpl;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Main class for recorder console application
 * <p>
 * Created by Mykhailo on 22.09.16.
 */
public class RecorderCmd {

    private static final Logger log = LoggerFactory.getLogger(RecorderCmd.class);
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
        switch (Command.fromString(command)) {
            case RECORD:
                recorder.record();
                break;
            case STOP:
                recorder.stop();
                break;
            case PAUSE:
                recorder.pause();
                break;
            case QUITE:
                recorder.quit();
            default:
                log.info(UNKNOWN_COMMAND);
                break;
        }
    }

    enum Command {
        RECORD("record", "r"),
        STOP("stop", "s"),
        PAUSE("pause", "p"),
        QUITE("quite", "q"),
        UNKNOWN("", "");

        private String shortcut;
        private String fullText;

        Command(String fullText, String shortcut) {
            this.fullText = fullText;
            this.shortcut = shortcut;
        }

        static Command fromString(String value) {
            return Arrays.stream(Command.values())
                    .filter(cmd -> cmd.shortcut.equalsIgnoreCase(value) || cmd.fullText.equalsIgnoreCase(value)).findFirst()
                    .orElse(UNKNOWN);
        }
    }
}
