package com.donbala.codeselect.service.impl;

import com.donbala.codeselect.dao.CodeAndNameMapper;
import com.donbala.codeselect.model.CodeAndName;
import com.donbala.codeselect.service.CodeSelectIntf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @CLassName: CodeSelectImpl
 * @Program: springbootdemo
 * @Author: wangran
 * @Date: 2019/7/12-14:15
 * @Description: todo
 **/
@Service
public class CodeSelectImpl implements CodeSelectIntf {

    @Autowired
    private CodeAndNameMapper codeAndNameMapper;

    /**
    *@description: 主要入口函数，返回要查询的编码和编码名称
    */
    public List<CodeAndName> codeSelect(String codetype) {
        List<CodeAndName> codeAndNameList = new ArrayList<>();

        if(codetype.equals("role")){
            codeAndNameList = getRoleList();
        }else if(codetype.equals("jobInterval")){
            codeAndNameList = getJobIntervalList();
        }else if(codetype.equals("jobCode")){
            codeAndNameList = getJobCodeAndName();
        }else if(codetype.equals("emailJob")){
            codeAndNameList = getEmailJob();
        }else if(codetype.equals("emailSender")){
            codeAndNameList = getEmailSender();
        }else if(codetype.equals("emailReceiver")){
            codeAndNameList = getEmailReceiver();
        }else if(codetype.equals("emailCopype")){
            codeAndNameList = getEmailCopype();
        }

        return codeAndNameList;
    }
    /**
     * 发送人下拉
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/22 9:37
     * @param
     * @return java.util.List<com.donbala.codeselect.model.CodeAndName>
     */
    private List<CodeAndName> getEmailSender(){
        List<CodeAndName> codeAndNameList = codeAndNameMapper.selectEmailSender();
        return codeAndNameList;
    }
    /**
     * 收件人下拉
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/22 9:36
     * @param
     * @return java.util.List<com.donbala.codeselect.model.CodeAndName>
     */
    private List<CodeAndName> getEmailReceiver(){
        List<CodeAndName> codeAndNameList = codeAndNameMapper.selectEmailReceiver();
        return codeAndNameList;
    }
    /**
     * 抄送人下拉
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/22 9:36
     * @param
     * @return java.util.List<com.donbala.codeselect.model.CodeAndName>
     */
    private List<CodeAndName> getEmailCopype(){
        List<CodeAndName> codeAndNameList = codeAndNameMapper.selectEmailCopy();
        return codeAndNameList;
    }
    /**
     * 邮件任务下拉
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/10/22 9:33
     * @param
     * @return java.util.List<com.donbala.codeselect.model.CodeAndName>
     */
    private List<CodeAndName> getEmailJob(){
        List<CodeAndName> codeAndNameList = codeAndNameMapper.selectEmailJob();
        return codeAndNameList;
    }
    /**
     * 角色下拉
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/18 9:18
     * @param
     * @return java.util.List<com.donbala.codeselect.model.CodeAndName>
     */
    private List<CodeAndName> getRoleList() {
        List<CodeAndName> codeAndNameList = codeAndNameMapper.selectRole() ;
        return codeAndNameList;
    }
    /**
     * 执行间隔下拉
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/18 9:18
     * @param
     * @return
     */
    private List<CodeAndName> getJobIntervalList(){
        List<CodeAndName> codeAndNameList = codeAndNameMapper.selectJobInterval();
        return codeAndNameList;
    }
    /**
     * 作业名称下拉
     *  {\_/}
     * ( ^.^ )
     *  / > @ zhangmaofei
     * @date 2019/9/18 10:24
     * @param
     * @return java.util.List<com.donbala.codeselect.model.CodeAndName>
     */
    private List<CodeAndName> getJobCodeAndName(){
        return codeAndNameMapper.selectJobCodeAndName();
    }
}
