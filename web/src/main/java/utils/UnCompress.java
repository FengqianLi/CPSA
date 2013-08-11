package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnCompress {

	private static final Logger logger = LoggerFactory
			.getLogger(UnCompress.class);
	private static OutputStream outputStream;

	public static ArrayList<String> unZip(String zipfile, String destDir) {

		logger.debug("upziping file: {}", zipfile);
		ArrayList<String> unzippedFileList = new ArrayList<String>();

		destDir = destDir.endsWith("/") ? destDir : destDir + "/";
		byte b[] = new byte[1024];
		int length;

		ZipFile zipFile;
		try {
			zipFile = new ZipFile(new File(zipfile));
			@SuppressWarnings("unchecked")
			Enumeration<ZipEntry> enumeration = zipFile.getEntries();
			ZipEntry zipEntry = null;

			while (enumeration.hasMoreElements()) {
				zipEntry = (ZipEntry) enumeration.nextElement();
				File loadFile = new File(destDir
						+ Replace.replaceAll(zipEntry.getName()));
				unzippedFileList.add(loadFile.getAbsolutePath());

				if (zipEntry.isDirectory()) {
					loadFile.mkdirs();
				} else {
					if (!loadFile.getParentFile().exists())
						loadFile.getParentFile().mkdirs();

					outputStream = new FileOutputStream(loadFile);
					InputStream inputStream = zipFile.getInputStream(zipEntry);

					while ((length = inputStream.read(b)) > 0)
						outputStream.write(b, 0, length);
				}
			}
			logger.debug("unCompress file {} success!", zipfile);
			return unzippedFileList;
		} catch (IOException e) {
			e.printStackTrace();
			return unzippedFileList;
		}
	}
}