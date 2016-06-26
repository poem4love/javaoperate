import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Set set = new HashSet();
        set.add("abc");
        set.add("cde");
        set.add("efg");
        set.add("fgh");
        set.add("abc"); // 重复的abc,set会自动将其去掉
//		System.out.println("size=" + set.size());

        List list = new ArrayList();
        list.add("abc");
        list.add("aaa");
        list.add("fff");
        set.addAll(list); // 将list中的值加入set,并去掉重复的abc
//		System.out.println("size=" + set.size());

//		for (Iterator it = set.iterator(); it.hasNext();) {
//			System.out.println("value=" + it.next().toString());
//		}

//		System.out.println("adc".split("#").length + "");
//		System.out.println("adc#".split("#").length + "");
//		System.out.println("#adc".split("#").length + "");
//		System.out.println("#a#dc".split("#").length + "");
//		int len = "a#dc".split("#").length;
//		System.out.println(len);
        String fileName = "9E:abab.cdf ";
        System.out.println(fileName.substring(fileName.indexOf("E"), fileName.length()));

    }
}
