package javabeans;

public class User {
	// Fields
	private int userId;
	private String userName;
	private String password;
	private String email;
	private String role;
	private int groupId;
	private boolean active;

	// Constructor
	public User() {

	}

	public User(int userId, String password, String userName, String email,
			String role, int groupId, boolean active) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.role = role;
		this.active = active;
	}

	// Property Accessors
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public boolean getStatus() {
		return this.active;
	}

	public void setStatus(boolean active) {
		this.active = active;
	}
}
