package dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.String;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The database operation agents
 * 
 * @author damon
 * 
 */
public class DBAgent {
	private Connection conn = null;
	private static final Logger logger = LoggerFactory.getLogger(DBAgent.class);

	/**
	 * Construct, initialize the connection to the database
	 */
	public DBAgent() {
		// Initial the connection to the database
		try {
			conn = DBConnPool.getConnection("connectPool");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (conn == null)
			logger.info("Get the connection to the database from the Connection Pool failed");
		else
			logger.info("DBAgent get a connection from the Connection Pool");
	}

	/**
	 * Get the resultSet of querying the databases
	 * 
	 * @param sql
	 * @return the query ResultSet
	 */
	public ResultSet select(String sql) {
		logger.debug("sql: {}", sql);

		ResultSet rs = null;
		if (conn == null)
			return null;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Insert a record
	 * 
	 * @param sql
	 *            , the sql statement of inserting a record to be executed
	 * @return true or false
	 * @throws SQLException
	 * @throws SQLException
	 */
	public boolean insert(String sql) throws SQLException {
		logger.debug("sql: {}", sql);
		if (conn == null) {
			return false;
		}
		Statement stmt = conn.createStatement();
		if (stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS) >= 0)
			return true;
		else
			return false;
	}

	/**
	 * Update a record
	 * 
	 * @param sql
	 *            , the sql statement of updating a record to be executed
	 * @return The number of records updated, return -1 if not connect
	 * @throws SQLException
	 */
	public int update(String sql) throws SQLException {
		logger.debug("sql: {}", sql);
		if (conn == null) {
			return -1;
		}
		Statement stmt = conn.createStatement();
		int updateNum = stmt.executeUpdate(sql);
		return updateNum;
	}

	/**
	 * Delete a record
	 * 
	 * @param sql
	 *            , the sql statement of deleting a record to be executed
	 * @return The number of records deleted, return -1 if not connect
	 * @throws SQLException
	 */
	public int delete(String sql) throws SQLException {
		logger.debug("sql: {}", sql);
		if (conn == null) {
			return -1;
		}
		Statement stmt = conn.createStatement();
		int deleteNum = stmt.executeUpdate(sql);
		return deleteNum;
	}

	public Connection getConnection() {
		return conn;
	}

	/**
	 * Print the state of Connection Pool
	 */
	public void printPoolState() {
		DBConnPool.printDriverStats();
	}

	/**
	 * Return the connection to the Connection Pool
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}
}
