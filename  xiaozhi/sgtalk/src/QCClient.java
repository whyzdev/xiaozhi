
import java.io.BufferedWriter;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class QCClient {

	public static String[] query(String input) {
		try {
			String ip = "60.195.250.60";
			int port = 21779;
			Socket server = new Socket(ip, port);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream() , "utf8"));
			ObjectInputStream in = new ObjectInputStream(server
					.getInputStream());
			out.write(input + "\r\n");
			out.flush();
			String[] infos = (String[]) in.readObject();
			if(infos[0].equals("no suggestion"))
				return null;
			in.close();
			out.close();
			server.close();
			return infos;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		}
		//return null;
	}

	public static void main(String args[]) {
		long start = System.currentTimeMillis();
		
		String ret[] = QCClient.query("什么东西比较好吃");
		System.out.println(ret[0]);
		System.out.println("###");
		System.out.println(ret[1]);
		long end = System.currentTimeMillis();
		double time = (double)(end-start);
		System.out.println(time);
	}
}
