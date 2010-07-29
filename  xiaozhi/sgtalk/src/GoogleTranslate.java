import com.google.api.translate.Language;
import com.google.api.translate.Translate;

public class GoogleTranslate {
	
	public static boolean firstuse = true;
	
	public static void init(){
		firstuse = false;
		Translate.setHttpReferrer("http://60.195.250.72/homepage");
	}
	
	public static String translate(String word , String langtype, String fromtype){
		if(firstuse)
			init();
		
		String translatedText = null;
		try{
			/*
			if(isChinese){
				translatedText = Translate.execute(word, Language.CHINESE, Language.ENGLISH); 	
			}else{
				translatedText = Translate.execute(word, Language.ENGLISH, Language.CHINESE); 	
			}
			*/
			Language from=Language.CHINESE;
			Language to=Language.ENGLISH;
			if(langtype.equals("英")){
				//translatedText = Translate.execute(word, Language.CHINESE, Language.ENGLISH);
				to=Language.ENGLISH;
			}
			else if(langtype.equals("法")){
				//translatedText = Translate.execute(word, Language.CHINESE, Language.FRENCH);
				to=Language.FRENCH;
			}
			else if(langtype.equals("日")){
				//translatedText = Translate.execute(word, Language.CHINESE, Language.JAPANESE);
				to=Language.JAPANESE;
			}
			else if(langtype.equals("韩")){
				//translatedText = Translate.execute(word, Language.CHINESE, Language.KOREAN);
				to=Language.KOREAN;
			}
			else if(langtype.equals("中")){
				//translatedText = Translate.execute(word, Language.ENGLISH, Language.CHINESE);
				to=Language.CHINESE;
			}
			
			
			if(fromtype.equals("英")){
				//translatedText = Translate.execute(word, Language.CHINESE, Language.ENGLISH);
				from=Language.ENGLISH;
			}
			else if(fromtype.equals("法")){
				//translatedText = Translate.execute(word, Language.CHINESE, Language.FRENCH);
				from=Language.FRENCH;
			}
			else if(fromtype.equals("日")){
				//translatedText = Translate.execute(word, Language.CHINESE, Language.JAPANESE);
				from=Language.JAPANESE;
			}
			else if(fromtype.equals("韩")){
				//translatedText = Translate.execute(word, Language.CHINESE, Language.KOREAN);
				from=Language.KOREAN;
			}
			else if(fromtype.equals("中")){
				//translatedText = Translate.execute(word, Language.ENGLISH, Language.CHINESE);
				from=Language.CHINESE;
			}
			
			translatedText = Translate.execute(word, from, to);
			
			
			if(word.contains(translatedText)){
				translatedText = Translate.execute(word, Language.ENGLISH, Language.CHINESE);
				//System.out.println("english->chinese");
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return translatedText;
	}
	
	public static void main(String[] args) throws Exception {
		// Set the HTTP referrer to your website address.    
		Translate.setHttpReferrer("http://60.195.250.72/homepage");    
		//String translatedText = Translate.execute("who is the first president of America", Language.CHINESE, Language.ENGLISH); 
		System.out.println(translate("黑猫英语怎么说","英","中"));
		//String translatedText = Translate.execute("N95", Language.CHINESE, Language.ENGLISH);  
		String translatedText = Translate.execute("who is the first president of America", Language.ENGLISH, Language.CHINESE);  
		System.out.println(translatedText);  
	}
	
}