//packagot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Messager;

import org.jivesoftware.smack.RosterEntry;


import net.sf.jml.Email;
import net.sf.jml.MsnContact;
import net.sf.jml.MsnList;
import net.sf.jml.MsnMessenger;
import net.sf.jml.MsnProtocol;
import net.sf.jml.MsnSwitchboard;
import net.sf.jml.MsnUserStatus;
import net.sf.jml.event.MsnAdapter;
import net.sf.jml.impl.MsnContactImpl;
import net.sf.jml.impl.MsnMessengerFactory;
import net.sf.jml.message.MsnControlMessage;
import net.sf.jml.message.MsnDatacastMessage;
import net.sf.jml.message.MsnInstantMessage;
import net.sf.jml.message.MsnSystemMessage;
import net.sf.jml.message.MsnUnknownMessage;

public class MSN extends MsnAdapter {

	private MsnMessenger messenger = null;

	GetXMLDoc rssContent = null;

	private String email = null, password = null;

	/** */
	/** Creates a new instance of TestMSN */
	public MSN() {
	}

	public static void main(String args[]) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		MSN msn = (MSN) Class.forName("MSN")
				.newInstance();// 创建类实例
		msn.setEmail("thaixzhi@gmail.com");// 设置登录用户
		msn.setPassword("tsinghua");// 设置密码
		msn.start();
	}

	// 打印信息
	private static void msg(Object obj) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		//if (obj instanceof Throwable)
			//System.err.println("[" + sdf.format(new Date()) + "] " + obj);
		//else
			//System.out.println("[" + sdf.format(new Date()) + "] " + obj);
	}

	public void start() {
		messenger = MsnMessengerFactory.createMsnMessenger(email, password);// 创建MsnMessenger
		messenger.setSupportedProtocol(new MsnProtocol[] { MsnProtocol.MSNP12 });// 设置登录协议
		messenger.getOwner().setInitStatus(MsnUserStatus.ONLINE);// 设置用户状态
		messenger.getOwner().setInitDisplayName("小智");
		messenger.getOwner().setInitPersonalMessage("我是清华的聊天机器人小智，或许我能帮到你~");
		//messenger.getOwner().setDisplayName("小智");
		//messenger.getOwner().setPersonalMessage("我是清华的聊天机器人小智，或许我能帮到你~");
		messenger.addListener(this);// 注册事件
		messenger.setLogIncoming(true);

        //log outgoing message
        messenger.setLogOutgoing(true);

		messenger.login();// 登录
	}
	
	// 收到正常信息的时候发生
	public void instantMessageReceived(MsnSwitchboard switchboard,
			MsnInstantMessage message, MsnContact contact) {
		String resContent = message.getContent().trim();
		Module m = new Module();
		
		System.out.println("aaaaaaaaaaaaa="+resContent);
		System.out.println("bbbbbbbbbbbbb="+contact.getEmail());
		//message.setContent(DataProcess.GetReply(resContent,contact.toString()));
		message.setContent(DataProcess.GetReply(resContent,contact.getEmail().toString()));
		/*
		if (resContent.startsWith("天气 ")) {
			message.setContent(m.getWeather(resContent.substring(resContent
					.indexOf(" ") + 1)));
		} else 
		*/
		//message.setFontRGBColor((int) (Math.random() * 255 * 255 * 255));// 设置消息的文本颜色
		
		switchboard.sendMessage(message);// 发送信息
		savehistory(contact.getDisplayName(),resContent,message.getContent().trim());
	}

	// 收到系统信息的时候发生，登录时
	public void systemMessageReceived(MsnMessenger messenger,
			MsnSystemMessage message) {
		msg(messenger + " recv system message " + message);
	}
	
	//收到加为好友信息的时候发生
	public void contactAddedMe(MsnMessenger messenger,
			MsnContact contact) {
		 msg(contact.getDisplayName() + "add me into friend list");
		 messenger.addFriend(contact.getEmail(), contact.getDisplayName());
		 UserData.addData(contact.getEmail().toString());
	}
	//	收到加为好友成功的时候发生
	public void contactAddCompleted(MsnMessenger messenger,
			MsnContact contact) {
		 msg(contact.getDisplayName() + "add me into friend list completed!");
		 messenger.addFriend(contact.getEmail(), contact.getDisplayName());
	}	

	// 当在联系人聊天窗口获得光标并按下第一个键时发生
	public void controlMessageReceived(MsnSwitchboard switchboard,
			MsnControlMessage message, MsnContact contact) {
		msg(contact.getFriendlyName() + "正在输入文字。");
	}

	// 异常时发生
	public void exceptionCaught(MsnMessenger messenger, Throwable throwable) {
		msg(messenger + throwable.toString());
		msg(throwable);
	}

	// 登录完成时发生
	public void loginCompleted(MsnMessenger messenger) {
		msg(messenger.getOwner().getDisplayName() + "登录成功！");
		// messenger.getOwner().setDisplayName("天天持之以恒");
		//MsnSwitchboard switchboard = null;
		//MsnInstantMessage message = new MsnInstantMessage();
		//message.setContent("Hello,I'm robot designed by tsinghua! what can i help you? ");
		//switchboard.sendMessage(message);
		
	}
	
	public void ownerStatusChanged(MsnMessenger messenger) {
		//List emaillist = null;
		//MsnContact[] cons = this.messenger.getContactList().getContactsInList(MsnList.AL);
		//emaillist = new ArrayList();
		//for (int i = 0; i < cons.length; i++){
		//	emaillist.add(cons[i].getEmail());
		//}
		//for (int j = 0; j < emaillist.size(); j++){
		//	messenger.sendText((Email) emaillist.get(j), "Hi,tsinghua bot login in ,can i help you?");
		//}
		//msg(messenger.getOwner().getDisplayName() + "状态改变！");
    }
	//public void contactStatusChanged(MsnMessenger messenger, 
    //        MsnContact contact) {
		//messenger.sendText((Email) contact.getEmail(), "你好，我是小M，来自清华，有什么需要帮助的呢？\n" +
		//		"我现在能帮你\n" +
		//		"=============\n" +
		//		"1.查询天气：     请输入  天气+中文空格+主要城市名\n" +
		//		"2.查询ip：	      请输入   ip+中文空格+任意ip地址\n" +
		//		"3.查询手机号：请输入  手机+中文空格+中国手机号（11位）\n" +
		//		"4.查询身份证：请输入   身份证+中文空格+合法的身份证号" );
		//List emaillist = null;
		//MsnContact[] cons = this.messenger.getContactList().getContactsInList(MsnList.AL);
		//for (int i = 0; i < cons.length; i++){
		//	emaillist.add(cons[i].getEmail());
		//}
		//for (int j = 0; j < emaillist.size(); j++){
		//	messenger.sendText((Email) emaillist.get(j), "Hi,tsinghua bot login in ,can i help you?");
		//}
	//}
	
	// 注销时发生
	public void logout(MsnMessenger messenger) {
		//messenger = MsnMessengerFactory.createMsnMessenger(email, password);// 创建MsnMessenger
		//messenger.setSupportedProtocol(new MsnProtocol[] { MsnProtocol.MSNP12 });// 设置登录协议
		//messenger.getOwner().setInitStatus(MsnUserStatus.ONLINE);// 设置用户状态
		//messenger.addListener(this);// 注册事件
		//messenger.login();// 登录
		//msg(messenger + " logout");
	}

	// 收到系统广播信息时发生
	public void datacastMessageReceived(MsnSwitchboard switchboard,
			MsnDatacastMessage message, MsnContact friend) {
		msg(switchboard + " recv datacast message " + message);
		switchboard.sendMessage(message, false);
	}

	// 收到目前不能处理的信息时发生
	public void unknownMessageReceived(MsnSwitchboard switchboard,
			MsnUnknownMessage message, MsnContact friend) {
		// msg(switchboard + " recv unknown message " + message);
	}

	public void contactListInitCompleted(MsnMessenger messenger) {
		listContacts();
		// rssContent.setMessager(messenger);
		// TestSendMsg.sendMsg(messenger);
		MsnContact[] cons = messenger.getContactList().getContactsInList(
				MsnList.AL);
		try {
			UserData.LoadData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (MsnContact con : cons) {
        		UserData.addData(con.getEmail().toString());
		}
	}

	/**
	 * 更新好友列表完成时发生
	 */
	public void contactListSyncCompleted(MsnMessenger messenger) {
		listContacts();
		// rssContent.setMessager(messenger);
		//TestSendMsg.sendMsg(messenger);
	}

	/** */
	/**
	 * 关闭一个聊天窗口时发生
	 */
	public void switchboardClosed(MsnSwitchboard switchboard) {
		msg("switchboardStarted " + switchboard);
	}

	/** */
	/**
	 * 打开一个聊天窗口时发生
	 */
	public void switchboardStarted(MsnSwitchboard switchboard) {
		msg("switchboardStarted " + switchboard);
	}

	// 打印联系人
	private void listContacts() {
		MsnContact[] cons = messenger.getContactList().getContactsInList(
				MsnList.AL);
		if (cons.length == 0)
			msg("空");
		else
			msg("你现在有" + cons.length + "个联系人");
		for (int i = 0; i < cons.length; i++) {
			String personal = ((MsnContactImpl) cons[i]).getPersonalMessage()
					.equals("") ? "空" : ((MsnContactImpl) cons[i])
					.getPersonalMessage();
			msg(cons[i].getDisplayName() + " " + cons[i].getEmail() + " "
					+ cons[i].getStatus() + " " + personal);
		}
	}

	/**
	 * 获得联系人的列表
	 * 
	 * @return
	 */
	public List getContactsList() {
		List list = null;
		MsnContact[] cons = messenger.getContactList().getContactsInList(
				MsnList.AL);
		if (cons.length == 0)
			return null;
		else {
			list = new ArrayList();
			for (int i = 0; i < cons.length; i++)
				list.add(cons[i].getEmail());
		}
		return list;
	}

	public void sendMsg(List list) {
		Map rss = null;
		List email = getContactsList();
		for (int j = 0; j < email.size(); j++) {
			for (int i = 0; i < list.size(); i++) {
				rss = (Map) list.get(i);
				String content = rss.get("title") + "\r" + rss.get("content")
						+ "\r" + rss.get("link") + "\r" + rss.get("time");
				messenger.sendText((Email) email.get(j), content);
			}
		}
	}

	public MsnMessenger getMessager() {
		return this.messenger;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public static void savehistory(String from, String query, String reply) {
		
		try { 
			BufferedWriter out = new BufferedWriter(new FileWriter("history.txt", true)); 
			out.write(from+":"+query+"\n");
			out.newLine();
			out.write("thaixzhi:"+reply+"\n");
			out.newLine();
			out.newLine();
			out.close(); 
		} catch (IOException e) { } 
		
	}
}
