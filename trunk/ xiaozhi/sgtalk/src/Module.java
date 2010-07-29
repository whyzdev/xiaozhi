//package com.lee.kooqi.mybot;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * @author leo
 * 
 */
public class Module {

	private final String urlIp = "http://www.123cha.com/ip/?q=";

	private final String urlIdcard = "http://www.123cha.com/idcard/";

	private final String urlPhone = "http://www.123cha.com/ip/?q=";

	private final String urlWeather = "http://php.weather.sina.com.cn/search.php?city=";

	/**
	 * 根据url获得返回的信息
	 * 
	 * @param urlIn
	 * @return
	 */
	private String getResponse(String urlIn, String CharSet) {
		String result = null;
		URL url = null;
		HttpURLConnection httpURLConnection = null;
		java.io.InputStream urlStream = null;
		StringBuffer buffer = new StringBuffer();
		try {
			url = new URL(urlIn);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.connect();
			urlStream = httpURLConnection.getInputStream();
			java.io.BufferedReader urlreader = new java.io.BufferedReader(
					new java.io.InputStreamReader(urlStream, CharSet)); // /读取静态页面内容并且转换字符串
			String returnStr = null;
			while ((returnStr = urlreader.readLine()) != null) {
				buffer.append(returnStr);
			}
			result = buffer.toString();
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String getArea(String ip) {
		String urlStr = urlIp + ip;
		String tmp = getResponse(urlStr, "utf-8");
		String tmp1 = getResponse(urlStr, "utf-8");
		try{
		if (ip.split("\\.").length != 4)
			tmp1 = "您的输入不正确！";
		else {
			//tmp = tmp.substring(tmp.indexOf("<br>[本站主数据]: ") + 13, tmp
			//		.indexOf("<br>[本站辅数据]: "));
			tmp1 = tmp1.substring(tmp1.indexOf("<br>[参考数据一]: "), tmp1
					.indexOf("[查询提供]:"));
			//tmp = tmp + "\n" + tmp1;
			tmp1 = tmp1.replaceAll("&nbsp;", " ");
			tmp1 = tmp1.replaceAll("<br>", " ");
			if ("".equals(tmp1) || tmp1 == null)
				tmp1 = "对不起，您的输入不正确，暂时没有您需要的数据！";
			else
				tmp1 = "IP " + ip + " 的详细信息：\n" + tmp1;
		}
		return tmp1;
		}
		catch(Exception e){
			tmp1 = "对不起，您的输入不正确，暂时没有您需要的数据！";
			return tmp1;
		}
	}

	public String getPhone(String Phone) {
		String urlStr = urlPhone + Phone;
		String tmp = getResponse(urlStr, "utf-8");
		try{
		if (Phone.length() != 11)
			tmp = "您的输入不正确！";
		else {
			tmp = tmp.substring(tmp.indexOf("<br>[其他参考数据]: ") + 13, tmp
					.indexOf("<br>[查询提供]"));
			tmp = tmp.replaceAll("&nbsp;", " ");
			tmp = tmp.replaceAll("<br>", " ");
			if ("".equals(tmp) || tmp == null ||"  ".equals(tmp))
				tmp = "对不起，您的输入不正确，暂时没有您需要的数据！";
			else
				tmp = "手机号码 " + Phone + " 的详细信息：\n" + tmp;
		}
		return tmp;
		}
		catch(Exception e){
			tmp = "对不起，您的输入不正确，暂时没有您需要的数据！";
			return tmp;
		}
	}

	public String getIDCard(String card) {
		String urlStr = urlIdcard + card + "/";
		String tmp = getResponse(urlStr, "utf-8");
		try{
		if (card.length() < 15 || card.length() > 18)
			tmp = "您的输入不正确！";
		else {
			tmp = tmp
					.substring(
							tmp.indexOf("出生日期"),
							tmp
									.indexOf("判断参考"));
			tmp = tmp.replaceAll(
					"</td><td align=center bgcolor=#ffffff width=240>", ":");
			tmp = tmp.replaceAll(
					"</td><td align=center bgcolor=#f6f6f6 width=80>", "  ");
			tmp = tmp.replaceAll(
					"</td><td align=center bgcolor=#ffffff width=80>", ":");
			tmp = tmp.replaceAll(
					"</td></tr><tr><td align=center bgcolor=#f6f6f6>", "\n");
			tmp = tmp.replaceAll(
					"</td><td align=center bgcolor=#ffffff colspan=3>", ":");
			
			tmp = tmp.replaceAll(
					"</td></tr><tr><td align=center bgcolor=#f6f6f6>", "  ");
			tmp = tmp.replaceAll(
					"</td><td align=center bgcolor=#ffffff colspan=3>", ":");
			tmp = tmp.replaceAll(
					"</td></tr><tr><td align=center bgcolor=#f6f6f6>", " ");
			//tmp = tmp.replaceAll("</p>", "\n");
			if ("".equals(tmp) || tmp == null)
				tmp = "对不起，您的输入不正确，暂时没有您需要的数据！";
			else
				tmp = "身份证号码 " + card + " 的详细信息：\n" + tmp;
		}
		return tmp;
		}
		catch(Exception e){
			tmp = "对不起，您的输入不正确，暂时没有您需要的数据！";
			return tmp;
		}
	}

	public String getWeather(String weather) {
		String urlStr = urlWeather + weather + "&dpc=1";
		String tmp = getResponse(urlStr, "gb2312");
		try{
		tmp = tmp
		.substring(
				tmp.indexOf("<a href=\"javascript:sent_to_vb(") + 31,
				tmp.indexOf(");\" class=\"i6\">"));
		if ("".equals(tmp) || tmp == null)
			tmp = "对不起，您的输入不正确，暂时没有您需要的数据！";
		else
			tmp = weather + " 的天气情况是：\n" + tmp;
		return tmp;
		}
		catch(Exception e){
			tmp = "对不起，您的输入不正确，暂时没有您需要的数据！";
			return tmp;
		}
	}

	 public static void main(String args[]) {
	 Module m = new Module();
	 //m.getArea("202.103.67.170");
	  System.out.println(m.getArea("202.103.13.12"));
	 // System.out.println(m.getIDCard("430621198409073311"));
	 // System.out.println(m.getPhone("13875853243"));
	 //System.out.println(m.getWeather("长沙"));
	 }
}
