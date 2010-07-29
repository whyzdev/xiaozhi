//package com.lee.kooqi.mybot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * @author leo
 * 
 */
public class GetResource {

	public GetResource() {
		setProperties();
	}

	private String loginName;

	private String password;

	private boolean isSendNews;

	/**
	 * @return the isSendNews
	 */
	public boolean getIsSendNews() {
		return isSendNews;
	}

	/**
	 * @param isSendNews
	 *            the isSendNews to set
	 */
	public void setIsSendNews(boolean isSendNews) {
		this.isSendNews = isSendNews;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName
	 *            the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	private void setProperties() {
		try {
			FileInputStream fis = new FileInputStream("mybot.properties");
			Properties props = new Properties();
			props.load(fis);
			fis.close();
			setIsSendNews(props.getProperty("isSendNews").equals("true") ? true
					: false);
			setLoginName(props.getProperty("loginName"));
			setPassword(props.getProperty("password"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
