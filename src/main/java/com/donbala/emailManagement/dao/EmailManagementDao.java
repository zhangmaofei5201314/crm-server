package com.donbala.emailManagement.dao;

import com.donbala.emailManagement.model.EmailJobModel;
import com.donbala.emailManagement.model.EmailLogModel;
import com.donbala.emailManagement.model.EmailModel;

import java.util.List;
import java.util.Map;

public interface EmailManagementDao {
    //邮箱是否存在验证查询
    int selectEmailInfoCount(EmailModel emailModel);
    //插入邮箱信息
    int insertEmailInfo(EmailModel emailModel);
    //查询邮箱信息
    List<EmailModel> selectEmailInfo(EmailModel emailModel);
    //启/停 用邮箱
    int startOrStopEmail (EmailModel emailModel);
    //删除邮箱
    int deleteEmail(EmailModel emailModel);
    //回显
    EmailModel selectReturnEmailInfo(EmailModel emailModel);
    //修改邮箱
    int editEmail(EmailModel emailModel);
    //查询每个发送任务的邮箱信息
    List<EmailModel> selectEmailInfoByCode(EmailModel emailModel);
    //发送任务表格查询
    List<EmailJobModel> selectEmailJobList();

    //发送任务是否存在验证查询
    int selectEmailJobCount(EmailJobModel emailJobModel);

    //新增发送任务
    int insertEmailJob(List<Map<String,String>> list);

    //删除发送任务
    int deleteEmailJob(EmailJobModel emailJobModel);

    //发送任务回显
    EmailJobModel emailJobReturnView(EmailJobModel emailJobModel);

    //插入发送日志
    int addEmailLog(Map<String, Object> map);

    //查询发送日志
    List<EmailLogModel> selectEmailLog(EmailLogModel emailLogModel);
    int selectEmailLogCount(EmailLogModel emailLogModel);
}
