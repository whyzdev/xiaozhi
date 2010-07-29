import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!
public class ChatDtbase {
	static Connection conn=null;
	public static String CheckDatabase(String question){
		String ans=null;
		try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }
        
        try {
            conn =
            DriverManager.getConnection("jdbc:mysql://localhost/chatbot","xiaozhi","tsinghua");
            //DriverManager.getConnection("jdbc:mysql://localhost/chatbot","root","");
            // Do something with the Connection
       
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        
        try {
        	Statement stmt=conn.createStatement(ResultSet.CONCUR_READ_ONLY,ResultSet.TYPE_SCROLL_SENSITIVE);
        	stmt.execute("use chatbot");
        	//System.out.println("use chatbot");
			
			ResultSet srs = stmt.executeQuery("SELECT * from direct where question = '"+new String(question.getBytes("gbk"),"ISO-8859-1")+"' order by rand() limit 1");
        	//ResultSet srs = stmt.executeQuery("SELECT * from direct where question = '"+question+"' order by rand() limit 1");
			if(srs.next()){
				ans = srs.getString("answer");
			}
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String ans1 = null;
		try {
			if(ans!=null && !ans.equals("")){
				ans1=new String(ans.getBytes("ISO-8859-1"),"gbk");
				//ans1=ans;
			}
		} 
		catch(Exception e){
			
		}
		/*
		 catch (UnsupportedEncodingException e) {
		 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return ans1;
	}
	
	public static String GetEmoticon(){
		String ans=null;
		try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }
        
        try {
            conn =
               DriverManager.getConnection("jdbc:mysql://localhost/chatbot","xiaozhi","tsinghua");
            // Do something with the Connection
       
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        
        try {
        	Statement stmt=conn.createStatement(ResultSet.CONCUR_READ_ONLY,ResultSet.TYPE_SCROLL_SENSITIVE);
        	stmt.execute("use chatbot");
        	//System.out.println("use chatbot");
			
			ResultSet srs = stmt.executeQuery("SELECT * from emoticons order by rand() limit 1");
			if(srs.next()){
				ans = srs.getString("emoticons");
			}
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ans;
	}
	
    
}

