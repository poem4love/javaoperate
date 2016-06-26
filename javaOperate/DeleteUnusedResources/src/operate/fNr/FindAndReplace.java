package operate.fNr;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by andynan on 2016/6/2.
 */
public class FindAndReplace {
    static int i = 1;

    public static void main(String[] args) {
        HashMap<String, String> mMap = Find.encapsulate();
        Set<Map.Entry<String, String>> mSet = mMap.entrySet();
        for (Map.Entry<String, String> entry : mSet) {
            doencapsulate(entry.getKey(), entry.getValue());
            i++;
        }
    }

    public static void doencapsulate(String pathSrc, String line) {
        int lineNum = Integer.parseInt(line) - 1;
        File file = new File(pathSrc.substring(pathSrc.indexOf("E"), pathSrc.length()));
        if (!file.exists()) {
            System.out.println("file " + pathSrc + " doesn't exists");
            return;
        }
        Path path = Paths.get(pathSrc.substring(pathSrc.indexOf("E"), pathSrc.length()));
        Charset charset = StandardCharsets.UTF_8;
        List<String> mList;
        try {
            mList = Files.readAllLines((path), charset);

            int size = mList.size();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < size; i++) {
                String lineContent = mList.get(i);
                if (i == lineNum) {
                    if (lineContent.contains("/>")) {
                        sb.append("/>" + "\n");
                    } else if (lineContent.contains(">")) {
                        sb.append(">" + "\n");
                    } else {
                        sb.append("" + "\n");

                    }
                } else if (i < size) {
                    sb.append(lineContent + "\n");
                } else {
                    sb.append(lineContent);
                }
            }
            String content = sb.toString();
            Files.write(path, content.getBytes(charset));
            System.out.println(i + " - " + "替换 | " + pathSrc + " | 文件..." + " 第 | " + line + " | 行, 结束...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
