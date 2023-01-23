package tech.asmussen.ranked;

public class Elo {
	
	public static final int MAX_RATING = 5_000;
	public static final int MIN_RATING = 0;
	
	/**
	 * 400 corresponds to the strong player having a 90% chance of winning against the weaker player.
	 */
	public static final int STRONG_PLAYER_DENOMINATOR = 400;
	
	/**
	 * The maximum amount of rating points a player can gain or lose in a single match.
	 */
	public static final int GAIN_CAP = 40;
	
	/**
	 * Calculates the probability of player A winning against player B.
	 * <p>
	 * <b>Formula:</b> 1 / (1 + 10 ^ ((ratingB - ratingA) / {@link #STRONG_PLAYER_DENOMINATOR}))
	 * The probability of player A winning against player B.
	 *
	 * @param ratingA The rating of player A.
	 * @param ratingB The rating of player B.
	 * @return The probability of player A winning, between 0 and 1.
	 */
	public static double getProbability(double ratingA, double ratingB) {
		
		return 1 / (1 + Math.pow(10, (ratingB - ratingA) / STRONG_PLAYER_DENOMINATOR));
	}
	
	/**
	 * Calculates the new rating of player A after a match against player B.
	 * <p>
	 * <b>Formula:</b> ratingA + {@link #GAIN_CAP} * (scoreA - probabilityA)
	 *
	 * @param playerA The first player.
	 * @param playerB The second player.
	 * @param win     Whether the first player won the match or not.
	 * @return The new rating of the first player.
	 */
	public static int adjustRating(Player playerA, Player playerB, boolean win) {
		
		final int ratingA = playerA.getInternalRating();
		final int ratingB = playerB.getInternalRating();
		
		double expectedScore = getProbability(ratingA, ratingB);
		int actualScore = win ? 1 : 0;
		
		int personalizedGainCap = personalizeGainCap(playerA);
		
		// The new rating is the old rating plus the adjustment sensitivity times the difference between the expected and actual score.
		int newRating = (int) (ratingA + personalizedGainCap * (actualScore - expectedScore));
		
		// If the new rating is higher than the highest possible rating, set it to the highest possible rating.
		if (newRating >= MAX_RATING) {
			
			return MAX_RATING;
			
			// Else find the highest number between the lowest possible rating, and the new rating.
		} else {
			
			return Math.max(newRating, MIN_RATING);
		}
	}
	
	public static int personalizeGainCap(Player player) {
		
		int kFactor;
		
		// If the player has played less than 30 games, and has less than 2,300 Elo, return the gain cap.
		if (player.getGamesPlayed() < 30 && player.getRating() < 2300) {
			
			kFactor = GAIN_CAP;
			
		} else if (player.getRating() < 2400) {
			
			kFactor = 20;
			
		} else {
			
			kFactor = 10;
		}
		
		return kFactor;
	}
}
