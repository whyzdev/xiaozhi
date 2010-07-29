import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GetNews {
	public static String GetTopNews() {
		String outputstring="";
		try {
		URL url = new URL("http://news.thuir.org/day.html");
		URLConnection conn = url.openConnection();
		
		conn.setDoOutput(true);
		InputStream in = null;
		in = url.openStream();
		
		
		StringBuffer s = new StringBuffer();
        String charset="GB2312";
        
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
       
        Random rand=new Random();
        int num=rand.nextInt(7);
        
        String content=s.toString();
        
        
        String result=null;
		String result2=null;
		
		String temp1=null;
		String temp2=null;
		String temp3=null;
		
		
		Pattern pt=Pattern.compile("target=_blank><b>.{1,100}</b>");
		Matcher mt=pt.matcher(content);
		
		//
		for(int i=0;i<num;i++){
			mt.find();
		}
		//
		
		if(mt.find()){
		 result=content.substring(mt.start()+17, mt.end()-4);
		}
		result=result.replaceAll("&quot;", "\"");
		result=result.replaceAll("\\[ͼ\\]", "");
		result=result.replaceAll("\\(ͼ\\)", "");
	
		 outputstring=outputstring+result+"\r\n\r\n";
		 
		 
		 
		 
		if(mt.find()){
			temp3=content.substring(mt.start()+17, mt.end()-4);
		}
		temp3=temp3.replaceAll("&quot;", "\"");
		temp3=temp3.replaceAll("\\[ͼ\\]", "");
		temp3=temp3.replaceAll("\\(ͼ\\)", "");
		
		temp1=temp3+"\r\n"; 
		
		if(mt.find()){
			temp3=content.substring(mt.start()+17, mt.end()-4);
		}
		temp3=temp3.replaceAll("&quot;", "\"");
		temp3=temp3.replaceAll("\\[ͼ\\]", "");
		temp3=temp3.replaceAll("\\(ͼ\\)", "");
		 
		temp2=temp3+"\r\n"; 
		 
		pt=Pattern.compile("</nobr></b></font><br><font size=-1>.{1,1000}</font><b>");
		mt=pt.matcher(content);
		
		//
		for(int i=0;i<num;i++){
			mt.find();
		}
		//
		if(mt.find()){
			 result2=content.substring(mt.start()+38, mt.end()-10);
		}
		result2=result2.replaceAll("&quot;", "\"");
		result2=result2.replaceAll("\\[ͼ\\]", "");
		result2=result2.replaceAll("\\(ͼ\\)", "");
		
		 outputstring=outputstring+result2+"\r\n";
		 
		pt=Pattern.compile("<a href='.{1,100}' target=_blank><b>");
		mt=pt.matcher(content);
		
		//
		for(int i=0;i<num;i++){
			mt.find();
		}
		//
				
		if(mt.find()){
		 result2=content.substring(mt.start()+9, mt.end()-17);
		}
		result2=result2.replaceAll("&quot;", "\"");
		result2=result2.replaceAll("\\[ͼ\\]", "");
		result2=result2.replaceAll("\\(ͼ\\)", "");
	
		outputstring=outputstring+result2+"\r\n";
		
		
		
		if(mt.find()){
			 temp3=content.substring(mt.start()+9, mt.end()-17);
		}
		temp3=temp3.replaceAll("&quot;", "\"");
		temp3=temp3.replaceAll("\\[ͼ\\]", "");
		temp3=temp3.replaceAll("\\(ͼ\\)", "");
		
		temp1=temp1+temp3+"\r\n";
		
		if(mt.find()){
			 temp3=content.substring(mt.start()+9, mt.end()-17);
		}
		temp3=temp3.replaceAll("&quot;", "\"");
		temp3=temp3.replaceAll("\\[ͼ\\]", "");
		temp3=temp3.replaceAll("\\(ͼ\\)", "");
		
		temp2=temp2+temp3+"\r\n";
		
		outputstring=outputstring+"\r\n\r\n"+temp1+"\r\n"+temp2;
		
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		return outputstring;
	}
}
