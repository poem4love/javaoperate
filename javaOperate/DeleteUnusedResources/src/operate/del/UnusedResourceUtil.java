package operate.del;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class UnusedResourceUtil {

	public static UnusedResourceUtil getInstance(){
		return new UnusedResourceUtil();
	}
	
	/**
	 * 
	 * @param unusedRes_Doc_AbsPath
	 *            ָ��¼��δʹ�õ���Դ�ļ������Ƶ��ı��ĵ��ľ���·��,
	 *            һ��strings.xml,dimens.xml,values.xml�Ȼ���һЩ����ֱ���ڴ��������õ���Դ�ļ�
	 *            (����һ���������)����AS�����ı�׼�͹淶
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
								orgFile.getParent().indexOf("res")
										+ "res".length() + 1);
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
	
}
