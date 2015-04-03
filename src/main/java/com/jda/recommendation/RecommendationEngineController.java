package com.jda.recommendation;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommendation")
public class RecommendationEngineController {
	@RequestMapping(method = RequestMethod.GET, value = "{userid}")
	public String getRecommendationForUser(@PathVariable int userid) throws Exception {
		RecommendationEngine recoEngine = new DefaultRecommendationEngine();
		return recoEngine.recommendForUser(userid);
	}
}
