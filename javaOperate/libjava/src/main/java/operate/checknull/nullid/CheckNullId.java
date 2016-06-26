package operate.checknull.nullid;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import operate.expection.FindNotIdException;

/**
 * <p>
 * * 蜂与蜂蜜 *
 * </p>
 * 检测指定文件或文件夹下android activity或fragment当中使用到的id是否在其布局文件中包含，以减少空指针
 * <p>
 * 但是这样病不能完全杜绝,比如 自定义的 view 可以 findviewbyid(),但是那些id是可以配置在valus里的,activity在find时却要包含view里指定的id。这个暂时还未完善,
 * </p>
 * <p>
 * 需要使用到 .xml解析和输入流
 * </p>
 */
public class CheckNullId {
    public static void main(String[] args) {
        final String PATH = "E:\\newfolder\\renandroidddle7\\src\\main\\java\\com\\wenshi\\ddle\\activity\\DesignMyPersonZoneCredActivity.java";
        $(genAllfilesname(PATH));

    }

    static ArrayList<String> allfilesName = new ArrayList();
    static ArrayList<String> allXmlElesIds = new ArrayList();

    static {
        allfilesName.clear();
        allXmlElesIds.clear();
    }

    //1.首先得到所有要分析的文件的文件名
    public static ArrayList<String> genAllfilesname(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return new ArrayList();
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            //如果是文件夹，那么对其中的文件递归调用方法，得到文件而不是文件夹的名字.
            for (File f : files) {
                genAllfilesname(f.getPath());
            }
        } else {
            allfilesName.add(file.getPath());
        }

        return allfilesName;
    }

    public static void $(ArrayList<String> allfiles) {
        //一个一个文件的进行查询!!!!
        for (String fileName : allfiles) {

            System.out.println("## analyzing file:\\ " + fileName + " ##");
            File f = new File(fileName);//单个文件
            //2.读取每一个文件,读取到所有的id置入一个集合.
            ArrayList<String> idsInPer = new ArrayList<>();//perfile所有id名
            FileInputStream ins = null;
            InputStreamReader insReader = null;
            BufferedReader buffReader = null;
            String perXmlFileName = ""; //xml文件名
            ArrayList<String> theLayouts = new ArrayList<>();  //用到了多个layout
            try {
                ins = new FileInputStream(f);
                insReader = new InputStreamReader(ins);
                buffReader = new BufferedReader(insReader);
                String perFileName = "";

                while ((perFileName = buffReader.readLine()) != null) {//每一行的内容
                    String trimedPerFileName = perFileName.trim();
                    if (!trimedPerFileName.startsWith("//") && !trimedPerFileName.startsWith("*") && !trimedPerFileName.startsWith("**")) { //如果不是注释的行，当然注释种类很多

                        if (trimedPerFileName.contains("R.layout.")) {
                            if (trimedPerFileName.contains("inflate")) {//inflater形式 有多种inflater形式，所以从R.layout开始截取至遇见的第一个逗号
                                String subCommaString = trimedPerFileName.substring(trimedPerFileName.indexOf("R.layout."));
                                perXmlFileName = subCommaString.substring(subCommaString.indexOf("R.layout.") + "R.layout.".length(), subCommaString.indexOf(","));
                            } else if (trimedPerFileName.contains("setContentView")) { //setContentView形式
                                perXmlFileName = trimedPerFileName.substring(trimedPerFileName.indexOf("R.layout.") + "R.layout.".length(), trimedPerFileName.lastIndexOf(")"));
                            } else if (trimedPerFileName.contains("super(context,")) { //super()构造函数形式
                                perXmlFileName = trimedPerFileName.substring(trimedPerFileName.indexOf("R.layout.") + "R.layout.".length(), trimedPerFileName.lastIndexOf(")"));
                            } else {
                                System.out.println(trimedPerFileName + " not found ~~~~~");
                            }
                            System.out.println("  **  " + perXmlFileName + "  **  ");

                            theLayouts.add(perXmlFileName);

                        } else if (trimedPerFileName.contains("findViewById")) { // findviewById格式
                            String s = trimedPerFileName.trim().substring(trimedPerFileName.trim().indexOf("R.id.") + "R.id.".length());
                            idsInPer.add(s.substring(0, s.indexOf(")")));
                        } else if (trimedPerFileName.contains("case R.id.")) { // case R.id.* 格式
                            String s = trimedPerFileName.trim().substring(trimedPerFileName.trim().indexOf("R.id.") + "R.id.".length());
                            idsInPer.add(s.substring(0, s.indexOf(":")));
                        } else if (trimedPerFileName.contains("@Bind")) { //@Bind(R.id.*)
                            String s = trimedPerFileName.trim().substring(trimedPerFileName.trim().indexOf("R.id.") + "R.id.".length());
                            idsInPer.add(s.substring(0, s.indexOf(")")));
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            ArrayList<String> allXmlIds = new ArrayList<>();
            //如果有多个layout,那么把多个的ids都加起来.
            for (String per : theLayouts) {
                ArrayList<String> subAllXmlIds = genAllXmlIds("file:\\E:\\newfolder\\renandroidddle7\\src\\main\\res\\layout\\" + per + ".xml");//xml中所有id
                allXmlIds.addAll(subAllXmlIds);
            }
            if (allXmlIds.size() > 0) {
                for (String id : idsInPer) {
                    //如果不存在于任何一个layout里面,则抛出异常
                    try {
                        if (!allXmlIds.contains(id)) {
                            throw new FindNotIdException("does not has id : " + id + " in .xml " + perXmlFileName.toUpperCase());
                        }
                    } catch (FindNotIdException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 4.读取每一个xml文件,读取到所有的id置入一个集合.
     * <p>!需要考虑include的情况</p>
     *
     * @param xmlName
     * @return
     */
    public static ArrayList<String> genAllXmlIds(String xmlName) {
        InputStream ins;
        try {
            ins = new URL(xmlName).openConnection().getInputStream();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(ins);
            Element root = document.getDocumentElement();
            getAllElementIds(root);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allXmlElesIds;
    }

    private static void getAllElementIds(Element ele) {
        if (ele.getTagName().equals("include")) {
            genAllXmlIds("file:\\E:\\\\newfolder\\renandroidddle7\\src\\main\\res\\layout\\" + ele.getAttribute("layout").substring("@layout/".length()) + ".xml");
        }

        if (ele.hasAttribute("android:id")) {
            String perXmlEleId = ele.getAttribute("android:id");
            allXmlElesIds.add(perXmlEleId.substring("@+id/".length()));
        }
        NodeList nodeList = ele.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) childNode;
                getAllElementIds(element);
            }
        }
    }

}
