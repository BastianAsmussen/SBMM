package tech.asmussen.ranked;

public class Elo {
	
	public static final int MAX_RATING = 5_000;
	public static final int MIN_RATING = 0;
	
	public static final int ASSUMED_RATING_DIFFERENCE = 400;
	public static final int GAIN_CAP = 40;
	
	/**
	 * Calculates the probability of player A winning against player B.
	 * <p>
	 * <b>Formula:</b> 1 / (1 + 10 ^ ((ratingB - ratingA) / {@link #ASSUMED_RATING_DIFFERENCE}))
	 * The probability of player A winning against player B.
	 *
	 * @param ratingA The rating of player A.
	 * @param ratingB The rating of player B.
	 * @return The probability of player A winning, between 0 and 1.
	 */
	public static double getProbability(double ratingA, double ratingB) {
		
		return 1 / (1 + Math.pow(10, (ratingB - ratingA) / ASSUMED_RATING_DIFFERENCE));
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
		
		// The new rating is the old rating plus the adjustment sensitivity times the difference between the expected and actual score.
		int newRating = (int) (ratingA + GAIN_CAP * (actualScore - expectedScore));
		
		// If the new rating is higher than the highest possible rating, set it to the highest possible rating.
		if (newRating >= MAX_RATING) {
			
			return MAX_RATING;
			
		// Else find the highest number between the lowest possible rating, and the new rating.
		} else {
			
			return Math.max(newRating, MIN_RATING);
		}
	}
}
