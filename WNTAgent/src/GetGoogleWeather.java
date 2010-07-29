
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class GetGoogleWeather {
	
	
    public static String getWeather(String cityName) {
        // 获取google上的天气情况，写入文件
    	String fileAddr="weather.xml";
    	
        try {
            URL url = new URL("http://www.google.com/ig/api?hl=zh_cn&weather="
                    + cityName.replaceAll(" ", "%20"));
        	
            InputStream inputstream = url.openStream();
            String s, str;
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    inputstream,"GB2312"));
            StringBuffer stringbuffer = new StringBuffer();
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileAddr), "utf-8"));
            while ((s = in.readLine()) != null) {
                stringbuffer.append(s);
            }
            str = new String(stringbuffer);
            out.write(str);
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 读取需要的数据
        File file = new File(fileAddr);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        String str = null;
        String str1=null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            
            NodeList nltry=(NodeList) doc.getElementsByTagName("current_conditions");
            if(nltry==null ||nltry.item(0)==null){
            	return null;
            }
            NodeList nltry1=nltry.item(0).getChildNodes();
            
            //System.out.println(new String(nltry1.item(0).getAttributes().item(0).getNodeValue()).getBytes("utf-8"));
            
            str1=	"\r\n"+GoogleTranslate.translate(cityName, "中", "英")+":\r\n" 
            		+"目前："
            		+nltry1.item(0).getAttributes().item(0).getNodeValue()
            		+"\r\n温度："
            		+nltry1.item(2).getAttributes().item(0).getNodeValue()
            		+"℃\r\n"
            		+nltry1.item(3).getAttributes().item(0).getNodeValue()
            		+"\r\n"
            		+nltry1.item(5).getAttributes().item(0).getNodeValue()
            		+"\r\n\r\n"
            		+"今天：";
            
           // System.out.println(str1);
            NodeList nodelist1 = (NodeList) doc
                    .getElementsByTagName("forecast_conditions");
            NodeList nodelist2 = nodelist1.item(0).getChildNodes();
            str = str1
            		+nodelist2.item(4).getAttributes().item(0).getNodeValue()
                    + ",温度:"
                    + nodelist2.item(1).getAttributes().item(0).getNodeValue()
                    + "℃-"
                    + nodelist2.item(2).getAttributes().item(0).getNodeValue()
                    + "℃\r\n";
            
            nodelist2 = nodelist1.item(1).getChildNodes();
            str = str
            		+"明天："
            		+nodelist2.item(4).getAttributes().item(0).getNodeValue()
                    + ",温度:"
                    + nodelist2.item(1).getAttributes().item(0).getNodeValue()
                    + "℃-"
                    + nodelist2.item(2).getAttributes().item(0).getNodeValue()
                    + "℃\r\n";
            
            nodelist2 = nodelist1.item(2).getChildNodes();
            str = 	str
            		+"后天："
            		+nodelist2.item(4).getAttributes().item(0).getNodeValue()
                    + ",温度:"
                    + nodelist2.item(1).getAttributes().item(0).getNodeValue()
                    + "℃-"
                    + nodelist2.item(2).getAttributes().item(0).getNodeValue()
                    + "℃\r\n\r\n" ;//\r\n\r\n如果需要其它地区的天气，请输入地名，谢谢！";
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        //删除文件
        file.delete();
        
		System.out.println(str);
		 
        return str;
    }
    
    public static void main(String[] args) {
		System.out.println(getWeather("北京"));
		
	}
}
  