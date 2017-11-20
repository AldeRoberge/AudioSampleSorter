package ass.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ass.file.Manager;
import ass.file.player.AudioPlayer;
import constants.Properties;
import ui.MiddleOfTheScreen;

public class Settings extends JPanel {

	private static final String TAG = "SettingsPanel";

	private JLabel lblMasterVolume;
	private JLabel lblMasterPan;

	private JCheckBox chckbxIncludeSubfoldersInSearch;

	private AudioPlayer audioPlayer;

	/**
	 * Create the panel.
	 */
	public Settings(Manager fMan) {

		this.audioPlayer = fMan.fileVisualiser.getAudioPlayer();

		setLayout(null);

		setBounds(0, 0, 400, 290);

		setLocation(MiddleOfTheScreen.getMiddleOfScreenLocationFor(this));

		JCheckBox chckbxNewCheckBox = new JCheckBox("Display file extensions");
		chckbxNewCheckBox.setSelected(Properties.DISPLAY_SOUND_SUFFIXES.getValueAsBoolean());
		chckbxNewCheckBox.setBounds(22, 115, 186, 25);
		ActionListener displayFileExtensionsAction = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				Properties.DISPLAY_SOUND_SUFFIXES.setNewValue(chckbxNewCheckBox.isSelected());
				//sorter.refreshInfoPanels();
			}
		};
		chckbxNewCheckBox.addActionListener(displayFileExtensionsAction);
		add(chckbxNewCheckBox);

		//

		lblMasterVolume = new JLabel("Master Volume (100%)");
		lblMasterVolume.setBounds(22, 51, 147, 25);
		add(lblMasterVolume);

		JSlider volumeSlider = new JSlider();
		volumeSlider.setBounds(167, 51, 200, 25);

		volumeSlider.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) { // On mouse release
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
		lblMasterPan.setBounds(22, 13, 147, 25);
		add(lblMasterPan);

		JSlider panSlider = new JSlider();
		panSlider.setBounds(167, 13, 200, 25);

		panSlider.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) { // On mouse release
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

		//

		JCheckBox chckbxPlayAudioOn = new JCheckBox("Play Audio on Click");
		chckbxPlayAudioOn.setSelected(Properties.PLAY_ON_CLICK.getValueAsBoolean());
		chckbxPlayAudioOn.setBounds(22, 85, 147, 25);
		ActionListener act = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				Properties.PLAY_ON_CLICK.setNewValue(chckbxPlayAudioOn.isSelected());
			}
		};
		chckbxPlayAudioOn.addActionListener(act);
		add(chckbxPlayAudioOn);

		//

		chckbxIncludeSubfoldersInSearch = new JCheckBox("Include subfolders on import");
		chckbxIncludeSubfoldersInSearch = new JCheckBox("Include subfolders on import");
		chckbxIncludeSubfoldersInSearch.setToolTipText("Include folders inside the selected folders");
		chckbxIncludeSubfoldersInSearch.setBounds(22, 145, 208, 25);
		chckbxIncludeSubfoldersInSearch.setSelected(Properties.INCLUDE_SUBFOLDERS.getValueAsBoolean());
		ActionListener includeSubfoldersAction = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				Properties.INCLUDE_SUBFOLDERS.setNewValue(chckbxIncludeSubfoldersInSearch.isSelected());
			}
		};
		chckbxIncludeSubfoldersInSearch.addActionListener(includeSubfoldersAction);

		add(chckbxIncludeSubfoldersInSearch);

		//

		JCheckBox chckbxPromptOnClose = new JCheckBox("Prompt on close");
		chckbxPromptOnClose.setSelected(Properties.PROMPT_ON_EXIT.getValueAsBoolean());
		chckbxPromptOnClose.setBounds(22, 175, 147, 25);
		ActionListener changePromptOnClose = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				Properties.PROMPT_ON_EXIT.setNewValue(chckbxPromptOnClose.isSelected());
				//sorter.refreshInfoPanels();
			}
		};
		chckbxPromptOnClose.addActionListener(changePromptOnClose);
		add(chckbxPromptOnClose);

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
