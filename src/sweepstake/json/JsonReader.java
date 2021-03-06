package sweepstake.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sweepstake.api.FootballDataHelper;

public class JsonReader {
	
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
  
	private static InputStream getInputStream(String url) throws MalformedURLException, IOException {
		URLConnection urlConnection = new URL(url).openConnection();
		urlConnection.setRequestProperty(FootballDataHelper.AUTH_TOKEN_HEADER, FootballDataHelper.AUTH_TOKEN_VALUE);
		return urlConnection.getInputStream();
	}

	public static JSONObject readJsonObjectFromUrl(String url) throws IOException, JSONException {
		InputStream is = getInputStream(url);
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}
  
	public static JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException {
		InputStream is = getInputStream(url);
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONArray json = new JSONArray(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

}
