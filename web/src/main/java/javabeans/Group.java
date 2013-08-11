package javabeans;

import java.util.ArrayList;

public class Group {
	// fields
	private int groupId;
	private ArrayList<User> userList;
	private String groupName;

	public Group() {

	}

	public Group(int groupId, ArrayList<User> userList, String groupName) {
		this.groupId = groupId;
		this.userList = userList;
		this.groupName = groupName;
	}

	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public ArrayList<User> getUserList() {
		return this.userList;
	}

	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
