package com.donbala.emailManagement.service.impl;

import ch.qos.logback.classic.Logger;
import com.donbala.Commons.controller.Common;
import com.donbala.emailManagement.dao.EmailManagementDao;
import com.donbala.emailManagement.model.EmailJobModel;
import com.donbala.emailManagement.model.EmailLogModel;
import com.donbala.emailManagement.model.EmailModel;
import com.donbala.emailManagement.service.EmailManagementService;
import com.donbala.emailManagement.util.EmailSender;
import com.donbala.util.DateUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * {\_/}
 *( ^.^ )
 * / > @ zmf
 *
 * @date 2019/10/17
 */
@Service
@Transactional
public class EmailManagementServiceImpl implements EmailManagementService {
    public final static Logger log = (Logger) LoggerFactory.getLogger(EmailManagementServiceImpl.class);

    @Autowired
    private EmailManagementDao emailManagementDao;
    /**
     * 新增邮件信息
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/17 16:09
     * @param emailModel 1
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    @Transactional
    public Map<String, Object> insertEmailInfo(EmailModel emailModel) {
        Map<String,Object> map = new HashMap<>();
        int emailExists = emailManagementDao.selectEmailInfoCount(emailModel);
        if(emailExists>0){
            map.put("code", "405");
            map.put("status", "warning");
            map.put("msg", "该角色下邮箱已存在");
            return map;
        }
        String date = DateUtil.getSysDate();
        emailModel.setMakeDate(date);
        emailModel.setModifyDate(date);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        emailModel.setId(uuid);
        try {
            emailManagementDao.insertEmailInfo(emailModel);

            map.put("code", "200");
            map.put("status", "success");
            map.put("msg", "邮箱新增成功");
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "500");
            map.put("status", "error");
            map.put("msg", "邮箱新增失败");
        }
        return map;
    }
    /**
     * 查询邮箱信息
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/18 10:51
     * @param
     * @return java.util.List<com.donbala.emailManagement.model.EmailModel>
     */
    @Override
    public List<EmailModel> selectEmailInfo(EmailModel emailModel) {
        return emailManagementDao.selectEmailInfo(emailModel);
    }
    /**
     * 启用
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/18 11:17
     * @param emailModel 1
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> startEmail(EmailModel emailModel) {
        Map<String,Object> map = new HashMap<>();
        emailModel.seteStatus("1");
        try {
            emailManagementDao.startOrStopEmail(emailModel);
            map.put("code", "200");
            map.put("status", "success");
            map.put("msg", "邮箱启用成功");
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "200");
            map.put("status", "error");
            map.put("msg", "邮箱启用失败");
        }
        return map;
    }
    /**
     * 停用
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/18 11:17
     * @param emailModel 1
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> stopEmail(EmailModel emailModel) {
        Map<String,Object> map = new HashMap<>();
        emailModel.seteStatus("0");
        try {
            emailManagementDao.startOrStopEmail(emailModel);
            map.put("code", "200");
            map.put("status", "success");
            map.put("msg", "邮箱启用成功");
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "200");
            map.put("status", "error");
            map.put("msg", "邮箱启用失败");
        }
        return map;
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
    @Override
    public Map<String, Object> deleteEmail(EmailModel emailModel) {
        Map<String,Object> map = new HashMap<>();
        emailManagementDao.deleteEmail(emailModel);
        map.put("code", "200");
        map.put("status", "success");
        map.put("msg", "邮箱删除成功");
        return map;
    }
    /**
     * 回显
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/18 14:30
     * @param emailModel 1
     * @return com.donbala.emailManagement.model.EmailModel
     */
    @Override
    public EmailModel selectReturnEmailInfo(EmailModel emailModel) {
        return emailManagementDao.selectReturnEmailInfo(emailModel);
    }
    /**
     * 修改
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/18 15:24
     * @param emailModel 1
     * @return int
     */
    @Override
    @Transactional
    public Map<String,Object> editEmail(EmailModel emailModel) {
        Map<String,Object> map = new HashMap<>();
        int emailExists = emailManagementDao.selectEmailInfoCount(emailModel);
        if(emailExists>0){
            map.put("code", "405");
            map.put("status", "warning");
            map.put("msg", "该角色下邮箱已存在");
            return map;
        }
        String date = DateUtil.getSysDate();
        emailModel.setModifyDate(date);
        //修改前做只有一个发件人校验--以后
        emailManagementDao.editEmail(emailModel);
        map.put("code", "200");
        map.put("status", "success");
        map.put("msg", "邮箱修改成功");
        return map;
    }
    /**
     * 发送任务表格查询
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/22 10:49
     * @param
     * @return java.util.List<com.donbala.emailManagement.model.EmailJobModel>
     */
    @Override
    public List<EmailJobModel> selectEmailJobList() {
        return emailManagementDao.selectEmailJobList();
    }
    /**
     * 新增发送任务
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/23 9:58
     * @param emailJobModel 1
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> insertEmailJob(EmailJobModel emailJobModel) {
        Map<String, Object> map = new HashMap<>();
//        String emailJobCode = emailJobModel.getEmailJobCode();
//        String sender = emailJobModel.getEmailSender();
//        String receiver = emailJobModel.getEmailReceiver();
//        String copype = emailJobModel.getEmailCopype();
//        String operater = emailJobModel.getMakeUser();
        int exists = emailManagementDao.selectEmailJobCount(emailJobModel);
        //验证是否有此任务，有-不可新增
        if(exists>0){
            map.put("code", "405");
            map.put("status", "warning");
            map.put("msg", "该任务已存在");
            return map;
        }
        //开始新增
//        List<Map<String, String>> list = new ArrayList<>();
//        List<Map<String, String>> sList = getStructureToDB(sender.split(","), emailJobCode, "S",operater);
//        List<Map<String, String>> rList = getStructureToDB(receiver.split(","), emailJobCode, "R",operater);
//        List<Map<String, String>> cList = new ArrayList<>();
//        if(!copype.equals("")){
//            cList = getStructureToDB(copype.split(","), emailJobCode, "C",operater);
//        }
//        list.addAll(sList);
//        list.addAll(rList);
//        list.addAll(cList);
        List<Map<String, String>> list = getStructureToDB2(emailJobModel);
        //入库
        emailManagementDao.insertEmailJob(list);
        map.put("code", "200");
        map.put("status", "success");
        map.put("msg", "发送任务新增成功");

        return map;
    }
    /**
     * 删除发送任务
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/23 17:54
     * @param emailJobModel 1
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> deleteEmailJob(EmailJobModel emailJobModel) {
        Map<String, Object> map = new HashMap<>();
        emailManagementDao.deleteEmailJob(emailJobModel);
        map.put("code", "200");
        map.put("status", "success");
        map.put("msg", "删除发送任务成功");

        return map;
    }
    /**
     * 发送任务回显
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/24 11:04
     * @param emailJobModel 1
     * @return com.donbala.emailManagement.model.EmailJobModel  收件人和抄送人字段是：几个code值以逗号拼接
     */
    @Override
    public EmailJobModel emailJobReturnView(EmailJobModel emailJobModel) {
        return emailManagementDao.emailJobReturnView(emailJobModel);
    }
    /**
     * 修改发送任务
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/24 14:06
     * @param emailJobModel 1
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    @Transactional
    public Map<String, Object> editEmailJob(EmailJobModel emailJobModel) {
        Map<String, Object> map = new HashMap<>();
        emailManagementDao.deleteEmailJob(emailJobModel);

        List<Map<String, String>> list = getStructureToDB2(emailJobModel);
        //入库
        emailManagementDao.insertEmailJob(list);
        map.put("code", "200");
        map.put("status", "success");
        map.put("msg", "发送任务修改成功");

        return map;
    }
    /**
     * 查询发送日志
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/24 17:26
     * @param emailLogModel 1
     * @return java.util.List<com.donbala.emailManagement.model.EmailLogModel>
     */
    @Override
    public Map<String, Object> selectEmailLog(EmailLogModel emailLogModel) {
        List<EmailLogModel> list = emailManagementDao.selectEmailLog(emailLogModel);
        int resultTotal = emailManagementDao.selectEmailLogCount(emailLogModel);
        Map<String,Object> resultMap = Common.getSuccess(list);
        resultMap.put("rows", list);
        resultMap.put("total", resultTotal);
        return resultMap;
    }

    /**
     * 再次封装入库数据结构
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/24 14:14
     * @param emailJobModel 1
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     */
    public List<Map<String, String>> getStructureToDB2(EmailJobModel emailJobModel){

        String emailJobCode = emailJobModel.getEmailJobCode();
        String sender = emailJobModel.getEmailSender();
        String receiver = emailJobModel.getEmailReceiver();
        String copype = emailJobModel.getEmailCopype();
        String operater = emailJobModel.getMakeUser();

        List<Map<String, String>> list = new ArrayList<>();
        List<Map<String, String>> sList = getStructureToDB(sender.split(","), emailJobCode, "S",operater);
        List<Map<String, String>> rList = getStructureToDB(receiver.split(","), emailJobCode, "R",operater);
        List<Map<String, String>> cList = new ArrayList<>();
        if(!copype.equals("")){
            cList = getStructureToDB(copype.split(","), emailJobCode, "C",operater);
        }
        list.addAll(sList);
        list.addAll(rList);
        list.addAll(cList);

        return list;
    }

    /**
     * 生成入库所需的数据结构
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/23 11:20
     * @param strings 1
     * @param eRole 2
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     */
    public List<Map<String, String>> getStructureToDB(String[] strings,String emailJobCode, String eRole,String operater) {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i <strings.length ; i++) {
            Map<String, String> rmap = new HashMap<>();
            rmap.put("emailJobCode", emailJobCode);
            rmap.put("emailId", strings[i]);
            rmap.put("eRole", eRole);
            rmap.put("operater", operater);

            list.add(rmap);
        }

        return list;
    }

    /**
     * 发邮件公共方法
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/21 10:27
     * @param theme 邮件主题
     * @param content 邮件内容
     * @param emailJobCode 发送任务id
     * @return int  1--发送成功；0--发送失败；2--不发邮件
     */
    @Override
    public int sendEmailCommon(String theme,String content,String emailJobCode) {

        String host = "";
        String user = "";
        String password = "";
        String receiver = "";
        String copype = "";
        String nickName = "";
        String errMsg = "";

        List<String> receviceList = new ArrayList<>();
        List<String> copypeList = new ArrayList<>();

        EmailModel emailModel = new EmailModel();
        emailModel.setEmailJobCode(emailJobCode);

        List<EmailModel> eList = emailManagementDao.selectEmailInfoByCode(emailModel);

        if(eList.size()==0){
            //没有添加发送任务，不发邮件
            return 2;
        }
        for (int i = 0; i <eList.size() ; i++) {
            EmailModel em=eList.get(i);
            //发送人
            if(em.geteRole().equals("S")){
                host = em.geteHost();
                user = em.getEmail();
                password = em.getePassword();
                nickName = em.getNickName();
            }
            //收件人
            if(em.geteRole().equals("R")){
                receviceList.add(em.getEmail());
            }
            //抄送人
            if(em.geteRole().equals("C")){
                copypeList.add(em.getEmail());
            }
        }
        receiver = listTOStringOfComma(receviceList);
        copype = listTOStringOfComma(copypeList);

        if(user.equals("")){
            errMsg = "发件人邮箱没有或停用，不发邮件";
            //记录邮件日志
            insertEmailSendLog2(user,receiver,copype,theme,content,errMsg,emailJobCode);
            return 2;
        }
        if(receiver.equals("")){
            errMsg = "收件人邮箱没有或停用，不发邮件";
            //记录邮件日志
            insertEmailSendLog2(user,receiver,copype,theme,content,errMsg,emailJobCode);
            return 2;
        }
        Map<String, Object> emailMap = new HashMap<>();
        emailMap.put("host", host);//邮箱服务器
        emailMap.put("user", user);//发件人
        emailMap.put("password", password);//密码
        emailMap.put("receivers", receiver);//收件人
        emailMap.put("copies", copype);//抄送人
        emailMap.put("nickName", nickName);//昵称
        emailMap.put("content", content);//内容
        emailMap.put("subject", theme);//主题
        errMsg = EmailSender.sendEmail(emailMap, 2);

        if(errMsg.equals("success")){
            //记录邮件日志
            insertEmailSendLog2(user,receiver,copype,theme,content,errMsg,emailJobCode);
            return 1;
        }else {
            //记录邮件日志
            insertEmailSendLog2(user,receiver,copype,theme,content,errMsg,emailJobCode);

            return 0;
        }

    }

    public void insertEmailSendLog2(String user, String receivers, String copies, String subject, String content, String errMsg,String type) {
        Map<String,Object> emailMap=new HashMap<String,Object>();
        emailMap.put("serialno", UUID.randomUUID().toString().replaceAll("-", ""));
        emailMap.put("sender", user);
        emailMap.put("addressee", receivers);
        emailMap.put("copyperson", copies);
        emailMap.put("theme", subject);
        emailMap.put("content", content);
        emailMap.put("type", type);
        if("success".equals(errMsg)) {
            emailMap.put("state", "1");
            emailMap.put("errMsg", "发送成功");
        }else {
            emailMap.put("state", "0");
            emailMap.put("errMsg", errMsg);
        }
        emailManagementDao.addEmailLog(emailMap);
    }

    /**
     * list集合中的各个字符串转化成用逗号(,)拼接的大字符串
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/21 10:41
     * @param list 1
     * @return java.lang.String
     */
    public String listTOStringOfComma(List<String> list){
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<list.size();i++) {
            if(i<list.size()-1){
                sb.append(list.get(i));
                sb.append(",");
            }else {
                sb.append(list.get(i));
            }
        }
        return sb.toString();
    }

}
