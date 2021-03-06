//package com.lee.kooqi.mybot;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author leo
 * 
 */
public class QGetDoc implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("In SimpleQuartzJob - executing its JOB at " 
                + new Date() + " by " + context.getTrigger().getName());
    }

}
