package tech.asmussen.ranked;

import java.util.Queue;

public class Matchmaker {
	
	public static Player[] findMatch(Queue<Player> queue, boolean isSkillBased) {
		
		if (queue.size() < 2) {
			
			return null;
		}
		
		// If skill based matchmaking isn't enabled, return the first two players in the queue as a match.
		if (!isSkillBased) {
			
			return new Player[]{queue.poll(), queue.poll()};
		}
		
		// Get the first player in the queue.
		Player playerA = queue.poll();
		
		// Find a player in the queue with a similar rating to player A.
		for (Player playerB : queue) {
			
			/*
			 The internal rating is the players current rating plus their streak times 10.
			 A win sets the streak to 1, a loss sets the streak to -1, and a draw sets the streak to 0.
			 
			 Example 1:
			 - Rating: 1,000
			 - Streak: 5
			 - Internal Rating: 1,050
			 - Formula: Rating + Streak * 10
			 
			 Example 2:
			 - Rating: 1,000
			 - Streak: -5
			 - Internal Rating: 950
			 - Formula: Rating + Streak * 10
			 */
			final double probability = Elo.getProbability(playerA.getInternalRating(), playerB.getInternalRating());
			
			// If the probability of player A winning against player B is between 0.45 and 0.55, return the two players as a match.
			if (probability > 0.45 && probability < 0.55) {
				
				queue.remove(playerB);
				
				return new Player[]{playerA, playerB};
			}
		}
		
		// If no fair match was found, put the player back in the queue.
		queue.add(playerA);
		
		return null;
	}
}
