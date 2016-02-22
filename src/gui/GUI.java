package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import io.Settings;
import map.MapPanel;

public class GUI {

	JFrame frame;
	GridBagLayout layout = new GridBagLayout();
	JPanel panel, pnlOptions;
	JTabbedPane tabbedPane;
	
	MapPanel mapPanel;

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

		loadFrame();
	}

	static void addComponent(Container cont, GridBagLayout gbl, Component c, int x, int y, int width, int height,
			double weightx, double weighty, Insets insets) {

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.insets = insets;
		gbl.setConstraints(c, gbc);
		cont.add(c);
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
	
	void loadFrame() {
		
		frame = new JFrame("FTG Szenario Editor | v2.0");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(layout);
		frame.setMinimumSize(new Dimension(1200, 800));

		panel = new JPanel();
		panel.setLayout(layout);
		addComponent(frame, layout, panel, 0, 0, 1, 1, 1, 1, new Insets(0, 0, 0, 0));

		mapPanel = SelectionGUI.getMapPanel();
		addComponent(panel, layout, mapPanel, 0, 0, 1, 1, 1, 1, new Insets(5, 5, 5, 5));
		
		pnlOptions = new JPanel();
		pnlOptions.setLayout(layout);
		addComponent(panel, layout, pnlOptions, 0, 1, 1, 1, 1, 0, new Insets(0, 5, 5, 5));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		addComponent(panel, layout, tabbedPane, 1, 0, 1, 2, 0, 1, new Insets(5, 0, 5, 5));
	}
}