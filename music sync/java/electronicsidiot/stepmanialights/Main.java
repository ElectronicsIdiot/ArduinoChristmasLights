package electronicsidiot.stepmanialights;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

public class Main {
	static boolean[] hold = new boolean[4];
	static boolean[] display = new boolean[4];
	static GUI gui;
	static SerialPortWriter writer;

	public static boolean firstRun = true;
	public static volatile boolean stop = false;

	public static void main(String[] args) throws  Exception {
		writer = new SerialPortWriter();

		Thread.sleep(2000);

		gui = new GUI();
		com.sun.javafx.application.PlatformImpl.startup(new Runnable() {public void run(){}});

		gui.setDropTarget(new DropTarget() {
			@Override
			public synchronized void drop(DropTargetDropEvent dtde) {

				dtde.acceptDrop(DnDConstants.ACTION_COPY);
				try {
					List<File> files = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					File file = files.get(0);

					stopAndPlay(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void stopAndPlay(File file) throws Exception {
		if (!firstRun) {
			writer.write((byte) 0);
			stop = true;

			while(stop);
		}

		firstRun = false;

		play(file);
	}

	public static void play(File file) throws Exception {
		StepMania sm = new StepMania(file.toPath());

		Notes notesFind = null;

		for (Notes n : sm.getNotes()) {
			if (n.getDifficultyClass().equalsIgnoreCase("challenge") && n.getType().equalsIgnoreCase("dance-single")) {
				notesFind = n;
			}

			if (notesFind == null && n.getType().equalsIgnoreCase("dance-single")) {
				notesFind = n;
			}
		}

		System.out.println(notesFind.getType());

		final Notes notes = notesFind;

		String music = sm.getTags().get("MUSIC");

		String title = sm.getTags().get("TITLE");
		if (title == null) title = "";

		String artist = sm.getTags().get("ARTIST");
		if (artist == null) artist = "";

		gui.setTitle(artist + " - " + title);

		File musicFile = new File(file.getParentFile(), music);
		String filePath = musicFile.toURI().toString();
		Media media = new Media(filePath);
		MediaPlayer mp = new MediaPlayer(media);

		final double offset = Double.parseDouble(sm.getTags().get("OFFSET"));
		System.out.println("Offset: " + offset);

		new Thread() {
			public void run() {
				double beat = 0;
				double last = 0;
				byte lastByte = 0;

				for (;;) {
					if (stop) {
						mp.dispose();
						stop = false;
						break;
					}

					double seconds = mp.getCurrentTime().toSeconds() + offset;

					if (seconds < 0) {
						continue;
					}

					double change = seconds - last;
					last = seconds;
					double bpm = sm.getBpm().getBPM(beat);

					beat += (bpm / 60) * change;

					int measureIndex = (int) (beat / 4);
					double inMeasure = beat / 4 - measureIndex;

					String measure = notes.getMeasures().get(measureIndex);
					int start = ((int) ((measure.length() / 4) * inMeasure)) * 4;
					byte currentByte = 0;

					for (int i = 0; i < 4; i++) {
						char c = measure.charAt(start + i);

						if (c == '2') {
							hold[i] = true;
						} else if (c == '3') {
							hold[i] = false;
						} else if (c != '0') {
							display[i] = true;
						} else {
							display[i] = false;
						}

						display[i] |= hold[i];
						if (display[i]) currentByte |= (1 << i);
					}

					if (currentByte != lastByte) {
						writer.write(currentByte);
					}

					lastByte = currentByte;

					gui.red = display[0];
					gui.green = display[1];
					gui.blue = display[2];
					gui.yellow = display[3];
					gui.repaint();


					try {
						Thread.sleep(10);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();

		mp.play();
		System.out.println("Playing...");
	}
}
