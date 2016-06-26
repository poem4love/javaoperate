package operate.copy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by andynan on 2016/5/31.
 */
public class M {
    public static void main(String[] args) {
        read(new File("E:\\05-31\\svnd.txt"));
    }

    public static void read(File delFile) {
        FileInputStream ins = null;
        InputStreamReader reader= null;
        BufferedReader bufferReader= null;
        try {
            String unused_res_name = "";
            ins = new FileInputStream(delFile);
            reader = new InputStreamReader(ins);
            bufferReader = new BufferedReader(reader);
            long start = System.nanoTime();
            long end;
            while ((unused_res_name=bufferReader.readLine()) != null) {
                move(unused_res_name);
            }
            end = System.nanoTime();
            System.out.println(end - start);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                System.out.println("finally");
                bufferReader.close();
                reader.close();
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void move(String fileName) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        Date date = new Date();
        String time = sdf.format(date);
        File file = new File("E:\\newfolder\\renandroidddle7\\src\\main\\res\\drawable-xhdpi");
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.getName().equals(fileName)) {
                    String prefix = f.getParent().substring(f.getParent().indexOf("res")+"res".length() + 1);
                    File destPath = new File("E:\\svn_" + time + File.separator + prefix);
                    if (!destPath.exists()) {
                        destPath.mkdirs();
                    }
                    File destFile = new File(destPath + File.separator + f.getName());
                    if (destFile.exists()) {
                        destFile.delete();
                    }
                    FilesJava7.copy(f,destFile);
                }
            }
        } else {
            if (file.getName().equals(fileName)) {
                String prefix = file.getParent().substring(file.getParent().indexOf("res")+"res".length() + 1);

                File destPath = new File("E:\\svn_" + time + File.separator + prefix);
                if (!destPath.exists()) {
                    destPath.mkdirs();
                }
                File destFile = new File(destPath + File.separator + file.getName());
                if (destFile.exists()) {

                }
                FilesJava7.copy(file,destFile);
            }
        }
    }

}
