import recorder.config.Configurator;

import java.io.File;
import java.net.URISyntaxException;

/**
 * test for file names
 * <p>
 * Created by Mykhailo on 027 27.10.16.
 */
public class FileNameTest {

    public static void main(String[] args) throws URISyntaxException {
        File file = Configurator.getOutputFile();
        System.out.println("file = " + file);
        File file2 = Configurator.getOutputFile();
        System.out.println("file2 = " + file2);
        File file3 = Configurator.getOutputFile();
        System.out.println("file3 = " + file3);
    }
}
