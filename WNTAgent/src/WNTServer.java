import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class WNTServer {
	public static void StartServer() {

			ServerSocket server = null;
			try {
				server = new ServerSocket(15233);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("start Server");
			while(true){
				BufferedReader in = null;
				PrintWriter out = null;
				Socket client = null;
				try {
					client = server.accept();
					//BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream() , "utf8"));
					out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(),"utf-8"));
					in = new BufferedReader(new InputStreamReader(client.getInputStream(),"utf-8"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						
				String query = null;
				try {
					query = (String) in.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String answer=null;
				answer=MainAgent.GetReply(query);
				
				
				out.println(answer);
				out.flush();
				
				try {
					client.close();
					in.close();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//System.out.println("stop Server");
			
		
	}

	public static void main(String args[]) {
		
		StartServer();
	}
}
