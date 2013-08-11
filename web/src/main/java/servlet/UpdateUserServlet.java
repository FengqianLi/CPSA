package servlet;

import java.io.IOException;

import javabeans.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbManager.DBManager;

/**
 * Servlet implementation class UpdateUserServlet
 */
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateUserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User user = DBManager.dbUtil.getUserByUserId(Integer.parseInt(request
				.getParameter("id")));
		String role = request.getParameter("role");
		int groupId = Integer.parseInt(request.getParameter("group"));
		user.setGroupId(groupId);
		user.setRole(role);

		if (DBManager.dbUtil.updateUser(user)) {
			response.sendRedirect("success.jsp?msg=update password success");
		} else {
			response.sendRedirect("fail.jsp?msg=update password failed");
		}
	}

}
