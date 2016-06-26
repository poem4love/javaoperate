package operate.del;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class RemoveUtil {
	
	public static RemoveUtil getInstance(){
		return new RemoveUtil();
	}

	private String separator = File.separator;

	static String res_root = "E:" + File.separator + "creditModule"
			+ File.separator + "WSZX" + File.separator + "src" + File.separator
			+ "main" + File.separator + "res";

	static BufferedWriter bWriter = null;
	static File name = null;
	static String res_name = "";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		name = new File("E:" + File.separator + "temporary" + File.separator
				+ "result.txt");
		// try {
		// bWriter = new BufferedWriter(new OutputStreamWriter(new
		// FileOutputStream(name)));
		// bWriter.write(checkFile(res_root));
		// bWriter.flush();
		// } catch (FileNotFoundException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// if (!name.exists()) {
		// try {
		// name.createNewFile();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }

		FileInputStream ins = null;
		InputStreamReader reader = null;
		BufferedReader buffer = null;
		File file = new File("E:" + File.separator + "temporary"
				+ File.separator + "unused.txt");
		try {
			String space = "";
			ins = new FileInputStream(file);
			reader = new InputStreamReader(ins);
			buffer = new BufferedReader(reader);
			while ((space = buffer.readLine()) != null) {
				checkFile(res_root, space);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (buffer != null) {
					buffer.close();
				}
				if (reader != null) {
					reader.close();
				}
				if (ins != null) {
					ins.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param unusedRes_Doc_AbsPath
	 *            指记录了未使用的资源文件的名称的文本文档的绝对路径,
	 *            一般strings.xml,dimens.xml,values.xml等还有一些不是直接在代码中引用的资源文件根据AS导出的标准和规范
	 *            ,会同样被记录在内,但是这些文件要在该文本文档里去掉.
	 * @param orgPath 准备进行检测并清理的,现有的资源文件所在的文件夹绝对路径
	 * @param desPath 目标的,准备移动到的文件夹的绝对路径
	 * 
	 */
	public void removeUnusedRes(String unusedRes_Doc_AbsPath,
			String orgPath, String desPath) {
		FileInputStream ins = null;
		InputStreamReader ins_reader = null;
		BufferedReader buf_Reader = null;
		// 将记录了未使用的资源文件的名称的文本文档转化为File
		File unusedResDocFile = new File(unusedRes_Doc_AbsPath);
		try {
			String unused_res_name = "";
			ins = new FileInputStream(unusedResDocFile);
			ins_reader = new InputStreamReader(ins);
			buf_Reader = new BufferedReader(ins_reader);
			while ((unused_res_name = buf_Reader.readLine()) != null) { // 读取记录了未使用的资源文档的每一行.
				removeUnusedRes_SecondStep(unused_res_name, orgPath, desPath);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (buf_Reader != null) {
					buf_Reader.close();
				}
				if (ins_reader != null) {
					ins_reader.close();
				}
				if (ins != null) {
					ins.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
/**
 * 把每一行读到的未使用的资源文件的名称在准备移动的文件夹下orgPath进行检测.若存在则移动到一个准备好的准备移动到的文件夹desPath里.
 * 在准备移动的是资源文件的前提下,一般是...\src\main\res\
 * 
 * @param unusedResName 读取到的未使用的资源文件的名称
 * @param orgPath 准备进行检测并清理的,现有的资源文件所在的文件夹绝对路径
 * @param desPath 目标的,准备移动到的文件夹的绝对路径
 */
	private void removeUnusedRes_SecondStep(String unusedResName, String orgPath,
			String desPath) {
		File orgFile = new File(orgPath);
		if (orgFile.isDirectory()) {
			File[] files = orgFile.listFiles();
			for (File f : files) {
				removeUnusedRes_SecondStep(unusedResName,f.getAbsolutePath(), desPath);
			}
		} else if (orgFile.isFile()) {
			String existFileName = orgFile.getName();
			if (unusedResName.equals(existFileName)) {
				String prefix = orgFile.getParent()
						.substring(
								orgFile.getParent().indexOf("src")
										+ "src".length() + 1);
				File desFileFolder = new File(desPath + File.separator + prefix
						);
				if (!desFileFolder.exists()) {
					desFileFolder.mkdirs();
				}
				File desFile = new File(desFileFolder.getAbsolutePath()+ File.separator+ existFileName);
				orgFile.renameTo(desFile);
			}
		}
	}

	public static String checkFile(String filepath, String rFile) {

		File targetFile = new File(filepath);
		if (targetFile.isDirectory()) {
			File[] files = targetFile.listFiles();
			for (File f : files) {
				checkFile(f.getAbsolutePath(), rFile);
			}
		} else if (targetFile.isFile()) {
			String name = targetFile.getName();
			res_name = targetFile.getAbsolutePath();
			if (rFile.equals(name)) {
				String prefix = targetFile.getParent().substring(
						targetFile.getParent().indexOf("res") + "res".length()
								+ 1);
				File nova = new File("E:" + File.separator + "temporary"
						+ File.separator + prefix + File.separator);
				if (!nova.exists()) {
					nova.mkdirs();
				}
				new File(res_name).renameTo(new File(nova.getAbsolutePath()
						+ File.separator + name));
			}
		}
		return "";
	}

}
