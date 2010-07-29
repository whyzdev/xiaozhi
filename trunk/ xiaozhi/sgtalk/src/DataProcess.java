import java.util.Calendar;

//处理文字的模块
public class DataProcess {
	
	public final static String mSymbols="~|\\.|\\?|!|'|:|;|,|/|\\(|\\)|&|\"|“|”|。|，|：|！|？|《|》|（|）|\\+";
	
	//线程存储用
	static String t1[]=null;
	
	
	//答案推荐线程
	/*
	class threadQC implements Runnable{
		@Override
		public void run() {
			t1=QCClient.query(t3);
		}
		
	}
	
	//CQA线程
	class threadCQA implements Runnable {
		@Override
		public void run() {
			t2=CQAClient.GetAnswer(t3);
		}
	}
	*/
	//自我介绍	
	public static String GetSelfIntro(){
		String temp="我是小智，我可以回答你的问题，例如：鱼香肉丝怎么做\r\n"
					+"还可以告诉你天气预报，如：北京天气，上海天气，今天天气怎么样\r\n"
					+"还可以告诉你当前的热点新闻,输入形式：新闻\r\n"
					+"还可以将中文或英文单词转换成英、法、韩、日、中任意一种，如：中国英文怎么说，Japan中文什么意思\r\n"
					+"如果简单地进行中、英文之间的转换，可用输入形式：翻译，中国；翻译，Japan\r\n\r\n\r\n"
					+"查询ip：请输入   ip+空格+任意ip地址\r\n"
					+"查询手机号：请输入  手机+空格+中国手机号（11位）\r\n" 
					+"查询身份证：请输入   身份证+空格+合法的身份证号\r\n"
					+"ps:输入“？”能再一次看到这段介绍";
		return temp;
	}
	
	
	//主要处理函数
	public static String GetReply(String inputstring, String from) {
		
		//遇到今年替换成当前年份（2010）,删除头尾空格		
		inputstring=inputstring.replaceAll("今年",String.valueOf((Calendar.getInstance().get(Calendar.YEAR)))).trim();
		
		System.out.println("DATA PROCESS================="+inputstring);
		//清空线程存储数据
		t1=null;
	
		
		//问题推荐返回
		String outputstring1[]={"",""};
		
		//输出字符
		String outputstring=null;
		
		//是否匹配上上文问题返回
		boolean change=false;
				
		//获取电子邮件
		
		for(int i=0;i<from.length();i++){
			if(from.substring(i, i+1).equals("/")){
				from=from.substring(0, i);
				break;
			}
		}
		
		
		
		System.out.println("1DATA PROCESS================="+inputstring);
		//匹配输出自我介绍
		if(inputstring.matches("？|\\?") || inputstring.matches(".*(小智|你)(会|能)(干|做)什么.*")){
			//+++++++++
			//UserData.accountdata.get(UserData.getIndex(from)).SetWeather(false);
			return GetSelfIntro();
		}
		
		
		
		
		
		Module m = new Module();
		if (inputstring.startsWith("ip ")) {
			outputstring=m.getArea(inputstring.substring(inputstring.indexOf(" ") + 1));
			
		} else if (inputstring.startsWith("手机 ")) {
			outputstring=m.getPhone(inputstring.substring(inputstring.indexOf(" ") + 1));
		} else if (inputstring.startsWith("身份证 ")) {
			outputstring=m.getIDCard(inputstring.substring(inputstring.indexOf(" ") + 1));
		} 
		
		
		/*else if (resContent.equalsIgnoreCase("你好")) {
			message.setContent("你好，我是小智，来自清华，有什么需要帮助的呢？\n" +
									"我现在能帮你\n" +
									"=============\n" +
									"1.查询天气：     请输入  天气+空格+主要城市名\n" +
									"2.查询ip：	      请输入   ip+空格+任意ip地址\n" +
									"3.查询手机号：请输入  手机+空格+中国手机号（11位）\n" +
									"4.查询身份证：请输入   身份证+空格+合法的身份证号");
		} else {
			message
					.setContent("你好，我是小智 ，来自清华，有什么需要帮助的呢？\n" +
									"我现在能帮你\n" +
									"=============\n" +
									"1.查询天气：     请输入  天气+空格+主要城市名\n" +
									"2.查询ip：	      请输入   ip+空格+任意ip地址\n" +
									"3.查询手机号：请输入  手机+空格+中国手机号（11位）\n" +
									"4.查询身份证：请输入   身份证+空格+合法的身份证号");// 设置要发送消息内容
		}
		*/
		
		
		
		
		
		
		//匹配上次问题答案
		
		
		if(UserData.accountdata.get(UserData.getIndex(from)).lastquestion!=null){
			for(int i=0;i<UserData.accountdata.get(UserData.getIndex(from)).answerset.size();i=i+2){
				if(inputstring.equals(UserData.accountdata.get(UserData.getIndex(from)).answerset.get(i))){
					//有则替换问题
					inputstring=UserData.accountdata.get(UserData.getIndex(from)).answerset.get(i+1);
					System.out.println(inputstring);
					change=true;
					
					break;
				}
			}
			//没匹配上则当成新问题，清除保存问题
			if(change==false){
				UserData.accountdata.get(UserData.getIndex(from)).clearquery();
			}			
		}
		
		
		System.out.println("2DATA PROCESS================="+inputstring);
		//System.out.println("inputstring:"+inputstring);		
		//天气，翻译，新闻
		//TODO 提取出来
		
		//---------------------------------------------------------------------------------
				
		
		
		//获取地点
		/*
		if(inputstring.contains("天气") && inputstring.length()<10){
			//TODO 匹配城市
			
			if(GetGoogleWeather.getCity1(inputstring)!=null){
				UserData.accountdata.get(UserData.getIndex(from)).SetPlace(GetGoogleWeather.getCity1(inputstring));
				//把题目替换成获取天气预报
				inputstring="今天天气";
			}
		}
		else if(UserData.accountdata.get(UserData.getIndex(from)).GetWeather()){
			if(GetGoogleWeather.getCity2(inputstring)!=null){
				UserData.accountdata.get(UserData.getIndex(from)).SetPlace(GetGoogleWeather.getCity2(inputstring));
				//把题目替换成获取天气预报
				inputstring="今天天气";
			}
			
			else {
				tryplace=inputstring;
				System.out.println(tryplace);
			}
			
		}
		place=UserData.accountdata.get(UserData.getIndex(from)).GetPlace();
		
		*/
		
		
		/*
		outputstring=WeatherManager.GetWeather(inputstring);
		if(outputstring!=null){
			System.out.println("output 1");
			return outputstring;
		}
		
		
		
		outputstring=TranslateManager.GetTranslate(inputstring);
		if(outputstring!=null){
			System.out.println("output 2");
			return outputstring;
		}
		
		outputstring=NewsManager.GetNews(inputstring);
		if(outputstring!=null ){
			System.out.println("output 3");
			return outputstring;
		}
		*/
		
		System.out.println("3DATA PROCESS================="+inputstring);
		inputstring.replaceAll("===天气", "");
		if(UserData.accountdata.get(UserData.getIndex(from)).GetWeather() && inputstring.length()<10){
			//System.out.println(inputstring);
			inputstring=inputstring+"===天气";
			//System.out.println(inputstring);
		}
		
		System.out.println("4DATA PROCESS================="+inputstring);
		outputstring=WNTClient.GetWNT(inputstring);
		
		if(outputstring!=null ){
			//System.out.println("output 3");
			if(outputstring.equals("null")){
				outputstring=null;
			}
			else{
				/*
				if(outputstring.contains("温度") && outputstring.contains("湿度")){
					UserData.accountdata.get(UserData.getIndex(from)).SetWeather(true);
				}
				else{
					UserData.accountdata.get(UserData.getIndex(from)).SetWeather(false);
				}
				System.out.println("output wnt");
				
				++++++++++++++++++++++++++++++++++++++++++
				*/
				return outputstring;
			}
		}
		System.out.println("5DATA PROCESS================="+inputstring);
		
		inputstring.replaceAll("===天气", "");
		/*
		//翻译
		if(TranslateIntentClassifier.hasIntent(inputstring)){
			//System.out.println("翻译："+ TranslateIntentClassifier.translationWord(inputstring));
			outputstring=GoogleTranslate.translate(TranslateIntentClassifier.translationWord(inputstring),TranslateIntentClassifier.translationLanguage(inputstring),TranslateIntentClassifier.translationFrom(inputstring));
			return outputstring;
		}
		*/
		
		//去除标点符号，以用来匹配语料库
		String nosymbolinput=inputstring;
		
		nosymbolinput=nosymbolinput.replaceAll(mSymbols," ");
		nosymbolinput=nosymbolinput.replaceAll("\\s{1,100}"," ");
		
		//输入为空时
		if(inputstring.matches("\\s*") || inputstring.isEmpty()){
			outputstring=ChatDtbase.GetEmoticon();
		}
		
		
		
		
		
		//匹配语料库
		
		
		outputstring=ChatDtbase.CheckDatabase(nosymbolinput);
		/*
		if(outputstring!=null &&outputstring.equals("新闻")){
			outputstring=GetNews.GetTopNews();
			return outputstring;
		}
		*/
		
		
		
		
		//TODO 提取天气，新闻，翻译
		//System.out.println("天气测试："+outputstring+"\r\n"+nosymbolinput);
		/*
		UserData.accountdata.get(UserData.getIndex(from)).SetWeather(false);
		if(outputstring!=null && outputstring.contains("今天天气")){
			//天气
			//-------
			outputstring= GetGoogleWeather.getWeather(place);
			//-------
			if(outputstring==null ||outputstring.contains("今天天气")){
				outputstring=GetCNWeather.GetWeather(place);
			}
			UserData.accountdata.get(UserData.getIndex(from)).SetWeather(true);
			return outputstring;
			
		}
		
		if(tryplace!=null && place!=null){
			outputstring=GetCNWeather.GetWeather(tryplace);
			if(outputstring!=null){
				return outputstring;
			}
		}
		*/
		//-------------------------------------------------------------------------------
		
		/*
		 * 英文则翻译
		if(TranslateIntentClassifier.translationFrom(inputstring).equals("英")){
			inputstring=GoogleTranslate.translate(inputstring, "中", "英");
			//System.out.println("translate:"+inputstring);
		}
		*/
		//并行查询cqa和答案推荐
		if(outputstring==null){
			t1=CQAClient.GetAnswer(inputstring);
			if(t1==null){
				outputstring=null;
			}
			else{
				outputstring=t1[0];
				outputstring1[0]=t1[1];
				outputstring1[1]=t1[2];
				if(outputstring1[0].equals("")){
					outputstring1=null;
				}
			}
			/*
			t3=inputstring;
			Thread qcthread=new Thread(dp.new threadQC());
			Thread cqathread=new Thread(dp.new threadCQA());
			qcthread.start();
			cqathread.start();
			
			while(true){
				//等待线程结束
				if(!qcthread.isAlive() && !cqathread.isAlive()){
					break;
				}
				try {
				//查询休息间隔
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			//获取信息
			outputstring1=t1;
			outputstring=t2;
			*/
		}
		
		
		//查询失败处理
		if (outputstring==null || outputstring.equals("") || outputstring.contains("对不起，我们的数据库里暂时没有类似您提出的问题。") || outputstring.contains("<h4><font color=\"red\">对不起，暂时无法回答您的问题</font></h4>") ){
			
			if(outputstring1==null ){
				//cqa和答案推荐都失败，返回表情
				outputstring=ChatDtbase.GetEmoticon();
			}
			else{
				//cqa失败，答案推荐成功，将输出设为答案推荐，并保存推荐表
				outputstring=outputstring1[0];
				System.out.println(outputstring1[1]);
				
				UserData.accountdata.get(UserData.getIndex(from)).SaveQuery(inputstring, outputstring1[1]);
			}
			
		}
		else{
			//若两方都得到答案，整合答案，保存推荐表
			if(outputstring1!=null && outputstring1[0]!=null &&outputstring1[1]!=null && !outputstring1[0].equals("")){
				outputstring=outputstring+"\r\n\r\n"+"您可以添加以下信息获取更好的答案: \r\n"+outputstring1[0];
				System.out.println(outputstring1[1]);
				
				UserData.accountdata.get(UserData.getIndex(from)).SaveQuery(inputstring, outputstring1[1]);
				
			}
			
		}
		
		//System.out.println(outputstring+" : got reply ");
		
		UserData.accountdata.get(UserData.getIndex(from)).SetWeather(false);
		System.out.println("output end");
		if (outputstring==null || outputstring.equals("")){
			outputstring=ChatDtbase.GetEmoticon();
		}
		return outputstring;
		
	}

	
}
