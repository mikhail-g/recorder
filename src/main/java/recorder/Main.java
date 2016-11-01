package recorder;

import java.util.Scanner;

import static recorder.CommandApi.*;

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



    public static void main(String[] args) {
        getCommandInit().execute();
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
                getCommandRecord().execute();
                break;
            case STOP:
            case STOP_SHORT:
                getCommandStop().execute();
                break;
            case QUITE:
            case QUITE_SHORT:
                getCommandQuite().execute();
            default:
                System.out.println("Unknown command");
                break;
        }
    }
}
