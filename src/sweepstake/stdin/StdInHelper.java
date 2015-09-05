package sweepstake.stdin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StdInHelper {
	
	public static JSONObject getJSONObjectFromJSONArrayFromInput(JSONArray a) throws JSONException {
		JSONObject o = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input;
			while ((input = br.readLine()) != null) {
				try {
					int index = Integer.parseInt(input) - 1;
					if (index < 0 || a.length() <= index) {
						throw new NumberFormatException();
					}
					o = a.getJSONObject(index);
					break;
				} catch (NumberFormatException e) {
					System.out.println("Please enter a number between 1-" 
								+ a.length() + ". You entered: \"" + input + "\".");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	public static String[] readInputsToStringArray() {
		List<String> resultBuilder = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input;
			while ((input = br.readLine()) != null) {
				if (input.trim().equalsIgnoreCase("")) {
					break;
				} else {
					resultBuilder.add(input);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultBuilder.toArray(new String[0]);
	}

}
