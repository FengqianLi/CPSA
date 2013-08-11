package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.MD5;

import model.UserManager;

/**
 * Servlet implementation class SignupServlet
 */
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(SignupServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignupServlet() {
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
		int groupId = Integer.parseInt(request.getParameter("groupId"));
		String email = request.getParameter("email");
		MD5 oMD5 = new MD5();
		String pwdmd5 = oMD5.getMD5ofStr(password);

		logger.debug(userName + "," + password + "," + groupId + "," + email);
		if (UserManager.getInstance().regUser(userName, pwdmd5, email, groupId)) {
			response.sendRedirect("success.jsp?msg=register succeed, please waiting for administator's verification!");
		} else {
			response.sendRedirect("fail.jsp?msg=register failed!");
		}

	}
}
