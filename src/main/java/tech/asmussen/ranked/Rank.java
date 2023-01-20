package tech.asmussen.ranked;

public enum Rank {
	
	BRONZE, SILVER, GOLD, PLATINUM, DIAMOND, MASTER, GRANDMASTER;
	
	public static Rank box(int rating) {
		
		if (rating < 1_500) return BRONZE;
		else if (rating < 2_000) return SILVER;
		else if (rating < 2_500) return GOLD;
		else if (rating < 3_000) return PLATINUM;
		else if (rating < 3_500) return DIAMOND;
		else if (rating < 4_000) return MASTER;
		else return GRANDMASTER;
	}
}
