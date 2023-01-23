package tech.asmussen.ranked;

public enum Rank {
	
	BRONZE(1_499),
	SILVER(1_999),
	GOLD(2_499),
	PLATINUM(2_999),
	DIAMOND(3_499),
	MASTER(3_999),
	GRANDMASTER(Elo.MAX_RATING);
	
	private final int threshold;
	
	Rank(int threshold) {
		
		this.threshold = threshold;
	}
	
	public static Rank box(int rating) {
		
		for (Rank rank : values()) {
			
			if (rating < rank.getThreshold())
				return rank;
		}
		
		return GRANDMASTER;
	}
	
	public int getThreshold() {
		
		return threshold;
	}
}
