package ass.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ass.file.player.AudioPlayer;
import constants.property.PropertiesImpl;
import ui.MiddleOfTheScreen;

public class SettingsUI extends JPanel {

	private JLabel lblMasterVolume;
	private JLabel lblMasterPan;

	private JCheckBox chckbxIncludeSubfoldersInSearch;

	private AudioPlayer audioPlayer;
	
	/**
	 * Create the panel.
	 */
	public SettingsUI(AudioPlayer p) {
		
		this.audioPlayer = p;

		setLayout(null);

		setBounds(0, 0, 400, 290);

		setLocation(MiddleOfTheScreen.getMiddleOfScreenLocationFor(this));

		//

		lblMasterVolume = new JLabel("Master Volume (100%)");
		lblMasterVolume.setBounds(22, 51, 147, 25);
		add(lblMasterVolume);

		JSlider volumeSlider = new JSlider();
		volumeSlider.setPaintTicks(true);
		volumeSlider.setBounds(167, 51, 221, 25);

		volumeSlider.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) { // On mouse release
				PropertiesImpl.MAIN_VOLUME_SLIDER_VALUE.setNewValue(volumeSlider.getValue() + "");
			}
		});

		int newVolumeValue = PropertiesImpl.MAIN_VOLUME_SLIDER_VALUE.getValueAsInt();
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
		lblMasterPan.setBounds(22, 13, 147, 25);
		add(lblMasterPan);

		JSlider panSlider = new JSlider();
		panSlider.setPaintTicks(true);
		panSlider.setBounds(167, 13, 221, 25);

		panSlider.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) { // On mouse release
				PropertiesImpl.MAIN_PAN_SLIDER_VALUE.setNewValue(panSlider.getValue() + "");
			}
		});

		int newPanValue = PropertiesImpl.MAIN_PAN_SLIDER_VALUE.getValueAsInt();
		updateAudioPan(newPanValue);
		panSlider.setValue(newPanValue);

		panSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {

				updateAudioPan(panSlider.getValue());

			}

		});

		add(panSlider);

		//

		JCheckBox chckbxPlayAudioOn = new JCheckBox("Play audio on click");
		chckbxPlayAudioOn.setToolTipText("Toggles weither to play audio files on click");
		chckbxPlayAudioOn.setSelected(PropertiesImpl.PLAY_ON_CLICK.getValueAsBoolean());
		chckbxPlayAudioOn.setBounds(22, 115, 147, 25);
		ActionListener act = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				PropertiesImpl.PLAY_ON_CLICK.setNewValue(chckbxPlayAudioOn.isSelected());
			}
		};
		chckbxPlayAudioOn.addActionListener(act);
		add(chckbxPlayAudioOn);

		//

		chckbxIncludeSubfoldersInSearch = new JCheckBox("Include subfolders on import");
		chckbxIncludeSubfoldersInSearch = new JCheckBox("Include subfolders on import");
		chckbxIncludeSubfoldersInSearch.setToolTipText("Toggles weither if we should import files contained inside the selected folders");
		chckbxIncludeSubfoldersInSearch.setBounds(22, 145, 208, 25);
		chckbxIncludeSubfoldersInSearch.setSelected(PropertiesImpl.INCLUDE_SUBFOLDERS.getValueAsBoolean());
		ActionListener includeSubfoldersAction = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				PropertiesImpl.INCLUDE_SUBFOLDERS.setNewValue(chckbxIncludeSubfoldersInSearch.isSelected());
			}
		};
		chckbxIncludeSubfoldersInSearch.addActionListener(includeSubfoldersAction);

		add(chckbxIncludeSubfoldersInSearch);

		//

		JCheckBox chckbxPromptOnClose = new JCheckBox("Prompt on close");
		chckbxPromptOnClose.setToolTipText("Toggles the 'Are you sure you want to exit?' dialog");
		chckbxPromptOnClose.setSelected(PropertiesImpl.PROMPT_ON_EXIT.getValueAsBoolean());
		chckbxPromptOnClose.setBounds(22, 175, 147, 25);
		ActionListener changePromptOnClose = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				PropertiesImpl.PROMPT_ON_EXIT.setNewValue(chckbxPromptOnClose.isSelected());
				//sorter.refreshInfoPanels();
			}
		};
		chckbxPromptOnClose.addActionListener(changePromptOnClose);
		add(chckbxPromptOnClose);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(22, 98, 366, 2);
		add(separator);

	}

	private void updateAudioVolume(double volume) {

		audioPlayer.setVolume(volume);

		lblMasterVolume.setText("Master volume (" + (int) (volume) + "%)");
	}

	private void updateAudioPan(double pan) {

		audioPlayer.setPan(pan);

		lblMasterPan.setText("Master pan (" + (int) (pan) + "%)");
	}
}
