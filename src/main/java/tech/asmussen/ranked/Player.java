package tech.asmussen.ranked;

import lombok.Data;

@Data
public class Player {
	
	private final String name;
	
	private int rating;
	
	private int wins;
	private int losses;
	private int draws;
	
	private int streak;
	
	public Player(String name, int rating, int wins, int losses, int draws) {
		
		this.name = name;
		
		this.rating = rating;
		
		this.wins = wins;
		this.losses = losses;
		this.draws = draws;
	}
	
	public int getInternalRating() {
		
		/*
		 The streak can be positive or negative, and is multiplied by 10 to increase the effect of the streak.
		 A win sets the streak to 1, a loss sets the streak to -1, and a draw sets the streak to 0.
		 
		 Formula:
		 - r + s * c
		 - r = rating
		 - s = streak
		 - c = cap
		 
		 Example 1:
		 - Rating: 1,000
		 - Streak: 5
		 - Cap: 40
		 - Internal Rating: 1,200
		 */
		return rating + streak * Elo.personalizeGainCap(this);
	}
	
	public int getGamesPlayed() {
		
		return wins + losses + draws;
	}
	
	public void addWin() {
		
		wins++;
		
		updateStreak(true);
	}
	
	public void addLoss() {
		
		losses++;
		
		updateStreak(false);
	}
	
	public void addDraw() {
		
		draws++;
	}
	
	private void updateStreak(boolean isWin) {
		
		// If the player won, increase their streak.
		if (isWin) {
			
			if (streak < 0) {
				
				streak = 1;
				
			} else {
				
				streak++;
			}
			
			// Else, decrease their streak.
		} else {
			
			if (streak > 0) {
				
				streak = -1;
				
			} else {
				
				streak--;
			}
		}
	}
}
