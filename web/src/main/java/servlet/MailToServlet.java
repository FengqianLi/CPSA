package servlet;

import java.io.IOException;
import java.sql.Timestamp;

import javabeans.FindPassword;
import javabeans.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.MD5;
import utils.MailTo;

import dbManager.DBManager;

/**
 * Servlet implementation class MailToServlet
 */
public class MailToServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MailToServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		MD5 md5 = new MD5();

		User user = DBManager.dbUtil.getUserByEmail(email);
		if (user == null) {
			response.sendRedirect("fail.jsp?msg=this email doest not exists");
			return;
		}
		Timestamp _time = new Timestamp(System.currentTimeMillis());
		String url = md5.getMD5ofStr(String.valueOf(user.getUserId()) + _time);
		FindPassword fp = new FindPassword(url, user.getUserId(), _time);

		if (DBManager.dbUtil.addFindPassword(fp) == true) {
			// send the mail
			MailTo mail = new MailTo();
			mail.sendResetPassword(email, user.getUserName(), url);
			response.sendRedirect("success.jsp?msg=we have sent an email to "
					+ email + ", your can reset your password in 15 minites");
		}
	}
}
