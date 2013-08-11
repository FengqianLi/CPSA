package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javabeans.Project;
import javabeans.Submit;
import javabeans.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.SubmitManager;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import protocol.Config;

import utils.Executor;
import utils.MailTo;
import utils.Replace;

import dbManager.DBManager;

public class UploadFileServlet2 extends HttpServlet {
	private static final long serialVersionUID = 56890894234786L;
	private static final Logger logger = LoggerFactory
			.getLogger(UploadFileServlet2.class);

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		request.setCharacterEncoding("UTF-8");

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (isMultipart == true) {
			try {
				User user = (User) request.getSession().getAttribute("user");
				Project project = new Project();
				project.setOwnerId(user.getUserId());
				project.setGroupId(user.getGroupId());
				Submit submit = new Submit();
				submit.setUserId(user.getUserId());
				submit.setProjectId(project.getProjectId());
				char[] analyzers = new char[50];
				for (int i = 0; i < analyzers.length; i++) {
					analyzers[i] = '0';
				}

				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);

				List<FileItem> fileItems = upload.parseRequest(request);
				Iterator<FileItem> iter = fileItems.iterator();

				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (item.isFormField()) {
						String name = item.getFieldName();
						String value = item.getString();
						if (0 == name.compareTo("projectName")) {
							project.setProjectName(value);
						} else if (name.startsWith("projectDescription")) {
							project.setDescription(value);
						} else if (0 == name.compareTo("visible")) {
							project.setVisible((0 == value.compareTo("on") ? true
									: false));
						} else if (0 == name.compareTo("submitDescription")) {
							submit.setDescription(value);
						} else if (name.compareTo("analyzer") == 0) {
							analyzers[Integer.parseInt(value) - 1] = '1';
						}
						logger.trace("name: {}, value: {}", name, value);
					} else {
						project.setProjectId(DBManager.dbUtil
								.addProject(project));
						submit.setProjectId(project.getProjectId());
						submit.setAnalyzers(new String(analyzers));
						submit.setSubmitId(DBManager.dbUtil.addSubmit(submit));

						String projectPath = Replace.replaceAll((Config.prop
								.getProperty("test_path")
								+ "/"
								+ DBManager.dbUtil
										.getGroupNameByGroupId(project
												.getGroupId()) + "/" + project
								.getProjectName()));
						String fileName = Replace.replaceAll(item.getName());
						logger.trace("fileName: {}", fileName);
						if (fileName != null && fileName.compareTo("") != 0) {
							File dirFile = new File(projectPath);
							if (!dirFile.exists())
								dirFile.mkdirs();
							item.write(new File(projectPath + "/" + fileName));
							logger.trace("File: {}/{} upload succeed!",
									projectPath, fileName);
							SubmitManager.getInstance().addFile(projectPath,
									projectPath + "/" + fileName,
									submit.getSubmitId(),
									project.getProjectId());
						}
					}
				}

				Executor.execute(submit.getSubmitId());
				logger.debug("submit done!");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			logger.warn("the enctype must be multipart/form-data");
		}

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/upload.jsp?type=2");
		dispatcher.forward(request, response);
		return;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		doPost(request, response);
	}
}