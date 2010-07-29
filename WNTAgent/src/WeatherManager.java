import java.util.HashSet;
import java.util.Set;


public class WeatherManager {

	/**
	 * @param args
	 */
	public static String GetWeather(String inputstring){
		
		
		boolean getout=false;
		//翻译 英->中 以支持英文
		inputstring=GoogleTranslate.translate(inputstring, "中" , "英");
		//输出串
		String outputstring="";
		//分词
		String temp=null;
		temp=WordSplit.split(inputstring, 1);
		
		//保存每个城市
		Set<String> cities=new HashSet<String>();
		//System.out.println(inputstring);
		if(inputstring.contains("===天气")){
			System.out.println("here0"+inputstring);
			getout=true;
		}
		//若输入不含天气字眼则退出
		else if(!temp.contains("天气/n") && !inputstring.replaceAll("===天气", "").matches("(今天|明天|后天)?(的|会)?(气温|温度|下雨)(吗|怎样|如何)?(\\?|？)?")){
			System.out.println("here1"+inputstring);
			return null;
		}
		inputstring=inputstring.replaceAll("===天气", "");
		//将分词结果分开
		String[] s=temp.split("[ ]+");
		System.out.println("here3"+inputstring);
		if(inputstring.matches("(今天|明天|后天)?(的|会)?(气温|温度|下雨|天气)(吗|怎样|如何)?(\\?|？)?") ){
			System.out.println("here2"+inputstring);
			getout=false;
		}
		
		//找寻所有城市名称，并存到cities
		for(String str:s){
			str=str.trim();
			if(str.matches(".*/nsf") || str.matches(".*/ns"))
			{
				String[] ct=str.split("\\/");
				cities.add(ct[0]);
				
			}
		}
		
		//若没有城市则默认北京
		if(cities.size()==0){
			if(getout==true){
				System.out.println("here");
				return null;
			}
			else{
				cities.add("北京");
			}
		}
		
		//将每个城市翻译成英文从google查询
		for(String city:cities){
			String engname=GoogleTranslate.translate(city, "英" , "中");
			outputstring=outputstring+GetGoogleWeather.getWeather(engname);
		}
		
		//若没有结果则从中国天气网查询
		if(outputstring.equals("") || outputstring.equals("null")){
			for(String city:cities){
				outputstring=GetCNWeather.GetWeather(city);
				if(outputstring!=null && !outputstring.equals("null")){
					//因为这个查询比较耗时，找到一个答案便返回，不继续查找
					return outputstring;
				}
			}
			//还是没结果则返回null
			return null;
		}
		return outputstring;
	}
	
	
	public static void main(String[] args) {
		System.out.println(GetWeather("今天天气怎样"));
		
	}

}
