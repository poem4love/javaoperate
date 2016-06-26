package operate.del;

public class RemoveUnusedResUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UnusedResourceUtil.getInstance().removeUnusedRes(
				"E:\\unused.txt",
				"E:\\wealth\\wsjtcf\\src\\main\\res",
				"E:\\unressdk\\");
	}

}
