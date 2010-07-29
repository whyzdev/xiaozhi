
public class TranslateManager {

	/**
	 * @param args
	 */
	public static String GetTranslate(String inputstring){
		String outputstring=null;
		if(TranslateIntentClassifier.hasIntent(inputstring)){
			outputstring=GoogleTranslate.translate(TranslateIntentClassifier.translationWord(inputstring),TranslateIntentClassifier.translationLanguage(inputstring),TranslateIntentClassifier.translationFrom(inputstring));
		}
		return outputstring;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
