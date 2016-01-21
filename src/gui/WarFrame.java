package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JTextPane;

import java.awt.Insets;

import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.Color;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import java.awt.Font;

public class WarFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JComboBox comboBox, comboBox_1, comboBox_2, comboBox_3, comboBox_4,
			comboBox_5;
	private JEditorPane editorPane, editorPane_1;
	int type, id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WarFrame frame = new WarFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WarFrame() {
		setTitle("Add new war");
		setMinimumSize(new Dimension(550, 400));
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0, 1.0, 1.0, 0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panel = new JPanel();
		panel.setFont(new Font("Verdana", Font.PLAIN, 11));
		panel.setBorder(new TitledBorder(null, "Date", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		comboBox = new JComboBox();
		comboBox.setFont(new Font("Verdana", Font.PLAIN, 11));
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "1", "2",
				"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
				"14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
				"24", "25", "26", "27", "28", "29", "30" }));
		comboBox.setEditable(true);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 0;
		panel.add(comboBox, gbc_comboBox);

		comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "january",
				"february", "march", "april", "mai", "june", "uly", "august",
				"september", "october", "november", "december" }));
		comboBox_1.setEditable(true);
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 0;
		panel.add(comboBox_1, gbc_comboBox_1);

		textField = new JTextField();
		textField.setFont(new Font("Verdana", Font.PLAIN, 11));
		textField.setMinimumSize(new Dimension(80, 20));
		textField.setText("1337");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 0;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		panel_1.setBorder(new TitledBorder(null, "Enddate",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridwidth = 2;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 2;
		gbc_panel_1.gridy = 0;
		contentPane.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		comboBox_2 = new JComboBox();
		comboBox_2.setFont(new Font("Verdana", Font.PLAIN, 11));
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] { "1", "2",
				"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
				"14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
				"24", "25", "26", "27", "28", "29", "30" }));
		comboBox_2.setEditable(true);
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 0;
		gbc_comboBox_2.gridy = 0;
		panel_1.add(comboBox_2, gbc_comboBox_2);

		comboBox_3 = new JComboBox();
		comboBox_3.setFont(new Font("Verdana", Font.PLAIN, 11));
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] { " january",
				"february", "march", "april", "mai", "june", "uly", "august",
				"september", "october", "november", "december" }));
		comboBox_3.setEditable(true);
		GridBagConstraints gbc_comboBox_3 = new GridBagConstraints();
		gbc_comboBox_3.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3.gridx = 1;
		gbc_comboBox_3.gridy = 0;
		panel_1.add(comboBox_3, gbc_comboBox_3);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		textField_1.setMinimumSize(new Dimension(80, 20));
		textField_1.setText("1337");
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 0;
		panel_1.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);

		JPanel panel_2 = new JPanel();
		panel_2.setFont(new Font("Verdana", Font.PLAIN, 11));
		panel_2.setBorder(new TitledBorder(null, "Attackers",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridwidth = 4;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		contentPane.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.weighty = 1.0;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel_2.add(scrollPane, gbc_scrollPane);

		editorPane = new JEditorPane();
		editorPane.setFont(new Font("Verdana", Font.PLAIN, 11));
		editorPane.setPreferredSize(new Dimension(106, 100));
		scrollPane.setViewportView(editorPane);

		comboBox_4 = new JComboBox();
		GridBagConstraints gbc_comboBox_4 = new GridBagConstraints();
		gbc_comboBox_4.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_4.gridx = 0;
		gbc_comboBox_4.gridy = 1;
		panel_2.add(comboBox_4, gbc_comboBox_4);

		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Verdana", Font.PLAIN, 11));
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.gridx = 1;
		gbc_btnAdd.gridy = 1;
		panel_2.add(btnAdd, gbc_btnAdd);

		JPanel panel_3 = new JPanel();
		panel_3.setFont(new Font("Verdana", Font.PLAIN, 11));
		panel_3.setBorder(new TitledBorder(null, "Defenders",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.gridwidth = 4;
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 2;
		contentPane.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_3.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_3.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.weighty = 1.0;
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panel_3.add(scrollPane_1, gbc_scrollPane_1);

		editorPane_1 = new JEditorPane();
		editorPane_1.setPreferredSize(new Dimension(106, 100));
		scrollPane_1.setViewportView(editorPane_1);

		comboBox_5 = new JComboBox();
		GridBagConstraints gbc_comboBox_5 = new GridBagConstraints();
		gbc_comboBox_5.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_5.gridx = 0;
		gbc_comboBox_5.gridy = 1;
		panel_3.add(comboBox_5, gbc_comboBox_5);

		JButton btnAdd_1 = new JButton("Add");
		btnAdd_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		GridBagConstraints gbc_btnAdd_1 = new GridBagConstraints();
		gbc_btnAdd_1.gridx = 1;
		gbc_btnAdd_1.gridy = 1;
		panel_3.add(btnAdd_1, gbc_btnAdd_1);

		JButton btnOk = new JButton("OK");
		btnOk.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String result = "war = {\n    id = { type = "
						+ type
						+ " id = "
						+ id
						+ " }\n    date = { year = "
						+ textField.getText()
						+ " month = "
						+ comboBox_1.getSelectedItem().toString()
						+ " day = "
						+ comboBox.getSelectedItem().toString()
						+ " }\n    enddate = { year = "
						+ textField_1.getText()
						+ " month = "
						+ comboBox_3.getSelectedItem().toString()
						+ " day = "
						+ comboBox_2.getSelectedItem().toString()
						+ " }\n    attackers = {\n    id = { type = "
						+ type
						+ " id = "
						+ (id + 1)
						+ " }\n    type = war\n    expirydate = { year = 0 month = january day = 0 }\n    participant = { "
						+ editorPane.getText()
						+ " }\n}\n    defenders = {\n    id = { type = "
						+ type
						+ " id = "
						+ (id + 2)
						+ " }\n    type = war\n    expirydate = { year = 0 month = january day = 0 }\n    participant = { "
						+ editorPane.getText() + " }\n}";
				gui.GlobalDataPanel.jepWar
						.setText(gui.GlobalDataPanel.jepWar
								.getText() + "\n" + result);
				close();

			}
		});
		btnOk.setMinimumSize(new Dimension(70, 23));
		btnOk.setMaximumSize(new Dimension(70, 23));
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 0, 5, 0);
		gbc_btnOk.anchor = GridBagConstraints.EAST;
		gbc_btnOk.gridx = 3;
		gbc_btnOk.gridy = 3;
		contentPane.add(btnOk, gbc_btnOk);

	}

	private void close() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
}
