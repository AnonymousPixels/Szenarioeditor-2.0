package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import io.Settings;

public class IOTestMain {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		// HashMap<String, Object> hash = new HashMap<String, Object>();
		//
		// hash = io.Settings.getSettings("C://Program Files
		// (x86)//Steam//steamapps//common//For The Glory", "German",
		// "1419 - The Grand Campaign.eeg");
		//
		// // System.out.println(((HashMap<String, Object>) ((HashMap<String,
		// // Object>) hash.get("localisationdata")).get("USA")).get("name"));
		//
		// System.out.println((HashMap<String, Object>)
		// hash.get("culturedata"));
		// System.out.println("----------------------------");
		// for (String key : ((HashMap<String, Object>)
		// hash.get("culturedata")).keySet()) {
		// System.out.println(key + ((HashMap<String, Object>) ((HashMap<String,
		// Object>) hash.get("culturedata"))
		// .get("culturedatatag")));
		//
		// }
		//
		// // new SaveSettings(hash);

		read();

	}

	public static void read() throws IOException {
		FileReader filereader = new FileReader(new File(
				"C:/Program Files (x86)/Steam/steamapps/common/For The Glory/Scenarios/Save Games/Russia_1619_January_14.eeg"));
		BufferedReader reader = new BufferedReader(filereader);
		int brackets = 0;
		String varification = "";
		String includestring = "";

		HashMap<String, Object> scenarioeeghashmap = new HashMap<String, Object>();
		HashMap<String, Object> scenariohashmap = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder();
		try {

			String line = reader.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = reader.readLine();
			}
		} finally {
			reader.close();
		}

		String line = sb.toString();

		String[] lines = line.split("[\\r\\n]+");

		for (String input : lines) {

			
			//
			// if (brackets >= 1) {
			// String[] checkFor = { "name", "startyear", "endyear", "startdate"
			// };
			// for (String s : checkFor) {
			//
			// if (input.contains(s)) {
			//
			// String property = input.replaceAll(s + "=", "");
			// scenariohashmap.put(s, property);
			//
			// }
			//
			// }
			// }
			// input = input.replaceAll(" ", "");
			// if (input.contains("include")) {
			// includestring = input.replaceAll("include=", "") + "," +
			// includestring;
			// }
			//
			// if (brackets == 0 && varification != null &&
			// !varification.equals("")) {
			//
			// scenarioeeghashmap.put(varification, scenariohashmap.clone());
			//
			// }

			if (brackets >= 1 && input.contains("header")) {
				String[] checkFor = { "name", "tutorial", "absolutestartyear", "startyear", "endyear", "gametype",
						"saved", "free", "optionmode", "set_ai_aggressive", "set_difficulty", "set_gamespeed",
						"set_fow", "set_conq_capital", "set_missions", "set_basevp", "set_ai_event_choices",
						"set_fow_owner", "optionfile" };
				for (String s : checkFor) {

					if (input.contains(s)) {

						String property = input.replaceAll(s + "=", "");
						scenariohashmap.put(s, property);
						System.out.println(s + " " + property);

					}

				}
			}

		}
//		io.Settings.putInHashMap("scenariodata", scenarioeeghashmap.clone());

	}

}
