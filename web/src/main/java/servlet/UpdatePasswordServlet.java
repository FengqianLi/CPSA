package servlet;

import java.io.IOException;

import javabeans.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.MD5;

import model.UserManager;

import dbManager.DBManager;

/**
 * Servlet implementation class updatePassword
 */
public class UpdatePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdatePasswordServlet() {
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
		User user = (User) request.getSession().getAttribute("user");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");

		if (protocol.UserProtocol.PASSWORD_ERROR == UserManager.getInstance()
				.validateLogin(user.getUserName(), oldPassword)) {
			response.sendRedirect("fail.jsp?msg=the old password you input is wrong");
			return;
		}
		MD5 oMD5 = new MD5();
		String pwdmd5 = oMD5.getMD5ofStr(newPassword);
		user.setPassword(pwdmd5);
		if (DBManager.dbUtil.updateUser(user)) {
			response.sendRedirect("success.jsp?msg=update password success");
		} else {
			response.sendRedirect("fail.jsp?msg=update password failed");
		}
	}
}
