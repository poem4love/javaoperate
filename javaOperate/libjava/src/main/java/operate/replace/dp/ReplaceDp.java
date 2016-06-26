package operate.replace.dp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ReplaceDp {
	private static final String root = "E:\\temporary\\layout";
	
	private static final String reRoot = "E:\\temporary\\replace";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileInputStream ins = null;
		InputStreamReader ins_reader = null;
		BufferedReader buf_reader = null;
		FileOutputStream ops = null;
		OutputStreamWriter osw_writter = null;
		BufferedWriter buf_writter = null;
		
		File rootFile = new File(root);
		File reRootFile = new File(reRoot);
		if (!reRootFile.exists()) {
			reRootFile.mkdirs();
		}
		File [] files = rootFile.listFiles();
		for (File file :files){
			String targetLine = "";
			String reStr = "";
			try {
				ins = new FileInputStream(file);
				ins_reader = new InputStreamReader(ins, "UTF-8");
				buf_reader = new BufferedReader(ins_reader);
				File reFile = new File(reRootFile.getAbsolutePath() + File.separator + file.getName());
				if (!reFile.exists()) {
					reFile.createNewFile();
				}
				ops = new FileOutputStream(reFile);
				osw_writter = new OutputStreamWriter(ops, "UTF-8");
				buf_writter = new BufferedWriter(osw_writter);
				
				while( (targetLine = buf_reader.readLine())!= null){
					for (int i = 0; i < 1000; i++) {
						if (targetLine.contains("\""+i+"dp\"")) {
							reStr = targetLine.replace("\""+i+"dp\"", "\"@dimen/dp_"+ i +"\"");
							break;
						}else{
							reStr = targetLine;
						}
					}
					buf_writter.append(reStr);
					buf_writter.newLine();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					buf_reader.close();
					ins_reader.close();
					ins.close();
					buf_writter.flush();
					buf_writter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
