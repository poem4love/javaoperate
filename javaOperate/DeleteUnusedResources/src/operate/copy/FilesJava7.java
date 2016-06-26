package operate.copy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by andynan on 2016/5/31.
 */
public class FilesJava7 {
    public static void copy(File sour, File dest) {
        try {
            Files.copy(sour.toPath(), dest.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
