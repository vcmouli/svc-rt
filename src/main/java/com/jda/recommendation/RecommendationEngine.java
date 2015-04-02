package com.jda.recommendation;

public interface RecommendationEngine {
	String recommendForUser(int userId) throws Exception;
}
