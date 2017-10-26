package samplerSorter.audioplayer;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import samplerSorter.Constants;
import samplerSorter.properties.Properties;

public class AudioVisualizer {

	public static String status;

	public static final String OFF = "off";
	public static final String OSCILLO = "oscillo";
	public static final String SPECTRUM = "spectrum";

	public SpectrumTimeAnalyzer analyzer;

	public void setStatus(String newStatus) {
		System.out.println(newStatus);
		status = newStatus;
	}

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AudioVisualizer() {

		initAnalyzer();

		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));

		contentPane.add(analyzer, BorderLayout.CENTER);

	}

	private void initAnalyzer() {
		analyzer = new SpectrumTimeAnalyzer();

		status = Properties.SPECTRUM_ANALYZER_STATUS.getValue();

		if (status.equals(OFF)) {
			analyzer.setDisplayMode(SpectrumTimeAnalyzer.DISPLAY_MODE_OFF);
		} else if (status.equals(OSCILLO)) {
			analyzer.setDisplayMode(SpectrumTimeAnalyzer.DISPLAY_MODE_SCOPE);
		} else if (status.equals(SPECTRUM)) {
			analyzer.setDisplayMode(SpectrumTimeAnalyzer.DISPLAY_MODE_SPECTRUM_ANALYSER);
		} else {
			analyzer.setDisplayMode(SpectrumTimeAnalyzer.DISPLAY_MODE_SPECTRUM_ANALYSER);
		}

		analyzer.setSpectrumAnalyserBandCount(19);
		//analyzer.setVisColor(viscolor);
		analyzer.setBackground(Constants.SICK_PURPLE);
		analyzer.setScopeColor(Color.WHITE);
		analyzer.setLocation(24, 44);
		analyzer.setSize(76, 15);
		analyzer.setSpectrumAnalyserDecay(0.05f);
		int fps = SpectrumTimeAnalyzer.DEFAULT_FPS;
		analyzer.setFps(fps);
		analyzer.setPeakDelay((int) (fps * SpectrumTimeAnalyzer.DEFAULT_SPECTRUM_ANALYSER_PEAK_DELAY_FPS_RATIO));
		analyzer.setToolTipText("Click to switch between modes");

	}

}
