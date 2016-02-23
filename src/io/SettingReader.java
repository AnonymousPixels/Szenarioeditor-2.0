package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class SettingReader {
	private static BufferedReader reader;
	private static FileReader file;
	private static String input, line;
	private static int brackets;

	private static HashMap<String, Object> provincesettinghashmap, provincehashmap, countryhashmap,
			countrysettinghashmap, countrynamehashmap, culturesettingshashmap, culturehashmap, techgroupsettingshashmap,

			techgrouphashmap, scenarioeeghashmap, scenariohashmap;

	private static String id;
	private static String varification;

	private static String countryname, countrytag;

	public SettingReader(String gamepath, String language, HashMap<String, Object> hashmap, String scenariofilepath)
			throws IOException {

		System.out.println("Scenariofilepath: " + scenariofilepath);
		System.out.println("Gamefilepath: " + gamepath);
		getCountrySettings(gamepath + "//Db//countries.txt");
		getProvinces(gamepath + "//Db//Map//provinces.txt");
		getLocalisation(gamepath + "//Localisation//" + language + "//countries.csv", hashmap);
		getCultures(gamepath + "//Db//cultures.txt");
		getTechgroups(gamepath + "//Db//Technologies//techgroups.txt");
		getReligion(gamepath + "//Db//Religions//religions.txt");

//		getScenario(scenariofilepath);
		
		System.out.println("Finished reading class - Johannes");

	}

	private void getReligion(String culturefilepath) throws IOException {

		culturesettingshashmap = new HashMap<String, Object>();
		file = new FileReader(culturefilepath);
		reader = new BufferedReader(file);
		input = "";
		id = "";
		line = "";
		varification = "";

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

		line = sb.toString();

		String[] lines = line.split("[\\r\\n]+");
		culturehashmap = new HashMap<String, Object>();
		for (String input : lines) {
			input = input.replaceAll(" ", "");
			input = input.replaceAll("	", "");
			input = input.replaceAll("\"", "");
			if (input.contains("{")) {
				brackets++;
			}
			if (input.contains("}")) {
				brackets--;
			}
			if (!input.equals("") && input != null && input.trim().charAt(0) == '#') {

			} else if ((input != null || input != "") && brackets == 1 && input.contains("={")) {
				varification = input.substring(0, input.indexOf("="));
			}
			String[] checkFor = { "predominance", "force_conversion", "defender", "annexable", "annex_same_penalty",
					"annex_other_penalty", "tech_speed", "reveal_map", "whiteman", "defectprovinceto_penalty",
					"province_nationalism", "province_religion", "coastalprovince_bonus", "stability_bonus",
					"stability_cost", "colonists", "diplomats", "missionaries", "missionary_placement_chance",
					"missionary_placement_penalty", "missionary_sprite", "land_morale", "naval_morale",
					"trade_efficiency", "production_efficiency", "global_tax_modifier", "slaves_effect" };
			for (String s : checkFor) {

				if (input.contains(s) && s != null) {
					String property = input.replaceAll(s + "=", "");
					culturehashmap.put(s, property);

				}
			}
			if (brackets == 0 && varification != null && !varification.equals("")) {

				culturesettingshashmap.put(varification, culturehashmap.clone());
				culturehashmap.clear();

			}
		}

		Settings.putInHashMap("religiondata", culturesettingshashmap.clone());

	}

	private void getScenario(String scenariofilepath) throws IOException {
		// TODO hashmap f�r jedes land, informationen in hashmap
		scenarioeeghashmap = new HashMap<String, Object>();
		scenariohashmap = new HashMap<String, Object>();
		file = new FileReader(scenariofilepath);
		reader = new BufferedReader(file);
		line = "";
		String includestring = "";

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

		line = sb.toString();

		String[] lines = line.split("[\\r\\n]+");
		for (String input : lines) {
			input = input.replaceAll("\t", "");
			input = input.replaceAll("\"", "");
			if ((input != null && input != "") && brackets == 0 && input.contains("= {")) {
				varification = input.substring(0, input.indexOf("= {"));

			}
			if (input.contains("{")) {
				brackets++;
			}
			if (input.contains("}")) {
				brackets--;
			}
			if (!input.equals("") && input != null && input.trim().charAt(0) == '#') {

			} else if (brackets >= 1) {
				String[] checkFor = { "name", "startyear", "endyear", "startdate" };
				for (String s : checkFor) {

					if (input.contains(s)) {

						String property = input.replaceAll(s + "=", "");
						scenariohashmap.put(s, property);

					}

				}
			}
			input = input.replaceAll(" ", "");
			if (input.contains("include")) {
				includestring = input.replaceAll("include=", "") + "," + includestring;
			}

			if (brackets == 0 && varification != null && !varification.equals("")) {

				scenarioeeghashmap.put(varification, scenariohashmap.clone());

			}
		}

		Settings.putInHashMap("scenariodata", scenarioeeghashmap.clone());
		String[] includes = includestring.split(",");
		getAllScenarioSettings(includes);

	}

	private void getAllScenarioSettings(String[] includes) {
		for (int i = 0; i < includes.length; i++) {
			System.out.println("Include: " + includes[i]);
		}
	}

	private void getScenarioSettings(String[] includes) {

	}

	public void getLocalisation(String localisationpath, HashMap<String, Object> hashmap) throws IOException {
		countrynamehashmap = new HashMap<String, Object>();
		countryhashmap = new HashMap<String, Object>();
		file = new FileReader(localisationpath);
		reader = new BufferedReader(file);
		line = "";

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

		line = sb.toString();

		String[] lines = line.split("[\\r\\n]+");

		for (int i = 0; i < lines.length; i++) {
			if (!lines[i].contains("_DESC") && lines[i].charAt(0) != '#') {

				countrytag = lines[i].substring(0, 3);
				countryname = lines[i].substring(4, lines[i].lastIndexOf(";")).replaceAll(";", "");
				countryhashmap.put("name", countryname);
				countrynamehashmap.put(countrytag, countryhashmap.clone());
				countryhashmap.clear();

			}
		}

		Settings.hashmap.put("localisationdata", countrynamehashmap.clone());

	}

	private void getCountrySettings(String countryfilepath) throws IOException {
		countrysettinghashmap = new HashMap<String, Object>();
		file = new FileReader(countryfilepath);
		reader = new BufferedReader(file);
		input = "";
		id = "";
		line = "";
		varification = "";

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

		line = sb.toString();

		String[] lines = line.split("[\\r\\n]+");
		countryhashmap = new HashMap<String, Object>();
		for (String input : lines) {
			input = input.replaceAll(" ", "");
			input = input.replaceAll("	", "");
			input = input.replaceAll("\"", "");
			if (input.contains("{")) {
				brackets++;
			}
			if (input.contains("}")) {
				brackets--;
			}
			if (!input.equals("") && input != null && input.trim().charAt(0) == '#') {

			}
			if (input.contains("history")) {
			} else if ((input != null || input != "") && brackets == 1 && input.contains("={")) {
				varification = input.substring(0, 3);
			}
			String[] checkFor = { "picture", "color", "techgroup", "leader_language", "combat",
					"colonization_difficulty", "cot_modifier", "new_colony", "army", "navy", "aristocracy",
					"centralization", "innovative", "mercantilism", "offensive", "land", "serfdom", "quality",
					"elector", "history", "varification" };
			for (String s : checkFor) {

				if (input.contains(s) && s != null) {
					String property = input.replaceAll(s + "=", "");
					countryhashmap.put(s, property);

				}
			}
			if (brackets == 0) {

				countrysettinghashmap.put(varification, countryhashmap.clone());
				countryhashmap.clear();

			}
		}

		// Iterator<Entry<String, Object>> it = countrysettinghashmap.entrySet()
		// .iterator();
		// while (it.hasNext()) {
		// Map.Entry pair = (Map.Entry) it.next();
		// System.out.println(pair.getKey() + " = " + pair.getValue());
		// }

		Settings.putInHashMap("countrydata", countrysettinghashmap.clone());

	}

	public void getCultures(String culturefilepath) throws IOException {

		culturesettingshashmap = new HashMap<String, Object>();
		file = new FileReader(culturefilepath);
		reader = new BufferedReader(file);
		input = "";
		id = "";
		line = "";
		varification = "";

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

		line = sb.toString();

		String[] lines = line.split("[\\r\\n]+");
		culturehashmap = new HashMap<String, Object>();
		for (String input : lines) {
			input = input.replaceAll(" ", "");
			input = input.replaceAll("	", "");
			input = input.replaceAll("\"", "");
			if (input.contains("{")) {
				brackets++;
			}
			if (input.contains("}")) {
				brackets--;
			}
			if (!input.equals("") && input != null && input.trim().charAt(0) == '#') {

			}
			if ((input != null || input != "") && brackets == 1 && input.contains("={")) {
				varification = input.substring(0, input.indexOf("="));
			}
			String[] checkFor = { "city", "buildings", "color" };
			for (String s : checkFor) {

				if (input.contains(s) && s != null) {
					String property = input.replaceAll(s + "=", "");
					culturehashmap.put(s, property);

				}
			}
			if (brackets == 0 && varification != null && !varification.equals("")) {

				culturesettingshashmap.put(varification, culturehashmap.clone());
				culturehashmap.clear();

			}
		}

		Settings.putInHashMap("culturedata", culturesettingshashmap.clone());

	}

	private void getTechgroups(String techgroupfilepath) throws IOException {

		techgroupsettingshashmap = new HashMap<String, Object>();
		file = new FileReader(techgroupfilepath);
		reader = new BufferedReader(file);
		input = "";
		id = "";
		line = "";
		varification = "";

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

		line = sb.toString();

		// input = reader.readLine();
		// while (input != null) {
		//
		// if (input.indexOf("#") != 1 || input.indexOf("#") != 0) {
		// // System.out.println(input);
		// line = line + input + "\n";
		// input = reader.readLine();
		// }
		// }

		String[] lines = line.split("[\\r\\n]+");
		techgrouphashmap = new HashMap<String, Object>();
		for (String input : lines) {
			input = input.replaceAll(" ", "");
			input = input.replaceAll("	", "");
			input = input.replaceAll("\"", "");
			if (input.contains("{")) {
				brackets++;
			}
			if (input.contains("}")) {
				brackets--;
			}
			if (!input.equals("") && input != null && input.trim().charAt(0) == '#') {

			}
			if ((input != null || input != "") && brackets == 1 && input.contains("={")) {
				varification = input.substring(0, input.indexOf("="));
			}
			String[] checkFor = { "tech_speed" };
			for (String s : checkFor) {

				if (input.contains(s) && s != null) {
					String property = input.replaceAll(s + "=", "");
					techgrouphashmap.put(s, property);

				}
			}
			if (brackets == 0) {

				techgroupsettingshashmap.put(varification, techgrouphashmap.clone());
				techgrouphashmap.clear();

			}
		}

		Settings.putInHashMap("techgroupdata", techgroupsettingshashmap.clone());
	}

	private void getProvinces(String countryfilepath) throws IOException {
		provincesettinghashmap = new HashMap<String, Object>();
		file = new FileReader(countryfilepath);
		reader = new BufferedReader(file);
		input = "";
		id = "";
		line = "";

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

		line = sb.toString();

		String[] lines = line.split("[\\r\\n]+");
		provincehashmap = new HashMap<String, Object>();
		for (String input : lines) {

			input = input.replaceAll(" ", "");
			input = input.replaceAll("	", "");
			input = input.replaceAll("\"", "");
			if (input.contains("{")) {
				brackets++;
			}
			if (input.contains("}")) {
				brackets--;
			}
			if (!input.equals("") && input != null && input.trim().charAt(0) == '#') {
			}
			String[] checkFor = { "terrain", "sea_adjacency", "tolerance", "tp_negotiation", "efficiency", "ferocity",
					"combat", "colonization_difficulty", "cot_modifier", "city_name", "goods", "income", "manpower",
					"culture", "religion", "climate", "size_modifier", "terrain", "type", "area", "region", "continent",
					"name", "id", "terrain1", "city", "terrain2", "terrain3", "terrain4", "river" };
			for (String s : checkFor) {

				if (input.contains(s)) {

					String property = input.replaceAll(s + "=", "");

					provincehashmap.put(s, property);

					if (s.contains("id") && property.length() <= 4)
						id = property;

					if (brackets == 0) {
						provincesettinghashmap.put(id, provincehashmap.clone());
						provincehashmap.clear();
					}

					provincehashmap.put(s, property);
					if (s.contains("id") && property.length() <= 4) {

						id = property;
					}
				}

				if (brackets == 0) {
					provincesettinghashmap.put(id, provincehashmap.clone());

				}
			}
		}

		Settings.putInHashMap("provincedata", provincesettinghashmap.clone());

	}

}
