package tech.asmussen;

import tech.asmussen.ranked.Elo;
import tech.asmussen.ranked.Matchmaker;
import tech.asmussen.ranked.Player;

import java.util.*;

public class SBMM {
	
	public static void main(String[] args) {
		
		ArrayList<Integer> differences = new ArrayList<>();
		
		Random random = new Random();
		
		Queue<Player> queue = new LinkedList<>();
		
		for (int i = 0; i < 1_000; i++) {
			
			Player player = new Player("Player " + i, random.nextInt(Elo.MIN_RATING, Elo.MAX_RATING), 0, 0, 0);
			
			queue.add(player);
		}
		
		Collections.shuffle((List<?>) queue);
		
		for (int i = 0; i < queue.size(); i++) {
			
			Player[] match = Matchmaker.findMatch(queue, true);
			
			if (match == null) {
				
				System.out.println("No more fair matches could be found.");
				
				break;
			}
			
			Player playerA = match[0];
			Player playerB = match[1];
			
			System.out.println(playerA.getName() + " vs " + playerB.getName());
			
			int ratingA = playerA.getRating();
			int ratingB = playerB.getRating();
			
			differences.add(Math.abs(ratingA - ratingB));
			
			double probability = Elo.getProbability(ratingA, ratingB);
			
			System.out.printf("Probability: %.2f%%\n", probability * 100);
			
			final boolean isPlayerAVictorious = true;
			
			if (isPlayerAVictorious) {
				
				playerA.addWin();
				playerB.addLoss();
				
				playerA.setRating(Elo.adjustRating(playerA, playerB, isPlayerAVictorious));
				playerB.setRating(Elo.adjustRating(playerB, playerA, !isPlayerAVictorious));
				
				System.out.println(playerA.getName() + " won!");
				
			} else {
				
				playerA.addLoss();
				playerB.addWin();
				
				playerA.setRating(Elo.adjustRating(playerA, playerB, isPlayerAVictorious));
				playerB.setRating(Elo.adjustRating(playerB, playerA, !isPlayerAVictorious));
				
				System.out.println(playerB.getName() + " won!");
			}
			
			System.out.printf("Rating A: %d Elo -> %d Elo\n", ratingA, playerA.getRating());
			System.out.printf("Rating B: %d Elo -> %d Elo\n", ratingB, playerB.getRating());
		}
		
		System.out.printf("Average Rating Difference: %.2f Elo\n", differences.stream().mapToInt(Integer::intValue).average().getAsDouble());
	}
}
