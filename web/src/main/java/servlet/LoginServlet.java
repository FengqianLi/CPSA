package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javabeans.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.UserManager;
import dbManager.DBManager;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(LoginServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		PrintWriter out = response.getWriter();

		int ret = UserManager.getInstance().validateLogin(userName, password);
		if (ret == protocol.UserProtocol.VALIDATE_SUCCESS) {
			// 通过登入验证
			User user = DBManager.dbUtil.getUserByUserName(userName);
			request.getSession().setAttribute("user", user);
			if (DBManager.dbUtil.getGroupNameByGroupId(user.getGroupId())
					.compareTo("Admin") == 0)
				response.sendRedirect("adminPage/admin.jsp");
			else
				response.sendRedirect("index.jsp");
		} else if (ret == protocol.UserProtocol.USER_NOT_FOUND) {
			response.sendRedirect("fail.jsp?msg=this user does not exists");
		} else if (ret == protocol.UserProtocol.PASSWORD_ERROR) {
			response.sendRedirect("fail.jsp?msg=password error");
		} else if (ret == protocol.UserProtocol.USER_UNACTIVE) {
			response.sendRedirect("fail.jsp?msg=this account is inactive now, please waiting for the administator's verification");
		}

		out.flush();
		out.close();
	}
}
