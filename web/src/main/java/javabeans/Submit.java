package javabeans;

import java.sql.Timestamp;

public class Submit {
	private int submitId;
	private int userId;
	private int projectId;
	private String analyzers;
	private Timestamp submitTime;
	private String description;
	private boolean status;

	public Submit() {

	}

	public Submit(int userId, int projectId, Timestamp submitTime,
			String description, boolean status, String analyzers) {
		this.userId = userId;
		this.projectId = projectId;
		this.submitTime = submitTime;
		this.analyzers = analyzers;
		this.status = status;
		this.submitTime = submitTime;
	}

	public void setSubmitId(int submitId) {
		this.submitId = submitId;
	}

	public int getSubmitId() {
		return this.submitId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getProjectId() {
		return this.projectId;
	}

	public void setSubmitTime(Timestamp summitTime) {
		this.submitTime = summitTime;
	}

	public Timestamp getSubmitTime() {
		return this.submitTime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setAnalyzers(String analyzers) {
		this.analyzers = analyzers;
	}

	public String getAnalyzers() {
		return this.analyzers;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean getStatus() {
		return this.status;
	}
}
