

import java.util.regex.*;;

public class TranslateIntentClassifier {

	private static Pattern p=Pattern.compile("(?:\"|&quot;|“)(.+)(?:\"|&quot;|”)");
	private static Pattern planguage=Pattern.compile(".*(.)[语文].*");
	public final static String puctuationReg="~|\\.|\\?|!|'|:|;|,|/|\\(|\\)|&|\"|“|”|。|，|：|！|？|《|》|（|）|\\+";
	
	
	
	public static boolean hasIntent(String question)
	{
		if(question.matches(".*(怎么|如何)(说|讲).*")&&question.matches(".*[英法日韩中][语文].*"))
			return true;
		
		if(question.matches(".*(什么|啥)意思.*")&&question.matches(".*[英法日韩中][语文].*"))
			return true;
		
		if(question.matches(".*(怎么|如何|请)(翻译).*"))
			return true;
		
		if(question.matches(".*的英文(是什么)?"))
			return true;
	
		if(question.matches("翻译((为|到|成)?.(语|文))?(：|:|,|，|\\s).*"))
			return true;
		
		return false;
	}
	
	public static String translationFrom(String question)
	{
		/*
		if(!hasIntent(question))
			return null;
		*/
		String tword=question;
		if(tword.matches("[^\u4e00-\u9fa5]+"))
			return "英";
		
		
		return "中";
	}
	
	
	public static String translationLanguage(String question)
	{
		if(!hasIntent(question))
			return null;
		
		Matcher m=planguage.matcher(question);
		if(m.find())
		{
			for(int i=1;i<=m.groupCount();i++)
			if(m.group(i)!=null)
			{	
				return m.group(i);
			}
		}
		
		
		if(!translationFrom(question).equals("英"))
			return "英";
		else
			return "中";
	}
	
	public static String translationWord(String question)
	{
		if(!hasIntent(question))
			return null;
		//public final static String puctuationReg="~|\\.|\\?|!|'|:|;|,|/|\\(|\\)|&|\"|“|”|。|，|：|！|？|《|》|（|）|\\+";
		//Pattern p=Pattern.compile("(?:\"|&quot;|“)(.+)(?:\"|&quot;|”)");
		
		if(question.matches("翻译(：|:).*"))
			return question.replaceAll("^翻译(：|:)", "").replaceAll("\"|&quot;|“|”", "").trim();
		
		Matcher m=p.matcher(question);
		if(m.find())
		{
			for(int i=1;i<=m.groupCount();i++)
			if(m.group(i)!=null)
			{	
				return m.group(i);
			}
		}
		
		String temp=question.replaceAll("(怎么|如何)(用)?", "").replaceAll("(说|讲|翻译)(啊)?", "").replaceAll("(用|的)?[英法日韩中][语文](的|中|里|应该)?", "").replaceAll(puctuationReg, "").replaceAll("是什么$", "").replaceAll("(什么|啥)意思", "");
		return temp;
	}
	
	public static void main(String[] args)
	{
		String s="Japan中文什么意思";
		if(hasIntent(s))
		{
			System.out.println(translationWord(s));
			System.out.println(translationLanguage(s));
		}
		
	}
}
