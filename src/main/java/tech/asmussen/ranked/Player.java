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
		
		return rating + streak * 10;
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
