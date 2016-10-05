/**
 * 
 */
package test;

import io.GameFiles;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
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
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " " + pair.getValue());

			Point north = new Point(10000, 10000);
			Point left = new Point(10000, 10000);
			Point right = new Point(-1, -1);
			Point south = new Point(-1, -1);

			for (int x = 0; x < img.getWidth(); x++) {
				for (int y = 0; y < img.getHeight(); y++) {

					if (img2.getRGB(x, y) == ((Color) pair.getKey()).getRGB()) {
						if (x < left.getX())
							left = new Point(x, y);
						if (x > right.getX())
							right = new Point(x, y);
						if (y < north.getY())
							north = new Point(x, y);
						if (y > south.getY())
							south = new Point(x, y);

					}

				}
			}
			System.out.println(north);
			System.out.println(south);
			System.out.println(right);
			System.out.println(left);

			Pnorth.put(pair.getValue(), north);
			Psouth.put(pair.getValue(), south);
			Pleft.put(pair.getValue(), left);
			Pright.put(pair.getValue(), right);

		}
		Pnorth.store(new FileWriter(new File("C://g//north.properties")), "");
		Psouth.store(new FileWriter(new File("C://g//south.properties")), "");
		Pleft.store(new FileWriter(new File("C://g//left.properties")), "");
		Pright.store(new FileWriter(new File("C://g//right.properties")), "");
	}
}
