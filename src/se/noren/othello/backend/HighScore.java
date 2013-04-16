package se.noren.othello.backend;


public class HighScore {
	
	private String owner;
	private long date;
	private String application;
	private long    score;
	private long  level;
	
	public HighScore() {
		
	}
	
	public HighScore(String owner, long score, String application, long date, long level) {
		this.score = score;
		this.owner = owner;
		this.application = application;
		this.date = date;
		this.level = level;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}
	
}