package electronicsidiot.stepmanialights;

import java.util.LinkedList;
import java.util.List;

public class Notes {
	private String type, description, difficultyClass, difficultyMeter, radarValues;
	private final List<String> measures = new LinkedList<>();

	public Notes(String data) {
		int i = 0;

		for (String element : data.split(":")) {
			element = element.trim();

			switch (i++) {
				case 0:
					type = element;
					break;

				case 1:
					description = element;
					break;

				case 2:
					difficultyClass = element;
					break;

				case 3:
					difficultyMeter = element;
					break;

				case 4:
					radarValues = element;
					break;

				case 5:
					element = element.replaceAll("\\s+", "");

					for (String measure : element.split(",")) {
						measures.add(measure);
					}
			}
		}
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public String getDifficultyClass() {
		return difficultyClass;
	}

	public String getDifficultyMeter() {
		return difficultyMeter;
	}

	public String getRadarValues() {
		return radarValues;
	}

	public List<String> getMeasures() {
		return measures;
	}
}
