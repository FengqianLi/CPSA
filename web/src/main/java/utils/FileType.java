package utils;

public class FileType {

	public static boolean isSourceCode(String fileName) {
		if (fileName.endsWith(".cpp") || fileName.endsWith(".c")
				|| fileName.endsWith(".cc") || fileName.endsWith(".h"))
			return true;
		else
			return false;
	}

	public static boolean isDir(String fileName) {
		if (fileName.endsWith("/"))
			return true;
		else {
			String str = fileName.substring(fileName.lastIndexOf("/") + 1,
					fileName.length());
			if (str.startsWith(".") || str.contains(".") == false)
				return true;
			else
				return false;
		}
	}

	public static boolean isCompressedFile(String fileName) {
		if (fileName.endsWith(".zip") || fileName.endsWith(".rar")
				|| fileName.endsWith(".tar.gz") || fileName.endsWith(".tar"))
			return true;
		else
			return false;
	}
}
