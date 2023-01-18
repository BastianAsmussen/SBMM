package tech.asmussen;

import tech.asmussen.ranked.Elo;
import tech.asmussen.ranked.Leaderboard;
import tech.asmussen.ranked.Matchmaker;
import tech.asmussen.ranked.Player;
import tech.asmussen.ranked.Rank;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Main {
	
	public static void main(String[] args) {
		
		Random random = new Random();
		
		Queue<Player> queue = new LinkedList<>();
		
		for (int i = 0; i < 100; i++) {
			
			Player player = new Player("Player " + i, random.nextInt(Elo.MIN_RATING, Elo.MAX_RATING), 0, 0, 0);
			
			queue.add(player);
		}
		
		Collections.shuffle((List<?>) queue);
		
		Player[] match = Matchmaker.findMatch(queue, true);
		
		if (match != null) {
			
			System.out.println("Match found!");
			
			for (Player player : match) {
				
				System.out.println(player.getName());
				System.out.println("- Rating: " + player.getRating());
				System.out.println("- Rank: " + Rank.box(player.getRating()));
				System.out.println("- Internal Rating: " + player.getInternalRating());
				System.out.println("- Streak: " + player.getStreak());
				System.out.println("- Wins: " + player.getWins());
				System.out.println("- Losses: " + player.getLosses());
				System.out.println("- Draws: " + player.getDraws());
				System.out.println();
			}
		}
		
		System.out.println("Average Skill Rating: " + Leaderboard.getAverageSkillRating(queue.stream().toList()));
	}
}
