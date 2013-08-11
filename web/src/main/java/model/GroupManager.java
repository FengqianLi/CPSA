package model;

import java.util.ArrayList;

import javabeans.Group;
import javabeans.User;
import dbManager.DBManager;

public class GroupManager implements DBManager {
	private static GroupManager groupManager;

	public GroupManager() {

	}

	public static synchronized GroupManager getInstance() {
		if (groupManager == null) {
			groupManager = new GroupManager();
		}
		return groupManager;
	}

	/**
	 * 
	 */
	public ArrayList<Group> getGroupListByUser(User user) {
		ArrayList<Group> groupList;
		if (user.getRole().compareTo("manager") == 0) {
			groupList = dbUtil.getGroupList();
		} else {
			groupList = dbUtil.getGroupListByUser(user);
		}

		for (Group group : groupList) {
			ArrayList<User> userList;
			if (user.getRole().compareTo("member") == 0) {
				userList = new ArrayList<User>();
				userList.add(user);
			} else {
				userList = dbUtil.getUserListByGroupId(group.getGroupId());
			}
			group.setUserList(userList);
		}

		return groupList;
	}

	public String getHtmlUrl(int sId) {
		return dbUtil.getGroupNameBySid(sId).replace(" ", "_") + "/"
				+ dbUtil.getProjectNameBySid(sId).replace(" ", "_");
	}
}
