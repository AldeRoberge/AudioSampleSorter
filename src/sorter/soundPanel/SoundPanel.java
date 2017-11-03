package sorter.soundPanel;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import constants.Constants;
import property.Properties;
import sorter.Sorter;
import util.file.FileSizeToString;

public class SoundPanel extends JPanel {
	
	private static final Color SELECTED = Constants.SICK_PURPLE;
	private static final Color UNSELECTED = Color.LIGHT_GRAY;
	private static final Color UNSELECTED_LABEL = new Color(18, 18, 24); // Eigengrau :D

	private static final long serialVersionUID = 1L;

	Sorter sorter;

	public Sound sound;

	private JLabel lblFilename;
	private JLabel lblFileSize;

	private SoundPanel me = this;
	public boolean selected;

	public SoundPanel(Sound sound, Sorter s) {
		this.sorter = s;
		this.sound = sound;

		setBackground(UNSELECTED);

		setPreferredSize(new Dimension(579, 47));
		setLayout(null);

		JPanel containerPanel = new JPanel();
		containerPanel.setBackground(Color.WHITE); //always white, the other panel changes
		containerPanel.setBounds(1, 1, 576, 44);
		add(containerPanel);
		containerPanel.setLayout(null);

		lblFilename = new JLabel();
		lblFilename.setBounds(12, 5, 552, 16);
		containerPanel.add(lblFilename);
		lblFilename.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFilename.setForeground(UNSELECTED_LABEL);

		lblFileSize = new JLabel("File Size");
		lblFileSize.setBounds(12, 22, 552, 16);
		containerPanel.add(lblFileSize);
		lblFileSize.setForeground(UNSELECTED_LABEL);
		updateFilenameLabel();

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				if (SwingUtilities.isLeftMouseButton(e)) { //right click is handled by DefaultContextMenu
					sorter.soundPanelIsLeftClicked(me);
				}

			}
		});

		//SoundPanelContextMenu.addDefaultContextMenu(this); TODO

	}

	public void updateFilenameLabel() {
		lblFilename.setText(sound.getName(Properties.DISPLAY_SOUND_SUFFIXES.getValueAsBoolean()));
		lblFileSize.setText(FileSizeToString.getFileSizeAsString(sound.file));
	}

	//Dont call this directly, use Sorter. setSelected instead

	public void updateIsSelected(boolean isSelected, boolean shouldPlay) {
		selected = isSelected;
		
		if (isSelected) {

			setBackground(SELECTED);
			lblFilename.setForeground(SELECTED);
			lblFileSize.setForeground(SELECTED);

			if (shouldPlay) {
				sound.playOrPause();
			}

		} else {

			setBackground(UNSELECTED);
			lblFilename.setForeground(UNSELECTED_LABEL);
			lblFileSize.setForeground(UNSELECTED_LABEL);

			sound.pause();
		}
	}

	public File getFile() {
		return sound.file;
	}

	@Override
	public String toString() {
		return "SoundPanel [sound=" + sound + ", lblFilename=" + lblFilename + ", lblFileSize=" + lblFileSize + "]";
	}

	public void openFileLocation() {
		try {
			Desktop.getDesktop().open(getFile().getParentFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
