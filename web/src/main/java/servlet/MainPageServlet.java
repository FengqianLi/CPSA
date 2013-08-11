package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class MainPageServlet
 */
public class MainPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(MainPageServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainPageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String mode = (String) request.getSession()
				.getAttribute("mainPageMode");
		if (null == mode)
			mode = "upload";

		logger.debug("main page mode: {}", mode);

		// request.getSession().setAttribute("mainPageMode", mode);

		if (0 == mode.compareTo("submitlist")) {
			int userId = (Integer) request.getSession().getAttribute("userId");
			int pId = (Integer) request.getSession().getAttribute("projectId");
			response.sendRedirect("submitList.jsp?userId=" + userId
					+ "&projectId=" + pId);
		} else if (0 == mode.compareTo("upload")) {
			response.sendRedirect("Upload");
		} else if (0 == mode.compareTo("analyzeResult")) {
			int sid = (Integer) request.getSession().getAttribute("sid");
			String directory = (String) request.getSession().getAttribute(
					"directory");
			response.sendRedirect("analyzeResult.jsp?sid=" + sid
					+ "&directory=" + directory);
		} else if (0 == mode.compareTo("analyzeResult2")) {
			String sid1 = (String) request.getSession().getAttribute("sid1");
			String sid2 = (String) request.getSession().getAttribute("sid2");
			String directory = (String) request.getSession().getAttribute(
					"directory");
			response.sendRedirect("analyzeResult2.jsp?sid1=" + sid1 + "&sid2="
					+ sid2 + "&directory=" + directory);
		} else if (0 == mode.compareTo("modify")) {
			response.sendRedirect("user_modify.jsp");
		} else {
			response.sendRedirect("Upload");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
