package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.MD5;
import javabeans.User;

import dbManager.DBManager;

public class UserManager implements DBManager {
	private static UserManager userManager;
	private static final Logger logger = LoggerFactory
			.getLogger(UserManager.class);

	public UserManager() {

	}

	public static synchronized UserManager getInstance() {
		if (userManager == null) {
			userManager = new UserManager();
		}
		return userManager;
	}

	/**
	 * 验证用户登入时输入的用户名、密码是否正确
	 * 
	 * @param userName
	 * @param password
	 * @throws UserNotFoundException
	 * @throws PasswordErrorException
	 */
	public int validateLogin(String userName, String password) {
		MD5 oMD5 = new MD5();
		String pwdmd5 = oMD5.getMD5ofStr(password);
		User user = dbUtil.getUserByUserName(userName);
		if (user == null)
			return protocol.UserProtocol.USER_NOT_FOUND;
		if (!user.getPassword().equals(pwdmd5))
			return protocol.UserProtocol.PASSWORD_ERROR;
		logger.debug("user status: {}", user.getStatus());

		if (user.getStatus() == false)
			return protocol.UserProtocol.USER_UNACTIVE;

		return protocol.UserProtocol.VALIDATE_SUCCESS;
	}

	/**
	 * 注册用户
	 * 
	 * @param userName
	 * @param password
	 * @param email
	 * @param groupName
	 * @return
	 */
	public boolean regUser(String userName, String password, String email,
			int groupId) {
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setEmail(email);
		user.setGroupId(groupId);

		return dbUtil.addUser(user);
	}

	public boolean updatePassword(int userId, String password) {
		User user = dbUtil.getUserByUserId(userId);
		user.setPassword(password);

		return dbUtil.updateUser(user);
	}
}
