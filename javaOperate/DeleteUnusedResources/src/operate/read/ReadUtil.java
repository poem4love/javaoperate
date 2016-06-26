package operate.read;

import java.io.File;

/**
 * Created by andynan on 2016/4/11.
 */
public class ReadUtil {
    public static void readFilesName(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                System.out.println(f.getName());
            }
        }
    }
}
