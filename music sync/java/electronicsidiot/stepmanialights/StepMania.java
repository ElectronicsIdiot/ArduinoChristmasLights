package electronicsidiot.stepmanialights;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StepMania {
	private final HashMap<String, String> tags = new HashMap<>();
	private BPM bpm;
	private final List<Notes> notes = new ArrayList<>();

	public StepMania(Path path) throws Exception {
		String file = new String(Files.readAllBytes(path));

		file = file.replaceAll("//.*\\s", ""); // Remove comments
		file = file.replace("\n", ""); // Remove newlines
		file = file.replace("\r", ""); // Remove returns

		String[] tags = file.split(";");

		for (String tag: tags) {
			boolean isKey = true;
			StringBuilder key = new StringBuilder();
			StringBuilder value = new StringBuilder();

			for (int i = 1; i < tag.length(); i++) {
				char c = tag.charAt(i);

				if (isKey) {
					if (c == ':') {
						isKey = false;
						continue;
					}

					key.append(c);
					continue;
				}

				value.append(c);
			}

			if (key.toString().equalsIgnoreCase("NOTES")) {
				notes.add(new Notes(value.toString()));
				continue;
			}

			if (key.toString().equalsIgnoreCase("BPMS")) {
				bpm = new BPM(value.toString());
				continue;
			}

			this.tags.put(key.toString(), value.toString());
		}
	}

	public HashMap<String, String> getTags() {
		return tags;
	}

	public BPM getBpm() {
		return bpm;
	}

	public List<Notes> getNotes() {
		return notes;
	}
}
