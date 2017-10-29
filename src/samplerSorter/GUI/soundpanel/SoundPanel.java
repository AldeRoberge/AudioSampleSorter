package samplerSorter.GUI.soundpanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import samplerSorter.GUI.Sorter;
import samplerSorter.constants.Constants;
import samplerSorter.property.Properties;
import samplerSorter.util.file.FileSizeToString;

public class SoundPanel extends JPanel {

	private static final Color SELECTED = Constants.SICK_PURPLE;
	private static final Color UNSELECTED = Color.LIGHT_GRAY;

	private static final long serialVersionUID = 1L;

	Sorter sorter;

	private Sound sound;

	private JLabel lblFilename;
	private JLabel lblFileSize;

	private SoundPanel me = this;

	public SoundPanel(Sound sound, Sorter s) {
		this.sorter = s;
		this.sound = sound;

		setBackground(UNSELECTED);

		setPreferredSize(new Dimension(579, 48));
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
		lblFilename.setForeground(Constants.SICK_PURPLE);

		lblFileSize = new JLabel("File Size");
		lblFileSize.setBounds(12, 22, 552, 16);
		containerPanel.add(lblFileSize);
		lblFileSize.setForeground(Constants.SICK_PURPLE);
		updateFilenameLabel();

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				if (SwingUtilities.isLeftMouseButton(e)) {
					sorter.soundPanelIsLeftClicked(me);
				} else if (SwingUtilities.isRightMouseButton(e)) {
					sorter.soundPanelIsRightClicked(me);
				}

			}
		});

		DefaultContextMenu.addDefaultContextMenu(this);

	}

	public void updateFilenameLabel() {
		lblFilename.setText(sound.getName(Properties.DISPLAY_SOUND_SUFFIXES.isTrue()));
		lblFileSize.setText(FileSizeToString.getFileSizeAsString(sound.file));
	}

	public void setSelected(boolean isSelected) {
		if (isSelected) {

			setBackground(SELECTED);

			if (Properties.PLAY_ON_CLICK.isTrue()) {
				playOrPause();
			}

		} else {
			setBackground(UNSELECTED);
		}
	}

	//Should only be used by Actions, use setSelected(true) instead for selection
	public void playOrPause() {
		if (sound.isPlaying) {
			sound.stop();
		} else {
			sound.play();
		}
	}



	@Override
	public String toString() {
		return "SoundPanel [sound=" + sound + ", lblFilename=" + lblFilename + ", lblFileSize=" + lblFileSize + "]";
	}

}
