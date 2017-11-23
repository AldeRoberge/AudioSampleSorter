package ass.file.player;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import constants.Constants;
import constants.property.Properties;
import logger.Logger;

public class AudioVisualizer {

	private static final String TAG = "AudioVisualizer";

	private static final boolean DEBUG = false;

	private static String status;

	private static final String OFF = "off";
	private static final String OSCILLO = "oscillo";
	private static final String SPECTRUM = "spectrum";

	public SpectrumTimeAnalyzer analyzer;

	public void setStatus(String newStatus) {
		if (DEBUG) {
			Logger.logInfo(TAG, newStatus);
		}

		status = newStatus;
	}

	/**
	 * Create the frame.
	 */
	public AudioVisualizer() {

		initAnalyzer();

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));

		contentPane.add(analyzer, BorderLayout.CENTER);

	}

	private void initAnalyzer() {
		analyzer = new SpectrumTimeAnalyzer();

		status = Properties.SPECTRUM_ANALYZER_STATUS.getValue();

		switch (status) {
		case OFF:
			analyzer.setDisplayMode(SpectrumTimeAnalyzer.DISPLAY_MODE_OFF);
			break;
		case OSCILLO:
			analyzer.setDisplayMode(SpectrumTimeAnalyzer.DISPLAY_MODE_SCOPE);
			break;
		case SPECTRUM:
			analyzer.setDisplayMode(SpectrumTimeAnalyzer.DISPLAY_MODE_SPECTRUM_ANALYSER);
			break;
		default:
			analyzer.setDisplayMode(SpectrumTimeAnalyzer.DISPLAY_MODE_SPECTRUM_ANALYSER);
			break;
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
		analyzer.setToolTipText("Click to switch between modes of the Audio Visualizer");

	}

}