package recorder.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static java.util.Optional.ofNullable;

/**
 * Configurations and data preparations for Java Sound Recorder
 * <p>
 * Created by Mykhailo on 017 17.10.16.
 */
public class Configurator {

    public static final String OUTPUT_AUDIO_FORMAT_PROP_PATH = "output.audio.format";
    public static final String SAMPLE_RATE = "sampleRate";
    public static final String SAMPLE_SIZE_IN_BITS = "sampleSizeInBits";
    public static final String CHANNELS = "channels";
    public static final String SIGNED = "signed";
    public static final String BIG_ENDIAN = "bigEndian";
    public static final String OUTPUT_FILE_PROP_PATH = "output.file";
    public static final String EXTENSION_PROP = "extension";
    public static final String NAME_PROP = "name";
    public static final String PATH_PROP = "path";
    public static final String EXTENSION_SEPARATOR = ".";
    public static final String EXTENSION_SPLITTER = "\\.";
    public static final String FILE_NAME_SEPARATOR = "_";
    public static final String RECORD_NUMBER_FORMAT = "%03d";
    public static final String FILE_NAME_NUMBER_BASE = "name_19891231_000";
    private static final Logger log = LoggerFactory.getLogger(Configurator.class);
    private static Config conf = ConfigFactory.load();
    private static Config outputFileConfig = conf.getConfig(OUTPUT_FILE_PROP_PATH);

    public static File getOutputFile() throws URISyntaxException {
        return new File(getOutputFolder(), getOutputFileFullName());
    }


    private static File getOutputFolder() {
        File dir = new File(outputFileConfig.getString(PATH_PROP));
        if (!dir.exists()) {
            boolean isOutputDirCreated = dir.mkdirs();
            log.debug("isOutputDirCreated = {}", isOutputDirCreated);
        }
        return dir;
    }

    private static String getOutputFileFullName() {
        return String.join(EXTENSION_SEPARATOR, getOutputFileName(), getOutputFileExtension());
    }

    public static String getOutputFileExtension() {
        return outputFileConfig.getString(EXTENSION_PROP);
    }

    private static String getOutputFileName() {
        String name = outputFileConfig.getString(NAME_PROP);
        String timestamp = getDateString();
        String number = String.format(RECORD_NUMBER_FORMAT, getRecordNumber());
        String outputFileName = String.join(FILE_NAME_SEPARATOR, name, timestamp, number);
        log.debug("outputFileName = {}", outputFileName);
        return outputFileName;
    }

    private static int getRecordNumber() {
        File[] files = ofNullable(getOutputFolder().listFiles()).orElse(getEmptyFileArray());
        Integer lastIndex = Arrays.stream(files)
                .filter(file -> file.getName().contains(getDateString()))
                .sorted((file1, file2) -> file2.getName().compareTo(file1.getName()))
                .findFirst()
                .map(file3 -> Integer.parseInt(
                        file3.getName().substring(file3.getName()
                                .lastIndexOf(FILE_NAME_SEPARATOR) + 1)
                                .split(EXTENSION_SPLITTER)[0]))
                .orElseGet(() -> 0);
        return lastIndex + 1;
    }

    private static String getDateString() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    private static File[] getEmptyFileArray() {
        File[] arr = new File[1];
        arr[0] = new File(FILE_NAME_NUMBER_BASE);
        return arr;
    }

    public static AudioFileFormat.Type getFileType() {
        return getFileType(getOutputFileExtension());
    }

    public static AudioFileFormat.Type getFileType(String extension) {
        return Arrays.stream(AudioSystem.getAudioFileTypes()).filter(type -> type.getExtension()
                .equalsIgnoreCase(extension)).findFirst().orElseGet(() -> AudioFileFormat.Type.WAVE);
    }

    public static AudioFormat getAudioFormat() {
        Config conf = ConfigFactory.load();
        Config audioFormat = conf.getConfig(OUTPUT_AUDIO_FORMAT_PROP_PATH);
        return new AudioFormat(
                audioFormat.getInt(SAMPLE_RATE),
                audioFormat.getInt(SAMPLE_SIZE_IN_BITS),
                audioFormat.getInt(CHANNELS),
                audioFormat.getBoolean(SIGNED),
                audioFormat.getBoolean(BIG_ENDIAN)
        );
    }
}