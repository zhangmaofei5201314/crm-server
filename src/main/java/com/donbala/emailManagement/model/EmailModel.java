package com.donbala.emailManagement.model;

import com.donbala.Commons.model.BaseResult;

import javax.mail.Session;
import java.io.Serializable;
import java.util.List;

public class EmailModel extends BaseResult implements Serializable{
	private static final long serialVersionUID = 1L;
	//id
	private String id;
	//邮件接收方,有可能是多个
	private List<String> emailReceiver;
	//邮件抄送,有可能是多个
	private List<String> emailCarbonCopy;
	//邮件主题
	private String emailSubject;
	//邮件正文
	private String emailContent;
	//发件人昵称
	private String nickName;
	//邮件发送账号
	private String user;
	//邮件会话
	private Session session;

	//邮箱账号
	private String email;
	//发件人邮箱密码
	private String ePassword;
	//发件人邮箱服务器
	private String eHost;
	//邮箱状态，0-停用，1-启用
	private String eStatus;
	//邮箱角色，S-发件人，R-收件人，C-抄送人
	private String eRole;
	//邮件发送任务id
	private String emailJobCode;

	private String makeDate;
	private String makeUser;
	private String modifyDate;
	private String modifyUser;

	public String getEmailJobCode() {
		return emailJobCode;
	}

	public void setEmailJobCode(String emailJobCode) {
		this.emailJobCode = emailJobCode;
	}

	public String geteHost() {
		return eHost;
	}

	public void seteHost(String eHost) {
		this.eHost = eHost;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getePassword() {
		return ePassword;
	}

	public void setePassword(String ePassword) {
		this.ePassword = ePassword;
	}

	public String geteStatus() {
		return eStatus;
	}

	public void seteStatus(String eStatus) {
		this.eStatus = eStatus;
	}

	public String geteRole() {
		return eRole;
	}

	public void seteRole(String eRole) {
		this.eRole = eRole;
	}

	public List<String> getEmailReceiver() {
		return emailReceiver;
	}
	public void setEmailReceiver(List<String> emailReceiver) {
		this.emailReceiver = emailReceiver;
	}
	public List<String> getEmailCarbonCopy() {
		return emailCarbonCopy;
	}
	public void setEmailCarbonCopy(List<String> emailCarbonCopy) {
		this.emailCarbonCopy = emailCarbonCopy;
	}
	public String getEmailSubject() {
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}		

}
