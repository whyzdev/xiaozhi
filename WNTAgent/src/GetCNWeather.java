import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GetCNWeather {
	public static String GetWeather(String cityName){
		 String result=null;
		 if(cityName==null){
			 cityName="北京";
		 }
		 System.out.println(cityName);
	try {
	 	 URL[] url;
	 	 url=new URL[8];
		 
		 url[0] = new URL("http://www.weather.com.cn/textFC/hb.shtml");
		 url[1] = new URL("http://www.weather.com.cn/textFC/db.shtml");
		 url[2] = new URL("http://www.weather.com.cn/textFC/hd.shtml");
		 url[3] = new URL("http://www.weather.com.cn/textFC/hz.shtml");
		 url[4] = new URL("http://www.weather.com.cn/textFC/hn.shtml");
		 url[5] = new URL("http://www.weather.com.cn/textFC/xb.shtml");
		 url[6] = new URL("http://www.weather.com.cn/textFC/xn.shtml");
		 url[7] = new URL("http://www.weather.com.cn/textFC/gat.shtml");

			 
		 for(int i=0;i<8;i++)
		 {
			 URLConnection conn = url[i].openConnection();
			 conn.setDoOutput(true);
			
			 InputStream in = null;
			 in = url[i].openStream();
			 String content = pipe(in,"utf-8");
			 //System.out.println(content);
			 Pattern pt=Pattern.compile("target=\"_blank\">"+cityName+"</a></td><td width=\"82\">.{1,10}</td><td width=\"168\"><span>.{1,10}</span><span class=\"conMidtabright\">.{1,10}</span></td><td width=\"86\">.{1,10}</td><td width=\"97\">.{1,10}</td><td width=\"174\"><span>.{1,10}</span><span class=\"conMidtabright\">.{1,10}</span></td><td width=\"85\">.{1,10}</td><td width=\"47\" class=\"last\">");
			 Matcher mt=pt.matcher(content);
			 if(mt.find()){
				 result=content.substring(mt.start(), mt.end());
				// break;
			 }
		 }
		 
	
		 
		 if(result!=null){
			 result=result.replace("target=\"_blank\">", "\r\n");
			 result=result.replace("</a></td><td width=\"82\">", ":\r\n白天：");
			 result=result.replace("</td><td width=\"168\"><span>", "，");
			 result=result.replace("</span><span class=\"conMidtabright\">", "，");
			 result=result.replace("</span></td><td width=\"86\">", "，最高温度：");
			 result=result.replace("</td><td width=\"97\">", "\r\n晚上：");
			 result=result.replace("</td><td width=\"174\"><span>", "，");
			 result=result.replace("</span></td><td width=\"85\">", "，最低温度：");
			 result=result.replace("</td><td width=\"47\" class=\"last\">", "");
		 }
	} catch (MalformedURLException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	if(result==null){
		return result;
	}
		//result=result+"\r\n\r\n如果需要其它地区的天气，请输入地名，谢谢！";
		 return result;
	}
	
	static String pipe(InputStream in,String charset) throws IOException {
        StringBuffer s = new StringBuffer();
        if(charset==null||"".equals(charset)){
        	charset="utf-8";
        }
        String rLine = null;
        BufferedReader bReader = new BufferedReader(new InputStreamReader(in,charset));
        
	
        while ( (rLine = bReader.readLine()) != null) {
            String tmp_rLine = rLine;
            int str_len = tmp_rLine.length();
            if (str_len > 0) {
              s.append(tmp_rLine);
             
            }
            tmp_rLine = null;
       }
        in.close();
        
        return s.toString();
	}
	
	
}
