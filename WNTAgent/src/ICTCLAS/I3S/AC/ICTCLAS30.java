package ICTCLAS.I3S.AC;

public class ICTCLAS30 {
  
		public native   boolean ICTCLAS_Init(byte[] sPath);
		public native   boolean ICTCLAS_Exit();
		public native   int ICTCLAS_ImportUserDict(byte[] sPath);
		public native float ICTCLAS_GetUniProb(byte[] sWord);
		public native boolean ICTCLAS_IsWord(byte[] sWord);
		public native   byte[] ICTCLAS_ParagraphProcess(byte[] sSrc,int bPOSTagged);
		public native   boolean ICTCLAS_FileProcess(byte[] sSrcFilename,byte[] sDestFilename,int bPOSTagged);

		public native   byte[] nativeProcAPara(byte[] src);

	
/*********************************************************************
*
*  Func Name  : ICTCLAS_AddUserWord
*
*  Description: add a word to the user dictionary ,example:���	
*													 i3s	n
*
*  Parameters : sFilename: file name
*               
*  Returns    : 1,true ; 0,false
*
*  Author     :   
*  History    : 
*              1.create 11:10:2008
*********************************************************************/
public native int ICTCLAS_AddUserWord(byte[] sWord);//add by qp 2008.11.10



/*********************************************************************
*
*  Func Name  : Save
*
*  Description: Save dictionary to file
*
*  Parameters :
*               
*  Returns    : 1,true; 2,false
*
*  Author     :   
*  History    : 
*              1.create 11:10:2008
*********************************************************************/
public native int ICTCLAS_SaveTheUsrDic();

/*********************************************************************
*
*  Func Name  : ICTCLAS_DelUsrWord
*
*  Description: delete a word from the  user dictionary
*
*  Parameters : 
*  Returns    : -1, the word not exist in the user dictionary; else, the handle of the word deleted
*
*  Author     :   
*  History    : 
*              1.create 11:10:2008
*********************************************************************/
public native int ICTCLAS_DelUsrWord(byte[] sWord);

/*********************************************************************
*
*  Func Name  : ICTCLAS_KeyWord
*
*  Description: Extract keyword from paragraph
*
*  Parameters : resultKey, the returned key word 
				nCountKey, resultKey���ܴ��reslut_t�ĸ���
*  Returns    : 0, failed; else, 1, successe
*  ��ע���ú�����nativeProcAPara�����,resultKey���ڴ��СӦ�ô��ڻ����nativeProcAPara�ķ��ؽ��nCountKeyΪresultKey/ICTCLAS_GetElemLength(0)
*  Author     :   
*  History    : 
*              1.create 11:10:2008
*********************************************************************/
public native int ICTCLAS_KeyWord(byte[] resultKey, int nCountKey);

/*********************************************************************
*
*  Func Name  : ICTCLAS_FingerPrint
*
*  Description: Extract a finger print from the paragraph
*
*  Parameters :
*  Returns    : 0, failed; else, the finger print of the content
*
*  Author     :   
*  History    : 
*              1.create 11:10:2008
*********************************************************************/
public native  long ICTCLAS_FingerPrint();

/*********************************************************************
*
*  Func Name  : ICTCLAS_SetPOSmap
*
*  Description: select which pos map will use
*
*  Parameters :nPOSmap, ICT_POS_MAP_FIRST  ������һ����ע��
						ICT_POS_MAP_SECOND  �����������ע��
						PKU_POS_MAP_SECOND   ���������ע��
						PKU_POS_MAP_FIRST 	  ����һ����ע��
*  Returns    : 0, failed; else, success
*
*  Author     :   
*  History    : 
*              1.create 11:10:2008
*********************************************************************/
public native int ICTCLAS_SetPOSmap(int nPOSmap);

/*********************************************************************
*
*  Func Name  : ICTCLAS_SetPOSmap
*
*  Description: ��ȡ�ṹ��result_t��ÿ������ռ���ڴ��С
*
*  Parameters :nIndex, ��ȡ��nIndex������ռ���ڴ��С������GetElemLength(1),Ϊ��ȡresult_t.length��ռ���ڴ��С
*  Returns    :ռ���ڴ�Ĵ�С
*	��ע��1.�����ڴ��������⣬ÿ������ʵ��ռ���ڴ�Ĵ�С��ȷ����
 *		  2.ICTCLAS_GetElemLength(0)��ȡ�ṹ��result_t�Ĵ�С
*  Author     :   
*  History    : 
*             
*********************************************************************/

public native int ICTCLAS_GetElemLength(int nIndex);

    /* Use static intializer */
    static {
    	//System.out.println(System.getenv());
    		
			System.loadLibrary("ICTCLAS30");
			
    }
}