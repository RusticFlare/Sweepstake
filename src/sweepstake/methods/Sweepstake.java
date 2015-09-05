package sweepstake.methods;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sweepstake.api.FootballDataHelper;
import sweepstake.json.JsonReader;
import sweepstake.stdin.StdInHelper;

public class Sweepstake {

	public static void main(String[] args) throws IOException, JSONException {
		JSONArray seasonsList = JsonReader.readJsonArrayFromUrl("http://api.football-data.org/alpha/soccerseasons");
		for (int i = 0; i < seasonsList.length(); i++) {
			System.out.println("(" + (i+1) + ") " + seasonsList.getJSONObject(i).getString("caption"));
		}
		JSONObject season = StdInHelper.getJSONObjectFromJSONArrayFromInput(seasonsList);
		JSONObject links = season.getJSONObject("_links");
		JSONObject fixturesLink = links.getJSONObject("fixtures");
		String fixturesUrl = fixturesLink.getString("href") + FootballDataHelper.TIME_FRAME_URL_FILTER;
		JSONObject fixtures = JsonReader.readJsonObjectFromUrl(fixturesUrl);
		JSONArray fixturesList = fixtures.getJSONArray("fixtures");
		for (int i = 0; i < fixturesList.length(); i++) {
			JSONObject fixture = fixturesList.getJSONObject(i);
			System.out.println("(" + (i+1) + ") " + fixture.getString("homeTeamName") + " v " + fixture.getString("awayTeamName"));
		}
		JSONObject fixture = StdInHelper.getJSONObjectFromJSONArrayFromInput(fixturesList);
		links = fixture.getJSONObject("_links");
		String homePlayersUrl = links.getJSONObject("homeTeam").getString("href") + FootballDataHelper.PLAYERS_URL;
		String awayPlayersUrl = links.getJSONObject("awayTeam").getString("href") + FootballDataHelper.PLAYERS_URL;
		JSONArray homePlayers = JsonReader.readJsonObjectFromUrl(homePlayersUrl).getJSONArray("players");
		JSONArray awayPlayers = JsonReader.readJsonObjectFromUrl(awayPlayersUrl).getJSONArray("players");
		String[] players = new String[homePlayers.length() + awayPlayers.length()];
		for (int i = 0; i < homePlayers.length(); i++) {
			players[i] = homePlayers.getJSONObject(i).getString("name");
		}
		for (int i = 0; i < awayPlayers.length(); i++) {
			int j = i + homePlayers.length();
			players[j] = awayPlayers.getJSONObject(i).getString("name");
		}
		String[] punters = StdInHelper.readInputsToStringArray();
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
		if (players == null || players.length == 0) {
			throw new IllegalArgumentException();
		}
		if (punters == null || punters.length == 0) {
			throw new IllegalArgumentException();
		}
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
