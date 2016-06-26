package operate.gen.dp;

public class GenerateDimens {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i = 1; i < 1001;i++){
			
			System.out.println("<dimen name=\"dp_"+i + "\">" + i + "dp</dimen>");
			
		}
	}

}
