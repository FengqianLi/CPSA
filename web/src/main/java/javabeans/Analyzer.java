/**
 * 
 */
package javabeans;

/**
 * @author damon
 * 
 */
public class Analyzer {
	// fields
	private int aid;
	private String name;
	private String errorId;
	private String description;
	private String solution;

	public Analyzer() {

	}

	public Analyzer(int aid, String name, String errorId, String description,
			String solution) {
		this.aid = aid;
		this.name = name;
		this.errorId = errorId;
		this.description = description;
		this.solution = solution;
	}

	public int getAId() {
		return this.aid;
	}

	public void setAId(int aid) {
		this.aid = aid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public String getErrorId() {
		return this.errorId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getSolution() {
		return this.solution;
	}
}
