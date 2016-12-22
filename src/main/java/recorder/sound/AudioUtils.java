package recorder.sound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Class represents communications with with audio system
 * <p>
 * Created by Mykhailo on 021 21.12.16.
 */
public class AudioUtils {

    private static final Logger log = LoggerFactory.getLogger(AudioUtils.class);

    static void startRecording(TargetDataLine line, AudioFileFormat.Type fileType, File outputFile) {
        AudioInputStream ais = new AudioInputStream(line);
        try {
            log.debug("Start capturing...");
            line.start();
            log.debug("Start recording...");
            AudioSystem.write(ais, fileType, outputFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static TargetDataLine createTargetDataLine(AudioFormat audioFormat) {
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
        checkLineSupported(dataLineInfo);
        TargetDataLine dataLine = null;
        try {
            dataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            dataLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        return dataLine;
    }

    private static void checkLineSupported(DataLine.Info info) {
        if (!AudioSystem.isLineSupported(info)) {
            log.error("Line not supported");
            System.exit(0);
        }
    }
}
