package operate.backupnremove;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class AimedAtString {
    private static final String STRING = "E:\\06-21\\unusedstrings.txt";
    public static void main(String [] args) {
        sortAllUnusedLines(STRING);
    }

    /**
     * 读取无用资源的所在行数,从小到大排序.
     */
    public static void sortAllUnusedLines(String path) {
        File f = new File(path);
        ArrayList<Integer> allLines = new ArrayList();
        FileInputStream ins = null;
        InputStreamReader insReader = null;
        BufferedReader bufferedReader = null;

        try {
            ins = new FileInputStream(f);
            insReader = new InputStreamReader(ins);
            bufferedReader = new BufferedReader(insReader);

            String s = "";
            while ((s = bufferedReader.readLine()) != null) {
                if (s.length() > 0) {
                    int index = Integer.parseInt(s.substring(s.indexOf("line")+5,s.length() - 1)) - 1;
                    allLines.add(index);
                }
            }
            sort(allLines);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
                insReader.close();
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void sort(List<Integer> arrays) {
        Integer [] lines = arrays.toArray(new Integer [arrays.size()]);
        for(int i = 0;i<lines.length - 1;i++) {
            for (int k = i + 1;k<lines.length;k++) {
                if (lines[k] < lines[i]) {
                    int temp = lines[i];
                    lines[i] = lines[k];
                    lines[k] = temp;
                }
            }
        }
        arrays = Arrays.asList(lines);
        Path pa = Paths.get("E:\\06-21\\strings.xml");
        Charset charset = StandardCharsets.UTF_8;
        List<String> mList;
        try {
            mList = Files.readAllLines((pa), charset);
            int size = mList.size();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < size - 1; i++) {
                if (arrays.contains(i)) {
                    sb.append("");
                    sb.append("\n");
                } else {
                    sb.append(mList.get(i));
                    sb.append("\n");
                }
            }
            sb.append(mList.get(size - 1));
            String content = sb.toString();
            Files.write(pa, content.getBytes(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
