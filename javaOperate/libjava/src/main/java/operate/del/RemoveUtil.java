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
	 *            ָ��¼��δʹ�õ���Դ�ļ������Ƶ��ı��ĵ��ľ���·��,
	 *            һ��strings.xml,dimens.xml,values.xml�Ȼ���һЩ����ֱ���ڴ��������õ���Դ�ļ�����AS�����ı�׼�͹淶
	 *            ,��ͬ������¼����,������Щ�ļ�Ҫ�ڸ��ı��ĵ���ȥ��.
	 * @param orgPath ׼�����м�Ⲣ�����,���е���Դ�ļ����ڵ��ļ��о���·��
	 * @param desPath Ŀ���,׼���ƶ������ļ��еľ���·��
	 * 
	 */
	public void removeUnusedRes(String unusedRes_Doc_AbsPath,
			String orgPath, String desPath) {
		FileInputStream ins = null;
		InputStreamReader ins_reader = null;
		BufferedReader buf_Reader = null;
		// ����¼��δʹ�õ���Դ�ļ������Ƶ��ı��ĵ�ת��ΪFile
		File unusedResDocFile = new File(unusedRes_Doc_AbsPath);
		try {
			String unused_res_name = "";
			ins = new FileInputStream(unusedResDocFile);
			ins_reader = new InputStreamReader(ins);
			buf_Reader = new BufferedReader(ins_reader);
			while ((unused_res_name = buf_Reader.readLine()) != null) { // ��ȡ��¼��δʹ�õ���Դ�ĵ���ÿһ��.
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
 * ��ÿһ�ж�����δʹ�õ���Դ�ļ���������׼���ƶ����ļ�����orgPath���м��.���������ƶ���һ��׼���õ�׼���ƶ������ļ���desPath��.
 * ��׼���ƶ�������Դ�ļ���ǰ����,һ����...\src\main\res\
 * 
 * @param unusedResName ��ȡ����δʹ�õ���Դ�ļ�������
 * @param orgPath ׼�����м�Ⲣ�����,���е���Դ�ļ����ڵ��ļ��о���·��
 * @param desPath Ŀ���,׼���ƶ������ļ��еľ���·��
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
