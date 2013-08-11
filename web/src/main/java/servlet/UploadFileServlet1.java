package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import utils.Pair;
import utils.Replace;

import dbManager.DBManager;

public class UploadFileServlet1 extends HttpServlet {
	private static final long serialVersionUID = 56890894234786L;
	private static final Logger logger = LoggerFactory
			.getLogger(UploadFileServlet1.class);

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		request.setCharacterEncoding("UTF-8");

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (isMultipart == true) {
			try {
				ArrayList<Pair> fileList = new ArrayList<Pair>();
				Project project = null;
				String description = null;
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
						if (0 == name.compareTo("pId")) {
							project = DBManager.dbUtil
									.getProjectByProjectId(Integer
											.parseInt(value));
						} else if (name.startsWith("dir")) {
							Pair pair = new Pair();
							pair.directory = value;
							fileList.add(pair);
						} else if (0 == name.compareTo("description")) {
							description = value;
						} else if (name.startsWith("analyzer")) {
							analyzers[Integer.parseInt(value) - 1] = '1';
						}
						logger.trace("name: {}, value: {}", name, value);
					} else {
						String fileName = item.getName();
						logger.trace("fileName: {}", fileName);
						fileList.get(fileList.size() - 1).uploadFileName = fileName;
						fileList.get(fileList.size() - 1).item = item;
					}
				}

				User user = (User) request.getSession().getAttribute("user");
				Submit submit = new Submit();
				submit.setUserId(user.getUserId());
				submit.setProjectId(project.getProjectId());
				submit.setDescription(description);
				submit.setAnalyzers(new String(analyzers));
				int sId = DBManager.dbUtil.addSubmit(submit);

				String initPath = (Config.prop.getProperty("test_path")
						+ "/"
						+ DBManager.dbUtil.getGroupNameByGroupId(project
								.getGroupId()) + "/" + project.getProjectName());
				for (Pair pair : fileList) {
					String projectPath = Replace.replaceAll(initPath);
					logger.trace("Pairs: {} : {}", pair.directory,
							pair.uploadFileName);

					String dir = Replace.replaceAll(((pair.directory
							.startsWith("/")) ? pair.directory : "/"
							+ pair.directory));
					dir += dir.endsWith("/") ? "" : "/";
					String fileName = Replace.replaceAll(pair.uploadFileName);
					if (fileName != null && fileName.compareTo("") != 0) {
						File dirFile = new File(projectPath + dir);
						if (!dirFile.exists())
							dirFile.mkdirs();
						pair.item.write(new File(projectPath + dir + fileName));
						logger.trace("File: {} upload succeed!", projectPath
								+ dir + fileName);
						SubmitManager.getInstance().addFile(projectPath,
								projectPath + dir + fileName, sId,
								project.getProjectId());
					}
				}

				Executor.execute(sId);
				logger.debug("Submit done!");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			logger.warn("the enctype must be multipart/form-data");
		}

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/upload.jsp?type=1");
		dispatcher.forward(request, response);
		return;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		doPost(request, response);
	}
}