package com.donbala.quartz.jobs;

import com.donbala.emailManagement.service.EmailManagementService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 测试筛选邮件提醒
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 *
 * @date 2019/10/21
 */
@DisallowConcurrentExecution
public class JobEmailScreen implements Job {

    @Autowired
    public EmailManagementService emailManagementService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String emailJobCode = "03";
        String theme = "筛选提醒-邮件界面化测试";
        String content = "筛选出错啦。~";
        int flag=emailManagementService.sendEmailCommon(theme, content, emailJobCode);
        if(flag==1){
            System.out.println("发送成功");
        }else{
            System.out.println("发送失败");
        }
    }
}
