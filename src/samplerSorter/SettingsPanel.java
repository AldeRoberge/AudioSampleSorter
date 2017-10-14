package samplerSorter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import samplerSorter.properties.Properties;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SettingsPanel extends JPanel {
	private JLabel lblMasterVolume;
	private JLabel lblMasterPan;
	private JSlider panSlider;

	/**
	 * Create the panel.
	 */
	public SettingsPanel() {
		setLayout(null);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Display sound suffixes");
		chckbxNewCheckBox.setSelected(Properties.DISPLAY_SOUND_SUFFIXES.isTrue());
		chckbxNewCheckBox.setBounds(8, 9, 186, 25);
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				Properties.DISPLAY_SOUND_SUFFIXES.setNewValue(chckbxNewCheckBox.isSelected());
				RunSS.refreshSoundPanelAfterSuffixChange();
			}
		};
		chckbxNewCheckBox.addActionListener(actionListener);
		add(chckbxNewCheckBox);

		//

		lblMasterVolume = new JLabel("Master Volume (100%)");
		lblMasterVolume.setBounds(8, 125, 147, 25);
		add(lblMasterVolume);

		JSlider volumeSlider = new JSlider();
		volumeSlider.setBounds(167, 125, 214, 25);

		volumeSlider.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) { //On mouse release
				Properties.MAIN_VOLUME_SLIDER_VALUE.setNewValue(volumeSlider.getValue() + "");
			}
		});

		int newVolumeValue = Properties.MAIN_VOLUME_SLIDER_VALUE.getValueAsInt();
		updateAudioVolume(newVolumeValue);

		volumeSlider.setValue(newVolumeValue);

		volumeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {

				updateAudioVolume(volumeSlider.getValue());
			}

		});

		add(volumeSlider);

		//

		lblMasterPan = new JLabel("Master pan (50%)");
		lblMasterPan.setBounds(8, 94, 147, 25);
		add(lblMasterPan);

		JSlider panSlider = new JSlider();
		panSlider.setBounds(157, 94, 214, 25);

		panSlider.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) { //On mouse release
				Properties.MAIN_PAN_SLIDER_VALUE.setNewValue(panSlider.getValue() + "");
			}
		});

		int newPanValue = Properties.MAIN_PAN_SLIDER_VALUE.getValueAsInt();
		updateAudioPan(newPanValue);

		panSlider.setValue(newPanValue);

		panSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {

				updateAudioPan(panSlider.getValue());

			}

		});

		add(panSlider);

	}

	private void updateAudioVolume(double volume) {

		RunSS.audioPlayer.setVolume(volume);

		lblMasterVolume.setText("Master volume (" + (int) (volume) + "%)");
	}

	private void updateAudioPan(double pan) {

		RunSS.audioPlayer.setPan(pan);

		lblMasterPan.setText("Master pan (" + (int) (pan) + "%)");
	}

}
