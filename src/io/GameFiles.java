package io;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A useful class to load a couple of game-files from the game <i>For The
 * Glory</i>
 * 
 * @author Maximilian von Gaisberg
 */

public class GameFiles {

	static String gamePath;

	public GameFiles(String path) {
		//TODO ADDED BY JOHANNES
		gamePath = path + "\\";
	}

	/**
	 * Search the given {@code geography.txt} file for the given string and
	 * returns the tags. It makes sense for one geography files, so this method
	 * is completely useless. But hardcoding <b>sucks</b>, so I had to code
	 * this.
	 *
	 * @param findWhat
	 *            The String to search for (continent, region, area)
	 * 
	 * @return A Stringarray of the detected tags
	 */

	public static String[] loadTags(String findWhat)
			throws FileNotFoundException, IOException {

		String path2 = gamePath + "\\Db\\Map\\geography.txt";

		BufferedReader stream = new BufferedReader(new FileReader(path2));
		String text = "";
		String text2 = "";
		String line = stream.readLine();

		while (line != null) {
			if ((!line.startsWith("#"))
					&& (!line.startsWith("vp_discover_first")))
				text = text + line;

			line = stream.readLine();
		}
		stream.close();
		text = text.replaceAll("\t", "");
		text = text.replaceAll(" ", "");

		String[] lines = text.split("[\\r\\n]+");

		text = "";

		for (String string : lines) {
			// System.out.println(string);
			if (!string.startsWith("vp_discover_first"))
				text = text + string + "\n";
		}

		int count = 0;

		while (text.contains(findWhat + "={")) {
			count++;

			String section = text.substring(text.indexOf(findWhat + "={"),
					text.indexOf("}", text.indexOf(findWhat + "={")));
			text = text.substring(text.indexOf("}") + 1, text.length());
			// System.out.println(count);
			// System.out.println(section);
			text2 = text2 + "\n" + section;

		}
		String[] tags = new String[count];
		int count2 = 0;
		lines = text2.split("[\\r\\n]+");
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].contains("tag=")) {
				int e = count - 1;

				tags[count2] = lines[i].substring(
						lines[i].indexOf("tag=\"") + 5,
						lines[i].indexOf("\"", lines[i].indexOf("tag=\"") + 5));
				count2++;
			}
		}
		// System.out.println("Find That: " + findWhat);

		// Wiederholungen l�schen

		int count3 = 1;
		// System.out.println(tags.length);
		for (int o = 0; o < tags.length; o++) {
			if (o > 0)
				if (tags[o] != null && tags[o - 1] != null)
					if (!tags[o].equals(tags[o - 1])) {
						// System.out.println(tags[o]);
						// System.out.println(count3);
						count3++;
					}
		}
		// System.out.println(count3);

		String[] tags2 = new String[count3];
		tags2[0] = tags[0];

		int count4 = 1;
		for (int o = 0; o < tags.length; o++) {
			if (o > 0)
				if (tags[o] != null && tags[o - 1] != null)
					if (!tags[o].equals(tags[o - 1])) {
						tags2[count4] = tags[o];
						// System.out.println(tags2[count4]);
						count4++;
					}
		}
		Arrays.sort(tags2);
		return tags2;

	}

	/**
	 * Read the Coordinates from the Gamefiles-HashMap and associate the colors
	 * with the Province-IDs
	 * 
	 * @param map
	 *            A HashMap with all the GameFiles
	 * @param biBackend
	 *            The backend.png image
	 * @return
	 */

	public static HashMap<Color, Integer> loadAffilation(
			HashMap<String, Object> map, BufferedImage biBackend) {

		Iterator it = ((HashMap<String, Object>) map.get("provincedata"))
				.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey());

			String value = (String) (((HashMap<String, Object>) pair.getValue())
					.get("city"));
			System.out.println(pair.getKey());
			System.out.println(value);
			if (value != null) {
				value = value.replaceAll(" ", "");
				System.out.println(value);
				it.remove(); // avoids a ConcurrentModificationException
			}
		}

		return null;

	}

	/**
	 * Load the colors for the Map from .txt File
	 * 
	 * @param MapPath
	 *            Path of the Map Configuration File (most likely called
	 *            {@code MapConfigurationFile.txt})
	 * @return A HashMap with all the colors and the IDs contained in the file.
	 *         It has only one use:<br>
	 *         {@code MapPanel(bif,bib,GameFiles.loadMap(String path));}
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static HashMap<Color, Integer> loadMap(String MapPath)
			throws IOException, InterruptedException {
		HashMap<Color, Integer> map = new HashMap<Color, Integer>();
		FileReader r = new FileReader(new File(MapPath));
		BufferedReader br = new BufferedReader(r);
		String line;
		while ((line = br.readLine()) != null)
			if (line.startsWith("ID")) {
				String line2 = line.substring(9);
				String line3 = line2.substring(0, line2.indexOf(" "));
				// System.out.println(line3);

				String line4 = line2.substring(line2.indexOf(" ",
						line2.indexOf(" ") + 1) + 1);
				String line5 = line4.substring(0, 6);
				// System.out.println(line5);
				int zahl = Integer.parseInt(line5, 16);
				// System.out.println(zahl);
				Color color = new Color(zahl);
				// System.out.println(color.getRed());
				map.put(color, Integer.parseInt(line3));
			}
		br.close();
		return map;
	}

	public String[] loadCultures() throws IOException {
		int length = 0;

		FileReader r = new FileReader(new File(gamePath + "Db\\cultures.txt"));
		BufferedReader br = new BufferedReader(r);
		String line;
		while ((line = br.readLine()) != null)
			if (line.endsWith("{")) {
				length++;
			}
		br.close();
		r.close();
		r = null;
		br = null;
		r = new FileReader(new File(gamePath + "Db\\cultures.txt"));
		br = new BufferedReader(r);
		String[] cultures = new String[length];
		int i = 0;
		while ((line = br.readLine()) != null)
			if (line.endsWith("{")) {
				// System.out.println(line);
				String line2 = line.substring(0, line.indexOf("="));
				// System.out.println(line2);
				String line3 = line2.trim();
				// System.out.println(line3);
				cultures[i] = line3;
				i++;
			}
		br.close();

		return cultures;

	}

	public String[] loadGoods() throws IOException {
		int length = 0;

		FileReader r = new FileReader(new File(gamePath + "Db\\goods.txt"));
		BufferedReader br = new BufferedReader(r);
		String line;
		while ((line = br.readLine()) != null)
			if (line.endsWith("{")) {
				length++;
			}
		br.close();
		r.close();
		r = null;
		br = null;
		r = new FileReader(new File(gamePath + "Db\\goods.txt"));
		br = new BufferedReader(r);
		String[] cultures = new String[length];
		int i = 0;
		while ((line = br.readLine()) != null)
			if (line.endsWith("{")) {
				// System.out.println(line);
				String line2 = line.substring(0, line.indexOf("="));
				// System.out.println(line2);
				String line3 = line2.trim();
				// System.out.println(line3);
				cultures[i] = line3;
				i++;
			}
		br.close();

		return cultures;

	}

	public String[] loadReligions() throws IOException {
		int length = 0;

		FileReader r = new FileReader(new File(gamePath
				+ "Db\\Religions\\religions.txt"));
		BufferedReader br = new BufferedReader(r);
		String line;
		while ((line = br.readLine()) != null)
			if (line.endsWith("{") && !line.contains("heretic")
					&& !line.contains("allowed_conversion")
					&& !line.contains("conflict")
					&& !line.contains("aggressiveness")
					&& !line.contains("income_bonus") && !line.contains("war")) {
				length++;
			}
		br.close();
		r.close();
		r = null;
		br = null;
		r = new FileReader(new File(gamePath + "Db\\Religions\\religions.txt"));
		br = new BufferedReader(r);
		String[] cultures = new String[length];
		int i = 0;
		while ((line = br.readLine()) != null)
			if (line.endsWith("{") && !line.contains("heretic")
					&& !line.contains("allowed_conversion")
					&& !line.contains("conflict")
					&& !line.contains("aggressiveness")
					&& !line.contains("income_bonus") && !line.contains("war")) {
				// System.out.println(line);
				String line2 = line.substring(0, line.indexOf("="));
				// System.out.println(line2);
				String line3 = line2.trim();
				// System.out.println(line3);
				cultures[i] = line3;
				i++;
			}
		br.close();

		return cultures;

	}

	public String[] loadTerrains() throws IOException {
		int length = 0;

		FileReader r = new FileReader(new File(gamePath
				+ "\\Db\\Map\\terrains.txt"));
		BufferedReader br = new BufferedReader(r);
		String line;
		while ((line = br.readLine()) != null)
			if (line.endsWith("{")) {
				length++;
			}
		br.close();
		r.close();
		r = null;
		br = null;
		r = new FileReader(new File(gamePath + "\\Db\\Map\\terrains.txt"));
		br = new BufferedReader(r);
		String[] cultures = new String[length];
		int i = 0;
		while ((line = br.readLine()) != null)
			if (line.endsWith("{")) {
				// System.out.println(line);
				String line2 = line.substring(0, line.indexOf("="));
				// System.out.println(line2);
				String line3 = line2.trim();
				// System.out.println(line3);
				cultures[i] = line3;
				i++;
			}
		br.close();

		return cultures;

	}
}
