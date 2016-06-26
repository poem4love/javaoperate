package operate.fNr;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by andynan on 2016/6/2.c
 */
public class Find {
    static HashMap<String, String> mMap = null;
    public static void main(String[] args) {
//        FileInputStream ins = null;
//        InputStreamReader reader = null;
//        BufferedReader br = null;
//        File file = new File("E:\\06-01\\before\\");
        encapsulate();
    }

    public static HashMap<String, String> encapsulate() {
        InputStream ins = null;
        PX px = null;
        try {
            ins = new URL("file:\\E:\\06-01\\AFTER\\lint-results-debug.xml").openConnection().getInputStream();
            px = new PX();
            mMap = px.parseXml(ins);
            return mMap;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
