package com.trans.translation.job;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeadlineQuartzConfig {

    @Bean
    public JobDetail testQuartzDetail(){
        return JobBuilder.newJob(DeadlineQuartz.class)
                .withIdentity("zxf").storeDurably()
                .requestRecovery(true).build();
    }


    @Bean
    public Trigger testQuartzTrigger(){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("name","zxf");

        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInMinutes(1)//设置时间周期单位分钟
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(testQuartzDetail())
                .withIdentity("zxf")
                .withSchedule(simpleScheduleBuilder)
                .usingJobData(jobDataMap)
                .build();
    }
}
