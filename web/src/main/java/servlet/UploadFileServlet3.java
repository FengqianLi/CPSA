package servlet;

import java.util.ArrayList;

import javabeans.Project;
import javabeans.Submit;
import javabeans.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.Executor;

import dbManager.DBManager;

public class UploadFileServlet3 extends HttpServlet {
	private static final long serialVersionUID = 56890894234786L;
	private static final Logger logger = LoggerFactory
			.getLogger(UploadFileServlet3.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		request.setCharacterEncoding("UTF-8");

		User user = (User) request.getSession().getAttribute("user");
		Project project = DBManager.dbUtil.getProjectByProjectId(Integer
				.parseInt(request.getParameter("projectId")));

		String groupName = DBManager.dbUtil.getGroupNameByGroupId(project
				.getGroupId());
		String description = "ReCompile project " + project.getProjectName()
				+ " of group " + groupName;

		Submit submit = new Submit();
		submit.setUserId(user.getUserId());
		submit.setProjectId(project.getProjectId());
		submit.setDescription(description);

		String[] analyzerSelect = request.getParameterValues("analyzer");
		char[] analyzers = new char[50];
		for (int i = 0; i < analyzers.length; i++) {
			analyzers[i] = '0';
		}
		for (String analyzer : analyzerSelect) {
			analyzers[Integer.parseInt(analyzer) - 1] = '1';
		}

		submit.setAnalyzers(new String(analyzers));

		int sId = DBManager.dbUtil.addSubmit(submit);
		ArrayList<String> fileList = DBManager.dbUtil
				.getProjectFileListByProjectId(project.getProjectId());
		for (String relativeFilePath : fileList) {
			DBManager.dbUtil.addSubmitFile(sId, relativeFilePath);
		}

		Executor.execute(sId);
		logger.debug("Submit done!");

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/upload.jsp?type=3");
		dispatcher.forward(request, response);
		return;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		doPost(request, response);
	}
}