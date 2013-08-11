package model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.FileType;
import utils.UnCompress;
import dbManager.DBManager;

public class SubmitManager implements DBManager {
	private static SubmitManager submitManager;
	private static final Logger logger = LoggerFactory
			.getLogger(SubmitManager.class);

	public SubmitManager() {

	}

	public static synchronized SubmitManager getInstance() {
		if (submitManager == null) {
			submitManager = new SubmitManager();
		}
		return submitManager;
	}

	public void addFile(String projectPath, String fullFilePath, int sId,
			int pId) {
		logger.debug("processing submit file: {}", fullFilePath);

		String relativeFilePath = fullFilePath.replaceFirst(projectPath, ".");

		int index = relativeFilePath.lastIndexOf("/");
		if (1 != index) {
			String tmpStr = relativeFilePath.substring(0, index);
			while (tmpStr.length() > "./".length()) {
				dbUtil.addSubmitFile(sId, tmpStr);
				dbUtil.addProjectFile(pId, tmpStr);
				logger.debug("add dir: {}", tmpStr);
				tmpStr = tmpStr.substring(0, tmpStr.lastIndexOf("/"));
			}
		}
		if (FileType.isDir(fullFilePath)) {
			dbUtil.addSubmitFile(sId, relativeFilePath);
			dbUtil.addProjectFile(pId, relativeFilePath);
			logger.debug("add submit file of dir: {}", relativeFilePath);
		} else if (FileType.isSourceCode(fullFilePath)) {
			dbUtil.addSubmitFile(sId, relativeFilePath);
			dbUtil.addProjectFile(pId, relativeFilePath);
			logger.debug("add submit file of source code: {}", relativeFilePath);
		} else if (FileType.isCompressedFile(fullFilePath)) {
			index = fullFilePath.lastIndexOf("/");
			String path = fullFilePath.substring(0, index + 1);
			String fileName = fullFilePath.substring(index + 1,
					fullFilePath.length());
			ArrayList<String> fileList = new ArrayList<String>();
			if (fullFilePath.endsWith(".zip")) {
				fileList = UnCompress.unZip(path + fileName, path);
			} else if (fileName.endsWith("rar")) {

			}

			for (String file : fileList) {
				logger.debug("list: {}", file);
				addFile(projectPath, file, sId, pId);
			}
		}
	}
}
