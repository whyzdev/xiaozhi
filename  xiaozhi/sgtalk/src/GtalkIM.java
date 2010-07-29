import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.Roster.SubscriptionMode;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.Roster;

//Gtalk客户端程序,主程序
public class GtalkIM {
	static GtalkIM gtalkim;
	static ChatManager chatmanager;
	static XMPPConnection connection;
	static Roster roster;
	static Chat chat;
	static UserData userdata;
	static Queue<Chat> chatlist;
	
    class LetRun{
    	Chat chatt;
    	Message message;
    	
    	public void startThread(Chat ct,Message mg){
    		chatt=ct;
    		message=mg;
    		Thread sendmsg=new Thread(this.new ReplyThread());
			sendmsg.start();
    	}
	
		class ReplyThread implements Runnable{
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				 try {
	              	
	              	  Message msg = new Message(message.getFrom(), Message.Type.chat);
	            
	                  msg.setBody(DataProcess.GetReply(message.getBody(),message.getFrom()));
	                  chatt.sendMessage(msg);
	                  
	                  savehistory(message.getFrom(),message.getBody(),msg.getBody());
	                  //System.out.println("发送"+message.getFrom()+msg.getBody());
	                  System.out.println("发送"+message.getFrom()+msg.getBody());
	                 
	              } catch (XMPPException ex) {
	                  //System.out.println("发送消息失败");
	              }
				
			}
			
		}
    }
	
	//信息监听
    public static class MListener implements MessageListener {
    	//收到信息时
        public void processMessage(Chat chat, Message message) {
            if(message.getType().equals(Message.Type.chat) && message.getBody() != null) {
                /*
            	msgque.add(message);
                chatque.add(chat);
                */
            	LetRun lr=gtalkim.new LetRun();
            	lr.startThread(chat, message);
                System.out.println(message.getFrom()+": " + message.getBody());
            	
            } else {
            	//System.out.println(message.getType());
            	//System.out.println(message.getBody());
                System.out.println("不能辨识信息");
            }
        }
   
    }
    
    
    
    //状态监听
    public static class Rlistener implements RosterListener {
    	
		@Override
		public void entriesAdded(Collection<String> arg0) {
		
			for(String s:arg0)
			{
				System.out.println(s);
				Message msg = new Message(s,Message.Type.chat);
                msg.setBody("hi");
                try {
					chat.sendMessage(msg);
					//System.out.println("Message sent:"+msg.getBody());
				} catch (XMPPException e) {
			
					e.printStackTrace();
				}
				
				
			}
			Collection<RosterEntry> entries = roster.getEntries();
			chatlist.clear();
	        for (RosterEntry entry : entries) {
	        	
	        	if(UserData.getIndex(entry.getUser())==-1){
	        		
	        		UserData.addData(entry.getUser());
	        	}
	        	
	            chatlist.add(chatmanager.createChat(entry.getUser(), new MListener()));
	        
	        }
		}

		@Override
		public void entriesDeleted(Collection<String> arg0) {
			/*
			for(String s:arg0){
				//System.out.println("deleted:"+s);
			}
			*/
		}

		@Override
		public void entriesUpdated(Collection<String> arg0) {
			/*
			for(String s:arg0){
				//System.out.println("updated:"+s);
			}
			*/
			//更新时重新打开所有chat,避免程序无反应
			Collection<RosterEntry> entries = roster.getEntries();
			for (RosterEntry entry : entries) {
	        	
	        	if(UserData.getIndex(entry.getUser())==-1){
	        		
	        		UserData.addData(entry.getUser());
	        	}
	        	
	        	//System.out.println(entry.getUser());
	        	chatlist.add(chatmanager.createChat(entry.getUser(), new MListener()));
	            
	        	//roster.removeEntry(entry);
	        }
			
		}

		@Override
		public void presenceChanged(Presence arg0) {
			
			
		}

		
    	
    }
    
    public static void savehistory(String from, String query, String reply) {
		
		try { 
			BufferedWriter out = new BufferedWriter(new FileWriter("history.txt", true)); 
			out.write(from+":"+query);
			out.newLine();
			out.write("thaixzhi:"+reply);
			out.newLine();
			out.newLine();
			out.close(); 
		} catch (IOException e) { } 
		
	}
  
    //主函数
    public static void main( String[] args ) throws XMPPException, IOException, ClassNotFoundException {
        
        chatlist=new LinkedList<Chat>();
        
        gtalkim=new GtalkIM();
        
        System.out.println("开始连接");
        //连接gtalk
        ConnectionConfiguration connConfig = new ConnectionConfiguration("talk.google.com", 5222, "gmail.com");
        connection = new XMPPConnection(connConfig);
        try {
            connection.connect();
            System.out.println("成功连接");
        } catch (XMPPException ex) {
            System.out.println("连接失败");
            System.exit(1);
        }
        
        //登陆gtalk
        
        try {
            connection.login("thaixzhi@gmail.com", "tsinghua");
            System.out.println("登陆为" + connection.getUser());
            
            Presence presence = new Presence(Presence.Type.available);
            connection.sendPacket(presence);
            
        } catch (XMPPException ex) {
            //ex.printStackTrace();
            System.out.println("登陆失败 " );
            System.exit(1);
        }
       
        chatmanager = connection.getChatManager();
        roster=connection.getRoster();
        roster.addRosterListener(new Rlistener());
        roster.setSubscriptionMode(SubscriptionMode.accept_all);
       
        Collection<RosterEntry> entries = roster.getEntries();
        UserData.LoadData();
        
        //----------------------------------
        chatlist.clear();
        for (RosterEntry entry : entries) {
        	
        	if(UserData.getIndex(entry.getUser())==-1){
        		
        		UserData.addData(entry.getUser());
        	}
        	
        	chatlist.add(chatmanager.createChat(entry.getUser(), new MListener()));
        	
        }
      
       //Message message;
       int key; 
       //int count=0;
       
        while(true)
        {
        	
        	//TODO msg send
        	/*
        	if(!msgque.isEmpty()){
        		//System.out.println("here");
        		message=msgque.element();
        		chat=chatque.element();
        		 try {
                 	
                 	Message msg = new Message(message.getFrom(), Message.Type.chat);
                 	//System.out.println(message);
                     msg.setBody(DataProcess.GetReply(message.getBody(),message.getFrom()));
                     chat.sendMessage(msg);
                     savehistory(message.getFrom(),message.getBody(),msg.getBody());
                     System.out.println("发送"+message.getFrom()+msg.getBody());
                     msgque.remove();
                     chatque.remove();
                 } catch (XMPPException ex) {
                     System.out.println("发送消息失败");
                 }
        	}
        	
        	try {
        		if(msgque.isEmpty()){
        			Thread.sleep(10);
        			count++;
        		}
        		if(count==100){
        			count=0;
        			
        			
        			 chatlist.clear();
        		        for (RosterEntry entry : entries) {
        		        	
        		        	if(UserData.getIndex(entry.getUser())==-1){
        		        		
        		        		UserData.addData(entry.getUser());
        		        	}
        		        	
        		        	//System.out.println(entry.getUser());
        		            //chat = chatmanager.createChat(entry.getUser(), new MListener());
        		            chatlist.add(chatmanager.createChat(entry.getUser(), new MListener()));
        		        	//roster.removeEntry(entry);
        		        }
        		}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
        	/*
        	int count = 0;
			
        		try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		count++;
        		if(count==100){
        			count=0;
        			
        			chatlist.clear();
     		        for (RosterEntry entry : entries) {
     		        	
     		        	if(UserData.getIndex(entry.getUser())==-1){
     		        		
     		        		UserData.addData(entry.getUser());
     		        	}
     		        	
     		        	//System.out.println(entry.getUser());
     		            //chat = chatmanager.createChat(entry.getUser(), new MListener());
     		            chatlist.add(chatmanager.createChat(entry.getUser(), new MListener()));
     		        	//roster.removeEntry(entry);
     		        }
        			
        			
        			
        		}
        		else if (count>200){
        			break;
        		}
			
			
			
			
			
			
			
			
        	*/
        	key=System.in.read();
        	//System.out.println("key:"+key);
        	if(key==113){
        		//key==q
        		//退出程序
        	
        		break;
        	}
        	else{
        		switch(key){
        		case 114:
        			//key==r
        			
        			//重置连接
        			
            	
        			for (RosterEntry entry : entries) {
        	        	
        	        	if(UserData.getIndex(entry.getUser())==-1){
        	        		
        	        		UserData.addData(entry.getUser());
        	        	}
        	        	
        	        	//System.out.println(entry.getUser());
        	        	chatlist.add(chatmanager.createChat(entry.getUser(), new MListener()));
        	            
        	        	//roster.removeEntry(entry);
        	        }
        			break;
        		case 108:
        			//key==l
        			
        			//列表
        			for (RosterEntry entry : entries) {
        	        	
        	        	if(UserData.getIndex(entry.getUser())==-1){
        	        		
        	        		UserData.addData(entry.getUser());
        	        	}
        	        	
        	        	//System.out.println(entry.getUser());
        	            
        	        }
        			break;
        		case 115:
        			//key==s
        			//保存
        			UserData.SaveData();
        			break;
        		
        		}
        		
        	
        	
        }
        
        
        //UserData.SaveData();
       // connection.disconnect();  
        
    }


	
    }}
