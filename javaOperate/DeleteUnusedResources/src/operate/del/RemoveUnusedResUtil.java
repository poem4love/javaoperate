package operate.del;

public class RemoveUnusedResUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UnusedResourceUtil.getInstance().removeUnusedRes(
				"E:\\06-21\\unusedsdkres.txt",
				"E:\\wealth\\wsjtcf\\src\\main\\res",
				"E:\\06-21\\unressdk\\");
	}

}
