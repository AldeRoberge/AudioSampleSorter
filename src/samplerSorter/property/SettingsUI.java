package samplerSorter.property;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import samplerSorter.GUI.SorterUI;
import samplerSorter.GUI.Sorter;
import samplerSorter.logger.Logger;
import samplerSorter.util.ui.ToastMessage;

public class SettingsUI extends JPanel {

	private static final String TAG = "SettingsPanel";

	private JLabel lblMasterVolume;
	private JLabel lblMasterPan;
	private JComboBox<String> comboBox;

	private JCheckBox chckbxIncludeSubfoldersInSearch;

	private Sorter sorter;

	/**
	 * Create the panel.
	 */
	public SettingsUI(Sorter s) {
		this.sorter = s;

		setLayout(null);

		setBounds(0, 0, 400, 290);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Display file extensions");
		chckbxNewCheckBox.setSelected(Properties.DISPLAY_SOUND_SUFFIXES.isTrue());
		chckbxNewCheckBox.setBounds(22, 115, 186, 25);
		ActionListener displayFileExtensionsAction = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				Properties.DISPLAY_SOUND_SUFFIXES.setNewValue(chckbxNewCheckBox.isSelected());
				sorter.refreshInfoPanels();
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

		JCheckBox chckbxPlayAudioOn = new JCheckBox("Play Audio on Click");
		chckbxPlayAudioOn.setSelected(Properties.PLAY_ON_CLICK.isTrue());
		chckbxPlayAudioOn.setBounds(22, 85, 147, 25);
		ActionListener act = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				Properties.PLAY_ON_CLICK.setNewValue(chckbxPlayAudioOn.isSelected());
			}
		};
		chckbxPlayAudioOn.addActionListener(act);
		add(chckbxPlayAudioOn);

		comboBox = new JComboBox<String>();

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (!info.getName().equals("Windows")) { // Windows theme causes
																// major glitch with
															// TabbedPanes TODO
					comboBox.addItem(info.getName());
				}

			}
		} catch (Exception e) {
			Logger.logError(TAG, "Error while retrieving look and feels", e);
		}

		comboBox.setBounds(79, 208, 129, 22);
		add(comboBox);

		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (comboBox.getSelectedItem() != null) {
					String newTheme = (String) comboBox.getSelectedItem();

					Properties.THEME.setNewValue(newTheme);
					setNewTheme(newTheme);
					setNewTheme(newTheme);
				}
			}

		});

		setNewTheme(Properties.THEME.getValue());

		JLabel lblTheme = new JLabel("Theme");
		lblTheme.setBounds(22, 211, 56, 16);
		add(lblTheme);

		chckbxIncludeSubfoldersInSearch = new JCheckBox("Include subfolders on import");
		chckbxIncludeSubfoldersInSearch = new JCheckBox("Include subfolders on import");
		chckbxIncludeSubfoldersInSearch.setToolTipText("Include folders inside the selected folders");
		chckbxIncludeSubfoldersInSearch.setBounds(22, 145, 208, 25);
		chckbxIncludeSubfoldersInSearch.setSelected(Properties.INCLUDE_SUBFOLDERS.isTrue());
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				Properties.INCLUDE_SUBFOLDERS.setNewValue(chckbxIncludeSubfoldersInSearch.isSelected());
				ToastMessage.makeToast(chckbxIncludeSubfoldersInSearch, "MHM MHM!", 3000, false);
			}
		};
		chckbxIncludeSubfoldersInSearch.addActionListener(actionListener);

		add(chckbxIncludeSubfoldersInSearch);

	}

	private void setNewTheme(String theme) {

		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if (info.getName().equals(Properties.THEME.getValue())) {
				System.out.println("Updating theme...");

				try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					Logger.logError(TAG, "Error while setting new theme");
					e.printStackTrace();
				}

				// Refresh the UI
				sorter.refreshInfoPanels();

				//This is experimental TODO
				for (Window w : SorterUI.getOwnerlessWindows()) {
					SwingUtilities.updateComponentTreeUI(w);
				}

				comboBox.setSelectedItem(theme);

			}
		}

	}

	private void updateAudioVolume(double volume) {

		sorter.audioPlayer.setVolume(volume);

		lblMasterVolume.setText("Master volume (" + (int) (volume) + "%)");
	}

	private void updateAudioPan(double pan) {

		sorter.audioPlayer.setPan(pan);

		lblMasterPan.setText("Master pan (" + (int) (pan) + "%)");
	}
}
