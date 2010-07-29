
public class MainAgent {

	public static String GetReply(String inputstring){
		String outputstring=null;
		
		outputstring=WeatherManager.GetWeather(inputstring);
		if(outputstring!=null){
			return outputstring;
		}
		inputstring=inputstring.replaceAll("===", "");
		inputstring=inputstring.replaceAll("天气", "");
		System.out.println("修改："+inputstring);
		outputstring=NewsManager.GetNews(inputstring);
		if(outputstring!=null){
			return outputstring;
		}
		
		outputstring=TranslateManager.GetTranslate(inputstring);
		if(outputstring!=null){
			return outputstring;
		}
		
		return outputstring;
	}

	public static void main(String[] args) {
		System.out.println(GetReply("北京天气"));
		System.out.println(GetReply("你好日文怎么说===天气"));
		System.out.println(GetReply("新闻"));
	}
}
