import ICTCLAS.I3S.AC.ICTCLAS30;

public class WordSplit {
	
	static ICTCLAS30 testICTCLAS30 = null;
	static void init(){
		try{
			
			testICTCLAS30 = new ICTCLAS30();
			String argu = ".";
			
			if (testICTCLAS30.ICTCLAS_Init(argu.getBytes("GB2312")) == false){
				System.out.println("Init Fail!");
				return;
			}
			/*
			 * 设置词性标注集
		        ID		    代表词性集 
				1			计算所一级标注集
				0			计算所二级标注集
				2			北大二级标注集
				3			北大一级标注集
			 */
			
			testICTCLAS30.ICTCLAS_SetPOSmap(0);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//flag参数说明： 0-不带词性； 1-带词性标注
	public static String split(String paragraph, int flag){
		if(testICTCLAS30 == null)
			init();
		try{
			String trans = new String(paragraph.getBytes(),"GB2312");
			
			byte nativeBytes[] = testICTCLAS30.ICTCLAS_ParagraphProcess(trans.getBytes("GB2312"), flag);
			String nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GB2312");
			return nativeStr;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
