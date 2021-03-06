/**
 * 
 */
package test;

import io.GameFiles;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

/**
 * @author Maximilian
 *
 */
public class GenerateBorders {

	/**
	 * 
	 */
	public GenerateBorders() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		BufferedImage img = null;
		try {
			img = ImageIO
					.read(new File(
							"C:\\Users\\Maximilian\\GitHub\\ForTheGlory-Scenarioeditor\\ForTheGlory-Scenarioeditor\\res\\frontend.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BufferedImage img2 = null;

		try {
			img2 = ImageIO
					.read(new File(
							"C:\\Users\\Maximilian\\GitHub\\ForTheGlory-Scenarioeditor\\ForTheGlory-Scenarioeditor\\res\\backend.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map map = GameFiles
				.loadAffilation(
						io.Settings
								.getSettings(
										"C:\\Program Files (x86)\\Steam\\SteamApps\\common\\For The Glory",
										"English",
										"C:\\Program Files (x86)\\Steam\\SteamApps\\common\\For The Glory\\Scenarios\\1419 - The Grand Campaign.eeg"),
						img2);

		Properties Pnorth = new Properties();
		Properties Psouth = new Properties();
		Properties Pleft = new Properties();
		Properties Pright = new Properties();

		Pnorth.load(new FileInputStream(new File("C://g//north.properties")));
		Psouth.load(new FileInputStream(new File("C://g//south.properties")));
		Pleft.load(new FileInputStream(new File("C://g//left.properties")));
		Pright.load(new FileInputStream(new File("C://g//right.properties")));

		Iterator it = map.entrySet().iterator();
		int i = map.size();
		int e = 0;
		System.out.println("Image: "+img.getWidth()+" "+img.getHeight());
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println("A:" + pair.getKey() + " " + pair.getValue());
			e++;
			System.out.println(e + "/" + i);
			if (Pnorth.containsKey(String.valueOf(pair.getValue()))) {
				System.out.println("skip");
				continue;

			}
			Point north = new Point(img.getWidth(), img.getHeight());
			Point left = new Point(img.getWidth(), img.getHeight());
			Point right = new Point(-1, -1);
			Point south = new Point(-1, -1);

			for (int x = 0; x < img.getWidth(); x++) {
				for (int y = 0; y < img.getHeight(); y++) {

					if (img2.getRGB(x, y) == ((Color) pair.getKey()).getRGB()) {
						if (x < left.getX()) {
							left = new Point(x, y);
						}
						if (x > right.getX()) {
							right = new Point(x, y);
						}
						if (y < north.getY()) {
							north = new Point(x, y);
						}
						if (y > south.getY()) {
							south = new Point(x, y);
						}

					}

				}
			}

			Pnorth.put(String.valueOf(pair.getValue()), north.toString());
			Psouth.put(String.valueOf(pair.getValue()), south.toString());
			Pleft.put(String.valueOf(pair.getValue()), left.toString());
			Pright.put(String.valueOf(pair.getValue()), right.toString());

			Pnorth.store(new FileWriter(new File("C://g//north.properties")),
					"");
			Psouth.store(new FileWriter(new File("C://g//south.properties")),
					"");
			Pleft.store(new FileWriter(new File("C://g//left.properties")), "");
			Pright.store(new FileWriter(new File("C://g//right.properties")),
					"");
		}

	}
}
