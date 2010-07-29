//package com.lee.kooqi.mybot;

import java.io.File;

/**
 * @author leo
 * 
 */
public class Tools {
	/**
	 * 
	 */
	public Tools() {
		super();
	}
	public static boolean checkType(String url) {
		url = url.toLowerCase();
		boolean flag = false;
		if (url.endsWith(".htm") || url.endsWith(".html")
				|| url.endsWith(".shtml") || url.endsWith(".dhtml")
				|| url.endsWith("/")) {
			flag = true;
		} else
		{
			
		}
		//	System.out.println("�Բ���������������ַ���Ϸ���"
		//			+ "Ŀǰֻ����*.htm��*.shtml��*.html��ת����лл����");
		return flag;
	}

	public String getNow() {
		java.sql.Timestamp time = new java.sql.Timestamp(System
				.currentTimeMillis());

		return time.toString().replaceAll("-", "").replaceAll(":", "").trim();
	}

	public void createPath(String filePath) {
		File myFilePath = new File(filePath);
		if (myFilePath.exists() == false) {
			myFilePath.mkdirs();
		} else {
			if (myFilePath.isDirectory() == false) {
				myFilePath.delete();
				myFilePath.mkdirs();
			}
		}
	}
}
