package se.noren.othello.backend;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for Legends app family highscore services. 
 */
@Controller
@RequestMapping("/highscores")
public class LegendsHighScoreController {
	private static final long serialVersionUID = 1L;

	@Autowired
	HighScoreService highScoresService;

	/**
	 * @return Fetch all registered applications in the highscore database.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getAllApplications() {
		List<String> allApplications = highScoresService.getAllApplications();
		return new ModelAndView("highScoresView", BindingResult.MODEL_KEY_PREFIX + "applications", allApplications);
	}
	
	/**
	 * Fetch all highscores for a particular application.
	 * @param application Name of application
	 * @return 
	 */
	@RequestMapping(value = "/{application}", method = RequestMethod.GET)
	public ModelAndView getAllHighScores(@PathVariable String application) {
		List<HighScore> allHighScores = highScoresService.getAllHighScores(application);
		return new ModelAndView("highScoresView", BindingResult.MODEL_KEY_PREFIX + "scores", allHighScores);
	}
	
	/**
	 * Add a new highscore to the database if it makes it to the high score list.
	 * @param application Name of application
	 * @param owner Owner of the highscore
	 * @param score Score as whole number
	 * @param level Level of player reaching score.
	 * @return The created score.
	 */
	@RequestMapping(value = "/{application}", method = RequestMethod.POST)
	public ModelAndView addHighScores(@PathVariable String application, 
			                          @RequestParam String owner, 
			                          @RequestParam long score,
			                          @RequestParam long level
			                          ) {
		
		HighScore highScore = new HighScore(owner, score, application, new Date().getTime(), level);
		highScoresService.addHighScores(highScore);
		return new ModelAndView("highScoresView", BindingResult.MODEL_KEY_PREFIX + "scores", highScore);
	}
}
