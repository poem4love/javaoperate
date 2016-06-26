package operate.copy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by andynan on 2016/5/31.
 */
public class FileStreams {
    public static void copy(File sour, File dest) {
        InputStream ins = null;
        OutputStream ous = null;
        try {
            ins = new FileInputStream(sour);
            ous = new FileOutputStream(dest);
            byte[] buff = new byte[1024];
            int bytesRead;
            while ((bytesRead = ins.read(buff)) > 0) {
                ous.write(buff, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                ins.close();
                ous.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
