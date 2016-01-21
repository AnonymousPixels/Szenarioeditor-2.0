package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import essentials.Essentials;
import main.Main;

public class SelectionGUI {

	static JFrame frame;
	static JPanel panel;
	static GridBagLayout layout;
	static JLabel lblPath, lblMods, lblName;
	static JTextField txfPath, txfName;
	static JButton btnPath, btnContinue, btnAccept;
	static JFileChooser chooser;
	static JComboBox<String> cbxMods;
	static File file = new File("");
	static String path = "D:\\Programme\\Steam\\SteamApps\\common\\For The Glory\\";
	static boolean finishedLoading = false;

	public SelectionGUI() {

		layout = new GridBagLayout();

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(layout);

		ImageIcon icon = new ImageIcon(SelectionGUI.class.getResource("/logo.png"));
		frame.setIconImage(icon.getImage());

		panel = new JPanel();
		panel.setLayout(layout);
		panel.setBackground(Main.clrBackground);
		Essentials.addComponent(frame, layout, panel, 0, 0, 1, 1, 1, 1, new Insets(0, 0, 0, 0));

		lblPath = new JLabel("Pfad:", SwingConstants.LEFT);
		lblPath.setForeground(Main.clrFont);
		lblPath.setFont(Main.fntBold);
		Essentials.addComponent(panel, layout, lblPath, 0, 0, 1, 1, 1, 0, new Insets(10, 10, 10, 10));

		chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setDialogTitle("'For The Glory'-Ordner ausw�hlen");

		txfPath = new JTextField(path);
		txfPath.setEditable(false);
		txfPath.setForeground(Main.clrFont);
		txfPath.setBackground(Color.white);
		txfPath.setPreferredSize(new Dimension(300, 20));
		Essentials.addComponent(panel, layout, txfPath, 0, 1, 2, 1, 1, 0, new Insets(0, 10, 10, 10));

		lblMods = new JLabel("Mod ausw�hlen", SwingConstants.LEFT);
		lblMods.setForeground(Color.black);
		lblMods.setFont(Main.fntBold);
		Essentials.addComponent(panel, layout, lblMods, 0, 3, 2, 1, 1, 0, new Insets(10, 10, 5, 10));
		lblMods.setVisible(false);

		lblName = new JLabel("Name", SwingConstants.LEFT);
		lblName.setForeground(Color.black);
		lblName.setFont(new Font("Verdana", 0, 12));
		Essentials.addComponent(panel, layout, lblName, 0, 5, 2, 1, 1, 0, new Insets(5, 10, 5, 10));
		lblName.setVisible(false);

		txfName = new JTextField();
		txfName.setEditable(true);
		txfName.setForeground(Color.black);
		txfName.setBackground(Color.white);
		Essentials.addComponent(panel, layout, txfName, 0, 6, 2, 1, 1, 0, new Insets(0, 10, 10, 10));
		txfName.setVisible(false);

		btnPath = new JButton("ausw�hlen");
		btnPath.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int i = chooser.showOpenDialog(null);
				if (i == JFileChooser.APPROVE_OPTION) {

					file = chooser.getSelectedFile();
					path = file.getPath();
					txfPath.setText(path);
				}
			}
		});
		Essentials.addComponent(panel, layout, btnPath, 1, 0, 1, 1, 0, 0, new Insets(10, 0, 10, 10));

		Essentials.addComponent(panel, layout, new JPanel(), 0, 2, 1, 1, 1, 0, new Insets(0, 0, 0, 0));

		btnAccept = new JButton("weiter");
		btnAccept.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Thread loadingThread = new Thread(new Runnable() {

					@Override
					public void run() {

						Thread loadLoadingGUI = new Thread(new Runnable() {

							@Override
							public void run() {

								try {
									loadGUI();
								} catch (IOException | InterruptedException e) {

									e.printStackTrace();
								}
							}
						});
						loadLoadingGUI.start();

						try {
							new GUI();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

				if (cbxMods.getSelectedItem().equals("Neuen Mod erstellen")) {

					if (txfName.getText().trim() != null && !txfName.getText().trim().equals("")) {

						loadingThread.start();
					} else
						JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Namen ein!", "Fehler  aufgetreten",
								JOptionPane.ERROR_MESSAGE);
				} else
					loadingThread.start();
			}
		});
		Essentials.addComponent(panel, layout, btnAccept, 1, 7, 1, 1, 0, 0, new Insets(0, 10, 10, 10));
		btnAccept.setVisible(false);

		btnContinue = new JButton("weiter");
		btnContinue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					if (path.trim() != null && !path.trim().equals("")) {

						String modDir = path + "//Mods";
						File[] modFiles = new File(modDir).listFiles();
						String[] mods = new String[1];

						mods[0] = "Neuen Mod erstellen";

						for (int i = 0; i < modFiles.length; i++) {

							if (modFiles[i].isDirectory()) {

								String[] mods2 = new String[mods.length + 1];
								System.arraycopy(mods, 0, mods2, 0, mods.length);
								mods2[mods.length] = modFiles[i].getName();
								mods = mods2;
							}
						}

						cbxMods = new JComboBox<String>(mods);
						cbxMods.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {

								if (cbxMods.getSelectedItem().equals("Neuen Mod erstellen")) {
									txfName.setVisible(true);
									lblName.setVisible(true);
									frame.pack();
								} else {
									txfName.setVisible(false);
									lblName.setVisible(false);
									frame.pack();
								}
							}
						});
						Essentials.addComponent(panel, layout, cbxMods, 0, 4, 2, 1, 1, 0, new Insets(0, 10, 10, 10));
						cbxMods.setVisible(false);

						lblMods.setVisible(true);
						cbxMods.setVisible(true);
						lblName.setVisible(true);
						txfName.setVisible(true);
						btnAccept.setVisible(true);

						btnPath.setEnabled(false);
						btnContinue.setEnabled(false);

						frame.pack();

					} else
						JOptionPane.showMessageDialog(null, "Geben Sie bitte den korrekten Spiel-Pfad an!",
								"Fehler aufgetreten", JOptionPane.ERROR_MESSAGE);
				} catch (Exception E) {
					JOptionPane.showMessageDialog(null, "Geben Sie bitte den korrekten Spiel-Pfad an!",
							"Fehler aufgetreten", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		Essentials.addComponent(panel, layout, btnContinue, 1, 2, 1, 1, 0, 0, new Insets(0, 10, 10, 10));

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	static void loadGUI() throws IOException, InterruptedException {

		frame.setTitle("L�dt...");

		btnPath.setEnabled(false);
		btnContinue.setEnabled(false);
		txfPath.setEditable(false);

		btnPath.setVisible(false);
		btnContinue.setVisible(false);
		txfPath.setVisible(false);
		lblPath.setVisible(false);

		try {
			panel.removeAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ImageIcon icon = new ImageIcon(Main.class.getResource("/loading.gif"));

		JLabel label = new JLabel("Laden...");
		label.setForeground(Main.clrFont);
		label.setFont(Main.fntBold);
		Essentials.addComponent(panel, layout, label, 0, 0, 1, 1, 0, 0, new Insets(5, 5, 5, 5));

		Essentials.addComponent(panel, layout, new JPanel(), 1, 0, 1, 1, 1, 0, new Insets(0, 0, 0, 0));

		JLabel gif = new JLabel(icon);
		Essentials.addComponent(panel, layout, gif, 2, 0, 1, 1, 0, 0, new Insets(5, 0, 5, 5));

		gif.setVisible(false);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		Essentials.addComponent(panel, layout, progressBar, 0, 1, 3, 1, 1, 0, new Insets(5, 5, 5, 5));

		frame.pack();
		frame.setLocationRelativeTo(null);

		while (!finishedLoading)
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		frame.setVisible(false);
	}

	public static String getModFolderName() {
		return path;
	}

	public static String getGameFolderName() {

		String s = "";

		if (cbxMods.getSelectedItem().equals("Neuen Mod erstellen"))
			return txfName.getText().trim();
		return (String) cbxMods.getSelectedItem();
	}
}
