package com.jda.recommendation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jda.util.FileUtil;

public class DefaultRecommendationEngine implements RecommendationEngine {
	private static Map<String, String> moviesMap = new HashMap<String, String>();
	private static int NUMBER_OF_RECOMMENDATIONS = 10;
	private static int USER_ID = 716;
	private static final Logger logger = LoggerFactory.getLogger(DefaultRecommendationEngine.class);
	
	static {
		init();
	}
	
	static void init() {
		loadMovies();
	}

	private static void loadMovies() {
		FileUtil obj = new FileUtil();
		String movies = "dataset/movies.txt";
		moviesMap = obj.load(movies, "\\|");
		logger.info("movies count: {}", moviesMap.size());
	}

	public static void main(String[] args) throws Exception {
		RecommendationEngine recoEngine = new DefaultRecommendationEngine();
		recoEngine.recommendForUser(USER_ID);
	}

	private static List<RecommendedItem> recommend(DataModel model, int userid)
			throws Exception {
		//UserSimilarity similarity = new TanimotoCoefficientSimilarity(model);
		//UserSimilarity similarity = new LogLikelihoodSimilarity(model);
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);

		//TODO: can have user-based + item-based recommendation
		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

		List<RecommendedItem> recommendations = recommender.recommend(userid, NUMBER_OF_RECOMMENDATIONS);
		return recommendations;
	}

	@Override
	public String recommendForUser(int userId) throws Exception {
		long startTime = System.currentTimeMillis();
		String movieName = "";
		List<String> output = new ArrayList<String>();
		long movieId = 0;
		DataModel model = new FileDataModel(new File("dataset/ratings.txt"));
		logger.info("*** movie lens recommendation ****");
		List<RecommendedItem> recommendations = recommend(model, userId);
		for (RecommendedItem recommendation : recommendations) {
			movieId = recommendation.getItemID();
			movieName = moviesMap.get(Long.toString(movieId));
			output.add(movieId + " - " + movieName + " - " + recommendation.getValue());
		}
		long endTime = System.currentTimeMillis();
		logger.info("Time taken in ms: {}", (endTime - startTime));
		return output.toString();
	}
}
