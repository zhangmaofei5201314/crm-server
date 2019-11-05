package com.donbala.emailManagement.util;

import ch.qos.logback.classic.Logger;
import com.donbala.emailManagement.model.EmailModel;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.*;

public class EmailSender {
	public final static Logger log = (Logger) LoggerFactory.getLogger(EmailSender.class);
	
	/**
	 * --不要使用163邮箱服务器（smtp.163.com），会报错554 DT:SPM 163 smtp9,是网易邮箱的反垃圾信息处理机制，目前没有解决办法
	 * 
	 * 
	 */
	/**
	 * 发邮件
	 * map： host-邮件服务器，user-发件人邮箱，password-密码，receivers-收件人邮箱，copies-抄送人邮箱，nickName-发件人昵称，
	 *      content-内容，subject-主题，imageMap-图片，listFile-附件
	 * flag：1-发送纯文本邮件，2-发送纯HTML邮件，3-发送 HTML中包含图片 的邮件，4-发送html文本，邮件可添加附件，5-发送html文本（html可包含图片），邮件可添加附件，
	 */
	public static String sendEmail(Map<String,Object> map,int flag) {
		String host= (String) map.get("host");
		String user = (String) map.get("user");
		String password = (String) map.get("password");
		String receivers=(String) map.get("receivers");
		String copies=(String) map.get("copies");
		String nickName=(String) map.get("nickName");
		String content=(String) map.get("content");
		String subject=(String) map.get("subject");
		Properties properties = System.getProperties();
		//设置邮件服务器地址
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user,password);//发件人用户名、密码
			}
		});
		EmailModel emailModel = new EmailModel();
		emailModel.setEmailContent(content);//邮件内容
		List<String> emailReceiver=new ArrayList<String>();
		String[] receiver=receivers.split(",");
		for (String re : receiver) {
			emailReceiver.add(re);
		}
		if(!(copies==null||copies.equals(""))) {
			List<String> emailCarbonCopy=new ArrayList<String>();
			String[] copy=copies.split(",");
			for (String co : copy) {
				emailCarbonCopy.add(co);
			}
			emailModel.setEmailCarbonCopy(emailCarbonCopy);//抄送人
		}
		emailModel.setEmailReceiver(emailReceiver);//收件人，list-多个
		emailModel.setEmailSubject(subject);//主题
		emailModel.setNickName(nickName);
		emailModel.setUser(user);
		emailModel.setSession(session);
		String errMsg="";
		if(flag==1){//发送纯文本邮件
			errMsg=EmailSender.sendTextEmail(emailModel);
		}else if (flag==2){//发送纯HTML邮件
			errMsg=EmailSender.sendHtml(emailModel);
		}else if (flag==3){//发送 HTML中包含图片 的邮件
			Map<String, File> imageMap = (Map<String, File>) map.get("imageMap");
			errMsg = EmailSender.sendHtmlWithImage(emailModel, imageMap);
		}else if (flag==4){//发送html文本，邮件可添加附件
			Map<String, File> imageMap = new HashMap<String, File>();
			List<File> list = (List<File>) map.get("listFile");
			errMsg= EmailSender.sendHtmlWithImageAndAttachFile(emailModel,imageMap,list);
		}else if (flag==5){//发送html文本（html可包含图片），邮件可添加附件
			Map<String, File> imageMap = (Map<String, File>) map.get("imageMap");
			List<File> list = (List<File>) map.get("listFile");
			errMsg= EmailSender.sendHtmlWithImageAndAttachFile(emailModel,imageMap,list);
		}

		return errMsg;
	}
	
	/**
	 * 发送纯文本邮件
	 * @param emailModel 邮件实体类   收件人  抄送人  主题  内容（纯文本）
	 * @return
	 */
	public static String sendTextEmail(EmailModel emailModel) {
		String resultMessage = "failed";
		try {			
			//创建默认的MimeMessage对象
			MimeMessage mimeMessage = new MimeMessage(emailModel.getSession());
			//解决发件人名称乱码
			String name=String.format("%1$s <%2$s>", MimeUtility.encodeText(emailModel.getNickName(), "UTF-8", "B"), emailModel.getUser()); 
			//设置发送人
//			mimeMessage.setFrom(new InternetAddress(nickname+"<"+user+">"));
			mimeMessage.setFrom(new InternetAddress(name));
			if(emailModel.getEmailReceiver()==null) {
				log.info("发送纯文本邮件收件人为null");
				resultMessage = "发送纯文本邮件收件人为空";
				return resultMessage;
			}
			//设置收件人			
			List<String> receiveAddressList = emailModel.getEmailReceiver();
			int receiveAddressCount = receiveAddressList.size();
			InternetAddress[] receiveAddress = null;
			if(receiveAddressCount>0) {
				receiveAddress = new InternetAddress[receiveAddressCount];
				for(int i=0;i<receiveAddressCount;i++) {
					receiveAddress[i]=new InternetAddress(receiveAddressList.get(i));
				}
			}else {
				log.info("收件人为空");
				resultMessage = "收件人为空";
				return resultMessage;
			}				
			mimeMessage.setRecipients(Message.RecipientType.TO, receiveAddress);
			if(emailModel.getEmailCarbonCopy()!=null) {
				System.out.println("jinlai ");
				//获取邮件抄送人
				List<String> carbonCopyAddressList = emailModel.getEmailCarbonCopy();
				int carbonCopyAddressCount = carbonCopyAddressList.size();
				InternetAddress[] carbonCopyAddress = null;
				//抄送人不为空添加抄送人地址
				if(carbonCopyAddressCount>0) {
					carbonCopyAddress = new InternetAddress[carbonCopyAddressCount];
					for(int i=0;i<carbonCopyAddressCount;i++) {
						carbonCopyAddress[i]=new InternetAddress(carbonCopyAddressList.get(i));
					}
					mimeMessage.setRecipients(Message.RecipientType.CC, carbonCopyAddress);
				}
			}		
			System.out.println("主题前");
			//设置主题
			mimeMessage.setSubject(emailModel.getEmailSubject());
			System.out.println("主题后");
			//设置内容								
			mimeMessage.setText(emailModel.getEmailContent(), "UTF-8");
			//设置发送时间
			mimeMessage.setSentDate(new Date());
			//发送消息
			Transport.send(mimeMessage);
			log.info("发送成功");
			resultMessage = "success";
		} catch (Exception e) {
			//log.error("邮件发送失败："+e.toString().substring(0, e.toString().length()<500?e.toString().length():500));					
			resultMessage = e.toString().substring(0, e.toString().length()<500?e.toString().length():500);
			log.info("发送异常:"+resultMessage);
		}
		return resultMessage;
		
	}
	
	/**
	 * 发送纯HTML邮件
	 * @param emailModel 邮件实体类   收件人  抄送人   主题  内容（纯HTML 不包含图片、文件等）
	 * @return
	 */
	public static String sendHtml(EmailModel emailModel) {	
		String resultMessage = "failed";
		try {			
			//创建默认的MimeMessage对象
			MimeMessage mimeMessage = new MimeMessage(emailModel.getSession());
			String name=String.format("%1$s <%2$s>", MimeUtility.encodeText(emailModel.getNickName(), "UTF-8", "B"), emailModel.getUser()); 
			//设置发送人
			mimeMessage.setFrom(new InternetAddress(name));
			if(emailModel.getEmailReceiver()==null) {
				log.info("收件人为null");
				resultMessage = "收件人为空";
				return resultMessage;
			}
			//设置收件人			
			List<String> receiveAddressList = emailModel.getEmailReceiver();
			int receiveAddressCount = receiveAddressList.size();
			InternetAddress[] receiveAddress = null;
			if(receiveAddressCount>0) {
				receiveAddress = new InternetAddress[receiveAddressCount];
				for(int i=0;i<receiveAddressCount;i++) {
					receiveAddress[i]=new InternetAddress(receiveAddressList.get(i));
				}
			}else {
				log.info("收件人为空");
				resultMessage = "收件人为空";
				return resultMessage;
			}				
			mimeMessage.setRecipients(Message.RecipientType.TO, receiveAddress);
			if(emailModel.getEmailCarbonCopy()!=null) {
				//获取邮件抄送人
				List<String> carbonCopyAddressList = emailModel.getEmailCarbonCopy();
				int carbonCopyAddressCount = carbonCopyAddressList.size();
				InternetAddress[] carbonCopyAddress = null;
				//抄送人不为空添加抄送人地址
				if(carbonCopyAddressCount>0) {
					carbonCopyAddress = new InternetAddress[carbonCopyAddressCount];
					for(int i=0;i<carbonCopyAddressCount;i++) {
						carbonCopyAddress[i]=new InternetAddress(carbonCopyAddressList.get(i));
					}
					mimeMessage.setRecipients(Message.RecipientType.CC, carbonCopyAddress);
				}
			}								
			//设置主题
			mimeMessage.setSubject(emailModel.getEmailSubject());
			//设置内容
			Multipart mp = new MimeMultipart();
			BodyPart bdpt = new MimeBodyPart();
			bdpt.setContent(emailModel.getEmailContent(), "text/html;charset=UTF-8");			
			mp.addBodyPart(bdpt);														
			mimeMessage.setContent(mp);
			//设置发送时间
			mimeMessage.setSentDate(new Date());
			//发送消息
			Transport.send(mimeMessage);
			log.info("发送成功");
			resultMessage = "success";
		} catch (Exception e) {
			//log.error("邮件发送失败："+e.toString().substring(0, e.toString().length()<500?e.toString().length():500));					
			resultMessage = e.toString().substring(0, e.toString().length()<500?e.toString().length():500);
			log.info("发送异常:"+resultMessage);
		}
		return resultMessage;
		
	}		
	
	/**
	 * 发送 HTML中包含图片 的邮件
	 * @param emailModel  邮件实体类  收件人、抄送人、邮件主题、内容、图片ID与图片文件对应map
	 * @param imageMap html文件中图片id与图片文件对应关系 map
	 * @return
	 */
	public static String sendHtmlWithImage(EmailModel emailModel,Map<String,File> imageMap) {	
		String resultMessage = "failed";
		try {			
			//创建默认的MimeMessage对象
			MimeMessage mimeMessage = new MimeMessage(emailModel.getSession());
			String name=String.format("%1$s <%2$s>", MimeUtility.encodeText(emailModel.getNickName(), "UTF-8", "B"), emailModel.getUser()); 
			//设置发送人
			mimeMessage.setFrom(new InternetAddress(name));
			if(emailModel.getEmailReceiver()==null) {
				log.info("收件人为null");
				resultMessage = "收件人为空";
				return resultMessage;
			}
			//设置收件人			
			List<String> receiveAddressList = emailModel.getEmailReceiver();
			int receiveAddressCount = receiveAddressList.size();
			InternetAddress[] receiveAddress = null;
			if(receiveAddressCount>0) {
				receiveAddress = new InternetAddress[receiveAddressCount];
				for(int i=0;i<receiveAddressCount;i++) {
					receiveAddress[i]=new InternetAddress(receiveAddressList.get(i));
				}
			}else {
				log.info("收件人为空");
				resultMessage = "收件人为空";
				return resultMessage;
			}				
			mimeMessage.setRecipients(Message.RecipientType.TO, receiveAddress);
			if(emailModel.getEmailCarbonCopy()!=null) {
				//获取邮件抄送人
				List<String> carbonCopyAddressList = emailModel.getEmailCarbonCopy();
				int carbonCopyAddressCount = carbonCopyAddressList.size();
				InternetAddress[] carbonCopyAddress = null;
				//抄送人不为空添加抄送人地址
				if(carbonCopyAddressCount>0) {
					carbonCopyAddress = new InternetAddress[carbonCopyAddressCount];
					for(int i=0;i<carbonCopyAddressCount;i++) {
						carbonCopyAddress[i]=new InternetAddress(carbonCopyAddressList.get(i));
					}
					mimeMessage.setRecipients(Message.RecipientType.CC, carbonCopyAddress);
				}
			}								
			//设置主题
			mimeMessage.setSubject(emailModel.getEmailSubject());
			//设置内容
			Multipart mp = new MimeMultipart();
			BodyPart bdpt = new MimeBodyPart();
			bdpt.setContent(emailModel.getEmailContent(), "text/html;charset=UTF-8");			
			mp.addBodyPart(bdpt);
			//将图片载入模板中对应的图片ID位置
			for(String imageId : imageMap.keySet()) {
				MimeBodyPart imageBodyPart = new MimeBodyPart();
				File imageFile = imageMap.get(imageId);
				DataSource fds = new FileDataSource(imageFile);
				imageBodyPart.setDataHandler(new DataHandler(fds));
				imageBodyPart.setContentID(imageId);
				mp.addBodyPart(imageBodyPart);								
			}									
												
			mimeMessage.setContent(mp);
			//设置发送时间
			mimeMessage.setSentDate(new Date());
			mimeMessage.saveChanges();
			//发送消息
			Transport.send(mimeMessage);
			log.info("发送成功");
			resultMessage = "success";
		} catch (Exception e) {
			//log.error("邮件发送失败："+e.toString().substring(0, e.toString().length()<500?e.toString().length():500));					
			resultMessage = e.toString().substring(0, e.toString().length()<500?e.toString().length():500);
			log.info("发送异常:"+resultMessage);
		}
		return resultMessage;
		
	}
	
	/**
	 * 发送html文本（html可包含图片），邮件可添加附件
	 * @param emailModel 邮件实体类   收件人、抄送人、邮件主题、邮件内容
	 * @param imageMap 邮件文本中图片ID与图片文件对应的 map
	 * @param attachFileList 邮件附件list
	 * @return
	 */
	public static String sendHtmlWithImageAndAttachFile(EmailModel emailModel,Map<String,File> imageMap,List<File> attachFileList) {	
		String resultMessage = "failed";
		try {			
			//创建默认的MimeMessage对象
			MimeMessage mimeMessage = new MimeMessage(emailModel.getSession());
			String name=String.format("%1$s <%2$s>", MimeUtility.encodeText(emailModel.getNickName(), "UTF-8", "B"), emailModel.getUser()); 
			//设置发送人
			mimeMessage.setFrom(new InternetAddress(name));
			if(emailModel.getEmailReceiver()==null) {
				log.info("收件人为null");
				resultMessage = "收件人为空";
				return resultMessage;
			}
			//设置收件人			
			List<String> receiveAddressList = emailModel.getEmailReceiver();
			int receiveAddressCount = receiveAddressList.size();
			InternetAddress[] receiveAddress = null;
			if(receiveAddressCount>0) {
				receiveAddress = new InternetAddress[receiveAddressCount];
				for(int i=0;i<receiveAddressCount;i++) {
					receiveAddress[i]=new InternetAddress(receiveAddressList.get(i));
				}
			}else {
				log.info("收件人为空");
				resultMessage = "收件人为空";
				return resultMessage;
			}				
			mimeMessage.setRecipients(Message.RecipientType.TO, receiveAddress);
			if(emailModel.getEmailCarbonCopy()!=null) {
				//获取邮件抄送人
				List<String> carbonCopyAddressList = emailModel.getEmailCarbonCopy();
				int carbonCopyAddressCount = carbonCopyAddressList.size();
				InternetAddress[] carbonCopyAddress = null;
				//抄送人不为空添加抄送人地址
				if(carbonCopyAddressCount>0) {
					carbonCopyAddress = new InternetAddress[carbonCopyAddressCount];
					for(int i=0;i<carbonCopyAddressCount;i++) {
						carbonCopyAddress[i]=new InternetAddress(carbonCopyAddressList.get(i));
					}
					mimeMessage.setRecipients(Message.RecipientType.CC, carbonCopyAddress);
				}
			}								
			//设置主题
			mimeMessage.setSubject(emailModel.getEmailSubject());
			//设置内容
			Multipart mp = new MimeMultipart();
			BodyPart bdpt = new MimeBodyPart();
			bdpt.setContent(emailModel.getEmailContent(), "text/html;charset=UTF-8");			
			mp.addBodyPart(bdpt);
			
			for(String imageId : imageMap.keySet()) {
				MimeBodyPart imageBodyPart = new MimeBodyPart();				
				File file = imageMap.get(imageId);			
				DataSource fds = new FileDataSource(file);
				imageBodyPart.setDataHandler(new DataHandler(fds));
				imageBodyPart.setContentID(imageId);
				mp.addBodyPart(imageBodyPart);
			}
			
			//将需要发送的附件添加到邮件附件中
			for(File attachFile : attachFileList) {
				MimeBodyPart attach = new MimeBodyPart();							
				DataSource attachDataSource = new FileDataSource(attachFile);
				DataHandler dh = new DataHandler(attachDataSource);
				attach.setDataHandler(dh);
				try {
					attach.setFileName(MimeUtility.encodeWord(dh.getName()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				mp.addBodyPart(attach);
			}
									
			mimeMessage.setContent(mp);
			//设置发送时间
			mimeMessage.setSentDate(new Date());			
			mimeMessage.saveChanges();
			//发送消息
			Transport.send(mimeMessage);
			log.info("发送成功");
			resultMessage = "success";
		} catch (Exception e) {
			//log.error("邮件发送失败："+e.toString().substring(0, e.toString().length()<500?e.toString().length():500));					
			resultMessage = e.toString().substring(0, e.toString().length()<500?e.toString().length():500);
			log.info("发送异常:"+resultMessage);
		}
		return resultMessage;
		
	}
	
	

}

