package se.noren.othello.backend;


import java.util.List;

public interface HighScoreService {
	public List<String> getAllApplications();
	public List<HighScore> getAllHighScores(String application);
	public void addHighScores(HighScore highscore);
}
