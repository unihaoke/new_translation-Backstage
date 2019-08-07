package com.trans.translation.job;

import com.trans.translation.dao.ProductDao;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 处理截止时间定时任务
 */
@DisallowConcurrentExecution
public class DeadlineQuartz extends QuartzJobBean {


    @Autowired
    private ProductDao productDao;

    /**
     * 执行定时任务
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    @Transactional
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

//        long date = new Date().getTime();
//        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println("date:"+date);
//        try {
//            long date1 = sDateFormat.parse("2019-08-05 15:39:00").getTime();
//            System.out.println("date1:"+date1);
//            if(date-date1>=0){
//                System.out.println("current time :"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        int i = productDao.updateStatusByDateline();
        System.out.println(i);

    }
}
