package dbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import javabeans.AnalyzeResult;
import javabeans.Analyzer;
import javabeans.FindPassword;
import javabeans.Group;
import javabeans.Project;
import javabeans.Submit;
import javabeans.User;

import dao.DBAgent;

public class DBUtil {
	private DBAgent anAgent = null;

	public DBUtil() {
		anAgent = new DBAgent();
	}

	/**
	 * Get the User model by userId, 使用在用户登入
	 * 
	 * @param userName
	 * @return the User model or null if no such userId exists
	 */
	public User getUserByUserName(String userName) {
		String sql = "select * from UserInfo where name = '" + userName + "'";
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return null;
			}

			User user = new User();
			user.setUserId(Integer.parseInt(rs.getString("uid")));
			user.setUserName(userName);
			user.setPassword(rs.getString("password"));
			user.setEmail(rs.getString("email"));
			user.setRole(rs.getString("role"));
			user.setGroupId(rs.getInt("gid"));
			user.setStatus(rs.getBoolean("active"));
			return user;
		} catch (SQLException e) {
			return null;
		}
	}

	public User getUserByUserId(int userId) {
		return getUserByUserName(getUserNameByUserId(userId));
	}

	public String getUserNameByUserId(int uId) {
		String sql = "select name from UserInfo where uid = " + uId;
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return null;
			}

			return rs.getString("name");
		} catch (SQLException e) {
			return null;
		}
	}

	public User getUserByEmail(String email) {
		String sql = "select * from UserInfo where email = '" + email + "'";
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return null;
			}

			User user = new User();
			user.setUserId(Integer.parseInt(rs.getString("uid")));
			user.setUserName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setEmail(email);
			user.setRole(rs.getString("role"));
			user.setGroupId(rs.getInt("gid"));
			user.setStatus(rs.getBoolean("active"));
			return user;
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Add a new user, 使用在注册新用户等情况
	 * 
	 * @param user
	 * @return true if add success or false
	 */
	public boolean addUser(User user) {
		String sql = "insert into UserInfo(name, password, email, gid)"
				+ "values('" + user.getUserName() + "', '" + user.getPassword()
				+ "', '" + user.getEmail() + "', '" + user.getGroupId() + "')";

		try {
			if (anAgent.insert(sql) == false) // Not Connect!
				return false;
			else
				return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * 
	 */
	public int addSubmit(Submit submit) {
		String sql = "insert into Submit(uid, pid, _time, description, analyzers) values(?, ?, ?, ?, ?)";
		try {
			Connection conn = anAgent.getConnection();
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			conn.setAutoCommit(false);

			pstmt.setInt(1, submit.getUserId());
			pstmt.setInt(2, submit.getProjectId());
			pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(4, submit.getDescription());
			pstmt.setString(5, submit.getAnalyzers());

			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			int sId = 0;
			if (rs.next()) {
				sId = rs.getInt(1);
			}
			conn.commit();
			conn.setAutoCommit(true);

			return sId;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int addProject(Project project) {
		String sql = "insert into Project(name, description, gid, ownerId, visible) values(?, ?, ?, ?, ?)";
		try {
			Connection conn = anAgent.getConnection();
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			conn.setAutoCommit(false);

			pstmt.setString(1, project.getProjectName());
			pstmt.setString(2, project.getDescription());
			pstmt.setInt(3, project.getGroupId());
			pstmt.setInt(4, project.getOwnerId());
			pstmt.setBoolean(5, project.getVisible());

			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			int pId = 0;
			if (rs.next()) {
				pId = rs.getInt(1);
			}
			conn.commit();
			conn.setAutoCommit(true);

			return pId;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 */
	public boolean addSubmitFile(int sId, String fileName) {
		String sql = "insert into SubmitFile(sid, name) values(" + sId + ", '"
				+ fileName + "')";

		try {
			if (anAgent.insert(sql) == false) // Not Connect!
				return false;
			else
				return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * 
	 */
	public boolean addProjectFile(int pId, String fileName) {
		String sql = "insert into PrjFile(pid, name) values(" + pId + ", '"
				+ fileName + "')";

		try {
			if (anAgent.insert(sql) == false) // Not Connect!
				return false;
			else
				return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean addFindPassword(FindPassword record) {
		String sql = "insert into FindPassword(url, uid, _time) values('"
				+ record.getUrl() + "', " + record.getUserId() + ", '"
				+ record.getTimestamp() + "')";

		try {
			if (anAgent.insert(sql) == false) // Not Connect!
				return false;
			else
				return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean invalidateFindPassword(String url) {
		String sql = "update FindPassword set active = false where url = '"
				+ url + "'";

		try {
			if (anAgent.update(sql) == -1) // Not Connect!
				return false;
			else
				return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public FindPassword getFindPasswordByUrl(String url) {
		String sql = "select * from FindPassword where url = '" + url + "'";
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return null;
			}

			FindPassword fd = new FindPassword();
			fd.setUrl(url);
			fd.setUserId(rs.getInt("uid"));
			fd.setTimeStamp(rs.getTimestamp("_time"));
			fd.setActive(rs.getBoolean("active"));
			return fd;
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Update the information of a user, 使用在修改密码等情况
	 * 
	 * @param user
	 * @return true if update success or false
	 */
	public boolean updateUser(User user) {
		String sql = "update UserInfo set password = '" + user.getPassword()
				+ "', email = '" + user.getEmail() + "', role = '"
				+ user.getRole() + "', gid = " + user.getGroupId()
				+ ", active = " + user.getStatus() + " where uid = '"
				+ user.getUserId() + "'";
		try {
			if (anAgent.update(sql) <= 0) // update error!
				return false;
			else
				return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Get user list of this group
	 * 
	 * @param groupId
	 * @return The ArrayList of all the users
	 */
	public ArrayList<User> getUserListByGroupId(int groupId) {
		String sql = "select * from UserInfo where gid = '" + groupId + "'";
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return null;
			}
			ArrayList<User> userList = new ArrayList<User>();
			do {
				User aUser = new User();
				aUser.setUserId(rs.getInt("uid"));
				aUser.setUserName(rs.getString("name"));
				userList.add(aUser);
			} while (rs.next());

			return userList;

		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Get project list of this group
	 * 
	 * @param groupId
	 * @return The ArrayList of all the users
	 */
	public ArrayList<Project> getProjectListByGroupId(int groupId) {
		String sql = "select * from Project where gid = '" + groupId + "'";
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return null;
			}
			ArrayList<Project> projectList = new ArrayList<Project>();
			do {
				Project project = new Project();
				project.setProjectName(rs.getString("name"));
				projectList.add(project);
			} while (rs.next());

			return projectList;

		} catch (SQLException e) {
			return null;
		}
	}

	public ArrayList<Project> getProjectList() {
		String sql = "select * from Project";
		ArrayList<Project> projectList = new ArrayList<Project>();
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return projectList;
			}
			do {
				Project project = new Project();
				project.setProjectId(rs.getInt("pid"));
				project.setProjectName(rs.getString("name"));
				project.setDescription(rs.getString("description"));
				project.setGroupId(rs.getInt("gid"));
				project.setOwnerId(rs.getInt("ownerId"));
				project.setVisible(rs.getBoolean("visible"));
				projectList.add(project);
			} while (rs.next());

			return projectList;

		} catch (SQLException e) {
			return projectList;
		}
	}

	public Project getProjectByProjectId(int pId) {
		String sql = "select * from Project where pid = '" + pId + "'";
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return null;
			}

			Project project = new Project();
			project.setProjectId(pId);
			project.setProjectName(rs.getString("name"));
			project.setGroupId(rs.getInt("gid"));
			project.setDescription(rs.getString("description"));
			project.setVisible(rs.getBoolean("visible"));
			project.setOwnerId(rs.getInt("ownerId"));
			return project;

		} catch (SQLException e) {
			return null;
		}
	}

	public ArrayList<String> getProjectFileListByProjectId(int pId) {
		String sql = "select * from PrjFile where pid = " + pId;
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return null;
			}

			ArrayList<String> projectFileList = new ArrayList<String>();
			do {
				projectFileList.add(rs.getString("name"));
			} while (rs.next());

			return projectFileList;
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Get submission list by project id and user id
	 * 
	 * @param user
	 *            id, project id
	 * @return The ArrayList of all the users
	 */
	public ArrayList<Submit> getSubmitListByUserIdandProjectId(int userId,
			int projectId, int pageNum, int pageSize) {
		String sql = "select * from Submit where uid = " + userId
				+ " and pid = " + projectId + " order by _time desc limit "
				+ (pageNum - 1) * pageSize + "," + pageSize;
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return null;
			}
			ArrayList<Submit> submitList = new ArrayList<Submit>();
			do {
				Submit submit = new Submit();
				submit.setSubmitId(rs.getInt("sid"));
				submit.setSubmitTime(rs.getTimestamp("_time"));
				submit.setDescription(rs.getString("description"));
				submit.setStatus(rs.getBoolean("status"));
				submitList.add(submit);
			} while (rs.next());

			return submitList;

		} catch (SQLException e) {
			return null;
		}
	}

	public int getSubmitSizeByUserIdandProjectId(int userId, int projectId) {
		String sql = "select count(*) from Submit where uid =" + userId
				+ " and pid = " + projectId;
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return 0;
			}

			return rs.getInt(1);

		} catch (SQLException e) {
			return 0;
		}
	}

	/**
	 * Get submission project list by user name
	 * 
	 * @param user
	 *            id
	 * @return The ArrayList of all the users
	 */
	public ArrayList<Project> getSubmitProjectListByUser(User user) {
		String sql = "select distinct Project.pid, Project.name from Submit, Project where Submit.uid = '"
				+ user.getUserId() + "' and Submit.pid = Project.pid";
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return null;
			}
			ArrayList<Project> projectList = new ArrayList<Project>();
			do {
				Project project = new Project();
				project.setProjectId(rs.getInt("pid"));
				project.setProjectName(rs.getString("name"));
				projectList.add(project);
			} while (rs.next());

			return projectList;

		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * 
	 * 
	 */
	public ArrayList<Project> getVisibleProjectListByUser(User user) {
		String sql;
		if (user.getRole().compareTo("member") == 0) {
			sql = "select * from Project where gid = "
					+ user.getGroupId()
					+ " and visible = true union select * from Project where ownerId = "
					+ user.getUserId();
		} else if (user.getRole().compareTo("leader") == 0) {
			sql = "select * from Project where gid = " + user.getGroupId();
		} else if (user.getRole().compareTo("manager") == 0) {
			sql = "select * from Project";
		} else {
			sql = "";
		}

		ArrayList<Project> projectList = new ArrayList<Project>();
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return projectList;
			}
			do {
				Project project = new Project();
				project.setProjectId(rs.getInt("pid"));
				project.setProjectName(rs.getString("name"));
				project.setDescription(rs.getString("description"));
				project.setGroupId(rs.getInt("gid"));
				project.setVisible(rs.getBoolean("visible"));
				project.setOwnerId(rs.getInt("ownerId"));
				projectList.add(project);
			} while (rs.next());

			return projectList;

		} catch (SQLException e) {
			return projectList;
		}
	}

	/**
	 * 
	 * 
	 */
	public ArrayList<Project> getRecompilableProjectListByUser(User user) {
		String sql;
		if (user.getRole().compareTo("member") == 0) {
			sql = "select * from Project where ownerId = " + user.getUserId();
		} else if (user.getRole().compareTo("leader") == 0) {
			sql = "select * from Project where gid = " + user.getGroupId();
		} else if (user.getRole().compareTo("manager") == 0) {
			sql = "select * from Project";
		} else {
			sql = "";
		}

		ArrayList<Project> projectList = new ArrayList<Project>();
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return projectList;
			}
			do {
				Project project = new Project();
				project.setProjectId(rs.getInt("pid"));
				project.setProjectName(rs.getString("name"));
				project.setDescription(rs.getString("description"));
				project.setGroupId(rs.getInt("gid"));
				project.setVisible(rs.getBoolean("visible"));
				project.setOwnerId(rs.getInt("ownerId"));
				projectList.add(project);
			} while (rs.next());

			return projectList;

		} catch (SQLException e) {
			return projectList;
		}
	}

	public ArrayList<Analyzer> getAnalyzerList() {
		String sql = "select * from Analyzer";
		ArrayList<Analyzer> analyzerList = new ArrayList<Analyzer>();
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return analyzerList;
			}
			do {
				Analyzer analyzer = new Analyzer();
				analyzer.setAId(rs.getInt("aid"));
				analyzer.setName(rs.getString("name"));
				analyzer.setErrorId(rs.getString("errorId"));
				analyzer.setDescription(rs.getString("description"));
				analyzer.setSolution(rs.getString("solution"));

				analyzerList.add(analyzer);
			} while (rs.next());

			return analyzerList;
		} catch (SQLException e) {
			return analyzerList;
		}
	}

	public Analyzer getAnalyzerByAnalyzerName(String analyzerName) {
		String sql = "select * from Analyzer where name = '" + analyzerName + "'";

		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return null;
			}

			Analyzer analyzer = new Analyzer();
			analyzer.setAId(rs.getInt("aid"));
			analyzer.setName(rs.getString("name"));
			analyzer.setErrorId(rs.getString("errorId"));
			analyzer.setDescription(rs.getString("description"));
			analyzer.setSolution(rs.getString("solution"));

			return analyzer;
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Get analyze result list by Submit Id and directory
	 */
	public ArrayList<AnalyzeResult> getAnalyzeResultListBySidandDirectory(
			int sid, String directory) {

		String sql = "select * from SubmitFile where name like '"
				+ directory
				+ "/%' and name not in (select name from SubmitFile where name like '"
				+ directory + "/%/%' and sid = " + sid + " )"
                + " and sid = "
                + sid;

		ArrayList<AnalyzeResult> analyzeResultList = new ArrayList<AnalyzeResult>();
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return analyzeResultList;
			}
			do {
				AnalyzeResult analyzeResult = new AnalyzeResult();
				analyzeResult.setSid(sid);
				analyzeResult.setFileName(rs.getString("name"));
				analyzeResult.setErrorNum(rs.getInt("error_num"));

				String sql2 = "select * from AnalyzeResult, Analyzer where sid = "
						+ sid
						+ " and fileName = '"
						+ analyzeResult.getFileName()
						+ "' and AnalyzeResult.aid = Analyzer.aid";
				ResultSet rs2 = anAgent.select(sql2);
				HashMap<String, Integer> analyzerResult = new HashMap<String, Integer>();
				try {
					while (rs2.next()) {
						analyzerResult.put(rs.getString("name"),
								Integer.parseInt(rs.getString("error_num")));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				analyzeResult.setAnalyzerMap(analyzerResult);

				analyzeResultList.add(analyzeResult);
			} while (rs.next());

			return analyzeResultList;

		} catch (SQLException e) {
			return analyzeResultList;
		}
	}

	public String getGroupNameBySid(int sId) {
		String sql = "select GroupInfo.name from Submit, UserInfo, GroupInfo where Submit.uid = UserInfo.uid and UserInfo.gid = GroupInfo.gid and Submit.sid = "
				+ sId;
		ResultSet rs = anAgent.select(sql);
		try {
			while (rs.next()) {
				return rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getProjectNameBySid(int sId) {
		String sql = "select Project.name from Project, Submit where Project.pid = Submit.pid and Submit.sid = "
				+ sId;
		ResultSet rs = anAgent.select(sql);
		try {
			while (rs.next()) {
				return rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	public AnalyzeResult getAnalyzeResultListBySidandFileName(int sid,
			String fileName) {
		AnalyzeResult analyzeResult = new AnalyzeResult();
		analyzeResult.setSid(sid);
		analyzeResult.setFileName(fileName);

		String sql = "select * from AnalyzeResult, Analyzer where sid = " + sid
				+ " and fileName = '" + fileName
				+ "' and AnalyzeResult.aid = Analyzer.aid";
		ResultSet rs = anAgent.select(sql);
		HashMap<String, Integer> analyzerResult = new HashMap<String, Integer>();
		try {
			while (rs.next()) {
				analyzerResult.put(rs.getString("name"),
						Integer.parseInt(rs.getString("error_num")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		analyzeResult.setAnalyzerMap(analyzerResult);

		return analyzeResult;
	}

	/**
	 * Get all the group information
	 * 
	 * @return The Arraylist of group information
	 */
	public ArrayList<Group> getGroupList() {
		String sql = "select * from GroupInfo where name != 'Admin' order by gid";
		ArrayList<Group> groupList = new ArrayList<Group>();
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return groupList;
			}
			do {
				Group group = new Group();
				group.setGroupId(rs.getInt("gid"));
				group.setGroupName(rs.getString("name"));
				groupList.add(group);
			} while (rs.next());

			return groupList;

		} catch (SQLException e) {
			return groupList;
		}
	}

	/**
	 * Get group information
	 * 
	 * @return The Arraylist of group information
	 */
	public ArrayList<Group> getGroupListByUser(User user) {
		String sql = "select * from GroupInfo where gid = " + user.getGroupId()
				+ " and name != 'Admin' order by name";
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return null;
			}
			ArrayList<Group> groupList = new ArrayList<Group>();
			do {
				Group group = new Group();
				group.setGroupId(rs.getInt("gid"));
				group.setGroupName(rs.getString("name"));
				groupList.add(group);
			} while (rs.next());

			return groupList;

		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Get the Group Name by group Id
	 * 
	 * @param groupId
	 * @return The group name or null if exists no group of group Id
	 */
	public String getGroupNameByGroupId(int groupId) {
		String sql = "select * from GroupInfo where gid = " + groupId;
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return null;
			}

			return rs.getString("name");
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param userName
	 * @param groupName
	 * @return
	 */
	public int getGroupIdByUserNameandGroupName(String userName,
			String groupName) {
		String sql = "select groupId from Groups where userName = '" + userName
				+ "'" + " and groupName = '" + groupName + "'";
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return -1;
			}

			return rs.getInt("groupId");
		} catch (SQLException e) {
			return -1;
		}
	}

	/**
	 * Add a new group.
	 * 
	 * @param group
	 *            name
	 * @return true if add group success or false
	 */
	public boolean addGroup(String groupName) {
		String sql = "insert into GroupInfo(name)" + " values('" + groupName
				+ "')";

		try {
			if (anAgent.insert(sql) == false) // Not Connect!
				return false;
			else
				return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean deleteGroup(String groupName) {
		String sql = "delete from GroupInfo where name = '" + groupName + "')";

		try {
			if (anAgent.delete(sql) == -1) // Not Connect!
				return false;
			else
				return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean deleteUser(int uid) {
		String sql = "delete from UserInfo where uid = " + uid;
		try {
			if (anAgent.delete(sql) == -1) // Not Connect!
				return false;
			else
				return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean deleteProject(int pid) {
		String sql = "delete from Project where pid = " + pid;
		try {
			if (anAgent.delete(sql) == -1) // Not Connect!
				return false;
			else
				return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Update the information of a Group, 使用在更改一个分组的名称等情况
	 * 
	 * @param group
	 * @return true if success or false
	 */
	public boolean updateGroup(Group group) {
		String sql = "update Groups set groupName = '" + group.getGroupName()
				+ "' where groupId = " + group.getGroupId();
		try {
			if (anAgent.update(sql) <= 0) // update error!
				return false;
			else
				return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Search whether exists an email. 用于注册时验证输入的email是否已存在
	 * 
	 * @param String
	 *            email
	 * @return true if exists such an email or has SQL exception and return
	 *         false if no such email
	 */
	public boolean existEmail(String email) {
		String sql = "select * from userInfo where email = '" + email + "'";
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return false;
			}

			return true;
		} catch (SQLException e) {
			return true;
		}
	}

	public ArrayList<User> getUserList() {
		String sql = "select * from UserInfo where active = true and name != 'admin'";
		ArrayList<User> userList = new ArrayList<User>();
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return userList;
			}
			do {
				User user = new User();
				user.setUserId(rs.getInt("uid"));
				user.setUserName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setGroupId(rs.getInt("gid"));
				user.setRole(rs.getString("role"));
				user.setStatus(true);
				userList.add(user);
			} while (rs.next());

			return userList;

		} catch (SQLException e) {
			return userList;
		}
	}

	public ArrayList<User> getInactiveUserList() {
		String sql = "select * from UserInfo where active = false";
		ArrayList<User> userList = new ArrayList<User>();
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return userList;
			}
			do {
				User user = new User();
				user.setUserId(rs.getInt("uid"));
				user.setUserName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setGroupId(rs.getInt("gid"));
				user.setRole(rs.getString("role"));
				user.setStatus(false);
				userList.add(user);
			} while (rs.next());

			return userList;

		} catch (SQLException e) {
			return userList;
		}
	}

	public int getGroupIdByGroupName(String groupName) {
		String sql = "select gid from GroupInfo where name = '" + groupName
				+ "'";
		ResultSet rs = anAgent.select(sql);
		try {
			if (!rs.next()) {
				return 0;
			}

		} catch (SQLException e) {
			return 0;
		}
		return 0;
	}
}
