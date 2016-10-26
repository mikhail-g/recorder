import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Test For Configurations
 * <p>
 * Created by Mykhailo on 021 21.10.16.
 */
public class ConfiguratorTest {

    public static void main(String[] args) {
        Config conf = ConfigFactory.load();

        Config audioFormat = conf.getConfig("audio.format");

        int sampleRate = audioFormat.getInt("sampleRate");
        int sampleSizeInBits = audioFormat.getInt("sampleSizeInBits");
        int channels = audioFormat.getInt("channels");
        boolean signed = audioFormat.getBoolean("signed");
        boolean bigEndian = audioFormat.getBoolean("bigEndian");

        System.out.println("bigEndian = " + bigEndian);
        System.out.println("signed = " + signed);
        System.out.println("channels = " + channels);
        System.out.println("sampleSizeInBits = " + sampleSizeInBits);
        System.out.println("sampleRate = " + sampleRate);
    }
}
