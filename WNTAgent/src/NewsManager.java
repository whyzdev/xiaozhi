
public class NewsManager {

	/**
	 * @param args
	 */
	
	public static String GetNews(String inputstring){
		String outputstring=null;
		inputstring=inputstring.trim();
		//TODO 新闻判断
		if(inputstring.equals("新闻")){
			outputstring=GetNews.GetTopNews();
		}
		else if(inputstring.matches("(最近)?(来点|有什么)?(新闻|新鲜趣事)(吗|吧)?.*")){
			outputstring=GetNews.GetTopNews();
		}
		return outputstring;
	}
	
	
	public static void main(String[] args) {
		System.out.println(NewsManager.GetNews("新闻"));
	}

}
