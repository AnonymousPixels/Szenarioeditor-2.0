package editor;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import scenarioeditor.Strings;

public class SelectionGUI {

	static File file = new File("");
	static String path = "";
	
	public SelectionGUI() {
		
		GridBagLayout layout = new GridBagLayout();
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(layout);
		
		ImageIcon icon = new ImageIcon(SelectionGUI.class.getResource("/logo.png"));
		frame.setIconImage(icon.getImage());
		
		JPanel panel = new JPanel();
		panel.setLayout(layout);
		panel.setBackground(Main.clrBackground);
		Essentials.addComponent(frame, layout, panel, 0, 0, 1, 1, 1, 1, new Insets(0, 0, 0, 0));
		

		JLabel lblPath = new JLabel("Pfad:", SwingConstants.LEFT);
		lblPath.setForeground(Main.clrFont);
		lblPath.setFont(Main.fntBold);
		Essentials.addComponent(panel, layout, lblPath, 0, 0, 1, 1, 1, 0, new Insets(10, 10, 10, 10));

		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setDialogTitle("'For The Glory'-Ordner auswählen");
		
		JTextField txfPath = new JTextField("D:\\Programme\\Steam\\SteamApps\\common\\For The Glory\\");
		txfPath.setEditable(false);
		txfPath.setForeground(Main.clrFont);
		txfPath.setBackground(Color.white);
		txfPath.setPreferredSize(new Dimension(300, 20));
		Essentials.addComponent(panel, layout, txfPath, 0, 1, 2, 1, 1, 0, new Insets(0, 10, 10, 10));
		
		JButton btnPath = new JButton("auswählen");
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

		JButton btnContinue = new JButton("weiter");
		btnContinue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				//TODO
			}
		});
		Essentials.addComponent(panel, layout, btnContinue, 1, 2, 1, 1, 0, 0, new Insets(0, 10, 10, 10));

		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
