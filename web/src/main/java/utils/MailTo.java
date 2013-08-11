package utils;

import protocol.Config;

public class MailTo {

	private String serverHost;
	private String ServerPort;
	private String userName;
	private String password;
	private String fromAddress;
	private String pageAddress;
	private String subject;

	public MailTo() {
		this.serverHost = Config.prop.getProperty("server_host");
		this.ServerPort = Config.prop.getProperty("server_port");
		this.userName = Config.prop.getProperty("mail_user");
		this.password = Config.prop.getProperty("mail_password");
		this.fromAddress = Config.prop.getProperty("from_address");
		this.pageAddress = Config.prop.getProperty("site_url")
				+ "/resetPassword.jsp";
		this.subject = "华为静态代码性能检测系统密码修改";
	}

	public void sendResetPassword(String toAddress, String userName, String id) {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost(this.serverHost);
		mailInfo.setMailServerPort(this.ServerPort);
		mailInfo.setValidate(true);
		mailInfo.setUserName(this.userName);
		mailInfo.setPassword(this.password);
		mailInfo.setFromAddress(this.fromAddress);
		mailInfo.setToAddress(toAddress);
		mailInfo.setSubject(this.subject);
		mailInfo.setContent(EmailContent.getResetPasswordContent(userName,
				pageAddress + "?id=" + id));

		SimpleMailSender sms = new SimpleMailSender();
		sms.sendHtmlMail(mailInfo);
	}

	public void sendUserVerify(String toAddress, String userName, boolean res) {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost(this.serverHost);
		mailInfo.setMailServerPort(this.ServerPort);
		mailInfo.setValidate(true);
		mailInfo.setUserName(this.userName);
		mailInfo.setPassword(this.password);
		mailInfo.setFromAddress(this.fromAddress);
		mailInfo.setToAddress(toAddress);
		mailInfo.setSubject(this.subject);
		if (res)
			mailInfo.setContent(EmailContent.getVerifySuccessContent(userName));
		else
			mailInfo.setContent(EmailContent.getVerifyFailContent(userName));

		SimpleMailSender sms = new SimpleMailSender();
		sms.sendHtmlMail(mailInfo);
	}
}
