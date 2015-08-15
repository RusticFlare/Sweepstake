package sweepstake.methods;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Sweepstake {

	public static void main(String[] args) {
		String[] players = {"Bony","Raheem","nav","silv","toooreee","ferndidinidho","kol","komps","mandela","backary","harty","myhill","chesterfield","dawson","lescott","brunt","gardener","fletcher","mozz","mcleaner","lambert","berahinoos" };
		String[] punters = { "James", "Ed", "Frank" };
		Map<String, String[]> results = sweepstake(players,punters);
		for(Map.Entry<String, String[]> result : results.entrySet()) {
			System.out.println(result.getKey() + ": ");
			String[] resultPlayers = result.getValue();
			for (int i = 0; i < resultPlayers.length; i++) {
				System.out.println("\t" + resultPlayers[i]);
			}
		}
	}
	
	public static Map<String,String[]> sweepstake(String[] players, String[] punters) {
		String[][] playerSplit = new String[punters.length][];
		int sizeOfSplit = players.length / punters.length;
		int remainder = players.length % punters.length;
		
		for (int i = 0; i < playerSplit.length; i++) {
			int extra = 0;
			if (remainder-- > 0)
				extra = 1;
			playerSplit[i] = new String[sizeOfSplit + extra];
		}
		
		Random randomGenerator = new Random();
		for (int i = 0; i < players.length; i++) {
			int random = randomGenerator.nextInt(players.length);
			while (players[random] == null) {
				random = randomGenerator.nextInt(players.length);
			}
			int punterIndex = i % punters.length;
			int playerIndex = i / punters.length;
			playerSplit[punterIndex][playerIndex] = players[random];
			players[random] = null;
		}
		
		Map<String, String[]> result = new HashMap<>();
		for (int i = 0; i < punters.length; i++) {
			int random = randomGenerator.nextInt(punters.length);
			while (punters[random] == null) {
				random = randomGenerator.nextInt(punters.length);
			}
			result.put(punters[random], playerSplit[random]);
			punters[random] = null;
		}
		return result;
	}

}
