package tech.asmussen;

import tech.asmussen.ranked.*;
import tech.asmussen.util.Time;

import java.util.*;

public class SBMM {
	
	public static void main(String[] args) {
		
		final long startTime = System.currentTimeMillis();
		final int numberOfPlayers = 10_000_000;
		
		System.out.println("Generating players...");
		
		Player[] players = new Player[numberOfPlayers];
		
		for (int i = 0; i < numberOfPlayers; i++) {
			
			try {
				
				Player player = new Player("Player " + i, 1_000, 0, 0, 0);
				
				players[i] = player;
				
			} catch (Exception e) {
				
				System.out.println("Failed at index " + i);
				
				e.printStackTrace();
			}
		}
		
		System.out.println("Creating shuffled queue...");
		
		Queue<Player> queue = new LinkedList<>(Arrays.asList(players));
		Collections.shuffle((List<?>) queue);
		
		System.out.println("Matching...");
		
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			
			Player[] match = Matchmaker.findMatch(queue, true);
			
			if (match == null) {
				
				System.out.println("No more fair matches could be found, " + i + " matches were made!");
				
				break;
			}
			
			Player playerA = match[0];
			Player playerB = match[1];
			
			// System.out.println(playerA.getName() + " vs " + playerB.getName());
			
			int ratingA = playerA.getRating();
			int ratingB = playerB.getRating();
			
			double probability = Elo.getProbability(ratingA, ratingB);
			
			// System.out.printf("A's Probability of Victory: %.2f%%\n", probability * 100);
			
			final boolean isPlayerAVictorious = Math.random() > probability;
			
			if (isPlayerAVictorious) {
				
				playerA.addWin();
				playerB.addLoss();
				
				playerA.setRating(Elo.adjustRating(playerA, playerB, true));
				playerB.setRating(Elo.adjustRating(playerB, playerA, false));
				
				// System.out.println(playerA.getName() + " won!");
				
			} else {
				
				playerA.addLoss();
				playerB.addWin();
				
				playerA.setRating(Elo.adjustRating(playerA, playerB, false));
				playerB.setRating(Elo.adjustRating(playerB, playerA, true));
				
				// System.out.println(playerB.getName() + " won!");
			}
			
			queue.add(playerA);
			queue.add(playerB);
			
			// System.out.printf("Rating A: %d Elo (%s) -> %d Elo (%s)\n", ratingA, Rank.box(ratingA), playerA.getRating(), Rank.box(playerA.getRating()));
			// System.out.printf("Rating B: %d Elo (%s) -> %d Elo (%s)\n", ratingB, Rank.box(ratingB), playerB.getRating(), Rank.box(playerB.getRating()));
		}
		
		System.out.printf("Took %s.\n", Time.formatMillis(System.currentTimeMillis() - startTime));
		
		List<Player> leaderboard = Leaderboard.generateLeaderboard(Arrays.asList(players), 500);
		
		double averageSkillRating = Leaderboard.getAverageSkillRating(leaderboard);
		Rank averageRank = Rank.box((int) Math.round(averageSkillRating));
		
		System.out.printf("Average Skill Rating of Top 500: %.2f Elo, %s\n", averageSkillRating, averageRank);
		
		for (int i = 0; i < leaderboard.size(); i++) {
			
			Player player = leaderboard.get(i);
			
			int rating = player.getRating();
			
			System.out.printf("%d. %s (%d Games, Streak: %d, %d Elo, %s)\n", i + 1, player.getName(), player.getGamesPlayed(), player.getStreak(), rating, Rank.box(rating));
		}
	}
}
