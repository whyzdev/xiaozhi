//package com.lee.kooqi.mybot;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.Perl5Compiler;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * @author leo
 * 
 */
public class GetXMLDoc extends Thread {

	public Date lastDate = null;

	MSN msn = null;

	public List mmList = null;

	GetResource resource = null;

	/**
	 * 
	 */
	public GetXMLDoc() {
		super();
		resource = new GetResource();
	}

	public void run() {
		synchronized (this) {
			while (resource.getIsSendNews()) {
				try {
					sleep(1000 * 1 * 60);
					System.out
							.println("￥￥￥￥￥￥￥￥￥￥￥￥￥线程开始￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥");
					List li = getRssNew();
					System.out.println("有 " + li.size() + " 条新信息！");
					lastDate = getCurrentDate();
					System.out.println("开始扫描时间：" + lastDate.toLocaleString());
					if (li.size() != 0)
						sendMessage(li);
					resource = (GetResource) Class.forName(
							"com.lee.kooqi.mybot.GetResource").newInstance();
					sleep(1000 * 5 * 60);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// TODO 自动生成构造函数存根
	}

	void startMSN() {
		try {
			msn = (MSN) Class.forName("com.lee.kooqi.mybot.TestMSN")
					.newInstance();// 创建类实例
			msn.setEmail(resource.getLoginName());// 设置登录用户
			msn.setPassword(resource.getPassword());// 设置密码
			msn.start();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		GetXMLDoc gxd = new GetXMLDoc();
		gxd.lastDate = gxd.getCurrentDate();
		gxd.startMSN();
		gxd.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adgetter.leo.net.GetPageImp#readPage(java.lang.String,
	 *      java.lang.String)
	 */
	public Document readPage(String urlstr, String CharSet) {
		// StringBuffer buffer = new StringBuffer();
		Document doc = null;
		URL url = null;
		HttpURLConnection httpURLConnection = null;
		java.io.InputStream urlStream = null;

		try {
			url = new URL(urlstr);
			// /打开连接
			httpURLConnection = (HttpURLConnection) url.openConnection();
			// 判断请求页面是否存在
			if (isAvriable(httpURLConnection)) {
				// 连接到远程连接
				httpURLConnection.connect();
				// 打开输入流
				urlStream = httpURLConnection.getInputStream();
				// /将输入流导入到缓冲流读取
				// java.io.BufferedReader urlreader = new
				// java.io.BufferedReader(
				// new java.io.InputStreamReader(urlStream, CharSet)); //
				// /读取静态页面内容并且转换字符串
				// String returnStr = null;
				// // 以字符缓冲的形式逐行读取加载到内存
				// while ((returnStr = urlreader.readLine()) != null) {
				// buffer.append(returnStr);
				// }
				// // 返回读取的原始内容
				// return buffer.toString();
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				doc = builder.parse(urlStream);
			}
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("流操作错误：IOException " + e.toString());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("其他错误：IOException " + e.toString());
		}
		return doc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adgetter.leo.net.GetPageImp#isAvriable(java.net.HttpURLConnection)
	 */
	public boolean isAvriable(HttpURLConnection connection) {
		// TODO 自动生成方法存根
		boolean confirm = true;
		try {
			// 请求的页面不存在
			if (connection.getResponseCode() / 100 == 4) {
				System.out.println("404 ERROR，您请求的页面不存在！");
				confirm = false;
			}
			// 内部服务器错误
			else if (connection.getResponseCode() / 100 == 5) {
				System.out.println("505 ERROR，内部服务器错误，请稍后访问！");
				confirm = false;
			}
			// 页面定向错误
			else if (connection.getResponseCode() / 100 == 3) {
				System.out.println("303 ERROR，页面重定向错误，请稍后访问！");
				confirm = false;
			}
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("流操作错误：IOException " + e.toString());
		}

		return confirm;
	}

	/**
	 * 获得最新的咨询内容
	 * 
	 * @return
	 */
	public List getRssNew() {
		List rs = new ArrayList();
		Map rss = null;

		Document doc = readPage("http://www.cnbeta.com/backend.php", "gb2312");
		NodeList nl = doc.getElementsByTagName("item");
		for (int i = 0; i < nl.getLength(); i++) {
			String title, content, link, time;
			time = doc.getElementsByTagName("pubDate").item(i + 1)
					.getFirstChild().getNodeValue();
			if (compareDate(convertTime(time.trim()), lastDate)) {
				System.out.println("#################:" + convertTime(time));
				title = doc.getElementsByTagName("title").item(i + 2)
						.getFirstChild().getNodeValue();
				content = doc.getElementsByTagName("description").item(i + 1)
						.getFirstChild().getNodeValue();
				link = doc.getElementsByTagName("link").item(i + 2)
						.getFirstChild().getNodeValue();
				rss = new HashMap();
				rss.put("title", title);
				rss.put("content", replaceContent(content));
				rss.put("link", link);
				rss.put("time", convertTime(time.trim()).toLocaleString()
						+ "   By http://www.icnote.com/");
				rs.add(rss);
			} else
				break;
		}
		return rs;
	}

	/**
	 * 聚合内容处理
	 * 
	 * @param content
	 * @return
	 */
	public String replaceContent(String content) {
		if (content.trim().startsWith("<strong>")) {
			int a = content.indexOf("<br />");
			content = content.substring(a + 6);
		}
		content = content.replaceAll("<span style=\"font-weight: bold;\">", "")
				.replaceAll("</span>", "");
		content = content.replaceAll("\"", "'");
		content = content.replaceAll("<strong>", "")
				.replaceAll("</strong>", "");
		content = content.replaceAll("<br />", "\r");
		content = content.replaceAll("&mdash;", "");
		content = content.replaceAll("<p>", "");
		content = content.replaceAll("</p>", "。");
		content = content.replaceAll("<div>", "");
		content = content.replaceAll("</div>", "。");
		content = content.replaceAll("&ldquo;", "");
		content = content.replaceAll("&rdquo;", "");
		content = content.replaceAll("&middot;", "");
		content = content.replaceAll("&hellip;", "");
		content = content.replaceAll("&micro;", "");
		content = content.replaceAll("<br style='font-weight: bold;' />", "");
		content = content.replaceAll("&quot;", "");
		content = content.replaceAll("&nbsp;", "");
		content = content.replaceAll("<div style='font-size: 12px;'>", "");
		content = content.replaceAll(
				"<div id='message826214' class='t_msgfont'>", "");
		return content;
	}

	/**
	 * 日期转换
	 * 
	 * @param time
	 * @return
	 */
	Date convertTime(String time) {
		Date date = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
			date = format.parse(time.trim());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 比较日期
	 * 
	 * @param src1
	 * @param src2
	 * @return
	 */
	boolean compareDate(Date src1, Date src2) {
		boolean flag = false;
		if (src1.after(src2)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 获得当前的时间
	 * 
	 * @return
	 */
	Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 发送消息
	 * 
	 * @param list
	 */
	void sendMessage(List list) {
		if (list.size() == 0)
			return;
		else {
			msn.sendMsg(list);
		}
	}
}
