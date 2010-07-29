import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class WNTClient {

	/**
	 * @param args
	 */
	
	public static String GetWNT(String inputstring){
		String outputstring=null;
		//System.out.println("here1");
		try {
			Socket server = new Socket("60.195.250.61",15233);
			//Socket server = new Socket("127.0.0.1",15233);
			PrintWriter out = new PrintWriter(new OutputStreamWriter(server.getOutputStream(),"utf-8"),true);
			BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream(),"utf-8"));
			
			//System.out.println("here2");
			out.println(inputstring);
			out.flush();
			//System.out.println("here3");
			String temp;
			while((temp=in.readLine() )!= null){
				if(outputstring==null){
					outputstring=temp;
				}
				else{
					outputstring=outputstring+"\r\n"+temp;
				}
			}
			//outputstring=in.readLine();
			//System.out.println(outputstring);			
			//System.out.println("here4");
			
		} catch (Exception e) {
			e.printStackTrace(System.out);
			
		}
		return outputstring;
		
		
		
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(GetWNT("新闻"));
	}

}
