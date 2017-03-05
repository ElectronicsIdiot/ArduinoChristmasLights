package electronicsidiot.stepmanialights;

public class BPM {
	Segment[] segments;

	public BPM(String data) {
		String[] segments = data.split(",");
		this.segments = new Segment[segments.length];

		int i = 0;

		for (String segment : segments) {
			String[] values = segment.split("=");

			this.segments[i++] = new Segment(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
		}
	}

	public double getBPM(double beat) {
		double compareBeat = 0;
		double bpm = -1;

		for (Segment segment : segments) {
			if (segment.getBeat() <= beat && segment.getBeat() >= compareBeat) {
				compareBeat = segment.getBeat();
				bpm = segment.getBpm();
			}
		}

		return bpm;
	}

	private static class Segment {
		private final double beat, bpm;

		private Segment(double beat, double bpm) {
			this.beat = beat;
			this.bpm = bpm;
		}

		public double getBeat() {
			return beat;
		}

		public double getBpm() {
			return bpm;
		}
	}
}
