package com.donbala.emailManagement.model;

import com.donbala.Commons.model.BaseResult;

import java.io.Serializable;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 * 邮件日志model
 * @date 2019/10/24
 */
public class EmailLogModel  extends BaseResult implements Serializable {
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
    //时间段-开始
    private String startDate;
    //时间段-结束
    private String endDate;
    //发送状态
    private String sendState;
    //发送时间
    private String sendTime;
    //邮件主题
    private String theme;
    //邮件内容
    private String content;
    //发送结果
    private String errMsg;

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSendState() {
        return sendState;
    }

    public void setSendState(String sendState) {
        this.sendState = sendState;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
