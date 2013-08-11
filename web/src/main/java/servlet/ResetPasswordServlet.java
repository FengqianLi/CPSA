package servlet;

import java.io.IOException;

import javabeans.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.MD5;

import dbManager.DBManager;

/**
 * Servlet implementation class updatePassword
 */
public class ResetPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ResetPasswordServlet() {
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
		String url = request.getParameter("urlId");
		User user = DBManager.dbUtil.getUserByUserId(DBManager.dbUtil
				.getFindPasswordByUrl(url).getUserId());
		String newPassword = request.getParameter("newPassword");
		MD5 m = new MD5();
		user.setPassword(m.getMD5ofStr(newPassword));
		if (DBManager.dbUtil.updateUser(user)
				&& DBManager.dbUtil.invalidateFindPassword(url)) {
			response.sendRedirect("success.jsp?msg=" + "密码修改成功");
		} else {
			response.sendRedirect("fail.jsp?msg=" + "密码修改失败");
		}
	}
}
