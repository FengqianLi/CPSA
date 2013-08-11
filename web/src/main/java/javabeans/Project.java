package javabeans;

public class Project {
	private int pId;
	private String projectName;
	private int groupId;
	private boolean visible;
	private String description;
	private int ownerId;

	public Project() {

	}

	public Project(int pid, String name, int groupId, boolean visible,
			String description, int ownerId) {
		this.projectName = name;
		this.groupId = groupId;
		this.visible = visible;
	}

	public void setProjectId(int pid) {
		this.pId = pid;
	}

	public int getProjectId() {
		return this.pId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getOwnerId() {
		return this.ownerId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setProjectName(String name) {
		this.projectName = name;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getGroupId() {
		return this.groupId;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean getVisible() {
		return this.visible;
	}
}
