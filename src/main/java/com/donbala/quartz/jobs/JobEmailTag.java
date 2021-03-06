package com.donbala.quartz.jobs;

import com.donbala.emailManagement.service.EmailManagementService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 测试标签邮件提醒
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 *
 * @date 2019/10/21
 */
@DisallowConcurrentExecution
public class JobEmailTag implements Job {

    @Autowired
    public EmailManagementService emailManagementService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        String emailJobCode = "02";
        String theme = "标签提醒-邮件界面化测试";
        String content = "标签出错啦。~";
        int flag=emailManagementService.sendEmailCommon(theme, content, emailJobCode);
        if(flag==1){
            System.out.println("发送成功");
        }else{
            System.out.println("发送失败");
        }

    }
}
