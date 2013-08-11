package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailContent {
	private static final Logger logger = LoggerFactory
			.getLogger(EmailContent.class);

	public static String getResetPasswordContent(String userName, String url) {
		String htmlContent = "<html><body><h3>" + userName
				+ ",您好:</h3>\n<p>请通过下面的链接在15分钟内重设您的密码！</p>\n <a href=\"" + url
				+ "\">" + url + "</a></body></html>";
		logger.trace(htmlContent);

		return htmlContent;
	}

	public static String getVerifySuccessContent(String userName) {
		String htmlContent = "<html><body><h3>" + userName
				+ ",您好:</h3>\n<p>您的账户 <h1>" + userName + "</h1> 已经申请成功</p>\n";
		return htmlContent;
	}

	public static String getVerifyFailContent(String userName) {
		String htmlContent = "<html><body><h3>" + userName
				+ ",您好:</h3>\n<p>您的账户 <h1>" + userName
				+ "</h1> 已经申请失败，请联系管理员！</p>\n";
		;
		return htmlContent;
	}
}
