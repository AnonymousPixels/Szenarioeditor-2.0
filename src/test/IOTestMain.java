package test;

import java.io.IOException;
import java.util.HashMap;

public class IOTestMain {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		HashMap<String, Object> hash = new HashMap<String, Object>();

		hash = io.Settings.getSettings("C://Program Files (x86)//Steam//steamapps//common//For The Glory",
				"German", "1419 - The Grand Campaign.eeg");


		// System.out.println(((HashMap<String, Object>) ((HashMap<String,
		// Object>) hash.get("localisationdata")).get("USA")).get("name"));

		System.out.println((HashMap<String, Object>) hash.get("culturedata"));
		System.out.println("----------------------------");
		for (String key : ((HashMap<String, Object>) hash.get("culturedata")).keySet()) {
			System.out.println(key + ((HashMap<String, Object>) ((HashMap<String, Object>) hash.get("culturedata"))
					.get("culturedatatag")));

		}

		// new SaveSettings(hash);

	}

}
