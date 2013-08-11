package javabeans;

import java.util.HashMap;

public class AnalyzeResult {
	private int sid;
	private String fileName;
	private int error_num;
	private HashMap<String, Integer> analyzerMap;

	public AnalyzeResult() {

	}

	public AnalyzeResult(int sid, String fileName, int error_num,
			HashMap<String, Integer> analyzerMap) {
		this.sid = sid;
		this.fileName = fileName;
		this.error_num = error_num;
		this.analyzerMap = analyzerMap;
	}

	public int getSid() {
		return this.sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getErrorNum() {
		return this.error_num;
	}

	public void setErrorNum(int error_num) {
		this.error_num = error_num;
	}

	public HashMap<String, Integer> getAnalyzerMap() {
		return this.analyzerMap;
	}

	public void setAnalyzerMap(HashMap<String, Integer> analyzerMap) {
		this.analyzerMap = analyzerMap;
	}
}
