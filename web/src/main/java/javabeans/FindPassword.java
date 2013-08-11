package javabeans;

import java.sql.Timestamp;

public class FindPassword {
	private String url;
	private int userId;
	private Timestamp _time;
	private boolean active;

	public FindPassword() {

	}

	public FindPassword(String url, int userId, Timestamp _time) {
		this.url = url;
		this.userId = userId;
		this._time = _time;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setTimeStamp(Timestamp _time) {
		this._time = _time;
	}

	public Timestamp getTimestamp() {
		return this._time;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean getActive() {
		return this.active;
	}
}
