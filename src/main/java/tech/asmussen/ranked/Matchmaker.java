package tech.asmussen.ranked;

import java.util.Queue;

public class Matchmaker {
	
	public static Player[] findMatch(Queue<Player> queue, boolean isSkillBased) {
		
		if (queue.size() < 2) {
			
			return null;
		}
		
		// If skill based matchmaking isn't enabled, return the first two players in the queue as a match.
		if (!isSkillBased) {
			
			return new Player[] { queue.poll(), queue.poll() };
		}
		
		// Get the first player in the queue.
		Player playerA = queue.poll();
		
		// Find a player in the queue with a similar rating to player A.
		for (Player playerB : queue) {
			
			// Create a booster for player A's rating based on their win streak,
			// if it's positive it means they're on a win streak and should be matched with a player with a higher rating.
			// Otherwise, they're on a loss streak and should be matched with a player with a lower rating.
			final int winStreak = playerA.getStreak();
			final int ratingBooster = winStreak > 0 ? winStreak * 10 : winStreak * -10;
			
			final double probability = Elo.getProbability(playerA.getRating() + ratingBooster, playerB.getRating());
			
			// If the probability of player A winning against player B is between 0.45 and 0.55, return the two players as a match.
			if (probability > 0.45 && probability < 0.55) {
				
				queue.remove(playerB);
				
				return new Player[] { playerA, playerB };
			}
		}
		
		// If no fair match was found, put the player back in the queue.
		queue.add(playerA);
		
		return null;
	}
}
