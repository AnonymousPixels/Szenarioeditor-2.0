package map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;

public class MapPanel extends JPanel implements MouseListener,
		MouseMotionListener, MouseWheelListener {

	BufferedImage biBackend, biFrontendOriginal, biBackendOriginal;
	JScrollBar sliderX, sliderY;
	JLabel image;
	Point drag;
	float zoomFactorSelected = 0.3f;
	float zoomFactor = (float) 0.1;
	Map map2;
	int x = 10000, y = 2000;
	int newHeight, newWidth;
	private List<IMapEventListener> listeners;
	boolean[] state = new boolean[5000];
	boolean multiSelectionMode;

	Point[] Bleft = new Point[5000];
	Point[] Bright = new Point[5000];
	Point[] Bnorth = new Point[5000];
	Point[] Bsouth = new Point[5000];

	public MapPanel(BufferedImage Frontend, BufferedImage Backend, Map map) {
		super();
		this.setOpaque(true);
		this.setLayout(null);
		listeners = new ArrayList<IMapEventListener>();
		biFrontendOriginal = Frontend;
		biBackend = biBackendOriginal = Backend;
		// image = new JLabel(new ImageIcon(Frontend));
		sliderX = new JScrollBar();
		sliderY = new JScrollBar();
		sliderY.setOrientation(SwingConstants.VERTICAL);
		sliderX.setOrientation(SwingConstants.HORIZONTAL);
		sliderX.setVisible(true);
		sliderY.setVisible(true);
		this.add(sliderX);
		this.add(sliderY);
		// this.add(image);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		mouseWheelMoved(new MouseWheelEvent(this, 1, 1, 1, 1, 1, 0, false, 0,
				0, 0));
		map2 = map;
		try {
			loadBorders();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void loadBorders() throws FileNotFoundException, IOException {
		System.out.println("Loading Border");

		ClassLoader classLoader = getClass().getClassLoader();

		File left = new File(classLoader.getResource("left.properties")
				.getFile());
		File right = new File(classLoader.getResource("right.properties")
				.getFile());
		File north = new File(classLoader.getResource("north.properties")
				.getFile());
		File south = new File(classLoader.getResource("south.properties")
				.getFile());

		Properties Pnorth = new Properties();
		Properties Psouth = new Properties();
		Properties Pleft = new Properties();
		Properties Pright = new Properties();

		Pleft.load(new FileInputStream(left));
		Pright.load(new FileInputStream(right));
		Pnorth.load(new FileInputStream(north));
		Psouth.load(new FileInputStream(south));

		for (int i = 0; i < 5000; i++) {
			String c = Pleft.getProperty(String.valueOf(i));
			try {
				int x = Integer.parseInt(c.substring(17, c.indexOf(",")));
				int y = Integer.parseInt(c.substring(c.indexOf("y=") + 2,
						c.indexOf("]")));
				Bleft[i] = new Point(x, y);
			} catch (NullPointerException e) {
			}

		}

		for (int i = 0; i < 5000; i++) {
			String c = Pright.getProperty(String.valueOf(i));
			try {
				int x = Integer.parseInt(c.substring(17, c.indexOf(",")));
				int y = Integer.parseInt(c.substring(c.indexOf("y=") + 2,
						c.indexOf("]")));
				Bright[i] = new Point(x, y);
			} catch (NullPointerException e) {
			}

		}

		for (int i = 0; i < 5000; i++) {
			String c = Pnorth.getProperty(String.valueOf(i));
			try {
				int x = Integer.parseInt(c.substring(17, c.indexOf(",")));
				int y = Integer.parseInt(c.substring(c.indexOf("y=") + 2,
						c.indexOf("]")));
				Bnorth[i] = new Point(x, y);
			} catch (NullPointerException e) {
			}

		}

		for (int i = 0; i < 5000; i++) {
			String c = Psouth.getProperty(String.valueOf(i));
			try {
				int x = Integer.parseInt(c.substring(17, c.indexOf(",")));
				int y = Integer.parseInt(c.substring(c.indexOf("y=") + 2,
						c.indexOf("]")));
				Bsouth[i] = new Point(x, y);
			} catch (NullPointerException e) {
			}

		}
		System.out.println("Loaded Borders");

	}

	public void setMultiSelectionMode(boolean mode) {
		this.multiSelectionMode = mode;

		if (!mode)
			for (int i = 0; i < 5000; i++) {
				state[i] = false;
			}

	}

	public void setState(int province, boolean state) {
		this.state[province] = state;
	}

	public boolean getState(int province) {
		return state[province];
	}

	public void addMapListener(IMapEventListener toAdd) {
		listeners.add(toAdd);
	}

	public void paintComponent(Graphics g) {
		System.out.println(x);
		System.out.println(y);
		System.out.println(zoomFactorSelected);
		super.paintComponent(g);
		newHeight = (int) (biFrontendOriginal.getHeight() * zoomFactorSelected / 2);
		newWidth = (int) ((float) newHeight * ((float) this.getWidth() / (float) this
				.getHeight()));

		if ((y + newHeight) > biFrontendOriginal.getHeight()) {
			y = biFrontendOriginal.getHeight() - newHeight;
		}
		if ((x + newWidth) > biFrontendOriginal.getWidth()) {
			x = biFrontendOriginal.getWidth() - newWidth;
		}

		if (newHeight > y) {
			y = newHeight;
		}
		if (newWidth > x) {
			x = newWidth;
		}

		g.drawImage(biFrontendOriginal, 0, 0, this.getWidth(),
				this.getHeight(), x - newWidth, y - newHeight, x + newWidth, y
						+ newHeight, null);
		g.setColor(Color.RED);
		for (int i = 0; i < 5000; i++) {
			if (state[i]) {

				Point left = calculateChords(Bleft[i]);
				Point right = calculateChords(Bright[i]);
				Point north = calculateChords(Bnorth[i]);
				Point south = calculateChords(Bsouth[i]);
				double zoom = (double) (this.newHeight * 2)
						/ (double) this.getHeight();

				int size = (int) ((double) 13 / zoom) + 2;
				int x = (int) ((left.getX() + right.getX()) / 2);
				int y = (int) ((north.getY() + south.getY()) / 2);

				// g.fillRect((int) left.getX() - 1, (int) left.getY() - 1, 3,
				// 3);
				// g.fillRect((int) right.getX() - 1, (int) right.getY() - 1, 3,
				// 3);
				// g.fillRect((int) south.getX() - 1, (int) south.getY() - 1, 3,
				// 3);
				// g.fillRect((int) north.getX() - 1, (int) north.getY() - 1, 3,
				// 3);
				g.fillOval((int) x - size / 2, (int) y - size / 2, size, size);

			}
		}

	}

	private Point calculateChords(Point p) {
		double zoom = (double) (this.newHeight * 2) / (double) this.getHeight();
		double x1 = p.getX() - (this.x - this.newWidth);
		double y1 = p.getY() - (this.y - this.newHeight);
		int y = (int) (y1 / zoom);
		int x = (int) (x1 / zoom);
		return new Point(x, y);
	}

	public void mouseClicked(MouseEvent arg0) {
		Graphics2D g = (Graphics2D) biBackend.getGraphics();

		newHeight = (int) (biFrontendOriginal.getHeight() * zoomFactorSelected / 2);
		newWidth = (int) ((float) newHeight * ((float) this.getWidth() / (float) this
				.getHeight()));

		if ((y + newHeight) > biFrontendOriginal.getHeight()) {
			y = biFrontendOriginal.getHeight() - newHeight;
		}
		if ((x + newWidth) > biFrontendOriginal.getWidth()) {
			x = biFrontendOriginal.getWidth() - newWidth;
		}

		if (newHeight > y) {
			y = newHeight;
		}
		if (newWidth > x) {
			x = newWidth;
		}

		g.drawImage(biBackendOriginal, 0, 0, this.getWidth(), this.getHeight(),
				x - newWidth, y - newHeight, x + newWidth, y + newHeight, null);
		this.getGraphics().drawImage(biFrontendOriginal, 0, 0, this.getWidth(),
				this.getHeight(), x - newWidth, y - newHeight, x + newWidth,
				y + newHeight, null);

		Color target = new Color(biBackend.getRGB(
				(arg0.getX() + sliderX.getValue()),
				(arg0.getY() + sliderY.getValue())));
		System.out.println("Red:" + target.getRed() + " Green:"
				+ target.getGreen() + " Blue:" + target.getBlue());
		System.out.println(map2.get(target));

		// ********************************************************
		// Hier Code einfügen!
		// target ist die ausgewählte Provinz
		// map2.get(target) ist die gespeicherte ID für die Provinz
		// ********************************************************

		if (multiSelectionMode) {
			setState(((Integer) map2.get(target)),
					!getState((Integer) map2.get(target)));
			System.out.println("Set state of "
					+ String.valueOf((Integer) map2.get(target)) + " to "
					+ getState((Integer) map2.get(target)));

			String all = new String("");
			for (int i = 0; i < 5000; i++) {
				if (state[i])
					all += i + ";";
			}
			for (IMapEventListener listeners : listeners)
				listeners.provinceClicked(
						String.valueOf((Integer) map2.get(target)), all);
		} else {
			for (IMapEventListener listeners : listeners)
				listeners.provinceClicked(
						String.valueOf((Integer) map2.get(target)),
						String.valueOf((Integer) map2.get(target)) + ";");
		}
		Graphics gr = this.getGraphics();
		for (int i = 0; i < 5000; i++) {
			if (state[i]) {

				Point left = calculateChords(Bleft[i]);
				Point right = calculateChords(Bright[i]);
				Point north = calculateChords(Bnorth[i]);
				Point south = calculateChords(Bsouth[i]);
				double zoom = (double) (this.newHeight * 2)
						/ (double) this.getHeight();

				int size = (int) ((double) 13 / zoom) + 2;
				int x = (int) ((left.getX() + right.getX()) / 2);
				int y = (int) ((north.getY() + south.getY()) / 2);

				// g.fillRect((int) left.getX() - 1, (int) left.getY() - 1, 3,
				// 3);
				// g.fillRect((int) right.getX() - 1, (int) right.getY() - 1, 3,
				// 3);
				// g.fillRect((int) south.getX() - 1, (int) south.getY() - 1, 3,
				// 3);
				// g.fillRect((int) north.getX() - 1, (int) north.getY() - 1, 3,
				// 3);

				gr.setColor(Color.RED);
				gr.fillOval((int) x - size / 2, (int) y - size / 2, size, size);

			}
		}

	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent arg0) {
		drag = arg0.getPoint();

	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void mouseDragged(MouseEvent e) {

		float factor = (float) newWidth / (float) this.getWidth();
		factor = factor * 2;

		x = -(int) ((e.getX() - drag.getX()) * factor) + x;
		y = -(int) ((e.getY() - drag.getY()) * factor) + y;

		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;

		drag = e.getPoint();
		repaint();

	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		zoomFactorSelected = zoomFactorSelected + zoomFactor
				* e.getWheelRotation();
		if (zoomFactorSelected < 0.1)
			zoomFactorSelected = 0.1f;
		if (zoomFactorSelected > 0.5)
			zoomFactorSelected = 0.5f;
		repaint();
	}
}
