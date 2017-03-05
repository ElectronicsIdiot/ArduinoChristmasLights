package electronicsidiot.stepmanialights;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {
	public boolean red, green, blue, yellow;

	public GUI() {
		setTitle("Drop file...");
		setSize(1024, 512);
		setResizable(false);

		add(new Component() {
			@Override
			public void paint(Graphics g) {
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 1024, 512);

				if (red) {
					g.setColor(Color.RED);
					g.fillRect(0, 0, 256, 512);
				}

				if (green) {
					g.setColor(Color.GREEN);
					g.fillRect(256, 0, 256, 512);
				}

				if (blue) {
					g.setColor(Color.BLUE);
					g.fillRect(512, 0, 256, 512);
				}

				if (yellow) {
					g.setColor(Color.yellow);
					g.fillRect(768, 0, 256, 512);
				}
			}
		});

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
