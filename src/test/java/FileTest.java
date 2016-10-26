import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class FileTest {

    public static void main(String[] args) throws URISyntaxException, IOException {
        String content = "Some text to write to file";
        String path = "./angry_file.txt";

        String resourcePath = FileTest.class.getClassLoader().getResource("").toString() + "text.txt";
        writeToFile(content, path);
//        printFilePaths();
    }

    private static void writeToFile(String content, String path) throws URISyntaxException, IOException {
        System.out.println("path = " + path);
        File dir = new File("dir");
        System.out.println("is dir created: " + dir.mkdirs());
        File file = new File(dir, "text.txt");
        System.out.println("file = " + file.getName());

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());

            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            System.out.println("Done!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printFilePaths() {
        String pathFromSys = System.getProperty("user.dir");

        File currentDirFile = new File("huy");
        String absPath = currentDirFile.getAbsolutePath();
        String canonicalPath = null;
        String currentDir = null;
        String resourcePath = FileTest.class.getClassLoader().getResource("").toString();

        try {
            canonicalPath = currentDirFile.getCanonicalPath();
            currentDir = absPath.substring(0, absPath.length() - canonicalPath.length());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("pathFromSys = " + pathFromSys);
        System.out.println("absPath = " + absPath);
        System.out.println("canonicalPath = " + canonicalPath);
        System.out.println("currentDir = " + currentDir);
        System.out.println("resourcePath = " + resourcePath);
    }
}
