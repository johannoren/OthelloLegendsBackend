package se.noren.othello.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

@Service
public class HighScoreServiceImpl implements HighScoreService {

	@Override
	public List<HighScore> getAllHighScores(String application) {
		ArrayList<HighScore> list = new ArrayList<HighScore>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		// The Query interface assembles a query
		Query q = new Query("HighScore");
		q.addFilter("application", Query.FilterOperator.EQUAL, application);
		q.addFilter("score", FilterOperator.GREATER_THAN_OR_EQUAL, 0);
		q.addSort("score", SortDirection.DESCENDING);

		// PreparedQuery contains the methods for fetching query results
		// from the datastore
		PreparedQuery pq = datastore.prepare(q);

		for (Entity result : pq.asIterable()) {
			String owner = (String) result.getProperty("owner");
			Long date = (Long) result.getProperty("date");
			Long score = (Long) result.getProperty("score");
			Long level = (Long) result.getProperty("level");
			list.add(new HighScore(owner, score, application, date, level));
		}

		return list;
	}

	@Override
	public void addHighScores(HighScore highscore) {
		if (getAllApplications().contains(highscore.getApplication())) {

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			// The Query interface assembles a query
			Query q = new Query("HighScore");
			q.addFilter("application", Query.FilterOperator.EQUAL, highscore.getApplication());
			q.addFilter("owner", Query.FilterOperator.EQUAL, highscore.getOwner());

			// PreparedQuery contains the methods for fetching query results
			// from the datastore
			PreparedQuery pq = datastore.prepare(q);

			for (Entity result : pq.asIterable()) {
				Long score = (Long) result.getProperty("score");
				if (score > highscore.getScore()) {
					return;
				} else {
					datastore.delete(result.getKey());
				}
			}

			Entity hs = new Entity("HighScore");
			hs.setProperty("owner", highscore.getOwner());
			hs.setProperty("date", highscore.getDate());
			hs.setProperty("score", highscore.getScore());
			hs.setProperty("level", highscore.getLevel());
			hs.setProperty("application", highscore.getApplication());
			datastore.put(hs);
		}

	}

	@Override
	public List<String> getAllApplications() {
		String[] list = { "othellolegends" };
		return Arrays.asList(list);
	}
}
