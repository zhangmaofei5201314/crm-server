package com.donbala.emailManagement.model;

import com.donbala.Commons.model.BaseResult;

import java.io.Serializable;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 *
 * @date 2019/10/22
 */
public class EmailJobModel extends BaseResult implements Serializable {
    //发送任务id
    private String emailJobCode;
    //发送任务名称
    private String emailJobName;
    //发件人邮箱
    private String emailSender;
    //收件人邮箱
    private String emailReceiver;
    //抄送人邮箱
    private String emailCopype;


    private String makeDate;
    private String makeUser;
    private String modifyDate;
    private String modifyUser;

    public String getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(String makeDate) {
        this.makeDate = makeDate;
    }

    public String getMakeUser() {
        return makeUser;
    }

    public void setMakeUser(String makeUser) {
        this.makeUser = makeUser;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getEmailJobCode() {
        return emailJobCode;
    }

    public void setEmailJobCode(String emailJobCode) {
        this.emailJobCode = emailJobCode;
    }

    public String getEmailJobName() {
        return emailJobName;
    }

    public void setEmailJobName(String emailJobName) {
        this.emailJobName = emailJobName;
    }

    public String getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(String emailSender) {
        this.emailSender = emailSender;
    }

    public String getEmailReceiver() {
        return emailReceiver;
    }

    public void setEmailReceiver(String emailReceiver) {
        this.emailReceiver = emailReceiver;
    }

    public String getEmailCopype() {
        return emailCopype;
    }

    public void setEmailCopype(String emailCopype) {
        this.emailCopype = emailCopype;
    }


}
