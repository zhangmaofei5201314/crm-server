package com.donbala.emailManagement.controller;

import com.donbala.emailManagement.model.EmailJobModel;
import com.donbala.emailManagement.model.EmailLogModel;
import com.donbala.emailManagement.model.EmailModel;
import com.donbala.emailManagement.service.EmailManagementService;
import com.donbala.userManagement.model.CmsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 *
 * @date 2019/10/17
 */
@RestController
public class EmailManagementController {

    @Autowired
    private EmailManagementService emailManagementService;

    /**
     * 新增邮件信息
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/17 16:15
     * @param emailModel 1
     * @param session 2
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value="/controller/email/management/insertEmail",method= RequestMethod.POST)
    public Map<String, Object> insertEmail(EmailModel emailModel, HttpSession session) {
        String user = ((CmsUser) session.getAttribute("user")).getUsercode();
        emailModel.setMakeUser(user);
        emailModel.setModifyUser(user);
        Map<String, Object> map = emailManagementService.insertEmailInfo(emailModel);
        return map;

    }
    /**
     * 查询邮箱信息
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/18 10:52
     * @param
     * @return java.util.List<com.donbala.emailManagement.model.EmailModel>
     */
    @RequestMapping(value="/controller/email/management/selectEmail",method= RequestMethod.POST)
    public List<EmailModel> selectEmailInfo(EmailModel emailModel){
        return emailManagementService.selectEmailInfo(emailModel);
    }
    /**
     * 启用
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/18 11:24
     * @param emailModel 1
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value="/controller/email/management/startEmail",method= RequestMethod.POST)
    public Map<String, Object> startEmail(EmailModel emailModel){
        return emailManagementService.startEmail(emailModel);
    }
    /**
     * 停用
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/18 11:24
     * @param emailModel 1
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value="/controller/email/management/stopEmail",method= RequestMethod.POST)
    public Map<String, Object> stopEmail(EmailModel emailModel){
        return emailManagementService.stopEmail(emailModel);
    }
    /**
     * 删除邮箱
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/18 14:02
     * @param emailModel 1
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value="/controller/email/management/deleteEmail",method= RequestMethod.POST)
    public Map<String, Object> deleteEmail(EmailModel emailModel){
        return emailManagementService.deleteEmail(emailModel);
    }
    /**
     * 回显
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/18 14:37
     * @param emailModel 1
     * @return com.donbala.emailManagement.model.EmailModel
     */
    @RequestMapping(value="/controller/email/management/returnEmail",method= RequestMethod.POST)
    public EmailModel selectReturnEmailInfo(EmailModel emailModel){
        return emailManagementService.selectReturnEmailInfo(emailModel);
    }
    /**
     * 修改
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/18 15:31
     * @param emailModel 1
     * @param session 2
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value="/controller/email/management/editEmail",method= RequestMethod.POST)
    public Map<String, Object> editEmail(EmailModel emailModel, HttpSession session) {
        String user = ((CmsUser) session.getAttribute("user")).getUsercode();
        emailModel.setModifyUser(user);
        Map<String, Object> map = emailManagementService.editEmail(emailModel);
        return map;

    }
    ////邮箱管理结束
    ///邮件发送管理开始
    /**
     * 查询发送任务列表
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/23 14:04
     * @param
     * @return java.util.List<com.donbala.emailManagement.model.EmailJobModel>
     */
    @RequestMapping(value="/controller/email/management/selectEmailJobInfo",method= RequestMethod.POST)
    public List<EmailJobModel> selectEmailJobInfo(){
        return emailManagementService.selectEmailJobList();
    }
    /**
     * 新增发送任务
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/23 14:04
     * @param emailJobModel 1
     * @param session 2
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value="/controller/email/management/insertEmailJob",method= RequestMethod.POST)
    public Map<String, Object> insertEmailJob(EmailJobModel emailJobModel, HttpSession session) {
        String user = ((CmsUser) session.getAttribute("user")).getUsercode();
        emailJobModel.setMakeUser(user);
        emailJobModel.setModifyUser(user);
        Map<String, Object> map = emailManagementService.insertEmailJob(emailJobModel);
        return map;

    }
    /**
     * 删除发送任务
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/23 17:57
     * @param emailJobModel 1
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value="/controller/email/management/deleteEmailJob",method= RequestMethod.POST)
    public Map<String, Object> deleteEailJob(EmailJobModel emailJobModel){
        return emailManagementService.deleteEmailJob(emailJobModel);
    }
    /**
     * 发送任务回显
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/24 11:07
     * @param emailJobModel 1
     * @return com.donbala.emailManagement.model.EmailJobModel
     */
    @RequestMapping(value="/controller/email/management/returnEmailJob",method= RequestMethod.POST)
    public EmailJobModel emailJobReturnView(EmailJobModel emailJobModel){
        return emailManagementService.emailJobReturnView(emailJobModel);
    }
    /**
     * 修改发送任务
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/24 17:26
     * @param emailJobModel 1
     * @param session 2
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value="/controller/email/management/editEmailJob",method= RequestMethod.POST)
    public Map<String,Object> editEmailJob(EmailJobModel emailJobModel, HttpSession session){
        String user = ((CmsUser) session.getAttribute("user")).getUsercode();
        emailJobModel.setMakeUser(user);
        emailJobModel.setModifyUser(user);
        return emailManagementService.editEmailJob(emailJobModel);
    }
    /**
     * 查询发送日志
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/24 17:28
     * @param emailLogModel 1
     * @return java.util.List<com.donbala.emailManagement.model.EmailLogModel>
     */
    @RequestMapping(value="/controller/email/sendlog/search",method= RequestMethod.POST)
    public Map<String, Object> selectEmailLog(EmailLogModel emailLogModel) {
        return emailManagementService.selectEmailLog(emailLogModel);
    }


}
