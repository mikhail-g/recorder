package recorder;

/**
 * Sound Recorder API
 * <p>
 * Created by Mykhailo on 002 02.11.16.
 */
public interface Recorder {

    void record();

    void stop();

    void quit();

    Status getStatus();

    interface Status {

        String getMessage();
    }
}
