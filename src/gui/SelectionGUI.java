package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.Main;

public class SelectionGUI implements ActionListener {

	JFrame frame;
	GridBagLayout layout = new GridBagLayout();
	JPanel panel, pnlLoading;
	JLabel lblGamePath, lblMod, lblLanguage, lblScenario, lblLoading, lblLoadingAnimation;
	JTextField txfGamePath;
	JButton btnChooseGamePath, btnGamePath, btnMod, btnFinish;
	JComboBox<String> cbxMod, cbxLanguage, cbxScenario;

	static String gamePath, mod, language, scenario;

	public SelectionGUI() {

		frame = new JFrame("FTG Szenario Editor | v2.0");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(layout);

		ImageIcon icon = new ImageIcon(SelectionGUI.class.getResource("/logo.png"));
		frame.setIconImage(icon.getImage());

		panel = new JPanel();
		panel.setLayout(layout);
		addComponent(frame, layout, panel, 0, 0, 1, 1, 1, 1, new Insets(0, 0, 0, 0));

		lblGamePath = new JLabel("'For The Glory'-Ordner", SwingConstants.LEFT);
		lblGamePath.setFont(Main.fntBold);
		addComponent(panel, layout, lblGamePath, 0, 0, 2, 1, 1, 0, new Insets(5, 5, 5, 5));

		btnChooseGamePath = new JButton("auswählen");
		btnChooseGamePath.addActionListener(this);
		addComponent(panel, layout, btnChooseGamePath, 1, 0, 1, 1, 0, 0, new Insets(5, 0, 5, 5));

		txfGamePath = new JTextField("C:\\Program Files (x86)\\Steam\\SteamApps\\common\\For The Glory");
		txfGamePath.setPreferredSize(new Dimension(300, 20));
		addComponent(panel, layout, txfGamePath, 0, 1, 2, 1, 1, 0, new Insets(0, 5, 5, 5));

		addComponent(panel, layout, new JPanel(), 0, 2, 1, 1, 1, 0, new Insets(0, 0, 0, 0));

		btnGamePath = new JButton("weiter");
		btnGamePath.addActionListener(this);
		addComponent(panel, layout, btnGamePath, 1, 2, 1, 1, 0, 0, new Insets(0, 5, 5, 5));

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
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

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnChooseGamePath) {

			JFileChooser chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(false);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			chooser.setDialogTitle("'For The Glory'-Ordner auswählen");

			int i = chooser.showOpenDialog(frame);
			if (i == JFileChooser.APPROVE_OPTION) {
				gamePath = chooser.getSelectedFile().getPath();
				txfGamePath.setText(gamePath);
			}

		} else if (e.getSource() == btnGamePath) {

			gamePath = txfGamePath.getText();

			String[] mods = new String[1];
			mods[0] = "Kein Mod";

			File[] modFiles = new File(gamePath + "//Mods").listFiles();
			for (int i = 0; i < modFiles.length; i++) {

				if (modFiles[i].isDirectory()) {

					String[] mods2 = new String[mods.length + 1];
					System.arraycopy(mods, 0, mods2, 0, mods.length);
					mods2[mods.length] = modFiles[i].getName();
					mods = mods2;
				}
			}

			btnChooseGamePath.setEnabled(false);
			txfGamePath.setEnabled(false);
			btnGamePath.setEnabled(false);

			lblMod = new JLabel("Mod (Szenario)", SwingConstants.LEFT);
			lblMod.setFont(Main.fntBold);
			addComponent(panel, layout, lblMod, 0, 3, 2, 1, 1, 0, new Insets(5, 5, 5, 5));

			cbxMod = new JComboBox<>(mods);
			addComponent(panel, layout, cbxMod, 0, 4, 2, 1, 1, 0, new Insets(0, 5, 5, 5));

			addComponent(panel, layout, new JPanel(), 0, 5, 1, 1, 1, 0, new Insets(0, 0, 0, 0));

			btnMod = new JButton("weiter");
			btnMod.addActionListener(this);
			addComponent(panel, layout, btnMod, 1, 5, 1, 1, 0, 0, new Insets(0, 5, 5, 5));

			frame.pack();
			frame.setLocationRelativeTo(null);

		} else if (e.getSource() == btnMod) {

			if (!cbxMod.getSelectedItem().equals("Kein Mod"))
				mod = cbxMod.getSelectedItem().toString();

			String[] languages = new String[0];

			String languagesPath = gamePath + "//Localisation";
			if (!cbxMod.getSelectedItem().equals("Kein Mod"))
				languagesPath = gamePath + "//Mods//" + cbxMod.getSelectedItem() + "//Localisation";

			File[] languagesFiles = new File(languagesPath).listFiles();
			for (int i = 0; i < languagesFiles.length; i++) {

				if (languagesFiles[i].isDirectory()) {

					String[] languages2 = new String[languages.length + 1];
					System.arraycopy(languages, 0, languages2, 0, languages.length);
					languages2[languages.length] = languagesFiles[i].getName();
					languages = languages2;
				}
			}

			cbxMod.setEnabled(false);
			btnMod.setEnabled(false);

			lblLanguage = new JLabel("Sprache", SwingConstants.LEFT);
			lblLanguage.setFont(Main.fntBold);
			addComponent(panel, layout, lblLanguage, 0, 6, 2, 1, 1, 0, new Insets(5, 5, 5, 5));

			cbxLanguage = new JComboBox<>(languages);
			addComponent(panel, layout, cbxLanguage, 0, 7, 2, 1, 1, 0, new Insets(0, 5, 5, 5));

			String[] scenarios = new String[0];

			String scenariosPath = gamePath + "//Scenarios";
			if (!cbxMod.getSelectedItem().equals("Kein Mod"))
				scenariosPath = gamePath + "//Mods//" + cbxMod.getSelectedItem() + "//Scenarios";

			File[] scenariosFiles = new File(scenariosPath).listFiles();
			for (int i = 0; i < scenariosFiles.length; i++) {

				if (scenariosFiles[i].isFile()
						&& scenariosFiles[i].getName().substring((int) scenariosFiles[i].getName().length() - 4,
								(int) scenariosFiles[i].getName().length()).equals(".eeg")) {

					String[] scenarios2 = new String[scenarios.length + 1];
					System.arraycopy(scenarios, 0, scenarios2, 0, scenarios.length);
					scenarios2[scenarios.length] = scenariosFiles[i].getName().substring(0,
							scenariosFiles[i].getName().length() - 4);
					scenarios = scenarios2;
				}
			}

			lblScenario = new JLabel("Szenario");
			lblScenario.setFont(Main.fntBold);
			addComponent(panel, layout, lblScenario, 0, 8, 2, 1, 1, 0, new Insets(5, 5, 5, 5));

			cbxScenario = new JComboBox<>(scenarios);
			addComponent(panel, layout, cbxScenario, 0, 9, 2, 1, 1, 0, new Insets(0, 5, 5, 5));

			addComponent(panel, layout, new JPanel(), 0, 10, 1, 1, 1, 0, new Insets(0, 0, 0, 0));

			btnFinish = new JButton("weiter");
			btnFinish.addActionListener(this);
			addComponent(panel, layout, btnFinish, 1, 10, 1, 1, 0, 0, new Insets(0, 5, 5, 5));

			frame.pack();
			frame.setLocationRelativeTo(null);

		} else if (e.getSource() == btnFinish) {

			language = cbxLanguage.getSelectedItem().toString();
			scenario = cbxScenario.getSelectedItem().toString() + ".eeg";

			cbxLanguage.setEnabled(false);
			cbxScenario.setEnabled(false);
			btnFinish.setEnabled(false);

			pnlLoading = new JPanel();
			pnlLoading.setLayout(layout);
			addComponent(panel, layout, pnlLoading, 0, 11, 2, 1, 1, 0, new Insets(5, 5, 5, 5));

			lblLoadingAnimation = new JLabel(new ImageIcon(SelectionGUI.class.getResource("/loading.gif")));
			addComponent(pnlLoading, layout, lblLoadingAnimation, 0, 0, 1, 1, 0, 0, new Insets(0, 0, 0, 5));

			lblLoading = new JLabel("Laden...", SwingConstants.LEFT);
			lblLoading.setFont(Main.fntBold);
			addComponent(pnlLoading, layout, lblLoading, 1, 0, 1, 1, 1, 0, new Insets(0, 0, 0, 0));

			frame.pack();
			frame.setLocationRelativeTo(null);

			new GUI();
		}
	}

	public static String getGamePath() {
		return gamePath;
	}

	public static String getLanguage() {
		return language;
	}

	public static String getScenario() {
		return scenario;
	}

	public static String getMod() {
		return mod;
	}
}