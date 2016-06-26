package operate.gen.folder;

public class GenCharacters {
private static String quotation_mark = "\"";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i = 97;i<123;i++){
			char[] chars = Character.toChars(i);
			System.out.print(quotation_mark);
			System.out.print(chars);
			System.out.print(quotation_mark);
			if (i != 122) {
				System.out.print(",");
			}
		}
	}

}
