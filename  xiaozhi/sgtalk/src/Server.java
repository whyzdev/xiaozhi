//package com.lee.kooqi.mybot;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author leo
 *
 */
public class Server  {

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
		try {
			server.task();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void task() throws SchedulerException {

		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		long ctime = System.currentTimeMillis();

		JobDetail jobDetail = new JobDetail("QGetDoc",
				scheduler.DEFAULT_GROUP, QGetDoc.class);

		SimpleTrigger simpleTrigger = new SimpleTrigger("simpleTrigger",
				"triggerGroup-s1");

		simpleTrigger.setStartTime(new Date(ctime));

		simpleTrigger.setRepeatInterval(10000);

		simpleTrigger.setRepeatCount(100);

		scheduler.scheduleJob(jobDetail, simpleTrigger);

		scheduler.start();
	}	
}
