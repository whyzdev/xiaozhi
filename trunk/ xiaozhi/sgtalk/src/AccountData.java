import java.util.Vector;


public class AccountData{
	String name;
	String place;
	boolean weather;
	String lastquestion;
	Vector<String> answerset;
	int index;
	
	public AccountData(String name2) {
		name=name2;
		place="北京";
	}
		
	public AccountData() {
		
	}
	public void SaveQuery(String lquestion,String ans){
		//TODO 处理anserset里面的种类提取
		//System.out.println("here x");
		String temp="";
		lastquestion=lquestion;
		//ans=ans.replaceAll("还是"," ");
	    //ans=ans.replaceAll("是"," ");
	    ans=ans.replaceAll("\t"," ");
	    ans=ans.replaceAll("\r\n"," ");
	    ans=ans.replaceAll(","," ");
	    ans=ans.replaceAll("  "," ");
	    ans=ans.replaceAll("  "," ");
	    ans=ans.replaceAll("  "," ");
	    answerset=new Vector<String>();
	    int count=-1;
	    //System.out.println(ans);
	    for(int i=0;i<ans.length();i++)
	    {
	    	//System.out.println(ans);
	    	
	    	if(ans.substring(i, i+1).equals(" ") ){
	    		count++;
	    		if(temp!=null){
	    			answerset.add(temp);
	    			//System.out.println("add ans:"+temp);
	    			
	    		}
	    		temp="";
	    	}
	    	else{
	    		temp=temp+ans.substring(i, i+1);
	    	}
	    	
	    		
	    }
	    //System.out.println("here z");
	}
	
	public String GetQuery(String ans){
		String question = null;
		//TODO 寻找答案集中是否拥有该答案
		return question;
	}
	
	public void clearquery(){
		lastquestion=null;
		answerset=null;
	}
	public void SetPlace(String p){
		place=p;
	}
	public String GetPlace(){
		return place;
	}
	public boolean GetWeather(){
		return weather;
	}
	public void SetWeather(boolean a){
		weather=a;
	}
	
}