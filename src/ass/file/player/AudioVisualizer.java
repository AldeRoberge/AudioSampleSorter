package ass.file.player;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import ass.file.player.spectrum.SpectrumTimeAnalyzer;
import constants.Constants;
import constants.property.PropertiesImpl;

public class AudioVisualizer extends JPanel {

	private static final String TAG = "AudioVisualizer";

	private static final String OFF = "off";
	private static final String OSCILLO = "oscillo";
	private static final String SPECTRUM = "spectrum";

	public static SpectrumTimeAnalyzer analyzer;

	public static AudioVisualizer me;

	private AudioVisualizer() {
	}

	public static AudioVisualizer getVisualiser() {

		if (me == null) {
			me = new AudioVisualizer();

			me.init();

			me.setLayout(new BorderLayout(0, 0));
			me.add(analyzer, BorderLayout.CENTER);
		}

		return me;
	}

	private void init() {
		analyzer = new SpectrumTimeAnalyzer();

		switch (PropertiesImpl.SPECTRUM_ANALYZER_STATUS.getValue()) {
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