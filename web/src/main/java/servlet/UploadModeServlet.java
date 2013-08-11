package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.DBAgent;

/**
 * Servlet implementation class UploadModeServlet
 */
public class UploadModeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(UploadModeServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadModeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		if (mode == null) {
			String uploadMode = (String) request.getSession().getAttribute(
					"uploadMode");
			if (uploadMode != null)
				mode = uploadMode;
			else
				mode = "1";
		}
		logger.debug("Get upload mode: {}", mode);

		request.getSession().setAttribute("mainPageMode", "upload");
		request.getSession().setAttribute("uploadMode", mode);
		response.sendRedirect("upload.jsp");
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
