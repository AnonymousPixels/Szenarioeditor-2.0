package editor;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;

/**
 * Main class of the szenario editor. Contains main void and handles the program
 * flow.
 * 
 * @author Felix
 */
public class Main {

	static Color clrBackground = new Color(240, 240, 240), clrFont = new Color(0, 0, 0);
	static Font fntStandard = new Font("Verdana", 0, 12), fntBold = new Font("Verdana", 1, 12);
	
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Error while setting LookAndFeel! Default Java LookAndFeel will be used...");
		}
		
		new SelectionGUI();
	}
}