package tech.asmussen.util;

public final class Time {
	
	/**
	 * Format the given time in milliseconds to a human-readable format.
	 *
	 * @param millis The time in milliseconds.
	 * @return The formatted time.
	 */
	public static String formatMillis(long millis) {
		
		long seconds = millis / 1_000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long days = hours / 24;
		
		if (days > 0) {
			
			return String.format("%dd, %dh, %dm, %ds, %dms", days, hours % 24, minutes % 60, seconds % 60, millis % 1000);
			
		} else if (hours > 0) {
			
			return String.format("%dh, %dm, %ds, %dms", hours, minutes % 60, seconds % 60, millis % 1000);
			
		} else if (minutes > 0) {
			
			return String.format("%dm, %ds, %dms", minutes, seconds % 60, millis % 1000);
			
		} else if (seconds > 0) {
			
			return String.format("%ds, %dms", seconds, millis % 1000);
			
		} else {
			
			return String.format("%dms", millis);
		}
	}
}
