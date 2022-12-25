package tech.asmussen.ranked;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard {
	
	public static List<Player> generateLeaderboard(List<Player> players, int size) {
		
		players.sort((playerA, playerB) -> playerB.getRating() - playerA.getRating());
		
		return new ArrayList<>(players.subList(0, size));
	}
	
	public static int getAverageSkillRating(List<Player> leaderboard) {
		
		int ratingTotal = 0;
		
		for (Player player : leaderboard) {
			
			ratingTotal += player.getRating();
		}
		
		return ratingTotal / leaderboard.size();
	}
}
