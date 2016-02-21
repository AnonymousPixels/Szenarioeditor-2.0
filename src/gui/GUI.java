package gui;

import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import io.Settings;

public class GUI {

	HashMap<String, Object> dataMap = new HashMap<String, Object>();

	String gamePath, mod, language, scenario;

	public GUI() {

		try {
			readData();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fehler beim Einlesen der Daten aufgetreten! Programm wird beendet...",
					"Fehler aufgetreten", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(1);
		}
	}

	void readData() throws IOException {

		gamePath = SelectionGUI.getGamePath();
		language = SelectionGUI.getLanguage();
		mod = SelectionGUI.getMod();

		if (mod != null) {
			scenario = gamePath + "//Mods//" + mod + "//Scenarios//" + SelectionGUI.getScenario();
		} else {
			scenario = gamePath + "//Scenarios//" + SelectionGUI.getScenario();
		}

		dataMap = Settings.getSettings(gamePath, language, scenario);
	}
}