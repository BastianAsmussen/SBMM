package tech.asmussen;

import tech.asmussen.ranked.Elo;
import tech.asmussen.ranked.Matchmaker;
import tech.asmussen.ranked.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {
	
	public static void main(String[] args) {
		
		List<Player> players = new ArrayList<>();
		
		Player playerA = new Player("Player A", 1000, 0, 0, 0);
		Player playerB = new Player("Player B", 1000, 0, 0, 0);
		Player playerC = new Player("Player C", 1400, 0, 0, 0);
		
		players.add(playerA);
		players.add(playerB);
		players.add(playerC);
		
		Queue<Player> queue = new LinkedList<>(players);
		
		int matches = 0;
		
		// Matchmaking
		while (queue.size() > 1) {
			
			Player[] match = Matchmaker.findMatch(queue, true);
			
			if (match == null) {
				
				continue;
			}
			
			matches++;
			
			Player fighterA = match[0];
			Player fighterB = match[1];
			
			// Simulate a match between the two players.
			final boolean isVictoryA = Math.random() > 0.5;
			
			final int ratingA = fighterA.getRating();
			final int ratingB = fighterB.getRating();
			
			if (isVictoryA) {
				
				fighterA.addWin();
				fighterB.addLoss();
				
				Elo.adjustRating(fighterA, fighterB, true);
				
				System.out.printf("%s won against %s! (%d vs %d)%n", fighterA.getName(), fighterB.getName(), ratingA, ratingB);
				
			} else {
				
				fighterA.addLoss();
				fighterB.addWin();
				
				System.out.printf("%s (%d) defeated %s (%d)%n", fighterB.getName(), ratingB, fighterA.getName(), ratingA);
			}
			
			fighterA.setRating(Elo.adjustRating(fighterA, fighterB, isVictoryA));
			fighterB.setRating(Elo.adjustRating(fighterB, fighterA, !isVictoryA));
			
			queue.add(fighterA);
			queue.add(fighterB);
		}
	}
}
