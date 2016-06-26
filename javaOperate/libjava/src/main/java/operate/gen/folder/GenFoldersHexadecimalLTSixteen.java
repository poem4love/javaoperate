package operate.gen.folder;

import java.io.File;

public class GenFoldersHexadecimalLTSixteen {
private final static String fileRoot = "E:\\creditbackup\\java_backup";
private final static int seqeunce = 0;
private final static String [] characters = new String []{
	"a","b","c","d","e","f"
};
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int index = 0; index < 16; index ++){
			if (index % 16 < 10) {
				File file = new File(fileRoot + File.separator +"0x0" + index);
				if (!file.exists()) {
					file.mkdirs();
				}
			}else{
				File file = new File(fileRoot + File.separator +"0x0" + characters[index%10]);
				if (!file.exists()) {
					file.mkdirs();
				}
			}
			
		}
	}

}
