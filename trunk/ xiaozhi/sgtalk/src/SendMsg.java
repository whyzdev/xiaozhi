//package com.lee.kooqi.mybot;

import net.sf.jml.Email;
import net.sf.jml.MsnMessenger;

/**
 * @author leo
 * 
 */
public class SendMsg {
	public static void sendMsg(MsnMessenger messenger) {
		messenger.sendText(Email.parseStr("fillsunshine@msn.com"),
				"Hi,tsinghua bot login in ,can i help you?");
	}
}
